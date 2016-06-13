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

    }

    @Override
    public void run() {
        while(running){
            if(pelota.isVisible()){
                pelota.cambiaPosicion();
                switch(pelota.rebotaPelota(view.getWidth(), view.getHeight(), paleta)){
                    case 0:
                        view.sendXPelota(pelota.getX()/view.getWidth());
                        break;
                    case 1:
                        view.sendPelota();
                        pelota.setVisible(false);
                        break;
                    case 2:
                        view.setPuntos(view.getPuntos()+1);
                        view.sendPunto();
                        view.initPelota(true);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
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

    public void setSensor(SensorPong sensor) {
        this.sensor = sensor;
    }
}
