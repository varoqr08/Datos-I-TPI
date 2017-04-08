import javax.swing.*;
import java.awt.*;

/**
 * Created by Gabriel on 3/20/2017.
 */
public class Bala {
    private int  posX;
    private int posY;
    private int afiliacion;//define si la bala es alidad o enemiga 1 para aliada 0 para enemiga
    private  int dañoBala;
    private int MovX; //movimiento en X para las balas
    public Bala(){
        this.posX = 0;
        this.posY = 0;
    }
    public int getPosX(){
        return this.posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getAfiliacion() {
        return afiliacion;
    }

    public void setAfiliacion(int afiliacion) {
        this.afiliacion = afiliacion;
    }

    public int getDañoBala() {
        return dañoBala;
    }

    public void setDañoBala(int dañoBala) {
        this.dañoBala = dañoBala;
    }

    public int getMovX() {
        return MovX;
    }

    public void setMovX(int movX) {
        MovX = movX;
    }
}

