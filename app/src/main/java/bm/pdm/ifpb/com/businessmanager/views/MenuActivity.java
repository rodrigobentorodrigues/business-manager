package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import bm.pdm.ifpb.com.businessmanager.R;

public class MenuActivity extends AppCompatActivity {

    private ImageButton atividade, contatos, cadastro, duvida;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //
        Intent intent = getIntent();
        this.tipo = intent.getStringExtra("tipo");
        //
        this.atividade = findViewById(R.id.botaoAtividade);
        this.contatos = findViewById(R.id.botaoContatos);
        this.cadastro = findViewById(R.id.botaoCadastro);
        this.duvida = findViewById(R.id.botaoDuvida);
        //
        atividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,
                        CadastroAtividade.class);
                intent.putExtra("tipo", tipo);
                startActivity(intent);
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
                //
                if(tipo.equals("Funcionário")){
                    AlertDialog alert = construirAlerta("Acesso negado",
                            "Você não tem acesso para essa parte do aplicativo!");
                    alert.show();
                } else {
                    Intent intent = new Intent(MenuActivity.this,
                            CadastroFuncionario.class);
                    startActivity(intent);
                }
                //
            }
        });
        duvida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, DuvidaActivity.class);
                startActivity(intent);
            }
        });
    }

    private AlertDialog construirAlerta(String titulo, String mensagem){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(titulo);
        b.setMessage(mensagem);
        b.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alerta = b.create();
        return alerta;
    }

}
