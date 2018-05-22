package bm.pdm.ifpb.com.businessmanager.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import bm.pdm.ifpb.com.businessmanager.R;

public class CadastroFuncionario extends AppCompatActivity {

    private EditText nome, cargo, login, senha;
    private Button cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);
        //
        this.nome = findViewById(R.id.nomeFunc);
        this.cargo = findViewById(R.id.cargoFunc);
        this.login = findViewById(R.id.loginFunc);
        this.senha = findViewById(R.id.senhaFunc);
        this.cadastro = findViewById(R.id.cadFunc);
        //
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeFunc = nome.getText().toString();
                String cargoFunc = cargo.getText().toString();
                String loginFunc = login.getText().toString();
                String senhaFunc = senha.getText().toString();
                if(nomeFunc.equals("") || cargoFunc.equals("") ||
                        loginFunc.equals("") || senhaFunc.equals("")){
                    Toast.makeText(CadastroFuncionario.this, "Informe todos os campos",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Valores", nomeFunc + cargoFunc + loginFunc + senhaFunc);
                }
            }
        });
    }

}
