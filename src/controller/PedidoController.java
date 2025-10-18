package controller;

import dao.PedidoDAO;
import dao.ProveedorDAO;
import dao.ProductoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import model.Pedido;
import model.Proveedor;
import model.Producto;

import java.time.LocalDate;
import java.util.Optional;

public class PedidoController {

    @FXML private ComboBox<Proveedor> proveedorComboBox;
    @FXML private ComboBox<Producto> productoComboBox;
    @FXML private DatePicker fechaPicker;
    @FXML private CheckBox recibidoCheckBox;
    @FXML private TextField observacionesField;

    @FXML private TableView<Pedido> pedidoTable;
    @FXML private TableColumn<Pedido, Integer> idColumn;
    @FXML private TableColumn<Pedido, String> proveedorColumn;
    @FXML private TableColumn<Pedido, String> productoColumn;
    @FXML private TableColumn<Pedido, LocalDate> fechaColumn;
    @FXML private TableColumn<Pedido, Boolean> recibidoColumn;
    @FXML private TableColumn<Pedido, String> observacionesColumn;

    @FXML private Button btnAdd, btnUpdate, btnDelete, btnFetch;

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();

    private final ObservableList<Pedido> pedidoList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarComboBoxes();
        pedidoTable.setItems(pedidoList);
        refreshTable();

        // Eventos de botones
        btnAdd.setOnAction(e -> agregarPedido());
        btnUpdate.setOnAction(e -> actualizarPedido());
        btnDelete.setOnAction(e -> eliminarPedido());
        btnFetch.setOnAction(e -> refreshTable());

        // ✅ Selección en tabla
        pedidoTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) llenarCampos(newSel);
        });

        // 🔹 Permitir deseleccionar la fila con la tecla ESC
        pedidoTable.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                pedidoTable.getSelectionModel().clearSelection();
                limpiarCampos();
            }
        });
    }

    // =====================
    // CONFIGURACIÓN TABLA
    // =====================
    private void configurarColumnas() {
        idColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdPedido()).asObject());

        proveedorColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getNombreProveedor()));

        productoColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getNombreProducto()));

        fechaColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getFechaCreacion()));

        recibidoColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleBooleanProperty(data.getValue().isRecibido()).asObject());

        observacionesColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getObservaciones()));
    }

    // =====================
    // CARGAR COMBOBOX
    // =====================
    private void cargarComboBoxes() {
        // Proveedores
        ObservableList<Proveedor> proveedores = FXCollections.observableArrayList(proveedorDAO.getAll());
        proveedorComboBox.setItems(proveedores);
        proveedorComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Proveedor p) {
                return (p != null) ? p.getNombre() : "";
            }

            @Override
            public Proveedor fromString(String s) { return null; }
        });

        // Productos
        ObservableList<Producto> productos = FXCollections.observableArrayList(productoDAO.getAll());
        productoComboBox.setItems(productos);
        productoComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Producto p) {
                return (p != null) ? p.getNombre() : "";
            }

            @Override
            public Producto fromString(String s) { return null; }
        });
    }

    // =====================
    // CRUD
    // =====================
    private void agregarPedido() {
        if (!validarCampos()) return;

        Pedido nuevo = new Pedido();
        nuevo.setIdProveedor(proveedorComboBox.getValue().getIdProveedor());
        nuevo.setIdProducto(productoComboBox.getValue().getIdProducto());
        nuevo.setFechaCreacion(fechaPicker.getValue());
        nuevo.setRecibido(recibidoCheckBox.isSelected());
        nuevo.setObservaciones(observacionesField.getText().trim());

        if (pedidoDAO.insert(nuevo)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "✅ Pedido agregado correctamente.");
            refreshTable();
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "❌ Error al agregar el pedido.");
        }
    }

    private void actualizarPedido() {
        Pedido seleccionado = pedidoTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un pedido para actualizar.");
            return;
        }

        if (!validarCampos()) return;

        seleccionado.setIdProveedor(proveedorComboBox.getValue().getIdProveedor());
        seleccionado.setIdProducto(productoComboBox.getValue().getIdProducto());
        seleccionado.setFechaCreacion(fechaPicker.getValue());
        seleccionado.setRecibido(recibidoCheckBox.isSelected());
        seleccionado.setObservaciones(observacionesField.getText().trim());

        if (pedidoDAO.update(seleccionado)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "✅ Pedido actualizado correctamente.");
            refreshTable();
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "❌ No se pudo actualizar el pedido.");
        }
    }

    private void eliminarPedido() {
        Pedido seleccionado = pedidoTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un pedido para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Está seguro de eliminar este pedido?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (pedidoDAO.delete(seleccionado.getIdPedido())) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "🗑 Pedido eliminado correctamente.");
                refreshTable();
                limpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "❌ Error al eliminar el pedido.");
            }
        }
    }

    // =====================
    // AUXILIARES
    // =====================
    private void refreshTable() {
        pedidoList.setAll(pedidoDAO.getAll());
    }

    private void limpiarCampos() {
        proveedorComboBox.getSelectionModel().clearSelection();
        productoComboBox.getSelectionModel().clearSelection();
        fechaPicker.setValue(null);
        recibidoCheckBox.setSelected(false);
        observacionesField.clear();
        pedidoTable.getSelectionModel().clearSelection();
    }

    private void llenarCampos(Pedido pedido) {
        proveedorComboBox.getSelectionModel().select(
                proveedorComboBox.getItems().stream()
                        .filter(p -> p.getIdProveedor() == pedido.getIdProveedor())
                        .findFirst()
                        .orElse(null)
        );

        productoComboBox.getSelectionModel().select(
                productoComboBox.getItems().stream()
                        .filter(p -> p.getIdProducto() == pedido.getIdProducto())
                        .findFirst()
                        .orElse(null)
        );

        fechaPicker.setValue(pedido.getFechaCreacion());
        recibidoCheckBox.setSelected(pedido.isRecibido());
        observacionesField.setText(pedido.getObservaciones());
    }

    private boolean validarCampos() {
        if (proveedorComboBox.getValue() == null || productoComboBox.getValue() == null || fechaPicker.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "⚠️ Todos los campos obligatorios deben estar completos.");
            return false;
        }
        return true;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
