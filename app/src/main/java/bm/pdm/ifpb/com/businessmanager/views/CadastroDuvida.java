package bm.pdm.ifpb.com.businessmanager.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.infra.ListarUsuariosPorId;
import bm.pdm.ifpb.com.businessmanager.services.AdicionarDuvida;
import bm.pdm.ifpb.com.businessmanager.sqlite.DuvidaDao;
import bm.pdm.ifpb.com.businessmanager.sqlite.UsuarioDao;

public class CadastroDuvida extends AppCompatActivity {

    private EditText pergunta;
    private Spinner paraUsuario;
    private TextView usuarioDe;
    private Button enviar, voltar;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;
    private CadastroDuvidaBroad broadcast;
    private Configuracao configuracao;
    private String repositorio;
    private DuvidaDao duvidaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_duvida);

        this.broadcast = new CadastroDuvidaBroad();
        this.configuracao = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));

        IntentFilter broadDuvida = new IntentFilter("cad-duv");
        registerReceiver(broadcast, broadDuvida);

        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.usuario = dadosUsuario.autenticado();

        this.usuarioDe = findViewById(R.id.usuarioDuvida);
        usuarioDe.setText(usuario.getNome());
        usuarioDe.setEnabled(true);

        // this.usuarioPara = findViewById(R.id.destDuvida);
        this.paraUsuario = findViewById(R.id.spinner);
        this.pergunta = findViewById(R.id.campoPerg);
        this.enviar = findViewById(R.id.botaoEnv);
        this.voltar = findViewById(R.id.backCadDuv);

        repositorio = configuracao.getRepositorio();
        if(repositorio.equals("remoto")){
            ListarUsuariosPorId listar = new ListarUsuariosPorId(this, paraUsuario, usuario.getNome());
            listar.execute("http://business-manager-server.herokuapp.com/usuario/nomes?id="+usuario.getIdEmpresa());
        } else {
            UsuarioDao usuarioDao = new UsuarioDao(this);
            List<String> usuarios = usuarioDao.nomesUsuariosPorId(usuario.getIdEmpresa(), usuario.getNome());
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, usuarios);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            paraUsuario.setAdapter(adapter);
        }

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String para = paraUsuario.getSelectedItem().toString();
                String perg = pergunta.getText().toString();
                if(para.isEmpty() || perg.isEmpty()){
                    Toast.makeText(CadastroDuvida.this, "Informe todos os dados",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Duvida duvida = new Duvida(usuario.getNome(),
                            para, pergunta.getText().toString());
                    if(repositorio.equals("remoto")){
                        Toast.makeText(CadastroDuvida.this, "Dados enviados", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CadastroDuvida.this, AdicionarDuvida.class);
                        intent.putExtra("url", "https://business-manager-server.herokuapp.com/");
                        intent.putExtra("duvida", duvida);
                        startService(intent);
                    } else {
                        DuvidaDao duvidaDao = new DuvidaDao(CadastroDuvida.this);
                        duvida.setEnviado(0);
                        duvida.setResposta("null");
                        duvidaDao.inserirDuvida(duvida);
                        Intent intent1 = new Intent(CadastroDuvida.this, MenuActivity.class);
                        startActivity(intent1);
                    }
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

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcast);
        super.onDestroy();

    }

    private class CadastroDuvidaBroad extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(CadastroDuvida.this, "Cadastrado", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(CadastroDuvida.this, MenuActivity.class);
            startActivity(intent1);
        }

    }

}
