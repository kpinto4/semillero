package dao;

import model.Proveedor;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    // CREATE
    public boolean insert(Proveedor p) {
        String sql = "INSERT INTO proveedor (nombre, telefono, correo, ciudad, activo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getTelefono());
            stmt.setString(3, p.getCorreo());
            stmt.setString(4, p.getCiudad());
            stmt.setBoolean(5, p.isActivo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ
    public List<Proveedor> getAll() {
        List<Proveedor> list = new ArrayList<>();
        String sql = "SELECT * FROM proveedor ORDER BY id_proveedor";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Proveedor(
                        rs.getInt("id_proveedor"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("ciudad"),
                        rs.getBoolean("activo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public boolean update(Proveedor p) {
        String sql = "UPDATE proveedor SET nombre=?, telefono=?, correo=?, ciudad=?, activo=? WHERE id_proveedor=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getTelefono());
            stmt.setString(3, p.getCorreo());
            stmt.setString(4, p.getCiudad());
            stmt.setBoolean(5, p.isActivo());
            stmt.setInt(6, p.getIdProveedor());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM proveedor WHERE id_proveedor=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
