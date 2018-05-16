package bm.pdm.ifpb.com.businessmanager;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class CadastroActivity extends AppCompatActivity {

    private Button administrador, funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        //
        this.administrador = findViewById(R.id.botaoAdm);
        this.funcionario = findViewById(R.id.botaoFunc);
        //
        administrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialogo = construirAlerta("Escolha por cadastro de administrador",
                        "Informe a palavra passe: ");
                dialogo.show();
            }
        });
        funcionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private AlertDialog construirAlerta(String titulo, String mensagem){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(titulo);
        b.setMessage(mensagem);
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        b.setView(input);
        b.setNegativeButton("Prosseguir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
            }
        });
        AlertDialog alerta = b.create();
        return alerta;
    }

}
