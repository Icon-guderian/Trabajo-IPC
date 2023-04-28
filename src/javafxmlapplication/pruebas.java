/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class pruebas extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crea el botón para seleccionar la foto
        Button btnSeleccionarFoto = new Button("Seleccionar foto");
        
        // Crea el contenedor para mostrar la foto seleccionada
        ImageView imgFoto = new ImageView();
        
        // Manejador de evento para el botón de selección de foto
        btnSeleccionarFoto.setOnAction(event -> {
            // Crea el diálogo para seleccionar un archivo
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar foto");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.gif"));
            
            // Muestra el diálogo y obtiene el archivo seleccionado
            File archivoSeleccionado = fileChooser.showOpenDialog(primaryStage);
            
            // Si se ha seleccionado un archivo, lo muestra en el contenedor de la foto
            if (archivoSeleccionado != null) {
                Image imagenSeleccionada = new Image(archivoSeleccionado.toURI().toString());
                imgFoto.setImage(imagenSeleccionada);
            }
        });
        
        // Crea el contenedor principal y añade los elementos
        HBox root = new HBox();
        root.getChildren().addAll(btnSeleccionarFoto, imgFoto);
        
        // Crea la escena y la muestra en la ventana
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("Selección de foto");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}