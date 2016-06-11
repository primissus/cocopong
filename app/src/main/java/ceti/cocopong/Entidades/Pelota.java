package ceti.cocopong.Entidades;

/**
 * Created by law on 11/06/16.
 */
public class Pelota {

    private int x;
    private int y;
    private int width;
    private int height;

    public Pelota(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void rebotaPelota(){
        int Dx = 1;
        int Dy = 1;

        while(true){
            x+=Dx;
            y+=Dy;

            if ((x + width) >= this.getWidth())
                Dx*= -1;

            if ((y+height) >= this.getHeight())
                Dy*= -1;
        }
    }
}
