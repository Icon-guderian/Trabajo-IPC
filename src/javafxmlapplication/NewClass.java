/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewClass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Crear una lista de elementos
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Elemento 1");
        items.add("Elemento 2");
        items.add("Elemento 3");

        // Crear el ComboBox y asignarle la lista de elementos
        ComboBox<String> comboBox = new ComboBox<>(items);

        // Crear el contenedor y añadir el ComboBox
        VBox root = new VBox();
        root.getChildren().add(comboBox);

        // Crear la escena y mostrarla
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}