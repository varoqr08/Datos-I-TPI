/**
 * Created by Gabriel on 3/25/2017.
 */
public class Cola <T> {   //Implementación de la estructura de cola la cual almacena datos y los evaulúa mediante la función FIFO

    /**
     * Nodo<T> nodo del que está compuesta la lista
     */
    public Cola(){
        this.size = 0;
    }
    private Nodo inicio;
    private int size;

    public Nodo getInicio() {
        Nodo x = inicio;
        desencolar();
        x.setSiguiente(null);
        return x;
    }

    /**
     * Método que verifica si la lista está vacía
     * @return true si no hay elementos
     */
    public boolean esVacia(){   //Método que evalúa si la lista está vacía y respecto a esto asigna un valor nulo al inicio
        return inicio==null;
    }

    /**
     * Método que crea un nodo con un dato
     * @param dato dato del que está compuesto el nodo
     */
    public void encolar (Enemigos dato){
        encolar (new Nodo(dato));
    }

    /**
     * Método que agrega un nodo a la lista
     * @param nodo nodo que se agrega a la lista
     */
    public void encolar(Nodo  nodo){    //Método que va asignando valores a la cola

        if(nodo != null){
            if (esVacia()){   //Si es vacía, se le asigna al primer nodo como el inicial
                inicio=nodo;
                size+=1;
            } else {    //En caso de que la lista ya posea valores, se le asigna al nuevo nodo como el siguiente valor de la lista

                Nodo aux=inicio;

                while(aux.getSiguiente()!=null){
                    aux=aux.getSiguiente();
                }
                aux.setSiguiente(nodo);
                size+=1;
            }
        }

    }

    /**
     * Método que elimina el primer nodo de la lista
     */
    public void desencolar(){  //Método que permite "desencolar" la lista y permite extraer el primer valor de esta
        if (!esVacia()){
            inicio=inicio.getSiguiente();
            size-=1;
        }
        else{
            System.err.println("No se pudo eliminar, ya que la cola está vacía");
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}