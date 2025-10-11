package dao;

import model.Pedido;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // CREATE
    public boolean insert(Pedido p) {
        String sql = "INSERT INTO pedido (id_proveedor, fecha_creacion, recibido, observaciones) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdProveedor());
            stmt.setDate(2, Date.valueOf(p.getFechaCreacion()));
            stmt.setBoolean(3, p.isRecibido());
            stmt.setString(4, p.getObservaciones());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ
    public List<Pedido> getAll() {
        List<Pedido> list = new ArrayList<>();
        String sql = "SELECT * FROM pedido ORDER BY id_pedido";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_proveedor"),
                        rs.getDate("fecha_creacion").toLocalDate(),
                        rs.getBoolean("recibido"),
                        rs.getString("observaciones")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public boolean update(Pedido p) {
        String sql = "UPDATE pedido SET id_proveedor=?, fecha_creacion=?, recibido=?, observaciones=? WHERE id_pedido=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdProveedor());
            stmt.setDate(2, Date.valueOf(p.getFechaCreacion()));
            stmt.setBoolean(3, p.isRecibido());
            stmt.setString(4, p.getObservaciones());
            stmt.setInt(5, p.getIdPedido());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM pedido WHERE id_pedido=?";
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
