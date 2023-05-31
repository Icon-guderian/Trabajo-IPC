/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import com.gluonhq.charm.glisten.control.Icon;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

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
    private PasswordField cajaContraseña;
    @FXML
    private Button aceptarBoton;
    @FXML
    private Label errorUsuarioVacío;
    @FXML
    private Label errorContraseñaVaic;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        aceptarBoton.setId("boton_verde_a_sombra");
        menuBoton.setId("boton_verde_a_sombra");
        cajaContraseña.setId("cajaContraseña");
        registrarBoton.setId("boton_verde_a_sombra");
        CajaUsuario.setId("cajaUsuario");
    }    

   
    @FXML
    private void IrAlMenu(ActionEvent event) throws IOException 
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
    private void Registrar(ActionEvent event) throws IOException 
    {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/Registrarse.fxml"));
        Parent root = miCargador.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(460);
        stage.setMinHeight(700);
        stage.setMaxWidth(800);
        stage.setMaxHeight(1000);
        stage.setTitle("Registrar");
        stage.initModality(Modality.APPLICATION_MODAL);
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
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #F68A1F; -fx-prompt-text-fill: #F68A1F;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #F68A1F; -fx-prompt-text-fill: #F68A1F; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3); ");
            errorUsuarioVacío.setVisible(true);
            errorContraseñaVaic.setVisible(true);
            errorUsuarioVacío.setStyle("-fx-text-fill: #F68A1F"); 
            errorContraseñaVaic.setStyle("-fx-text-fill: #F68A1F"); 
            errorUsuarioVacío.setText("Introduzca un usuario");
            errorContraseñaVaic.setText("Introduzca una contraseña");
        } 
            else if(contraseña.isEmpty() && !usuario.isEmpty()) 
        {
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #F68A1F; -fx-prompt-text-fill: #F68A1F;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3); -fx-prompt-text-fill: black;");
            errorContraseñaVaic.setVisible(true);
            errorContraseñaVaic.setStyle("-fx-text-fill: #F68A1F"); 
            errorContraseñaVaic.setText("Introduzca una contraseña");
            errorUsuarioVacío.setVisible(false);
        } 
            else if(!contraseña.isEmpty() && usuario.isEmpty() )
        {
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #F68A1F; -fx-prompt-text-fill: #F68A1F;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            errorUsuarioVacío.setVisible(true);
            errorUsuarioVacío.setStyle("-fx-text-fill: #F68A1F");
            errorUsuarioVacío.setText("Introduzca un usuario");
            errorContraseñaVaic.setVisible(false);        
        }
            else if(usuario.isEmpty())
        {
           CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #F68A1F; -fx-prompt-text-fill: #F68A1F;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
           errorUsuarioVacío.setVisible(true);
           errorUsuarioVacío.setStyle("-fx-text-fill: #F68A1F");
           errorUsuarioVacío.setText("Introduzca un usuario");
        }
            else 
        {
            
            List<Member> elarray = club.getMembers(); 
            boolean existe = false; 
            for(int i = 0; i < elarray.size(); i++) 
            {
                Member a = elarray.get(i); 
                if(a.chekCredentials(usuario, contraseña) == true)
                {
                    existe = true; 
                    break; 
                }
            }
                
            if(existe == false) 
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("El usuario no está registrado");
                alert.showAndWait();
                CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3); ");
                errorUsuarioVacío.setVisible(true);
                errorContraseñaVaic.setVisible(true);
                errorUsuarioVacío.setStyle("-fx-text-fill: red"); 
                errorContraseñaVaic.setStyle("-fx-text-fill: red");
                errorUsuarioVacío.setText("Este usuario puede no estar registrado");
                errorContraseñaVaic.setText("Esta contraseña puede estar incorrecta");
            } 
            else 
            {
                m = club.getMemberByCredentials(usuario, contraseña); 
                FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/MenuFXML.fxml"));
                Parent root = (Parent) miCargador.load();
                MenuFXMLController controlador = miCargador.getController(); 
                controlador.initUsuario(m);
                controlador.initImageNick(m);
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

    @FXML
    private void contraseñaCambiar(MouseEvent event) 
    {
        if(errorContraseñaVaic.getText().contains("Introduzca una contraseña")) 
        {
            errorContraseñaVaic.setVisible(false);
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3); -fx-prompt-text-fill: black;");
        }
        else 
        {
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3); -fx-prompt-text-fill: black;");
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3); -fx-prompt-text-fill: black;");
       
        }
    }

    @FXML
    private void usuarioCambiar(MouseEvent event) 
    {
       if (errorUsuarioVacío.getText().contains("Introduzca un usuario"))
        {
            errorUsuarioVacío.setVisible(false);
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3); -fx-prompt-text-fill: black;");
        }
        else 
        {
            cajaContraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3); -fx-prompt-text-fill: black;");
            CajaUsuario.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3); -fx-prompt-text-fill: black;");
        }
    }
}
