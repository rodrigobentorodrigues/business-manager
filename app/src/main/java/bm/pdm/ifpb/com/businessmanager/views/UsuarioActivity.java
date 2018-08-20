package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.infra.ListarUsuario;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.UsuarioAdapter;
import bm.pdm.ifpb.com.businessmanager.sqlite.UsuarioDao;

public class UsuarioActivity extends AppCompatActivity {

    private ListView listView;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;
    private Configuracao config;
    private String repositorio;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.usuario = dadosUsuario.autenticado();
        this.config = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        this.repositorio = config.getRepositorio();
        this.listView = findViewById(android.R.id.list);
        if (repositorio.equals("remoto")){
            ListarUsuario listarUsuario = new ListarUsuario(UsuarioActivity.this, listView);
            listarUsuario.execute("https://business-manager-server.herokuapp.com/usuario/todosPorId?id="+usuario.getIdEmpresa());
        } else {
            usuarioDao = new UsuarioDao(UsuarioActivity.this);
            listView.setAdapter(new UsuarioAdapter(usuarioDao.todosUsuariosPorId(usuario.getIdEmpresa()),
                    UsuarioActivity.this));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = (Usuario) parent.getAdapter().getItem(position);
                Intent intent = new Intent(UsuarioActivity.this, InfoUsuarioActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
    }
}
