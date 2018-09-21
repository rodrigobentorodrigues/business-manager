package bm.pdm.ifpb.com.businessmanager.views;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.AutenticarUsuario;
import bm.pdm.ifpb.com.businessmanager.infra.NetworkUtils;
import bm.pdm.ifpb.com.businessmanager.infra.SincronizarDadosUsuario;
import bm.pdm.ifpb.com.businessmanager.sqlite.DuvidaDao;
import bm.pdm.ifpb.com.businessmanager.sqlite.TarefaDao;
import bm.pdm.ifpb.com.businessmanager.sqlite.UsuarioDao;

public class MainActivity extends AppCompatActivity {

    private Button botaoLogin, botaoConfig;
    private Spinner spinner;
    private EditText login, senha;
    private Configuracao config;
    private String repositorio;
    private UsuarioDao usuarioDao;
    private TarefaDao tarefaDao;
    private DuvidaDao duvidaDao;
    private DadosUsuario dadosUsuario;
    private NetworkUtils networkUtils;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.usuarioDao = new UsuarioDao(this);
//        usuarioDao.removerDados();
//        this.duvidaDao = new DuvidaDao(this);
//        this.tarefaDao = new TarefaDao(this);
//        tarefaDao.removerDados();
//        duvidaDao.removerDados();

        this.config = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        this.networkUtils = new NetworkUtils();

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 100);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 100);
        }
        //
        this.login = findViewById(R.id.inputLogin);
        this.senha = findViewById(R.id.inputSenha);
        this.spinner = findViewById(R.id.spinner2);
        this.botaoLogin = findViewById(R.id.botaoLogin);
        this.botaoConfig = findViewById(R.id.botaoConfiguracao);

//        Animation animation = AnimationUtils.
//                loadAnimation(MainActivity.this, R.anim.rotate);
//        Animation animation2 = AnimationUtils.
//                loadAnimation(MainActivity.this, R.anim.sample_anim);
//        botaoConfig.setAnimation(animation);
//        botaoLogin.setAnimation(animation);

        // Setando os valores para o Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);

//        botaoConfig.clearAnimation();
//        botaoLogin.clearAnimation();

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
                    repositorio = config.getRepositorio();
                    if(repositorio.equals("remoto")){
                        if(networkUtils.verificarConexao(MainActivity.this)){
                            if(valorLogin.equals("admin") && valorSenha.equals("admin")){
                                Intent intent = new Intent(MainActivity.this, CadastroAdministrador.class);
                                startActivity(intent);
                            } else {
                                AutenticarUsuario auth = new AutenticarUsuario(MainActivity.this,  spinner.getSelectedItem().toString());
                                auth.execute("https://business-manager-server.herokuapp.com/usuario/autenticar?login="+valorLogin+"&senha="+valorSenha);
                            }
                        } else {
                            String titulo = "Sem conexão com a internet";
                            String msg = "Por favor, conecte-se com alguma rede e tente novamente";
                            AlertDialog alerta = construirAlerta(titulo, msg);
                            alerta.show();
                        }
                    } else {
                        List<Usuario> usuarios = usuarioDao.todosUsuarios();
                        Log.d("Tamanho", ": " + usuarios.size());
                        if(usuarios.size() == 0){
                            AlertDialog.Builder b2 = new AlertDialog.Builder(MainActivity.this);
                            b2.setTitle("Seu repositório local não possui dados");
                            b2.setMessage("Deseja buscar os dados de usuários do servidor remoto?");
                            b2.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            b2.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(networkUtils.verificarConexao(MainActivity.this)){
                                        SincronizarDadosUsuario sinc = new SincronizarDadosUsuario(MainActivity.this);
                                        sinc.execute("https://business-manager-server.herokuapp.com/usuario/listar");
                                    } else {
                                        String titulo = "Sem conexão com a internet";
                                        String msg = "Por favor, conecte-se com alguma rede e tente novamente";
                                        AlertDialog alerta2 = construirAlerta(titulo, msg);
                                        alerta2.show();
                                    }
                                }
                            });
                            AlertDialog alerta3 = b2.create();
                            alerta3.show();

                        } else {
                            Usuario autenticado = usuarioDao.autenticarUsuario(valorLogin, valorSenha);
                            int id = autenticado.getId();
                            String tipo = spinner.getSelectedItem().toString();
                            if(id != 0){
                                String cargo = autenticado.getCargo();
                                if(cargo.equals(tipo) || tipo.equals("Funcionário")){
                                    dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
                                    dadosUsuario.alterarValores(autenticado);
                                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "O usuário não é desse tipo de cargo",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Usuário não cadastrado na base de dados",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        botaoConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfiguracaoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadein);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater layoutInflater = getMenuInflater();
//        layoutInflater.inflate(R.menu.configuracoes, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.config:
//                AlertDialog.Builder b = new AlertDialog.Builder(this);
//                b.setTitle("Repositório de dados");
//                b.setMessage("Informe o repositório de dados que você deseja utilizar");
//                b.setNegativeButton("Local", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        config.setRepositorio("local");
//                        AlertDialog alert = construirAlerta("Repositorio Local", "Utilizando esse tipo de repositorio você ganha " +
//                                "mais perfomance ao requisitar as informações, porém para ter acesso a todos os dados " +
//                                "é necessário realizar uma requisição ao servidor quando possivel. \n\n" +
//                                " - Para realizar uma requisição ao servidor, selecione o icone no canto superior direito;");
//                        alert.show();
//                    }
//                });
//                b.setPositiveButton("Remoto", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        config.setRepositorio("remoto");
//                        Toast.makeText(MainActivity.this, "Dados remotos", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                AlertDialog alerta = b.create();
//                alerta.show();
//                break;
//            case R.id.sinc:
//                if(networkUtils.verificarConexao(MainActivity.this)){
//                    AlertDialog.Builder b2 = new AlertDialog.Builder(this);
//                    b2.setTitle("Sincronização de Dados");
//                    b2.setMessage("Deseja buscar os dados de usuários do servidor remoto?");
//                    b2.setNegativeButton("Não", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                        }
//                    });
//                    b2.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            SincronizarDadosUsuario sinc = new SincronizarDadosUsuario(MainActivity.this);
//                            sinc.execute("https://business-manager-server.herokuapp.com/usuario/listar");
//                        }
//                    });
//                    AlertDialog alerta2 = b2.create();
//                    alerta2.show();
//                } else {
//                    String titulo = "Sem conexão com a internet";
//                    String msg = "Por favor, conecte-se com alguma rede e tente novamente";
//                    AlertDialog alerta2 = construirAlerta(titulo, msg);
//                    alerta2.show();
//                }
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
