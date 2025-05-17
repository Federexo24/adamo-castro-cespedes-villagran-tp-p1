package juego;
import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Image fondo;
	// Variables y métodos propios de cada grupo
	// ...
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "TP_GRUPO_13", 800, 600);
		this.fondo = Herramientas.cargarImagen("imagenes/fondo.png");
		// Inicializar lo que haga falta para el juego
		// ...
		// Inicia el juego!
		this.entorno.iniciar();
	}
	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
	    // Dibujar imagen de fondo
	    entorno.dibujarImagen(fondo, 400, 300, 0);

	    
	   // Aca va el resto del procesamiento del juego (mago, enemigos, etc.)
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
