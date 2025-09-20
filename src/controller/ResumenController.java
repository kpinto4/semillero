package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import service.BodegaService;
import model.Bodega;
import ui.MainApp;

import java.time.LocalDate;
import java.util.List;

public class ResumenController {

    @FXML
    private TableView<Bodega> resumenTable;

    @FXML
    private TableColumn<Bodega, Integer> colIdBodega;

    @FXML
    private TableColumn<Bodega, String> colLugar;

    @FXML
    private TableColumn<Bodega, Integer> colCantidad;

    @FXML
    private TableColumn<Bodega, Double> colPeso;

    @FXML
    private TableColumn<Bodega, LocalDate> colFecha;
    @FXML
    private TableColumn<Bodega, String> colEstado;

    @FXML
    private Button btnVolver;

    private BodegaService bodegaService = new BodegaService();

    @FXML
    private void initialize() {
        colIdBodega.setCellValueFactory(new PropertyValueFactory<>("idBodega"));
        colLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadProductos"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("pesoTotalActivo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("ultimaEntrega"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoBodega"));

        cargarResumenInventario();
    }

    private void cargarResumenInventario() {
        List<Bodega> resumen = bodegaService.obtenerResumenPesoInventario();
        ObservableList<Bodega> datos = FXCollections.observableArrayList(resumen);
        resumenTable.setItems(datos);
    }

    @FXML
    private void volverMenu(ActionEvent event) {
        MainApp.cargarVista("menu.fxml", "Gestión de Inventario - Bodega");
    }
}