package ceti.cocopong.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import ceti.cocopong.R;

public class ResultadoActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSalir;
    private TextView tvMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_resultado);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        tvMensaje = (TextView) findViewById(R.id.tvMensaje);

        Bundle recibido = getIntent().getExtras();
        tvMensaje.setText(recibido.getString("Mensaje"));           //Se muestra en un TextView el mensaje de gananaste o perdiste

        btnSalir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSalir){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
