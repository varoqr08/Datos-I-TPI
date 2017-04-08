import java.util.Random;

/**
 * Created by Gabriel on 3/22/2017.
 */
public class Enemigos {
    private int PosX;
    private  int PosY;
    private int daño;
    private int tipo;//2= jet 1=Bombarderos 3=kamikaze
    private int HP;
    private int contadorMovimiento;
    private int Nivel;//variable para jefes y su dificultad
    public Enemigos(){
        Random random = new Random();
        PosX = 20+random.nextInt(520);
        PosY = 0;
        tipo = 1+random.nextInt(3);
        HP=2;
        if(tipo !=1){
            HP = 1;
        }
        contadorMovimiento = -50;
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

    public int getContadorMovimiento() {
        return contadorMovimiento;
    }

    public void setContadorMovimiento(int contadorMovimiento) {
        this.contadorMovimiento = contadorMovimiento;
    }

    public int getNivel() {
        return Nivel;
    }

    public void setNivel(int nivel) {
        Nivel = nivel;
    }
}
