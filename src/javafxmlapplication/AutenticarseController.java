/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AutenticarseController {
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldEdad;
    @FXML
    private Label labelMensaje;
    @FXML
    private VBox titulo;



    public void initialize(URL url, ResourceBundle rb) {
        //titulo.getStyleClass().add("titulo");
    }

    @FXML
    private void aceptar(ActionEvent event) {
        String nombre = textFieldNombre.getText();
        String edad = textFieldEdad.getText();
        // Validar nombre y edad
        if (nombre.isEmpty() || edad.isEmpty()) {
            labelMensaje.setText("Por favor, ingrese nombre y edad.");
        } else {
            labelMensaje.setText("¡Bienvenido, " + nombre + "! Tu edad es: " + edad + " años.");
        }
    }
}
