package ceti.cocopong.Controladores;

import ceti.cocopong.Entidades.Paleta;
import ceti.cocopong.Entidades.Pelota;
import ceti.cocopong.Vistas.PongView;

/**
 * Created by law on 12/06/16.
 */
public class GameLooper extends Thread {

    private PongView view;
    private Pelota pelota;
    private Paleta paleta;
    private SensorPong sensor;
    private boolean running;

    public GameLooper(PongView view, Pelota pelota, Paleta paleta) {
        this.view = view;
        this.pelota = pelota;
        this.paleta = paleta;
        sensor = new SensorPong();
    }

    @Override
    public void run() {
        while(running){
            if(pelota.isVisible()){

            }
            int movement;
            if((movement = sensor.getInclinacion())!=0)
                paleta.move(movement, view.getWidth());
            view.draw();
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
