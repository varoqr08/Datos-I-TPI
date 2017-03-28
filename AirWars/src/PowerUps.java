/**
 * Created by Gabriel on 3/27/2017.
 */
public class PowerUps {
    private int PosX;
    private  int  PosY;
    private  int Tipo;

    PowerUps(int PosX,int PosY){
        this.PosX = PosX;
        this.PosY = PosY;
    }

    public int getPosX() {
        return PosX;
    }

    public int getPosY() {
        return PosY;
    }

    public int getTipo() {
        return Tipo;
    }

    public void setPosX(int posX) {
        PosX = posX;
    }

    public void setPosY(int posY) {
        PosY = posY;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }
}
