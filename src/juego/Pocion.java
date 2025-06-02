package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Pocion {
    private double x;
    private double y;
    private Image imagen;

    public Pocion(double x, double y) {
        this.x = x;
        this.y = y;
        this.imagen = Herramientas.cargarImagen("Iitems/Pocion.png");
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagen, x, y, 0);
    }

    public boolean colisionaCon(Gondolf gondolf) {
        double dx = this.x - gondolf.getX();
        double dy = this.y - gondolf.getY();
        return dx * dx + dy * dy < 30 * 30; // radio de colisión
    }

    public double getX() { return x; }
    public double getY() { return y; }
}
