/**
 * Created by Gabriel on 3/22/2017.
 */
public class Enemigos {
    private int PosX;
    private  int PosY;
    private int daño;

    public Enemigos(){
        PosX = 100;
        PosY = 0;
    }

    public int getPosX() {
        return PosX;
    }

    public void setPosX(int posX) {
        PosX = posX;
    }

    public int getPosY() {
        return PosY;
    }

    public void setPosY(int posY) {
        PosY = posY;
    }

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }
}
