import java.util.Random;

/**
 * Created by Gabriel on 4/4/2017.
 */
public class Torre {
    private int tipo;//1=torre misiles 2= torre normal
    private int posX;
    private int posY;
    public Torre(){
        Random random = new Random();
        this.tipo = 1+random.nextInt(2);
        this.posY = 0;
        this.posX = 20+random.nextInt(520);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getTipo() {
        return tipo;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
