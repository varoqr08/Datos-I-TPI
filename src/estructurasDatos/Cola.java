package estructurasDatos;

/**
 * Lista de tipo cola donde se pueden mover los elementos de arriba para abajo, agregarlos o eliminarlos
 * @param <T> tipo de dato del que se compondr� la lista
 */
public class Cola <T> {   //Implementaci�n de la estructura de cola la cual almacena datos y los evaul�a mediante la funci�n FIFO
	
	/**
	 * Nodo<T> nodo del que est� compuesta la lista
	 */
	private Nodo<T> inicio;
	
	public Nodo<T> getInicio() {
		return inicio;
	}
	
	/**
	 * M�todo que verifica si la lista est� vac�a
	 * @return true si no hay elementos
	 */
	public boolean esVacia(){   //M�todo que eval�a si la lista est� vac�a y respecto a esto asigna un valor nulo al inicio 
		return inicio==null;
	}
	
	/**
	 * M�todo que crea un nodo con un dato
	 * @param dato dato del que est� compuesto el nodo
	 */
	public void encolar (T dato){
		encolar (new Nodo<T>(dato));
	}
	
	/**
	 * M�todo que agrega un nodo a la lista
	 * @param nodo nodo que se agrega a la lista
	 */
	public void encolar(Nodo <T> nodo){    //M�todo que va asignando valores a la cola   
		
		if (esVacia()){   //Si es vac�a, se le asigna al primer nodo como el inicial 
			inicio=nodo;
		} else {    //En caso de que la lista ya posea valores, se le asigna al nuevo nodo como el siguiente valor de la lista
			
			Nodo<T> aux=inicio;
			
			while(aux.getSiguiente()!=null){				
				aux=aux.getSiguiente();					
			}
			aux.setSiguiente(nodo);			
		}		
	}
	
	/**
	 * M�todo que elimina el primer nodo de la lista
	 */
	public void desencolar(){  //M�todo que permite "desencolar" la lista y permite extraer el primer valor de esta
		if (!esVacia()){
			inicio=inicio.getSiguiente();		
		}
		else{
			System.err.println("No se pudo eliminar, ya que la cola est� vac�a");
		}
	}
}