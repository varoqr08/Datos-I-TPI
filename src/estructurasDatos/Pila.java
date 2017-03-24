package estructurasDatos;

public class Pila <T> {  //Clase que implementa la estructura de un Pila, a la cual se le asignan valores que se  ejecutan en función a First In/Last Out
	private Nodo<T> tope;
	public Nodo<T> getTope() {
		return tope;
	}
	public void setTope(Nodo<T> tope) {
		this.tope = tope;
	}
	public boolean esVacia(){
		return tope==null;
	}
	public void push (T dato){
		push (new Nodo<T>(dato));
	}
	public void push(Nodo <T> nodo){
		if (esVacia()){
			setTope(nodo);
		}
		else{
			nodo.setSiguiente(tope);			
			tope=nodo;			
		}		
	}
	//mueve un elemento una posición hacia afuera de la pila
	public void moverHaciaAfuera(T dato){
		Pila <T> pilaTemp=new Pila<T>();
		Nodo<T>aux=null;
		if (dato!=getTope().getDato()){		
			while (!esVacia()){
				if (dato==getTope().getDato()){
					aux=getTope();
					pop();
					break;
				}
				else{
					
					pilaTemp.push(getTope().getDato());
					pop();
				}
				
			
			}	
			
			
			int contador=0;
			while (!pilaTemp.esVacia()){
				if (contador==0){
					push(pilaTemp.getTope().getDato());
					push(aux.getDato());							
				}
				else{
					push(pilaTemp.getTope().getDato());
				}
				
				pilaTemp.pop();	
				contador++;
			}
		}
		
		
	}
	public void moverHaciaAdentro(T dato){  //Método que envía el dato evaluado hacia adentro de la lista
		Pila <T> pilaTemp=new Pila<T>();
		Nodo<T>aux=null;
		while (!esVacia()){
				if (dato==getTope().getDato()){
					aux=getTope();
					pop();
					if (aux.getSiguiente()!=null){
						pilaTemp.push(getTope().getDato());
						pilaTemp.push(aux.getDato());
						pop();
					}
					else{
						pilaTemp.push(aux);
					}
					break;
				}
				else{
					
					pilaTemp.push(getTope().getDato());
					pop();
				}
				
			
			}	
			
			
			
			while (!pilaTemp.esVacia()){
				push(pilaTemp.getTope().getDato());
				pilaTemp.pop();	
				
			}
		
		
		
	}
	public void pop(){
		if (!esVacia()){			
			tope=tope.getSiguiente();
			
		}
		else{
			System.err.println("No se pudo eliminar, ya que la pila está vacía");
		}
	}
	public void modificarTope(Nodo <T>Nodo){
		modificarTope(Nodo.getDato());
	}
	public void modificarTope(T dato){
		if (!esVacia()){
			tope.setDato(dato);		
		}
		else{
			System.err.println("No se pudo modificar, ya que la pila está vacía");
		}
	}
	
   public void imprimir(){
	   //Método que imprime la lista con sus elementos
		
		if (!esVacia()){			
			Nodo <T> aux=tope;			
			String listaImpresa="[";
			
			while(aux!=null){
				if(aux.getSiguiente()!=null){
					listaImpresa+=aux.getDato()+",";					
				}	
				else{
					listaImpresa+=aux.getDato();
					
				}
				aux=aux.getSiguiente();	
			}
			
			System.out.println(listaImpresa+"]");
		}
		else{
			System.out.println("[]");
		}
	}
	
	
}
