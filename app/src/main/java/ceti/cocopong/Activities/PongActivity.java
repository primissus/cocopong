package ceti.cocopong.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import ceti.cocopong.R;
import ceti.cocopong.Vistas.PongView;

public class PongActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private PongView pongView;
    private boolean exit;

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

    public void dataReceived(JSONObject json){
        try{
            switch(json.getInt("code")){
                case 0:     //Turno
                    break;
                case 1:     //Posicion pelota contrario
                    break;
                case 2:     //Recibir pelota
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void showConexionError(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(PongActivity.this);
                builder.setTitle("Servidor desconectado");
                builder.setMessage("No existe servidor para el juego, y por lo tanto la partida no puede continuar. La partida se cerrará");
                builder.setNeutralButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeActivity();
                    }
                });
                builder.show();
            }
        });
    }

    private void closeActivity(){
        pongView.stop();
    }

    public void dismissProgresDialog(){
        progressDialog.dismiss();
    }

    public PongView getPongView() {
        return pongView;
    }
}
