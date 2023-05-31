/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Member;
import model.Booking;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import static model.Club.getInstance;

/**
 * FXML Controller class
 *
 * @author david
 */
public class ModificarPerfilController implements Initializable {

     /**
     * Initializes the controller class.
     */
    private Club club; 

    private Member m; 
    
    private int a = 1, c = 1, d = 1, e = 1, f = 1, g = 1; 
    
    private boolean cambiado = false, cambiadoC = false, seHaCambiado = false, noVisible = false, tarjetaCvvCambiado = false; 
    
    private Image imageDePerfil;
    
    private String nombreNuevo = "", apellidoNuevo = "", telefonoNuevo = "", contraseñaNueva = "", numeroTarjetaNuevo = "";
    
    private int CVVnuevo = -1; 
    
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelPistaReservada;
    @FXML
    private MenuItem miMenu;
    @FXML
    private MenuItem hacerReserva;
    @FXML
    private MenuItem miReserva;
    @FXML
    private MenuItem cerrar;
    @FXML
    private ImageView fotoPerfil;
    @FXML
    private MenuButton opcionesBoton;
    @FXML
    private TextField apellidos;
    @FXML
    private TextField telefóno;
    @FXML
    private TextField NúmeroTarjeta;
    @FXML
    private TextField CVV;
    @FXML
    private PasswordField contraseña;
    @FXML
    private Button fotoBoton;
    @FXML
    private Button actualizarBoton;
    @FXML
    private CheckBox mostrarContra;
    @FXML
    private TextField textfield1;
    @FXML
    private ImageView ImagenPerfil;
    @FXML
    private Button nombreBoton;
    @FXML
    private Button apellidosBoton;
    @FXML
    private Button contraseñaBoton;
    @FXML
    private Button tarjetaBoton;
    @FXML
    private Button cvvBoton;
    @FXML
    private Button telBoton;
    @FXML
    private Label nombreLabel;
    @FXML
    private Label apellidosLabel;
    @FXML
    private Label telefonoLabel;
    @FXML
    private Label nicknameLabel;
    @FXML
    private Label numerotarjetaLabel;
    @FXML
    private PasswordField contraseñaAntigua;
    @FXML
    private Label cvvLabel;
    @FXML
    private TextField nombre1;
    @FXML
    private Label labelImagen;
    @FXML
    private CheckBox mostrarCVV;
    @FXML
    private TextField textfield2;
    @FXML
    private Label labelContraseña;
    @FXML
    private Button cancelarCambioBoton;
    @FXML
    private Button cancelarNombreBoton;
    @FXML
    private Button cancelarApellidoBoton;
    @FXML
    private Button cancelarTelefonoBoton;
    @FXML
    private Button cancelarTarjetaBoton;
    @FXML
    private Button cancelarCVVBoton;
    
    
    public void initUsuario(Member member) {
       m = member;  
    }
    
    public static int cambiarStrAInt(String str) {
        return Integer.parseInt(str);
    }
    
    public String cambiarIntStr(int num) {
        return String.valueOf(num);
    }
    
    public static boolean contieneSoloLetras(String str) {
        return str.matches("[a-zA-Z ]+");
    }
    
    public static boolean contieneSoloNumeros(String str) {
        return str.matches("[0-9]+");
    }
    
