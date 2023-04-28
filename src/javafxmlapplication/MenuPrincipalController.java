/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author david
 */
public class MenuPrincipalController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuItem menuSalir;
    @FXML
    private Label label;
    @FXML
    private ImageView registro;
    @FXML
    private Button autenticarse;
    @FXML
    private Button autenticarse1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void salir(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de confirmación");
        alert.setHeaderText("Vas a salir del programa");
        alert.setContentText("¿Seguro que quieres salir?");
        
        ButtonType buttonTypeCancel = new ButtonType("Salir", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeCancel){
            System.out.println("OK");
            
        } else {
            System.out.println("CANCEL");
        }
    }



  
    private void accederRegistro(ActionEvent event) throws IOException 
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
        Stage myStage = (Stage) registro.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void accederAutenticarse(ActionEvent event) throws IOException 
    {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/IniciarSesion.fxml"));
        Parent root = miCargador.load();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Autenticarse");
        stage.initModality(Modality.APPLICATION_MODAL);
        //la ventana se muestra modal
        stage.show();
        Stage myStage = (Stage) autenticarse.getScene().getWindow();
        myStage.close();
        
    }

    @FXML
    private void accederRegistro(MouseEvent event) {
    }
    
}
