package model;

import java.time.LocalDate;

public class Bodega {

    private int idBodega;
    private String lugar;
    private int cantidadProductos;
    private double pesoTotalActivo;
    private LocalDate ultimaEntrega;
    private String estadoBodega;

    // 🔹 Constructor para PesoInventario (solo id y peso)
    public Bodega(int idBodega, double pesoTotalActivo) {
        this.idBodega = idBodega;
        this.pesoTotalActivo = pesoTotalActivo;
        this.lugar = null;
        this.cantidadProductos = 0;
        this.ultimaEntrega = null;
        this.estadoBodega = "Datos parciales";
    }

    // 🔹 Constructor completo para ResumenGeneral
    public Bodega(int idBodega, String lugar, int cantidadProductos, double pesoTotalActivo,
                  LocalDate ultimaEntrega, String estadoBodega) {
        this.idBodega = idBodega;
        this.lugar = lugar;
        this.cantidadProductos = cantidadProductos;
        this.pesoTotalActivo = pesoTotalActivo;
        this.ultimaEntrega = ultimaEntrega;
        this.estadoBodega = estadoBodega;
    }

    // 🔹 Getters
    public int getIdBodega() {
        return idBodega;
    }

    public String getLugar() {
        return lugar;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public double getPesoTotalActivo() {
        return pesoTotalActivo;
    }

    public LocalDate getUltimaEntrega() {
        return ultimaEntrega;
    }

    public String getEstadoBodega() {
        return estadoBodega;
    }

    // 🔹 toString completo
    @Override
    public String toString() {
        return "Bodega " + idBodega +
               " | Lugar: " + (lugar != null ? lugar : "N/A") +
               " | Cantidad: " + cantidadProductos +
               " | Peso: " + pesoTotalActivo + " kg" +
               " | Última entrega: " + (ultimaEntrega != null ? ultimaEntrega : "N/A") +
               " | Estado: " + (estadoBodega != null ? estadoBodega : "Desconocido");
    }
}




