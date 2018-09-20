package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.nio.file.Files;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.infra.NetworkUtils;
import bm.pdm.ifpb.com.businessmanager.infra.SincronizarDadosUsuario;

public class ConfiguracaoActivity extends AppCompatActivity {

    private Button remoto, local, voltar, sobre, sincronizar;
    private Configuracao config;
    private NetworkUtils networkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        this.config = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        this.networkUtils = new NetworkUtils();
        this.remoto = findViewById(R.id.escolhaRemoto);
        this.local = findViewById(R.id.escolhaLocal);
        this.voltar = findViewById(R.id.botaoVoltarConfig);
        this.sobre = findViewById(R.id.sobre);
        this.sincronizar = findViewById(R.id.sincDados);

        Animation animation = AnimationUtils.
                loadAnimation(ConfiguracaoActivity.this, R.anim.rotate);
        local.setAnimation(animation);
        remoto.setAnimation(animation);

        remoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setRepositorio("remoto");
                Toast.makeText(ConfiguracaoActivity.this, "Utilizando os dados do servidor remoto", Toast.LENGTH_SHORT).show();
            }
        });
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setRepositorio("local");
                String titulo = "Repositorio Local";
                String msg = "Utilizando esse tipo de repositorio você ganha \n" +
                        "mais perfomance ao requisitar as informações, porém para ter acesso a todos os dados \n" +
                        "é necessário realizar uma requisição ao servidor quando possivel. \n" +
                        " - Para realizar uma requisição ao servidor, selecione o botão abaixo;";
                AlertDialog alerta = construirAlerta(titulo, msg);
                alerta.show();
            }
        });
        sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(networkUtils.verificarConexao(ConfiguracaoActivity.this)){
                    AlertDialog.Builder b2 = new AlertDialog.Builder(ConfiguracaoActivity.this);
                    b2.setTitle("Sincronização de Dados");
                    b2.setMessage("Deseja buscar os dados de usuários do servidor remoto?");
                    b2.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    b2.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SincronizarDadosUsuario sinc = new SincronizarDadosUsuario(ConfiguracaoActivity.this);
                            sinc.execute("https://business-manager-server.herokuapp.com/usuario/listar");
                        }
                    });
                    AlertDialog alerta2 = b2.create();
                    alerta2.show();
                } else {
                    String titulo = "Sem conexão com a internet";
                    String msg = "Por favor, conecte-se com alguma rede e tente novamente";
                    AlertDialog alerta2 = construirAlerta(titulo, msg);
                    alerta2.show();
                }
            }
        });
        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(networkUtils.verificarConexao(ConfiguracaoActivity.this)){
                    Intent intent = new Intent(ConfiguracaoActivity.this, WebViewActivity.class);
                    startActivity(intent);
                } else {
                    String titulo = "Sem conexão com a internet";
                    String msg = "Por favor, conecte-se com alguma rede e tente novamente";
                    AlertDialog alerta = construirAlerta(titulo, msg);
                    alerta.show();
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
