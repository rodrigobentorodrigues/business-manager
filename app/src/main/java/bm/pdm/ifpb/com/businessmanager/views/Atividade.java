package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;

public class Atividade extends AppCompatActivity {

    private TextView deUsuario, paraUsuario, titulo, descricao, data;
    private Button botaoVoltar;
    private Tarefa tarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        Intent intent = getIntent();
        tarefa = (Tarefa) intent.getSerializableExtra("atividade");

        this.deUsuario = findViewById(R.id.atividadeDe);
        this.paraUsuario = findViewById(R.id.atividadePara);
        this.titulo = findViewById(R.id.atividadeTitulo);
        this.descricao = findViewById(R.id.atividadeDesc);
        this.data = findViewById(R.id.atividadeData);
        this.botaoVoltar = findViewById(R.id.atividadeVoltar);

        deUsuario.setText(tarefa.getDeUsuario());
        paraUsuario.setText(tarefa.getParaUsuario());
        titulo.setText(tarefa.getTitulo());
        descricao.setText(tarefa.getDescricao());
        data.setText(tarefa.getData());

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
