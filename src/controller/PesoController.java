package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import service.BodegaService;
import model.Bodega;
import ui.MainApp;

import java.util.List;

public class PesoController {

    @FXML
    private TableView<Bodega> tablaPeso;

    @FXML
    private TableColumn<Bodega, Integer> colIdBodega;

    @FXML
    private TableColumn<Bodega, Double> colPeso;

    @FXML
    private Button volverAlMenu;

    private BodegaService bodegaService = new BodegaService();

    @FXML
    private void initialize() {
        // Configurar columnas
        colIdBodega.setCellValueFactory(new PropertyValueFactory<>("idBodega"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("pesoTotalActivo"));

        // Cargar datos en la tabla
        cargarPesoInventario();
    }

    private void cargarPesoInventario() {
        List<Bodega> listaBodegas = bodegaService.obtenerResumenPesoInventario();
        tablaPeso.setItems(FXCollections.observableArrayList(listaBodegas));
    }

    @FXML
    private void volverAlMenu(ActionEvent event) {
        MainApp.cargarVista("menu.fxml", "Gestión de Inventario - Bodega");
    }
}