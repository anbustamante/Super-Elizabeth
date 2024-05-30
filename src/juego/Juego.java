package juego;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import entidades.Bloque;
import entidades.Fondo;
import entidades.Princesa;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;	
	private int ticksCounter = 0;
	private Princesa princesa;
	private Fondo fondo;
	private Bloque[] bloques = new Bloque[600];
	int ANCHO_DE_BLOQUES = 27;
	int SEPARACION = 150;
	// Variables y métodos propios de cada grupo
	// ...

	Juego() {
		Random rand = new Random();
		this.entorno = new Entorno(this, " Super Elizabeth Sis, Volcano Edition - Grupo ... - v1", 800, 600);
		
		
		inicializarBloques(570,27,false);
		
		fondo = new Fondo(400,300);
		princesa = new Princesa(400, 535);
		this.entorno.iniciar();
	}


	public void tick() {
			ticksCounter+=2;
			fondo.dibujarse(entorno);
			princesa.dibujarse(entorno, ticksCounter);
			dibujarPisoSolido();
			movimientosPrincesa();
		
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
		
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			princesa.saltar();
		}
		
		
		
	}
	
	
	
	private void dibujarPisoSolido() {
		for(int i = 0;i<bloques.length-1;i++) {
			if (bloques[i]!=null){
				bloques[i].dibujarse(entorno);
			}
			
			
		}
	}

	private void inicializarBloques(int altura, int cantidad, boolean rompible) {
	    int x = 10;
	    int numFilas = 4; // número de filas de bloques (ajustar según sea necesario)
	    Random r = new Random ();

	    for (int fila = 0; fila < numFilas; fila++) {
	    	ArrayList<Integer> bloquesRompibles = new ArrayList<>();
	    	while (bloquesRompibles.size() < 3) {
                int posicion = r.nextInt(ANCHO_DE_BLOQUES);
                if (!bloquesRompibles.contains(posicion)) {
                	bloquesRompibles.add(posicion);}
	    	
	        for (int i = 0; i < ANCHO_DE_BLOQUES; i++) {
	            int index = fila * ANCHO_DE_BLOQUES + i;
	            int alturaBloque = altura - (SEPARACION * fila);
	            boolean esRompible = bloquesRompibles.contains(i);
	           
	            bloques[index] = new Bloque(x, alturaBloque,esRompible);
	            x += 30;
	        }
	        x = 10; // Resetear x para la siguiente fila
	    }}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	
	
}
