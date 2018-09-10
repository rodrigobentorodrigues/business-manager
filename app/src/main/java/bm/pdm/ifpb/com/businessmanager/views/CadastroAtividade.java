package bm.pdm.ifpb.com.businessmanager.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Configuracao;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import bm.pdm.ifpb.com.businessmanager.domains.DadosUsuario;
import bm.pdm.ifpb.com.businessmanager.infra.ListarUsuariosPorId;
import bm.pdm.ifpb.com.businessmanager.infra.NetworkUtils;
import bm.pdm.ifpb.com.businessmanager.services.AdicionarAtividade;
import bm.pdm.ifpb.com.businessmanager.sqlite.TarefaDao;

public class CadastroAtividade extends AppCompatActivity {

    private Spinner spinner;
    private EditText desc, data, titulo;
    private Button cadastro, voltar;
    private Usuario usuario;
    private DadosUsuario dadosUsuario;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private final String format = "dd/MM/yyyy";
    private CadAtivBroadCast broadCast;
    private Configuracao configuracao;
    private TarefaDao tarefaDao;
    private NetworkUtils networkUtils;
    private static List<String> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_atividade);
        this.broadCast = new CadAtivBroadCast();
        this.configuracao = new Configuracao(getSharedPreferences("config", MODE_PRIVATE));
        this.networkUtils = new NetworkUtils();
        this.dadosUsuario = new DadosUsuario(getSharedPreferences("usuario", MODE_PRIVATE));
        this.usuario = dadosUsuario.autenticado();
        this.usuarios = new ArrayList<>();
        //
        IntentFilter filter = new IntentFilter("cad-ativ");
        registerReceiver(broadCast, filter);

        this.spinner = findViewById(R.id.spinnerFuncAtividade);

        ListarUsuariosPorId listar = new ListarUsuariosPorId(this, spinner, usuario.getNome());
        listar.execute("http://business-manager-server.herokuapp.com/usuario/nomes?id="+usuario.getIdEmpresa());

        this.desc = findViewById(R.id.campoDesc);

        this.data = findViewById(R.id.campoData);
        this.dateFormat = new SimpleDateFormat(format, new Locale("pt", "BR"));
        this.calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate();
            }
        };
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CadastroAtividade.this, datePicker,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateLabelDate();
        this.titulo = findViewById(R.id.campoTitulo);
        this.cadastro = findViewById(R.id.cadAtiv);
        this.voltar = findViewById(R.id.backCadAtiv);



        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deUsuario = usuario.getNome();
                String paraUsuario = spinner.getSelectedItem().toString();
                String valorDesc = desc.getText().toString();
                String valorData = data.getText().toString();
                String valorTitulo = titulo.getText().toString();
                if(paraUsuario.isEmpty() || valorDesc.isEmpty() ||
                        valorData.isEmpty() || valorData.isEmpty() || valorTitulo.isEmpty()){
                    Toast.makeText(CadastroAtividade.this, "Informe todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    Date informado = null;
                    try {
                        informado = dateFormat.parse(valorData);
                        Log.i("Data Convertida", informado.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date atual = new Date();
                    if(atual.after(informado)){
                        Toast.makeText(CadastroAtividade.this, "A data não pode ser igual ou inferior ao dia de hoje!", Toast.LENGTH_SHORT).show();
                    } else {
                        Tarefa tarefa = new Tarefa(0, deUsuario, paraUsuario, valorTitulo, valorDesc, valorData, false);
                        String repositorio = configuracao.getRepositorio();
                        if(repositorio.equals("remoto")){
                            if(networkUtils.verificarConexao(CadastroAtividade.this)){
                                Intent intent = new Intent(CadastroAtividade.this, AdicionarAtividade.class);
                                intent.putExtra("url", "https://business-manager-server.herokuapp.com/");
                                intent.putExtra("tarefa", tarefa);
                                startService(intent);
                            } else {
                                String titulo = "Sem conexão com a internet";
                                String msg = "Por favor, conecte-se com alguma rede e tente novamente";
                                AlertDialog.Builder b = new AlertDialog.Builder(CadastroAtividade.this);
                                b.setTitle(titulo);
                                b.setMessage(msg);
                                b.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                                AlertDialog alerta = b.create();
                                alerta.show();
                            }

                        } else {
                            tarefaDao = new TarefaDao(CadastroAtividade.this);
                            tarefaDao.inserirTarefa(tarefa);
                            Intent intent = new Intent(CadastroAtividade.this,
                                    MenuActivity.class);
                            startActivity(intent);
                        }
                    }
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

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadCast);
        super.onDestroy();
    }

    private void updateLabelDate(){
        data.setText(dateFormat.format(calendar.getTime()));
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
