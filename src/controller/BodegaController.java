package controller;

import dao.BodegaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import model.Bodega;

import java.util.Optional;

public class BodegaController {

    @FXML
    private TextField nombreField, ubicacionField, capacidadField;

    @FXML
    private TableView<Bodega> bodegaTable;
    @FXML
    private TableColumn<Bodega, Integer> idColumn;
    @FXML
    private TableColumn<Bodega, String> nombreColumn;
    @FXML
    private TableColumn<Bodega, String> ubicacionColumn;
    @FXML
    private TableColumn<Bodega, Double> capacidadColumn;

    @FXML
    private Button btnAdd, btnUpdate, btnDelete, btnFetch;

    private final BodegaDAO bodegaDAO = new BodegaDAO();
    private final ObservableList<Bodega> bodegaList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabla();
        cargarBodegas();

        // Listeners de botones
        btnAdd.setOnAction(e -> agregarBodega());
        btnUpdate.setOnAction(e -> actualizarBodega());
        btnDelete.setOnAction(e -> eliminarBodega());
        btnFetch.setOnAction(e -> cargarBodegas());

        // Selección de tabla
        bodegaTable.setOnMouseClicked(e -> {
            Bodega seleccionada = bodegaTable.getSelectionModel().getSelectedItem();
            if (seleccionada != null) {
                nombreField.setText(seleccionada.getNombre());
                ubicacionField.setText(seleccionada.getUbicacion());
                capacidadField.setText(String.valueOf(seleccionada.getCapacidad()));
            }
        });
    }

    private void configurarTabla() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idBodega"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ubicacionColumn.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        capacidadColumn.setCellValueFactory(new PropertyValueFactory<>("capacidad"));

        bodegaTable.setItems(bodegaList);
    }

    private void cargarBodegas() {
        try {
            bodegaList.setAll(bodegaDAO.getAll());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar las bodegas:\n" + e.getMessage());
        }
    }

    private void agregarBodega() {
        if (!validarCampos()) return;

        try {
            Bodega nueva = new Bodega();
            nueva.setNombre(nombreField.getText().trim());
            nueva.setUbicacion(ubicacionField.getText().trim());
            nueva.setCapacidad(Double.parseDouble(capacidadField.getText().trim()));

            bodegaDAO.insert(nueva);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Bodega agregada correctamente.");
            cargarBodegas();
            clearFields();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo agregar la bodega:\n" + e.getMessage());
        }
    }

    private void actualizarBodega() {
        Bodega seleccionada = bodegaTable.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione una bodega para actualizar.");
            return;
        }

        if (!validarCampos()) return;

        try {
            seleccionada.setNombre(nombreField.getText().trim());
            seleccionada.setUbicacion(ubicacionField.getText().trim());
            seleccionada.setCapacidad(Double.parseDouble(capacidadField.getText().trim()));

            bodegaDAO.update(seleccionada);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Bodega actualizada correctamente.");
            cargarBodegas();
            clearFields();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar la bodega:\n" + e.getMessage());
        }
    }

    private void eliminarBodega() {
        Bodega seleccionada = bodegaTable.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione una bodega para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de eliminar la bodega seleccionada?");
        confirmacion.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                bodegaDAO.delete(seleccionada.getIdBodega());
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Bodega eliminada correctamente.");
                cargarBodegas();
                clearFields();
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar la bodega:\n" + e.getMessage());
            }
        }
    }

    private boolean validarCampos() {
        String nombre = nombreField.getText().trim();
        String ubicacion = ubicacionField.getText().trim();
        String capacidadTexto = capacidadField.getText().trim();

        if (nombre.isEmpty() || ubicacion.isEmpty() || capacidadTexto.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor, complete todos los campos.");
            return false;
        }

        try {
            double capacidad = Double.parseDouble(capacidadTexto);
            if (capacidad <= 0) {
                mostrarAlerta(Alert.AlertType.WARNING, "Dato inválido", "La capacidad debe ser un número positivo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Dato inválido", "La capacidad debe ser un número válido.");
            return false;
        }

        return true;
    }

    private void clearFields() {
        nombreField.clear();
        ubicacionField.clear();
        capacidadField.clear();
        bodegaTable.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alerta.showAndWait();
    }
}
