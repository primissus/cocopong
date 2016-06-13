package ceti.cocopong.Vistas;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import ceti.cocopong.Activities.PongActivity;
import ceti.cocopong.Controladores.ClientThread;
import ceti.cocopong.Controladores.Drawer;
import ceti.cocopong.Controladores.GameLooper;
import ceti.cocopong.Controladores.ServerThread;
import ceti.cocopong.Entidades.Paleta;
import ceti.cocopong.Entidades.Pelota;

/**
 * Created by law on 11/06/16.
 */
public class PongView extends SurfaceView{
    private SurfaceHolder holder;
    private Pelota pelota;
    private Paleta paleta;

    private String user;
    private boolean isServer;

    private PongActivity activity;

    private ServerThread server;
    private ClientThread client;

    private GameLooper looper;

    private Drawer drawer;

    public PongView(Context context) {
        super(context);
        activity = (PongActivity) context;
        user = activity.getIntent().getStringExtra("Usuario");
        isServer = activity.getIntent().getBooleanExtra("isServer", false);
        pelota = new Pelota();
        paleta = new Paleta();
        if(isServer){
            server = new ServerThread(activity);
            server.start();
        }
        else{
            client = new ClientThread(activity);
            server.start();
        }
    }

    public void init(){
        if(isServer){
            Random rnd = new Random();
            pelota.setVisible(rnd.nextBoolean());
            sendTurn(!pelota.isVisible());
        }
        drawer = new Drawer(pelota,paleta);
    }

    private void sendTurn(boolean openentsTurn){
        JSONObject json = new JSONObject();
        try {
            json.put("code",0);
            json.put("turn",openentsTurn);
            if(isServer)
                server.sendData(json.toString());
            else
                client.sendData(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void draw(){
        Canvas canvas = holder.lockCanvas();
        drawer.draw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    public void stop(){
        looper.setRunning(false);
        if(isServer)
            server.setRunning(false);
        else
            client.setRunning(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        pelota.setHeight(h/10);
        pelota.setWidth(h/10);
        paleta.setBounds(w*4/10,h*9/10,w/5,h/10);
    }
}
