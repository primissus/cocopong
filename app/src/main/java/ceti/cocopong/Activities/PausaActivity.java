package ceti.cocopong.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ceti.cocopong.R;

public class PausaActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnReanudar;
    private Button btnReiniciar;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pausa);

        btnReanudar = (Button) findViewById(R.id.btnReanudar);
        btnReiniciar = (Button) findViewById(R.id.btnReiniciar);
        btnSalir = (Button) findViewById(R.id.btnSalir);

        btnReanudar.setOnClickListener(this);
        btnReiniciar.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnReanudar){          //Metodo del boton de reanudar

        }
        else if(v.getId() == R.id.btnReiniciar){    //Metodo del boton de reiniciar

        }
        else if(v.getId() == R.id.btnSalir){        //Metodo del boton de salir

        }

    }
}
