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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import static model.Club.getInstance;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author UX431
 */
public class IniciarSesionController implements Initializable {

    private Member m; 
    
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
    private void Aceptar(ActionEvent event) throws ClubDAOException, IOException 
    {
        String usuario = CajaUsuario.getText();
        String contraseña = cajaContraseña.getText();
        
        Club club = getInstance(); 
        
        if (usuario.isEmpty() && contraseña.isEmpty()) 
        {
            CajaUsuario.setPromptText("Introduzca su usuario");
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            cajaContraseña.setPromptText("Introduzca su contraseña por favor");
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3); ");
        
        } 
            else if(contraseña.isEmpty() && !usuario.isEmpty()) 
        {
            cajaContraseña.setPromptText("Introduzca su contraseña");
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3); -fx-prompt-text-fill: black;");
        } 
            else if(!contraseña.isEmpty() && usuario.isEmpty() )
        {
            CajaUsuario.setPromptText("Introduzca su usuario por favor");
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        }
            else if(usuario.isEmpty())
        {
           CajaUsuario.setPromptText("Introduzca su usuario por favor");
           CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        }
            else 
        {
            m = club.getMemberByCredentials(usuario, contraseña);
            
            if(m == null) 
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("El usuario no está registrado");
                alert.showAndWait();
            } 
            else 
            {
                FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/MenuFXML.fxml"));
                Parent root = (Parent) miCargador.load();
                MenuFXMLController controlador = miCargador.getController(); 
                controlador.initUsuario(m);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Menú");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                Stage myStage = (Stage) registrarBoton.getScene().getWindow();
                myStage.close();
            }   
        } 
    }
}