    public static String ocultar(String texto, int a, int b) {
        char[] caracteres = texto.toCharArray();
        for (int i = a; i <= b; i++) {
            caracteres[i] = '•';
        }  
        return new String(caracteres);
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
    
    public void initImageNick(Member member) 
    {
        m = member; 
        labelNombre.setText("¡Bienvenido "+ m.getNickName()+"!");

        LocalDate fechaActual = LocalDate.now();
        List<Booking> elarray = club.getForDayBookings(fechaActual); 
        
        int horaCompararInt = LocalTime.now().getHour();
        
        if (elarray.isEmpty() && (horaCompararInt >= 22 || horaCompararInt < 9))
        {
            
            labelPistaReservada.setText("Nuestras pistas de tenis permanecen cerradas. Horario de apertura de 9:00 a 22:00.");
        }
        else if (elarray.isEmpty())
        { 
            labelPistaReservada.setText("A lo largo del día no tienes ninguna reserva todavía.");
        } else {
        
        for(int i = 0; i < elarray.size(); i++) 
        {
            Booking b = elarray.get(i); 
            
            LocalTime hora1 = LocalTime.now(); 
            int comparacion = hora1.getHour(); 
            
            LocalTime hora = b.getFromTime(); 
            int comparacion1 = hora.getHour(); 
            
            DateTimeFormatter formatterDentroIf = DateTimeFormatter.ofPattern("HH:mm");
            String horaIF = hora.format(formatterDentroIf); 
            
            if(b.belongsToMember(m.getNickName()) && comparacion < comparacion1)
            {
                String a = ""; 
                if(b.getPaid() == false) { a = "está pagada."; } else { a = "no está pagado, recuerde pasar por la oficina a pagar la reserva."; }
                String mostrar = b.getCourt().getName() +", la hora es "+ horaIF + " y "+ a+ " ¡Disfrutad!"; 
                labelPistaReservada.setText("Tu próxima pista reservada es la "+ mostrar);
                break; 
            }
            else if(b.belongsToMember(m.getNickName()) && comparacion == comparacion1)
            {
                String a = ""; 
                if(b.getPaid() == false) { a = "está pagada."; } else { a = "no está pagado, recuerde pasar por la oficina a pagar la reserva."; }
                String mostrar = b.getCourt().getName() +" y "+ a + " ¡Disfrutad!"; 
                labelPistaReservada.setText("Tienes una reserva activa ahora mismo, tú pista es la "+ mostrar);
                break; 
            }
            else if (horaCompararInt >= 22 || horaCompararInt < 9)
            { 
                labelPistaReservada.setText("Nuestras pistas de tenis permanecen cerradas. Horario de apertura de 9:00 a 22:00.");
            }
            else 
            {
                labelPistaReservada.setText("A lo largo del día no tienes ninguna reserva todavía.");
            }
        }
        }
        Image imagenUsuario = m.getImage();
        if (imagenUsuario != null) 
        {
            fotoPerfil.setImage(imagenUsuario);
            ImagenPerfil.setImage(imagenUsuario);
        } 
        else 
        {
            Image imagenPredeterminada = new Image("../src/resources/default-avatar-profile-icon-social-media-user-free-vector.jpg");
            fotoPerfil.setImage(imagenPredeterminada);
            ImagenPerfil.setImage(imagenPredeterminada);
        }
        nombreLabel.setText(m.getName());     
        apellidosLabel.setText(m.getSurname());
        telefonoLabel.setText(m.getTelephone());
        nicknameLabel.setText(m.getNickName());
        numerotarjetaLabel.setText(ocultar(m.getCreditCard(), 4, 11));
        int cvv = m.getSvc(); 
        if(cvv != -1) cvvLabel.setText(ocultar(String.valueOf(m.getSvc()), 0, 2)); 
        else 
        {
            cvvLabel.setText("CVV no introducido ");
            cvvLabel.setStyle("-fx-text-fill: #F68A1F;");
        }  
 
        mostrarCVV.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue) {
        // Mostrar CVV y Tarjeta
            if(cambiado == false) 
            {
                numerotarjetaLabel.setText(m.getCreditCard());
            }
            else 
            {
                numerotarjetaLabel.setText(numeroTarjetaNuevo + "    Cambiado.");
            }
            tarjetaCvvCambiado = true; 
            int Cvv = m.getSvc(); 
            if(Cvv != -1) cvvLabel.setText(String.valueOf(m.getSvc())); 
            if(cambiadoC == false) 
            {
                if(Cvv != -1)
                {
                    cvvLabel.setText(cambiarIntStr(m.getSvc()));
                }   
                else 
                {
                    cvvLabel.setText("CVV no introducido ");
                    cvvLabel.setStyle("-fx-text-fill: #F68A1F;");
                }
                
            }
            else 
            {
                if(Cvv != -1)
                {
                    cvvLabel.setText(cambiarIntStr(m.getSvc()) + "    Cambiado.");
                }   
                else 
                {
                    cvvLabel.setText("CVV no introducido ");
                    cvvLabel.setStyle("-fx-text-fill: #F68A1F;");
                }                
            }
            tarjetaCvvCambiado = true; 
            
        } else {
        // Ocultar contraseña
            if(cambiado == false) 
            {
                numerotarjetaLabel.setText(ocultar(m.getCreditCard(), 4, 11));
            }
            else 
            {
                numerotarjetaLabel.setText(ocultar(numeroTarjetaNuevo, 4, 11) + "    Cambiado.");
            }
            tarjetaCvvCambiado = false;
            int Cvv = m.getSvc(); 
            if(Cvv != -1) cvvLabel.setText(ocultar(String.valueOf(m.getSvc()), 0, 2)); 
            if(cambiadoC == false) 
            {
                if(Cvv != -1)
                {
                    cvvLabel.setText(ocultar(cambiarIntStr(m.getSvc()),0 ,2));
                }
                else 
                {
                    cvvLabel.setText("CVV no introducido ");
                    cvvLabel.setStyle("-fx-text-fill: #F68A1F;");
                }
            }
            else 
            {
                if(Cvv != -1)
                {
                    cvvLabel.setText(ocultar(cambiarIntStr(CVVnuevo), 0, 2) + "    Cambiado.");
                }
                else 
                {
                    cvvLabel.setText("CVV no introducido ");
                    cvvLabel.setStyle("-fx-text-fill: #F68A1F;");
                }
                
            }
        }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            club = getInstance();  
            
        } catch (ClubDAOException | IOException e)  {}
        
