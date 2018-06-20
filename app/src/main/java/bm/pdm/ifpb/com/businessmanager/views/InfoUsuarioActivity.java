package bm.pdm.ifpb.com.businessmanager.views;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.ContatosUtil;

public class InfoUsuarioActivity extends AppCompatActivity {

    private TextView nome, telefone;
    private Button ligar, adicionar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_usuario);
        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("usuario");
        this.nome = findViewById(R.id.nomeUsu);
        this.nome.setText(usuario.getNome());
        this.telefone = findViewById(R.id.numUsu);
        this.telefone.setText(usuario.getTelefone());
        this.ligar = findViewById(R.id.botaoLigar);
        this.adicionar = findViewById(R.id.botaoAgenda);

        ligar.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(InfoUsuarioActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 100);
                }
                Uri uri = Uri.parse("tel:" + usuario.getTelefone());
                Intent call = new Intent(Intent.ACTION_CALL, uri);
                startActivity(call);
            }
        });

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContatosUtil util = new ContatosUtil(getContentResolver());
                boolean condAgenda = util.verificarNumeroAgenda(usuario.getTelefone());
                if(!condAgenda){
                    // Adicionando a agenda de contatos
                    Intent inten = new Intent(ContactsContract.Intents.Insert.ACTION);
                    inten.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    inten.putExtra(ContactsContract.Intents.Insert.NAME, usuario.getNome());
                    inten.putExtra(ContactsContract.Intents.Insert.PHONE, usuario.getTelefone());
                    inten.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                    startActivityForResult(inten, 0);
                } else {
                    String titulo = "Contato ja cadastrado";
                    String msg = "Esse contato já existe na sua agenda...";
                    AlertDialog alerta = construirAlerta(titulo, msg);
                    alerta.show();
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
