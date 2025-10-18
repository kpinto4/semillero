package dao;

import model.Lote;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoteDAO {

    // CREATE
    public boolean insert(Lote l) {
        String sql = """
            INSERT INTO lote (id_producto, id_pedido, peso_total, humedad, fermentacion, 
                              fecha_cosecha, fecha_ingreso, estado)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, l.getIdProducto());
            stmt.setInt(2, l.getIdPedido());
            stmt.setDouble(3, l.getPesoTotal());
            stmt.setDouble(4, l.getHumedad());
            stmt.setDouble(5, l.getFermentacion());
            stmt.setDate(6, l.getFechaCosecha());
            stmt.setDate(7, l.getFechaIngreso());
            stmt.setString(8, l.getEstado());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar lote: " + e.getMessage());
        }
        return false;
    }

    // READ
    public List<Lote> getAll() {
        List<Lote> list = new ArrayList<>();
        String sql = """
            SELECT 
                l.id_lote, 
                l.id_producto, 
                l.id_pedido, 
                l.peso_total, 
                l.humedad, 
                l.fermentacion, 
                l.fecha_cosecha, 
                l.fecha_ingreso, 
                l.estado,
                p.nombre AS nombre_producto,
                ped.id_pedido AS nombre_pedido
            FROM lote l
            JOIN producto p ON l.id_producto = p.id_producto
            JOIN pedido ped ON l.id_pedido = ped.id_pedido
            ORDER BY l.id_lote
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Lote l = new Lote();
                l.setIdLote(rs.getInt("id_lote"));
                l.setIdProducto(rs.getInt("id_producto"));
                l.setIdPedido(rs.getInt("id_pedido"));
                l.setPesoTotal(rs.getDouble("peso_total"));
                l.setHumedad(rs.getDouble("humedad"));
                l.setFermentacion(rs.getDouble("fermentacion"));
                l.setFechaCosecha(rs.getDate("fecha_cosecha"));
                l.setFechaIngreso(rs.getDate("fecha_ingreso"));
                l.setEstado(rs.getString("estado"));
                l.setNombreProducto(rs.getString("nombre_producto"));
                l.setNombrePedido(rs.getString("nombre_pedido"));
                list.add(l);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener lotes: " + e.getMessage());
        }

        return list;
    }

    // UPDATE
    public boolean update(Lote l) {
        String sql = """
            UPDATE lote
            SET id_producto=?, id_pedido=?, peso_total=?, humedad=?, fermentacion=?, 
                fecha_cosecha=?, fecha_ingreso=?, estado=?
            WHERE id_lote=?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, l.getIdProducto());
            stmt.setInt(2, l.getIdPedido());
            stmt.setDouble(3, l.getPesoTotal());
            stmt.setDouble(4, l.getHumedad());
            stmt.setDouble(5, l.getFermentacion());
            stmt.setDate(6, l.getFechaCosecha());
            stmt.setDate(7, l.getFechaIngreso());
            stmt.setString(8, l.getEstado());
            stmt.setInt(9, l.getIdLote());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar lote: " + e.getMessage());
        }
        return false;
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM lote WHERE id_lote=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar lote: " + e.getMessage());
        }

        return false;
    }
}

