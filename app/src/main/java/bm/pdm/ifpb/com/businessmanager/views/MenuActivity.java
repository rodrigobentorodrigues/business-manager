package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import bm.pdm.ifpb.com.businessmanager.R;

public class MenuActivity extends AppCompatActivity {

    private ImageButton atividade, contatos, cadastro, duvida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //
        this.atividade = findViewById(R.id.botaoAtividade);
        this.contatos = findViewById(R.id.botaoContatos);
        this.cadastro = findViewById(R.id.botaoCadastro);
        this.duvida = findViewById(R.id.botaoDuvida);
        //
        atividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        contatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
        duvida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
