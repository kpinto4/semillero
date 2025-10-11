package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class AdminMenuController {

    @FXML
    private StackPane adminContentPane;

    // Método genérico para cambiar la vista en el panel central
    private void loadView(String fxmlName) {
        try {
            Node view = FXMLLoader.load(getClass().getResource("/ui/" + fxmlName));
            adminContentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Botones del menú
    @FXML
    private void gestionarProductos() {
        loadView("ProductoView.fxml");
    }

    @FXML
    private void gestionarProveedores() {
        loadView("ProveedorView.fxml");
    }

    @FXML
    private void gestionarPedidos() {
        loadView("PedidoView.fxml");
    }

    @FXML
    private void gestionarLotes() {
        loadView("LoteView.fxml");
    }

    @FXML
    private void gestionarBodegas() {
        loadView("BodegaView.fxml");
    }

    @FXML
    private void gestionarInventario() {
        loadView("InventarioView.fxml");
    }
}
