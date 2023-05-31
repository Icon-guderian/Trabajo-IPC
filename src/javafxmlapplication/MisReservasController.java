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
import javafx.scene.control.DialogPane;
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
    
    private List<Booking> ArrayAModificar, ArrayAUtilizar, ArrayAComparar;
    
    private Booking selectedBooking;

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
    @FXML
    private Button anularReservaBoton;
    /**
     * Initializes the controller class.
     */
    
    public void initArray(List<Booking> a) {
        ArrayAComparar = a; 
    }
    
    public void initUsuario(Member member) {
       m = member; 
       if(ArrayAComparar == null)
       {
            ArrayAModificar = club.getUserBookings(m.getNickName()); 
            ArrayAUtilizar = ordenarPorFechaYHora(ArrayAModificar); 
       }
       else
       {
           ArrayAUtilizar = ArrayAComparar;  
       }
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
        } 
        else 
        {
            Image imagenPredeterminada = new Image("../src/resources/default-avatar-profile-icon-social-media-user-free-vector.jpg");
            fotoPerfil.setImage(imagenPredeterminada);
        }
    }
    
    public static List<Booking> ordenarPorFechaYHora(List<Booking> bookings) {
        List<Booking> copy = new ArrayList<>(bookings);
        Collections.sort(copy, (Booking booking1, Booking booking2) -> booking1.getMadeForDay().compareTo(booking2.getMadeForDay()));
        return copy;
        }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            club = getInstance();  
            
        } catch (ClubDAOException | IOException e)  {}
        
        opcionesBoton.setId("boton_blanco_a_sombra");
        mostrarDisponBoton.setId("boton_verde_a_sombra");
        anularReservaBoton.setId("boton_verde_a_sombra");


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
            stage.setMinWidth(550);
            stage.setMinHeight(810);
            stage.setMaxWidth(800);
            stage.setMaxHeight(1000);
            stage.setTitle("Modificar Perfil");
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

    @FXML
    private void mostrarDisponibilidad(ActionEvent event) throws ClubDAOException, IOException {
        club = getInstance(); 
        
        ArrayAUtilizar = ordenarPorFechaYHora(ArrayAModificar);
                
                if (ArrayAUtilizar.isEmpty()) {
                Label label = new Label();
                label.setText("               No tienes reservas próximas");
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-alignment: center; -fx-text-alignment: center;");
                GridPane.add(label, 1, 0);
                return; 
            }
           
        int i = 0;
        
        for (Booking b : ArrayAUtilizar){
                       
            if(b.getMadeForDay().isAfter(LocalDate.now()) || b.getMadeForDay().isEqual(LocalDate.now())){
            
                if((!b.equals(null)) ){ 

                    Label label = new Label();
                    LocalDate diaReserva = b.getMadeForDay();
                    LocalTime horaInicio = b.getFromTime();
                    int duracion = club.getBookingDuration();
                    LocalTime horaFin = horaInicio.plusMinutes(duracion);

                    String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
                    String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));
                    String diaReservaTexto = diaReserva.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
                    
                    String a = ""; 
                    if(b.getPaid() == false) { a = "Está pagada."; } 
                    else { a = "No está pagado, recuerde pasar por la oficina a pagar la reserva."; }
                    
                    label.setText("         " + diaReservaTexto + " | " + horaInicioTexto + " - " + horaFinTexto + " | " + "Reservado por: " + m.getNickName() + " | " + b.getCourt().getName() + " | " + a + "         ");  
                    label.setId("selected_reserva");
                    
                    label.setOnMouseClicked(e -> {
            
                        if (selectedBooking != null) {
                            // Restaurar el estilo de la reserva previamente seleccionada
                            label.setId("selected_reserva");              
                        }
                        selectedBooking = b;
                            label.setId("selected_reserva");              
                        anularReservaBoton.setDisable(false); // Habilitar el botón de anular reserva

                    });
                    
                    GridPane.add(label, 1, i); 

                    i++;
                }
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
    private void anularReserva(ActionEvent event) throws ClubDAOException, IOException{
        if (selectedBooking != null) {
            
            LocalDate now = LocalDate.now();
            LocalDate reservaDate = selectedBooking.getMadeForDay();

            LocalDate diaReserva = selectedBooking.getMadeForDay();
            LocalTime horaInicio = selectedBooking.getFromTime();
            int duracion = club.getBookingDuration();
            LocalTime horaFin = horaInicio.plusMinutes(duracion);

            String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
            String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));
            String diaReservaTexto = diaReserva.format(DateTimeFormatter.ofPattern("dd/MM/yy"));

            String a = "";
            if(selectedBooking.getPaid() == false) { a = "El importe de su reserva ha sido resuelto."; } 
            else { a = ""; }

                // Verificar que la reserva es posterior a la fecha actual por más de 24 horas
                if (reservaDate.isAfter(now.plusDays(1))) {
                    // Eliminar la reserva del club
                    boolean removed = club.removeBooking(selectedBooking);
                    mostrarDisponibilidad(event);

                    // Mostrar un mensaje de error si la reserva no cumple con la condición de tiempo
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Reserva anulada");
                    alert.setHeaderText("");
                    alert.setContentText("Se ha anulado la siguiente reserva: " + diaReservaTexto + " " + horaInicioTexto + " - " + horaFinTexto + " " + selectedBooking.getCourt().getName() + ".      " + a + "");

                    DialogPane dialogPane = alert.getDialogPane();

                    // Establecer un ancho y alto personalizados
                    dialogPane.setPrefWidth(450);
                    dialogPane.setPrefHeight(100);

                    Optional<ButtonType> result = alert.showAndWait();
                    FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/MisReservas.fxml"));
                    Parent root = miCargador.load();    
                    Scene scene = new Scene(root);
                    MisReservasController controlador = miCargador.getController(); 
                    controlador.initUsuario(m); 
                    controlador.initImageNick(m);
                    controlador.initArray(ArrayAUtilizar); 
                    scene.getStylesheets().add(getClass().getResource("textfield.css").toExternalForm());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setMinWidth(650);
                    stage.setMinHeight(720);
                    stage.setMaxWidth(900);
                    stage.setMaxHeight(1000);
                    stage.setTitle("Mis reservas");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                    Stage myStage = (Stage) anularReservaBoton.getScene().getWindow();
                    myStage.close();
                } else {
                    // Mostrar un mensaje de error si la reserva no cumple con la condición de tiempo
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error anulando la reserva");
                    alert.setHeaderText("");
                    alert.setContentText("No se puede anular una reserva con menos de 24 horas de anticipación.");

                    Optional<ButtonType> result = alert.showAndWait();
                } 
        }else{
            // Mostrar un mensaje de error si no se pudo eliminar la reserva
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error anulando la reserva");
            alert.setHeaderText("");
            alert.setContentText("Error al anular la reserva. Inténtelo de nuevo.");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }
}
