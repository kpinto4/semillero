package dao;

import model.Bodega;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BodegaDAO {

    // CREATE
    public boolean insert(Bodega b) {
        String sql = "INSERT INTO bodega (nombre, capacidad) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, b.getNombre());
            stmt.setDouble(2, b.getCapacidad());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ
    public List<Bodega> getAll() {
        List<Bodega> list = new ArrayList<>();
        String sql = "SELECT * FROM bodega ORDER BY id_bodega";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Bodega(
                        rs.getInt("id_bodega"),
                        rs.getString("nombre"),
                        rs.getDouble("capacidad")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public boolean update(Bodega b) {
        String sql = "UPDATE bodega SET nombre=?, capacidad=? WHERE id_bodega=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, b.getNombre());
            stmt.setDouble(2, b.getCapacidad());
            stmt.setInt(3, b.getIdBodega());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM bodega WHERE id_bodega=?";
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
