package bm.pdm.ifpb.com.businessmanager.infra;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.sqlite.UsuarioDao;

public class SincronizarDadosUsuario extends AsyncTask<String, Void, String>{

    private Context context;
    private ProgressDialog progressDialog;
    private UsuarioDao usuarioDao;
    private ConversorDados conversorDados;

    public SincronizarDadosUsuario(Context context) {
        this.context = context;
        this.progressDialog = ProgressDialog.show(context, "Aguarde...", "Buscando dados do servidor");
        this.usuarioDao = new UsuarioDao(context);
        this.conversorDados = new ConversorDados();
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setCancelable(false);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            int respCode = 0;
            do {
                respCode = httpURLConnection.getResponseCode();
            } while (respCode == -1);
            StringBuilder s = new StringBuilder();
            String palavra;
            BufferedReader buffer = new BufferedReader(new
                    InputStreamReader(httpURLConnection.getInputStream()));
            while((palavra = buffer.readLine()) != null){
                s.append(palavra);
            }
            buffer.close();
            httpURLConnection.disconnect();
            return s.toString();
        } catch (IOException e) {
            Toast.makeText(context, "Ocorreu um erro, tente novamente!",
                    Toast.LENGTH_SHORT).show();
            Log.d("Erro", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONArray valores = new JSONArray(s);
            List<Usuario> usuarios = usuarioDao.todosUsuarios();
            for(int i = 0; i < valores.length(); i++){
                JSONObject usuarioJSON = valores.getJSONObject(i);
                Usuario usuario = conversorDados.getUsuario(usuarioJSON);
                boolean exist = false;
                for(Usuario aux: usuarios){
                    if(aux.getLogin().equals(usuario.getLogin()) &&
                            aux.getSenha().equals(usuario.getSenha())){
                        exist = true;
                    }
                }
                if(!exist){
                    usuario.setEnviado(1);
                    usuarioDao.inserirUsuario(usuario);
                }
            }
        } catch (JSONException e) {
            Log.d("Erro JSON", e.getMessage());
        }
        progressDialog.dismiss();
    }
}
