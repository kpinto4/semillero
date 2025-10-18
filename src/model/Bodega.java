package model;

public class Bodega {
    private int idBodega;
    private String nombre;
    private double capacidad;

    public Bodega() {}

    public Bodega(int idBodega, String nombre, double capacidad) {
        this.idBodega = idBodega;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }
}
