package Obstaculos;

import java.awt.Color;
import entorno.Entorno;
import juego.Gondolf;

public class Roca {
    private double x;
    private double y;
    private double lado;

    public Roca(double x, double y, double lado) {
        this.x = x;
        this.y = y;
        this.lado = lado;
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(x, y, lado, lado, 0, Color.GRAY);
    }

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
}