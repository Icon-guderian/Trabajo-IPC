/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;


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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import javafx.scene.layout.GridPane;
import static model.Club.getInstance;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.control.MenuButton;

/**
 * FXML Controller class
 *
 * @author david
 */

public class MisReservasController implements Initializable {

    private Club club; 
    
    private Booking b;
    
    private Member m; 
    
    private List<Booking> ArrayAModificar, ArrayAUtilizar;

    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuItem hacerReserva;
    @FXML
    private MenuItem modificar;
    @FXML
    private MenuItem cerrar;
    @FXML
    private ImageView fotoPerfil;
    @FXML
    private GridPane GridPane;
    @FXML
    private Button mostrarDisponBoton;
    @FXML
    private MenuButton opcionesBoton;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelPistaReservada;
    @FXML
    private MenuItem miMenu;
    /**
     * Initializes the controller class.
     */
    
    public void initUsuario(Member member) {
       m = member; 
       ArrayAModificar = club.getUserBookings(m.getNickName()); 
       ArrayAUtilizar = ordenarPorFechaYHora(ArrayAModificar); 
    }
    
    public static int cambiarStrAInt(String str) {
        return Integer.parseInt(str);
    }
    
    public boolean devolverHoraReserva(List<Booking> ar, LocalTime local) 
    {
        boolean devolver = false; 
        for(int i = 0; i < ar.size(); i++)
        {
            Booking reserva = ar.get(i);
            if(reserva.getFromTime() == local) { return true; }
            else { devolver = false; }
        }
        return devolver; 
    }
    
    public boolean memberTieneReserva(Booking ar, Member m)
    {
        String nick = m.getNickName(); 
        if(ar == null) { return false; }
        return ar.belongsToMember(nick); 
    }
    
    
    public void initImageNick(Member member) 
    {
        m = member; 
        labelNombre.setText("¡Bienvenido "+ m.getNickName()+"!");

        LocalDate fechaActual = LocalDate.now();
        List<Booking> elarray = club.getForDayBookings(fechaActual); 
        
        int horaCompararInt = LocalTime.now().getHour();
        
        if (elarray.isEmpty() && (horaCompararInt > 22 || horaCompararInt < 9))
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
            else if (horaCompararInt > 22 || horaCompararInt < 9)
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
        } 
        else 
        {
            Image imagenPredeterminada = new Image("../src/resources/default-avatar-profile-icon-social-media-user-free-vector.jpg");
            fotoPerfil.setImage(imagenPredeterminada);
        }
    }
    
    public static List<Booking> ordenarPorFechaYHora(List<Booking> bookings) {
        List<Booking> copy = new ArrayList<>(bookings);
        Collections.sort(copy, (Booking booking1, Booking booking2) -> booking1.getBookingDate().compareTo(booking2.getBookingDate()));
        return copy;
        }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            club = getInstance();  
            
        } catch (ClubDAOException | IOException e)  {}
        
        opcionesBoton.setId("boton_blanco_a_sombra");

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
            stage.setScene(scene1);
            stage.show();
            myStage.close();
        } catch (IOException ex) {}
    }

    @FXML
    private void modificarPerfil(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
        Stage myStage = (Stage) scene.getWindow();
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("ModificarPerfil.fxml"));
        try {
            Parent root = miCargador.load();
            ModificarPerfilController controlador = miCargador.getController(); 
            controlador.initUsuario(m); 
            controlador.initImageNick(m);
            Scene scene1 = new Scene(root);
            Stage stage = new Stage();
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
                Stage stage = new Stage();
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

    @FXML
    private void mostrarDisponibilidad(ActionEvent event) throws ClubDAOException, IOException {
        club = getInstance(); 
        
        ArrayAUtilizar = ordenarPorFechaYHora(ArrayAModificar);

        for(int i = 0; i < 10 ; i++){
           
            Booking b = ArrayAUtilizar.get(i);
            
            if(b.equals(null)){
                
                Label label = new Label();
                label.setText("No tienes reservas proximas");
                GridPane.add(label,1,i);
                
            }else if((!b.equals(null)) ){ 
                
                Label label = new Label();
                LocalTime horaInicio = b.getFromTime();
                int duracion = club.getBookingDuration();
                LocalTime horaFin = horaInicio.plusMinutes(duracion);
                
                String a = ""; 
                if(b.getPaid() == false) { a = "está pagada."; } else { a = "no está pagado, recuerde pasar por la oficina a pagar la reserva."; }
                
                String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
                String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));

                label.setText(b.getMadeForDay() + "   " + horaInicioTexto + " - " + horaFinTexto + ".  Reservado por: "+ m.getNickName() + "    " + b.getCourt().getName()+ "   " + a +"                                                                               ");  
                label.setStyle("-fx-background-color: #ffff80");

                GridPane.add(label, 1, i); 
                
            }
        }
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
            stage.setScene(scene1);
            stage.setTitle("Menú");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            myStage.close();
        } catch (IOException ex) {}
    }

    
}
