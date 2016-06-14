package ceti.cocopong.Entidades;

import android.graphics.RectF;

/**
 * Created by law on 11/06/16.
 */
public class Paleta {

    private RectF paleta;
    private int speed;

    public void setBounds(int x, int y, int width, int height) {
        paleta = new RectF(x,y,x+width,y+height);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public RectF getPaleta() {
        return paleta;
    }

    public void changePos(int x, int y){
        int width;
        int height;
        width = (int) paleta.width();
        height = (int) paleta.height();
        paleta.set(x,y,x+width, y+height);
    }

    public void move(int movement, int gameWidth){
        int width = (int) paleta.width();
        paleta.left+=movement*speed;
        paleta.right+=movement*speed;
        if(movement<0 && (paleta.left)<=0){
            paleta.left=0;
            paleta.right=width;
        }
        else if(movement>0 && (paleta.right)>=gameWidth){
            paleta.right=gameWidth;
            paleta.left=gameWidth-width;
        }
    }

    public int getX(){
        return (int) paleta.left;
    }

    public int getY(){
        return (int) paleta.top;
    }

    public int getWidth(){
        return (int) paleta.width();
    }

    public int getHeight(){
        return  (int) paleta.height();
    }

}
