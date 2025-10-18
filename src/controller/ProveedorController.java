package controller;

import dao.ProveedorDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import model.Proveedor;
import java.util.List;

public class ProveedorController {

    @FXML
    private TextField nombreField, telefonoField, correoField, ciudadField;
    @FXML
    private CheckBox activoCheckBox;

    @FXML
    private TableView<Proveedor> proveedorTable;
    @FXML
    private TableColumn<Proveedor, Integer> idColumn;
    @FXML
    private TableColumn<Proveedor, String> nombreColumn, telefonoColumn, correoColumn, ciudadColumn;
    @FXML
    private TableColumn<Proveedor, Boolean> activoColumn;

    @FXML
    private Button btnAdd, btnUpdate, btnDelete, btnFetch;

    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private final ObservableList<Proveedor> proveedorList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProveedorProperty().asObject());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        telefonoColumn.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
        correoColumn.setCellValueFactory(cellData -> cellData.getValue().correoProperty());
        ciudadColumn.setCellValueFactory(cellData -> cellData.getValue().ciudadProperty());
        activoColumn.setCellValueFactory(cellData -> cellData.getValue().activoProperty().asObject());

        proveedorTable.setItems(proveedorList);
        fetchProveedores();

        // ✅ Listener para mostrar datos del proveedor seleccionado
        proveedorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                nombreField.setText(newSel.getNombre());
                telefonoField.setText(newSel.getTelefono());
                correoField.setText(newSel.getCorreo());
                ciudadField.setText(newSel.getCiudad());
                activoCheckBox.setSelected(newSel.isActivo());
            }
        });

        // Asignar eventos a los botones
        btnAdd.setOnAction(e -> addProveedor());
        btnUpdate.setOnAction(e -> updateProveedor());
        btnDelete.setOnAction(e -> deleteProveedor());
        btnFetch.setOnAction(e -> fetchProveedores());

        // 🔹 Permitir deseleccionar la fila con la tecla ESC
        proveedorTable.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                proveedorTable.getSelectionModel().clearSelection();
                clearFields();
            }
        });
    }

    // 🔹 Cargar todos los proveedores
    private void fetchProveedores() {
        proveedorList.clear();
        List<Proveedor> proveedores = proveedorDAO.getAll();
        proveedorList.addAll(proveedores);
    }

    // 🔹 Agregar proveedor
    private void addProveedor() {
        String nombre = nombreField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String correo = correoField.getText().trim();
        String ciudad = ciudadField.getText().trim();
        boolean activo = activoCheckBox.isSelected();

        if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || ciudad.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos incompletos", "Por favor complete todos los campos.");
            return;
        }

        Proveedor nuevo = new Proveedor(0, nombre, telefono, correo, ciudad, activo);
        if (proveedorDAO.insert(nuevo)) {
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Proveedor agregado correctamente.");
            clearFields();
            fetchProveedores();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo agregar el proveedor.");
        }
    }

    // 🔹 Actualizar proveedor
    private void updateProveedor() {
        Proveedor seleccionado = proveedorTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            showAlert(Alert.AlertType.WARNING, "Sin selección", "Seleccione un proveedor de la tabla.");
            return;
        }

        seleccionado.setNombre(nombreField.getText().trim());
        seleccionado.setTelefono(telefonoField.getText().trim());
        seleccionado.setCorreo(correoField.getText().trim());
        seleccionado.setCiudad(ciudadField.getText().trim());
        seleccionado.setActivo(activoCheckBox.isSelected());

        if (proveedorDAO.update(seleccionado)) {
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Proveedor actualizado correctamente.");
            clearFields();
            fetchProveedores();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el proveedor.");
        }
    }

    // 🔹 Eliminar proveedor
    private void deleteProveedor() {
        Proveedor seleccionado = proveedorTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            showAlert(Alert.AlertType.WARNING, "Sin selección", "Seleccione un proveedor para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de eliminar este proveedor?");
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (proveedorDAO.delete(seleccionado.getIdProveedor())) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Proveedor eliminado correctamente.");
                    clearFields();
                    fetchProveedores();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el proveedor.");
                }
            }
        });
    }

    // 🔹 Limpiar campos
    private void clearFields() {
        nombreField.clear();
        telefonoField.clear();
        correoField.clear();
        ciudadField.clear();
        activoCheckBox.setSelected(false);
        proveedorTable.getSelectionModel().clearSelection();
    }

    // 🔹 Mostrar alertas
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


