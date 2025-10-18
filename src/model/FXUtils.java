package model;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import java.time.LocalDate;

public class FXUtils {

    // =========================
    // MÉTODOS DE LIMPIEZA BASE
    // =========================
    public static <T> void clearSelectionAndFields(TableView<T> table, TextField... fields) {
        table.getSelectionModel().clearSelection();
        for (TextField field : fields) {
            if (field != null) field.clear();
        }
    }

    public static void clearComboBox(ComboBox<?>... comboBoxes) {
        for (ComboBox<?> comboBox : comboBoxes) {
            if (comboBox != null) comboBox.getSelectionModel().clearSelection();
        }
    }

    public static void clearDatePicker(DatePicker... pickers) {
        for (DatePicker picker : pickers) {
            if (picker != null) picker.setValue(null);
        }
    }

    // =====================================
    // MÉTODOS ESPECÍFICOS POR CADA ENTIDAD
    // =====================================

    // ---- Bodega ----
    public static <T> void clearSelectionAndFieldsBodega(TableView<T> table, TextField idField, TextField nombreField, TextField capacidadField) {
        clearSelectionAndFields(table, idField, nombreField, capacidadField);
        if (idField != null) idField.setDisable(false);
    }

    // ---- Inventario ----
    public static <T> void clearSelectionAndFieldsInventario(
            TableView<T> table,
            TextField idInventarioField,
            ComboBox<?> bodegaComboBox,
            ComboBox<?> loteComboBox,
            DatePicker fechaIngresoPicker,
            DatePicker fechaSalidaPicker,
            ComboBox<String> estadoComboBox,
            TextField cantidadField,
            TextField direccionField) {

        table.getSelectionModel().clearSelection();
        if (idInventarioField != null) idInventarioField.clear();
        clearComboBox(bodegaComboBox, loteComboBox, estadoComboBox);
        clearDatePicker(fechaIngresoPicker, fechaSalidaPicker);
        if (cantidadField != null) cantidadField.clear();
        if (direccionField != null) direccionField.clear();

        if (idInventarioField != null) idInventarioField.setDisable(false);
    }

    // ---- Lote ----
    public static <T> void clearSelectionAndFieldsLote(
            TableView<T> table,
            TextField idLoteField,
            ComboBox<?> productoComboBox,
            ComboBox<?> pedidoComboBox,
            TextField pesoField,
            TextField humedadField,
            TextField fermentacionField,
            DatePicker fechaCosechaPicker,
            DatePicker fechaIngresoPicker,
            ComboBox<String> estadoComboBox) {

        table.getSelectionModel().clearSelection();
        if (idLoteField != null) idLoteField.clear();
        clearComboBox(productoComboBox, pedidoComboBox, estadoComboBox);
        clearDatePicker(fechaCosechaPicker, fechaIngresoPicker);
        if (pesoField != null) pesoField.clear();
        if (humedadField != null) humedadField.clear();
        if (fermentacionField != null) fermentacionField.clear();

        if (idLoteField != null) idLoteField.setDisable(false);
    }

    // ---- Pedido ----
    public static <T> void clearSelectionAndFieldsPedido(
            TableView<T> table,
            TextField idPedidoField,
            ComboBox<?> proveedorComboBox,
            ComboBox<?> productoComboBox,
            DatePicker fechaPicker,
            CheckBox recibidoCheck,
            TextArea observacionesArea) {

        table.getSelectionModel().clearSelection();
        if (idPedidoField != null) idPedidoField.clear();
        clearComboBox(proveedorComboBox, productoComboBox);
        clearDatePicker(fechaPicker);
        if (recibidoCheck != null) recibidoCheck.setSelected(false);
        if (observacionesArea != null) observacionesArea.clear();

        if (idPedidoField != null) idPedidoField.setDisable(false);
    }

    // ---- Producto ----
    public static <T> void clearSelectionAndFieldsProducto(TableView<T> table, TextField idField, TextField nombreField, TextField descripcionField) {
        clearSelectionAndFields(table, idField, nombreField, descripcionField);
        if (idField != null) idField.setDisable(false);
    }

    // ---- Proveedor ----
    public static <T> void clearSelectionAndFieldsProveedor(
            TableView<T> table,
            TextField idField,
            TextField nombreField,
            TextField telefonoField,
            TextField correoField,
            TextField ciudadField,
            CheckBox activoCheck) {

        table.getSelectionModel().clearSelection();
        if (idField != null) idField.clear();
        if (nombreField != null) nombreField.clear();
        if (telefonoField != null) telefonoField.clear();
        if (correoField != null) correoField.clear();
        if (ciudadField != null) ciudadField.clear();
        if (activoCheck != null) activoCheck.setSelected(false);

        if (idField != null) idField.setDisable(false);
    }

    // ========================================================
    // MANEJO DE ESCAPE Y DOBLE CLIC PARA LIMPIAR AUTOMÁTICAMENTE
    // ========================================================
    public static <T> void setupClearOnEscapeAndDoubleClick(
            TableView<T> table,
            Runnable clearAction) {

        // 🔹 Limpiar con tecla ESC
        table.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                clearAction.run();
            }
        });

        // 🔹 Limpiar con doble clic en espacio vacío
        table.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                if (table.getSelectionModel().getSelectedItem() == null) {
                    clearAction.run();
                }
            }
        });
    }
}
