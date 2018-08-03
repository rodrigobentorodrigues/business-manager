package bm.pdm.ifpb.com.businessmanager.views;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.infra.SincronizarDadosAdicionais;
import bm.pdm.ifpb.com.businessmanager.infra.SincronizarDadosUsuario;

public class MenuActivity extends AppCompatActivity {

    private ImageButton atividade, contatos, cadastro, duvida;
    private final String TIPO_FUNC = "Administrador";
    private DadosUsuario dadosUsuario;
    private Usuario usuario;
    private Configuracao config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //
        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        //
        this.atividade = findViewById(R.id.botaoAtividade);
        this.contatos = findViewById(R.id.botaoContatos);
        this.cadastro = findViewById(R.id.botaoCadastro);
        this.duvida = findViewById(R.id.botaoDuvida);
        //
        usuario = dadosUsuario.autenticado();
        atividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usuario.getCargo().equals(TIPO_FUNC)){
                    Intent intent = new Intent(MenuActivity.this,
                            TarefaActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MenuActivity.this,
                            CadastroAtividade.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }
            }
        });
        contatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, UsuarioActivity.class);
                startActivity(intent);
            }
        });
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usuario.getCargo().equals(TIPO_FUNC)){
                    AlertDialog alert = construirAlerta("Acesso negado",
                            "Você não tem acesso para essa parte do aplicativo!");
                    alert.show();
                } else {
                    Intent intent = new Intent(MenuActivity.this,
                            CadastroFuncionario.class);
                    startActivity(intent);
                }
            }
        });
        duvida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, DuvidaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.opcoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.dados:
                Toast.makeText(this, "Em desenvolvimento", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buscaServidor:
                this.config = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
                String servidor = config.getRepositorio();
                if(servidor.equals("local")){
                    AlertDialog.Builder b2 = new AlertDialog.Builder(this);
                    b2.setTitle("Sincronização de Dados");
                    b2.setMessage("Deseja sincronizar com os dados do servidor remoto?");
                    b2.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    b2.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SincronizarDadosAdicionais sinc = new SincronizarDadosAdicionais(MenuActivity.this);
                            sinc.execute("https://business-manager-server.herokuapp.com/duvida/listar",
                                    "https://business-manager-server.herokuapp.com/tarefa/listar");
                        }
                    });
                    AlertDialog alerta2 = b2.create();
                    alerta2.show();
                } else {
                    Toast.makeText(MenuActivity.this, "Você ja está utilizando os dados do servidor", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.camera:
                requisitarPermissoes();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requisitarPermissoes(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        } else {
            abrirCamera();
        }
    }

    private void abrirCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 0);
        }
    }

    // Metodo responsavel por despachar o usuário em relação as permissões
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    abrirCamera();
                } else {
                    requisitarPermissoes();
                }
                return;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        // Caminho do diretorio da camera
        String diretorio = Environment.
                getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM).toString()+ "/Camera/BusinessManager";
        File dir = new File(diretorio);
        // Caso nao exista, criar pastas
        if(!dir.exists()){
            dir.mkdirs();
        }
        // ID para foto (não se repetir)
        UUID idFoto = UUID.randomUUID();
        String nomeArquivo = "Image-" + idFoto.toString() + ".png";
        File foto = new File(dir, nomeArquivo);
        try {
            // Escrevendo foto em arquivo
            FileOutputStream outputStream = new FileOutputStream(foto);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaScannerConnection.scanFile(MenuActivity.this, new String[]{foto.getPath()},
                new String[]{"image/jpeg"}, null);
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
