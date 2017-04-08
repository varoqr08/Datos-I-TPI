/**
 * Created by Gabriel on 4/5/2017.
 */
public class ListaUsuario {
        private LinkUsuario firstLink;
        private int size;
        ListaUsuario(){
        super();
        firstLink = null;
        size = 0;

    }
    public static void main(String[] args) {
        LinkList theList = new LinkList();
    }
    public int getSize(){
        return this.size;
    }
    public boolean isEmpty(){
        return(firstLink == null);
    }

    public void insertFirstLink(int cantidad){
        LinkUsuario newLink = new LinkUsuario(cantidad);
        newLink.setNext(firstLink);
        firstLink = newLink;
        size +=1;
    }
    public void insertLink(int cantidad,int contador){
        LinkUsuario LinkInsertar = new LinkUsuario(cantidad);
        LinkUsuario LinkActual = firstLink;
        LinkUsuario LinkPrevio = LinkActual;
        int x = 0;
        while(x!=contador){
            LinkPrevio = LinkActual;
            LinkActual = LinkActual.getNext();
            x = x+1;
        }
        LinkPrevio.setNext(LinkInsertar);
        LinkInsertar.setNext(LinkActual);
        size+=1;
    }

    public void removeFirst(){
        LinkUsuario linkReference = firstLink;

        if(!isEmpty()) {
            firstLink = firstLink.getNext();
            size-=1;
        }else{
        }

    }
    public void removesEspecifico(int cantidad){
        LinkUsuario linkActual = firstLink;
        LinkUsuario linkPrevio = firstLink;
        while(linkActual.getInformacion()!=cantidad){
            linkPrevio = linkActual;
            linkActual= linkActual.getNext();
        }
        if(linkActual == firstLink){
            removeFirst();
        }else{
            linkPrevio.setNext(linkActual.getNext());
        }
    }
    public void removePosicion(int Posicion){
        if(Posicion==0){
            removeFirst();
        }
        else {
            LinkUsuario linkActual = firstLink;
            LinkUsuario linkPrevio = firstLink;
            int x= 0;
            while(x!=Posicion){
                linkPrevio = linkActual;
                linkActual= linkActual.getNext();
            }
            if(linkActual == firstLink){
                removeFirst();
            }else{
                linkPrevio.setNext(linkActual.getNext());
            }
        }
    }
    public LinkUsuario getEspecifico(int posicion){
         LinkUsuario linkActual = firstLink;
        int x = 0;
        while(x!=posicion){
            linkActual= linkActual.getNext();
            x+=1;
        }
        return linkActual;
    }
    public void display(){
        LinkUsuario theLink =  firstLink;
        while(theLink != null){
            theLink.display();
            System.out.println("Next Link: " + theLink.getNext());
            theLink = theLink.getNext();
            System.out.println();
        }
    }
}
