/**
 * Created by Gabriel on 4/5/2017.
 */
public class LinkUsuario {
    private int informacion;

    private LinkUsuario next;

    public LinkUsuario() {

    }


    public int getInformacion() {
        return this.informacion;
    }

    public LinkUsuario getNext() {
        return this.next;
    }
    public void setNext(LinkUsuario L){
        this.next = L;
    }
    public void setInformacion(int informacion){
        this.informacion =informacion ;
    }

    public LinkUsuario( int informacion){

        this.informacion = informacion;

    }
    public void display(){
        System.out.println(informacion);
    }

    public static void main(String[] args) {

    }

}
