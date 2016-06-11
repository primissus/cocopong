package ceti.cocopong.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ceti.cocopong.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCrearPartida;
    private Button btnUnirPartida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCrearPartida = (Button) findViewById(R.id.btnCrearPartida);
        btnUnirPartida = (Button) findViewById(R.id.btnUnirPartida);

        btnUnirPartida.setOnClickListener(this);
        btnCrearPartida.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnCrearPartida){      //Metodo del boton crear partida

        }
        else if(v.getId() == R.id.btnUnirPartida){  //Método del boton unir partida

        }
    }
}
