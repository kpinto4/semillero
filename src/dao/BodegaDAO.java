package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Bodega;
import util.DatabaseConnection;

public class BodegaDAO {

    // 🔹 Consulta 1: Peso total de inventario de una bodega específica
    private static final String QUERY_PESO =
        "SELECT b.id_bodega, " +
        "       COALESCE(inv.peso_total,0) AS peso_total_activo " +
        "FROM bodega b " +
        "LEFT JOIN ( " +
        "    SELECT ib.id_bodega, SUM(p.peso * ib.cantidad_por_bodega) AS peso_total " +
        "    FROM inventario_bodega ib " +
        "    JOIN inventario i ON ib.id_inventario = i.id_inventario " +
        "    JOIN pedido pe ON i.id_pedido = pe.id_pedido AND pe.recibido = TRUE " +
        "    JOIN producto p ON p.id_pedido = pe.id_pedido " +
        "    GROUP BY ib.id_bodega " +
        ") AS inv ON b.id_bodega = inv.id_bodega " +
        "WHERE b.id_bodega = ? " +
        "ORDER BY b.id_bodega";

    // 🔹 Consulta 2: Resumen general de todas las bodegas (sin duplicados, corregida para PostgreSQL)
    private static final String QUERY_RESUMEN =
        "SELECT b.id_bodega, b.lugar, " +
        "       COUNT(DISTINCT i.id_inventario) AS cantidad_total, " +
        "       COALESCE(SUM(ib.cantidad_por_bodega * p.peso), 0) AS peso_total_activo, " +
        "       MAX(pe.fecha_entrega) AS ultima_entrega, " +
        "       CASE " +
        "           WHEN COUNT(ib.id_inventario) = 0 THEN 'No tiene inventario' " +
        "           WHEN COUNT(i.id_inventario) = 0 THEN 'Inventario sin pedido' " +
        "           WHEN COUNT(pe.id_pedido) = 0 THEN 'Pedido no recibido' " +
        "           ELSE 'Todo correcto' " +
        "       END AS estado_bodega " +
        "FROM bodega b " +
        "LEFT JOIN inventario_bodega ib ON b.id_bodega = ib.id_bodega " +
        "LEFT JOIN inventario i ON ib.id_inventario = i.id_inventario " +
        "LEFT JOIN pedido pe ON i.id_pedido = pe.id_pedido AND pe.recibido = TRUE " +
        "LEFT JOIN producto p ON p.id_pedido = pe.id_pedido " +
        "GROUP BY b.id_bodega, b.lugar " +
        "ORDER BY b.id_bodega";

    // 🔹 Método 1: Consultar peso total por bodega
    public Bodega obtenerPesoTotalPorBodega(int idBodega) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_PESO)) {

            stmt.setInt(1, idBodega);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Bodega(
                    rs.getInt("id_bodega"),
                    rs.getDouble("peso_total_activo")
                );
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al consultar el peso del inventario: " + e.getMessage());
        }
        return null;
    }

    // 🔹 Método 2: Resumen de todas las bodegas
    public List<Bodega> obtenerResumenInventario() {
        List<Bodega> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_RESUMEN);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bodega bodega = new Bodega(
                    rs.getInt("id_bodega"),
                    rs.getString("lugar"),
                    rs.getInt("cantidad_total"),
                    rs.getDouble("peso_total_activo"),
                    rs.getDate("ultima_entrega") != null ? rs.getDate("ultima_entrega").toLocalDate() : null,
                    rs.getString("estado_bodega")
                );
                lista.add(bodega);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al consultar el resumen de inventario: " + e.getMessage());
        }

        return lista;
    }
}

