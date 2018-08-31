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
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.infra.ResponderDuvida;
import bm.pdm.ifpb.com.businessmanager.sqlite.DuvidaDao;

public class InfoDuvidaActivity extends AppCompatActivity {

    private Button enviar, responder, voltar;
    private TextView usuario, descricao;
    private EditText resposta;
    private Duvida duvida;
    private DuvidaDao duvidaDao;
    private Configuracao config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_duvida);

        this.config = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        Intent intent = getIntent();
        duvida = (Duvida) intent.getSerializableExtra("duvida");

        this.usuario = findViewById(R.id.campoUsuarioDe);
        this.descricao = findViewById(R.id.descAtividade);

        usuario.setText(duvida.getDeUsuario());
        descricao.setText(duvida.getPergunta());

        this.resposta = findViewById(R.id.campoResp);
        this.responder = findViewById(R.id.botaoResp);
        this.enviar = findViewById(R.id.botaoEnviar);
        this.voltar = findViewById(R.id.backDuv);

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
                String repo = config.getRepositorio();
                String valorResposta = resposta.getText().toString();
                duvida.setResposta(valorResposta);
                if(repo.equals("remoto")){
                    ResponderDuvida responderDuvida = new ResponderDuvida(InfoDuvidaActivity.this, duvida);
                    responderDuvida.execute("https://business-manager-server.herokuapp.com/");
                } else {
                    duvidaDao = new DuvidaDao(InfoDuvidaActivity.this);
                    duvidaDao.responderDuvida(duvida);
                    Intent intent = new Intent(InfoDuvidaActivity.this, MenuActivity.class);
                    startActivity(intent);
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
