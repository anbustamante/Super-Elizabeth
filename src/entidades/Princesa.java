package entidades;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Princesa {
	
	double x;
	double y;
	Image img1;
	Image img2;
	Image img3;
	Image img4;
	int direccion = 4;
	Integer ciclo = 0;
	
	boolean saltando = false;
	double velocidadSalto = 12;
	double gravedad = 0.9;
	double velocidadY = 0;
	double posicionInicialY;
	
	
	
	public Princesa(double x, double y) {
		super();
		this.setX(x);
		this.y = y;
		this.posicionInicialY = y;
		direccion = 4;
		this.img1 = Herramientas.cargarImagen("sprites/princesa-quieta-der.png");
		this.img2 = Herramientas.cargarImagen("sprites/princesa-corriendo-der.png");
		this.img3 = Herramientas.cargarImagen("sprites/princesa-quieta-izq.png");
		this.img4 = Herramientas.cargarImagen("sprites/princesa-corriendo-izq.png");
		
	}

	

	public void dibujarse(Entorno entorno,Integer ticks){
		
		
		if (saltando) {
	        this.y += velocidadY;
	        velocidadY += gravedad;

	        // Si alcanza el suelo
	        if (this.y >= posicionInicialY) {
	            this.y = posicionInicialY;
	            saltando = false;
	        }
	    }
		ciclo = ticks % 40;
		
		if(this.direccion == 0) {
			if(ciclo >= 0 && ciclo < 21) {
		 		this.dibujarDerecha1(entorno);
			}else if(ciclo > 20 && ciclo <=40) {
				this.dibujarDerecha2(entorno);
			}
		}else if(this.direccion == 1){
			if(ciclo >= 0 && ciclo < 21) {
				this.dibujarIzquierda1(entorno);
			}else if(ciclo > 20 && ciclo <=40) {
				this.dibujarIzquierda2(entorno);
			}
		}else if(this.direccion == 4) {
			this.dibujarDerecha1(entorno);
		}
		
		if(this.direccion == 0) {
	        if(ciclo >= 0 && ciclo < 21) {
	            this.dibujarDerecha1(entorno);
	        } else if(ciclo > 20 && ciclo <= 40) {
	            this.dibujarDerecha2(entorno);
	        }
	    } else if(this.direccion == 1) {
	        if(ciclo >= 0 && ciclo < 21) {
	            this.dibujarIzquierda1(entorno);
	        } else if(ciclo > 20 && ciclo <= 40) {
	            this.dibujarIzquierda2(entorno);
	        }
	    } else if(this.direccion == 4) {
	        this.dibujarDerecha1(entorno);
	    }
		
		
	}
	
	public void dibujarDerecha1(Entorno entorno) {
		entorno.dibujarImagen(img1, this.getX(), this.y, 0, 0.08);
	}
	public void dibujarDerecha2(Entorno entorno) {
		entorno.dibujarImagen(img2, this.getX(), this.y, 0, 0.08);
	}
	public void dibujarIzquierda1(Entorno entorno) {
		entorno.dibujarImagen(img3, this.getX(), this.y, 0, 0.08);
	}
	public void dibujarIzquierda2(Entorno entorno) {
		entorno.dibujarImagen(img4, this.getX(), this.y, 0, 0.08);
	}
	
	 
	
	
	
	public void moverDerecha() {
        this.setX(this.getX() + 3);
        this.direccion = 0;
        
    }

    public void moverIzquierda() {
        this.setX(this.getX() - 3);
        this.direccion = 1;
    }

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	
	//Esta funcion es para poder hacer que cuando no se presionen teclas
	//No haga la animacion de estar corriendo
	public void estaQuieta() {
		this.direccion=4;
	}
	 public void saltar() {
	        if (!saltando) {
	            saltando = true;
	            velocidadY = -velocidadSalto;
	        }
	 }

}
