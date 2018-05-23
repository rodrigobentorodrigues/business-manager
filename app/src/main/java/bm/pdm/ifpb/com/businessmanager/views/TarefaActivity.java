package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

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
                "Construir todas as telas até a data prevista", null),
                new Tarefa("Chicão", "Rudan", "Cadastro em disciplinas",
                        "Realizar o cadastro no sistemas do SUAP", null),
                new Tarefa("Job", "Juan", "Peer Instruction",
                "Desenvolver Peer para a data prevista, assunto: (JPA)", null));
        //
        this.listView = findViewById(android.R.id.list);
        listView.setAdapter(new TarefaAdapter(tarefas, TarefaActivity.this));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater findMenuItems = getMenuInflater();
//        findMenuItems.inflate(R.menu.atividade, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.addAtiv:
//                Intent intent = new Intent(TarefaActivity.this,
//                        CadastroAtividade.class);
//                startActivity(intent);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
