/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import model.Member;

/**
 * FXML Controller class
 *
 * @author david
 */
public class HacerReservasController implements Initializable {

    private Member m; 
    /**
     * Initializes the controller class.
     */
    
    public void initUsuario(Member member) {
       m = member;  
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
