package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ui.MainApp;

public class MenuController {

    @FXML
    private Button btnPeso;

    @FXML
    private Button btnResumen;

    @FXML
    private void abrirPeso(ActionEvent event) {
        MainApp.cargarVista("PesoInventario.fxml", "Peso Inventario");
    }

    @FXML
    private void abrirResumen(ActionEvent event) {
        MainApp.cargarVista("ResumenInventario.fxml", "Resumen Inventario");
    }

    // Ejemplo de cómo manipular los botones desde el controlador
    // Puedes usar estos métodos en el constructor o en otros métodos iniciales
    @FXML
    private void initialize() {
        // Por ejemplo, deshabilitar el botón de resumen al inicio
        // btnResumen.setDisable(true);
    }
}