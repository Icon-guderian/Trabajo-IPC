/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Club;
import model.ClubDAOException;
import model.Member;

public class AutenticarseController {
 
    @FXML
    private VBox VBoxImagen;
    @FXML
    private HBox HBox;
    @FXML
    private VBox VBox;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldEdad;
    @FXML
    private Label labelMensaje;
    @FXML
    private ImageView imagen;
    
    
   
    public void initialize(URL url, ResourceBundle rb) throws ClubDAOException, IOException 
    {
        String nickName = "user99";
        String password = "123456X";
        String name = "user";
        String surname = "99";
        String creditC = "0000111122223333";
        int svc=123;
        String tel="666666666";
        Club club = Club.getInstance();
        Member result = club.registerMember(name, surname, tel, nickName, password, creditC, svc, null);
        
        
        double maxW = VBoxImagen.getWidth(); 
        double maxH = VBoxImagen.getHeight();
        imagen.setPreserveRatio(true);
        imagen.setFitWidth(maxW);
        imagen.setFitHeight(maxH);
    }

    
       
    
    @FXML
    private void aceptar(ActionEvent event) {
      
        String nombre = textFieldNombre.getText();
        String edad = textFieldEdad.getText();
        
        
        
        double maxW = VBoxImagen.getWidth(); 
        double maxH = VBoxImagen.getHeight();
        ImageView imagen1 = imagen; 
        imagen1.resize(maxH, maxW);
        VBoxImagen.getChildren().remove(imagen);
        VBoxImagen.getChildren().add(imagen1); 
        
        //imagen.resizeRelocate(maxH, maxH, maxH, maxH);
        //imagen.setPreserveRatio(true);
        //imagen.setFitWidth(maxW);
        //imagen.setFitHeight(maxH);
        
        // Validar nombre y edad  + maxH +" "+ maxW
        if (nombre.isEmpty() || edad.isEmpty()) {
            labelMensaje.setText("Por favor, ingrese nombre y edad."+ maxH +" "+ maxW);
        } else {
            labelMensaje.setText("¡Bienvenido, " + nombre + "! Tu edad es: " + edad + " años.");
        }
    }
}
