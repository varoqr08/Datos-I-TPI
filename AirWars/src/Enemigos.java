import java.util.Random;

/**
 * Created by Gabriel on 3/22/2017.
 */
public class Enemigos {
    private int PosX;
    private  int PosY;
    private int daño;
    private int tipo;
    private int HP;

    public Enemigos(){
        Random random = new Random();
        PosX = 1+random.nextInt(585);
        PosY = 0;
        HP = 2;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}
