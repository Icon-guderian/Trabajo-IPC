/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
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
public class RegistrarseController implements Initializable {

    Image imageDePerfil; 
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
    public ImageView ImagenPerfil;
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
    
    public static boolean validarString(String str) {
    if (str.length() > 6) {
        boolean contieneLetras = false;
        boolean contieneNumeros = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c)) {
                contieneLetras = true;
            }
            if (Character.isDigit(c)) {
                contieneNumeros = true;
            }
            if (contieneLetras && contieneNumeros) {
                return true;
            }
        }
    }
    return false;
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
                imageDePerfil = imagenSeleccionada;
            }
         
    }

    @FXML
    private void registrar(ActionEvent event) throws ClubDAOException, IOException 
    {
        String Nombre = nombre.getText(); 
        String Apellidos = apellidos.getText();
        String Telefono = telefóno.getText(); 
        String NickName = nickname.getText();
        String Contraseña = contraseña.getText(); 
        String ContraseñaOtra = contraseñaOtra.getText(); 
        String NumeroTarjeta = NúmeroTarjeta.getText(); 
        String cvv = CVV.getText(); 

        Club club = getInstance(); 
        
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
           nickname.setPromptText("Introduzca su usuario");
           nickname.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;"); 
        }
        else if(Contraseña.isEmpty())
        {
            contraseña.setPromptText("Introduzca la contraseña");
            contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;"); 
        }
        else if(ContraseñaOtra.isEmpty())
        {
            contraseñaOtra.setPromptText("Repita la contraseña");
            contraseñaOtra.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;"); 
        }
        else if(NumeroTarjeta.isEmpty()) 
        {
            NúmeroTarjeta.setPromptText("Introduzca su tarjeta");
            NúmeroTarjeta.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;"); 
       
        }
        else if(cvv.isEmpty()) 
        {
            CVV.setPromptText("Introduzca su tarjeta");
            CVV.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red;"); 
        }
        else if(!contieneSoloNumeros(Telefono))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Teléfono");
            alert.setContentText("Porfavor introduzca un número válido.");
            alert.showAndWait();
        }
        else if(club.existsLogin(NickName)) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nombre de usuario");
            alert.setContentText("Ya existe otro usuario con ese nick.");
            alert.showAndWait();
        }
        else if(!validarString(Contraseña)) 
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Contraseña");
            alert.setContentText("La contraseña debe de tener más de 6 caracteres, letras y números.");
            alert.showAndWait();
        }
        else if(Contraseña != ContraseñaOtra) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Contraseñas");
            alert.setContentText("Las contraseñas no coinciden.");
            alert.showAndWait();
        }
        else if(NumeroTarjeta.length() != 16 && !contieneSoloNumeros(NumeroTarjeta)) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Número de tarjeta");
            alert.setContentText("Número de tarjeta no válido.");
            alert.showAndWait();  
        }
        else if (cvv.length() != 3 && !contieneSoloNumeros(cvv)) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("CVV");
            alert.setContentText("CVV no válido.");
            alert.showAndWait(); 
        }  
        else 
        {
            int cvvValido = cambiarStrAInt(cvv); 
            club.registerMember(Nombre, Apellidos,NickName, Telefono, Contraseña, NumeroTarjeta, cvvValido, imageDePerfil); 
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
