package ceti.cocopong.Entidades;

/**
 * Created by law on 11/06/16.
 */
public class Pelota {

    private int x;
    private int y;
    private int width;
    private int height;
    private int dx;
    private int dy;

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

    public void rebotaPelota(int gameWidth, int gameHeight) {
        if ((x + width) >= gameWidth || x <= 0) {
            dx += -1;
            if (x < 0)
                x = 0;
            if (x + width > gameWidth)
                x = gameWidth - this.height;
        }

        if ((y + height) >= this.getHeight() || y <= 0) {
            y += -1;
            if (y < 0) {
                y = 0;
            }
            if (y + width > gameHeight) {
                y = gameHeight - height;
            }
        }
    }
}
