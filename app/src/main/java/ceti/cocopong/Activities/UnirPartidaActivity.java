package ceti.cocopong.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ceti.cocopong.Entidades.HotspotClient;
import ceti.cocopong.R;

public class UnirPartidaActivity extends AppCompatActivity {

    private Intent intent;
    private ArrayList<HotspotClient> hotspotClients;
    private EditText txtNombreUsuario;
    private Button btnIniciar;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_crear_partida);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnCancelar = (Button) findViewById(R.id.btnCancelarCrear);
        txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtNombreUsuario.getText().toString().isEmpty()) {
                    intent = new Intent(UnirPartidaActivity.this, PongActivity.class);
                    intent.putExtra("Usuario", txtNombreUsuario.getText().toString());
                    intent.putExtra("isServer", true);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(UnirPartidaActivity.this, "Es necesario que llene el campo requerido", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void goToMainActivity(){
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
            goToMainActivity();
        return true;
    }
}
