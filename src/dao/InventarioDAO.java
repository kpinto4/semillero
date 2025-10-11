package dao;

import model.Inventario;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    // CREATE
    public boolean insert(Inventario i) {
        String sql = "INSERT INTO inventario (id_bodega, id_lote, fecha_ingreso, fecha_salida, estado, cantidad, direccion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, i.getIdBodega());
            stmt.setInt(2, i.getIdLote());
            stmt.setDate(3, Date.valueOf(i.getFechaIngreso()));
            if (i.getFechaSalida() != null)
                stmt.setDate(4, Date.valueOf(i.getFechaSalida()));
            else
                stmt.setNull(4, Types.DATE);
            stmt.setString(5, i.getEstado());
            stmt.setDouble(6, i.getCantidad());
            stmt.setString(7, i.getDireccion());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ
    public List<Inventario> getAll() {
        List<Inventario> list = new ArrayList<>();
        String sql = "SELECT * FROM inventario ORDER BY id_inventario";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Inventario(
                        rs.getInt("id_inventario"),
                        rs.getInt("id_bodega"),
                        rs.getInt("id_lote"),
                        rs.getDate("fecha_ingreso").toLocalDate(),
                        rs.getDate("fecha_salida") != null ? rs.getDate("fecha_salida").toLocalDate() : null,
                        rs.getString("estado"),
                        rs.getDouble("cantidad"),
                        rs.getString("direccion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public boolean update(Inventario i) {
        String sql = "UPDATE inventario SET id_bodega=?, id_lote=?, fecha_ingreso=?, fecha_salida=?, estado=?, cantidad=?, direccion=? WHERE id_inventario=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, i.getIdBodega());
            stmt.setInt(2, i.getIdLote());
            stmt.setDate(3, Date.valueOf(i.getFechaIngreso()));
            if (i.getFechaSalida() != null)
                stmt.setDate(4, Date.valueOf(i.getFechaSalida()));
            else
                stmt.setNull(4, Types.DATE);
            stmt.setString(5, i.getEstado());
            stmt.setDouble(6, i.getCantidad());
            stmt.setString(7, i.getDireccion());
            stmt.setInt(8, i.getIdInventario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM inventario WHERE id_inventario=?";
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
