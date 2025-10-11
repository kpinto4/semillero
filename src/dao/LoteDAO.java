package dao;

import model.Lote;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoteDAO {

    // CREATE
    public boolean insert(Lote l) {
        String sql = "INSERT INTO lote (id_producto, id_pedido, peso_total, humedad, fermentacion, fecha_cosecha, fecha_ingreso, fecha_salida, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, l.getIdProducto());
            stmt.setInt(2, l.getIdPedido());
            stmt.setDouble(3, l.getPesoTotal());
            stmt.setDouble(4, l.getHumedad());
            stmt.setDouble(5, l.getFermentacion());
            stmt.setDate(6, l.getFechaCosecha());
            stmt.setDate(7, l.getFechaIngreso());
            stmt.setDate(8, l.getFechaSalida());
            stmt.setString(9, l.getEstado());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ
    public List<Lote> getAll() {
        List<Lote> list = new ArrayList<>();
        String sql = """
                SELECT l.*, p.nombre AS nombre_producto, pe.nombre AS nombre_pedido
                FROM lote l
                JOIN producto p ON l.id_producto = p.id_producto
                JOIN pedido pe ON l.id_pedido = pe.id_pedido
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
                l.setFechaSalida(rs.getDate("fecha_salida"));
                l.setEstado(rs.getString("estado"));
                l.setNombreProducto(rs.getString("nombre_producto"));
                l.setNombrePedido(rs.getString("nombre_pedido"));
                list.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE
    public boolean update(Lote l) {
        String sql = "UPDATE lote SET id_producto=?, id_pedido=?, peso_total=?, humedad=?, fermentacion=?, " +
                     "fecha_cosecha=?, fecha_ingreso=?, fecha_salida=?, estado=? WHERE id_lote=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, l.getIdProducto());
            stmt.setInt(2, l.getIdPedido());
            stmt.setDouble(3, l.getPesoTotal());
            stmt.setDouble(4, l.getHumedad());
            stmt.setDouble(5, l.getFermentacion());
            stmt.setDate(6, l.getFechaCosecha());
            stmt.setDate(7, l.getFechaIngreso());
            stmt.setDate(8, l.getFechaSalida());
            stmt.setString(9, l.getEstado());
            stmt.setInt(10, l.getIdLote());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return false;
    }
}
