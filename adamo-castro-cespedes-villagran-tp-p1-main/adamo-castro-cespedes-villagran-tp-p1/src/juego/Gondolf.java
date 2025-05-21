package juego;
 	
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Gondolf {
    private double x;
    private double y;
    private double velocidad;
    private Image imagen;
    private Image imagen2;
    private Image imagenActual;
    private int ancho = 80;
    private int alto = 80;
    private int vida;

    public Gondolf(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocidad = 3;
        this.imagen = Herramientas.cargarImagen("imagenes/magochico.png");
        this.imagen2 = Herramientas.cargarImagen("imagenes/magochico2.png");
        this.imagenActual = imagen;
        this.vida = 50;
    }

    // movimientos
    public void moverArriba() {
        if (y - velocidad - alto / 2 > 60)
            y -= velocidad;
    }

    public void moverAbajo() {
        if (y + velocidad + alto / 2 < 630)
            y += velocidad;
    }

    public void moverIzquierda() {
        if (x - velocidad - ancho / 2 > 30) 
        {
            x -= velocidad;
            imagenActual = imagen2;
        }
    }

    public void moverDerecha() {
        if (x + velocidad + ancho / 2 < 615) 
        { // choca justito con el hud, de otro lado se sale un poco
            x += velocidad;
            imagenActual = imagen;
        }
    }

    public void dibujar(Entorno entorno) {
        if (imagenActual == imagen2) {
            entorno.dibujarImagen(imagenActual, x - 55, y, 0);
        } else {
            entorno.dibujarImagen(imagenActual, x, y, 0);
        }
        entorno.cambiarFont("Arial", 15, java.awt.Color.RED);
        entorno.escribirTexto("Vida: " + vida, (int)(x - 20), (int)(y - 40));
    }

    // Métodos de vida
    public int getVida() {
        return vida;
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