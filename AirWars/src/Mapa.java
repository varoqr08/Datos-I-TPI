import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Random;

/**
 * Created by Gabriel on 3/20/2017.
 */
public class Mapa extends JPanel implements ActionListener,KeyListener{
    /**
     * @param Timer Se crean todos los diferentes timers que controlan los diferentes tiempos del juego(ver comentario ala par de timer para funcion especifica)
     */
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
    private Timer timeAlimentarCola = new Timer(2000, new ActionListener() {
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
    });//timer para poder chocar con enemigos
    private Timer timeEscudo = new Timer(5000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            condicionEscudo = false;
        }
    });//duracion de escudo
    private Timer timeTorre = new Timer(30, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            movTorre = true;
        }
    });//timer para que aparezcan torres
    private Timer timeMeterTorre = new Timer(5000, new ActionListener() {//timer para spawnear nueva torre
        @Override
        public void actionPerformed(ActionEvent e) {
            meterTorre = true;
        }
    });//timer para adherir torres a la lista
    private Timer timeJuego = new Timer(1000, new ActionListener() {//timer para spawnear nueva torre
        @Override
        public void actionPerformed(ActionEvent e) {
            listaUsuario.getEspecifico(0).setInformacion(listaUsuario.getEspecifico(0).getInformacion()+1);
        }
    });
    private Player Jugador = new Player();//creacion de jugador
    private String Nombre;
    /**
     * @param int Los diferentes integers necesarios para usar el juego
     */
    private int cambX = 0, cambY = 0, velBalas = -3,cantBalas=0, cantBalasEnemigo = 0, cantEnemigosTotal =0, contadorCantEnemigos = 0,PosEnemX=0,PosEnemY=0,balasEspeciales = 0,sizePowerUps = 0, contadorCicloPowerUps=1,cantTorres = 0, contadorJefe=0,NivelActual=0,torresTotal=0, puntaje=0, tiempoJugado =0, ganarVidas=0;//valores de cambio de jugador y cantidad de balas de enemigos o jugadores
    /**
     * @param Booleans los boleans necesarios para correr el juegos
     */
    private Boolean condicionBalas = false, MovBala = false, condicionEnemigos = false,balasEnemigos = false, movEnemigos = false, alimentarCola = false,choqueEnemigos =false,pintarPowerUps =false,condicionEscudo = false, movTorre=true, meterTorre = false;//balas,movimient balas,creacion enemigos,creacion balas enemigos,moviemiento de enemigos
    /**
     * @param Lista creacion de las listas de torres balas y de usuario
     */
    private ListaTorres listaTorres = new ListaTorres();
    private LinkList<Bala> listaBalas = new LinkList();//lista de balas
    private ListaUsuario listaUsuario = new ListaUsuario();
    private static JFrame ControladorJF = new JFrame();
    /**
     * @param Cola creacion de la cola de Enenmigos y de una cola de espera que se encarga de meter enemigos a un paso.
     */
    private Cola colaEnemigos = new Cola();//cola de enemigos activos
    private Cola colaEspera  = new Cola();//cola de enemigos que no han entrado al juego
    /**
     * @param Pila creacion de la pila de powerups
     */
    private Pila pilaPowerUps = new Pila();
    private Random generador = new Random();
    /**
     * Creacion de la clase Mapa que pintara todo lo necesario del juego y llenara la lista
     */
    public Mapa(int cantEnenemigos, int cantTorresGame,int NivelActual, String nombreDeJugador){
        Nombre = nombreDeJugador;
        listaUsuario.insertFirstLink(NivelActual);
        listaUsuario.insertFirstLink(puntaje);
        listaUsuario.insertFirstLink(tiempoJugado);
        this.NivelActual = NivelActual;
        this.cantTorres = cantTorresGame;
        this.torresTotal = cantTorresGame;
        torresTotal = cantTorresGame;
        cantEnemigosTotal = cantEnenemigos;
        contadorJefe = cantEnenemigos;
        int x=0;
        Enemigos enemigo = null;
        while(x!=cantEnenemigos){
            enemigo = new Enemigos();
            colaEspera.encolar(enemigo);
            x+=1;
        }
        //la siguientes 4 lineas son default de ejecucion del mapa
        timeMeterTorre.start();
        timer.start();
        timeEnemigos.start();//timer para que aparezcan los enemigos
        timeAlimentarCola.start();
        timeChoqueEnemigos.start();
        addKeyListener(this);
        setFocusable(true);//activa el keyListener
        setFocusTraversalKeysEnabled(false);//no se usara shift o tab por eso es false
    }


    //INICION LOGICA PRINCIPAL DEL JUEGO
    /**
     * Logica principal del juego
     */
    public void paintComponent(Graphics g){
        timeJuego.start();
        super.paintComponent(g);//Paint sale de JPanel esto se asegura que salga igual en cualquier computadora
        g.drawString("Nivel Actual: "+listaUsuario.getEspecifico(2).getInformacion(),200,470);
        g.drawString("Puntaje: "+listaUsuario.getEspecifico(1).getInformacion(),200,480);
        g.drawString("Tiempo Jugado: "+listaUsuario.getEspecifico(0).getInformacion(),200,490);
        if(Jugador.getVidas()<=0){
            reiniciarNivel();
        }
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
        /**
         * Pintado de la cola de PowerUps
         */
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
        /**
         * Logica de la funcion de los PowerUps
         */
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
        /**
         * Logica que alimenta la cola de enemigos
         */
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
        /**
         * Logica que controla a los enemigos para poder pintarlos y definir el color de cada uno dependiendo de su tipo
         */
        if(condicionEnemigos == true){
            timeEnemigos.stop();
            int contadorTorres = 0;
            //LOGICA DIBUJAR ENEMGIOS
            int sizeColaEnemigos = colaEnemigos.getSize();
            int contador = 1;
            Nodo enemigoActual = new Nodo();
            while(contador<=sizeColaEnemigos){
                enemigoActual = colaEnemigos.getInicio();//guarda en la variable el enemigo actual
                if(enemigoActual.getDato().getTipo()==1){
                    g.setColor(Color.MAGENTA);//colora enemigos
                }
                if(enemigoActual.getDato().getTipo()==2){
                    g.setColor(Color.BLUE);//colora enemigos
                }
                if(enemigoActual.getDato().getTipo()==3){
                    g.setColor(Color.cyan);//colora enemigos
                }
                if(enemigoActual.getDato().getTipo()==4){
                    g.setColor(Color.red);
                }
                g.fillRect(enemigoActual.getDato().getPosX(),enemigoActual.getDato().getPosY(),50,30);//posicion enemigos
                colaEnemigos.encolar(enemigoActual);//se vuelve a meter a el enemigo en la ultima posicion
                contador+=1;
            }
            /**
             * Logica que dibuja las torres en el canvas dependiendo de su tipo
             */
            //LOGICA DIBUJAR TORRES
            if(meterTorre == true&&listaTorres.getSize()!=1&&cantTorres!=0){
                Torre torre = new Torre();
                listaTorres.insertFirstLink(torre);
                meterTorre = false;
                cantTorres-=1;
            }
            while(contadorTorres<listaTorres.getSize()){
                g.setColor(Color.ORANGE);
                if(listaTorres.getEspecifico(0).getInformacion().getTipo()==1){
                    g.setColor(Color.BLACK);
                }
                g.fillRect(listaTorres.getEspecifico(0).getInformacion().getPosX(),listaTorres.getEspecifico(0).getInformacion().getPosY(),50,30);
                contadorTorres+=1;
            }
            contadorTorres =0;
            //FIN LOGICA DIBUJAR TORRES

            //FIN LOGICA DIBUJAR ENEMIGOS

            timeMov.start();//comienza para movimiento enemigos
            //LOGICA MOVIMIENTO ENEMIGOS
            /**
             * Logica que controla el moviemeinto de los enemigos tomando cada uno por aparte y moviendolos uno por uno
             */

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
                            if(enemigoActual.getDato().getContadorMovimiento()>50){
                                enemigoActual.getDato().setContadorMovimiento(-50);
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
                    //INICIO LOGICA MOV JEFES
                    if(enemigoActual.getDato().getTipo()==4){
                        enemigoActual.getDato().setPosX(enemigoActual.getDato().getPosX()+enemigoActual.getDato().getContadorMovimiento());
                        if(enemigoActual.getDato().getPosX()>580){
                            enemigoActual.getDato().setContadorMovimiento(-1);
                        }
                        else if(enemigoActual.getDato().getPosX()<20){
                            enemigoActual.getDato().setContadorMovimiento(1);
                        }
                    }
                    //FIN LOGICA MOV JEFES
                    if(enemigoActual.getDato().getPosY()>410){
                        enemigoActual.getDato().setPosY(0);
                    }
                    colaEnemigos.encolar(enemigoActual);
                    contador+=1;

                }
                //LOGICA MOVIMIENTO TORRES
                while(contadorTorres<listaTorres.getSize()){
                    if(movTorre==true){
                        listaTorres.getEspecifico(0).getInformacion().setPosY(listaTorres.getEspecifico(0).getInformacion().getPosY()+1);
                        timeTorre.start();
                        movTorre = false;
                    }
                    if(listaTorres.getEspecifico(0).getInformacion().getPosY()>410){
                        listaTorres.removeFirst();
                    }
                    contadorTorres+=1;
                }
                contadorTorres =0;
                //FIN LOGICA MOVIMIENTO TORRES
            }
            //FIN LOGICA MOVIMIENTO ENEMIGOS
            /**
             * Logica que genera a el Jefe una vez que ya no hay enemigos
             */
            //LOGICA GENERACION JEFE
            if(cantTorres==0&& contadorJefe==0){
                while(colaEnemigos.esVacia()==true){
                    Enemigos enemigo = new Enemigos();
                    enemigo.setTipo(4);
                    enemigo.setContadorMovimiento(1);
                    enemigo.setNivel(NivelActual);//designa cual nivel de boss es
                    enemigo.setHP(enemigo.getNivel()*3);//designa la vida del jefe dependiendo del nivel
                    colaEnemigos.encolar(enemigo);
                }
            }
            //FIN LOGICA GENERACION JEFE
            timeBalas.start();//comienza timer para que enemgios dispare
            //LOGICA BALAS ENEMIGOS
            /**
             * Logica que controla las balas de las naves enemigas
             */
            if(balasEnemigos == true){
                if(cantBalasEnemigo<1){//cantidad de balas de enemigos que pueden estar en la pantalla
                    contador = 1;
                    while(contador<=sizeColaEnemigos){
                        enemigoActual = colaEnemigos.getInicio();
                        if(enemigoActual.getDato().getTipo()!=3){
                            Bala bala = new Bala();//crea una bala
                            listaBalas.insertFirstLink(bala);//se inserta a lista la bala
                            if(enemigoActual.getDato().getTipo()==4){//daño de bala de jefe
                                listaBalas.getEspecifico(0).setDañoBala(1);
                            }
                            else{
                                listaBalas.getEspecifico(0).setDañoBala(enemigoActual.getDato().getTipo());//le asigna la bala el tipo de nave que la disparo para calcular daño
                            }
                            listaBalas.getEspecifico(0).setMovX(0);
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
                    //LOGICA GENERACION BALA TORRES
                    while (contadorTorres<listaTorres.getSize()){
                        Torre torreactual = listaTorres.getEspecifico(0).getInformacion();
                        Bala bala = new Bala();//crea una bala
                        listaBalas.insertFirstLink(bala);//se inserta a lista la bala
                        listaBalas.getEspecifico(0).setDañoBala(torreactual.getTipo());
                        listaBalas.getEspecifico(0).setAfiliacion(0);
                        if(torreactual.getPosX()>Jugador.getPosX()){
                            listaBalas.getEspecifico(0).setMovX(-1);
                        }
                        if(torreactual.getPosX()<Jugador.getPosX()){
                            listaBalas.getEspecifico(0).setMovX(1);
                        }
                        int x = torreactual.getPosX();
                        int y =torreactual.getPosY();
                        listaBalas.getEspecifico(0).setPosX(x+20);
                        listaBalas.getEspecifico(0).setPosY(y+30);
                        MovBala = true;
                        condicionBalas =false;
                        balasEnemigos = false;
                        cantBalasEnemigo+=1;
                        contadorTorres+=1;
                    }
                    //FIN LOGICA GENERACION BALA TORRES
                }
            }
            //FIN LOGICA BALAS ENEMIGOS
        }
        //FIN LOGICA ENEMIGOS
        /**
         * Logica de las balas del jugador
         */
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
        /**
         * Logica que define el movimiento de las balas y su colision ya sea con aliados o con enemigos
         */
        if(MovBala == true){
            int x = 0;//x con el que se recorre lista
            while(x<listaBalas.getSize()){// este while existe para que se puedan crear mas de 1 bala al mismo tiempo ya qu recorre una lista de balas
                int tipoMatado =0;//varaible para ver cuando muere jefe
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
                                int valor = 1+generador.nextInt(30);
                                if(valor<=100&&valor>0){
                                    PosEnemX=enemigoActual.getDato().getPosX();
                                    PosEnemY=enemigoActual.getDato().getPosY();
                                    pintarPowerUps = true;
                                }
                                tipoMatado = enemigoActual.getDato().getTipo();
                                listaUsuario.getEspecifico(1).setInformacion(listaUsuario.getEspecifico(1).getInformacion()+10/enemigoActual.getDato().getTipo());
                                ganarVidas+=10/enemigoActual.getDato().getTipo();
                                if(ganarVidas>=100){
                                    if(Jugador.getVidas()<100){
                                        Jugador.setVidas(Jugador.getVidas()+20);
                                    }
                                    ganarVidas=0;
                                }
                                enemigoActual = null;
                                contadorJefe-=1;
                            }
                            //FIN LOGICA CALCULO HP ENEMIGOS
                        }
                        colaEnemigos.encolar(enemigoActual);
                        contador+=1;
                    //FIN LOGICA COLISION BALAS CON ENEMIGOS

                    //LOGICA COLISION CON TORRES
                    }
                    if(listaTorres.getEspecifico(0)!=null){
                        if(listaBalas.getEspecifico(x).getPosY()<(listaTorres.getEspecifico(0).getInformacion().getPosY()+30)&&listaBalas.getEspecifico(x).getPosY()>(listaTorres.getEspecifico(0).getInformacion().getPosY())&&listaBalas.getEspecifico(x).getPosX()<(listaTorres.getEspecifico(0).getInformacion().getPosX()+50)&&(listaBalas.getEspecifico(x).getPosX()>listaTorres.getEspecifico(0).getInformacion().getPosX())){
                            listaUsuario.getEspecifico(1).setInformacion(listaUsuario.getEspecifico(1).getInformacion()+10/listaTorres.getEspecifico(0).getInformacion().getTipo());
                            ganarVidas+=10/listaTorres.getEspecifico(0).getInformacion().getTipo();
                            if(ganarVidas>=100){
                                if(Jugador.getVidas()<100){
                                    Jugador.setVidas(Jugador.getVidas()+20);
                                }
                                ganarVidas=0;
                            }
                            listaTorres.removeFirst();
                            cantBalas-=1;
                            listaBalas.getEspecifico(x).setPosX(-10);//mueve la bala out of bounds en un lugar no visible que no afecta
                            listaBalas.getEspecifico(x).setPosY(-10);//mueve la bala out of bounds
                        }
                    }
                    //FIN LOGICA COLISION CON TORRES
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
                    listaBalas.getEspecifico(x).setPosX(listaBalas.getEspecifico(x).getPosX()+listaBalas.getEspecifico(x).getMovX());
                    //LOGICA BALA CHOCA CONTRA JUGADOR
                    if(listaBalas.getEspecifico(x).getPosY()<(Jugador.getPosY()+30)&&listaBalas.getEspecifico(x).getPosY()>(Jugador.getPosY())&&listaBalas.getEspecifico(x).getPosX()<(Jugador.getPosX()+50)&&(listaBalas.getEspecifico(x).getPosX()>Jugador.getPosX())){
                        if(condicionEscudo!=true){//revisa si se tiene escudo
                            while (sizePowerUps!=0){
                                pilaPowerUps.pop();
                                sizePowerUps-=1;
                            }
                            Jugador.setVidas(Jugador.getVidas()-(40/listaBalas.getEspecifico(x).getDañoBala()));//se divide entre el tipo ya que tipo 1(bombardero) hace mas daño que tipo 2(jet)
                        }
                        listaBalas.getEspecifico(x).setPosX(1000);//mueve la bala out of bounds
                        listaBalas.getEspecifico(x).setPosY(-10);//mueve la bala out of bounds
                        g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),0,0);
                    }
                    //FIN LOGICA BALA CHOCA CONTRA JUGADOR
                    cantBalasEnemigo-=1;
                    timeBalas.start();
                }
                //FINAL LOGICA BALA ENEMIGOS
                x+=1;







                //VENCER JEFE Y TERMINAR NIVEL
                /**
                 * Logica que cambia de nivles una vez que el jefe es vencido
                 */
                if(tipoMatado==4){//revisa si enemigos derrotado es un jefe
                    if(NivelActual==10){
                        try {
                            PrintWriter txtfile = new PrintWriter(new BufferedWriter(new FileWriter("out.txt",true))) ;
                            txtfile.println(Nombre);
                            txtfile.println("Nivel Maximo: "+listaUsuario.getEspecifico(2).getInformacion());
                            txtfile.println("Tiempo Jugado: "+listaUsuario.getEspecifico(0).getInformacion());
                            txtfile.println("Puntaje: "+listaUsuario.getEspecifico(1).getInformacion());
                            txtfile.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ControladorJF.dispose();
                    }
                     x = listaBalas.getSize()+1;
                    cambX = 0;
                    cambY = 0;
                    velBalas = -3;
                    cantBalas=0;
                    cantBalasEnemigo = 0;
                    cantEnemigosTotal =cantEnemigosTotal+2;
                    contadorCantEnemigos = 0;
                    PosEnemX=0;
                    PosEnemY=0;
                    balasEspeciales = 0;
                    sizePowerUps = 0;
                    contadorCicloPowerUps=1;
                    torresTotal=torresTotal+1;
                    cantTorres = torresTotal;
                    contadorJefe=cantEnemigosTotal;
                    NivelActual = NivelActual+1;
                    condicionBalas = false;
                    MovBala = false;
                    condicionEnemigos = false;
                    balasEnemigos = false;
                    movEnemigos = false;
                    alimentarCola = false;
                    choqueEnemigos =false;
                    pintarPowerUps =false;
                    condicionEscudo = false;
                    movTorre=true;
                    meterTorre = false;
                    listaBalas = new LinkList();
                    colaEnemigos = new Cola();
                    colaEspera  = new Cola();
                    Enemigos enemigo = null;
                    int contadorCola = 0;
                    while(contadorCola!=cantEnemigosTotal){
                        enemigo = new Enemigos();
                        colaEspera.encolar(enemigo);
                        contadorCola+=1;
                    }
                    Jugador.setPosX(250);
                    Jugador.setPosY(410);
                    if(Jugador.getVidas()<100){
                        Jugador.setVidas(Jugador.getVidas()+20);
                    }
                    timeMeterTorre.start();
                    timeEnemigos.start();//timer para que aparezcan los enemigos
                    timeAlimentarCola.start();
                    timeChoqueEnemigos.start();
                    this.listaUsuario.getEspecifico(2).setInformacion(listaUsuario.getEspecifico(2).getInformacion()+1);
                //FIN LOGICA CAMBIO DE NIVEL







                }
        }
        //FIN LOGICA BALAS
            /**
             * Logica que controla el choque contra naves o contra torres
             */
        //LOGICA CHOQUE ENTRE NAVES
        if(choqueEnemigos == true && colaEnemigos.esVacia() != true){
            Nodo enemigoActual = colaEnemigos.getInicio();
            if((Jugador.getPosX()>=enemigoActual.getDato().getPosX()&&Jugador.getPosX()<=(enemigoActual.getDato().getPosX()+50)) || ((Jugador.getPosX()+50)>=enemigoActual.getDato().getPosX()&&(Jugador.getPosX()+50)<=(enemigoActual.getDato().getPosX()+50))){
                if((Jugador.getPosY()-15)>=(enemigoActual.getDato().getPosY()-15)&&(Jugador.getPosY()-15)<=(enemigoActual.getDato().getPosY()+15)||(Jugador.getPosY()+15)>=(enemigoActual.getDato().getPosY()-15)&&(Jugador.getPosY()+15)<=(enemigoActual.getDato().getPosY()+15)){
                    if(condicionEscudo!=true){
                        if(enemigoActual.getDato().getTipo()==4){//en caso de colision con jefe se pierde de un solo
                            Jugador.setVidas(0);

                        }
                        while (sizePowerUps!=0){
                            pilaPowerUps.pop();
                            sizePowerUps-=1;
                        }
                        Jugador.setVidas(Jugador.getVidas()-20);
                    }
                    if(enemigoActual.getDato().getTipo()==4){

                    }else{
                        enemigoActual = null;
                        contadorJefe-=1;
                     }
                }
            }
            colaEnemigos.encolar(enemigoActual);
        }
        if(choqueEnemigos == true && listaTorres.isEmpty()!=true){
            Torre torre = listaTorres.getEspecifico(0).getInformacion();
            if((Jugador.getPosX()>torre.getPosX()&&Jugador.getPosX()<(torre.getPosX()+50)) || ((Jugador.getPosX()+50)>torre.getPosX()&&(Jugador.getPosX()+50)<(torre.getPosX()+50))){
                if((Jugador.getPosY()-15)>(torre.getPosY()-15)&&(Jugador.getPosY()-15)<(torre.getPosY()+15)||(Jugador.getPosY()+15)>(torre.getPosY()-15)&&(Jugador.getPosY()+15)<(torre.getPosY()+15)){
                    if(condicionEscudo!=true){
                        while (sizePowerUps!=0){
                            pilaPowerUps.pop();
                            sizePowerUps-=1;
                        }
                        Jugador.setVidas(Jugador.getVidas()-20);
                    }
                    listaTorres.removeFirst();
                }
            }
        }
        //FIN LOGICA CHOQUE ENTRE NAVES
    }
    }

    //LIMITES Y MOVIMIENTO DE JUGADOR
    /**
     * Logica que cambia la posicion de la nave aliada y ademas establece sus bordes.
     */
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
    /**
     * Logica que controla el input del usuario en el programa
     */
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
            if(cantBalas<5){
                cantBalas += 1;
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
    /**
     * Logica que reinicia un nivel cuando se pierde
     */
    private void reiniciarNivel(){
        cambX = 0;
        cambY = 0;
        velBalas = -3;
        cantBalas=0;
        cantBalasEnemigo = 0;
        cantEnemigosTotal =cantEnemigosTotal;
        contadorCantEnemigos = 0;
        PosEnemX=0;
        PosEnemY=0;
        balasEspeciales = 0;
        sizePowerUps = 0;
        contadorCicloPowerUps=1;
        torresTotal=torresTotal;
        cantTorres = torresTotal;
        contadorJefe=cantEnemigosTotal;
        NivelActual = NivelActual;
        condicionBalas = false;
        MovBala = false;
        condicionEnemigos = false;
        balasEnemigos = false;
        movEnemigos = false;
        alimentarCola = false;
        choqueEnemigos =false;
        pintarPowerUps =false;
        condicionEscudo = false;
        movTorre=true;
        meterTorre = false;
        listaBalas = new LinkList();
        colaEnemigos = new Cola();
        colaEspera  = new Cola();
        Enemigos enemigo = null;
        int contadorCola = 0;
        while(contadorCola!=cantEnemigosTotal){
            enemigo = new Enemigos();
            colaEspera.encolar(enemigo);
            contadorCola+=1;
        }
        Jugador.setVidas(60);
        Jugador.setPosX(250);
        Jugador.setPosY(410);
        timeMeterTorre.start();
        timeEnemigos.start();//timer para que aparezcan los enemigos
        timeAlimentarCola.start();
        timeChoqueEnemigos.start();
    }

    //MAIN DE JUEGO
    /**
     * Logica que  crea el canvas.
     */
    public void Comenzar(int enemigos,int torres,int Nivel,String nombreDeJugador) {
        Mapa mapa = new Mapa(enemigos,torres,Nivel,nombreDeJugador);
        ControladorJF.addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent windowEvent){
                try {
                    PrintWriter txtfile = new PrintWriter(new BufferedWriter(new FileWriter("out.txt",true))) ;
                    txtfile.println(Nombre);
                    txtfile.println("Nivel Maximo: "+listaUsuario.getEspecifico(2).getInformacion());
                    txtfile.println("Tiempo Jugado: "+listaUsuario.getEspecifico(0).getInformacion());
                    txtfile.println("Puntaje: "+listaUsuario.getEspecifico(1).getInformacion());
                    txtfile.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ControladorJF.setLayout(new BoxLayout(ControladorJF.getContentPane(), BoxLayout.PAGE_AXIS));
        ControladorJF.setTitle("Air Wars");
        ControladorJF.setSize(640,535);//Tamaño de la ventana
        ControladorJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Operacion cuando se cierra ventana
        ControladorJF.setResizable(false);
        ControladorJF.add(mapa);//Se adhiere el controlador a la clase mapa para que consiga las propiedads establecidas
        ControladorJF.setVisible(true);
        }
}

