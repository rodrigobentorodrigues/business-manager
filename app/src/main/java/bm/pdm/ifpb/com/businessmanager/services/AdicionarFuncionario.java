package bm.pdm.ifpb.com.businessmanager.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.interfaces.RestUsuario;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdicionarFuncionario extends IntentService {

    public AdicionarFuncionario(){
        super("AdicionarFuncionario");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("Usuario", "Serviço em execução");
        Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
        String url = intent.getStringExtra("url");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).
                addConverterFactory(GsonConverterFactory.create()).build();
        RestUsuario daoUsuario = retrofit.create(RestUsuario.class);
        Call<Usuario> usuarioCall = daoUsuario.adicionarFuncionario(usuario.getId(), usuario.getNome(), usuario.getCargo(), usuario.getTelefone(),
                usuario.getLogin(), usuario.getSenha(), usuario.getIdEmpresa());
        try {
            usuarioCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
