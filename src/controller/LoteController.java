package controller;

import dao.LoteDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import model.Lote;
import model.Pedido;
import model.Producto;

import java.sql.Date;
import java.util.Optional;

public class LoteController {

    @FXML
    private ComboBox<Producto> productoComboBox;
    @FXML
    private ComboBox<Pedido> pedidoComboBox;
    @FXML
    private TextField pesoField, humedadField, fermentacionField;
    @FXML
    private DatePicker cosechaPicker, ingresoPicker;
    @FXML
    private ComboBox<String> estadoCombo;
    @FXML
    private TableView<Lote> loteTable;
    @FXML
    private TableColumn<Lote, Integer> idColumn;
    @FXML
    private TableColumn<Lote, String> productoColumn;
    @FXML
    private TableColumn<Lote, String> pedidoColumn;
    @FXML
    private TableColumn<Lote, Double> pesoColumn;
    @FXML
    private TableColumn<Lote, Double> humedadColumn;
    @FXML
    private TableColumn<Lote, Double> fermentacionColumn;
    @FXML
    private TableColumn<Lote, String> estadoColumn;
    @FXML
    private Button btnAdd, btnUpdate, btnDelete, btnFetch;

    private final LoteDAO loteDAO = new LoteDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final ObservableList<Lote> loteList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabla();
        cargarCombos();
        cargarLotes();

        estadoCombo.setItems(FXCollections.observableArrayList("En Proceso", "Almacenado", "Despachado"));

        btnAdd.setOnAction(e -> agregarLote());
        btnUpdate.setOnAction(e -> actualizarLote());
        btnDelete.setOnAction(e -> eliminarLote());
        btnFetch.setOnAction(e -> cargarLotes());

        // Evento al seleccionar fila
        loteTable.setOnMouseClicked(e -> {
            Lote seleccionado = loteTable.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                productoComboBox.setValue(buscarProductoPorId(seleccionado.getIdProducto()));
                pedidoComboBox.setValue(buscarPedidoPorId(seleccionado.getIdPedido()));
                pesoField.setText(String.valueOf(seleccionado.getPesoTotal()));
                humedadField.setText(String.valueOf(seleccionado.getHumedad()));
                fermentacionField.setText(String.valueOf(seleccionado.getFermentacion()));
                cosechaPicker.setValue(seleccionado.getFechaCosecha() != null ? seleccionado.getFechaCosecha().toLocalDate() : null);
                ingresoPicker.setValue(seleccionado.getFechaIngreso() != null ? seleccionado.getFechaIngreso().toLocalDate() : null);
                estadoCombo.setValue(seleccionado.getEstado());
            }
        });

        // 🔹 Permitir deseleccionar la fila con la tecla ESC
        loteTable.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                loteTable.getSelectionModel().clearSelection();
                clearFields();
            }
        });
    }

    private void configurarTabla() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        productoColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        pedidoColumn.setCellValueFactory(new PropertyValueFactory<>("nombrePedido"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("pesoTotal"));
        humedadColumn.setCellValueFactory(new PropertyValueFactory<>("humedad"));
        fermentacionColumn.setCellValueFactory(new PropertyValueFactory<>("fermentacion"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        loteTable.setItems(loteList);
    }

    private void cargarLotes() {
        try {
            loteList.setAll(loteDAO.getAll());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los lotes:\n" + e.getMessage());
        }
    }

    private void cargarCombos() {
        try {
            productoComboBox.setItems(FXCollections.observableArrayList(productoDAO.getAll()));
            pedidoComboBox.setItems(FXCollections.observableArrayList(pedidoDAO.getAll()));
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los combos:\n" + e.getMessage());
        }
    }

    private void agregarLote() {
        if (!validarCampos()) return;

        try {
            Lote nuevo = new Lote();
            nuevo.setIdProducto(productoComboBox.getValue().getIdProducto());
            nuevo.setIdPedido(pedidoComboBox.getValue().getIdPedido());
            nuevo.setPesoTotal(Double.parseDouble(pesoField.getText().trim()));
            nuevo.setHumedad(Double.parseDouble(humedadField.getText().trim()));
            nuevo.setFermentacion(Double.parseDouble(fermentacionField.getText().trim()));
            nuevo.setFechaCosecha(Date.valueOf(cosechaPicker.getValue()));
            nuevo.setFechaIngreso(Date.valueOf(ingresoPicker.getValue()));
            nuevo.setEstado(estadoCombo.getValue());

            if (loteDAO.insert(nuevo)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Lote agregado correctamente.");
                cargarLotes();
                clearFields();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo agregar el lote.");
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo agregar el lote:\n" + e.getMessage());
        }
    }

    private void actualizarLote() {
        Lote seleccionado = loteTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un lote para actualizar.");
            return;
        }
        if (!validarCampos()) return;

        try {
            seleccionado.setIdProducto(productoComboBox.getValue().getIdProducto());
            seleccionado.setIdPedido(pedidoComboBox.getValue().getIdPedido());
            seleccionado.setPesoTotal(Double.parseDouble(pesoField.getText().trim()));
            seleccionado.setHumedad(Double.parseDouble(humedadField.getText().trim()));
            seleccionado.setFermentacion(Double.parseDouble(fermentacionField.getText().trim()));
            seleccionado.setFechaCosecha(Date.valueOf(cosechaPicker.getValue()));
            seleccionado.setFechaIngreso(Date.valueOf(ingresoPicker.getValue()));
            seleccionado.setEstado(estadoCombo.getValue());

            if (loteDAO.update(seleccionado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Lote actualizado correctamente.");
                cargarLotes();
                clearFields();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el lote.");
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el lote:\n" + e.getMessage());
        }
    }

    private void eliminarLote() {
        Lote seleccionado = loteTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un lote para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Desea eliminar el lote seleccionado?");
        confirm.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (loteDAO.delete(seleccionado.getIdLote())) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Lote eliminado correctamente.");
                    cargarLotes();
                    clearFields();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el lote.");
                }
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el lote:\n" + e.getMessage());
            }
        }
    }

    private boolean validarCampos() {
        if (productoComboBox.getValue() == null || pedidoComboBox.getValue() == null ||
                pesoField.getText().isEmpty() || humedadField.getText().isEmpty() ||
                fermentacionField.getText().isEmpty() || cosechaPicker.getValue() == null ||
                ingresoPicker.getValue() == null || estadoCombo.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor, complete todos los campos.");
            return false;
        }

        try {
            Double.parseDouble(pesoField.getText());
            Double.parseDouble(humedadField.getText());
            Double.parseDouble(fermentacionField.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos inválidos", "Peso, humedad y fermentación deben ser números.");
            return false;
        }

        return true;
    }

    private Producto buscarProductoPorId(int id) {
        return productoComboBox.getItems().stream()
                .filter(p -> p.getIdProducto() == id)
                .findFirst().orElse(null);
    }

    private Pedido buscarPedidoPorId(int id) {
        return pedidoComboBox.getItems().stream()
                .filter(p -> p.getIdPedido() == id)
                .findFirst().orElse(null);
    }

    private void clearFields() {
        productoComboBox.setValue(null);
        pedidoComboBox.setValue(null);
        pesoField.clear();
        humedadField.clear();
        fermentacionField.clear();
        cosechaPicker.setValue(null);
        ingresoPicker.setValue(null);
        estadoCombo.setValue(null);
        loteTable.getSelectionModel().clearSelection();
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
