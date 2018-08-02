package bm.pdm.ifpb.com.businessmanager.infra;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.sqlite.DuvidaDao;
import bm.pdm.ifpb.com.businessmanager.sqlite.TarefaDao;

public class SincronizarDadosAdicionais extends AsyncTask<String, Void, String>{

    private Context context;
    private ProgressDialog progressDialog;
    private DadosUsuario dadosUsuario;
    private TarefaDao tarefaDao;
    private DuvidaDao duvidaDao;

    public SincronizarDadosAdicionais(Context context) {
        this.context = context;
        progressDialog = ProgressDialog.show(context, "Aguarde", "Cadastrando dados...");
        dadosUsuario = new DadosUsuario(context.getSharedPreferences("usuario", Context.MODE_PRIVATE));
        tarefaDao = new TarefaDao(context);
        duvidaDao = new DuvidaDao(context);
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
            //
            URL url2 = new URL(strings[1]);
            HttpURLConnection conexao2 = (HttpURLConnection) url2.openConnection();
            conexao2.connect();
            int codigoResposta2 = -1;
            do {
                if(conexao2 != null){
                    codigoResposta2 = conexao2.getResponseCode();
                }
            } while (codigoResposta == -1);
            StringBuilder conteudo2 = new StringBuilder();
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha2 = "";
            while((linha2 = bufferedReader2.readLine()) != null){
                conteudo.append(linha2);
            }
            bufferedReader2.close();
            conexao2.disconnect();
            return conteudo.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
