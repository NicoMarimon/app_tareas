package com.example.myjavafxapp;
// NuevaVentana.java
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class calendario {
    public void mostrarVentana() {
        Stage nuevaVentana = new Stage();
        nuevaVentana.initModality(Modality.APPLICATION_MODAL);
        nuevaVentana.setTitle("Nueva Ventana");

        Label etiqueta = new Label("¡Esta es la nueva ventana!");

        StackPane layout = new StackPane();
        layout.getChildren().add(etiqueta);

        Scene escena = new Scene(layout, 200, 100);
        nuevaVentana.setScene(escena);

        nuevaVentana.showAndWait();
    }
}
