package model;

import java.sql.Date;

public class Lote {
    private int idLote;
    private int idProducto;
    private int idPedido;
    private double pesoTotal;
    private double humedad;
    private double fermentacion;
    private Date fechaCosecha;
    private Date fechaIngreso;
    private Date fechaSalida;
    private String estado;

    // Campos adicionales para mostrar nombres (FK)
    private String nombreProducto;
    private String nombrePedido;

    // 🔹 Constructor vacío
    public Lote() {
    }

    // 🔹 Constructor completo
    public Lote(int idLote, int idProducto, int idPedido, double pesoTotal, double humedad,
                double fermentacion, Date fechaCosecha, Date fechaIngreso, Date fechaSalida,
                String estado, String nombreProducto, String nombrePedido) {
        this.idLote = idLote;
        this.idProducto = idProducto;
        this.idPedido = idPedido;
        this.pesoTotal = pesoTotal;
        this.humedad = humedad;
        this.fermentacion = fermentacion;
        this.fechaCosecha = fechaCosecha;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
        this.nombreProducto = nombreProducto;
        this.nombrePedido = nombrePedido;
    }

    // 🔹 Getters y Setters
    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public double getFermentacion() {
        return fermentacion;
    }

    public void setFermentacion(double fermentacion) {
        this.fermentacion = fermentacion;
    }

    public Date getFechaCosecha() {
        return fechaCosecha;
    }

    public void setFechaCosecha(Date fechaCosecha) {
        this.fechaCosecha = fechaCosecha;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombrePedido() {
        return nombrePedido;
    }

    public void setNombrePedido(String nombrePedido) {
        this.nombrePedido = nombrePedido;
    }

    @Override
    public String toString() {
        return "Lote{" +
                "idLote=" + idLote +
                ", idProducto=" + idProducto +
                ", idPedido=" + idPedido +
                ", pesoTotal=" + pesoTotal +
                ", humedad=" + humedad +
                ", fermentacion=" + fermentacion +
                ", fechaCosecha=" + fechaCosecha +
                ", fechaIngreso=" + fechaIngreso +
                ", fechaSalida=" + fechaSalida +
                ", estado='" + estado + '\'' +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", nombrePedido='" + nombrePedido + '\'' +
                '}';
    }
}
