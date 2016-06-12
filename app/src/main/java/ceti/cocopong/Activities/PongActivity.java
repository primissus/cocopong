package ceti.cocopong.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ceti.cocopong.R;
import ceti.cocopong.Vistas.PongView;

public class PongActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private PongView pongView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pongView = new PongView(this);
        setContentView(pongView);
        if(getIntent().getBooleanExtra("isServer",false)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show(this, "Realizando conexion", "Espere a que un usuario se conecte");
        }
    }

    public void dismissProgresDialog(){
        progressDialog.dismiss();
    }

    public PongView getPongView() {
        return pongView;
    }
}
