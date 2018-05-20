package bm.pdm.ifpb.com.businessmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;

public class MainActivity extends AppCompatActivity {

    private Button botaoLogin, botaoCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        this.botaoLogin = findViewById(R.id.botaoLogin);
        this.botaoCadastro = findViewById(R.id.botaoCad);
        //
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(MainActivity.this,
                        LoginActivity.class);
                startActivity(intentLogin);
            }
        });
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialogo = construirAlerta("Escolha por cadastro de administrador",
                        "Informe a palavra passe: ");
                dialogo.show();
            }
        });
    }

    private AlertDialog construirAlerta(String titulo, String mensagem){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(titulo);
        b.setMessage(mensagem);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setGravity(Gravity.CENTER);
        b.setView(input);
        b.setNegativeButton("Prosseguir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String palavraPasse = input.getText().toString();
                Log.i("Palavra", palavraPasse);
                if(palavraPasse.equals("pdm2018")){
                    Intent intentCadastro = new Intent(MainActivity.this,
                            CadastroActivity.class);
                    startActivity(intentCadastro);
                } else {
                    Toast.makeText(MainActivity.this,
                            "Credenciais invalidas", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alerta = b.create();
        return alerta;
    }
}
