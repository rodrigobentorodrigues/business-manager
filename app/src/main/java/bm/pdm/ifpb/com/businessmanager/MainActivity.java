package bm.pdm.ifpb.com.businessmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
                Intent intentCadastro = new Intent(MainActivity.this,
                        CadastroActivity.class);
                startActivity(intentCadastro);
            }
        });
    }
}
