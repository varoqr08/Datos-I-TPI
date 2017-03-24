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
    Timer timeEnemigos = new Timer(5000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            condicionEnemigos = true;
        }
    });
    Player Jugador = new Player();
    int cambX = 0, cambY = 0, velBalas = -3,cantBalas=0, cantBalasEnemigo = 0;//valores iniciales de la posicion de la forma
    Boolean condicionBalas = false, MovBala = false, condicionEnemigos = false,balasEnemigos = false;
    LinkList<Bala> listaBalas = new LinkList();
    Enemigos enemigo = new Enemigos();
    public Mapa(){
        timer.start();
        timeEnemigos.start();//timer para que aparezcan los enemigos
        addKeyListener(this);
        setFocusable(true);//activa el keyListener
        setFocusTraversalKeysEnabled(false);//no se usara shift o tab por eso es false
    }



    public void paintComponent(Graphics g){
        super.paintComponent(g);//Paint sale de JPanel esto se asegura que salga igual en cualquier computadora
        g.setColor(Color.RED);//Color de la forma
        g.fillRect(Jugador.getPosX(),Jugador.getPosY(),50,30);//primer dato cantidad de pixeles que se mueve a la Derecha segundo dato cantidad de pixeles que se mueve  abajo los ultmos dos numeros son las dimensiones del cuadrado
        g.setColor(Color.GRAY);
        g.fillRect(5,5,60,30);
        ///GENERACION BALAS ENEMIGOS
        if(condicionEnemigos == true){
            timeEnemigos.stop();
            g.setColor(Color.BLUE);
            g.fillRect(enemigo.getPosX(),enemigo.getPosY(),50,30);
            Timer timeBalas = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    balasEnemigos = true;
                }
            });
            timeBalas.start();
            if(balasEnemigos == true){
                if(cantBalasEnemigo<1){
                    Bala bala = new Bala();
                    listaBalas.insertFirstLink(bala);
                    listaBalas.getEspecifico(0).setAfiliacion(0);
                    int x = enemigo.getPosX();
                    int y = enemigo.getPosY();
                    listaBalas.getEspecifico(0).setPosX(x+20);
                    listaBalas.getEspecifico(0).setPosY(y+30);
                    g.fillOval( listaBalas.getEspecifico(0).getPosX(),listaBalas.getEspecifico(0).getPosY(),10,10);
                    MovBala = true;
                    condicionBalas =false;
                    balasEnemigos = false;
                    cantBalasEnemigo+=1;
                }
            }

        }
        if(condicionBalas == true){
            int x = Jugador.getPosX();
            int y = Jugador.getPosY();
            listaBalas.getEspecifico(0).setPosX(x+20);
            listaBalas.getEspecifico(0).setPosY(y-15);
            g.fillOval( listaBalas.getEspecifico(0).getPosX(),listaBalas.getEspecifico(0).getPosY(),10,10);
            condicionBalas = false;
            MovBala = true;
        }
        if(MovBala == true){
            g.setColor(Color.RED);
            int x = 0;
            //LOGICA BALAS
            while(x!=listaBalas.getSize()){// este while existe para que se puedan creamas de 1 bala al mismo tiempo ya qu recorre una lista de balas
                if (listaBalas.getEspecifico(x).getAfiliacion()==1){
                    g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),10,10);
                    listaBalas.getEspecifico(x).setPosY(listaBalas.getEspecifico(x).getPosY()+velBalas);
                    //LOGICA COLISION BALAS ENEMIGOS
                    if(listaBalas.getEspecifico(x).getPosY()<(enemigo.getPosY()+30)&&listaBalas.getEspecifico(x).getPosY()>(enemigo.getPosY())&&listaBalas.getEspecifico(x).getPosX()<(enemigo.getPosX()+50)&&(listaBalas.getEspecifico(x).getPosX()>enemigo.getPosX())){
                        condicionEnemigos = false;
                    }
                    if(listaBalas.getEspecifico(x).getPosY()> 0&& listaBalas.getEspecifico(x).getPosY()< 10){//revisa si las balas tocan el borde de arriba de la pantalla
                        listaBalas.getEspecifico(x).setPosX(-10);//mueve la bala out of bounds
                        listaBalas.getEspecifico(x).setPosY(-10);//mueve la bala out of bounds
                        g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),0,0);
                        cantBalas -=1;
                    }
                }if(listaBalas.getEspecifico(x).getAfiliacion()==0){
                    g.fillOval(listaBalas.getEspecifico(x).getPosX(),listaBalas.getEspecifico(x).getPosY(),10,10);
                    listaBalas.getEspecifico(x).setPosY(listaBalas.getEspecifico(x).getPosY()-velBalas);
                }
                x+=1;
                }
        }
    }

    public void actionPerformed(ActionEvent e){
        if(Jugador.getPosX()<0){//cuando salga del borde del la ventana
            cambX = 0;// se resetea el cambio de x
            Jugador.setPosX(0);//se reposiciona x en cero ya que sin esto cambX no puede cambiar
        }
        if(Jugador.getPosX()>575){
            cambX = 0;
            Jugador.setPosX(575);
        }
        if(Jugador.getPosY()<0){
            cambY = 0;
            Jugador.setPosY(0);
        }
        if (Jugador.getPosY()>410){
            cambY = 0;
            Jugador.setPosY(410);
        }
        Jugador.setPosX(Jugador.getPosX()+cambX);//cambia el valor de x de 1 en 1 cada vez que se toca el teclado
        Jugador.setPosY(Jugador.getPosY()+cambY);//igual pero para y
        repaint();//vuelve a dibujar el rectangulo
    }
    public void keyPressed(KeyEvent e){//consigue el valor de las teclas del teclado
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT){//Si toca izquierda
                cambX = -1;//Se usa -1 ya que se movera un pixel a la izquierda
                cambY = 0;//no hay cambio en y
            }
        if (tecla == KeyEvent.VK_UP){
                cambX = 0;
                cambY = -1;//Para arriba en y es negativo
            }
        if (tecla == KeyEvent.VK_RIGHT){
                cambX = 1;
                cambY = 0;
            }
        if (tecla == KeyEvent.VK_DOWN){
                cambX = 0;
                cambY = 1;
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


    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){//Metodo necesario para usar keylistener maneja la logica de soltar la tecla
        cambX = 0;
        cambY = 0;
    }
    public static void main(String[] args) {
        Mapa mapa = new Mapa();
        JPanel panel1 = new JPanel();
        JLabel jl = new JLabel("Vidas");
        panel1.add(jl);
        JFrame ControladorJF = new JFrame();
        ControladorJF.setTitle("Air Wars");
        ControladorJF.setSize(640,480);//Tamaño de la ventana
        jl.setVisible(true);
        panel1.setVisible(true);
        ControladorJF.setVisible(true);
        ControladorJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Operacion cuando se cierra ventana
        ControladorJF.setResizable(false);
        ControladorJF.add(panel1);
        ControladorJF.add(mapa);//Se adhiere el controlador a la clase mapa para que consiga las propiedads establecidas

    }
}
