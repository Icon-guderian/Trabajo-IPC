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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import model.Member;

/**
 * FXML Controller class
 *
 * @author UX431
 */


public class MenuFXMLController implements Initializable {

    Member m; 
    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuItem menuSalir;
    @FXML
    private DatePicker calendarioBoton;
    @FXML
    private ListView<?> personasListView;
    @FXML
    private Button verReservas;
    @FXML
    private Button reservar;
    @FXML
    private ComboBox<?> seleccionPistaBoton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initUsuario(Member member) {
        m = member; 
    }

    @FXML
    private void salir(ActionEvent event) {
    }

    @FXML
    private void calendario(ActionEvent event) {
    }

    @FXML
    private void verReservas(ActionEvent event) {
    }

    @FXML
    private void reservarPistas(ActionEvent event) {
    }

    @FXML
    private void seleccionPista(ActionEvent event) {
    }
    
}
