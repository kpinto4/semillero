package model;

import javafx.beans.property.*;

public class Proveedor {
    private final IntegerProperty idProveedor;
    private final StringProperty nombre;
    private final StringProperty telefono;
    private final StringProperty correo;
    private final StringProperty ciudad;
    private final BooleanProperty activo;

    public Proveedor() {
        this.idProveedor = new SimpleIntegerProperty();
        this.nombre = new SimpleStringProperty();
        this.telefono = new SimpleStringProperty();
        this.correo = new SimpleStringProperty();
        this.ciudad = new SimpleStringProperty();
        this.activo = new SimpleBooleanProperty();
    }

    public Proveedor(int idProveedor, String nombre, String telefono, String correo, String ciudad, boolean activo) {
        this.idProveedor = new SimpleIntegerProperty(idProveedor);
        this.nombre = new SimpleStringProperty(nombre);
        this.telefono = new SimpleStringProperty(telefono);
        this.correo = new SimpleStringProperty(correo);
        this.ciudad = new SimpleStringProperty(ciudad);
        this.activo = new SimpleBooleanProperty(activo);
    }

    // Getters y setters
    public int getIdProveedor() { return idProveedor.get(); }
    public void setIdProveedor(int idProveedor) { this.idProveedor.set(idProveedor); }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }

    public String getTelefono() { return telefono.get(); }
    public void setTelefono(String telefono) { this.telefono.set(telefono); }

    public String getCorreo() { return correo.get(); }
    public void setCorreo(String correo) { this.correo.set(correo); }

    public String getCiudad() { return ciudad.get(); }
    public void setCiudad(String ciudad) { this.ciudad.set(ciudad); }

    public boolean isActivo() { return activo.get(); }
    public void setActivo(boolean activo) { this.activo.set(activo); }

    // Propiedades
    public IntegerProperty idProveedorProperty() { return idProveedor; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty telefonoProperty() { return telefono; }
    public StringProperty correoProperty() { return correo; }
    public StringProperty ciudadProperty() { return ciudad; }
    public BooleanProperty activoProperty() { return activo; }

    // 👇 Agrega esto al final
    @Override
    public String toString() {
        return getNombre();
    }
}
