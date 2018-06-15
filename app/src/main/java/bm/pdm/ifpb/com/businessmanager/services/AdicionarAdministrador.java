package bm.pdm.ifpb.com.businessmanager.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.io.IOException;

import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.interfaces.RestUsuario;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdicionarAdministrador extends IntentService{

    public AdicionarAdministrador(String name) {
        super(name);
    }

    public AdicionarAdministrador() {
        super("AdicionarAdministrador");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String url = intent.getStringExtra("url");
        String nomeEmpresa = intent.getStringExtra("nomeEmpresa");
        Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).
                addConverterFactory(GsonConverterFactory.create()).build();
        RestUsuario daoUsuario = retrofit.create(RestUsuario.class);
        Call<Usuario> usuarioCall = daoUsuario.adicionarAdministrador(usuario.getId(), usuario.getNome(), usuario.getCargo(), usuario.getTelefone(),
                usuario.getLogin(), usuario.getSenha(), nomeEmpresa);
        try {
            usuarioCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent sendBroad = new Intent();
        sendBroad.setAction("cad-adm");
        sendBroadcast(sendBroad);
    }

}
