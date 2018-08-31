package bm.pdm.ifpb.com.businessmanager.infra;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import bm.pdm.ifpb.com.businessmanager.sqlite.DuvidaDao;
import bm.pdm.ifpb.com.businessmanager.sqlite.TarefaDao;

public class SincronizarDadosAdicionais extends AsyncTask<String, Void, String>{

    private Context context;
    private ProgressDialog progressDialog;
    private DadosUsuario dadosUsuario;
    private TarefaDao tarefaDao;
    private DuvidaDao duvidaDao;
    private ConversorDados conversorDados;

    public SincronizarDadosAdicionais(Context context) {
        this.context = context;
        progressDialog = ProgressDialog.show(context, "Aguarde", "Buscando dados...");
        dadosUsuario = new DadosUsuario(context.getSharedPreferences("usuario", Context.MODE_PRIVATE));
        tarefaDao = new TarefaDao(context);
        duvidaDao = new DuvidaDao(context);
        conversorDados = new ConversorDados();
    }

    @Override
    protected void onPreExecute() {
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
            try {
                List<Duvida> duvidas = duvidaDao.todasDuvidas();
                JSONArray array = new JSONArray(conteudo.toString());
                duvidaDao.removerDados();
                for(int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    Duvida duvida = conversorDados.getDuvida(object);
                    boolean exist = false;
                    for (Duvida aux: duvidas){
                        if(aux.getDeUsuario().equals(duvida.getDeUsuario()) &&
                                aux.getParaUsuario().equals(duvida.getParaUsuario()) &&
                                aux.getPergunta().equals(duvida.getPergunta()) &&
                                aux.getResposta().equals(duvida.getResposta())){
                            exist = true;
                        }
                    }
                    if(!exist){
                        Log.d("Inexistente Duvida", duvida.toString());
                        duvida.setEnviado(1);
                        duvidaDao.inserirDuvida(duvida);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //
            URL url2 = new URL(strings[1]);
            HttpURLConnection conexao2 = (HttpURLConnection) url2.openConnection();
            conexao2.connect();
            int codigoResposta2 = -1;
            do {
                if(conexao2 != null){
                    codigoResposta2 = conexao2.getResponseCode();
                }
            } while (codigoResposta2 == -1);
            StringBuilder conteudo2 = new StringBuilder();
            BufferedReader bufferedReader2 = new BufferedReader(new
                    InputStreamReader(conexao2.getInputStream()));
            String linha2 = "";
            while((linha2 = bufferedReader2.readLine()) != null){
                conteudo2.append(linha2);
            }
            bufferedReader2.close();
            conexao2.disconnect();
            try {
                JSONArray array = new JSONArray(conteudo2.toString());
                tarefaDao.removerDados();
                List<Tarefa> tarefas = tarefaDao.todasTarefas();
                for(int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    Tarefa tarefa = conversorDados.getTarefa(object);
                    boolean exist = false;
                    for(Tarefa aux: tarefas){
                        if(aux.getDeUsuario().equals(tarefa.getDeUsuario()) &&
                                aux.getParaUsuario().equals(tarefa.getParaUsuario()) &&
                                aux.getData().equals(tarefa.getData()) &&
                                aux.getTitulo().equals(tarefa.getTitulo())){
                            exist = true;
                        }
                    }
                    if(!exist){
                        Log.d("Inexistente Tarefa", tarefa.toString());
                        tarefa.setEnviado(1);
                        tarefaDao.inserirTarefa(tarefa);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return conteudo2.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
    }
}
