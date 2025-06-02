package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class PocionMana {
    private double x;
    private double y;
    private Image imagen;

    public PocionMana(double x, double y) {
        this.x = x;
        this.y = y;
        this.imagen = Herramientas.cargarImagen("Iitems/PocionMana.png");
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
