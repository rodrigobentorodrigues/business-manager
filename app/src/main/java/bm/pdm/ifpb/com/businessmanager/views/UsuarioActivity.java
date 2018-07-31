package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.infra.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.infra.ListarUsuario;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;

public class UsuarioActivity extends AppCompatActivity {

    private ListView listView;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        //
        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.usuario = dadosUsuario.autenticado();
        Log.d("Usuario", usuario.toString());
        //
        this.listView = findViewById(android.R.id.list);
        ListarUsuario listarUsuario = new ListarUsuario(UsuarioActivity.this, listView);
        listarUsuario.execute("https://business-manager-server.herokuapp.com/usuario/todosPorId?id="+usuario.getIdEmpresa());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = (Usuario) parent.getAdapter().getItem(position);
                Intent intent = new Intent(UsuarioActivity.this, InfoUsuarioActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
    }
}
