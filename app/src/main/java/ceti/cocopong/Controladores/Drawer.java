package ceti.cocopong.Controladores;

import android.graphics.Canvas;
import android.graphics.Paint;

import ceti.cocopong.Entidades.Paleta;
import ceti.cocopong.Entidades.Pelota;

/**
 * Created by law on 11/06/16.
 */
public class Drawer {

    private Paint generalPaint;
    private Pelota pelota;
    private Paleta paleta;

    public Drawer(Pelota pelota, Paleta paleta) {
        this.pelota = pelota;
        this.paleta = paleta;
    }

    public void draw(Canvas canvas){
        canvas.drawRGB(0,0,0);
        canvas.drawRect(paleta.getPaleta(),generalPaint);
        canvas.drawCircle(pelota.getX()+pelota.getWidth()/2,pelota.getY()+pelota.getHeight()/2,pelota.getWidth()/2,generalPaint);
    }
}
