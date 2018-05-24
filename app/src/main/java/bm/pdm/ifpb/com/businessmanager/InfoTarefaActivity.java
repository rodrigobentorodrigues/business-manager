package bm.pdm.ifpb.com.businessmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;

public class InfoTarefaActivity extends AppCompatActivity {

    private Button botaoConcluida;
    private TextView usuario, titulo, descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Tarefa tarefa = (Tarefa) intent.getSerializableExtra("tarefa");
        setContentView(R.layout.activity_info_tarefa);
        this.usuario = findViewById(R.id.cadPor);
        usuario.setText(tarefa.getDeUsuario());
        this.titulo = findViewById(R.id.tituloAtividade);
        titulo.setText(tarefa.getTitulo());
        this.descricao = findViewById(R.id.descricaoAtiv);
        descricao.setText(tarefa.getDescricao());
        this.botaoConcluida = findViewById(R.id.botaoConcluida);
        botaoConcluida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar logica
                finish();
            }
        });
    }
}
