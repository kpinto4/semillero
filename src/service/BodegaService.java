package service;

import dao.BodegaDAO;
import model.Bodega;
import java.util.List;

public class BodegaService {

    private BodegaDAO bodegaDAO = new BodegaDAO();

    // Método para obtener el resumen de peso por bodega
    public List<Bodega> obtenerResumenPesoInventario() {
        return bodegaDAO.obtenerResumenInventario();
    }

    // Puedes agregar más métodos según lo necesites para otros controladores
}