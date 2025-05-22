package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Murcielagos {
    private double x;
    private double y;
    private double velocidad;
    private Image imagen;
    private int ancho = 40;
    private int alto = 40;
    private int daño;
    

    public Murcielagos(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocidad = 1;
        this.imagen = Herramientas.cargarImagen("imagenes/batman.png");
        this.daño = 5;
    }

    public void mover() {
        //  se mueve de izquierda a derecha
        x += velocidad;
        if (x > 1300 || x < 0) {
            velocidad *= -1; // rebota
        }
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagen, x, y, 0);
        
    }
    
    public void seguirGondolf(double xGondolf, double yGondolf) {
        // Movimiento en X
        if (xGondolf < this.x && this.x - this.velocidad - ancho / 2 > 30) {
            this.x -= this.velocidad;
        } else if (xGondolf > this.x && this.x + this.velocidad + ancho / 2 < 615) {
            this.x += this.velocidad;
        }

        // Movimiento en Y
        if (yGondolf < this.y && this.y - this.velocidad - alto / 2 > 60) {
            this.y -= this.velocidad;
        } else if (yGondolf > this.y && this.y + this.velocidad + alto / 2 < 630) {
            this.y += this.velocidad;
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }
}