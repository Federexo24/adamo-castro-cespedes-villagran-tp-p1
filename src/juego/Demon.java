package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Demon {
    private double x;
    private double y;
    private double velocidad;
    private int ancho = 80;
    private int alto = 80;
    private int daño;
    
    private Image[] framesDerecha;
    private Image[] framesIzquierda;
    private int frameActual;
    private int contadorFrames;
    private int direccion; // 0 = derecha, 1 = izquierda

    public Demon(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocidad = 1.5;
        this.daño = 20;
        
        this.framesDerecha = new Image[4];
        this.framesIzquierda = new Image[4];
        this.frameActual = 0;
        this.contadorFrames = 0;
        this.direccion = 0;

        // Cargar imágenes (ajustá los nombres si son distintos)
        for (int i = 0; i < 4; i++) {
            this.framesIzquierda[i] = Herramientas.cargarImagen("Idemon/vuelo" + (i + 1) + ".png").getScaledInstance((int)ancho, (int)alto, Image.SCALE_SMOOTH);;
            this.framesDerecha[i] = Herramientas.cargarImagen("Idemon/frame" + (i + 1) + (i + 1) + ".png").getScaledInstance((int)ancho, (int)alto, Image.SCALE_SMOOTH);;
        }
    }

    public void mover() {
        x += velocidad;
        if (x > 1300 || x < 0) {
            velocidad *= -1;
            direccion = (velocidad > 0) ? 0 : 1;
        }
        animar();
    }

    public void seguirGondolf(double xGondolf, double yGondolf) {
        double tolerancia = 5;

        // Movimiento en X
        if (xGondolf < this.x && this.x - velocidad - ancho / 2 > 55) {
            this.x -= velocidad;
        } else if (xGondolf > this.x && this.x + velocidad + ancho / 2 < 980) {
            this.x += velocidad;
        }

        // Movimiento en Y
        if (yGondolf < this.y && this.y - velocidad - alto / 2 > 20) {
            this.y -= velocidad;
        } else if (yGondolf > this.y && this.y + velocidad + alto / 2 < 680) {
            this.y += velocidad;
        }

        // Cambiar dirección solo si está claramente a un lado
        double diferenciaX = xGondolf - this.x;

        if (Math.abs(diferenciaX) > tolerancia) {
            direccion = (diferenciaX < 0) ? 1 : 0;
        }

        animar();
    }

    private void animar() {
        contadorFrames++;
        if (contadorFrames >= 5) {
            frameActual = (frameActual + 1) % 4;
            contadorFrames = 0;
        }
    }
    
    
    public int getDaño() {
        return daño;
    }
    private boolean vivo = true; // Al principio está vivo

    public boolean estaVivo() {
        return vivo;
    }

    public void matar() {
        this.vivo = false;
    }

    public void dibujar(Entorno entorno) {
        Image imagen = (direccion == 0) ? framesDerecha[frameActual] : framesIzquierda[frameActual];
        entorno.dibujarImagen(imagen, x, y, 0);
    }

    public double getX() { return x; }
    public double getY() { return y; }
}