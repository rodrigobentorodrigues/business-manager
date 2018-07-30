package bm.pdm.ifpb.com.businessmanager.views;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.AdicionarAdministrador;
import bm.pdm.ifpb.com.businessmanager.infra.AdicionarFuncionario;
import bm.pdm.ifpb.com.businessmanager.infra.ConversorImagem;
import bm.pdm.ifpb.com.businessmanager.sqlite.UsuarioDao;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroAdministrador extends AppCompatActivity {

    private EditText nome, login, senha, tel, empresa;
    private Button cadastro;
    private Usuario usuario;
    private ConversorImagem conversor;
    private String caminhoImagem, empresaFunc;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_administrador);
        //
        this.nome = findViewById(R.id.campoFuncionario);
        this.login = findViewById(R.id.campoLogin);
        this.senha = findViewById(R.id.campoSenha);
        this.tel = findViewById(R.id.campoTel);
        MaskEditTextChangedListener mask = new MaskEditTextChangedListener("(###)#####-####", tel);
        tel.addTextChangedListener(mask);
        this.empresa = findViewById(R.id.campoEmpresa);
        this.cadastro = findViewById(R.id.botaoCadastro);
        //
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empresaFunc = empresa.getText().toString();
                String nomeFunc = nome.getText().toString();
                String loginFunc = login.getText().toString();
                String senhaFunc = senha.getText().toString();
                String telFunc = tel.getText().toString();
                if(nomeFunc.equals("") || loginFunc.equals("")
                        || senhaFunc.equals("") || telFunc.equals("")){
                    Toast.makeText(CadastroAdministrador.this, "Informe todos os campos",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //
                    requisitarPermissoes();
                    usuario = new Usuario(0, nomeFunc, "Administrador", loginFunc,
                            senhaFunc, telFunc);
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
            usuarioDao = new UsuarioDao(CadastroAdministrador.this);
            byte[] imagem = conversor.toByteArray(bitmap);
            Log.i("Imagem", imagem.toString());
            usuario.setImagem(imagem);
            AdicionarAdministrador add = new AdicionarAdministrador(usuario,
                    CadastroAdministrador.this, empresaFunc);
            add.execute("https://business-manager-server.herokuapp.com/");
            nome.setText("");
            empresa.setText("");
            login.setText("");
            senha.setText("");
            tel.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class CadastroAdmBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(CadastroAdministrador.this, "Cadastrado", Toast.LENGTH_SHORT).show();
        }

    }

}
