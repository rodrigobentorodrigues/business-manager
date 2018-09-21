package bm.pdm.ifpb.com.businessmanager.infra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.interfaces.RestUsuario;
import bm.pdm.ifpb.com.businessmanager.views.MainActivity;
import bm.pdm.ifpb.com.businessmanager.views.MenuActivity;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdicionarAdministrador extends AsyncTask<String, Void, Boolean>{

    private Usuario usuario;
    private Context context;
    private String nomeEmpresa;
    private ProgressDialog dialog;
    private DadosUsuario dadosUsuario;

    public AdicionarAdministrador(Usuario usuario, Context context, String empresa) {
        this.usuario = usuario;
        this.context = context;
        this.nomeEmpresa = empresa;
        this.dadosUsuario = new DadosUsuario(context.getSharedPreferences("usuario",
                context.MODE_PRIVATE));
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde...",
                "Cadastrando informações no servidor");
        dialog.setCancelable(false);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Log.d("Usuario", usuario.toString());
        Log.d("Empresa", nomeEmpresa);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(strings[0]).
                addConverterFactory(GsonConverterFactory.create()).build();
        RestUsuario daoUsuario = retrofit.create(RestUsuario.class);
        Call<Usuario> usuarioCall = daoUsuario.adicionarAdministrador(usuario.getId(),
                usuario.getNome(), usuario.getCargo(), usuario.getTelefone(),
                usuario.getLogin(), usuario.getSenha(), nomeEmpresa);
        try {
            usuarioCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        dialog.dismiss();
        ContatosUtil util = new ContatosUtil(context.getContentResolver());
        boolean condAgenda = util.verificarNumeroAgenda(usuario.getTelefone());
        dadosUsuario.alterarValores(usuario);
        if(!condAgenda){
            // Adicionando a agenda de contatos
            Intent inten = new Intent(ContactsContract.Intents.Insert.ACTION);
            inten.setType(ContactsContract.Contacts.CONTENT_TYPE);
            inten.putExtra(ContactsContract.Intents.Insert.NAME, usuario.getNome());
            inten.putExtra(ContactsContract.Intents.Insert.PHONE, usuario.getTelefone());
            inten.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
            context.startActivity(inten);
        } else {
            Toast.makeText(context, "Contato ja existente em sua agenda", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
        Toast.makeText(context, "Administrador cadastrado com exito", Toast.LENGTH_LONG).show();
    }

}
