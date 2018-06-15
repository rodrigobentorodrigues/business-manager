package bm.pdm.ifpb.com.businessmanager.infra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bm.pdm.ifpb.com.businessmanager.domains.RepoTemp;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.views.MenuActivity;

public class AutenticarUsuario extends AsyncTask<String, Void, String> {

    private Context contexto;
    private ProgressDialog dialog;

    public AutenticarUsuario(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(contexto, "Aguarde...", "Buscando informações do servidor");
        dialog.setCancelable(false);
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
            JSONObject retorno = new JSONObject(s);
            int id = retorno.getInt("id");
            dialog.dismiss();
            if(id != 0){
                Usuario usuario = new Usuario();
                usuario.setId(id);
                usuario.setNome(retorno.getString("nome"));
                String cargo = retorno.getString("funcao");
                RepoTemp temp = new RepoTemp();
                if(cargo != "Administrador"){
                    temp.setValor("Funcionário");
                } else {
                    temp.setValor(cargo);
                }
                usuario.setCargo(cargo);
                usuario.setLogin(retorno.getString("login"));
                usuario.setSenha(retorno.getString("senha"));
                usuario.setTelefone(retorno.getString("telefone"));
                Intent intent = new Intent(contexto, MenuActivity.class);
                RepoTemp.setUsuario(usuario);
                contexto.startActivity(intent);
            } else {
                Toast.makeText(contexto, "Usuário não cadastrado na base de dados", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
