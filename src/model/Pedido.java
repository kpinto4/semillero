package model;

import java.time.LocalDate;

public class Pedido {

    private int idPedido;
    private int idProveedor;
    private int idProducto; // 🆕 Nuevo campo
    private LocalDate fechaCreacion;
    private boolean recibido;
    private String observaciones;

    // 🆕 Campos auxiliares para mostrar en la interfaz (no se guardan directamente)
    private String nombreProveedor;
    private String nombreProducto;

    // === Constructores ===
    public Pedido() {}

    // Constructor principal (con producto)
    public Pedido(int idPedido, int idProveedor, int idProducto, LocalDate fechaCreacion, boolean recibido, String observaciones) {
        this.idPedido = idPedido;
        this.idProveedor = idProveedor;
        this.idProducto = idProducto;
        this.fechaCreacion = fechaCreacion;
        this.recibido = recibido;
        this.observaciones = observaciones;
    }

    // Constructor alternativo (sin producto, opcional)
    public Pedido(int idPedido, int idProveedor, LocalDate fechaCreacion, boolean recibido, String observaciones) {
        this(idPedido, idProveedor, 0, fechaCreacion, recibido, observaciones);
    }

    // === Getters y Setters ===
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isRecibido() {
        return recibido;
    }

    public void setRecibido(boolean recibido) {
        this.recibido = recibido;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // === Campos auxiliares ===
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    // === Representación textual ===
    @Override
    public String toString() {
        return "Pedido #" + idPedido +
                (nombreProducto != null ? " (" + nombreProducto + ")" : "") +
                " - " + fechaCreacion;
    }
}
