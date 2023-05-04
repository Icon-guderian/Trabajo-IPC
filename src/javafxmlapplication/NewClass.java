/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

/**
 *
 * @author UX431
 */

    import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NewClass extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll("Elemento 1", "Elemento 2", "Elemento 3");

        // Creamos un objeto ListCell personalizado para la celda en la posición 1
        ListCell<String> celdaPersonalizada = new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                if (getIndex() == 1) {
                    getStyleClass().add("celdaPersonalizada");
                } else {
                    getStyleClass().remove("celdaPersonalizada");
                }
            }
        };
        
        // Establecemos el objeto ListCell personalizado en la posición 1 del ListView
        listView.setCellFactory(lv -> {
            if (lv == null) return null;
            return celdaPersonalizada;
        });

        // Creamos una escena y le añadimos el ListView
        StackPane root = new StackPane();
        root.getChildren().add(listView);
        Scene scene = new Scene(root, 300, 250);

        // Añadimos la hoja de estilos css al archivo fxml
        scene.getStylesheets().add(getClass().getResource("celdaView.css").toExternalForm());

        primaryStage.setTitle("Ejemplo ListView");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

