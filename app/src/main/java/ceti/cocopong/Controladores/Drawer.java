package ceti.cocopong.Controladores;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ceti.cocopong.Entidades.Paleta;
import ceti.cocopong.Entidades.Pelota;

/**
 * Created by law on 11/06/16.
 */
public class Drawer {

    private Paint generalPaint;
    private Paint timePaint;
    private Paint textPaint;
    private Pelota pelota;
    private Paleta paleta;

    public Drawer(Pelota pelota, Paleta paleta) {
        this.pelota = pelota;
        this.paleta = paleta;
        generalPaint = new Paint();
        generalPaint.setColor(Color.WHITE);
        generalPaint.setAntiAlias(true);
        timePaint = new Paint();
        timePaint.setColor(Color.WHITE);
        timePaint.setAntiAlias(true);
        timePaint.setTextSize(80);
        generalPaint.setColor(Color.WHITE);
        generalPaint.setAntiAlias(true);
        generalPaint.setTextSize(pelota.getHeight()*7);
        generalPaint.setTextAlign(Paint.Align.CENTER);
        textPaint = new Paint();
        textPaint.setColor(Color.YELLOW);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        textPaint.setTextSize(pelota.getWidth()/2);
    }

    public void draw(Canvas canvas){
        canvas.drawRGB(0,0,0);
        canvas.drawRect(paleta.getPaleta(),generalPaint);
        if(pelota.isVisible())
            canvas.drawCircle(pelota.getX()+pelota.getWidth()/2,pelota.getY()+pelota.getHeight()/2,pelota.getWidth()/2,generalPaint);
        else
            canvas.drawRect(pelota.getX(),0, pelota.getX()+pelota.getWidth(), pelota.getHeight()/5, generalPaint);
    }


    public void drawTime(Canvas canvas, int time){
        canvas.drawRGB(0,0,0);
        canvas.drawText(String.valueOf(time), paleta.getWidth()*5/2+timePaint.getTextSize(), pelota.getHeight(), timePaint);
    }

    public void drawPoints(Canvas canvas,int points, int openentPoints){
        String text = String.valueOf(points)+":"+String.valueOf(openentPoints);
        canvas.drawText(text,paleta.getWidth()*5, textPaint.getTextSize(), textPaint);
    }
}
