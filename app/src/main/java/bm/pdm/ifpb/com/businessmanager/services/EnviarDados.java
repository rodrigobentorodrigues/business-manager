package bm.pdm.ifpb.com.businessmanager.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.interfaces.RestDuvida;
import bm.pdm.ifpb.com.businessmanager.interfaces.RestTarefa;
import bm.pdm.ifpb.com.businessmanager.interfaces.RestUsuario;
import bm.pdm.ifpb.com.businessmanager.sqlite.DuvidaDao;
import bm.pdm.ifpb.com.businessmanager.sqlite.TarefaDao;
import bm.pdm.ifpb.com.businessmanager.sqlite.UsuarioDao;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnviarDados extends IntentService {

    private UsuarioDao usuarioDao;
    private TarefaDao tarefaDao;
    private DuvidaDao duvidaDao;

    public EnviarDados() {
        super("EnviarDados");
        this.usuarioDao = new UsuarioDao(this);
        this.tarefaDao = new TarefaDao(this);
        this.duvidaDao = new DuvidaDao(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String usuario = intent.getStringExtra("usuario");
        int quantidadeEnviada = 0;
        List<Usuario> usuarios = usuarioDao.todasNaoEnviadas(usuario);
        List<Tarefa> tarefas = tarefaDao.todasNaoEnviadas(usuario);
        List<Duvida> duvidas = duvidaDao.todasNaoEnviadas(usuario);
        for(Usuario aux: usuarios){
            quantidadeEnviada++;
            Log.i("Usuario não enviado", aux.toString());
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("https://business-manager-server.herokuapp.com/").
                    addConverterFactory(GsonConverterFactory.create()).build();
            RestUsuario daoUsuario = retrofit.create(RestUsuario.class);
            Call<Usuario> usuarioCall = daoUsuario.adicionarFuncionario(aux.getId(),
                    aux.getNome(), aux.getCargo(), aux.getTelefone(),
                    aux.getLogin(), aux.getSenha(), aux.getIdEmpresa());
            try {
                usuarioCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(Tarefa tarefa: tarefas){
            quantidadeEnviada++;
            Log.i("Tarefa não enviada", tarefa.toString());
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("https://business-manager-server.herokuapp.com/").
                    addConverterFactory(GsonConverterFactory.create()).build();
            RestTarefa adicionarTarefa = retrofit.create(RestTarefa.class);
            Call<Tarefa> tarefaCall = adicionarTarefa.adicionarTarefa(0,
                    tarefa.getDeUsuario(), tarefa.getParaUsuario(), tarefa.getTitulo(),
                    tarefa.getDescricao(), tarefa.getData(), false);
            try {
                tarefaCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(Duvida duvida: duvidas){
            quantidadeEnviada++;
            Log.i("Duvida não enviada", duvida.toString());
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("https://business-manager-server.herokuapp.com/").
                    addConverterFactory(GsonConverterFactory.create()).build();
            RestDuvida restDuvida = retrofit.create(RestDuvida.class);
            Call<Duvida> duvidaCall = restDuvida.adicionarDuvida(duvida.getId(), duvida.getDeUsuario(),
                    duvida.getParaUsuario(), duvida.getPergunta(), duvida.getResposta());
            try{
                duvidaCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "Foram enviados " + quantidadeEnviada + " para o servidor",
                Toast.LENGTH_SHORT).show();
    }

}
