package ceti.cocopong.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ceti.cocopong.Controladores.ApManager;
import ceti.cocopong.Controladores.HotspotsAdapter;
import ceti.cocopong.R;

public class UnirPartidaActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView lstPartidas;
    private WifiManager wifiManager;
    private ApManager apManager;
    private HotspotsAdapter adapter;
    private EditText txtUsername;
    private Button btnCancelar;

    private Timer timer;
    private TimerTask task;

    private List<ScanResult> results;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_unir_partida);
        results = new ArrayList<>();
        lstPartidas = (ListView) findViewById(R.id.lstHotspots);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        btnCancelar = (Button) findViewById(R.id.btnCancelarUnir);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        apManager = new ApManager(wifiManager);
        apManager.stopHotSpot();
        wifiManager.setWifiEnabled(true);
        Toast.makeText(this, "Asegurese de que el wifi esté encendido", Toast.LENGTH_SHORT).show();
        adapter = new HotspotsAdapter(this, results);
        lstPartidas.setAdapter(adapter);

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                wifiManager.startScan();
            }
        };

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                results = wifiManager.getScanResults();
                adapter.setResults(results);
                filterResults();
                adapter.notifyDataSetChanged();
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        timer.schedule(task, 3000, 1000);
        lstPartidas.setOnItemClickListener(this);
        wifiManager.startScan();

    }

    private void filterResults(){
        for(int i=0;i<results.size();i++)
            if(!results.get(i).SSID.startsWith("Ptr")) {
                results.remove(i);
                i--;
            }
    }

    public void goToMainActivity(){
        Intent intent;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final int index = position;
        final String SSID = results.get(position).SSID;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (txtUsername.getText().toString().isEmpty()) {
            builder.setTitle("Datos incompletos");
            builder.setMessage("Por favor ingrese su nombre de jugador para poder conectarse a una partida");
            builder.setNeutralButton("Entendido", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        else{
            builder.setTitle("Intentar conectarse a partida");
            builder.setMessage("¿Desea intentar conectarse al hotspot para comprobar partida?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    if (connectToNetwork(SSID) && getConnectedNetwork().equals("\"" + SSID + "\"")) {
                        Toast.makeText(UnirPartidaActivity.this, "Conectado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UnirPartidaActivity.this, PongActivity.class);
                        intent.putExtra("Usuario", txtUsername.getText().toString());
                        intent.putExtra("isServer", false);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(UnirPartidaActivity.this, "Error al conectar", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    private boolean connectToNetwork(String SSID){
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = String.format("\"%s\"",SSID);
        conf.preSharedKey = "\""+SSID.replaceFirst("Ptr", "pss")+"\"";

        int netId= wifiManager.addNetwork(conf);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        return wifiManager.reconnect();

    }

    private String  getConnectedNetwork(){
        WifiInfo info = null;
        while((info = wifiManager.getConnectionInfo()).getSSID().equals("<unknown ssid>"));
        return info.getSSID();
    }
}
