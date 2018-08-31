package bm.pdm.ifpb.com.businessmanager.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.io.IOException;

import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.interfaces.RestDuvida;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdicionarDuvida extends IntentService {

    public AdicionarDuvida() {
        super("AdicionarDuvida");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String url = intent.getStringExtra("url");
        Duvida duvida = (Duvida) intent.getSerializableExtra("duvida");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).
                addConverterFactory(GsonConverterFactory.create()).build();
        RestDuvida restDuvida = retrofit.create(RestDuvida.class);
        Call<Duvida> duvidaCall = restDuvida.adicionarDuvida(duvida.getId(), duvida.getDeUsuario(),
                duvida.getParaUsuario(), duvida.getPergunta(), duvida.getResposta());
        try{
            duvidaCall.execute();
            Intent intentBroad = new Intent();
            intentBroad.setAction("cad-duv");
            sendBroadcast(intentBroad);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
