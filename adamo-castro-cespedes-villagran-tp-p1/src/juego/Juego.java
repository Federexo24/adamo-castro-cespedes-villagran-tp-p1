package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Image fondo;
	private Gondolf gondolf;

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "TP_GRUPO_13", 800, 600);
		this.fondo = Herramientas.cargarImagen("imagenes/fondo.png");
		this.gondolf = new Gondolf(400, 300);

		// Inicia el juego
		this.entorno.iniciar();
	}

	public void tick()
	{
	    // Dibujar imagen de fondo
	    entorno.dibujarImagen(fondo, 400, 300, 0);

	    // Mover y dibujar al mago
	    gondolf.mover(entorno);
	    gondolf.dibujar(entorno);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
