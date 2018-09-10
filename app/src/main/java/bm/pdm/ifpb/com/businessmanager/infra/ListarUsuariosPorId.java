package bm.pdm.ifpb.com.businessmanager.infra;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListarUsuariosPorId extends AsyncTask<String, Void, String>{

    private Context context;
    private Spinner spinner;
    private ArrayList<String> usuarios;
    private String usuario;

    public ListarUsuariosPorId(Context contexto, Spinner spinner, String usuario){
        this.context = contexto;
        this.spinner = spinner;
        this.usuario = usuario;
        this.usuarios = new ArrayList<>();
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
            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(conexao.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String linha = "";
            while((linha = bufferedReader.readLine()) != null){
                builder.append(linha);
            }
            bufferedReader.close();
            conexao.disconnect();
            return builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONArray objeto = new JSONArray(result);
            for(int i = 0; i < objeto.length(); i++){
                if(!objeto.getString(i).equals(usuario)){
                    usuarios.add(objeto.getString(i));
                }
            }
            ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, usuarios);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
