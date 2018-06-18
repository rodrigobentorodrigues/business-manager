package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.infra.ListarTarefas;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;

public class TarefaActivity extends AppCompatActivity {

    private ListView listView;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);
        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.usuario = dadosUsuario.autenticado();
        Log.i("Tarefa", dadosUsuario.toString());
        this.listView = findViewById(android.R.id.list);
        ListarTarefas listar = new ListarTarefas(TarefaActivity.this, listView);
        listar.execute("https://business-manager-server.herokuapp.com/tarefa/naoConcluidas?usuario="+usuario.getNome());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarefa tarefa = (Tarefa) parent.getItemAtPosition(position);
                if(tarefa.getParaUsuario().equals(usuario.getNome())){
                    Intent intent = new Intent(TarefaActivity.this, InfoTarefaActivity.class);
                    intent.putExtra("tarefa", tarefa);
                    startActivity(intent);
                } else {
                    Toast.makeText(TarefaActivity.this, "Para visualizar esta atividade você precisa ser o destinatário",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
