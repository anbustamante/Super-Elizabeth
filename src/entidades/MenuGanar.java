package entidades;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class MenuGanar {
	private Image imagenMenuGanar;
	
	public MenuGanar() {
		this.imagenMenuGanar = Herramientas.cargarImagen("sprites/imagen-menu-ganar.png");
    }

    public void dibujar(Entorno entorno, int puntaje, int muertes) {
        entorno.dibujarImagen(imagenMenuGanar, entorno.ancho() / 2, entorno.alto() / 2, 0);
        entorno.cambiarFont("Arial", 24, java.awt.Color.WHITE);
        entorno.escribirTexto("Puntaje: " + puntaje, entorno.ancho() / 2 - 60, entorno.alto() - 80);
        entorno.escribirTexto("Dinosaurios muertos: " + muertes, entorno.ancho() / 2 - 115, entorno.alto() - 50);
    }

}
