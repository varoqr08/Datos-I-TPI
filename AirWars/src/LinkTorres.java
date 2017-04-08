/**
 * Created by Gabriel on 4/4/2017.
 */
public class LinkTorres {
        private Torre informacion;

        private LinkTorres next;

    public LinkTorres() {

    }


    public Torre getInformacion() {
        return this.informacion;
    }

    public LinkTorres getNext() {
        return this.next;
    }
    public void setNext(LinkTorres L){
        this.next = L;
    }
    public void setInformacion(Torre informacion){
        this.informacion =informacion ;
    }

    public LinkTorres( Torre informacion){

        this.informacion = informacion;

    }
    public void display(){
        System.out.println(informacion);
    }

    public static void main(String[] args) {

    }

}
