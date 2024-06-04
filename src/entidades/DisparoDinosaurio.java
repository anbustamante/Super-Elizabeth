package entidades;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;


public class DisparoDinosaurio {
    private double x;
    private double y;
    private Image imagen;
    // 0: derecha, 1: izquierda
    private int direccion; 
    private double velocidad = 3; 

    public DisparoDinosaurio(double x, double y, Image imagen, int direccion) {
        this.x = x;
        this.y = y;
        this.imagen = imagen;
        this.direccion = direccion;
    }

    public void mover() {
        if (direccion == 0) {
            x += velocidad;
        } else {
            x -= velocidad;
        }
    }

    public boolean colisionaDisparo(DisparoPrincesa disparoPrincesa) {
        return this.x < disparoPrincesa.getX() + 10 &&
                this.x + 10 > disparoPrincesa.getX() &&
                this.y < disparoPrincesa.getY() + 10 &&
                this.y + 10 > disparoPrincesa.getY();
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagen, x, y, 0, 0.5);
    }

	public double getX() {
		return x;
	}
	
	public double getY() {
        return y;
    }
}