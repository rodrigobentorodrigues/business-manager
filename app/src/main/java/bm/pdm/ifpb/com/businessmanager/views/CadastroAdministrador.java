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
import bm.pdm.ifpb.com.businessmanager.services.AdicionarAdministrador;

public class CadastroAdministrador extends AppCompatActivity {

    private EditText nome, login, senha, tel, empresa;
    private Button cadastro;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_administrador);
        this.nome = findViewById(R.id.campoFuncionario);
        this.login = findViewById(R.id.campoLogin);
        this.senha = findViewById(R.id.campoSenha);
        this.tel = findViewById(R.id.campoTel);
        this.empresa = findViewById(R.id.campoEmpresa);
        this.cadastro = findViewById(R.id.botaoCadastro);

        //
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeFunc = nome.getText().toString();
                String empresaFunc = empresa.getText().toString();
                String loginFunc = login.getText().toString();
                String senhaFunc = senha.getText().toString();
                String telFunc = tel.getText().toString();
                if(nomeFunc.equals("") || loginFunc.equals("")
                        || senhaFunc.equals("") || telFunc.equals("")){
                    Toast.makeText(CadastroAdministrador.this, "Informe todos os campos",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //
                    usuario = new Usuario(0, nomeFunc, "Administrador", loginFunc,
                            senhaFunc, telFunc);
                    Intent intent = new Intent(CadastroAdministrador.this,
                            AdicionarAdministrador.class);
                    intent.putExtra("url",
                            "https://business-manager-server.herokuapp.com/");
                    intent.putExtra("nomeEmpresa", empresaFunc);
                    intent.putExtra("usuario", usuario);
                    startService(intent);
                    Toast.makeText(CadastroAdministrador.this,
                            "Dados enviados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("cad-adm");
        registerReceiver(new CadastroAdmBroadCast(), filter);
        Log.i("Receiver", "Registrado");
    }

    private class CadastroAdmBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(CadastroAdministrador.this, "Cadastrado", Toast.LENGTH_SHORT).show();
        }

    }

}
