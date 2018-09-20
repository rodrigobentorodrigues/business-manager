package bm.pdm.ifpb.com.businessmanager.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.AdicionarFuncionario;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.sqlite.UsuarioDao;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroFuncionario extends AppCompatActivity {

    // private EditText nome, cargo, login, senha, tel;
    private EditText nome, cargo, tel;
    private Button cadastro, voltar;
    private Usuario usuario;
    private Usuario usuarioAdministrador;
    private DadosUsuario dadosUsuario;
    private Configuracao configuracao;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);
        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.configuracao = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        this.usuarioAdministrador = dadosUsuario.autenticado();
        Log.d("Administrador", usuarioAdministrador.toString());
        this.nome = findViewById(R.id.nomeFunc);
        this.cargo = findViewById(R.id.cargoFunc);
        // this.login = findViewById(R.id.loginFunc);
        // this.senha = findViewById(R.id.senhaFunc);
        this.tel = findViewById(R.id.telefoneCampo);
        MaskEditTextChangedListener mask = new MaskEditTextChangedListener("(###)#####-####", tel);
        tel.addTextChangedListener(mask);
        this.cadastro = findViewById(R.id.cadFunc);
        this.voltar = findViewById(R.id.backCadFunc);
        //
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeFunc = nome.getText().toString();
                String cargoFunc = cargo.getText().toString();
                // String loginFunc = login.getText().toString();
                // String senhaFunc = senha.getText().toString();
                String telFunc = tel.getText().toString();
                Log.i("TELEFONE", telFunc);
                if(nomeFunc.equals("") || cargoFunc.equals("") || telFunc.equals("")){
                    Toast.makeText(CadastroFuncionario.this, "Informe todos os campos",
                            Toast.LENGTH_SHORT).show();
                } else {
                    usuario = new Usuario(0, nomeFunc, cargoFunc, "", "", telFunc);
                    usuario.setIdEmpresa(usuarioAdministrador.getIdEmpresa());
                    //
                    Intent intent = new Intent(CadastroFuncionario.this, CadastroFuncionario2.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
//                    nome.setText("");
//                    cargo.setText("");
//                    tel.setText("");
                }
            }
        });
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
