import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Gabriel on 3/20/2017.
 */
public class Mapa extends JPanel implements ActionListener,KeyListener{
    Timer timer = new Timer(5,this);//un timer para comenzar el juego
    Timer timeEnemigos = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            condicionEnemigos = true;
        }
    });//timer para que aparezca enemgios
    Timer timeBalas = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            balasEnemigos = true;
        }
    });//timer para que enemigos disparen
    Timer timeMov = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            movEnemigos = true;
        }
    });//timer para que enemigos se muevan
    Timer timeAlimentarCola = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            alimentarCola = true;
        }
    });//timer para que enemigos se muevan
    Timer timeChoqueEnemigos = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            choqueEnemigos = true;
        }
    });
    Player Jugador = new Player();//creacion de jugador
    int cambX = 0, cambY = 0, velBalas = -3,cantBalas=0, cantBalasEnemigo = 0, cantEnemigosTotal =0, contadorCantEnemigos = 0;//valores de cambio de jugador y cantidad de balas de enemigos o jugadores
    Boolean condicionBalas = false, MovBala = false, condicionEnemigos = false,balasEnemigos = false, movEnemigos = false, alimentarCola = false,choqueEnemigos =false;//balas,movimient balas,creacion enemigos,creacion balas enemigos,moviemiento de enemigos
    LinkList<Bala> listaBalas = new LinkList();//lista de balas
    Cola colaEnemigos = new Cola();//cola de enemigos activos
    Cola colaEspera  = new Cola();//cola de enemigos que no han entrado al juego
    public Mapa(int cantEnenemigos){
        cantEnemigosTotal = cantEnenemigos;
        int x=0;
        Enemigos enemigo = null;
        while(x!=cantEnenemigos){
            enemigo = new Enemigos();
            colaEspera.encolar(enemigo);
            x+=1;
        }
        //la siguientes 4 lineas son default de ejecucion del mapa
        timer.start();
        timeEnemigos.start();//timer para que aparezcan los enemigos
        timeAlimentarCola.start();
        timeChoqueEnemigos.start();
        addKeyListener(this);
        setFocusable(true);//activa el keyListener
        setFocusTraversalKeysEnabled(false);//no se usara shift o tab por eso es false
    }


    //INICION LOGICA PRINCIPAL DEL JUEGO
    public void paintComponent(Graphics g){
        super.paintComponent(g);//Paint sale de JPanel esto se asegura que salga igual en cualquier computadora
        g.setColor(Color.RED);//Color del jugador
        g.fillRect(Jugador.getPosX(),Jugador.getPosY(),50,30);//primer dato cantidad de pixeles que se mueve a la Derecha segundo dato cantidad de pixeles que se mueve  abajo los ultmos dos numeros son las dimensiones del cuadrado
        g.setColor(Color.GRAY);//Barra de vida
        g.fillRect(0,461,Jugador.getVidas(),30);//posicion de la barra de vida

        //LOGICA ALIMENTAR COLA ACTIVA DE ENEMIGOS
        if(alimentarCola==true){
            if(contadorCantEnemigos!=cantEnemigosTotal){
                colaEnemigos.encolar(colaEspera.getInicio());
                alimentarCola = false;
                timeAlimentarCola.start();
                contadorCantEnemigos+=1;
            }
        }

        //FIN LOGICA ALIMENTAR COLA ACTIVA DE ENEMIGOS

        //LOGICA ENEMIGOS
        if(condicionEnemigos == true){
            timeEnemigos.stop();
            g.setColor(Color.BLUE);//colora enemigos

            //LOGICA DIBUJAR ENEMGIOS
            int sizeColaEnemigos = colaEnemigos.getSize();
            int contador = 1;
            Nodo enemigoActual = new Nodo();
            while(contador<=sizeColaEnemigos){
                enemigoActual = colaEnemigos.getInicio();//guarda en la variable el enemigo actual
                g.fillRect(enemigoActual.getDato().getPosX(),enemigoActual.getDato().getPosY(),50,30);//posicion enemigos
                colaEnemigos.encolar(enemigoActual);//se vuelve a meter a el enemigo en la ultima posicion
                contador+=1;
            }

            //FIN LOGICA DIBUJAR ENEMIGOS

            timeMov.start();//comienza para movimiento enemigos
            //LOGICA MOVIMIENTO ENEMIGOS

            if(movEnemigos == true){
                contador = 1;
                while(contador<=sizeColaEnemigos){
                    enemigoActual = colaEnemigos.getInicio();
                    enemigoActual.getDato().setPosY( enemigoActual.getDato().getPosY()+1);
                    colaEnemigos.encolar(enemigoActual);
                    contador+=1;
                }
            }
            //FIN LOGICA MOVIMIENTO ENEMIGOS

            timeBalas.start();//comienza timer para que enemgios dispare
            if(balasEnemigos == true){
                if(cantBalasEnemigo<1){//cantidad de balas de enemigos que pueden estar en la pantalla
                    contador = 1;
                    while(contador<=sizeColaEnemigos){
                        Bala bala = new Bala();//crea una bala
                        listaBalas.insertFirstLink(bala);//se inserta a lista la bala
                        listaBalas.getEspecifico(0).setAfiliacion(0);
                        enemigoActual = colaEnemigos.getInicio();
                        int x = enemigoActual.getDato().getPosX();
                        int y =enemigoActual.getDato().getPosY();
                        colaEnemigos.encolar(enemigoActual);
                        listaBalas.getEspecifico(0).setPosX(x+20);
                        listaBalas.getEspecifico(0).setPosY(y+30);
                        g.fillOval( listaBalas.getEspecifico(0).getPosX(),listaBalas.getEspecifico(0).getPosY(),10,10);
                        MovBala = true;
                        condicionBalas =false;
                        balasEnemigos = false;
                        cantBalasEnemigo+=1;
                        contador+=1;
                    }
                }
            }

        }
        //FIN LOGICA ENEMIGOS
        //LOGICA GENERACION BALAS JUGADOR
        if(condicionBalas == true){
            int x = Jugador.getPosX();
            int y = Jugador.getPosY();
            listaBalas.getEspecifico(0).setPosX(x+20);
            listaBalas.getEspecifico(0).setPosY(y-15);
            g.fillOval( listaBalas.getEspecifico(0).getPosX(),listaBalas.getEspecifico(0).getPosY(),10,10);
            condicionBalas = false;
            MovBala = true;
        }
        //FIN LOGCA GENERACION BALAS ALIADO

        //LOGICA BALAS
        if(MovBala == true){
            g.setColor(Color.RED);
            int x = 0;//x con el que se recorre lista
            while(x!=listaBalas.getSize()){// este while existe para que se puedan crear mas de 1 bala al mismo tiempo ya qu recorre una lista de balas
                if (listaBalas.getEspecifico(x).getAfiliacion()==1){//si la bala es aliada
                    g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),10,10);
                    listaBalas.getEspecifico(x).setPosY(listaBalas.getEspecifico(x).getPosY()+velBalas);
                    //LOGICA COLISION BALAS CON ENEMIGOS
                    int contador = 1;
                    int sizeColaEnemigos = colaEnemigos.getSize();
                    while(contador<=sizeColaEnemigos){
                        Nodo enemigoActual = colaEnemigos.getInicio();
                        if(listaBalas.getEspecifico(x).getPosY()<(enemigoActual.getDato().getPosY()+30)&&listaBalas.getEspecifico(x).getPosY()>(enemigoActual.getDato().getPosY())&&listaBalas.getEspecifico(x).getPosX()<(enemigoActual.getDato().getPosX()+50)&&(listaBalas.getEspecifico(x).getPosX()>enemigoActual.getDato().getPosX())){
                            listaBalas.getEspecifico(x).setPosX(-10);//mueve la bala out of bounds en un lugar no visible que no afecta
                            listaBalas.getEspecifico(x).setPosY(-10);//mueve la bala out of bounds
                            g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),0,0);
                            cantBalas -=1;
                            //LOGICA CALCULO HP ENEMGIOS
                            if(enemigoActual.getDato().getHP()>1){
                                enemigoActual.getDato().setHP(enemigoActual.getDato().getHP()-1);
                            }else{
                                enemigoActual = null;
                            }
                            //FIN LOGICA CALCULO HP ENEMIGOS
                        }
                        colaEnemigos.encolar(enemigoActual);
                        contador+=1;
                    }
                    //FIN LOGICA COLISION BALAS CON ENEMIGOS
                    //LOGICA BALA ALIADA FUERA DE BORDES
                    if(listaBalas.getEspecifico(x).getPosY()> 0&& listaBalas.getEspecifico(x).getPosY()< 10){//revisa si las balas tocan el borde de arriba de la pantalla
                        listaBalas.getEspecifico(x).setPosX(-10);//mueve la bala out of bounds
                        listaBalas.getEspecifico(x).setPosY(-10);//mueve la bala out of bounds
                        g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),0,0);
                        cantBalas -=1;
                    }
                    //FIN LOGICA BALA FUERA DE BORDES
                }
                if(listaBalas.getEspecifico(x).getAfiliacion()==0){//si bala es enemiga
                    g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),10,10);
                    listaBalas.getEspecifico(x).setPosY(listaBalas.getEspecifico(x).getPosY()-velBalas);
                    //LOGICA BALA CHOCA CONTRA JUGADOR
                    if(listaBalas.getEspecifico(x).getPosY()<(Jugador.getPosY()+30)&&listaBalas.getEspecifico(x).getPosY()>(Jugador.getPosY())&&listaBalas.getEspecifico(x).getPosX()<(Jugador.getPosX()+50)&&(listaBalas.getEspecifico(x).getPosX()>Jugador.getPosX())){
                        Jugador.setVidas(Jugador.getVidas()-20);
                        listaBalas.getEspecifico(x).setPosX(-10);//mueve la bala out of bounds
                        listaBalas.getEspecifico(x).setPosY(-10);//mueve la bala out of bounds
                        g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),0,0);
                    }
                    //FIN LOGICA BALA CHOCA CONTRA JUGADOR
                    cantBalasEnemigo-=1;
                    timeBalas.start();
                }
                x+=1;
                }
        }
        //FIN LOGICA BALAS
        //LOGICA CHOQUE ENTRE NAVES
        if(choqueEnemigos == true && colaEnemigos.esVacia() != true){
            Nodo enemigoActual = colaEnemigos.getInicio();
            if((Jugador.getPosX()>enemigoActual.getDato().getPosX()&&Jugador.getPosX()<(enemigoActual.getDato().getPosX()+50)) || ((Jugador.getPosX()+50)>enemigoActual.getDato().getPosX()&&(Jugador.getPosX()+50)<(enemigoActual.getDato().getPosX()+50))){
                if((Jugador.getPosY()-15)>(enemigoActual.getDato().getPosY()-15)&&(Jugador.getPosY()-15)<(enemigoActual.getDato().getPosY()+15)||(Jugador.getPosY()+15)>(enemigoActual.getDato().getPosY()-15)&&(Jugador.getPosY()+15)<(enemigoActual.getDato().getPosY()+15)){
                    Jugador.setVidas(Jugador.getVidas()-20);
                    enemigoActual = null;
                }
            }
            colaEnemigos.encolar(enemigoActual);
        }
        //FIN LOGICA CHOQUE ENTRE NAVES
    }

    //LIMITES Y MOVIMIENTO DE JUGADOR
    public void actionPerformed(ActionEvent e){
        if(Jugador.getPosX()<0){//cuando salga del borde del la ventana
            cambX = 0;// se resetea el cambio de x
            Jugador.setPosX(0);//se reposiciona x en cero ya que sin esto cambX no puede cambiar
        }
        if(Jugador.getPosX()>585){
            cambX = 0;
            Jugador.setPosX(585);
        }
        if(Jugador.getPosY()<0){
            cambY = 0;
            Jugador.setPosY(0);
        }
        if (Jugador.getPosY()>410){
            cambY = 0;
            Jugador.setPosY(410);
        }
        Jugador.setPosX(Jugador.getPosX()+cambX);//cambia el valor de x en cantidad "cambX" cada vez que se toca el teclado
        Jugador.setPosY(Jugador.getPosY()+cambY);//igual pero para y
        repaint();//vuelve a dibujar el rectangulo
    }
    //FIN DE MOV Y LIMITES DE JUGADOR

    //INPUT DE USUARIO
    public void keyPressed(KeyEvent e){//consigue el valor de las teclas del teclado
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT){//Si toca izquierda
                cambX = -2;//Se usa -1 ya que se movera un pixel a la izquierda
                cambY = 0;//no hay cambio en y
            }
        if (tecla == KeyEvent.VK_UP){
                cambX = 0;
                cambY = -2;//Para arriba en y es negativo
            }
        if (tecla == KeyEvent.VK_RIGHT){
                cambX = 2;
                cambY = 0;
            }
        if (tecla == KeyEvent.VK_DOWN){
                cambX = 0;
                cambY = 2;
            }
        if (tecla == KeyEvent.VK_SPACE){
            cantBalas += 1;
            if(cantBalas<5){
                Bala bala = new Bala();// se crea una bala
                listaBalas.insertFirstLink(bala);// se adhiere la bala a la lista de balas
                listaBalas.getEspecifico(0).setAfiliacion(1);
                condicionBalas = true;

            }
            else{
                cantBalas-=1;
            }
        }
        }
    //FIN DE INPUT DE USUARIO

    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){//Metodo necesario para usar keylistener maneja la logica de soltar la tecla
        cambX = 0;
        cambY = 0;
    }
    //FIN LOGICA PRINCIPAL DE JUEGO

    //MAIN DE JUEGO
    public static void main(String[] args) {
        Mapa mapa = new Mapa(3);
        JPanel panel1 = new JPanel();
        JLabel jl = new JLabel("Vidas");
        panel1.add(jl);
        JFrame ControladorJF = new JFrame();
        ControladorJF.setTitle("Air Wars");
        ControladorJF.setSize(640,520);//Tamaño de la ventana
        jl.setVisible(true);
        panel1.setVisible(true);
        ControladorJF.setVisible(true);
        ControladorJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Operacion cuando se cierra ventana
        ControladorJF.setResizable(false);
        ControladorJF.add(panel1);
        ControladorJF.add(mapa);//Se adhiere el controlador a la clase mapa para que consiga las propiedads establecidas

    }
}
