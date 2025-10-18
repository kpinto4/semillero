package dao;

import model.Pedido;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // CREATE
    public boolean insert(Pedido p) {
        String sql = "INSERT INTO pedido (id_proveedor, id_producto, fecha_creacion, recibido, observaciones) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getIdProveedor());
            stmt.setInt(2, p.getIdProducto()); // nuevo campo
            stmt.setDate(3, Date.valueOf(p.getFechaCreacion()));
            stmt.setBoolean(4, p.isRecibido());
            stmt.setString(5, p.getObservaciones());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ
    public List<Pedido> getAll() {
        List<Pedido> list = new ArrayList<>();
        String sql = """
            SELECT p.id_pedido, p.id_proveedor, p.id_producto, 
                   p.fecha_creacion, p.recibido, p.observaciones,
                   pr.nombre AS nombre_proveedor,
                   prod.nombre AS nombre_producto
            FROM pedido p
            LEFT JOIN proveedor pr ON p.id_proveedor = pr.id_proveedor
            LEFT JOIN producto prod ON p.id_producto = prod.id_producto
            ORDER BY p.id_pedido
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_proveedor"),
                        rs.getInt("id_producto"),
                        rs.getDate("fecha_creacion").toLocalDate(),
                        rs.getBoolean("recibido"),
                        rs.getString("observaciones")
                );

                // (Opcional) Puedes guardar los nombres para mostrar en la tabla
                pedido.setNombreProveedor(rs.getString("nombre_proveedor"));
                pedido.setNombreProducto(rs.getString("nombre_producto"));

                list.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public boolean update(Pedido p) {
        String sql = "UPDATE pedido SET id_proveedor=?, id_producto=?, fecha_creacion=?, recibido=?, observaciones=? WHERE id_pedido=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getIdProveedor());
            stmt.setInt(2, p.getIdProducto());
            stmt.setDate(3, Date.valueOf(p.getFechaCreacion()));
            stmt.setBoolean(4, p.isRecibido());
            stmt.setString(5, p.getObservaciones());
            stmt.setInt(6, p.getIdPedido());

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

