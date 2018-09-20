package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.DuvidaAdapter;
import bm.pdm.ifpb.com.businessmanager.infra.ListarDuvida;
import bm.pdm.ifpb.com.businessmanager.infra.NetworkUtils;
import bm.pdm.ifpb.com.businessmanager.sqlite.DuvidaDao;

public class DuvidasConcluidas extends AppCompatActivity {

    private ListView listView;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;
    private Configuracao config;
    private String repositorio;
    private DuvidaDao duvidaDao;
    private NetworkUtils networkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvidas_concluidas);

        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.networkUtils = new NetworkUtils();
        this.usuario = dadosUsuario.autenticado();
        this.config = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        this.repositorio = config.getRepositorio();
        this.listView = findViewById(android.R.id.list);

        if (repositorio.equals("remoto")){
            if(networkUtils.verificarConexao(DuvidasConcluidas.this)){
                ListarDuvida listarDuvida = new ListarDuvida(DuvidasConcluidas.this, listView);
                listarDuvida.execute("https://business-manager-server.herokuapp.com/duvida/concluidas?usuario="+usuario.getNome());
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
            // SQLite
            duvidaDao = new DuvidaDao(this);
            List<Duvida> duvidas = duvidaDao.todasConcluidas(usuario.getNome());
            listView.setAdapter(new DuvidaAdapter(duvidas, this));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Duvida duvida = (Duvida) parent.getAdapter().getItem(position);
                Intent intent = new Intent(DuvidasConcluidas.this, bm.pdm.ifpb.com.businessmanager.views.Duvida.class);
                intent.putExtra("duvida", duvida);
                startActivity(intent);
                overridePendingTransition(R.anim.sample_anim, R.anim.sample_anim);
            }
        });

    }
}
