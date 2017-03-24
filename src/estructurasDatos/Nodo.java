package estructurasDatos;

public class Nodo <T> { // Clase que genera los nodos a los que se les asignará los valores a emplear
	private T dato;
	private Nodo <T>  siguiente;
	
	public Nodo(){  
		this.dato=null;
		this.siguiente=null;
	}
	
	public Nodo(T dato){  
		this.dato=dato;
		this.siguiente=null;
	}

	public T getDato() { //Retorna el dato que el nodo posee
		return dato;
	}

	public void setDato(T dato) {  //Asigna al nodo el dato que este representará
		this.dato = dato;
	}

	public Nodo<T> getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Nodo<T> siguiente) {
		this.siguiente = siguiente;
	}
	
	
	

}
