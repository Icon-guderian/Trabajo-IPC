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
import javafx.scene.input.MouseEvent;
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
    private CheckBox mostrarContra;
    @FXML
    private TextField textfield1;
    @FXML
    private TextField textfield2;
    @FXML
    private Button fotoBoton;
    

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
            contraseñaOtra.setVisible(false); 
            textfield2.setDisable(false);  //<-
            textfield2.setVisible(true);
            
            textfield1.setText(contraseña.getText());
            contraseña.setDisable(true); //<-
            contraseña.setVisible(false);
            textfield1.setDisable(false); //<-
            textfield1.setVisible(true);
                
        } else {
        // Ocultar contraseña
            contraseñaOtra.setText(textfield2.getText());
            textfield2.setDisable(true); //<-
            textfield2.setVisible(false);
            contraseñaOtra.setDisable(false); //<-
            contraseñaOtra.setVisible(true);
            
            contraseña.setText(textfield1.getText());
            textfield1.setDisable(true); //<-
            textfield1.setVisible(false);
            contraseña.setDisable(false); //<-
            contraseña.setVisible(true);
        }
        });
        
        fotoBoton.setId("boton_verde_a_sombra");
        registrarBoton.setId("boton_verde_a_sombra");
        iniciarSesionBoton.setId("boton_verde_a_sombra");
        menuBoton.setId("boton_verde_a_sombra");
    }    
    
    
    
    public static boolean contieneSoloNumeros(String str) {
        return str.matches("[0-9]+");
    }
    
    public static boolean contieneSoloLetras(String str) {
        return str.matches("[a-zA-Z ]+");
    }
    
    public static int cambiarStrAInt(String str) {
        return Integer.parseInt(str);
    }
    
    public boolean contieneNumChar(String st) {
        int a = 0, b = 0; 
        for(int i = 0;  i < st.length(); i++) {
            char c = st.charAt(i); 
            if(Character.isDigit(c)) { a++; }
            else if(Character.isLetter(c)) { b++; }
        }
        return a >= 1 && b >= 1;
    }
    
    public boolean validar(TextField... campos) 
    {
        for(TextField campo : campos) {
            if(campo.getText().trim().isEmpty()) return true; 
        }
        return false; 
    }
    
    public void validarCampos(TextField... campos) {
        for (TextField campo : campos) {
            if (campo.getText().trim().isEmpty()) 
            {
                campo.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #F68A1F; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
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
    
    public void validarCampos1(TextField... campos) {
        for (TextField campo : campos) {
            if (!campo.getText().trim().isEmpty()) {
            
                campo.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
        }
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
        String ContraseñaT = textfield1.getText(); 
        String ContraseñaOtraT = textfield2.getText(); 
        String cvv = CVV.getText(); 
        
        Club club = getInstance(); 
        
        if(validar(nombre, apellidos, telefóno, nickname, contraseña, contraseñaOtra, NúmeroTarjeta) && validar(nombre, apellidos, telefóno, nickname, textfield2, textfield1, NúmeroTarjeta)) 
        {
        
             validarCampos(nombre, apellidos, telefóno, nickname, contraseña, contraseñaOtra, textfield1, textfield2, NúmeroTarjeta); 
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setTitle("Error");
             alert.setHeaderText("Error en la introducción de datos");
             alert.setContentText("Por favor introduzca toda la información obligatoria. Son los campos de texto con un * al final.");
             alert.showAndWait();
        
        } else if(!validar(nombre, apellidos, telefóno, nickname, contraseña, contraseñaOtra, NúmeroTarjeta) || !validar(nombre, apellidos, telefóno, nickname, textfield1, textfield2, NúmeroTarjeta)) {
        
            validarCampos1(nombre, apellidos, telefóno, nickname, contraseña, contraseñaOtra, textfield1, textfield2, NúmeroTarjeta); 

            if(!contieneSoloLetras(Nombre)) 
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre");
            alert.setContentText("Un nombre no puede tener números.");
            alert.showAndWait();
            nombre.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");          
            }
            else if(!contieneSoloLetras(Apellidos))
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en los apellidos");
            alert.setContentText("Los apellidos o apellido no puede/pueden tener números.");
            alert.showAndWait();
            apellidos.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
            else if(!contieneSoloNumeros(Telefono))
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el teléfono");
            alert.setContentText("Porfavor introduzca un número válido.");
            alert.showAndWait();
            telefóno.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
            else if(club.existsLogin(NickName)) 
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre de usuario");
            alert.setContentText("Ya existe otro usuario con ese nick.");
            alert.showAndWait();
            nickname.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
            else if(Contraseña.length() < 7 & !textfield1.isVisible()) 
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la contraseña");
            alert.setContentText("La contraseña debe de tener más de 6 caracteres");
            alert.showAndWait();
            contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
            else if(ContraseñaOtra.length() < 7 & !textfield2.isVisible())
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la contraseña");
            alert.setContentText("La contraseña debe de tener más de 6 caracteres");
            alert.showAndWait();
            contraseñaOtra.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield2.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
            else if(ContraseñaT.length() < 7 & !contraseña.isVisible()) 
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la contraseña");
            alert.setContentText("La contraseña debe de tener más de 6 caracteres");
            alert.showAndWait();
            contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
            else if(ContraseñaOtraT.length() < 7 & !contraseñaOtra.isVisible())
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la contraseña");
            alert.setContentText("La contraseña debe de tener más de 6 caracteres");
            alert.showAndWait();
            contraseñaOtra.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield2.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
            else if(!contieneNumChar(Contraseña) & !textfield1.isVisible()) 
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la contraseña");
            alert.setContentText("La contraseña debe contener letras y números.");
            alert.showAndWait();
            contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
            else if(!contieneNumChar(ContraseñaOtra) & !textfield2.isVisible()) 
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la contraseña");
            alert.setContentText("La contraseña debe contener letras y números.");
            alert.showAndWait();
            contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
            else if(!contieneNumChar(ContraseñaT) & !contraseña.isVisible()) 
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la contraseña");
            alert.setContentText("La contraseña debe contener letras y números.");
            alert.showAndWait();
            contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
            else if(!contieneNumChar(ContraseñaOtraT) & !contraseñaOtra.isVisible()) 
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la contraseña");
            alert.setContentText("La contraseña debe contener letras y números.");
            alert.showAndWait();
            contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            }
            else if(!Contraseña.equals(ContraseñaOtra) || !ContraseñaT.equals(ContraseñaOtraT))
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en las contraseñas");
            alert.setContentText("Las contraseñas no coinciden.");
            alert.showAndWait();
            contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            contraseñaOtra.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield2.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        
            }
            else if(NumeroTarjeta.length() != 16 || !contieneSoloNumeros(NumeroTarjeta)) 
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el número de tarjeta");
            alert.setContentText("Número de tarjeta no válido. Una tarjeta válida solo puede contener números y debe de tener 16 dígitos");
            alert.showAndWait();  
            NúmeroTarjeta.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");

            }
            else if (!cvv.isEmpty() && (!contieneSoloNumeros(cvv) || cvv.length() != 3)) 
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el CVV");
            alert.setContentText("CVV no válido. Un CVV válida solo puede contener números y debe de tener 3 dígitos");
            alert.showAndWait(); 
            CVV.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");

            }  
            else 
            {
            if(!cvv.isEmpty()) 
            { 
                String s; 
                if("".equals(Contraseña)) { s = ContraseñaT; } else { s = Contraseña; }
                int cvvValido = cambiarStrAInt(cvv); 
                Member newMember = club.registerMember(Nombre, Apellidos, Telefono, NickName, s, NumeroTarjeta, cvvValido, imageDePerfil); 
                FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/MenuFXML.fxml"));
                Parent root = miCargador.load();
                MenuFXMLController controlador = miCargador.getController(); 
                controlador.initUsuario(newMember);
                controlador.initImageNick(newMember);
                controlador.meterComboBox(club.getCourts());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setMinWidth(650);
                stage.setMinHeight(700);
                stage.setMaxWidth(1400);
                stage.setMaxHeight(1400);
                stage.setScene(scene);
                stage.setTitle("Menú");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                Stage myStage = (Stage) registrarBoton.getScene().getWindow();
                myStage.close();
                }
                else 
                {   
                String s; 
                if("".equals(Contraseña)) { s = ContraseñaT; } else { s = Contraseña; }
                Member newMember = club.registerMember(Nombre, Apellidos, Telefono, NickName, s, NumeroTarjeta, -1 ,imageDePerfil); 
                FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/MenuFXML.fxml"));
                Parent root = miCargador.load();
                MenuFXMLController controlador = miCargador.getController(); 
                controlador.initUsuario(newMember);
                controlador.initImageNick(newMember);
                controlador.meterComboBox(club.getCourts());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setMinWidth(650);
                stage.setMinHeight(700);
                stage.setMaxWidth(1400);
                stage.setMaxHeight(1400);
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

    @FXML
    private void IrIniciarSesion(ActionEvent event) throws IOException 
    {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/IniciarSesion.fxml"));
        Parent root = miCargador.load();    
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("textfield.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(680);
        stage.setMinHeight(400);
        stage.setMaxWidth(800);
        stage.setMaxHeight(600);
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
        scene.getStylesheets().add(getClass().getResource("calendario.css").toExternalForm()); 
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(650);
        stage.setMinHeight(700);
        stage.setMaxWidth(1400);
        stage.setMaxHeight(1400);
        stage.setTitle("GreenBall");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        Stage myStage = (Stage) menuBoton.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void mostrarContraseñas(ActionEvent event) 
    {
    }

    @FXML
    private void apellidosClick(MouseEvent event) 
    {
       apellidos.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }

    @FXML
    private void telefonoClick(MouseEvent event) 
    { 
           telefóno.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }

    @FXML
    private void nickClick(MouseEvent event) 
    {   
           nickname.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }

    @FXML
    private void nombreClick(MouseEvent event) 
    {   
           nombre.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }

    @FXML
    private void tarjetaClick(MouseEvent event) 
    {   
           NúmeroTarjeta.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }

    @FXML
    private void cvvClick(MouseEvent event) 
    {    
           CVV.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }

    @FXML
    private void contraseñaClick(MouseEvent event) 
    { 
           contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
           textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }
               
    @FXML
    private void otraContraClick(MouseEvent event)
    {          
        contraseñaOtra.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        textfield2.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }
}
