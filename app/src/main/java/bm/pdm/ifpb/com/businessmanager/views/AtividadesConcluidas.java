package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.ListarTarefas;
import bm.pdm.ifpb.com.businessmanager.infra.NetworkUtils;
import bm.pdm.ifpb.com.businessmanager.infra.TarefaAdapter;
import bm.pdm.ifpb.com.businessmanager.sqlite.TarefaDao;

public class AtividadesConcluidas extends AppCompatActivity {

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
        setContentView(R.layout.activity_atividades_concluidas);

        this.networkUtils = new NetworkUtils();
        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.usuario = dadosUsuario.autenticado();
        this.config = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        this.repositorio = config.getRepositorio();
        this.listView = findViewById(android.R.id.list);
        if(repositorio.equals("remoto")){
            if(networkUtils.verificarConexao(AtividadesConcluidas.this)){
                ListarTarefas listar = new ListarTarefas(AtividadesConcluidas.this, listView);
                listar.execute("https://business-manager-server.herokuapp.com/tarefa/concluidas?usuario="+usuario.getNome());
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
            listView.setAdapter(new TarefaAdapter(tarefas, this));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarefa tarefa = (Tarefa) parent.getItemAtPosition(position);
                if(tarefa.getParaUsuario().equals(usuario.getNome())){
                    Intent intent = new Intent(AtividadesConcluidas.this,
                            InfoTarefaActivity.class);
                    intent.putExtra("tarefa", tarefa);
                    startActivity(intent);
                } else {
                    Toast.makeText(AtividadesConcluidas.this,
                            "Para visualizar esta atividade você precisa ser o destinatário",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
