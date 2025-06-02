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
		    int borde = (int)(Math.random() * 4); // 0izq, 1 der, 2arriba, 3 abajo
		    double x = 0;	
		    double y = 0;
	
		    // limite del mago
		    final int xMin = 55;
		    final int xMax = 980;
		    final int yMin = 20;
		    final int yMax = 680;
	
		   
		    final int margen = 30; 
	
		    switch (borde) {
		        case 0: // Izquierda
		            x = xMin - margen; 
		            y = yMin + Math.random() * (yMax - yMin);
		            break;
		        case 1: // Derecha
		            x = xMax + margen;
		            y = yMin + Math.random() * (yMax - yMin);
		            break;
		        case 2: // Arriba
		            x = xMin + Math.random() * (xMax - xMin);
		            y = yMin - margen; 
		            break;
		        case 3: // Abajo
		            x = xMin + Math.random() * (xMax - xMin);
		            y = yMax + margen; 
		            break;
		    }
	
		    return new Murcielagos(x, y);
		}
		
		// Lista para almacenar murciélagos
	    private ArrayList<Murcielagos> murcielagos;
	    private int tiempoGeneracionMurcielagos = 0;  // Temporizador para crear murciélagos
	    
	    
	  //  ArrayList<Pocion> pociones = new ArrayList<>();
		// El objeto Entorno que controla el tiempo y otros
		private Entorno entorno;
	    private int frameActual;
	    private int contadorFrames;
		private Image fondo;
		private Image[] animacionRayo;
		private Image[] animacionPasto;
		private Image[] animacionTierra;
		private Image[] animacionAgua;
		private Image[] bordeRayo;
		private Image[] bordePasto;
		private Image[] bordeTierra;
		private Image[] bordeAgua;
	    private Image iconRayo;  
	    private Image iconTierra;
	    private Image iconAgua;
	    private Image iconPasto;
	    private Image iconMago;
	    private Image[] marcoavatar;
		private Gondolf gondolf;
		private Roca[] rocas;
		private ArrayList<Golem> golems;
		private Image hud;
		private Murcielagos batman;
		private boolean juegoPausado = false;
		private Golem golem;
		private Demon demon;

		String animacionActual = "";
		int frameHechizo = 0;
		int contadorHechizo = 0;
		int xHechizo = 0;
		int yHechizo = 0;
		private int magiaSeleccionada = 0; // 1 = ataque normal, 2 = súper ataque
		private double ataqueX = 0;
		private double ataqueY = 0;
		private int contadorAnimacion;
		int frameRayo = 0;
		int frameAgua = 0;
		int frameTierra = 0;
		int framePasto = 0;

		boolean animandoAgua = false;
		boolean animandoTierra = false;
		boolean animandoRayo = false;
		boolean animandoPasto = false;
		
		
		//rondas 
		private int rondaActual = 1;
		private boolean nuevaRonda = false;
		private int tiempoEsperaRonda = 0;
		private int totalEnemigosGenerados = 0;
		private int totalEnemigosPorRonda = 7;

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
			this.entorno = new Entorno(this, "TP_GRUPO_13", 1360, 720);
			this.gondolf = new Gondolf(400, 300);
			this.golems = new ArrayList<Golem>();
			this.golems.add(new Golem(700, 300));
			this.golems.add(new Golem(200, 190));
			this.golems.add(new Golem(900, 500));
			this.golems.add(new Golem(300, 500));
			this.golems.add(new Golem(200, 600));
			this.demon = new Demon(700, 200);
			
			
			//rocas en el mapa
			this.rocas = new Roca[10];
			int distanciaMinima = 150;
	
			for (int i = 0; i < this.rocas.length; i++) {
			    boolean posicionValida = false;
			    int xAleatorio = 0;
			    int yAleatorio = 0;
	
			    while (!posicionValida) {
			        xAleatorio = 55 + (int)(Math.random() * (980 - 55));
			        yAleatorio = 20 + (int)(Math.random() * (680 - 20));
	
			        posicionValida = true;
	
			        for (int j = 0; j < i; j++) {
			            double dx = xAleatorio - this.rocas[j].getX();
			            double dy = yAleatorio - this.rocas[j].getY();
			            double distancia = Math.sqrt(dx * dx + dy * dy);
	
			            if (distancia < distanciaMinima) {
			                posicionValida = false;
			                break;
			            }
			        }
			    }
	
			    this.rocas[i] = new Roca(xAleatorio, yAleatorio, 30);
			}
			//imagen del hud(barra lateral derecha)
			this.hud = Herramientas.cargarImagen("Ientorno/MARCO.png");
			this.fondo = Herramientas.cargarImagen("Ientorno/MAPA.jpg");
			this.iconTierra= Herramientas.cargarImagen("Iicon/Icon2.png");
			this.iconPasto= Herramientas.cargarImagen("Iicon/Icon1.png");
			this.iconAgua= Herramientas.cargarImagen("Iicon/Icon9.png");
			this.iconRayo= Herramientas.cargarImagen("Iicon/Icon7.png");
			this.iconMago= Herramientas.cargarImagen("Iicon/magoicon.png").getScaledInstance(68, 68, Image.SCALE_SMOOTH);
			this.marcoavatar = new Image[4];
			for (int i = 0; i < 4; i++) {
				this.marcoavatar[i] = Herramientas.cargarImagen("Iicon/" + i + ".png").getScaledInstance(72, 72, Image.SCALE_SMOOTH);
			}
			
			this.bordeRayo = new Image[4];
			for (int i = 0; i < 4; i++) {
				this.bordeRayo[i] = Herramientas.cargarImagen("Iconspell/rayo" + (i+1) + ".png").getScaledInstance(74, 74, Image.SCALE_SMOOTH);
			}
			
			this.bordePasto = new Image[4];
			for (int i = 0; i < 4; i++) {
				this.bordePasto[i] = Herramientas.cargarImagen("Iconspell/pasto" + (i+1) + ".png").getScaledInstance(74, 74, Image.SCALE_SMOOTH);
			}
			
			this.bordeAgua = new Image[4];
			for (int i = 0; i < 4; i++) {
				this.bordeAgua[i] = Herramientas.cargarImagen("Iconspell/agua" + (i+1) + ".png").getScaledInstance(74, 74, Image.SCALE_SMOOTH);
			}
			
			this.bordeTierra= new Image[4];
			for (int i = 0; i < 4; i++) {
				this.bordeTierra[i] = Herramientas.cargarImagen("Iconspell/tierra" + (i+1) + ".png").getScaledInstance(74, 74, Image.SCALE_SMOOTH);
			}
			this.animacionAgua= new Image[10];
			for (int i = 0; i < 4; i++) {
				this.animacionAgua[i] = Herramientas.cargarImagen("Iagua/agua" + (i+1) + ".png").getScaledInstance(130, 130, Image.SCALE_SMOOTH);
			}
			this.animacionRayo= new Image[12];
			for (int i = 0; i < 4; i++) {
				this.animacionRayo[i] = Herramientas.cargarImagen("Irayo/rayo" + (i+1) + ".png").getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			}
			this.animacionTierra= new Image[10];
			for (int i = 0; i < 4; i++) {
				this.animacionTierra[i] = Herramientas.cargarImagen("Itierra/tierra" + (i+1) + ".png").getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			}
			this.animacionPasto= new Image[10];
			for (int i = 0; i < 4; i++) {
				this.animacionPasto[i] = Herramientas.cargarImagen("Ipasto/pasto" + (i+1) + ".png").getScaledInstance(350, 350, Image.SCALE_SMOOTH);
			}
			
			this.batman = new Murcielagos(0,0);
			
			 // Inicializamos la lista de murciélagos
	        this.murcielagos = new ArrayList<Murcielagos>();
	        
	        // Agregamos el primer murciélago
	        this.murcielagos.add(new Murcielagos(100, 400));
			
			// Inicia el juego
			this.entorno.iniciar();
		}
		
	
	
		public void tick(){
		if (juegoPausado) {
		    entorno.dibujarImagen(fondo, 397, 300, 0);
		    entorno.cambiarFont("Serif", 40, Color.RED);
	        entorno.escribirTexto("¡Gondolf ha sido derrotado!", 170, 300);
	        
	        if (gondolf.getMana() <= 0) {
	        	entorno.dibujarImagen(fondo, 397, 300, 0);
	            entorno.cambiarFont("Arial", 30, Color.RED);
	            entorno.escribirTexto("¡Gondolf se quedó sin magia!", 200, 300);
	        }

	        return;
	    }
			// Incrementamos el contador de tiempo
		    tiempoGeneracionMurcielagos++;
		    
		    // Si el contador alcanza un valor determinado (ejemplo, cada 300 frames)
		    if (!nuevaRonda && tiempoGeneracionMurcielagos >= 20 && murcielagos.size() < 10) {
		    	if (totalEnemigosGenerados < totalEnemigosPorRonda) {
		        	murcielagos.add(generarMurcielagoEnBorde());
		        	totalEnemigosGenerados++;
		        	tiempoGeneracionMurcielagos = 0;
		    }
		    }

		    
	
		    // Dibujar imagen de fondo
			entorno.dibujarImagen(fondo, 680, 360, 0);  
			entorno.dibujarImagen(hud, 680, 360, 0);
			entorno.dibujarImagen(iconRayo, 1230, 90, 0);
			entorno.dibujarImagen(iconTierra, 1230, 180, 0);
			entorno.dibujarImagen(iconPasto, 1110, 90, 0);
			entorno.dibujarImagen(iconAgua, 1110, 180, 0);
			entorno.dibujarImagen(iconMago, 1080, 580, 0);
			entorno.dibujarImagen(marcoavatar[frameActual], 1080, 580, 0);
			if (magiaSeleccionada == 1 ) {
			    entorno.dibujarImagen(bordeAgua[frameAgua], 1110, 180, 0);
			}
			if (magiaSeleccionada == 2 && gondolf.getMana() >= 10) {
			    entorno.dibujarImagen(bordeTierra[frameTierra], 1230, 180, 0);
			}
			if (magiaSeleccionada == 4 && gondolf.getMana() >= 20 ) {
			    entorno.dibujarImagen(bordePasto[framePasto], 1110, 90, 0);
			}
			if (magiaSeleccionada == 3 && gondolf.getMana() >= 5) {
			    entorno.dibujarImagen(bordeRayo[frameRayo], 1230, 90, 0);
			}
			contadorAnimacion++;
			if (contadorAnimacion >= 10) {
			    switch (magiaSeleccionada) {
			        case 1:
			        	frameAgua = (frameAgua + 1) % 4;
			            break;
			        case 2:
			            frameTierra = (frameTierra + 1) % 4;
			            break;
			        case 3:
			        	frameRayo = (frameRayo + 1) % 4;
			            break;
			        case 4:
			            framePasto = (framePasto + 1) % 4;
			            break;
			    }
			    contadorAnimacion = 0;
			}
			contadorFrames++;
			if (contadorFrames % 20 == 0) {
			    frameActual = (frameActual + 1) % 4;
			}
		    
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
		    gondolf.dibujar(entorno);
		    
		    
		    //rondas
	        
		     // detecta si la ronda terminó,si no quedan enemigos y no estamos esperando, 
		     //iniciar cuenta para la siguiente ronda
		        
		        if (murcielagos.isEmpty() && totalEnemigosGenerados >= totalEnemigosPorRonda && !nuevaRonda) {
		        	nuevaRonda = true;
		            tiempoEsperaRonda = 120; // 2 segundos si el juego corre a 60 FPS
		        }
		        if (nuevaRonda && rondaActual < 5) {
		            tiempoEsperaRonda--;

		            // muestra mensaje de la proxima ronda
		            entorno.cambiarFont("Arial", 36, Color.WHITE);
		            entorno.escribirTexto("Ronda " + (rondaActual + 1) + "!", 450, 100);
		            //aumenta la el numero de rondas actual
		            if (tiempoEsperaRonda == 0){
		                rondaActual++;// aumenta la cantidad de enemigos, es decir la dificultad
		                totalEnemigosPorRonda += 10;
		               
		                nuevaRonda = false;
		            }
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
		            // Restar vida del mago según daño del murciélago
		            gondolf.setVida(gondolf.getVida() - batman.getDaño());  // restar daño, por ejemplo 5
		            
		            System.out.println("Gondolf perdió vida, le queda: " + gondolf.getVida());
	
		            // Respawnear murciélago
		            
		            murcielagos.set(i, generarMurcielagoEnBorde());
	
		            // Revisar si el mago murió
		            if (gondolf.getVida() <= 0) {
		                juegoPausado = true;
		                System.out.println("¡Gondolf ha muerto! Juego pausado.");
		                break;
		            }
		        }
		        }if (demon.estaVivo()) {
		            demon.seguirGondolf(gondolf.getX(), gondolf.getY());
		            demon.dibujar(entorno);

		            // Colisión con Gondolf
		            if (colision(demon.getX(), demon.getY(), gondolf.getX(), gondolf.getY(), 30)) {
		                gondolf.setVida(gondolf.getVida() - demon.getDaño());
		                System.out.println("¡El demonio golpeó a Gondolf! Vida restante: " + gondolf.getVida());
		                demon.matar(); // Se muere tras el golpe
		            }
		        }
		        
		        
		    batman.dibujar(entorno);
		    //golem
		    for (int i = 0; i < golems.size(); i++) {
		        Golem g = golems.get(i);
		        g.actualizar(gondolf.getX(), gondolf.getY(), gondolf);  // Si tu Golem tiene este método
		        g.dibujar(entorno);
		    }
		    
		    
		    // Barra de vida y mana
		    
		 // Variables para las barras
		    int cuadritoAncho = 11;
		    int cuadritoAlto = 16;
	
		    // Vida
		    int cantidadCuadritosVida = 10;  // 20 cuadritos * 5 de vida = 100 total
		    int cuadritosLlenosVida = Math.round(gondolf.getVida() / 10.0f); 
		    int barraVidaX = 1120;
		    int barraVidaY = 570;
	
		    // Dibuja
		    for (int i = 0; i < cantidadCuadritosVida; i++) {
		        int x = barraVidaX + i * (cuadritoAncho + 2);
		        entorno.dibujarRectangulo(x + cuadritoAncho / 2, barraVidaY, cuadritoAncho, cuadritoAlto, 0, Color.RED.darker());
		    }
		    for (int i = 0; i < cuadritosLlenosVida; i++) {
		        int x = barraVidaX + i * (cuadritoAncho + 2);
		        entorno.dibujarRectangulo(x + cuadritoAncho / 2, barraVidaY, cuadritoAncho, cuadritoAlto, 0, Color.GREEN.darker());
		    }
	
	
		    // Mana
		    int cantidadCuadritosMana = 10;  
		    int cuadritosLlenosMana = Math.round(gondolf.getMana() / 10.0f);
	
		    // Posición barra mana (debajo de la vida)
		    int barraManaX = 1120;
		    int barraManaY = 608;
	
		    // Dibujar 
		    for (int i = 0; i < cantidadCuadritosMana; i++) {
		        int x = barraManaX + i * (cuadritoAncho + 2);
		        entorno.dibujarRectangulo(x + cuadritoAncho / 2, barraManaY, cuadritoAncho, cuadritoAlto, 0, Color.BLUE.darker());
		    }
		    for (int i = 0; i < cuadritosLlenosMana; i++) {
		        int x = barraManaX + i * (cuadritoAncho + 2);
		        entorno.dibujarRectangulo(x + cuadritoAncho / 2, barraManaY, cuadritoAncho, cuadritoAlto, 0, Color.CYAN.darker());
		    }
		    if (entorno.sePresionoBoton(1)) { 
		        double mouseX = entorno.mouseX();
		        double mouseY = entorno.mouseY();

		        // Rayo
		        if (mouseX >= 1110 - 32 && mouseX <= 1110 + 32 &&
		            mouseY >= 180 - 32 && mouseY <= 180 + 32) {
		            magiaSeleccionada = 1;
		        }

		        // Tierra
		        if (mouseX >= 1230 - 32 && mouseX <= 1230 + 32 &&
		            mouseY >= 180 - 32 && mouseY <= 180 + 32) {
		            magiaSeleccionada = 2;
		        }

		        // Pasto
		        if (mouseX >= 1230 - 32 && mouseX <= 1230 + 32 &&
		            mouseY >= 90 - 32 && mouseY <= 90 + 32) {
		            magiaSeleccionada = 3;
		        }

		        // Agua
		        if (mouseX >= 1110 - 32 && mouseX <= 1110 + 32 &&
		            mouseY >= 90 - 32 && mouseY <= 90 + 32) {
		            magiaSeleccionada = 4;
		        }
		    }

		    if (entorno.sePresionoBoton(3)) {
		        ataqueX = entorno.mouseX();
		        ataqueY = entorno.mouseY();
		        
		        if (ataqueX < 55 || ataqueX > 980 || ataqueY < 20 || ataqueY > 680) {
		            System.out.println("¡No podés lanzar hechizos fuera del área de combate!");
		        } else {
		            switch (magiaSeleccionada) {
		                case 1:
		                    animacionActual = "agua";
		                    xHechizo = (int) ataqueX;
		                    yHechizo = (int) ataqueY;
		                    frameHechizo = 0;
		                    contadorHechizo = 0;
		                    break;
		                case 2:
		                    if (gondolf.getMana() >= 10) {
		                        gondolf.restarMana(10);
		                        animacionActual = "tierra";
		                        xHechizo = (int) ataqueX;
		                        yHechizo = (int) ataqueY;
		                        frameHechizo = 0;
		                        contadorHechizo = 0;
		                    } else {
		                        System.out.println("No hay energía para Tierra.");
		                    }
		                    break;
		                case 3:
		                    if (gondolf.getMana() >= 5) {
		                        gondolf.restarMana(5);
		                        animacionActual = "rayo";
		                        xHechizo = (int) ataqueX;
		                        yHechizo = (int) ataqueY;
		                        frameHechizo = 0;
		                        contadorHechizo = 0;
		                    } else {
		                        System.out.println("No hay energía para Rayo.");
		                    }
		                    break;
		                case 4:
		                    if (gondolf.getMana() >= 20) {
		                        gondolf.restarMana(20);
		                        animacionActual = "pasto";
		                        xHechizo = (int) ataqueX;
		                        yHechizo = (int) ataqueY;
		                        frameHechizo = 0;
		                        contadorHechizo = 0;
		                    } else {
		                        System.out.println("No hay energía para Pasto.");
		                    }
		                    break;
		            }

		            magiaSeleccionada = 0;

		            // Aplicar daño
		            for (int i = murcielagos.size() - 1; i >= 0; i--) {
		                Murcielagos m = murcielagos.get(i);
		                if (m == null) continue;

		                boolean dentro = false;
		                switch (animacionActual) {
		                    case "agua":
		                        dentro = colision(ataqueX, ataqueY, m.getX(), m.getY(), 80);
		                        entorno.dibujarCirculo(xHechizo, yHechizo, 160, Color.blue);
		                        break;
		                    case "tierra":
		                        dentro = colision(ataqueX, ataqueY, m.getX(), m.getY(), 100);
		                        entorno.dibujarCirculo(xHechizo, yHechizo, 50, Color.red);
		                        break;
		                    case "rayo":
		                        dentro = colision(ataqueX, ataqueY, m.getX(), m.getY(), 40);
		                        entorno.dibujarCirculo(xHechizo, yHechizo, 20, Color.yellow);
		                        break;
		                    case "pasto":
		                        dentro = colision(ataqueX, ataqueY, m.getX(), m.getY(), 150);
		                        entorno.dibujarCirculo(xHechizo, yHechizo, 75, Color.green);
		                        break;
		                }

		                if (dentro) {
		                    murcielagos.remove(i);
		                    System.out.println("Murciélago eliminado con " + animacionActual);
		                }
		            }
		        }
		    }

		    // Dibujar animación
		    if (animacionActual.equals("agua") && frameHechizo < animacionAgua.length) {
		        entorno.dibujarImagen(animacionAgua[frameHechizo], xHechizo, yHechizo, 0);
		    } else if (animacionActual.equals("tierra") && frameHechizo < animacionTierra.length) {
		        entorno.dibujarImagen(animacionTierra[frameHechizo], xHechizo, yHechizo, 0);
		    } else if (animacionActual.equals("rayo") && frameHechizo < animacionRayo.length) {
		        entorno.dibujarImagen(animacionRayo[frameHechizo], xHechizo, yHechizo, 0);
		    } else if (animacionActual.equals("pasto") && frameHechizo < animacionPasto.length) {
		        entorno.dibujarImagen(animacionPasto[frameHechizo], xHechizo, yHechizo, 0);
		    }

		    // Avanzar animación
		    contadorHechizo++;
		    if (contadorHechizo >= 5) {
		        frameHechizo++;
		        contadorHechizo = 0;

		        boolean finAnimacion =
		            (animacionActual.equals("agua") && frameHechizo >= animacionAgua.length) ||
		            (animacionActual.equals("tierra") && frameHechizo >= animacionTierra.length) ||
		            (animacionActual.equals("rayo") && frameHechizo >= animacionRayo.length) ||
		            (animacionActual.equals("pasto") && frameHechizo >= animacionPasto.length);

		        if (finAnimacion) {
		            animacionActual = "";
		        }
		    }
		}
		
		@SuppressWarnings("unused")
		public static void main(String[] args)
		{
			Juego juego = new Juego();
		}
}
