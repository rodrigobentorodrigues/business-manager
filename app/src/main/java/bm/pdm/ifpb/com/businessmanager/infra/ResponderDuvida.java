package bm.pdm.ifpb.com.businessmanager.infra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.interfaces.RestDuvida;
import bm.pdm.ifpb.com.businessmanager.views.MenuActivity;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResponderDuvida extends AsyncTask<String, Void, Boolean> {

    private Context context;
    private Duvida duvida;
    private ProgressDialog progressDialog;

    public ResponderDuvida(Context context, Duvida duvida) {
        this.context = context;
        this.duvida = duvida;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Respondendo dúvida",
                "Aguarde enquanto enviamos os dados para o servidor");
        progressDialog.setCancelable(false);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(strings[0]).
                addConverterFactory(GsonConverterFactory.create()).build();
        RestDuvida restDuvida = retrofit.create(RestDuvida.class);
        Call<Duvida> duvidaCall = restDuvida.responderDuvida(duvida.getId(), duvida.getDeUsuario(),
                duvida.getParaUsuario(), duvida.getPergunta(), duvida.getResposta());
        try {
            duvidaCall.execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(aBoolean){
            progressDialog.dismiss();
            Toast.makeText(context, "Duvida respondida", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MenuActivity.class);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Ocorreu um erro ao enviar os dados", Toast.LENGTH_SHORT).show();
        }
    }
}
