package bm.pdm.ifpb.com.businessmanager.views;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.time.LocalDate;
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
        List<Tarefa> tarefas = Arrays.
                asList(new Tarefa(0, "Rodrigo", "Rennan",
                                "Tarefa 1", "Tarefa Descrição",
                                "31/05/1997", false),
                        new Tarefa(0, "Rennan", "Rodrigo",
                                "Tarefa 1", "Tarefa Descrição",
                                "15/05/2015", false));
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
