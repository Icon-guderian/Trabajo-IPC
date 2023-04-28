/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author UX431
 */
public class RegistrarseController implements Initializable {

    @FXML
    private TextField apellidos;
    @FXML
    private TextField telefóno;
    @FXML
    private TextField nickname;
    @FXML
    private TextField nombre;
    @FXML
    private TextField NúmeroTarjeta;
    @FXML
    private TextField CVV;
    @FXML
    private PasswordField contraseña;
    @FXML
    private PasswordField contraseñaOtra;
    @FXML
    private ImageView ImagenPerfil;
    @FXML
    private Button seleccionFoto;
    @FXML
    private Button registrarBoton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    

    @FXML
    private void seleccionarFoto(ActionEvent event) {
    }

    @FXML
    private void registrar(ActionEvent event) {
    }
    
}
