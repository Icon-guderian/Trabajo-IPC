/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Member;

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
    private Button registrarBoton;
    @FXML
    private Button menuBoton;
    @FXML
    private Button iniciarSesionBoton;
    @FXML
    private Button seleccionBoton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }    
    
    public static boolean contieneSoloNumeros(String str) {
        return str.matches("[0-9]+");
    }
    
    public static int cambiarStrAInt(String str) {
        return Integer.parseInt(str);
    }
    
    @FXML
    private void seleccionarFoto(ActionEvent event) 
    {
        FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar foto");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.gif"));
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            File archivoSeleccionado = fileChooser.showOpenDialog(stage);
            if (archivoSeleccionado != null) {
                Image imagenSeleccionada = new Image(archivoSeleccionado.toURI().toString());
                ImagenPerfil.setImage(imagenSeleccionada);
            }
    }

    @FXML
    private void registrar(ActionEvent event) 
    {
        String Nombre = nombre.getText(); 
        String Apellidos = apellidos.getText();
        String Telefono = telefóno.getText(); 
        String NickName = nickname.getText();
        String Contraseña = contraseña.getText(); 
        String ContraseñaOtra = contraseñaOtra.getText(); 
        String NumeroTarjeta = NúmeroTarjeta.getText(); 
        String cvv = CVV.getText(); 
        ImageView imagenPerfil = ImagenPerfil; 
        
        if(Nombre.isEmpty())
        {
            nombre.setPromptText("Introduzca su usuario");
            nombre.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;");
        } 
        else if (Apellidos.isEmpty())
        {
           apellidos.setPromptText("Introduzca sus apellidos");
           apellidos.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;"); 
        }
        else if(Telefono.isEmpty() || !contieneSoloNumeros(Telefono)) 
        {
           telefóno.setPromptText("Introduzca su teléfono");
           telefóno.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;"); 
        }
        else if(NickName.isEmpty()) 
        {
        
        }
    }   

    @FXML
    private void IrIniciarSesion(ActionEvent event) throws IOException 
    {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/IniciarSesion.fxml"));
        Parent root = miCargador.load();    
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Autenticarse");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        Stage myStage = (Stage) iniciarSesionBoton.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void IrMenu(ActionEvent event) throws IOException 
    {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/MenuPrincipal.fxml"));
        Parent root = miCargador.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("GreenBall");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        Stage myStage = (Stage) menuBoton.getScene().getWindow();
        myStage.close();
    }
    
}
