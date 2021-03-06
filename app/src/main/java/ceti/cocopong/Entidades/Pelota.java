package ceti.cocopong.Entidades;

/**
 * Created by law on 11/06/16.
 */
public class Pelota {

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;
    private int dx;
    private int dy;

    public Pelota() {}

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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int rebotaPelota(int gameWidth, int gameHeight, Paleta paleta) {
        if((y + height) >= paleta.getY()) {
            if(x >= paleta.getX() && (x + width) <= (paleta.getX() + paleta.getWidth())) {
                dy *= -1;
                y = paleta.getY()-height;
            }
        }
        else {
            if (((x + width) >= gameWidth && dx>0) || (x <= 0 && dx<0)) {
                dx *= -1;
                if (x < 0)
                    x = 0;
                if (x + width > gameWidth)
                    x = gameWidth - this.width;
            }
            if ( y <= 0 && dy<0) {
                y = 0;
                return 1;               //pasó al otro lado
            }
        }
        if((y + height) >= gameHeight && dy>0){
            return 2;
        }
        return 0;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void cambiaPosicion(){
        x += dx;
        y += dy;
    }
}
