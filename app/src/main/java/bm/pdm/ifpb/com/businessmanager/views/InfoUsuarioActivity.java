package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;

public class InfoUsuarioActivity extends AppCompatActivity {

    private TextView nome, telefone;
    private Button ligar, adicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_usuario);
        Intent intent = getIntent();
        Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
        this.nome = findViewById(R.id.nomeUsu);
        nome.setText(usuario.getNome());
        this.telefone = findViewById(R.id.numUsu);
        telefone.setText(usuario.getTelefone());
        this.ligar = findViewById(R.id.botaoLigar);
        this.adicionar = findViewById(R.id.botaoAgenda);
        ligar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
