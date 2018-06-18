package bm.pdm.ifpb.com.businessmanager.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.services.AdicionarDuvida;

public class CadastroDuvida extends AppCompatActivity {

    private EditText usuarioPara, pergunta;
    private TextView usuarioDe;
    private Button enviar;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_duvida);
        IntentFilter broadDuvida = new IntentFilter("cad-duv");
        registerReceiver(new CadastroDuvidaBroad(), broadDuvida);

        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.usuario = dadosUsuario.autenticado();

        this.usuarioDe = findViewById(R.id.usuarioDuvida);
        usuarioDe.setText(usuario.getNome());
        usuarioDe.setEnabled(true);

        this.usuarioPara = findViewById(R.id.destDuvida);
        this.pergunta = findViewById(R.id.campoPerg);
        this.enviar = findViewById(R.id.botaoEnv);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String para = usuarioPara.getText().toString();
                String perg = pergunta.getText().toString();
                if(para.isEmpty() || perg.isEmpty()){
                    Toast.makeText(CadastroDuvida.this, "Informe todos os dados",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Duvida duvida = new Duvida(usuario.getNome(),
                            usuarioPara.getText().toString(), pergunta.getText().toString());
                    Intent intent = new Intent(CadastroDuvida.this, AdicionarDuvida.class);
                    intent.putExtra("url", "https://business-manager-server.herokuapp.com/");
                    intent.putExtra("duvida", duvida);
                    startService(intent);
                    Toast.makeText(CadastroDuvida.this, "Dados enviados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class CadastroDuvidaBroad extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(CadastroDuvida.this, "Cadastrado", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

}
