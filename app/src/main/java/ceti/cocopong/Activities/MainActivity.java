package ceti.cocopong.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import ceti.cocopong.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCrearPartida;
    private Button btnUnirPartida;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        btnCrearPartida = (Button) findViewById(R.id.btnCrearPartida);
        btnUnirPartida = (Button) findViewById(R.id.btnUnirPartida);
        btnSalir = (Button) findViewById(R.id.btnSalir);

        btnUnirPartida.setOnClickListener(this);
        btnCrearPartida.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if(v.getId()== R.id.btnSalir)
            finish();                                   //Terminar aplicacion
        else if(v.getId() == R.id.btnCrearPartida)      //Ir a crear partida
            intent = new Intent(this,CrearPartidaActivity.class);
        else if(v.getId() == R.id.btnUnirPartida)       //Ir a unir partida
            intent = new Intent(this,UnirPartidaActivity.class);
        startActivity(intent);
        finish();
    }
}
