/**
 * Created by Gabriel on 4/4/2017.
 */
public class ListaTorres {
        public LinkTorres firstLink;
        private int size;
        ListaTorres(){
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

    public void insertFirstLink(Torre cantidad){
        LinkTorres newLink = new LinkTorres(cantidad);
        newLink.setNext(firstLink);
        firstLink = newLink;
        size +=1;
    }
    public void insertLink(Torre cantidad,int contador){
        LinkTorres LinkInsertar = new LinkTorres(cantidad);
        LinkTorres LinkActual = firstLink;
        LinkTorres LinkPrevio = LinkActual;
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
        LinkTorres linkReference = firstLink;

        if(!isEmpty()) {
            firstLink = firstLink.getNext();
            size-=1;
        }else{
        }

    }
    public void removesEspecifico(Torre cantidad){
        LinkTorres linkActual = firstLink;
        LinkTorres linkPrevio = firstLink;
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
            LinkTorres linkActual = firstLink;
            LinkTorres linkPrevio = firstLink;
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
    public LinkTorres getEspecifico(int posicion){
        LinkTorres linkActual = firstLink;
        int x = 0;
        while(x!=posicion){
            linkActual= linkActual.getNext();
            x+=1;
        }
        return linkActual;
    }
    public void display(){
        LinkTorres theLink =  firstLink;
        while(theLink != null){
            theLink.display();
            System.out.println("Next Link: " + theLink.getNext());
            theLink = theLink.getNext();
            System.out.println();
        }
    }
}
