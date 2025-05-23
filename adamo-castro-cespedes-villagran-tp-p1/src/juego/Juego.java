package juego;
import java.util.ArrayList;  // Importa ArrayList
import java.awt.Image;

import Obstaculos.Roca;

import java.awt.Color;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego



{
	private Murcielagos generarMurcielagoEnBorde() {
	    int borde = (int)(Math.random() * 4); // 0: izquierda, 1: derecha, 2: arriba, 3: abajo
	    double x = 0;
	    double y = 0;

	    switch (borde) {
	        case 0: // Izquierda
	            x = 30;
	            y = 60 + Math.random() * (630 - 60);
	            break;
	       
	        case 2: // Arriba
	            x = 0 + Math.random() * (615 - 30);
	            y = 0;
	            break;
	        case 3: // Abajo
	            x = 400 + Math.random() * (350 - 30);
	            y = 630;
	            break;
	    }

	    return new Murcielagos(x, y);
	}
	// Lista para almacenar murciélagos
    private ArrayList<Murcielagos> murcielagos;
    private int tiempoGeneracionMurcielagos = 0;  // Temporizador para crear murciélagos
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Image fondo;
	private Gondolf gondolf;
	private Roca[] rocas;
	private Image hud;
	private Image iconVIda;
	private Image iconmana;
	private Murcielagos batman;
	private int golpesRecibidos = 0;
	private boolean juegoPausado = false;
	private int energia = 100; // Maná inicial
	private boolean ataqueNormalActivo  = false;
	private boolean superAtaqueActivo = false;
	private int magiaSeleccionada = 1; // 1 = ataque normal, 2 = súper ataque


	
	 // FUNCION DE COLISION
	private boolean colision(double x1, double y1, double x2, double y2, double rango) {
	    double dx = x1 - x2;
	    double dy = y1 - y2;
	    double distanciaCuadrada = dx * dx + dy * dy;
	    double rangoCuadrado = rango * rango;
	    
	    return distanciaCuadrada < rangoCuadrado;
	}
	

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "TP_GRUPO_13", 800, 600);
		this.fondo = Herramientas.cargarImagen("imagenes/fondo.png");
		this.gondolf = new Gondolf(400, 300);
		this.iconVIda = Herramientas.cargarImagen("imagenes/iconvida1.png");
		this.iconmana = Herramientas.cargarImagen("imagenes/mana1.png");
		//rocas en el mapa
		this.rocas = new Roca[5];
		this.rocas[0] = new Roca(300, 300, 30);
		this.rocas[1] = new Roca(150, 150, 30);
		this.rocas[2] = new Roca(150, 450, 30);
		this.rocas[3] = new Roca(450, 150, 30);
		this.rocas[4] = new Roca(450, 450, 30); 
		
		//imagen del hud(barra lateral derecha)
		this.hud = Herramientas.cargarImagen("imagenes/fondohud.png");
		this.batman = new Murcielagos(0,0);
		
		 // Inicializamos la lista de murciélagos
        this.murcielagos = new ArrayList<Murcielagos>();
        
        // Agregamos el primer murciélago
        this.murcielagos.add(new Murcielagos(100, 400));
		
		// Inicia el juego
		this.entorno.iniciar();
	}
	


	public void tick()
	{
		if (juegoPausado) {
		    entorno.dibujarImagen(fondo, 397, 300, 0);
		    entorno.cambiarFont("Serif", 40, Color.RED);
	        entorno.escribirTexto("¡Gondolf ha sido derrotado!", 170, 300);
	        
	        if (energia <= 0) {
	        	entorno.dibujarImagen(fondo, 397, 300, 0);
	            entorno.cambiarFont("Arial", 30, Color.RED);
	            entorno.escribirTexto("¡Gondolf se quedó sin magia!", 200, 300);
	        }

	        return;
	    }
		// Incrementamos el contador de tiempo
	    tiempoGeneracionMurcielagos++;
	    
	    // Si el contador alcanza un valor determinado (ejemplo, cada 300 frames)
	    if (tiempoGeneracionMurcielagos >= 100 && murcielagos.size() < 10) {
	        murcielagos.add(generarMurcielagoEnBorde());
	        tiempoGeneracionMurcielagos = 0;
	    }

	    // Dibujar imagen de fondo
	    entorno.dibujarImagen(fondo, 370, 300, 0);
	 // Fondo del HUD (barra lateral derecha)
	    entorno.dibujarImagen(hud, 725, 300, 0);
	  //barras grises para los textos de stats  
	    entorno.dibujarRectangulo(700, 25, 200, 30, 0, Color.DARK_GRAY);
	    entorno.dibujarRectangulo(700, 123, 200, 30, 0, Color.DARK_GRAY);
	    entorno.dibujarRectangulo(700, 223, 200, 30, 0, Color.DARK_GRAY);
	    entorno.dibujarRectangulo(700, 423, 200, 30, 0, Color.DARK_GRAY);
	    
	    //magia
	    entorno.cambiarFont("Arial", 20, Color.CYAN);
	    entorno.escribirTexto("Energía: " + energia, 640, 330);
	    
	    Color colorEscudo = (magiaSeleccionada == 1) ? Color.GREEN : Color.BLUE;
	    Color colorExplosión = (magiaSeleccionada == 2) ? Color.YELLOW : Color.RED;


	 // Dibujar botón para ataque normal
	    entorno.dibujarRectangulo(650, 500, 80, 40, 0, colorEscudo);
	    entorno.cambiarFont("Arial", 16, Color.WHITE);
	    entorno.escribirTexto("Ataque", 620, 505);

	    // Dibujar botón para súper ataque
	    entorno.dibujarRectangulo(750, 500, 80, 40, 0, colorExplosión);
	    entorno.cambiarFont("Arial", 16, Color.WHITE);
	    entorno.escribirTexto("Explosión", 720, 505);


	    
	    // Texto de stats
	    entorno.cambiarFont("Serif", 20, Color.WHITE); 
	    entorno.escribirTexto("El camino de Gondolf", 610, 30);
	    entorno.cambiarFont("Arial", 20, Color.RED);
	    entorno.escribirTexto("Vida", 640, 130);
	    entorno.dibujarImagen(iconVIda, 620, 120, 0);
	    entorno.cambiarFont("Arial", 20, Color.blue);
	    entorno.dibujarImagen(iconmana, 620, 230, 0);
	    entorno.escribirTexto("Maná", 640, 230);
	    entorno.cambiarFont("Arial", 20, Color.white);
	    entorno.escribirTexto("Enemigos derrotados", 610,430);
	    
	    // Mover, dibujar al mago y colicionar con rocas
	    boolean bloqueaArriba = false;
	    boolean bloqueaAbajo = false;
	    boolean bloqueaIzquierda = false;
	    boolean bloqueaDerecha = false;

	    for (Roca r : rocas) {
	        if (r.colisionaArriba(gondolf)) bloqueaArriba = true;
	        if (r.colisionaAbajo(gondolf)) bloqueaAbajo = true;
	        if (r.colisionaIzquierda(gondolf)) bloqueaIzquierda = true;
	        if (r.colisionaDerecha(gondolf)) bloqueaDerecha = true;}

	    if ((entorno.estaPresionada('w') || entorno.estaPresionada(entorno.TECLA_ARRIBA)) && !bloqueaArriba) {
	        gondolf.moverArriba();
	    }
	    if ((entorno.estaPresionada('s') || entorno.estaPresionada(entorno.TECLA_ABAJO)) && !bloqueaAbajo) {
	        gondolf.moverAbajo();
	    }
	    if ((entorno.estaPresionada('a') || entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) && !bloqueaIzquierda) {
	        gondolf.moverIzquierda();
	    }
	    if ((entorno.estaPresionada('d') || entorno.estaPresionada(entorno.TECLA_DERECHA)) && !bloqueaDerecha) {
	        gondolf.moverDerecha();
	    }
	 // Ataque normal (clic izquierdo)
	    if (entorno.sePresionoBoton(1)) { 
	        double mouseX = entorno.mouseX();
	        double mouseY = entorno.mouseY();

	        if (mouseX >= 610 && mouseX <= 690 && mouseY >= 480 && mouseY <= 520) {
	            magiaSeleccionada = 1;
	        }
	        if (mouseX >= 710 && mouseX <= 790 && mouseY >= 480 && mouseY <= 520) {
	            magiaSeleccionada = 2;
	        }
	    }
	    
	    if (entorno.sePresionoBoton(3)) { // Clic derecho
	    	if (magiaSeleccionada == 1) {
	    	    ataqueNormalActivo = true;
	    	    System.out.println("ataque normal activado");
	    	    // magiaSeleccionada se mantiene
	        } else if (magiaSeleccionada == 2 && energia >= 20) {
	            superAtaqueActivo = true;
	            energia -= 20;
	            System.out.println("súper ataque activado");
	            magiaSeleccionada = 0; // deselecciona
	        }
	    }
	    
	    if (energia <= 0) {
	        juegoPausado = true;
	        System.out.println("¡Gondolf se quedó sin energía! ");
	    }


	    



	    gondolf.dibujar(entorno);

	
	    if (ataqueNormalActivo) {
	        for (int i = 0; i < murcielagos.size(); i++) {
	            Murcielagos m = murcielagos.get(i);
	            if (m != null && colision(gondolf.getX(), gondolf.getY(), m.getX(), m.getY(), 150)) {
	                murcielagos.remove(i);
	                System.out.println("murciélago eliminado con ataque normal");
	                break; // solo uno
	            }
	        }
	        ataqueNormalActivo = false; // reset
	    }

	    // SUPER ATAQUE, elimina varios en área
	    if (superAtaqueActivo) {
	    	
	    	entorno.dibujarCirculo(gondolf.getX(), gondolf.getY(), 150 * 2, Color.YELLOW);
	        for (int i = murcielagos.size() - 1; i >= 0; i--) {
	            Murcielagos m = murcielagos.get(i);
	            if (m != null && colision(gondolf.getX(), gondolf.getY(), m.getX(), m.getY(), 200)) {
	                murcielagos.remove(i);
	                System.out.println("Murciélago eliminado por explosión");
	            }
	        }
	        superAtaqueActivo = false; // reset
	    }
	    
	    //ROCAS 
	    for (Roca r : rocas) {
	        r.dibujar(entorno);
	    }
	    //batman.mover();	    

	    for (int i = 0; i < murcielagos.size(); i++) {
	        Murcielagos batman = murcielagos.get(i);

	        // Seguir y dibujar
	        batman.seguirGondolf(gondolf.getX(), gondolf.getY());
	        batman.dibujar(entorno);

	        // Detectar colisión
	        if (colision(batman.getX(), batman.getY(), gondolf.getX(), gondolf.getY(), 30)) {
	            golpesRecibidos++;
	            System.out.println("Gondolf fue golpeado: " + golpesRecibidos + " veces");

	            // Hacer el murciélago null
	            murcielagos.set(i, null); // Establecemos el murciélago en null

	            // Crear un nuevo murciélago en la misma posición
	            murcielagos.set(i, generarMurcielagoEnBorde());

	            if (golpesRecibidos >= 5) {
	                juegoPausado = true;
	                System.out.println("¡Gondolf ha sido derrotado! El juego se ha pausado.");
	                break; // No seguir si el juego terminó
	            }
	        }
	    }


	   

	    batman.dibujar(entorno);
	    
	}

	@SuppressWarnings("unused")
	
	
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
