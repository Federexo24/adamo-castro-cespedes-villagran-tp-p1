package Obstaculos;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import juego.Gondolf;

public class Roca {
    private double x;
    private double y;
    private double lado;
    private Image imagen;

    public Roca(double x, double y, double lado) {
        this.x = x;
        this.y = y;
        this.lado = lado;
        this.imagen = Herramientas.cargarImagen("Iobstaculo/roca.png").getScaledInstance((int)lado, (int)lado, Image.SCALE_SMOOTH);; // <- Ajustá el path según tu carpeta
    }

    public void dibujar(Entorno entorno) {
        if (imagen != null) {
            entorno.dibujarImagen(imagen, x, y, 0);
        } else {
            entorno.dibujarRectangulo(x, y, lado, lado, 0, java.awt.Color.GRAY); // backup
        }
    }

    // Métodos de colisión
    public boolean colisionaArriba(Gondolf g) {
        return Math.abs(g.getX() - this.x) < (g.getAncho() / 2 + this.lado / 2) &&
               g.getY() > this.y &&
               g.getY() - this.y < g.getAlto() / 2 + this.lado / 2 + 5;
    }

    public boolean colisionaAbajo(Gondolf g) {
        return Math.abs(g.getX() - this.x) < (g.getAncho() / 2 + this.lado / 2) &&
               g.getY() < this.y &&
               this.y - g.getY() < g.getAlto() / 2 + this.lado / 2 + 5;
    }

    public boolean colisionaIzquierda(Gondolf g) {
        return Math.abs(g.getY() - this.y) < (g.getAlto() / 2 + this.lado / 2) &&
               g.getX() > this.x &&
               g.getX() - this.x < g.getAncho() / 2 + this.lado / 2 + 5;
    }

    public boolean colisionaDerecha(Gondolf g) {
        return Math.abs(g.getY() - this.y) < (g.getAlto() / 2 + this.lado / 2) &&
               g.getX() < this.x &&
               this.x - g.getX() < g.getAncho() / 2 + this.lado / 2 + 5;
    }

public double getX() { return x; }
public double getY() { return y; }
}