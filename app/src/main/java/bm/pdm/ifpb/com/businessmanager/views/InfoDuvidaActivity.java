package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Duvida;

public class InfoDuvidaActivity extends AppCompatActivity {

    private Button enviar, responder;
    private TextView usuario, descricao;
    private EditText resposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_duvida);

        Intent intent = getIntent();
        Duvida duvida = (Duvida) intent.getSerializableExtra("duvida");

        this.usuario = findViewById(R.id.campoUsuarioDe);
        this.descricao = findViewById(R.id.descAtividade);

        usuario.setText(duvida.getDeUsuario());
        descricao.setText(duvida.getPergunta());

        this.resposta = findViewById(R.id.campoResp);
        this.responder = findViewById(R.id.botaoResp);
        this.enviar = findViewById(R.id.botaoEnviar);

        resposta.setVisibility(View.INVISIBLE);
        enviar.setVisibility(View.INVISIBLE);

        this.enviar = findViewById(R.id.botaoEnviar);
        responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resposta.setVisibility(View.VISIBLE);
                enviar.setVisibility(View.VISIBLE);
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorResposta = resposta.getText().toString();
                Toast.makeText(InfoDuvidaActivity.this, "Resposta: " +
                        valorResposta + " enviada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InfoDuvidaActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

    }
}
