import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * Created by Gabriel on 3/20/2017.
 */
public class Mapa extends JPanel implements ActionListener,KeyListener{
    private Timer timer = new Timer(5,this);//un timer para comenzar el juego
    private Timer timeEnemigos = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            condicionEnemigos = true;
        }
    });//timer para que aparezca enemgios
    private Timer timeBalas = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            balasEnemigos = true;
        }
    });//timer para que enemigos disparen
    private Timer timeMov = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            movEnemigos = true;
        }
    });//timer para que enemigos se muevan
    private Timer timeAlimentarCola = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            alimentarCola = true;
        }
    });//timer para que enemigos se muevan
    private Timer timeChoqueEnemigos = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            choqueEnemigos = true;
        }
    });
    private Timer timeEscudo = new Timer(5000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            condicionEscudo = false;
        }
    });
    private Player Jugador = new Player();//creacion de jugador
    private int cambX = 0, cambY = 0, velBalas = -3,cantBalas=0, cantBalasEnemigo = 0, cantEnemigosTotal =0, contadorCantEnemigos = 0,PosEnemX=0,PosEnemY=0,balasEspeciales = 0,sizePowerUps = 0, contadorCicloPowerUps=1;//valores de cambio de jugador y cantidad de balas de enemigos o jugadores
    private Boolean condicionBalas = false, MovBala = false, condicionEnemigos = false,balasEnemigos = false, movEnemigos = false, alimentarCola = false,choqueEnemigos =false,pintarPowerUps =false,condicionEscudo = false;//balas,movimient balas,creacion enemigos,creacion balas enemigos,moviemiento de enemigos
    private LinkList<Bala> listaBalas = new LinkList();//lista de balas
    private Cola colaEnemigos = new Cola();//cola de enemigos activos
    private Cola colaEspera  = new Cola();//cola de enemigos que no han entrado al juego
    private Pila pilaPowerUps = new Pila();
    private Random generador = new Random();
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
        if(condicionEscudo==true){//revisa si se tiene escudo
            g.setColor(Color.cyan);
            timeEscudo.start();
        }
        else {
            g.setColor(Color.RED);//Color del jugador
        }
        g.fillRect(Jugador.getPosX(),Jugador.getPosY(),50,30);//primer dato cantidad de pixeles que se mueve a la Derecha segundo dato cantidad de pixeles que se mueve  abajo los ultmos dos numeros son las dimensiones del cuadrado
        g.setColor(Color.PINK);//Barra de vida
        g.fillRect(0,461,Jugador.getVidas(),30);//posicion de la barra de vida
        while(contadorCicloPowerUps<=3){
            if(contadorCicloPowerUps<=sizePowerUps){
                PowerUps PU = pilaPowerUps.getTope().getDato();
                pilaPowerUps.pop();
                if(PU.getTipo()==1){
                    g.setColor(Color.CYAN);
                }
                if(PU.getTipo()==2){
                    g.setColor(Color.GREEN);
                }
                if(PU.getTipo()==3){
                    g.setColor(Color.MAGENTA);
                }
                pilaPowerUps.push(PU);
            }
            else {
                g.setColor(Color.GRAY);
            }
            g.fillRect(100+(contadorCicloPowerUps*20),461,15,30);
            contadorCicloPowerUps+=1;
        }
        contadorCicloPowerUps=1;
        //LOGICA POWERUPS
        if(pintarPowerUps==true){
            PowerUps powerUp = new PowerUps(PosEnemX,PosEnemY);
            g.setColor(Color.BLACK);
            g.fillOval(powerUp.getPosX(),powerUp.getPosY(),10,10);
            if((Jugador.getPosX()<powerUp.getPosX()&&(Jugador.getPosX()+50)>powerUp.getPosX() )){
               if((Jugador.getPosY()-15)<(powerUp.getPosY()-5)&&(Jugador.getPosY()+15)>(powerUp.getPosY()-10)){
                   if(sizePowerUps !=4){
                       pilaPowerUps.push(powerUp);
                       PosEnemY = -10;
                       PosEnemX = -10;
                       sizePowerUps+=1;
                   }
                }
            }
        }
        //FIN LOGICA POWERUPS

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
                    //LOGICA JETS
                    if(enemigoActual.getDato().getTipo()==1 || enemigoActual.getDato().getTipo()==2){
                        enemigoActual.getDato().setPosY( enemigoActual.getDato().getPosY()+1);
                        if(enemigoActual.getDato().getContadorMovimiento()<0){//Si contador menor a 0 se mueve izq
                            enemigoActual.getDato().setPosX(enemigoActual.getDato().getPosX()-enemigoActual.getDato().getTipo());
                            enemigoActual.getDato().setContadorMovimiento(enemigoActual.getDato().getContadorMovimiento()+1);
                        }
                        else if(enemigoActual.getDato().getContadorMovimiento()>=0){
                            enemigoActual.getDato().setPosX(enemigoActual.getDato().getPosX()+enemigoActual.getDato().getTipo());
                            enemigoActual.getDato().setContadorMovimiento(enemigoActual.getDato().getContadorMovimiento()+1);
                            if(enemigoActual.getDato().getContadorMovimiento()>30){
                                enemigoActual.getDato().setContadorMovimiento(-30);
                            }
                        }
                    }
                    //FIN LOGICA JETS

                    //INICIO LOGICA KAMIKAZE
                    if(enemigoActual.getDato().getTipo() ==3){
                        enemigoActual.getDato().setPosY( enemigoActual.getDato().getPosY()+1);
                        if(enemigoActual.getDato().getPosX()<Jugador.getPosX()){//ve si esta a la izquierda de jugador
                            enemigoActual.getDato().setPosX(enemigoActual.getDato().getPosX()+1);
                        }
                        if(enemigoActual.getDato().getPosX()>Jugador.getPosX()){//ve si esta a la derecha de jugador
                            enemigoActual.getDato().setPosX(enemigoActual.getDato().getPosX()-1);
                        }
                    }
                    //FIN LOGICA KAMIKAZE
                    if(enemigoActual.getDato().getPosY()>410){
                        enemigoActual.getDato().setPosY(0);
                    }
                    colaEnemigos.encolar(enemigoActual);
                    contador+=1;

                }
            }
            //FIN LOGICA MOVIMIENTO ENEMIGOS

            timeBalas.start();//comienza timer para que enemgios dispare
            //LOGICA BALAS ENEMIGOS
            if(balasEnemigos == true){
                if(cantBalasEnemigo<1){//cantidad de balas de enemigos que pueden estar en la pantalla
                    contador = 1;
                    while(contador<=sizeColaEnemigos){
                        enemigoActual = colaEnemigos.getInicio();
                        if(enemigoActual.getDato().getTipo()!=3){
                            Bala bala = new Bala();//crea una bala
                            listaBalas.insertFirstLink(bala);//se inserta a lista la bala
                            listaBalas.getEspecifico(0).setDañoBala(enemigoActual.getDato().getTipo());//le asigna la bala el tipo de nave que la disparo para calcular daño
                            listaBalas.getEspecifico(0).setAfiliacion(0);
                            int x = enemigoActual.getDato().getPosX();
                            int y =enemigoActual.getDato().getPosY();
                            listaBalas.getEspecifico(0).setPosX(x+20);
                            listaBalas.getEspecifico(0).setPosY(y+30);
                            g.fillOval( listaBalas.getEspecifico(0).getPosX(),listaBalas.getEspecifico(0).getPosY(),10,10);
                            MovBala = true;
                            condicionBalas =false;
                            balasEnemigos = false;
                            cantBalasEnemigo+=1;
                        }
                        contador+=1;
                        colaEnemigos.encolar(enemigoActual);
                    }
                }
            }
            //FIN LOGICA BALAS ENEMIGOS
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
            int x = 0;//x con el que se recorre lista
            while(x!=listaBalas.getSize()){// este while existe para que se puedan crear mas de 1 bala al mismo tiempo ya qu recorre una lista de balas
                if (listaBalas.getEspecifico(x).getAfiliacion()==1){//si la bala es aliada
                    if(listaBalas.getEspecifico(x).getDañoBala()==1){//desgina color segun power up
                        g.setColor(Color.RED);
                    }
                    if(listaBalas.getEspecifico(x).getDañoBala()==2){
                        g.setColor(Color.GREEN);
                    }
                    if(listaBalas.getEspecifico(x).getDañoBala()==3){
                        g.setColor(Color.MAGENTA);
                    }
                    g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),10,10);
                    listaBalas.getEspecifico(x).setPosY(listaBalas.getEspecifico(x).getPosY()+velBalas);
                    //LOGICA COLISION BALAS CON ENEMIGOS
                    int contador = 1;
                    int sizeColaEnemigos = colaEnemigos.getSize();
                    while(contador<=sizeColaEnemigos){
                        Nodo enemigoActual = colaEnemigos.getInicio();
                        if(listaBalas.getEspecifico(x).getPosY()<(enemigoActual.getDato().getPosY()+30)&&listaBalas.getEspecifico(x).getPosY()>(enemigoActual.getDato().getPosY())&&listaBalas.getEspecifico(x).getPosX()<(enemigoActual.getDato().getPosX()+50)&&(listaBalas.getEspecifico(x).getPosX()>enemigoActual.getDato().getPosX())){
                            if(listaBalas.getEspecifico(x).getDañoBala()!=3){//revisa si la bala es lase si es laser atraviesa enemigos
                                listaBalas.getEspecifico(x).setPosX(-10);//mueve la bala out of bounds en un lugar no visible que no afecta
                                listaBalas.getEspecifico(x).setPosY(-10);//mueve la bala out of bounds
                            }
                            g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),0,0);
                            cantBalas -=1;
                            //LOGICA CALCULO HP ENEMGIOS
                            enemigoActual.getDato().setHP(enemigoActual.getDato().getHP()-listaBalas.getEspecifico(x).getDañoBala());
                            if(enemigoActual.getDato().getHP()>0){

                            }else{
                                int valor = 1+generador.nextInt(100);
                                if(valor<=100&&valor>0){
                                    PosEnemX=enemigoActual.getDato().getPosX();
                                    PosEnemY=enemigoActual.getDato().getPosY();
                                    pintarPowerUps = true;
                                }
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
                //LOGICA BALA ENEMIGOS
                if(listaBalas.getEspecifico(x).getAfiliacion()==0){//si bala es enemiga
                    g.setColor(Color.RED);
                    g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),10,10);
                    listaBalas.getEspecifico(x).setPosY(listaBalas.getEspecifico(x).getPosY()-velBalas);
                    //LOGICA BALA CHOCA CONTRA JUGADOR
                    if(listaBalas.getEspecifico(x).getPosY()<(Jugador.getPosY()+30)&&listaBalas.getEspecifico(x).getPosY()>(Jugador.getPosY())&&listaBalas.getEspecifico(x).getPosX()<(Jugador.getPosX()+50)&&(listaBalas.getEspecifico(x).getPosX()>Jugador.getPosX())){
                        if(condicionEscudo!=true){//revisa si se tiene escudo
                            Jugador.setVidas(Jugador.getVidas()-(40/listaBalas.getEspecifico(x).getDañoBala()));//se divide entre el tipo ya que tipo 1(bombardero) hace mas daño que tipo 2(jet)
                        }
                        listaBalas.getEspecifico(x).setPosX(-10);//mueve la bala out of bounds
                        listaBalas.getEspecifico(x).setPosY(-10);//mueve la bala out of bounds
                        g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),0,0);
                    }
                    //FIN LOGICA BALA CHOCA CONTRA JUGADOR
                    cantBalasEnemigo-=1;
                    timeBalas.start();
                }
                //FINAL LOGICA BALA ENEMIGOS
                x+=1;
                }
        }
        //FIN LOGICA BALAS
        //LOGICA CHOQUE ENTRE NAVES
        if(choqueEnemigos == true && colaEnemigos.esVacia() != true){
            Nodo enemigoActual = colaEnemigos.getInicio();
            if((Jugador.getPosX()>enemigoActual.getDato().getPosX()&&Jugador.getPosX()<(enemigoActual.getDato().getPosX()+50)) || ((Jugador.getPosX()+50)>enemigoActual.getDato().getPosX()&&(Jugador.getPosX()+50)<(enemigoActual.getDato().getPosX()+50))){
                if((Jugador.getPosY()-15)>(enemigoActual.getDato().getPosY()-15)&&(Jugador.getPosY()-15)<(enemigoActual.getDato().getPosY()+15)||(Jugador.getPosY()+15)>(enemigoActual.getDato().getPosY()-15)&&(Jugador.getPosY()+15)<(enemigoActual.getDato().getPosY()+15)){
                    if(condicionEscudo!=true){
                        Jugador.setVidas(Jugador.getVidas()-20);
                    }
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
                listaBalas.getEspecifico(0).setDañoBala(Jugador.getDañoJugador());
                condicionBalas = true;
                if(balasEspeciales>1){
                    balasEspeciales-=1;
                }
                else{
                    Jugador.setDañoJugador(1);
                }

            }
            else{
                cantBalas-=1;
            }
        }
        if(tecla == KeyEvent.VK_Z){
            if(pilaPowerUps.esVacia()!=true){
                sizePowerUps-=1;
                PowerUps powActual = pilaPowerUps.getTope().getDato();
                pilaPowerUps.pop();
                if(powActual.getTipo()==1){
                    condicionEscudo =  true;
                }
                if(powActual.getTipo()==2){
                    balasEspeciales = 2;
                    Jugador.setDañoJugador(2);
                }
                if(powActual.getTipo()==3){
                    balasEspeciales = 1;
                    Jugador.setDañoJugador(3);
                }
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
