package juego;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import entidades.Princesa;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;	
	private int ticksCounter = 0;
	private Princesa princesa;
	// Variables y métodos propios de cada grupo
	// ...

	Juego() {
		Random rand = new Random();
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, " Super Elizabeth Sis, Volcano Edition - Grupo ... - v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		// Inicia el juego!
		princesa = new Princesa(400, 500);
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
			ticksCounter++;
			princesa.dibujarse(entorno, ticksCounter++);
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
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
