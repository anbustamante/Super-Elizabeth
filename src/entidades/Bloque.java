package entidades;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {
	
	double x;
	double y;
	Image img1;
	double id;
	
	
	
	public Bloque(double x, double y) {
		super();
		this.x = x;
		this.y = y;
		this.img1 = Herramientas.cargarImagen("sprites/bloque.png");
		this.id = id;

	}

	public void dibujarse(Entorno entorno)
	{
		entorno.dibujarImagen(img1, this.x, this.y, 0, 0.17);
	}
	

}
