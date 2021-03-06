package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.infra.ListarTarefas;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import bm.pdm.ifpb.com.businessmanager.infra.NetworkUtils;
import bm.pdm.ifpb.com.businessmanager.infra.TarefaAdapter;
import bm.pdm.ifpb.com.businessmanager.sqlite.TarefaDao;

public class TarefaActivity extends AppCompatActivity {

    private ListView listView;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;
    private Configuracao config;
    private String repositorio;
    private TarefaDao tarefaDao;
    private NetworkUtils networkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);

        this.networkUtils = new NetworkUtils();
        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.usuario = dadosUsuario.autenticado();
        this.config = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        this.repositorio = config.getRepositorio();
        this.listView = findViewById(android.R.id.list);
        if(repositorio.equals("remoto")){
            if(networkUtils.verificarConexao(TarefaActivity.this)){
                ListarTarefas listar = new ListarTarefas(TarefaActivity.this, listView);
                listar.execute("https://business-manager-server.herokuapp.com/tarefa/naoConcluidas?usuario="+usuario.getNome());
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
            // Buscar no SQLite
            tarefaDao = new TarefaDao(this);
            List<Tarefa> tarefas = tarefaDao.todasNaoConcluidas(usuario.getNome());
            Log.d("Tarefas", tarefas.toString());
            listView.setAdapter(new TarefaAdapter(tarefas, this));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarefa tarefa = (Tarefa) parent.getItemAtPosition(position);
                if(tarefa.getParaUsuario().equals(usuario.getNome())){
                    Intent intent = new Intent(TarefaActivity.this,
                            InfoTarefaActivity.class);
                    intent.putExtra("tarefa", tarefa);
                    startActivity(intent);
                    overridePendingTransition(R.anim.lefttoright, R.anim.lefttoright);
                } else {
                    Toast.makeText(TarefaActivity.this,
                            "Para visualizar esta atividade você precisa ser o destinatário",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater layoutInflater = getMenuInflater();
        layoutInflater.inflate(R.menu.atividade, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atividadesConcluidas:
                Intent intent2 = new Intent(TarefaActivity.this, AtividadesConcluidas.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
