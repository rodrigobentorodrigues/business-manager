package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import bm.pdm.ifpb.com.businessmanager.infra.ConcluirAtividade;
import bm.pdm.ifpb.com.businessmanager.infra.SincronizarDadosUsuario;
import bm.pdm.ifpb.com.businessmanager.sqlite.TarefaDao;

public class InfoTarefaActivity extends AppCompatActivity {

    private Button botaoConcluida, voltar;
    private TextView usuario, titulo, descricao, data;
    private Tarefa tarefa;
    private TarefaDao tarefaDao;
    private Configuracao config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        tarefa = (Tarefa) intent.getSerializableExtra("tarefa");
        config = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        setContentView(R.layout.activity_info_tarefa);
        this.usuario = findViewById(R.id.cadPor);
        usuario.setText(tarefa.getDeUsuario());
        this.titulo = findViewById(R.id.tituloAtividade);
        titulo.setText(tarefa.getTitulo());
        this.descricao = findViewById(R.id.descricaoAtiv);
        descricao.setText(tarefa.getDescricao());
        this.data = findViewById(R.id.dataAtividade);
        data.setText(tarefa.getData());
        this.botaoConcluida = findViewById(R.id.botaoConcluida);
        this.voltar = findViewById(R.id.backInfo);
        botaoConcluida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String repo = config.getRepositorio();
                if(repo.equals("remoto")){
                    ConcluirAtividade concluirAtividade = new ConcluirAtividade(
                            InfoTarefaActivity.this, tarefa);
                    concluirAtividade.execute("https://business-manager-server.herokuapp.com/");
                } else {
                    //
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoTarefaActivity.this);
                    builder.setTitle("Permissão negada");
                    builder.setMessage("Para concluir uma tarefa é necessário estar utilizando o repositório remoto.\n" +
                            "O repositório local só pode ser utilizado para cadastro e consulta!");
                    builder.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog alerta = builder.create();
                    alerta.show();
                    //
//                    tarefaDao = new TarefaDao(InfoTarefaActivity.this);
//                    tarefaDao.concluirTarefa(tarefa);
//                    Log.d("Tarefas", tarefaDao.todasNaoConcluidas("Rodrigo").toString());
//                    Intent intent = new Intent(InfoTarefaActivity.this,
//                            MenuActivity.class);
//                    startActivity(intent);
                }
            }
        });
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
