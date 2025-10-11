package model;

import java.time.LocalDate;

public class Pedido {
    private int idPedido;
    private int idProveedor;
    private LocalDate fechaCreacion;
    private boolean recibido;
    private String observaciones;

    public Pedido() {}

    public Pedido(int idPedido, int idProveedor, LocalDate fechaCreacion, boolean recibido, String observaciones) {
        this.idPedido = idPedido;
        this.idProveedor = idProveedor;
        this.fechaCreacion = fechaCreacion;
        this.recibido = recibido;
        this.observaciones = observaciones;
    }

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
}
