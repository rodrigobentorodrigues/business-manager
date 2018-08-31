package bm.pdm.ifpb.com.businessmanager.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.sqlite.DuvidaDao;
import bm.pdm.ifpb.com.businessmanager.sqlite.TarefaDao;
import bm.pdm.ifpb.com.businessmanager.sqlite.UsuarioDao;

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
        List<Usuario> usuarios = usuarioDao.todasNaoEnviadas(usuario);
        List<Tarefa> tarefas = tarefaDao.todasNaoEnviadas(usuario);
        List<Duvida> duvidas = duvidaDao.todasNaoEnviadas(usuario);
        for(Usuario aux: usuarios){
            Log.i("Usuario não enviado", aux.toString());
        }
        for(Tarefa aux: tarefas){
            Log.i("Tarefa não enviada", aux.toString());
        }
        for(Duvida aux: duvidas){
            Log.i("Duvida não enviada", aux.toString());
        }
    }

}
