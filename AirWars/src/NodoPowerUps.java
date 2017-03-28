/**
 * Created by Gabriel on 3/27/2017.
 */
public class NodoPowerUps {
    private PowerUps dato;
    private NodoPowerUps   siguiente;

    public NodoPowerUps(){
        this.dato=null;
        this.siguiente=null;
    }

    public NodoPowerUps(PowerUps dato){
        this.dato=dato;
        this.siguiente=null;
    }

    public  PowerUps getDato() { //Retorna el dato que el nodo posee
        return dato;
    }

    public void setDato(PowerUps dato) {  //Asigna al nodo el dato que este representará
        this.dato = dato;
    }

    public NodoPowerUps getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPowerUps siguiente) {
        this.siguiente = siguiente;
    }




}

