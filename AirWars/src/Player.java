/**
 * Created by Gabriel on 3/21/2017.
 */
public class Player {
    private  int PosX;
    private  int PosY;
    private int vidas;
    private  int dañoJugador;

    public Player(){
        PosX = 250;
        PosY = 410;
        vidas = 60;
        dañoJugador = 1;
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

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getDañoJugador() {
        return dañoJugador;
    }

    public void setDañoJugador(int dañoJugador) {
        this.dañoJugador = dañoJugador;
    }
}
