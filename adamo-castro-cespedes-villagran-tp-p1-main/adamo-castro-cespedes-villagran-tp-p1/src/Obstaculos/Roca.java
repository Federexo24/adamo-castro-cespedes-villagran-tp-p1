package Obstaculos;

import java.awt.Color;
import entorno.Entorno;


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
    


}
