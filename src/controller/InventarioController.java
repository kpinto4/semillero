package controller;

import dao.BodegaDAO;
import dao.InventarioDAO;
import dao.LoteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import model.Bodega;
import model.Inventario;
import model.Lote;

import java.time.LocalDate;
import java.util.Optional;

public class InventarioController {

    @FXML private ComboBox<Bodega> bodegaComboBox;
    @FXML private ComboBox<Lote> loteComboBox;
    @FXML private DatePicker fechaIngresoPicker;
    @FXML private DatePicker fechaSalidaPicker;
    @FXML private TextField estadoField;
    @FXML private TextField cantidadField;
    @FXML private TextField direccionField;

    @FXML private TableView<Inventario> inventarioTable;
    @FXML private TableColumn<Inventario, Integer> idColumn;
    @FXML private TableColumn<Inventario, String> bodegaColumn;
    @FXML private TableColumn<Inventario, String> loteColumn;
    @FXML private TableColumn<Inventario, LocalDate> fechaIngresoColumn;
    @FXML private TableColumn<Inventario, LocalDate> fechaSalidaColumn;
    @FXML private TableColumn<Inventario, String> estadoColumn;
    @FXML private TableColumn<Inventario, Double> cantidadColumn;
    @FXML private TableColumn<Inventario, String> direccionColumn;

    @FXML private Button btnAdd, btnUpdate, btnDelete, btnFetch;

    private final InventarioDAO inventarioDAO = new InventarioDAO();
    private final BodegaDAO bodegaDAO = new BodegaDAO();
    private final LoteDAO loteDAO = new LoteDAO();

    private final ObservableList<Inventario> inventarioList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idInventario"));
        bodegaColumn.setCellValueFactory(new PropertyValueFactory<>("idBodega"));
        loteColumn.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        fechaIngresoColumn.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));
        fechaSalidaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        direccionColumn.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        inventarioTable.setItems(inventarioList);

        // Cargar combos
        loadComboBoxes();

        // Listeners
        btnAdd.setOnAction(e -> addInventario());
        btnUpdate.setOnAction(e -> updateInventario());
        btnDelete.setOnAction(e -> deleteInventario());
        btnFetch.setOnAction(e -> refreshTable());

        inventarioTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) fillForm(newSel);
        });

        refreshTable();
    }

    private void loadComboBoxes() {
        ObservableList<Bodega> bodegas = FXCollections.observableArrayList(bodegaDAO.getAll());
        bodegaComboBox.setItems(bodegas);
        bodegaComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Bodega b) {
                return b != null ? b.getNombre() : "";
            }
            @Override
            public Bodega fromString(String s) {
                return null;
            }
        });

        ObservableList<Lote> lotes = FXCollections.observableArrayList(loteDAO.getAll());
        loteComboBox.setItems(lotes);
        loteComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Lote l) {
                return l != null ? "Lote #" + l.getIdLote() : "";
            }
            @Override
            public Lote fromString(String s) {
                return null;
            }
        });
    }

    private void addInventario() {
        if (!validateInputs()) return;

        Inventario inv = new Inventario();
        inv.setIdBodega(bodegaComboBox.getValue().getIdBodega());
        inv.setIdLote(loteComboBox.getValue().getIdLote());
        inv.setFechaIngreso(fechaIngresoPicker.getValue());
        inv.setFechaSalida(fechaSalidaPicker.getValue());
        inv.setEstado(estadoField.getText().trim());
        inv.setCantidad(Double.parseDouble(cantidadField.getText().trim()));
        inv.setDireccion(direccionField.getText().trim());

        boolean success = inventarioDAO.insert(inv);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "✅ Inventario agregado correctamente.");
            refreshTable();
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "❌ Error al agregar el inventario.");
        }
    }

    private void updateInventario() {
        Inventario selected = inventarioTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccione un registro para actualizar.");
            return;
        }

        if (!validateInputs()) return;

        selected.setIdBodega(bodegaComboBox.getValue().getIdBodega());
        selected.setIdLote(loteComboBox.getValue().getIdLote());
        selected.setFechaIngreso(fechaIngresoPicker.getValue());
        selected.setFechaSalida(fechaSalidaPicker.getValue());
        selected.setEstado(estadoField.getText().trim());
        selected.setCantidad(Double.parseDouble(cantidadField.getText().trim()));
        selected.setDireccion(direccionField.getText().trim());

        boolean success = inventarioDAO.update(selected);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "✅ Inventario actualizado correctamente.");
            refreshTable();
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "❌ No se pudo actualizar el inventario.");
        }
    }

    private void deleteInventario() {
        Inventario selected = inventarioTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccione un registro para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Está seguro de eliminar el inventario seleccionado?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = inventarioDAO.delete(selected.getIdInventario());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "🗑 Inventario eliminado correctamente.");
                refreshTable();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "❌ Error al eliminar el inventario.");
            }
        }
    }

    private void refreshTable() {
        inventarioList.setAll(inventarioDAO.getAll());
    }

    private void clearFields() {
        bodegaComboBox.getSelectionModel().clearSelection();
        loteComboBox.getSelectionModel().clearSelection();
        fechaIngresoPicker.setValue(null);
        fechaSalidaPicker.setValue(null);
        estadoField.clear();
        cantidadField.clear();
        direccionField.clear();
        inventarioTable.getSelectionModel().clearSelection();
    }

    private void fillForm(Inventario inv) {
        bodegaComboBox.getSelectionModel().select(
                bodegaComboBox.getItems().stream().filter(b -> b.getIdBodega() == inv.getIdBodega()).findFirst().orElse(null)
        );
        loteComboBox.getSelectionModel().select(
                loteComboBox.getItems().stream().filter(l -> l.getIdLote() == inv.getIdLote()).findFirst().orElse(null)
        );
        fechaIngresoPicker.setValue(inv.getFechaIngreso());
        fechaSalidaPicker.setValue(inv.getFechaSalida());
        estadoField.setText(inv.getEstado());
        cantidadField.setText(String.valueOf(inv.getCantidad()));
        direccionField.setText(inv.getDireccion());
    }

    private boolean validateInputs() {
        if (bodegaComboBox.getValue() == null || loteComboBox.getValue() == null ||
            fechaIngresoPicker.getValue() == null || estadoField.getText().isEmpty() ||
            cantidadField.getText().isEmpty() || direccionField.getText().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "⚠️ Todos los campos obligatorios deben estar completos.");
            return false;
        }

        try {
            double cantidad = Double.parseDouble(cantidadField.getText());
            if (cantidad <= 0) {
                showAlert(Alert.AlertType.WARNING, "⚠️ La cantidad debe ser un valor positivo.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "⚠️ La cantidad debe ser un número válido.");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
