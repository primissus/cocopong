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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        btnCrearPartida = (Button) findViewById(R.id.btnCrearPartida);
        btnUnirPartida = (Button) findViewById(R.id.btnUnirPartida);

        btnUnirPartida.setOnClickListener(this);
        btnCrearPartida.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if(v.getId() == R.id.btnCrearPartida)      //Metodo del boton crear partida
            intent = new Intent(this,CrearPartidaActivity.class);
        else if(v.getId() == R.id.btnUnirPartida)  //Método del boton unir partida
            intent = new Intent(this,UnirPartidaActivity.class);
        startActivity(intent);
        finish();
    }
}
