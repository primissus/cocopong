package ceti.cocopong.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

import ceti.cocopong.Controladores.ApManager;
import ceti.cocopong.Entidades.HotspotClient;
import ceti.cocopong.R;

public class CrearPartidaActivity extends AppCompatActivity {

    private Intent intent;
    private ApManager apManager;
    private ArrayList<HotspotClient> hotspotClients;

    private EditText txtNombrePartida;
    private EditText txtNombreUsuario;
    private Button btnIniciar;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_crear_partida);

        apManager = new ApManager((WifiManager) getSystemService(Context.WIFI_SERVICE));
        stopConexion();

        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnCancelar = (Button) findViewById(R.id.btnCancelarCrear);
        txtNombrePartida = (EditText) findViewById(R.id.txtNombrePartida);
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
                createConection(txtNombrePartida.getText().toString());
                intent = new Intent(CrearPartidaActivity.this, MainActivity.class);
                intent.putExtra("Usuario", txtNombreUsuario.getText().toString());
                intent.putExtra("isServer", true);
                startActivity(intent);
                finish();
            }
        });

    }

    public void createConection(String nombrePartida){
        if(apManager.isApOn()) {
            apManager.configApState(null);
        }
        apManager.configApState(nombrePartida);
    }

    public void stopConexion(){
        while(apManager.isApOn())
            apManager.configApState(null);
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
