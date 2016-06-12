package ceti.cocopong.Activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ceti.cocopong.Controladores.ApManager;
import ceti.cocopong.Controladores.HotspotsAdapter;
import ceti.cocopong.R;

public class UnirPartidaActivity extends AppCompatActivity {

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

        lstPartidas = (ListView) findViewById(R.id.lstHotspots);
        txtUsername = (EditText) findViewById(R.id.txtNombreUsuario);
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
}
