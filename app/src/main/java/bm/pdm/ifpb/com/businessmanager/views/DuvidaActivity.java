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

import java.util.Arrays;
import java.util.List;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.infra.DuvidaAdapter;

public class DuvidaActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvida);
        //
        List<Duvida> duvidasTeste = Arrays.asList(
                new Duvida("Rodrigo","Rennan", "Como faço um listView?"),
                new Duvida("Ari","Juan", "Como criar um gmail?"),
                new Duvida("Maria","Joao", "Me auxilia em PDM? Me auxilia em PDM? Me auxilia em PDM? "));
        //
        this.listView = findViewById(android.R.id.list);
        listView.setAdapter(new DuvidaAdapter(duvidasTeste, DuvidaActivity.this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Duvida duvida = (Duvida) parent.getAdapter().getItem(position);
                Intent intent = new Intent(DuvidaActivity.this, InfoDuvidaActivity.class);
                intent.putExtra("duvida", duvida);
                startActivity(intent);
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
