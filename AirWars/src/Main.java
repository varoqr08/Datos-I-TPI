import javax.swing.*;

/**
 * Created by Gabriel on 4/4/2017.
 */
public class Main {
    public static void main(String[] args) {
        String title = JOptionPane.showInputDialog(null,"Nombre  de Jugador: ");
        Mapa mapa = new Mapa(2,3,1,title);
        mapa.Comenzar(2,1,1,title);
     }
}
