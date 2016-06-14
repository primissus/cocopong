package ceti.cocopong.Vistas;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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

    private int time;

    private int puntos;
    private int puntosOpenente;

    public PongView(Context context) {
        super(context);
        holder = getHolder();
        activity = (PongActivity)context;
        user = activity.getIntent().getStringExtra("Usuario");
        isServer = activity.getIntent().getBooleanExtra("isServer", false);
        pelota = new Pelota();
        paleta = new Paleta();
        puntos = 0;
        puntosOpenente = 0;
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(isServer){
                    server = new ServerThread(activity);
                    server.start();
                }
                else{
                    client = new ClientThread(activity);
                    client.start();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        looper = new GameLooper(this, pelota, paleta);
    }


    public void init(){
        if(isServer){
            Random rnd = new Random();
            pelota.setVisible(rnd.nextBoolean());
            sendTurn(!pelota.isVisible());
            initPelota(pelota.isVisible());
        }
        drawer = new Drawer(pelota,paleta);
        showTimer();
    }

    private void showTimer(){
        time=3;
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                drawTime();
                time--;
                if(time==0){
                    timer.cancel();
                    looper.start();
                }
            }
        };
        timer.schedule(task, 50, 1000);
    }

    public void sendXPelota(int x){
        JSONObject json = new JSONObject();
        try {
            json.put("code",1);
            json.put("xPos",x);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setPelotaX(int x){
        pelota.setX(x*getWidth()/100);
    }

    public void receivePelota(int x){
        pelota.setX(x*getWidth()/100);
        pelota.setVisible(true);
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
        drawer.drawPoints(canvas, puntos, puntosOpenente);
        holder.unlockCanvasAndPost(canvas);
    }

    private void drawTime(){
        Canvas canvas = holder.lockCanvas();
        drawer.drawTime(canvas,time);
        holder.unlockCanvasAndPost(canvas);
    }

    public void stop(){
        looper.setRunning(false);
        if(isServer)
            server.setRunning(false);
        else
            client.setRunning(false);

    }

    private void placePelota(){
        Random rnd = new Random();
        pelota.setX(rnd.nextInt(this.getWidth()*9/10));
        pelota.setY(0);
        pelota.setDx((this.getWidth()*6/100)*((rnd.nextBoolean())?1:-1));
        pelota.setDy(this.getHeight()*4/100);
    }

    private void placePelota(int x){
        pelota.setX(x);
        pelota.setY(0);
        pelota.setDx(this.getWidth()*6/100);
        pelota.setDy(this.getHeight()*4/100);
    }

    public void sendPelota(){
        JSONObject json = new JSONObject();
        try {
            json.put("code",1);
            json.put("xPos",pelota.getX()/getWidth());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendPunto(){
        JSONObject json = new JSONObject();
        try {
            json.put("code",3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initPelota(boolean turn){
        if(turn){
            placePelota();
            pelota.setVisible(true);
        }
        else{
            placePelota(0);
            pelota.setVisible(false);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        pelota.setHeight(h/10);
        pelota.setWidth(h/10);
        paleta.setBounds(w*4/10,h*9/10,w/5,h/10);
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntosOpenente() {
        return puntosOpenente;
    }

    public void setPuntosOpenente(int puntosOpenente) {
        this.puntosOpenente = puntosOpenente;
    }

    public GameLooper getLooper() {
        return looper;
    }
}
