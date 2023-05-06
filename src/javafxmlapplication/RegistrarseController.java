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
import javafx.scene.control.CheckBox;
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

    
    private Image imageDePerfil; 
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
    @FXML
    private CheckBox mostrarContra;
    @FXML
    private TextField textfield1;
    @FXML
    private TextField textfield2;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        textfield2.setVisible(false);
        textfield1.setVisible(false);
        textfield2.setDisable(true);
        textfield1.setDisable(true);
        
        mostrarContra.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue) {
        // Mostrar contraseña
            textfield2.setText(contraseñaOtra.getText());
            contraseñaOtra.setDisable(true); //<-
            contraseñaOtra.setManaged(false);
            contraseñaOtra.setVisible(false); 
            textfield2.setDisable(false);  //<-
            textfield2.setManaged(true);
            textfield2.setVisible(true);
            
            textfield1.setText(contraseña.getText());
            contraseña.setDisable(true); //<-
            contraseña.setManaged(false);
            contraseña.setVisible(false);
            textfield1.setDisable(false); //<-
            textfield1.setManaged(true);
            textfield1.setVisible(true);

        } else {
        // Ocultar contraseña
            contraseñaOtra.setText(textfield2.getText());
            textfield2.setDisable(true); //<-
            textfield2.setManaged(false);
            textfield2.setVisible(false);
            contraseñaOtra.setDisable(false); //<-
            contraseñaOtra.setManaged(true);
            contraseñaOtra.setVisible(true);
            
            contraseña.setText(textfield1.getText());
            textfield1.setDisable(true); //<-
            textfield1.setManaged(false);
            textfield1.setVisible(false);
            contraseña.setDisable(false); //<-
            contraseña.setManaged(true);
            contraseña.setVisible(true);
        }
        });
    }    
    
    
    
    public static boolean contieneSoloNumeros(String str) {
        return str.matches("[0-9]+");
    }
    
    public static int cambiarStrAInt(String str) {
        return Integer.parseInt(str);
    }
    
    public boolean contieneNumChar(String st)
    {
        int a = 0, b = 0; 
        for(int i = 0;  i < st.length(); i++) {
            char c = st.charAt(i); 
            if(Character.isDigit(c)) { a++; }
            else if(Character.isLetter(c)) { b++; }
        }
        return a >= 1 && b >= 1;
    }
    
    public void validarCampos(TextField... campos) {
        for (TextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                //campo.setPromptText("Introduzca un valor");
                campo.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: red; -fx-prompt-text-fill: red; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            } else {
                campo.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
        }
        
        if(!contraseña.getText().isEmpty() || !textfield1.getText().isEmpty()) 
        {
            textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        }
        if(!contraseñaOtra.getText().isEmpty() || !textfield2.getText().isEmpty()) 
        {
            textfield2.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            contraseñaOtra.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
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
    protected void seleccionarFoto(ActionEvent event) 
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
        String ContraseñaVisible = textfield1.getText(); 
        String ContraseñaOtraVisible = textfield2.getText();
        
        Club club = getInstance(); 
        
        if(validarTextField(nombre, apellidos, telefóno, nickname, contraseña, contraseñaOtra, textfield1, textfield2, NúmeroTarjeta)) 
        {
            validarCampos(nombre, apellidos, telefóno, nickname, contraseña, contraseñaOtra, textfield1, textfield2, NúmeroTarjeta); 
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
        else if(Contraseña.length() < 6 || ContraseñaVisible.length() < 6) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Contraseña");
            alert.setContentText("La contraseña debe de tener más de 6 caracteres");
            alert.showAndWait();
        }
        else if(!contieneNumChar(Contraseña) /*|| !contieneNumChar(ContraseñaVisible)*/) 
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Contraseña");
            alert.setContentText("La contraseña debe contener letras y números.");
            alert.showAndWait();
        }
        else if(!Contraseña.equals(ContraseñaOtra) /*|| !ContraseñaVisible.equals(ContraseñaOtraVisible)*/)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Contraseñas");
            alert.setContentText("Las contraseñas no coinciden.");
            alert.showAndWait();
        }
        else if(NumeroTarjeta.length() != 16 /*|| !contieneSoloNumeros(NumeroTarjeta)*/) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Número de tarjeta");
            alert.setContentText("Número de tarjeta no válido.");
            alert.showAndWait();  
        }
        else if (cvv.length() != 3 || !contieneSoloNumeros(cvv)) 
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
            Member newMember = club.registerMember(Nombre, Apellidos, Telefono, NickName, Contraseña, NumeroTarjeta, cvvValido, imageDePerfil); 
            
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

    

    @FXML
    private void mostrarCOntraseña(ActionEvent event) 
    {
        
    }    
}
