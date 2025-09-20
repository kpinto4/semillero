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

    // 🔹 Consulta 1: Peso total de una bodega específica
    private static final String QUERY_PESO =
        "SELECT b.id_bodega, " +
        "       COALESCE(SUM(p.peso * ib.cantidad_por_bodega), 0) AS peso_total_activo " +
        "FROM bodega b " +
        "JOIN inventario_bodega ib ON b.id_bodega = ib.id_bodega " +
        "JOIN inventario i ON ib.id_inventario = i.id_inventario " +
        "JOIN pedido pe ON i.id_pedido = pe.id_pedido " +
        "JOIN producto p ON pe.id_pedido = p.id_pedido " +
        "WHERE pe.recibido = TRUE AND b.id_bodega = ? " +  // 👈 Filtro por bodega
        "GROUP BY b.id_bodega";

    private static final String QUERY_RESUMEN =
    	    "SELECT b.id_bodega, " +
    	    "       b.lugar, " +
    	    "       COUNT(p.id_producto) AS cantidad_total, " +
    	    "       COALESCE(SUM(p.peso * ib.cantidad_por_bodega), 0) AS peso_total_activo, " +
    	    "       MAX(pe.fecha_entrega) AS ultima_entrega, " +
    	    "       CASE " +
    	    "           WHEN ib.id_inventario IS NULL THEN 'No tiene inventario' " +
    	    "           WHEN i.id_pedido IS NULL THEN 'Inventario sin pedido' " +
    	    "           WHEN pe.id_pedido IS NULL THEN 'Pedido no recibido' " +
    	    "           WHEN p.id_producto IS NULL THEN 'Pedido sin productos' " +
    	    "           ELSE 'Todo correcto' " +
    	    "       END AS estado_bodega " +
    	    "FROM bodega b " +
    	    "LEFT JOIN inventario_bodega ib ON b.id_bodega = ib.id_bodega " +
    	    "LEFT JOIN inventario i ON ib.id_inventario = i.id_inventario " +
    	    "LEFT JOIN pedido pe ON i.id_pedido = pe.id_pedido AND pe.recibido = TRUE " +
    	    "LEFT JOIN producto p ON pe.id_pedido = p.id_pedido " +
    	    "GROUP BY b.id_bodega, b.lugar, " +
    	    "         CASE " +
    	    "           WHEN ib.id_inventario IS NULL THEN 'No tiene inventario' " +
    	    "           WHEN i.id_pedido IS NULL THEN 'Inventario sin pedido' " +
    	    "           WHEN pe.id_pedido IS NULL THEN 'Pedido no recibido' " +
    	    "           WHEN p.id_producto IS NULL THEN 'Pedido sin productos' " +
    	    "           ELSE 'Todo correcto' " +
    	    "         END " +
    	    "ORDER BY b.id_bodega";

    // 🔹 Método 1: Obtener peso de inventario de una bodega específica
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

    // 🔹 Método 2: Obtener resumen general de todas las bodegas
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
        	        rs.getString("estado_bodega")  // <- nuevo campo
        	    );
        	    lista.add(bodega);
        	}

           

        } catch (SQLException e) {
            System.err.println("❌ Error al consultar el resumen de inventario: " + e.getMessage());
        }

        return lista;
    }
}

