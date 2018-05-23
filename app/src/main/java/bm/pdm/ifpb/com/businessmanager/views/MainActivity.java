package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import bm.pdm.ifpb.com.businessmanager.R;

public class MainActivity extends AppCompatActivity {

    private Button botaoLogin;
    private Spinner spinner;
    private EditText login, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        this.login = findViewById(R.id.inputLogin);
        this.senha = findViewById(R.id.inputSenha);
        this.botaoLogin = findViewById(R.id.botaoLogin);

        // Setando os valores para o Spinner
        this.spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorLogin = login.getText().toString();
                String valorSenha = senha.getText().toString();
                if(valorLogin.isEmpty() || valorSenha.isEmpty()){
                    AlertDialog alert = construirAlerta("Valores invalidos",
                            "Informe todos os campos na tela");
                    alert.show();
                } else {
                    if(valorLogin.equals("adminpdm") && valorSenha.equals("pdmadmin")){
                        Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        intent.putExtra("tipo", spinner.getSelectedItem().toString());
                        startActivity(intent);
                    }
                }
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
