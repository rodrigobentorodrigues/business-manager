package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import bm.pdm.ifpb.com.businessmanager.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Spinner spinner;
    private EditText login, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Setando os valores para o Spinner
        this.spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);
        this.login = findViewById(R.id.campoLogin);
        this.senha = findViewById(R.id.campoSenha);
        //
        this.btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorLogin = login.getText().toString();
                String valorSenha = senha.getText().toString();
                String valorTipo = spinner.getSelectedItem().toString();
                //
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }



}
