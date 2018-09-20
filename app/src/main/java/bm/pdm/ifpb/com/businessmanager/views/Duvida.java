package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bm.pdm.ifpb.com.businessmanager.R;

public class Duvida extends AppCompatActivity {

    private TextView deUsuario, paraUsuario, pergunta, resposta;
    private Button voltar;
    private bm.pdm.ifpb.com.businessmanager.domains.Duvida duvida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvida2);

        Intent intent = getIntent();
        duvida = (bm.pdm.ifpb.com.businessmanager.domains.Duvida) intent.getSerializableExtra("duvida");

        this.deUsuario = findViewById(R.id.feitaPor);
        this.paraUsuario = findViewById(R.id.enviadoPara);
        this.pergunta = findViewById(R.id.duvidaPergunta);
        this.resposta = findViewById(R.id.duvidaResposta);
        this.voltar = findViewById(R.id.voltarDuvida);

        deUsuario.setText(duvida.getDeUsuario());
        paraUsuario.setText(duvida.getParaUsuario());
        pergunta.setText(duvida.getPergunta());
        resposta.setText(duvida.getResposta());

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
