package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.AdicionarFuncionario;
import bm.pdm.ifpb.com.businessmanager.infra.NetworkUtils;
import bm.pdm.ifpb.com.businessmanager.sqlite.UsuarioDao;

public class CadastroFuncionario2 extends AppCompatActivity {

    private Usuario usuario;
    private EditText login, senha;
    private Button voltar, cadastrar;
    private Configuracao configuracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario2);
        this.configuracao = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        Intent intent = getIntent();
        this.usuario = (Usuario) intent.getSerializableExtra("usuario");
        Log.d("Usuario", ": " + usuario.toString());
        this.login = findViewById(R.id.campoLoginCad);
        this.senha = findViewById(R.id.campoSenhaCad);
        this.voltar = findViewById(R.id.voltarCad);
        this.cadastrar = findViewById(R.id.cadastrarFuncionario);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorLogin = login.getText().toString();
                String valorSenha = senha.getText().toString();
                if(valorLogin.equals("") || valorSenha.equals("")){
                    String titulo = "Campos em branco";
                    String msg = "Por favor, informe todos os campos";
                    AlertDialog alerta = construirAlerta(titulo, msg);
                    alerta.show();
                } else {
                    String repositorio = configuracao.getRepositorio();
                    if(repositorio.equals("remoto")){
                        NetworkUtils networkInfo = new NetworkUtils();
                        if(networkInfo.verificarConexao(CadastroFuncionario2.this)){
                            usuario.setLogin(valorLogin);
                            usuario.setSenha(valorSenha);
                            AdicionarFuncionario addFunc = new bm.pdm.ifpb.com.businessmanager.infra.
                                    AdicionarFuncionario(usuario, CadastroFuncionario2.this);
                            addFunc.execute("https://business-manager-server.herokuapp.com/");
                        } else {
                            String titulo = "Sem conexão com a internet";
                            String msg = "Por favor, conecte-se com alguma rede e tente novamente";
                            AlertDialog alerta = construirAlerta(titulo, msg);
                            alerta.show();
                        }
                    } else {
                        UsuarioDao usuarioDao = new UsuarioDao(CadastroFuncionario2.this);
                        usuarioDao = new UsuarioDao(CadastroFuncionario2.this);
                        usuarioDao.inserirUsuario(usuario);
                        Intent intent = new Intent(CadastroFuncionario2.this, MenuActivity.class);
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
