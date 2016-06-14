package ceti.cocopong.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import ceti.cocopong.Controladores.SensorPong;
import ceti.cocopong.R;
import ceti.cocopong.Vistas.PongView;

public class PongActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private PongView pongView;
    private boolean exit;
    private int currentApiVersion = android.os.Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }
        pongView = new PongView(this);
        setContentView(pongView);
        progressDialog = new ProgressDialog(this);
        progressDialog.show(this, "Realizando conexion", "Espere a que un usuario se conecte");
        pongView.getLooper().setSensor(new SensorPong(this));
    }

    public void dataReceived(JSONObject json){
        try{
            switch(json.getInt("code")){
                case 0:     //Turno
                    pongView.initPelota(json.getBoolean("turn"));
                    break;
                case 1:     //Posicion pelota contrario
                    pongView.setPelotaX(json.getInt("xPos"));
                    break;
                case 2:     //Recibir pelota
                    pongView.receivePelota(json.getInt("xPos"));
                    break;
                case 3:
                    pongView.setPuntosOpenente(pongView.getPuntosOpenente()+1);
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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

    @Override
    protected void onStop() {
        super.onStop();
        pongView.stop();
    }

    public String getAddress(){
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }
}
