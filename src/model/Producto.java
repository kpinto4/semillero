package model;

import javafx.beans.property.*;

public class Producto {

    private final IntegerProperty idProducto;
    private final StringProperty nombre;
    private final StringProperty descripcion;

    public Producto() {
        this.idProducto = new SimpleIntegerProperty();
        this.nombre = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
    }

    public Producto(int idProducto, String nombre, String descripcion) {
        this.idProducto = new SimpleIntegerProperty(idProducto);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    // Getters y Setters normales (compatibles con DAO)
    public int getIdProducto() {
        return idProducto.get();
    }

    public void setIdProducto(int idProducto) {
        this.idProducto.set(idProducto);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    // Propiedades (para el TableView)
    public IntegerProperty idProductoProperty() {
        return idProducto;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    @Override
    public String toString() {
        return nombre.get();
    }
}
