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

    public Gondolf(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocidad = 3;
        this.imagen = Herramientas.cargarImagen("imagenes/magochico.png");
        this.imagen2 = Herramientas.cargarImagen("imagenes/magochico2.png");
    }

    public void mover(Entorno entorno) {
        if (entorno.estaPresionada('w') || entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
            if (y - velocidad > 0) y -= velocidad;
        }
        if (entorno.estaPresionada('s') || entorno.estaPresionada(entorno.TECLA_ABAJO)) {
            if (y + velocidad < 600) y += velocidad;
        }
        if (entorno.estaPresionada('a') || entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            if (x - velocidad > 0) x -= velocidad;
        }
        if (entorno.estaPresionada('d') || entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            if (x + velocidad < 600) x += velocidad; // Evita que cruce a la zona del menú si mide 200px
        }
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagen, x, y, 0);
    }

    public double getX() { return x; }
    public double getY() { return y; }
}
