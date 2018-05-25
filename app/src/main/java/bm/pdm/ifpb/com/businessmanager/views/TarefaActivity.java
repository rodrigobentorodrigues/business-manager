package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.infra.TarefaAdapter;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;

public class TarefaActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);
        List<Tarefa> tarefas = Arrays.asList(new Tarefa("Ari", "Rodrigo", "Construir telas",
                "Construir todas as telas até a data prevista", "31/05/1997"),
                new Tarefa("Chicão", "Rudan", "Cadastro em disciplinas",
                        "Realizar o cadastro no sistemas do SUAP", "22/06/2019"),
                new Tarefa("Job", "Juan", "Peer Instruction",
                "Desenvolver Peer para a data prevista, assunto: (JPA)", "15/01/2005"));
        //
        this.listView = findViewById(android.R.id.list);
        listView.setAdapter(new TarefaAdapter(tarefas, TarefaActivity.this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarefa tarefa = (Tarefa) parent.getItemAtPosition(position);
                Intent intent = new Intent(TarefaActivity.this, InfoTarefaActivity.class);
                intent.putExtra("tarefa", tarefa);
                startActivity(intent);
            }
        });
    }

}
