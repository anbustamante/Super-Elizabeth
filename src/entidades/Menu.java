package entidades;


import java.awt.Image;
import javax.sound.sampled.Clip;
import entorno.Entorno;
import entorno.Herramientas;

public class Menu {

	private Image imagenMenu;
	private boolean juegoIniciado;
	private Clip musicaMenu = Herramientas.cargarSonido("sounds/musica-menu.wav");
	private Clip musicaSelect = Herramientas.cargarSonido("sounds/select.wav");
	
	public Menu() {
		this.imagenMenu = Herramientas.cargarImagen("sprites/imagen-menu.png");
		this.juegoIniciado = false;
    }

    public void dibujar(Entorno entorno) {
    	musicaMenu.start();
        entorno.dibujarImagen(imagenMenu, entorno.ancho() / 2, entorno.alto() / 2, 0);
        entorno.cambiarFont("Arial", 24, java.awt.Color.WHITE);
        entorno.escribirTexto("Presiona ENTER para iniciar", entorno.ancho() / 2 - 150, entorno.alto() - 50);
    }

    public void play(Entorno entorno) {
        if (entorno.sePresiono(entorno.TECLA_ENTER)) {
            juegoIniciado = true;
            musicaMenu.stop();
            musicaSelect.start();
        }
    }

    public boolean isJuegoIniciado() {
        return juegoIniciado;
    }
}
