package bm.pdm.ifpb.com.businessmanager.infra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bm.pdm.ifpb.com.businessmanager.domains.Duvida;

public class ListarDuvida extends AsyncTask<String, Void, String>{

    private ProgressDialog progressDialog;
    private Context contexto;
    private ListView listView;
    private ConversorDados conversorDados;

    public ListarDuvida(Context contexto, ListView listView) {
        this.contexto = contexto;
        this.listView = listView;
        this.conversorDados = new ConversorDados();
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(contexto, "Aguarde...",
                "Buscando informações do servidor");
        progressDialog.setCancelable(false);
    }

    @Override
    protected String doInBackground(String... strings) {
        System.out.println(strings[0]);
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.connect();
            int codigoResposta = -1;
            do {
                if(conexao != null){
                    codigoResposta = conexao.getResponseCode();
                }
            } while (codigoResposta == -1);
            StringBuilder conteudo = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha = "";
            while((linha = bufferedReader.readLine()) != null){
                conteudo.append(linha);
            }
            bufferedReader.close();
            conexao.disconnect();
            return conteudo.toString();
        } catch (MalformedURLException e) {
            Toast.makeText(contexto, "Ocorreu um erro, tente novamente!",
                    Toast.LENGTH_SHORT).show();
            Log.d("Erro", e.getMessage());
        } catch (IOException e) {
            Toast.makeText(contexto, "Ocorreu um erro, tente novamente!",
                    Toast.LENGTH_SHORT).show();
            Log.d("Erro", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        List<Duvida> duvidas = new ArrayList<>();
        try {
            JSONArray arrayResult = new JSONArray(s);
            for(int i = 0; i < arrayResult.length(); i++){
                JSONObject object = arrayResult.getJSONObject(i);
                Duvida duvida = conversorDados.getDuvida(object);
                duvidas.add(duvida);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressDialog.dismiss();
        listView.setAdapter(new DuvidaAdapter(duvidas, contexto));
        AlertDialog.Builder b = new AlertDialog.Builder(contexto);
        b.setTitle("Info");
        b.setMessage("Nesta tela é possível visualizar todas as dúvidas cadastradas pelos funcionários e " +
                "administradores da empresa. Sendo possível tambem cadastrar uma duvida.\n\n" +
                " - Para visualizar os detalhes sobre, é necessário selecionar ou tocar a duvida desejada!\n" +
                " - Para cadastrar uma duvida para algum funcionário, vá no botão cadastrar que está no canto superior direito!");
        b.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alerta = b.create();
        alerta.show();
    }

}
