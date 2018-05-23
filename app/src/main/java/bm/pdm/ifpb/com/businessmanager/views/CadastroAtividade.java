package bm.pdm.ifpb.com.businessmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;

public class CadastroAtividade extends AppCompatActivity {

    private Spinner spinner;
    private EditText desc, data;
    private Button cadastro;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_atividade);

        tipo = getIntent().getStringExtra("tipo");

        this.spinner = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);

        this.desc = findViewById(R.id.campoDesc);
        this.data = findViewById(R.id.campoData);
        this.cadastro = findViewById(R.id.cadAtiv);
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorDesc = desc.getText().toString();
                String valorData = data.getText().toString();
                Toast.makeText(CadastroAtividade.this, valorDesc +
                        " " + valorData, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CadastroAtividade.this, MenuActivity.class);
                intent.putExtra("tipo", tipo);
                startActivity(intent);
            }
        });
    }

}
