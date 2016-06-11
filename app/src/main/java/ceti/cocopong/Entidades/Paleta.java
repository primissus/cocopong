package ceti.cocopong.Entidades;

import android.graphics.RectF;

/**
 * Created by law on 11/06/16.
 */
public class Paleta {

    private RectF paleta;

    public Paleta(int x, int y, int width, int height) {
        paleta = new RectF(x,y,x+width,y+height);
    }

    public void changePos(int x, int y){
        int width;
        int height;
        width = (int) paleta.width();
        height = (int) paleta.height();
        paleta.set(x,y,x+width, y+height);
    }

    public void changeX(int x){
        int width;
        width = (int) paleta.width();
        paleta.set(x,paleta.top,x+width,paleta.bottom);
    }
}
