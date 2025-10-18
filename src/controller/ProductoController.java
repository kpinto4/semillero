package controller;

import dao.ProductoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Producto;

public class ProductoController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField descripcionField;

    @FXML
    private TableView<Producto> productoTable;

    @FXML
    private TableColumn<Producto, Integer> idColumn;

    @FXML
    private TableColumn<Producto, String> nombreColumn;

    @FXML
    private TableColumn<Producto, String> descripcionColumn;

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProductoProperty().asObject());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());

        productoTable.setItems(listaProductos);
        fetchProductos();

        // Permitir seleccionar fila para editar
        productoTable.setOnMouseClicked(event -> {
            Producto seleccionado = productoTable.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                nombreField.setText(seleccionado.getNombre());
                descripcionField.setText(seleccionado.getDescripcion());
            }
        });

        // 🆕 Deseleccionar fila con la tecla ESC
        productoTable.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE -> {
                    productoTable.getSelectionModel().clearSelection();
                    clearFields();
                }
            }
        });
    }

    // 🔹 Agregar producto
    @FXML
    private void addProducto() {
        String nombre = nombreField.getText().trim();
        String descripcion = descripcionField.getText().trim();

        if (nombre.isEmpty()) {
            showAlert("Error", "El nombre del producto no puede estar vacío.", Alert.AlertType.WARNING);
            return;
        }

        Producto p = new Producto();
        p.setNombre(nombre);
        p.setDescripcion(descripcion);

        if (productoDAO.insert(p)) {
            showAlert("Éxito", "Producto agregado correctamente.", Alert.AlertType.INFORMATION);
            clearFields();
            fetchProductos();
        } else {
            showAlert("Error", "No se pudo agregar el producto.", Alert.AlertType.ERROR);
        }
    }

    // 🔹 Actualizar producto
    @FXML
    private void updateProducto() {
        Producto seleccionado = productoTable.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            showAlert("Advertencia", "Seleccione un producto para actualizar.", Alert.AlertType.WARNING);
            return;
        }

        seleccionado.setNombre(nombreField.getText().trim());
        seleccionado.setDescripcion(descripcionField.getText().trim());

        if (productoDAO.update(seleccionado)) {
            showAlert("Éxito", "Producto actualizado correctamente.", Alert.AlertType.INFORMATION);
            clearFields();
            fetchProductos();
        } else {
            showAlert("Error", "No se pudo actualizar el producto.", Alert.AlertType.ERROR);
        }
    }

    // 🔹 Eliminar producto
    @FXML
    private void deleteProducto() {
        Producto seleccionado = productoTable.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            showAlert("Advertencia", "Seleccione un producto para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que desea eliminar este producto?");
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (productoDAO.delete(seleccionado.getIdProducto())) {
                    showAlert("Éxito", "Producto eliminado correctamente.", Alert.AlertType.INFORMATION);
                    clearFields();
                    fetchProductos();
                } else {
                    showAlert("Error", "No se pudo eliminar el producto.", Alert.AlertType.ERROR);
                }
            }
        });
    }

    // 🔹 Mostrar todos los productos
    @FXML
    private void fetchProductos() {
        listaProductos.clear();
        listaProductos.addAll(productoDAO.getAll());
    }

    // 🔹 Limpiar campos
    private void clearFields() {
        nombreField.clear();
        descripcionField.clear();
        productoTable.getSelectionModel().clearSelection();
    }

    // 🔹 Mostrar alertas
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
