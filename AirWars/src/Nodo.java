/**
 * Created by Gabriel on 3/25/2017.
 */
public class Nodo { // Clase que genera los nodos a los que se les asignará los valores a emplear
    private Enemigos dato;
    private Nodo  siguiente;

    public Nodo(){
        this.dato=null;
        this.siguiente=null;
    }

    public Nodo(Enemigos dato){
        this.dato=dato;
        this.siguiente=null;
    }

    public Enemigos getDato() { //Retorna el dato que el nodo posee
        return dato;
    }

    public void setDato(Enemigos dato) {  //Asigna al nodo el dato que este representará
        this.dato = dato;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }




}
