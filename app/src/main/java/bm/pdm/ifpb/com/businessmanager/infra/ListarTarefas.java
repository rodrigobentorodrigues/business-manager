package bm.pdm.ifpb.com.businessmanager.infra;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

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

import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;

public class ListarTarefas extends AsyncTask<String, Void, String> {

    private Context contexto;
    private ListView listView;
    private ProgressDialog progressDialog;

    public ListarTarefas(Context context, ListView listView) {
        this.contexto = context;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(contexto, "Aguarde...",
                "Buscando informações do servidor");
        progressDialog.setCancelable(false);
    }

    @Override
    protected String doInBackground(String... strings) {
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            List<Tarefa> tarefas = new ArrayList<>();
            JSONArray arrayResult = new JSONArray(s);
            for(int i = 0; i < arrayResult.length(); i++){
                JSONObject object = arrayResult.getJSONObject(i);
                Tarefa tarefa = new Tarefa();
                tarefa.setId(object.getInt("id"));
                tarefa.setDeUsuario(object.getString("deUsuario"));
                tarefa.setParaUsuario(object.getString("paraUsuario"));
                tarefa.setTitulo(object.getString("titulo"));
                tarefa.setDescricao(object.getString("descricao"));
                tarefa.setData(object.getString("data"));
                tarefa.setConcluida(object.getBoolean("concluida"));
                tarefas.add(tarefa);
            }
            progressDialog.dismiss();
            listView.setAdapter(new TarefaAdapter(tarefas, contexto));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
