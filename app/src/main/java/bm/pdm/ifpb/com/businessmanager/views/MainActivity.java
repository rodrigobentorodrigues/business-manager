package bm.pdm.ifpb.com.businessmanager.views;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.infra.AutenticarUsuario;

public class MainActivity extends AppCompatActivity {

    private Button botaoLogin;
    private Spinner spinner;
    private EditText login, senha;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
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
        this.botaoLogin = findViewById(R.id.botaoLogin);

        // Setando os valores para o Spinner
        this.spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);

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
                    ConnectivityManager cm = (ConnectivityManager)
                            MainActivity.this.getSystemService(CONNECTIVITY_SERVICE);
                    // Objeto netInfo que recebe as informacoes da Network
                    NetworkInfo netInfo = cm.getActiveNetworkInfo();
                    //Se o objeto for nulo ou nao tem conectividade retorna false
                    if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable())){
                        //if(valorLogin.equals("adminpdm") && valorSenha.equals("pdmadmin")){
                        if(valorLogin.equals("a") && valorSenha.equals("a")){
                            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
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
                }
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
