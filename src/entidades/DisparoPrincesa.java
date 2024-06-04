package entidades;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class DisparoPrincesa {

	private double x;
    private double y;
    private int velocidad;
    private double tamanio;
    private Image img;

    public DisparoPrincesa(double x, double y, boolean direccion, Entorno e) {
        this.x = x + 100;
        this.y = y - 10;
        this.tamanio = 0.6;
        this.img = Herramientas.cargarImagen("sprites/disparo.png");
        this.velocidad = direccion ? 10 : -10;
    }

    public void dibujar(Entorno e) {
        e.dibujarImagen(img, x, y, 0, tamanio);
    }

    public void mover(Entorno e) {
        x += velocidad;
    }

    public boolean llegoAlBordeDeLaPantalla(Entorno e) {
        return x > e.ancho() || x < 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
