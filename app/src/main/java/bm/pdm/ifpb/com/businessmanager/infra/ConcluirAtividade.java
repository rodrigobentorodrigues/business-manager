package bm.pdm.ifpb.com.businessmanager.infra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import bm.pdm.ifpb.com.businessmanager.interfaces.RestTarefa;
import bm.pdm.ifpb.com.businessmanager.views.MenuActivity;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConcluirAtividade extends AsyncTask<String, Void, Boolean>{

    private Context context;
    private Tarefa tarefa;
    private ProgressDialog progressDialog;

    public ConcluirAtividade(Context context, Tarefa tarefa){
        this.context = context;
        this.tarefa = tarefa;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Concluindo atividade",
                "Aguarde enquanto enviamos os dados para o servidor");
        progressDialog.setCancelable(false);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(strings[0]).addConverterFactory(GsonConverterFactory.create()).build();
        RestTarefa restTarefa = retrofit.create(RestTarefa.class);
        Call<Tarefa> tarefaCall = restTarefa.responderTarefa(tarefa.getId(), tarefa.getDeUsuario(),
                tarefa.getParaUsuario(), tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getData(), true);
        try {
            tarefaCall.execute();
            return true;
        } catch (IOException e) {
            Toast.makeText(context, "Ocorreu um erro, tente novamente!",
                    Toast.LENGTH_SHORT).show();
            Log.d("Erro", e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(aBoolean){
            progressDialog.dismiss();
            Toast.makeText(context, "Atividade marcada como concluida", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MenuActivity.class);
            context.startActivity(intent);
        } else {
            Log.i("Erro", "Ocorreu um erro ao enviar os dados");
        }
    }
}
