package entidades;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {
	
	double x;
	double y;
	boolean rompible;
	Image img1;
	Image img2;
	double id;
	
	
	
	public Bloque(double x, double y,boolean rompible) {
		super();
		this.x = x;
		this.y = y;
		this.rompible = rompible;
		this.img1 = Herramientas.cargarImagen("sprites/bloque.png");
		this.img2 = Herramientas.cargarImagen("sprites/bloque-rompible.png");
		this.id = id;

	}
	
	
	public void dibujarse(Entorno entorno)
	{
		if (rompible) {entorno.dibujarImagen(img2, this.x, this.y, 0, 0.17);
			
		}else {
			entorno.dibujarImagen(img1, this.x, this.y, 0, 0.17);
		}
		
	

}}
