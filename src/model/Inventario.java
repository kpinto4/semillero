package model;

import java.time.LocalDate;

public class Inventario {
    private int idInventario;
    private int idBodega;
    private int idLote;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String estado;
    private double cantidad;
    private String direccion;

    public Inventario() {}

    public Inventario(int idInventario, int idBodega, int idLote, LocalDate fechaIngreso, LocalDate fechaSalida, String estado, double cantidad, String direccion) {
        this.idInventario = idInventario;
        this.idBodega = idBodega;
        this.idLote = idLote;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
        this.cantidad = cantidad;
        this.direccion = direccion;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
