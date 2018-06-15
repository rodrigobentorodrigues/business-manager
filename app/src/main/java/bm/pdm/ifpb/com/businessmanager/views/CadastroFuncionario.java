package bm.pdm.ifpb.com.businessmanager.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.services.AdicionarFuncionario;

public class CadastroFuncionario extends AppCompatActivity {

    private EditText nome, cargo, login, senha, tel;
    private Button cadastro;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);
        //
        this.nome = findViewById(R.id.nomeFunc);
        this.cargo = findViewById(R.id.cargoFunc);
        this.login = findViewById(R.id.loginFunc);
        this.senha = findViewById(R.id.senhaFunc);
        this.tel = findViewById(R.id.telefoneCampo);
        this.cadastro = findViewById(R.id.cadFunc);
        //
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeFunc = nome.getText().toString();
                String cargoFunc = cargo.getText().toString();
                String loginFunc = login.getText().toString();
                String senhaFunc = senha.getText().toString();
                String telFunc = tel.getText().toString();
                if(nomeFunc.equals("") || cargoFunc.equals("") ||
                        loginFunc.equals("")  || senhaFunc.equals("") || telFunc.equals("")){
                    Toast.makeText(CadastroFuncionario.this, "Informe todos os campos",
                            Toast.LENGTH_SHORT).show();
                } else {
                    usuario = new Usuario(0, nomeFunc, cargoFunc, loginFunc, senhaFunc, telFunc);
                    Intent intent = new Intent(CadastroFuncionario.this, AdicionarFuncionario.class);
                    intent.putExtra("url", "https://business-manager-server.herokuapp.com/");
                    intent.putExtra("idEmpresa", 1);
                    intent.putExtra("usuario", usuario);
                    startService(intent);
                    Toast.makeText(CadastroFuncionario.this, "Dados enviados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("cad-func");
        registerReceiver(new CadastroFuncBroadCast(), filter);
        Log.i("Receiver", "Registrado");
    }

    private class CadastroFuncBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(CadastroFuncionario.this, "Cadastrado", Toast.LENGTH_SHORT).show();
        }

    }

}