        mostrarContra.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue) {
            // Mostrar contraseña 
            
            textfield2.setText(contraseñaAntigua.getText());
            contraseñaAntigua.setDisable(true); //<-
            contraseñaAntigua.setVisible(false); 
            textfield2.setDisable(false);  //<-
            textfield2.setVisible(true);
            noVisible = true;
            textfield1.setText(contraseña.getText());
            contraseña.setDisable(true); //<-
            contraseña.setVisible(false);
            textfield1.setDisable(false); //<-
            textfield1.setVisible(true);
                
            } 
            else 
            {
            // Ocultar contraseña
            contraseñaAntigua.setText(textfield2.getText());
            textfield2.setDisable(true); //<-
            textfield2.setVisible(false);
            contraseñaAntigua.setDisable(false); //<-
            contraseñaAntigua.setVisible(true);
            noVisible = false;
            contraseña.setText(textfield1.getText());
            textfield1.setDisable(true); //<-
            textfield1.setVisible(false);
            contraseña.setDisable(false); //<-
            contraseña.setVisible(true);
                   
        }
        });
        
        opcionesBoton.setId("boton_blanco_a_sombra");
        fotoBoton.setId("boton_verde_a_sombra");
        actualizarBoton.setId("boton_verde_a_sombra");
        nombreBoton.setId("boton_verde_a_sombra");
        apellidosBoton.setId("boton_verde_a_sombra");
        contraseñaBoton.setId("boton_verde_a_sombra");
        tarjetaBoton.setId("boton_verde_a_sombra");
        cvvBoton.setId("boton_verde_a_sombra");
        telBoton.setId("boton_verde_a_sombra");
        cancelarCambioBoton.setId("boton_naranja_verde_a_sombra");
        cancelarNombreBoton.setId("boton_naranja_verde_a_sombra");
        cancelarApellidoBoton.setId("boton_naranja_verde_a_sombra");
        cancelarTelefonoBoton.setId("boton_naranja_verde_a_sombra");
        cancelarTarjetaBoton.setId("boton_naranja_verde_a_sombra");
        cancelarCVVBoton.setId("boton_naranja_verde_a_sombra");
    }    

    @FXML
    private void miMenu(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
        Stage myStage = (Stage) scene.getWindow();
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/MenuFXML.fxml"));
        try {
            Parent root = miCargador.load();
            MenuFXMLController controlador = miCargador.getController(); 
            controlador.initUsuario(m); 
            controlador.initImageNick(m);
            controlador.meterComboBox(club.getCourts());
            Scene scene1 = new Scene(root);
            Stage stage = new Stage();
            stage.setMinWidth(650);
            stage.setMinHeight(700);
            stage.setMaxWidth(1400);
            stage.setMaxHeight(1400);
            stage.setScene(scene1);
            stage.setTitle("Menú");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            myStage.close();
        } catch (IOException ex) {}
    }

    @FXML
    private void menuReservar(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
        Stage myStage = (Stage) scene.getWindow();
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("HacerReservas.fxml"));
        try {
            Parent root = miCargador.load();
            HacerReservasController controlador = miCargador.getController(); 
            controlador.initUsuario(m); 
            controlador.initImageNick(m); 
            Scene scene1 = new Scene(root);
            Stage stage = new Stage();
            stage.setMinWidth(650);
            stage.setMinHeight(720);
            stage.setMaxWidth(900);
            stage.setMaxHeight(1000);
            stage.setTitle("Hacer Reservas");
            stage.setScene(scene1);
            stage.show();
            myStage.close();
        } catch (IOException ex) {}
    }

    @FXML
    private void menuMiReserva(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
        Stage myStage = (Stage) scene.getWindow();
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("MisReservas.fxml"));
        try {
            Parent root = miCargador.load();
            MisReservasController controlador = miCargador.getController(); 
            controlador.initUsuario(m); 
            controlador.initImageNick(m); 
            Scene scene1 = new Scene(root);
            Stage stage = new Stage();
            stage.setMinWidth(650);
            stage.setMinHeight(720);
            stage.setMaxWidth(900);
            stage.setMaxHeight(1000);
            stage.setTitle("Mis Reservas");
            stage.setScene(scene1);
            stage.show();
            myStage.close();
        } catch (IOException ex) {}  
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Estás seguro de que deseas cerrar sesión?");

        ButtonType cancelarButton = new ButtonType("Cancelar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cerrarSesionButton = new ButtonType("Cerrar sesión", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(cancelarButton, cerrarSesionButton); 
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == cerrarSesionButton) 
        {
            MenuItem menuItem = (MenuItem) event.getSource();
            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
            Stage myStage = (Stage) scene.getWindow();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
            try {
                Parent root = miCargador.load();
                Scene scene1 = new Scene(root);
                scene1.getStylesheets().add(getClass().getResource("calendario.css").toExternalForm());
                Stage stage = new Stage();
                stage.setTitle("GreenBall");
                stage.setMinWidth(650);
                stage.setMinHeight(700);
                stage.setMaxWidth(1400);
                stage.setMaxHeight(1400);
                stage.setScene(scene1);
                stage.show();
                myStage.close();
            } catch (IOException ex) {} 
        } 
        else 
        { 
            alert.close(); 
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void editarNombre(ActionEvent event) 
    {      
        if(a == 1) 
        {           
            nombreLabel.setVisible(false); 
            nombre1.setDisable(false);  
            nombre1.setVisible(true);
            nombre1.setPromptText("Nuevo nombre");
            a--;            
            nombreBoton.setText("Guardar");
            if(nombreLabel.getText().contains("Cambiado"))
            {
                nombre1.setText(nombreNuevo);
            }
        } 
        else if (a == 0)
        {    
            if(nombre1.getText().equals(m.getName()) || nombre1.getText().isEmpty()) 
            {
                nombreLabel.setText(m.getName());
                nombreLabel.setStyle("-fx-text-fill: black");
                nombre1.setDisable(true);  
                nombre1.setVisible(false);
                nombreLabel.setDisable(false); 
                nombreLabel.setVisible(true);
                a++; 
                nombreBoton.setText("Editar");
            } 
            else 
            {
                if(!contieneSoloLetras(nombre1.getText())) 
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en el nombre");
                    alert.setContentText("Un nombre no puede tener números.");
                    alert.showAndWait();
                    nombre1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");                     
                }
                else 
                {
                    nombreNuevo = nombre1.getText(); 
                    nombreLabel.setText(nombreNuevo + "    Cambiado.");
                    nombreLabel.setStyle("-fx-text-fill: #15622E"); 
                    nombre1.setDisable(true);  
                    nombre1.setVisible(false);
                    nombreLabel.setDisable(false); 
                    nombreLabel.setVisible(true);
                    a++; 
                    nombreBoton.setText("Editar");
                }
            }          
        }
    }
    @FXML
    private void nombreClick(MouseEvent event) 
    {
        nombre1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }
    @FXML
    private void cancelarNombre(ActionEvent event) 
    {
        nombre1.setText("");
        nombre1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        nombre1.setVisible(false);
        nombreBoton.setText("Editar");
        nombreNuevo = ""; 
        /*
        if(a == 0 & nombreLabel.getText().contains("Cambiado")) 
        {
            nombreLabel.setVisible(true);
            nombreLabel.setText(nombreNuevo + "    Cambiado.");
            a++;
            nombreLabel.setStyle("-fx-text-fill: #15622E");
        } Esto está aquí para que por si acaso queremos que el boton cancelar, no cancele lo cambiado*/
        if(a == 0) 
        {
            nombreLabel.setVisible(true);
            nombreLabel.setText(m.getName());
            a++;
            nombreLabel.setStyle("-fx-text-fill: black");
        } 
        /*
        else if(!nombre1.isVisible() && nombreLabel.getText().contains("Cambiado."))
        {
            nombreLabel.setVisible(true);
            nombreLabel.setText(nombreNuevo + "    Cambiado.");
            nombreLabel.setStyle("-fx-text-fill: #15622E");
        } */
        else if(!nombre1.isVisible())
        {
            nombreLabel.setVisible(true);
            nombreLabel.setText(m.getName());
            nombreLabel.setStyle("-fx-text-fill: black");
        }     
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void editarApellidos(ActionEvent event) 
    {
        if(c == 1) 
        {            
            apellidosLabel.setVisible(false); 
            apellidos.setDisable(false);  
            apellidos.setVisible(true);
            apellidos.setPromptText("Nuevos apellidos");
            c--;            
            apellidosBoton.setText("Guardar");
            if(apellidosLabel.getText().contains("Cambiado"))
            {
                apellidos.setText(apellidoNuevo);
            }
        } 
        else if (c == 0)
        {    
            if(apellidos.getText().equals(m.getName()) || apellidos.getText().isEmpty()) 
            {
                apellidosLabel.setText(m.getSurname());
                apellidosLabel.setStyle("-fx-text-fill: black");
                apellidos.setDisable(true);  
                apellidos.setVisible(false);
                apellidosLabel.setDisable(false); 
                apellidosLabel.setVisible(true);
                c++; 
                apellidosBoton.setText("Editar");
            } 
            else 
            {
                if(!contieneSoloLetras(apellidos.getText())) 
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en los apellidos");
                    alert.setContentText("Los apellidos o apellido no puede/pueden tener números.");
                    alert.showAndWait(); 
                    apellidos.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");                     
                }
                else 
                {
                    apellidoNuevo = apellidos.getText(); 
                    apellidosLabel.setText(apellidoNuevo + "    Cambiado.");
                    apellidosLabel.setStyle("-fx-text-fill: #15622E"); 
                    apellidos.setDisable(true);  
                    apellidos.setVisible(false);
                    apellidosLabel.setDisable(false); 
                    apellidosLabel.setVisible(true);
                    c++; 
                    apellidosBoton.setText("Editar");
                }
            }          
        }
    }
    @FXML
    private void apellidosClick(MouseEvent event) 
    {
        apellidos.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }
     @FXML
    private void cancelarApellido(ActionEvent event) 
    {
        apellidos.setText("");
        apellidos.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        apellidos.setVisible(false);
        apellidosBoton.setText("Editar");
        apellidoNuevo = "";
        if(c == 0) 
        {
            apellidosLabel.setVisible(true);
            apellidosLabel.setText(m.getSurname());
            c++;
            apellidosLabel.setStyle("-fx-text-fill: black");
        }    
        else if(!apellidos.isVisible())
        { 
            apellidosLabel.setVisible(true);
            apellidosLabel.setText(m.getSurname());
            apellidosLabel.setStyle("-fx-text-fill: black");
        }     
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void editarTel(ActionEvent event) 
    {
        if(d == 1) 
        {           
            telefonoLabel.setVisible(false); 
            telefóno.setDisable(false);  
            telefóno.setVisible(true);
            telefóno.setPromptText("Nuevo teléfono");
            d--;            
            telBoton.setText("Guardar");
            if(telefonoLabel.getText().contains("Cambiado"))
            {
                telefóno.setText(telefonoNuevo);
            }
        } 
        else if (d == 0)
        {    
            if(telefóno.getText().equals(m.getName()) || telefóno.getText().isEmpty()) 
            {
                telefonoLabel.setText(m.getTelephone());
                telefonoLabel.setStyle("-fx-text-fill: black");
                telefóno.setDisable(true);  
                telefóno.setVisible(false);
                telefonoLabel.setDisable(false); 
                telefonoLabel.setVisible(true);
                d++; 
                telBoton.setText("Editar");
            } 
            else 
            { 
                if(!contieneSoloNumeros(telefóno.getText())) 
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en el teléfono");
                    alert.setContentText("Porfavor introduzca un número válido.");
                    alert.showAndWait();
                    telefóno.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                }
                else 
                {
                    telefonoNuevo = telefóno.getText(); 
                    telefonoLabel.setText(telefonoNuevo + "    Cambiado.");
                    telefonoLabel.setStyle("-fx-text-fill: #15622E"); 
                    telefóno.setDisable(true);  
                    telefóno.setVisible(false);
                    telefonoLabel.setDisable(false); 
                    telefonoLabel.setVisible(true);
                    d++; 
                    telBoton.setText("Editar");
                }
            }          
        }
    }
    @FXML
    private void telefonoClick(MouseEvent event)
    {
        telefóno.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }
     @FXML
    private void cancelarTelefono(ActionEvent event) 
    {
        telefóno.setText("");
        telefóno.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        telefóno.setVisible(false);
        telBoton.setText("Editar");
        telefonoNuevo = ""; 
        if(d == 0)
        {
            telefonoLabel.setVisible(true);
            telefonoLabel.setText(m.getTelephone());
            d++;
            telefonoLabel.setStyle("-fx-text-fill: black");
        }    
        else if(!telefóno.isVisible())
        {
            telefonoLabel.setVisible(true);
            telefonoLabel.setText(m.getTelephone());
            telefonoLabel.setStyle("-fx-text-fill: black");
        }  
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void cambiarFoto(ActionEvent event) 
    {
        Image ImagenAhora = m.getImage(); 
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
                labelImagen.setText("Imagen cambiada.");
                labelImagen.setStyle("-fx-text-fill: #15622E");
            }
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------ 
    @FXML
    private void editarContra(ActionEvent event) 
    {
        if(e == 1) 
        {           
            mostrarContra.setDisable(false);
            contraseña.setDisable(false);  
            contraseñaAntigua.setDisable(false);
            textfield1.setDisable(false);
            textfield2.setDisable(false);
            labelContraseña.setVisible(false);
            e--;   
            /*
            if(seHaCambiado == true & noVisible == false) 
            {
                textfield1.setVisible(true);
                contraseña.setVisible(false);
            }
            else if (seHaCambiado == true & noVisible == true) 
            {
                contraseña.setVisible(false);
                textfield1.setVisible(true);
            }*/
            seHaCambiado = false; 
            contraseñaBoton.setText("Guardar");
        } 
        else if (e == 0)
        {    
            if(contraseña.getText().isEmpty() && textfield1.getText().isEmpty()) 
            {              
                mostrarContra.setDisable(true);
                contraseña.setDisable(true);  
                contraseñaAntigua.setDisable(true);  
                textfield1.setDisable(true);
                textfield2.setDisable(true);
                contraseña.setText("");
                contraseñaAntigua.setText("");
                textfield1.setText("");
                textfield2.setText("");
                e++; 
                seHaCambiado = false; 
                contraseñaBoton.setText("Editar");
            } 
            else 
            { 
                if(contraseña.getText().length() < 7 & !textfield1.isVisible()) 
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la contraseña");
                    alert.setContentText("La contraseña debe de tener más de 6 caracteres");
                    alert.showAndWait();
                    contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                    textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                }
                else if (textfield1.getText().length() < 7 & !contraseña.isVisible())
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la contraseña");
                    alert.setContentText("La contraseña debe de tener más de 6 caracteres");
                    alert.showAndWait();
                    contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                    textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                }
                else if(!contieneNumChar(contraseña.getText()) & !textfield1.isVisible()) 
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la contraseña");
                    alert.setContentText("La contraseña debe contener letras y números.");
                    alert.showAndWait();
                    contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                    textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                }
                else if(!contieneNumChar(textfield1.getText()) & !contraseña.isVisible()) 
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la contraseña");
                    alert.setContentText("La contraseña debe contener letras y números.");
                    alert.showAndWait();
                    contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                    textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                }
                else if (!m.getPassword().equals(contraseñaAntigua.getText()) & !textfield2.isVisible())
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la confirmación de la contraseña");
                    alert.setContentText("La contraseña antigua no coincide, asegúrese de introducirla\n"+" correctamente para poder efectuar efectivamente el cambio\n"+" de contraseña.");
                    alert.showAndWait();
                    contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                    textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);"); 
                }  
                else if (!m.getPassword().equals(textfield2.getText()) & !contraseñaAntigua.isVisible())
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la confirmación de la contraseña");
                    alert.setContentText("La contraseña antigua no coincide, asegúrese de introducirla\n"+" correctamente para poder efectuar efectivamente el cambio\n"+" de contraseña.");
                    alert.showAndWait();
                    contraseñaAntigua.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
                    textfield2.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);"); 
                } 
                else
                {   
                    if(contraseña.isVisible()) 
                    {
                        contraseñaNueva = contraseña.getText(); 
                    }
                    else 
                    { 
                        contraseñaNueva = textfield1.getText(); 
                    }
                    seHaCambiado = true; 
                    labelContraseña.setText(ocultar(contraseñaNueva, 0, contraseñaNueva.length() - 1)+ "    Cambiada.");
                    labelContraseña.setStyle("-fx-text-fill: #15622E"); 
                    contraseña.setText("");
                    textfield1.setText("");
                    contraseñaAntigua.setText("");
                    textfield2.setText("");
                    contraseña.setVisible(false);
                    textfield1.setVisible(false);
                    contraseñaAntigua.setDisable(true);
                    textfield2.setDisable(true);                  
                    labelContraseña.setVisible(true);
                    mostrarContra.setDisable(true);
                    e++; 
                    contraseñaBoton.setText("Editar");
                }
            }          
        }
    }
    @FXML
    private void contraseñaClick(MouseEvent event) 
    {
        contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }
    @FXML
    private void contraseñaClickA(MouseEvent event) 
    {
        contraseñaAntigua.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        textfield2.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }
    @FXML
    private void cancelarCambio(ActionEvent event) 
    {
        contraseñaNueva = "";
        
        if(seHaCambiado == true) 
        {
            contraseña.setText("");
            textfield1.setText("");
            contraseñaAntigua.setText("");
            textfield2.setText("");
            labelContraseña.setVisible(false);
            if(noVisible == true)
            {
                textfield1.setVisible(true);
                textfield1.setDisable(true);
                contraseña.setVisible(false);
                contraseña.setDisable(true);
            }
            else
            {
                contraseña.setVisible(true);
                contraseña.setDisable(true);
                textfield1.setVisible(false);
                textfield1.setDisable(true);
            }
        }
        else 
        {
            if(e == 1) 
            {
            contraseña.setText("");
            textfield1.setText("");
            contraseñaAntigua.setText("");
            textfield2.setText("");
            contraseña.setDisable(true);
            textfield1.setDisable(true);
            contraseñaAntigua.setPromptText("Contraseña antigua*");
            textfield2.setPromptText("Contraseña antigua*");
            contraseñaAntigua.setDisable(true);
            textfield2.setDisable(true);                  
            labelContraseña.setVisible(false);
            mostrarContra.setDisable(true);
            contraseñaBoton.setText("Editar");
            contraseñaAntigua.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield2.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            } 
            else if (e == 0)
            {
            contraseña.setText("");
            textfield1.setText("");
            contraseñaAntigua.setText("");
            textfield2.setText("");
            contraseña.setDisable(true);
            textfield1.setDisable(true);
            contraseñaAntigua.setPromptText("Contraseña antigua*");
            textfield2.setPromptText("Contraseña antigua*");
            contraseñaAntigua.setDisable(true);
            textfield2.setDisable(true);                  
            labelContraseña.setVisible(false);
            mostrarContra.setDisable(true);
            e++; 
            contraseñaBoton.setText("Editar");
            contraseñaAntigua.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield2.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            contraseña.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
            textfield1.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        }
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------ 
    @FXML
    private void editarTarjeta(ActionEvent event) 
    {
        if(f == 1) 
        {           
            numerotarjetaLabel.setVisible(false); 
            NúmeroTarjeta.setDisable(false);  
            NúmeroTarjeta.setVisible(true);
            NúmeroTarjeta.setPromptText("Nueva tarjeta");
            f--;            
            tarjetaBoton.setText("Guardar");
            if(numerotarjetaLabel.getText().contains("Cambiado"))
            {
                NúmeroTarjeta.setText(numeroTarjetaNuevo);
            }
        } 
        else if (f == 0)
        {    
            if(NúmeroTarjeta.getText().equals(m.getCreditCard()) || NúmeroTarjeta.getText().isEmpty()) 
            {
                cambiado = false; 
                if(tarjetaCvvCambiado == false) 
                {
                    numerotarjetaLabel.setText(ocultar(m.getCreditCard(), 4, 11));
                }
                else if(tarjetaCvvCambiado == true) 
                {
                    numerotarjetaLabel.setText(m.getCreditCard());
                }
                numerotarjetaLabel.setStyle("-fx-text-fill: black");
                NúmeroTarjeta.setDisable(true);  
                NúmeroTarjeta.setVisible(false);
                numerotarjetaLabel.setDisable(false); 
                numerotarjetaLabel.setVisible(true);
                f++; 
                tarjetaBoton.setText("Editar");
            } 
            else 
            {
                if(NúmeroTarjeta.getText().length() != 16 || !contieneSoloNumeros(NúmeroTarjeta.getText())) 
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en el número de tarjeta");
                    alert.setContentText("Número de tarjeta no válido. Una tarjeta válida solo puede contener números y debe de tener 16 dígitos");
                    alert.showAndWait();  
                    NúmeroTarjeta.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: red; -fx-prompt-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");                     
                }
                else 
                {
                    cambiado = true; 
                    numeroTarjetaNuevo = NúmeroTarjeta.getText(); 
                    if(tarjetaCvvCambiado == false) 
                    {
                        numerotarjetaLabel.setText(ocultar(numeroTarjetaNuevo, 4, 11) + "    Cambiado.");
                    }
                    else if(tarjetaCvvCambiado == true) 
                    {
                        numerotarjetaLabel.setText(numeroTarjetaNuevo+ "    Cambiado.");
                    }       
                    numerotarjetaLabel.setStyle("-fx-text-fill: #15622E"); 
                    NúmeroTarjeta.setDisable(true);  
                    NúmeroTarjeta.setVisible(false);
                    numerotarjetaLabel.setDisable(false); 
                    numerotarjetaLabel.setVisible(true);
                    f++; 
                    tarjetaBoton.setText("Editar");
                }
            }          
        }
    }
    @FXML
    private void tarjetaClick(MouseEvent event) 
    {
        NúmeroTarjeta.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }
    @FXML
    private void cancelarTarjeta(ActionEvent event) 
    {
        cambiado = false; 
        numeroTarjetaNuevo = ""; 
        NúmeroTarjeta.setText("");
        NúmeroTarjeta.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        NúmeroTarjeta.setVisible(false);
        tarjetaBoton.setText("Editar");
        /*
        if(f == 0 & numerotarjetaLabel.getText().contains("Cambiado")) 
        {
            f++; 
            if(tarjetaCvvCambiado == true)
            {
                numerotarjetaLabel.setVisible(true);
                numerotarjetaLabel.setText(numeroTarjetaNuevo + "    Cambiado.");
                
                numerotarjetaLabel.setStyle("-fx-text-fill: #15622E");
            }
            else 
            {
                numerotarjetaLabel.setVisible(true);
                numerotarjetaLabel.setText(ocultar(numeroTarjetaNuevo, 4, 11) + "    Cambiado.");
                numerotarjetaLabel.setStyle("-fx-text-fill: #15622E");
            }        
        } Esto está aquí para que por si acaso queremos que el boton cancelar, no cancele lo cambiado*/
        if(f == 0)
        {
            f++;
            if(tarjetaCvvCambiado == true)
            {
                numerotarjetaLabel.setVisible(true);
                numerotarjetaLabel.setText(m.getCreditCard());
                numerotarjetaLabel.setStyle("-fx-text-fill: black");
            }
            else 
            {
                numerotarjetaLabel.setVisible(true);
                numerotarjetaLabel.setText(ocultar(m.getCreditCard(), 4, 11));
                numerotarjetaLabel.setStyle("-fx-text-fill: black");
            }   
        } /*
        else if(!NúmeroTarjeta.isVisible() && numerotarjetaLabel.getText().contains("Cambiado."))
        {
            if(tarjetaCvvCambiado == true)
            {
                numerotarjetaLabel.setVisible(true);
                numerotarjetaLabel.setText(numeroTarjetaNuevo + "    Cambiado.");
                numerotarjetaLabel.setStyle("-fx-text-fill: #15622E");
            }
            else 
            {
                numerotarjetaLabel.setVisible(true);
                numerotarjetaLabel.setText(ocultar(numeroTarjetaNuevo, 4, 11) + "    Cambiado.");
                numerotarjetaLabel.setStyle("-fx-text-fill: #15622E");
            } 
        }*/
        else if(!NúmeroTarjeta.isVisible())
        {           
            if(tarjetaCvvCambiado == true)
            {
                numerotarjetaLabel.setVisible(true);
                numerotarjetaLabel.setText(m.getCreditCard());
                numerotarjetaLabel.setStyle("-fx-text-fill: black");
            }
            else 
            {
                numerotarjetaLabel.setVisible(true);
                numerotarjetaLabel.setText(ocultar(m.getCreditCard(), 4, 11));
                numerotarjetaLabel.setStyle("-fx-text-fill: black");
            }   
        } 
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------ 
    @FXML
    private void editarCVV(ActionEvent event) 
    {
        if(g == 1) 
        {           
            cvvLabel.setVisible(false); 
            CVV.setDisable(false);  
            CVV.setVisible(true);
            CVV.setPromptText("CVV");
            g--;            
            cvvBoton.setText("Guardar");
        } 
        else if (g == 0)
        {    
            if(CVV.getText().equals(cambiarIntStr(m.getSvc())) || CVV.getText().isEmpty()) 
            {
                cambiadoC = false; 
                if(m.getSvc() != -1) 
                {
                    if(tarjetaCvvCambiado == false) 
                    {
                        cvvLabel.setText(ocultar(cambiarIntStr(m.getSvc()), 0, 2));
                    }
                    else if(tarjetaCvvCambiado == true) 
                    {
                        cvvLabel.setText(cambiarIntStr(m.getSvc()));
                    }
                    cvvLabel.setStyle("-fx-text-fill: black");
                }
                else 
                {
                    cvvLabel.setText("CVV no introducido ");
                    cvvLabel.setStyle("-fx-text-fill: #F68A1F;");
                }
                CVV.setDisable(true);  
                CVV.setVisible(false);
                cvvLabel.setDisable(false); 
                cvvLabel.setVisible(true);
                g++; 
                cvvBoton.setText("Editar");
            } 
            else 
            {
                if(CVV.getText().length() != 3 || !contieneSoloNumeros(CVV.getText())) 
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
                    cambiadoC = true; 
                    String cvvString = CVV.getText(); 
                    CVVnuevo = cambiarStrAInt(cvvString); 
                    if(tarjetaCvvCambiado == false) 
                    {
                        cvvLabel.setText(ocultar(cvvString, 0, 2) + "    Cambiado.");
                    }
                    else if(tarjetaCvvCambiado == true) 
                    {
                        cvvLabel.setText(cvvString+ "    Cambiado.");
                    }
                    cvvLabel.setStyle("-fx-text-fill: #15622E"); 
                    CVV.setDisable(true);  
                    CVV.setVisible(false);
                    cvvLabel.setDisable(false); 
                    cvvLabel.setVisible(true);
                    g++; 
                    cvvBoton.setText("Editar");
                }
            }          
        }
    }
    @FXML
    private void cvvClick(MouseEvent event) 
    {
        CVV.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
    }   
    @FXML
    private void cancelarCVV(ActionEvent event) 
    {
        cambiadoC = false; 
        int CvV = m.getSvc(); 
        String CVVV = cambiarIntStr(CvV);
        CVVnuevo = -1; 
        CVV.setText("");
        CVV.setStyle("-fx-background-color: transparent; -fx-border-width: 0px 0px 2px 0px; -fx-border-color: #15622E; -fx-prompt-text-fill: black;  -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 3);");
        CVV.setVisible(false);
        cvvBoton.setText("Editar");
        if(m.getSvc() == -1 & g == 0) 
        {
            cvvLabel.setVisible(true);
            cvvLabel.setText("CVV no introducido ");
            cvvLabel.setStyle("-fx-text-fill: #F68A1F;");
            g++; 
        }
        else if(m.getSvc() == -1 & g == 1) 
        {
            cvvLabel.setVisible(true);
            cvvLabel.setText("CVV no introducido ");
            cvvLabel.setStyle("-fx-text-fill: #F68A1F;");
            g--; 
        }
        else
        {
            if(g == 0)
            {
                g++;
                if(tarjetaCvvCambiado == true)
                {
                    cvvLabel.setVisible(true);
                    cvvLabel.setText(CVVV);
                    cvvLabel.setStyle("-fx-text-fill: black");
                }
                else 
                {
                    cvvLabel.setVisible(true);
                    cvvLabel.setText(ocultar(CVVV, 0, 2));
                    cvvLabel.setStyle("-fx-text-fill: black");
                }   
            }    
            else if(!NúmeroTarjeta.isVisible())
            {           
                if(tarjetaCvvCambiado == true)
                {
                    cvvLabel.setVisible(true);
                    cvvLabel.setText(CVVV);
                    cvvLabel.setStyle("-fx-text-fill: black");
                }
                else 
                {
                    cvvLabel.setVisible(true);
                    cvvLabel.setText(ocultar(CVVV, 0, 2));
                    cvvLabel.setStyle("-fx-text-fill: black");
                }   
            } 
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------ 
    @FXML
    private void actualizar(ActionEvent event) throws IOException 
    {
        boolean modficiado = false; 
        if(!"".equals(nombreNuevo))
        {
            modficiado = true;
            m.setName(nombreNuevo);
        }
        if (!"".equals(apellidoNuevo))
        {
            modficiado = true;
            m.setSurname(apellidoNuevo);
        }
        if (!"".equals(telefonoNuevo))
        {
            modficiado = true;
            m.setTelephone(telefonoNuevo);
        }
        if (!"".equals(contraseñaNueva))
        {
            modficiado = true;
            m.setPassword(contraseñaNueva);
        }
        if (!"".equals(numeroTarjetaNuevo))
        {
            modficiado = true;
            m.setCreditCard(numeroTarjetaNuevo);
        }
        if (CVVnuevo != -1)
        {
            modficiado = true;
            m.setSvc(CVVnuevo);
        }
        if(imageDePerfil != null)
        {
            modficiado = true;
            m.setImage(imageDePerfil);
        }    
        
        if(modficiado == false)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("No ha modificado ningún campo.");
            alert.setContentText("Si quiere editar algún dato presione Editar y si quiere eliminar ese cambio presione Cancelar");
            ButtonType botonSi = new ButtonType("Aceptar");
            alert.showAndWait();
        }
        else 
        {
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/ModificarPerfil.fxml"));
            Parent root = miCargador.load();   
            ModificarPerfilController controlador = miCargador.getController();
            controlador.initUsuario(m); 
            controlador.initImageNick(m); 
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("textfield.css").toExternalForm()); 
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modificar Perfil");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            Stage myStage = (Stage) actualizarBoton.getScene().getWindow();
            myStage.close();
        }
    }
}
