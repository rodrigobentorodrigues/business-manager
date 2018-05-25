package bm.pdm.ifpb.com.businessmanager.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bm.pdm.ifpb.com.businessmanager.R;

public class CadastroDuvida extends AppCompatActivity {

    private EditText usuarioPara, pergunta;
    private Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_duvida);
        this.usuarioPara = findViewById(R.id.destDuvida);
        this.pergunta = findViewById(R.id.campoPerg);
        this.enviar = findViewById(R.id.botaoEnv);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
