package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.infra.UsuarioAdapter;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;

public class UsuarioActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        List<Usuario> usuarios = Arrays.asList(new Usuario(0, "Rodrigo", "Administrador",
                        "rod", "123", "981549498"),
                new Usuario(0, "Rodrigo", "Administrador",
                        "rod", "123", "981549498"));
        this.listView = findViewById(android.R.id.list);
        listView.setAdapter(new UsuarioAdapter(usuarios, UsuarioActivity.this));
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
