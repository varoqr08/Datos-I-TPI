/**
 * Created by Gabriel on 3/20/2017.
 */
public class LinkList <T>  extends Link {
    public Link firstLink;
    private int size;
     LinkList(){
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

    public void insertFirstLink(Bala cantidad){
        Link newLink = new Link(cantidad);
        newLink.setNext(firstLink);
        firstLink = newLink;
        size +=1;
    }
    public void insertLink(Bala cantidad,int contador){
        Link LinkInsertar = new Link(cantidad);
        Link LinkActual = firstLink;
        Link LinkPrevio = LinkActual;
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
        Link linkReference = firstLink;

        if(!isEmpty()) {
            firstLink = firstLink.getNext();
            size-=1;
        }else{
        }

    }
    public void removesEspecifico(T cantidad){
        Link linkActual = firstLink;
        Link linkPrevio = firstLink;
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
            Link linkActual = firstLink;
            Link linkPrevio = firstLink;
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
    public Link getEspecifico(int posicion){
        Link linkActual = firstLink;
        int x = 0;
        while(x!=posicion){
            linkActual= linkActual.getNext();
            x+=1;
        }
        return linkActual;
    }
    public void display(){
        Link theLink =  firstLink;
        while(theLink != null){
            theLink.display();
            System.out.println("Next Link: " + theLink.getNext());
            theLink = theLink.getNext();
            System.out.println();
        }
    }
}
