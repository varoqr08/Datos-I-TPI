package estructurasDatos;



public class ListaSimple<T>  {   //Clase que implementa una lista simple 

	private Nodo<T> inicio;
	
	public ListaSimple(){   //Declara el inicio de la lista como nulo
		inicio=null;
	}

	public Nodo<T> getInicio() {   //Método que retorna el inicio de la lista
		return inicio;
	}

	public void setInicio(Nodo<T> inicio) {   //Método que establece el nuevo inicio de la lista
		this.inicio = inicio;
	}
	
	public boolean esVacia(){    //Método que evalúa si la lista es vacía y en caso de serlo, iguala el inicio de esta como nulo
		return inicio==null;
	}
	public void insertarInicio(T dato){  
		
		Nodo <T> nodo=new Nodo<T>(dato);
		insertarInicio(nodo);
	}
	public void insertarInicio(Nodo<T> nodo){  //Método que decide si asigna el nuevo nodo como el inicial o solo como el siguiente, dependiendo de si la lista es vacía o no.
		
		if (esVacia()){
			setInicio(nodo);


		}
		else{
			nodo.setSiguiente(inicio);			
			inicio=nodo;
			
		}
		
	}
	public void insertarFinal(Nodo<T> nodo){  //Método que apunte al último nodo de la lista
		if (esVacia()){
			setInicio(nodo);
		}
		else{
			Nodo<T> aux=inicio;
			while(aux.getSiguiente()!=null){				
				aux=aux.getSiguiente();					
			}
			aux.setSiguiente(nodo);
		}
		
	}
	public void insertarFinal(T dato){
		Nodo <T> nodo=new Nodo<T>(dato);
		insertarFinal(nodo);
		
	}
	
	
	public int tamano(){   //Se evalúa el tamaño de la lista
		int tamano=0;
		if (!esVacia()){		
			Nodo<T> aux=inicio;
			for(;aux.getSiguiente()!=null;tamano++){
				aux=aux.getSiguiente();
			}
			tamano++;
		}
		return tamano;
	}
	public void insertarPosicion(T dato,int index){   
		Nodo <T> nodo=new Nodo<T>(dato);
		insertarPosicion(nodo,index);
		
	}
	public void insertarPosicion(Nodo<T> nodo,int index){
		if (index<=tamano() && index>=0){
			if (index==0){
				insertarInicio(nodo);
			}
			else if (tamano()==index){
				insertarFinal(nodo);				
			}
			else{
				Nodo <T> aux=inicio;
				Nodo <T>temp=inicio;
				int contador=0;
				while (contador!=index){
					if (contador==index-1){
						temp=aux;
					}
					aux=aux.getSiguiente();
					contador++;
				}
				temp.setSiguiente(nodo);
				nodo.setSiguiente(aux);
				
			}
			
		}
		else if (index<0){
			System.err.println("No se pudo insertar, el índice debe ser mayor o igual que cero");
		}
		
		else{
			System.err.println("No se pudo insertar, el índice excede el tamaño de la lista");
		}
	}
	public void eliminar(T dato){
		eliminar(buscar(dato));		
	}
	public void eliminar(int index){   //Método que permite eliminar datos dentro de la lista
		if (index<tamano() && index>=0){
			if (index==0){
				inicio=inicio.getSiguiente();
			}
			else if(tamano()-1==index){
				Nodo <T> aux=inicio;
				int contador=0;
				while(contador<tamano()-2){
					aux=aux.getSiguiente();
					contador++;
				}
				aux.setSiguiente(null);
			}
			else{
				Nodo <T> aux=inicio;
				Nodo <T>temp=inicio;
				int contador=0;
				while (contador!=index){
					if (contador==index-1){
						temp=aux;
					}
					aux=aux.getSiguiente();
					contador++;
				}
				temp.setSiguiente(aux.getSiguiente());
				aux.setSiguiente(null);
				
			}
			
		}
		else if (index<0){
			System.err.println("No se pudo eliminar, el índice debe ser mayor o igual que cero");
		}
		
		else{
			System.err.println("No se pudo eliminar, el índice excede el tamaño de la lista");
		}
		
	}
	public void modificarPosicion(int index, Nodo <T>nodo){
		modificarPosicion(index, nodo.getDato());		
	}
	public void modificarPosicion(int index,T dato){   //Permite modificar y variar la posición de un dato dentro de la lista
		if (index<tamano()){
			Nodo <T>aux=inicio;
			for(int contador=0;contador!=index;contador++){
				aux=aux.getSiguiente();
			}
			aux.setDato(dato);
		}
		else{
			System.err.println("No se pudo modificar, el índice excede el tamaño de la lista");
		}
		
	}
	public int buscar(T dato){
		Nodo <T>aux=inicio;		
		for(int i=0;aux!=null;i++){
			if (dato==aux.getDato()){
				return i;
			}
			aux=aux.getSiguiente();
		}
		return -1;
		
	}
	
	public void imprimir(){  //Retorna o imprime la lista simple
		

		if (!esVacia()){			
			Nodo <T> aux=inicio;			
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
