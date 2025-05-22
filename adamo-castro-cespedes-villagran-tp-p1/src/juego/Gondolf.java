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
    private int ancho = 40;
    private int alto = 100;
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
    
    //variables para la colición
    public int getAncho() {
        return this.ancho;
    }

    public int getAlto() {
        return this.alto;
    }

    // movimientos
    public void moverArriba() {
        if (y - velocidad - alto / 2 > 10)
            y -= velocidad;
    }

    public void moverAbajo() {
        if (y + velocidad + alto / 2 < 600)
            y += velocidad;
    }

    public void moverIzquierda() {
        if (x - velocidad - ancho / 2 > 10) 
        {
            x -= velocidad;
            imagenActual = imagen2;
        }
    }

    public void moverDerecha() {
        if (x + velocidad + ancho / 2 < 600) 
        { // choca justito con el hud, de otro lado se sale un poco
            x += velocidad;
            imagenActual = imagen;
        }
    }

    public void dibujar(Entorno entorno) {
        int desplazamientoX = 0;

        if (imagenActual == imagen2) {
            desplazamientoX = -64; // Ajustá este valor hasta que se vea alineado
        }

        entorno.dibujarImagen(imagenActual, x+30 + desplazamientoX, y+40, 0);

        // Dibujamos la hitbox real para ver si coincide (opcional para debug)
        // entorno.dibujarRectangulo(x, y, ancho, alto, 0, java.awt.Color.RED);

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