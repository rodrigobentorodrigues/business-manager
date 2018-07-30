package bm.pdm.ifpb.com.businessmanager.views;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.AdicionarFuncionario;
import bm.pdm.ifpb.com.businessmanager.infra.ConversorImagem;
import bm.pdm.ifpb.com.businessmanager.infra.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.sqlite.UsuarioDao;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroFuncionario extends AppCompatActivity {

    private EditText nome, cargo, login, senha, tel;
    private Button cadastro;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;
    private ConversorImagem conversor;
    private String caminhoImagem;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);
        //
        this.nome = findViewById(R.id.nomeFunc);
        this.cargo = findViewById(R.id.cargoFunc);
        this.login = findViewById(R.id.loginFunc);
        this.senha = findViewById(R.id.senhaFunc);
        this.tel = findViewById(R.id.telefoneCampo);
        MaskEditTextChangedListener mask = new MaskEditTextChangedListener("(###)#####-####", tel);
        tel.addTextChangedListener(mask);
        this.cadastro = findViewById(R.id.cadFunc);
        //
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeFunc = nome.getText().toString();
                String cargoFunc = cargo.getText().toString();
                String loginFunc = login.getText().toString();
                String senhaFunc = senha.getText().toString();
                String telFunc = tel.getText().toString();
                Log.i("TELEFONE", telFunc);
                if(nomeFunc.equals("") || cargoFunc.equals("") ||
                        loginFunc.equals("")  || senhaFunc.equals("") || telFunc.equals("")){
                    Toast.makeText(CadastroFuncionario.this, "Informe todos os campos",
                            Toast.LENGTH_SHORT).show();
                } else {
                    usuario = new Usuario(0, nomeFunc, cargoFunc, loginFunc, senhaFunc, telFunc);
                    usuario.setIdEmpresa(1);
                    requisitarPermissoes();

                }
            }
        });
    }

    private void requisitarPermissoes(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else {
            despacharIntent();
        }
    }

    // Metodo responsavel por despachar o usuário em relação as permissões
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    despacharIntent();
                } else {
                    Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void despacharIntent(){
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentCamera.resolveActivity(getPackageManager()) != null){
            File imagem = null;
            File diretorioTemp = Environment.getExternalStorageDirectory();
            try {
                imagem = File.createTempFile("PERFIL", ".jpg", diretorioTemp);
                caminhoImagem = "file:" + imagem.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(imagem != null){
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagem));
                startActivityForResult(intentCamera, 0);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(caminhoImagem)));
            conversor = new ConversorImagem();
            usuarioDao = new UsuarioDao(CadastroFuncionario.this);
            usuario.setImagem(conversor.toByteArray(bitmap));
            usuarioDao.inserirUsuario(usuario);
            AdicionarFuncionario addFunc = new bm.pdm.ifpb.com.businessmanager.infra.
                    AdicionarFuncionario(usuario, CadastroFuncionario.this);
            addFunc.execute("https://business-manager-server.herokuapp.com/");
            nome.setText("");
            cargo.setText("");
            login.setText("");
            senha.setText("");
            tel.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
