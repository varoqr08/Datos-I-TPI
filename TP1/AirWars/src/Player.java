/**
 * Created by Gabriel on 3/21/2017.
 */
public class Player {
    private  int PosX;
    private  int PosY;
    private int vidas;

    public Player(){
        PosX = 250;
        PosY = 410;
        vidas = 3;
    }

    public int getPosY() {
        return PosY;
    }

    public void setPosY(int posY) {
        PosY = posY;
    }

    public void setPosX(int posX) {
        PosX = posX;
    }

    public int getPosX() {
        return PosX;
    }
}
