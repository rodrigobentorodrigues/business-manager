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
import bm.pdm.ifpb.com.businessmanager.infra.NetworkUtils;
import bm.pdm.ifpb.com.businessmanager.infra.UsuarioAdapter;
import bm.pdm.ifpb.com.businessmanager.sqlite.UsuarioDao;

public class UsuarioActivity extends AppCompatActivity {

    private ListView listView;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;
    private Configuracao config;
    private String repositorio;
    private UsuarioDao usuarioDao;
    private NetworkUtils networkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.networkUtils = new NetworkUtils();
        this.usuario = dadosUsuario.autenticado();
        this.config = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        this.repositorio = config.getRepositorio();
        this.listView = findViewById(android.R.id.list);
        if (repositorio.equals("remoto")){
            if(networkUtils.verificarConexao(UsuarioActivity.this)){
                ListarUsuario listarUsuario = new ListarUsuario(UsuarioActivity.this, listView);
                listarUsuario.execute("https://business-manager-server.herokuapp.com/usuario/todosPorId?id="+usuario.getIdEmpresa());
            } else {
                String titulo = "Sem conexão com a internet";
                String msg = "Por favor, conecte-se com alguma rede e tente novamente";
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setTitle(titulo);
                b.setMessage(msg);
                b.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog alerta = b.create();
                alerta.show();
            }
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
