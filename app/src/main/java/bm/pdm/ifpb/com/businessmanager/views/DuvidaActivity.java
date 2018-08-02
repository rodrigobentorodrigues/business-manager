package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.infra.ListarDuvida;

public class DuvidaActivity extends AppCompatActivity {

    private ListView listView;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;
    private Configuracao config;
    private String repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvida);

        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.usuario = dadosUsuario.autenticado();
        this.config = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        this.repositorio = config.getRepositorio();
        this.listView = findViewById(android.R.id.list);
        if (repositorio.equals("remoto")){
            ListarDuvida listarDuvida = new ListarDuvida(DuvidaActivity.this, listView);
            listarDuvida.execute("https://business-manager-server.herokuapp.com/duvida/naoConcluidas?usuario="+usuario.getNome());
        } else {
            // SQLite
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Duvida duvida = (Duvida) parent.getAdapter().getItem(position);
                if(duvida.getParaUsuario().equals(usuario.getNome())){
                    Intent intent = new Intent(DuvidaActivity.this, InfoDuvidaActivity.class);
                    intent.putExtra("duvida", duvida);
                    startActivity(intent);
                } else {
                    Toast.makeText(DuvidaActivity.this, "Para visualizar esta duvida você precisa ser o destinatário",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.duvida, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addContato:
                Intent intent = new Intent(DuvidaActivity.this, CadastroDuvida.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
