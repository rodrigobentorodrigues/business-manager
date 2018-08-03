package bm.pdm.ifpb.com.businessmanager.infra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.views.MenuActivity;

public class AutenticarUsuario extends AsyncTask<String, Void, String> {

    private Context contexto;
    private String tipo;
    private ProgressDialog dialog;
    private DadosUsuario dadosUsuario;
    private ConversorDados conversorDados;

    public AutenticarUsuario(Context contexto, String tipo) {
        this.contexto = contexto;
        this.tipo = tipo;
        this.dadosUsuario = new DadosUsuario(contexto.getSharedPreferences("usuario",
                Context.MODE_PRIVATE));
        this.conversorDados = new ConversorDados();
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
            Usuario usuario = conversorDados.getUsuario(retorno);
            Log.d("JSON", retorno.toString());
            int id = usuario.getId();
            dialog.dismiss();
            if(id != 0){
                String cargo = usuario.getCargo();
                if(cargo.equals(tipo) || tipo.equals("Funcionário")){
                    dadosUsuario.alterarValores(usuario);
                    Intent intent = new Intent(contexto, MenuActivity.class);
                    contexto.startActivity(intent);
                    // preencherUsuario(retorno);
                } else {
                    Toast.makeText(contexto, "O usuário não é desse tipo de cargo",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(contexto, "Usuário não cadastrado na base de dados",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    private void preencherUsuario(JSONObject retorno){
//        JSONObject empresa = null;
//        try {
//            empresa = retorno.getJSONObject("empresa");
//            Usuario usuario = new Usuario();
//            usuario.setId(retorno.getInt("id"));
//            usuario.setNome(retorno.getString("nome"));
//            usuario.setCargo(retorno.getString("funcao"));
//            usuario.setLogin(retorno.getString("login"));
//            usuario.setSenha(retorno.getString("senha"));
//            usuario.setTelefone(retorno.getString("telefone"));
//            usuario.setIdEmpresa(empresa.getInt("id"));
//            dadosUsuario.alterarValores(usuario);
//            Intent intent = new Intent(contexto, MenuActivity.class);
//            contexto.startActivity(intent);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

}
