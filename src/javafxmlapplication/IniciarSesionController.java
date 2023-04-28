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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author UX431
 */
public class IniciarSesionController implements Initializable {

    @FXML
    private TextField CajaUsuario;
    @FXML
    private Button menuBoton;
    @FXML
    private Button registrarBoton;
    @FXML
    private Button aceptarBoton;
    @FXML
    private PasswordField cajaContraseña;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void IrAlMenu(ActionEvent event) throws IOException 
    {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/MenuPrincipal.fxml"));
        Parent root = miCargador.load();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("GreenBall");
        stage.initModality(Modality.APPLICATION_MODAL);
        //la ventana se muestra modal
        stage.show();
        Stage myStage = (Stage) menuBoton.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void Registrar(ActionEvent event) throws IOException 
    {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/Registrarse.fxml"));
        Parent root = miCargador.load();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Registrar");
        stage.initModality(Modality.APPLICATION_MODAL);
        //la ventana se muestra modal
        stage.show();
        Stage myStage = (Stage) registrarBoton.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void Aceptar(ActionEvent event) 
    {
        String usuario = CajaUsuario.getText();
        String contraseña = cajaContraseña.getText();
        
        if (usuario.isEmpty() && contraseña.isEmpty()) 
        {
            CajaUsuario.setPromptText("Introduzca su usuario por favor");
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;");
            cajaContraseña.setPromptText("Introduzca su contraseña por favor");
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;");
        
        } 
            else if(contraseña.isEmpty() && !usuario.isEmpty()) 
        {
            cajaContraseña.setPromptText("Introduzca su contraseña por favor");
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;");
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E;");
        } 
            else if(!contraseña.isEmpty() && usuario.isEmpty() )
        {
            CajaUsuario.setPromptText("Introduzca su usuario por favor");
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;");
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E;");
        }
            else if(usuario.isEmpty())
        {
           CajaUsuario.setPromptText("Introduzca su usuario por favor");
           CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;");
        }
            else  //aquí me pongo a mañana a buscar si existe el usuario
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("El usuario no está registrado");
            alert.showAndWait();
        }
    }
}
