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
import bm.pdm.ifpb.com.businessmanager.infra.AdicionarFuncionario;
import bm.pdm.ifpb.com.businessmanager.infra.DadosUsuario;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroFuncionario extends AppCompatActivity {

    private EditText nome, cargo, login, senha, tel;
    private Button cadastro;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;

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
        MaskEditTextChangedListener mask = new MaskEditTextChangedListener("(###)#####-####", tel);
        tel.addTextChangedListener(mask);
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
                Log.i("TELEFONE", telFunc);
                if(nomeFunc.equals("") || cargoFunc.equals("") ||
                        loginFunc.equals("")  || senhaFunc.equals("") || telFunc.equals("")){
                    Toast.makeText(CadastroFuncionario.this, "Informe todos os campos",
                            Toast.LENGTH_SHORT).show();
                } else {
                    usuario = new Usuario(0, nomeFunc, cargoFunc, loginFunc, senhaFunc, telFunc);
                    usuario.setIdEmpresa(1);
                    AdicionarFuncionario addFunc = new bm.pdm.ifpb.com.businessmanager.infra.
                            AdicionarFuncionario(usuario, CadastroFuncionario.this);
                    addFunc.execute("https://business-manager-server.herokuapp.com/");
                    nome.setText("");
                    cargo.setText("");
                    login.setText("");
                    senha.setText("");
                    tel.setText("");
                }
            }
        });
    }

    private class CadastroFuncBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(CadastroFuncionario.this, "Cadastrado", Toast.LENGTH_SHORT).show();
        }

    }

}
