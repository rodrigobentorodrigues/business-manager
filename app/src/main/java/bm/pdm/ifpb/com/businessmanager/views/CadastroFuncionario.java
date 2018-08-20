package bm.pdm.ifpb.com.businessmanager.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.infra.AdicionarFuncionario;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroFuncionario extends AppCompatActivity {

    private EditText nome, cargo, login, senha, tel;
    private Button cadastro, voltar;
    private Usuario usuario;
    private Usuario usuarioAdministrador;
    private DadosUsuario dadosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);
        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.usuarioAdministrador = dadosUsuario.autenticado();
        Log.d("Administrador", usuarioAdministrador.toString());
        this.nome = findViewById(R.id.nomeFunc);
        this.cargo = findViewById(R.id.cargoFunc);
        this.login = findViewById(R.id.loginFunc);
        this.senha = findViewById(R.id.senhaFunc);
        this.tel = findViewById(R.id.telefoneCampo);
        MaskEditTextChangedListener mask = new MaskEditTextChangedListener("(###)#####-####", tel);
        tel.addTextChangedListener(mask);
        this.cadastro = findViewById(R.id.cadFunc);
        this.voltar = findViewById(R.id.backCadFunc);
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
                    usuario.setIdEmpresa(usuarioAdministrador.getIdEmpresa());
                    // usuarioDao.inserirUsuario(usuario);
                    AdicionarFuncionario addFunc = new bm.pdm.ifpb.com.businessmanager.infra.
                            AdicionarFuncionario(usuario, CadastroFuncionario.this);
                    addFunc.execute("https://business-manager-server.herokuapp.com/");
                    nome.setText("");
                    cargo.setText("");
                    login.setText("");
                    senha.setText("");
                    tel.setText("");
                    // requisitarPermissoes();
                }
            }
        });
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
            startActivityForResult(intentCamera, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Bundle bundle = data.getExtras();
//        if(bundle.get("data") != null){
//            Bitmap bitmap = (Bitmap) bundle.get("data");
//            conversor = new ConversorImagem();
//            // usuarioDao = new UsuarioDao(CadastroFuncionario.this);
//            // usuario.setImagem(conversor.toByteArray(bitmap));
//            // usuarioDao.inserirUsuario(usuario);
//            AdicionarFuncionario addFunc = new bm.pdm.ifpb.com.businessmanager.infra.
//                    AdicionarFuncionario(usuario, CadastroFuncionario.this);
//            addFunc.execute("https://business-manager-server.herokuapp.com/");
//            nome.setText("");
//            cargo.setText("");
//            login.setText("");
//            senha.setText("");
//            tel.setText("");
//        } else {
//            despacharIntent();
//        }
    }

}
