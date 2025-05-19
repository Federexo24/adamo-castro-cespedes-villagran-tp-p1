package juego;

import java.awt.Image;

import Obstaculos.Roca;

import java.awt.Color;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Image fondo;
	private Gondolf gondolf;
	private Roca roca;
	private Image hud;
	private Image iconVIda;

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "TP_GRUPO_13", 800, 600);
		this.fondo = Herramientas.cargarImagen("imagenes/fondo.png");
		this.gondolf = new Gondolf(400, 300);
		this.iconVIda = Herramientas.cargarImagen("imagenes/iconvida1.png");
		this.roca = new Roca(200, 300, 50); 
		this.hud = Herramientas.cargarImagen("imagenes/fondohud.png");//imagen del hud(barra lateral derecha)

		// Inicia el juego
		this.entorno.iniciar();
	}

	public void tick()
	{
	    // Dibujar imagen de fondo
	    entorno.dibujarImagen(fondo, 370, 300, 0);
	 // Fondo del HUD (barra lateral derecha)
	    entorno.dibujarImagen(hud, 700, 300, 0);
	  //barras grises para los textos de stats  
	    entorno.dibujarRectangulo(700, 25, 200, 30, 0, Color.DARK_GRAY);
	    entorno.dibujarRectangulo(700, 123, 200, 30, 0, Color.DARK_GRAY);
	    entorno.dibujarRectangulo(700, 223, 200, 30, 0, Color.DARK_GRAY);
	    entorno.dibujarRectangulo(700, 423, 200, 30, 0, Color.DARK_GRAY);

	    // Texto de stats
	    entorno.cambiarFont("Serif", 20, Color.WHITE);
	    entorno.escribirTexto("El camino de Gondolf", 610, 30);
	    entorno.cambiarFont("Arial", 20, Color.RED);
	    entorno.escribirTexto("Vida", 610, 130);
	    entorno.dibujarImagen(iconVIda, 585, 120, 0);
	    entorno.cambiarFont("Arial", 20, Color.blue);
	    entorno.escribirTexto("Maná", 610, 230);
	    entorno.cambiarFont("Arial", 20, Color.white);
	    entorno.escribirTexto("Enemigos derrotados", 610,430);
	    // Mover y dibujar al mago
	    gondolf.mover(entorno);
	    gondolf.dibujar(entorno);
	    //ROCAS
	    roca.dibujar(entorno);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
