package juego;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.Clip;

import entidades.Bloque;
import entidades.Dinosaurio;
import entidades.DisparoDinosaurio;
import entidades.DisparoPrincesa;
import entidades.Fondo;
import entidades.Menu;
import entidades.MenuGanar;
import entidades.MenuPerder;
import entidades.Princesa;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	/* El objeto Entorno que controla el tiempo y otros*/
	private Entorno entorno;	
	private int ticksCounter = 0;
	private Princesa princesa;
	private DisparoPrincesa disparoPrincesa;
    private List<Dinosaurio> dinosaurios; 
	private Fondo fondo;
	private Menu menu;
	private MenuPerder MenuPerder;
	private MenuGanar MenuGanar;
	private int puntaje;
	private int muertes;
    private boolean enJuego;
    public int tipoMenu = 0;
    private Clip musicaJuego = Herramientas.cargarSonido("sounds/musica-juego.wav");
	private Bloque[] bloques = new Bloque[600];
	int ANCHO_DE_BLOQUES = 27;
	int SEPARACION = 140;
	// Variables y métodos propios de cada grupo
	// ...
	
	/*disparos dinosaurio*/
	private int tiempoDisparos = 0;
    private int tiempoEntreDisparosDinosaurio = 200;
    private List<DisparoDinosaurio> disparosActivosDinosaurio;

	Juego() {
		Random rand = new Random();
		this.entorno = new Entorno(this, " Super Elizabeth Sis, Volcano Edition - Grupo ... - v1", 800, 600);
		
		this.puntaje = 0;
		
		/*Carga menu*/
        menu = new Menu();
        MenuPerder = new MenuPerder();
		MenuGanar = new MenuGanar();
        enJuego = false;
        
        
		inicializarBloques(570,27,false);
		
		fondo = new Fondo(400,300);
		princesa = new Princesa(400, 535, this);
		dinosaurios = new ArrayList<>();
		disparosActivosDinosaurio = new ArrayList<>();

        inicializarDinosaurios();
		
		this.entorno.iniciar();
	}


	public void tick() {
		
		if (!enJuego) {
	        if (tipoMenu == 0) {
	            menu.dibujar(entorno);
	            menu.play(entorno);
	            if (menu.isJuegoIniciado()) {
	                enJuego = true;
	                musicaJuego.start();
	            }
	        } else if (tipoMenu == 1) {
	            MenuPerder.dibujar(entorno, puntaje, muertes);
	        } else if (tipoMenu == 2) {
	            MenuGanar.dibujar(entorno, puntaje, muertes);
	        }
	    } else {
	        
			ticksCounter += 2;
			fondo.dibujarse(entorno);
			entorno.cambiarFont("Arial", 20, java.awt.Color.WHITE);
	        entorno.escribirTexto("Puntaje: " + puntaje, entorno.ancho() / 4 - 180, entorno.alto() - 555);
	        entorno.escribirTexto("Dinosaurios muertos: " + muertes, entorno.ancho() / 4 - 180, entorno.alto() - 535);
	        
			if (princesa.estaViva()) {
				if (princesa.getY() <= 0) {
			        /*evalua si gana*/
					enJuego = false;
			        tipoMenu = 2;
			    }
				princesa.dibujarse(entorno, ticksCounter);
				movimientosPrincesa();
				princesa.aplicarGravedad(bloques);
			}
			dibujarPisoSolido();

			for (Dinosaurio dinosaurio : dinosaurios) {
				dinosaurio.dibujarse(entorno, ticksCounter);
				dinosaurio.aplicarGravedad(bloques);
			}

			if (princesa.estaViva()) {
				princesa.dibujarDisparos(entorno, dinosaurios);
			}
			
			
			tiempoDisparos++;
	        if (tiempoDisparos >= tiempoEntreDisparosDinosaurio) {
	            /* Dispara para cada dinosaurio en la lista*/
	            for (Dinosaurio dinosaurio : dinosaurios) {
	                dinosaurio.disparar(disparosActivosDinosaurio);
	            }
	            tiempoDisparos = 0;
	        }
			
			for (DisparoDinosaurio disparo : disparosActivosDinosaurio) {
	            disparo.mover();
	            disparo.dibujar(entorno);
	        }

		
			
			detectarColisiones();
			detectarColisionesDisparos();
		}
	}

	private void movimientosPrincesa() {
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) && princesa.getX() < 780){
		    princesa.moverDerecha();
		}
		
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && princesa.getX() > 20) {
		    princesa.moverIzquierda();
		}
		
		if (!entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && !entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			princesa.estaQuieta();
		}
		
		if (entorno.estaPresionada('x')|| (entorno.estaPresionada(entorno.TECLA_ARRIBA))) {
			princesa.saltar();
		}else{
			princesa.liberarTeclaSalto();
		}
		if (entorno.sePresiono('c')|| (entorno.estaPresionada(entorno.TECLA_ESPACIO))) {
			princesa.disparar(entorno);

		}

		if (!entorno.estaPresionada('c')&& (!entorno.estaPresionada(entorno.TECLA_ESPACIO))) {
			princesa.resetDisparo(); // Permitir disparar de nuevo al soltar la tecla
		}
		
	}

	private void detectarColisiones() {
		for (int i = 0; i < bloques.length; i++) {
			Bloque bloque = bloques[i];
			if (bloque != null && colisiona(princesa, bloque)) {
				if (princesa.estaSaltando()) {
					if (bloque.esRompible()) {
						bloques[i] = null; // Destruir el bloque rompible
						princesa.aterrizar(bloque.getY() - 30); // Ajustar posición de la princesa
					} else if (princesa.getY() < bloque.getY()) {
						princesa.aterrizar(bloque.getY() - 30); // Ajustar posición de la princesa
					} else {
						princesa.detenerSalto(bloque.getY()); // Detener el salto si colisiona desde abajo
					}
				}
			}
		}
		for (Dinosaurio dinosaurio : dinosaurios) {
			if (colisiona(princesa, dinosaurio)) {
				princesa.muerta();
				tipoMenu =1;
				enJuego = false; 
				break;
			}
			
		}
		
		// Verificar colisiones con los disparos de los dinosaurios
	    for (DisparoDinosaurio disparo : disparosActivosDinosaurio) {
	        if (colisiona(princesa, disparo)) {
	            princesa.muerta();
	            tipoMenu = 1;
	            enJuego = false;
	            break;
	        }
	    }
	    
	}
	private void detectarColisionesDisparos() {
		List<DisparoPrincesa> disparosPrincesaAEliminar = new ArrayList<>();
		List<DisparoDinosaurio> disparosDinosaurioAEliminar = new ArrayList<>();

		for (DisparoPrincesa disparoPrincesa : princesa.getDisparosActivos()) {
			for (DisparoDinosaurio disparoDinosaurio : disparosActivosDinosaurio) {
				if (disparoPrincesa.colisionaBomba(disparoDinosaurio)) {
					disparosPrincesaAEliminar.add(disparoPrincesa);
					disparosDinosaurioAEliminar.add(disparoDinosaurio);
				}
			}
		}

		// Eliminar disparos colisionados
		princesa.getDisparosActivos().removeAll(disparosPrincesaAEliminar);
		disparosActivosDinosaurio.removeAll(disparosDinosaurioAEliminar);
	}


	private boolean colisiona(Princesa princesa, Bloque bloque) {
	        return princesa.getX() < bloque.getX() + 30 &&
	                princesa.getX() + 30 > bloque.getX() &&
	                princesa.getY() < bloque.getY() + 30 &&
	                princesa.getY() + 30 > bloque.getY();
	    }
	private boolean colisiona(Princesa princesa, Dinosaurio dinosaurio) {
		return princesa.getX() < dinosaurio.getX() + 30 &&
				princesa.getX() + 30 > dinosaurio.getX() &&
				princesa.getY() < dinosaurio.getY() + 30 &&
				princesa.getY() + 30 > dinosaurio.getY();
	}

	private boolean colisiona(Princesa princesa, DisparoDinosaurio disparo) {
	    return princesa.getX() < disparo.getX() + 30 &&
	            princesa.getX() + 30 > disparo.getX() &&
	            princesa.getY() < disparo.getY() + 30 &&
	            princesa.getY() + 30 > disparo.getY();
	}
	
	
	
	    private void dibujarPisoSolido() {
	        for (Bloque bloque : bloques) {
	            if (bloque != null) {
	                bloque.dibujarse(entorno);
	            }
	        }
	    }

	    private void inicializarBloques(int altura, int cantidad, boolean rompible) {
	        int x = 10;
	        int numFilas = 5;
	        Random r = new Random();

	        for (int fila = 0; fila < numFilas; fila++) {
	            int posicionInicio = r.nextInt(ANCHO_DE_BLOQUES - 2); // Posición de inicio para los 3 bloques consecutivos

	            for (int i = 0; i < ANCHO_DE_BLOQUES; i++) {
	                int index = fila * ANCHO_DE_BLOQUES + i;
	                int alturaBloque = altura - (SEPARACION * fila);
	                boolean esRompible = (i >= posicionInicio && i < posicionInicio + 3);
	                bloques[index] = new Bloque(x, alturaBloque, esRompible);
	                x += 30;
	            }
	            x = 10;
	        }
	    }
	    
	    private void inicializarDinosaurios() {
	    	// Distribuye 2 dinosaurios por piso en los pisos superiores (excepto el piso inferior)
	    	Random rand = new Random();
	        int numFilas = 4;
	        int dinosPorPiso = 2;
	        int alturaInicial = 570;
	        int anchoMaximo = 780;
	        int margen = 20;
	        
	        for (int fila = 1; fila < numFilas; fila++) { 
	            int altura = alturaInicial - (SEPARACION * fila);
	            for (int i = 0; i < dinosPorPiso; i++) {
	                int xPos = rand.nextInt(anchoMaximo - 2 * margen) + margen; 
	                dinosaurios.add(new Dinosaurio(xPos, altura - 30));
	            }
	        }
	    }
	    
	    public void incrementarPuntaje() {
	        puntaje+=2;
	        muertes++;
	    }
	    
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	
	
}
