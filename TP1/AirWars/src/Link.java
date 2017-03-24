/**
 * Created by Gabriel on 3/20/2017.
 */
public class Link extends  Bala {
    private Bala informacion;

    private Link next;

    public Link() {

    }


    public Bala getInformacion() {
        return this.informacion;
    }

    public Link getNext() {
        return this.next;
    }
    public void setNext(Link L){
        this.next = L;
    }
    public void setInformacion(Bala informacion){
        this.informacion =informacion ;
    }

    public Link( Bala informacion){

        this.informacion = informacion;

    }
    public void display(){
        System.out.println(informacion);
    }

    public static void main(String[] args) {

    }

}
