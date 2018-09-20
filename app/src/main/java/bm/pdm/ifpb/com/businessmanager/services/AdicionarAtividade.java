package bm.pdm.ifpb.com.businessmanager.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import bm.pdm.ifpb.com.businessmanager.interfaces.RestTarefa;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdicionarAtividade extends IntentService {

    public AdicionarAtividade() {
        super("AdicionarAtividade");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("Atividade", "Serviço em execução");
        String url = intent.getStringExtra("url");
        Tarefa tarefa = (Tarefa) intent.getSerializableExtra("tarefa");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).
                addConverterFactory(GsonConverterFactory.create()).build();
        RestTarefa adicionarTarefa = retrofit.create(RestTarefa.class);
        Call<Tarefa> tarefaCall = adicionarTarefa.adicionarTarefa(0,
                tarefa.getDeUsuario(), tarefa.getParaUsuario(), tarefa.getTitulo(),
                tarefa.getDescricao(), tarefa.getData(), false);
        try {
            tarefaCall.execute();
            Intent sendBroad = new Intent();
            sendBroad.setAction("cad-ativ");
            sendBroadcast(sendBroad);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
