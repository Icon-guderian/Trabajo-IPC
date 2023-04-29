/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    
    public static boolean validateString(String str) {
        if (str.length() <= 5) {
            return true;
        }
        boolean containsLetter = false;
        boolean containsNumber = false;
        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                containsLetter = true;
            } else if (Character.isDigit(c)) {
                containsNumber = true;
            }
            if (containsLetter && containsNumber) {
                return true;
            }   
        }
        return false;
    }   
    
    public void validarCampos(TextField... campos) {
        for (TextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                campo.setPromptText("Introduzca un valor");
                campo.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            } else {
                campo.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
        }
    }
    
    public boolean validarTextField(TextField... campos) {
        for (TextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                return true; 
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
        
        if(validarTextField(nombre, apellidos, telefóno, nickname, contraseña, contraseñaOtra, NúmeroTarjeta, CVV)) 
        {
            validarCampos(nombre, apellidos, telefóno, nickname, contraseña, contraseñaOtra, NúmeroTarjeta, CVV); 
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
        else if(validateString(Contraseña)) 
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
            Member newMember = club.registerMember(Nombre, Apellidos,NickName, Telefono, Contraseña, NumeroTarjeta, cvvValido, imageDePerfil); 
            
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/MenuFXML.fxml"));
            Parent root = miCargador.load();
            MenuFXMLController controlador = miCargador.getController(); 
            controlador.initUsuario(newMember);
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

    @FXML
    private void IrIniciarSesion(ActionEvent event) throws IOException 
    {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/IniciarSesion.fxml"));
        Parent root = miCargador.load();    
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Iniciar Sesión");
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
