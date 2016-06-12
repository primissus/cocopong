package ceti.cocopong.Vistas;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ceti.cocopong.Activities.PongActivity;
import ceti.cocopong.Controladores.ClientThread;
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

    public PongView(Context context) {
        super(context);
        activity = (PongActivity) context;
        user = activity.getIntent().getStringExtra("Usuario");
        isServer = activity.getIntent().getBooleanExtra("isServer", false);
    }

    public void init(){

    }

}
