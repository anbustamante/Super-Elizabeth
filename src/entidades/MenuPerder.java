package entidades;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class MenuPerder {
	private Image imagenMenuPerder;
	
	public MenuPerder() {
		this.imagenMenuPerder = Herramientas.cargarImagen("sprites/imagen-menu-perder.png");
    }

    public void dibujar(Entorno entorno, int puntaje, int muertes) {
        entorno.dibujarImagen(imagenMenuPerder, entorno.ancho() / 2, entorno.alto() / 2, 0);
        entorno.cambiarFont("Arial", 24, java.awt.Color.WHITE);
        entorno.escribirTexto("Puntaje: " + puntaje, entorno.ancho() / 2 - 60, entorno.alto() - 80);
        entorno.escribirTexto("Dinosaurios muertos: " + muertes, entorno.ancho() / 2 - 115, entorno.alto() - 50);
    }

}
