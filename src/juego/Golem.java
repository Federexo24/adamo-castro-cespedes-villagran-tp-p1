package juego;

import java.awt.Image;
import java.util.Random;
import entorno.Entorno;
import entorno.Herramientas;

public class Golem {
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

    private enum Estado { NORMAL, DASH, RECUPERANDO }
    private Estado estado;
    private double velocidadDash;
    private int pasosRecuperacion;
    private int cooldownDash;

    private double direccionDashX; // Dirección fija durante el dash
    private double direccionDashY;

    private Random rand;

    private boolean vivo;

    public Golem(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocidad = 0.5;
        this.velocidadDash = 4;
        this.daño = 20;

        this.framesDerecha = new Image[10];
        this.framesIzquierda = new Image[10];
        this.frameActual = 0;
        this.contadorFrames = 0;
        this.direccion = 0;

        this.estado = Estado.NORMAL;
        this.pasosRecuperacion = 0;
        this.cooldownDash = 100;
        this.rand = new Random();

        this.vivo = true;

        for (int i = 0; i < 10; i++) {
            this.framesIzquierda[i] = Herramientas.cargarImagen("Igolem/frame_" + (i + 1) + "_flipped.png")
                    .getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            this.framesDerecha[i] = Herramientas.cargarImagen("Igolem/frame_" + (i + 1) + ".png")
                    .getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        }
    }

    public boolean estaVivo() {
        return vivo;
    }

    public void actualizar(double xGondolf, double yGondolf, Gondolf gondolf) {
        if (!vivo) return; // Si está muerto no hacer nada

        switch (estado) {
            case NORMAL:
                // Movimiento hacia Gondolf
                double dx = xGondolf - this.x;
                double dy = yGondolf - this.y;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist != 0) {
                    this.x += velocidad * dx / dist;
                    this.y += velocidad * dy / dist;
                }

                direccion = (dx < 0) ? 1 : 0;

                // Intentar iniciar dash
                if (cooldownDash <= 0 && rand.nextInt(200) == 0) {
                    iniciarDash(xGondolf, yGondolf);
                } else {
                    cooldownDash--;
                }
                break;

            case DASH:
                this.x += velocidadDash * direccionDashX;
                this.y += velocidadDash * direccionDashY;

                if (direccionDashX < 0) {
                    direccion = 1;
                } else {
                    direccion = 0;
                }

                if (x < 55 || x > 970 || y < 30 || y > 670) {
                    estado = Estado.RECUPERANDO;
                    pasosRecuperacion = rand.nextInt(30) + 20;
                }

                // Si choca con Gondolf
                if (colisionaCon(gondolf)) {
                    gondolf.recibirDaño(daño);
                    vivo = false; // Golem muere al pegarle
                }
                break;

            case RECUPERANDO:
                x += (rand.nextDouble() - 0.5) * 2;
                y += (rand.nextDouble() - 0.5) * 2;
                pasosRecuperacion--;
                if (pasosRecuperacion <= 0) {
                    estado = Estado.NORMAL;
                    cooldownDash = 100;
                }
                break;
        }

        animar();
    }

    private void iniciarDash(double xGondolf, double yGondolf) {
        estado = Estado.DASH;

        double dx = xGondolf - this.x;
        double dy = yGondolf - this.y;
        double magnitud = Math.sqrt(dx * dx + dy * dy);

        if (magnitud != 0) {
            this.direccionDashX = dx / magnitud;
            this.direccionDashY = dy / magnitud;
        } else {
            this.direccionDashX = 0;
            this.direccionDashY = 0;
        }

        direccion = (dx < 0) ? 1 : 0;
    }

    private boolean colisionaCon(Gondolf gondolf) {
        double dx = Math.abs(this.x - gondolf.getX());
        double dy = Math.abs(this.y - gondolf.getY());
        return dx < (this.ancho / 2 + gondolf.getAncho() / 2) &&
               dy < (this.alto / 2 + gondolf.getAlto() / 2);
    }

    private void animar() {
        contadorFrames++;
        if (contadorFrames >= 5) {
            frameActual = (frameActual + 1) % 10;
            contadorFrames = 0;
        }
    }

    public void dibujar(Entorno entorno) {
        if (!vivo) return; // No dibujar si está muerto

        Image imagen = (direccion == 0) ? framesDerecha[frameActual] : framesIzquierda[frameActual];
        entorno.dibujarImagen(imagen, x, y, 0);
    }

    public int getDaño() {
        return daño;
    }

    public double getX() { return x; }
    public double getY() { return y; }
}