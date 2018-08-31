package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;

public class CadastroActivity extends AppCompatActivity {

    private Button administrador, funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        //
        this.administrador = findViewById(R.id.botaoAdm);
        this.funcionario = findViewById(R.id.botaoFunc);
        //
        administrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, CadastroAdministrador.class);
                startActivity(intent);
            }
        });
        funcionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, CadastroFuncionario.class);
                startActivity(intent);
            }
        });
    }

}
