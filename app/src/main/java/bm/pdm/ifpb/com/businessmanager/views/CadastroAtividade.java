package bm.pdm.ifpb.com.businessmanager.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.services.AdicionarAtividade;

public class CadastroAtividade extends AppCompatActivity {

    private Spinner spinner;
    private EditText desc, data, titulo, funcionario;
    private Button cadastro;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_atividade);
        //
        IntentFilter filter = new IntentFilter("cad-ativ");
        registerReceiver(new CadAtivBroadCast(), filter);

//        this.spinner = findViewById(R.id.spinner3);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.tipo, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        this.spinner.setAdapter(adapter);

        this.funcionario = findViewById(R.id.campoNomeFunc);
        this.desc = findViewById(R.id.campoDesc);
        this.data = findViewById(R.id.campoData);
        this.titulo = findViewById(R.id.campoTitulo);
        this.cadastro = findViewById(R.id.cadAtiv);

        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.usuario = dadosUsuario.autenticado();

        Log.i("Cad-Atividade", usuario.toString());
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deUsuario = usuario.getNome();
                String paraUsuario = funcionario.getText().toString();
                String valorDesc = desc.getText().toString();
                String valorData = data.getText().toString();
                Log.i("Data", valorData);
                String valorTitulo = titulo.getText().toString();

                if(paraUsuario.isEmpty() || valorDesc.isEmpty() ||
                        valorData.isEmpty() || valorData.isEmpty() || valorTitulo.isEmpty()){
                    Toast.makeText(CadastroAtividade.this, "Informe todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastroAtividade.this, "Enviando dados para o servidor", Toast.LENGTH_SHORT).show();
                    Tarefa tarefa = new Tarefa(0, deUsuario, paraUsuario, valorTitulo, valorDesc, valorData, false);
                    Intent intent = new Intent(CadastroAtividade.this, AdicionarAtividade.class);
                    intent.putExtra("url", "https://business-manager-server.herokuapp.com/");
                    intent.putExtra("tarefa", tarefa);
                    startService(intent);
                }
            }
        });
    }

    private class CadAtivBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(CadastroAtividade.this, "Cadastrado!", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(CadastroAtividade.this, MenuActivity.class);
            startActivity(intent1);
        }

    }

}
