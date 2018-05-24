package bm.pdm.ifpb.com.businessmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InfoDuvidaActivity extends AppCompatActivity {

    private Button enviar, responder;
    private TextView titulo, descricao;
    private EditText resposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_duvida);
        this.responder = findViewById(R.id.botaoResp);
        this.enviar = findViewById(R.id.botaoEnviar);
    }
}
