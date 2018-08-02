package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;

public class MenuActivity extends AppCompatActivity {

    private ImageButton atividade, contatos, cadastro, duvida;
    private final String TIPO_FUNC = "Administrador";
    private DadosUsuario dadosUsuario;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //
        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        //
        this.atividade = findViewById(R.id.botaoAtividade);
        this.contatos = findViewById(R.id.botaoContatos);
        this.cadastro = findViewById(R.id.botaoCadastro);
        this.duvida = findViewById(R.id.botaoDuvida);
        //
        usuario = dadosUsuario.autenticado();
        atividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usuario.getCargo().equals(TIPO_FUNC)){
                    Intent intent = new Intent(MenuActivity.this,
                            TarefaActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MenuActivity.this,
                            CadastroAtividade.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }
            }
        });
        contatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, UsuarioActivity.class);
                startActivity(intent);
            }
        });
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usuario.getCargo().equals(TIPO_FUNC)){
                    AlertDialog alert = construirAlerta("Acesso negado",
                            "Você não tem acesso para essa parte do aplicativo!");
                    alert.show();
                } else {
                    Intent intent = new Intent(MenuActivity.this,
                            CadastroFuncionario.class);
                    startActivity(intent);
                }
            }
        });
        duvida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, DuvidaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.opcoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.dados:
                Toast.makeText(this, "Selecionou Dados", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buscaServidor:
                Toast.makeText(this, "Selecionou Servidor", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
