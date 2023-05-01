/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import model.Club;
import static model.Club.getInstance;
import model.ClubDAOException;
import model.Court;
import model.Member;

/**
 * FXML Controller class
 *
 * @author UX431
 */


public class MenuFXMLController implements Initializable {
    Club club;
    
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
    private ComboBox<String> seleccionPistaBoton;
    @FXML
    private Button mostrarDisponBoton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        try {
            club = getInstance(); 
        } catch (ClubDAOException | IOException e)  {}
        
        List<Court> elarray = new ArrayList<>(); 
        elarray = club.getCourts(); 
        ObservableList<String> items = FXCollections.observableArrayList();
        for(int i = 0; i < elarray.size(); i++) 
        {
            items.add(elarray.get(i).getName()); 
        }
        seleccionPistaBoton.setItems(items);  
    }    
    
    public void initUsuario(Member member) {
        m = member; 
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
    private void seleccionPista(ActionEvent event) throws ClubDAOException, IOException {
        
    }

    @FXML
    private void mostrarDisponibilidad(ActionEvent event) {
    }
    
}
