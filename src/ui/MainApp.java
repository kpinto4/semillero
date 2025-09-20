package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        cargarVista("menu.fxml", "Gestión de Inventario - Bodega");
    }

    public static void cargarVista(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(rutaFXML));
            Parent root = loader.load();

            // Guardamos tamaño actual de la ventana
            double ancho = primaryStage.getWidth();
            double alto = primaryStage.getHeight();

            primaryStage.setTitle(titulo);
            primaryStage.setScene(new Scene(root, ancho, alto));
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

