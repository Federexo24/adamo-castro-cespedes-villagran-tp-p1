package juego;
 	
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Gondolf {
    private double x;
    private double y;
    private double velocidad;
    private int ancho = 40;
    private int alto = 40;
    
    //Stats personaje
    private int vida;
    private int mana;

    
    
    //movimiento
    private int direccion; // 0 = derecha, 1 = izquierda
    private int frameActual;
    private int contadorFrames;
    private Image[] framesDerecha;
    private Image[] framesIzquierda;

    public Gondolf(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocidad = 3;
        
        //stats --Modificar valores de daño dependiendo de la vida q le pongamos a los enemigos
        this.vida = 100;
        this.mana = 100;

        		
        		
        //Cargado imagen
        this.frameActual = 0;
        this.contadorFrames = 0;
        this.direccion = 0; // empieza mirando a la derecha

        this.framesDerecha = new Image[8];
        this.framesIzquierda = new Image[8];
        //for de imagenes para q siempre cargue la correspondiente
        for (int i = 0; i < 8; i++) {
            this.framesIzquierda[i] = Herramientas.cargarImagen("Imagorun/run" + (i + 1) + "i.png");
            this.framesDerecha[i] = Herramientas.cargarImagen("Imagorun/run" + (i + 1) + ".png");
        }
 
        
    }
    
    
    
    //variables para la colición
    public int getAncho() {
        return this.ancho;
    }

    public int getAlto() {
        return this.alto;
    }

    // movimientos
    public void moverArriba() {
        if (y- velocidad - alto / 2 > 20)
        	y-=velocidad;
        animar();
    }

    public void moverAbajo() {
    	if (y + velocidad + alto / 2 < 680)
    		y+= velocidad;
        animar();
    }

    public void moverIzquierda() {
        if (x - velocidad - ancho / 2 > 55) {
        x-=velocidad;
        direccion = 1;
        animar();
        } else {
        	x= 55 + ancho / 2;
        	direccion = 1;
        	animar();
        }
    }

    public void moverDerecha() {
     if (x + velocidad + ancho / 2 < 980)
    	x += velocidad;
        direccion = 0;
        animar();
    }
    
    //ciclo de frames
    private void animar() {
        contadorFrames++;
        if (contadorFrames >= 5) {
            frameActual = (frameActual + 1) % 8;
            contadorFrames = 0;
        }
    }
    //dibuja los frames de izq y der alternando
    public void dibujar(Entorno entorno) {
        Image imagen = (direccion == 1) ? framesIzquierda[frameActual] : framesDerecha[frameActual];
        entorno.dibujarImagen(imagen, x, y, 0);
        }

    // Métodos de vida
    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }
    public int getMana() {
        return mana;
    }
    public void restarMana(int cantidad) {
        this.mana -= cantidad;
        if (this.mana < 0) this.mana = 0;
    }


    public void recibirDaño(int daño) {
        vida -= daño;
        if (vida < 0) {
            vida = 0;
        }
    }

    public boolean estaVivo() { // agregar en el tick para perder juego
        return vida > 0;
    }

    public double getX() { return x; }
    public double getY() { return y; }
}