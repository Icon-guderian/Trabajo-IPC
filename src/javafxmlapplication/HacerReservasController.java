/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static javafxmlapplication.MisReservasController.ordenarPorFechaYHora;
import model.Booking;
import model.Club;
import static model.Club.getInstance;
import model.ClubDAOException;
import model.Court;
import model.Member;

/**
 * FXML Controller class
 *
 * @author david
 */
public class HacerReservasController extends ListCell<String> implements Initializable {

    private Member m; 
    
    private Club club;
    
    private List<Booking> ArrayAModificar, ArrayAUtilizar, ArrayAComparar;

    private Booking b;
    
    private Booking selectedBooking; 

    @FXML
    private BorderPane borderPane;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelPistaReservada;
    @FXML
    private MenuItem miMenu;
    @FXML
    private MenuItem miReserva;
    @FXML
    private MenuItem modificar;
    @FXML
    private MenuItem cerrar;
    @FXML
    private ImageView fotoPerfil;
    @FXML
    private MenuButton opcionesBoton;
    @FXML
    private DatePicker calendarioBoton;
    @FXML
    private ComboBox<String> seleccionarPistaBoton;
    @FXML
    private GridPane GridPane;
    @FXML
    private Button mostrarDisponBoton;
    @FXML
    private Button reservarBoton;
    /**
     * Initializes the controller class.
     */
    public void initArray(List<Booking> a)
    {
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
        labelNombre.setText("¡Bienvenido "+ m.getNickName()+"! :D");

        LocalDate fechaActual = LocalDate.now();
        List<Booking> elarray = club.getForDayBookings(fechaActual); 
        
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
            else 
            {
                labelPistaReservada.setText("A lo largo del día no tienes ninguna reserva todavía.");
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
     public void meterComboBox(List<Court> elarray) 
    {
        elarray = club.getCourts(); 
        ObservableList<String> items = FXCollections.observableArrayList();
        for(int i = 0; i < elarray.size(); i++) 
        {
            items.add(elarray.get(i).getName()); 
        }
        seleccionarPistaBoton.setItems(items);         
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        seleccionarPistaBoton.setItems(items);
        
        opcionesBoton.setId("boton_blanco_a_sombra");
        mostrarDisponBoton.setId("boton_verde_a_sombra");
        seleccionarPistaBoton.setId("seleccionarPistaBoton"); 
        reservarBoton.setId("boton_verde_a_sombra");


    }    
    
    @FXML
    private void mostrarDisponibilidad(ActionEvent event) throws ClubDAOException, IOException 
    {
        club = getInstance(); // Obtiene una instancia del objeto Club
        
        List<Booking> horarioDePista = new ArrayList<>(); 
        
        LocalDate fecha = calendarioBoton.getValue(); // Obtiene la fecha seleccionada en el DatePicker
        LocalDate fechaActual = LocalDate.now();
        String pista = seleccionarPistaBoton.getValue(); // Obtiene la pista seleccionada en el ComboBox

                    
        if(fecha == null) 
        {        // Si no se ha seleccionado una fecha, muestra un mensaje de error
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en selección de la fecha");
            alert.setContentText("Por favor introduzca una fecha.");
            alert.showAndWait();
        
        }
        else if(pista == null) 
        {        // Si no se ha seleccionado una pista, muestra un mensaje de error
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la selección de pista");
            alert.setContentText("Debe seleccionar 1 pista.");
            alert.showAndWait();
        } 
        else if(fecha.isBefore(fechaActual)) 
        {        // Si la fecha seleccionada es anterior a la fecha actual, muestra un mensaje de confirmación
            horarioDePista = club.getCourtBookings(pista, fecha); 
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Este día ya ha pasado");
            alert.setContentText("¿Está seguro que quiere mostrar la disponibilidad de ese día?");
            ButtonType botonSi = new ButtonType("Mostrar");
            ButtonType botonNo = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(botonSi, botonNo);
            Optional<ButtonType> resultado = alert.showAndWait();
            
            
            if (resultado.isPresent() && resultado.get() == botonSi) 
            {// Si el usuario confirma, muestra la disponibilidad de reservas para ese día pasado
                LocalTime horaInicio = LocalTime.of(9, 0);
                int duracion = club.getBookingDuration();
                int ReservasPistas = club.getBookingSlots(); 
                
                //borra el gridpane si hay cosas
                ObservableList<Node> listaDeCosas = GridPane.getChildren();
                Iterator<Node> iterator = listaDeCosas.iterator();
                while (iterator.hasNext()) {
                    Node child = iterator.next();
                    if (child instanceof Label) {
                        iterator.remove(); 
                    }
                }       
                
                for (int i = 0, j = 0; i < ReservasPistas ; i++) 
                {
                    Booking reserva; 
                    if(j < horarioDePista.size()) {
                        reserva = horarioDePista.get(j);
                    } 
                    else { reserva = null; }
                
                    if(devolverHoraReserva(horarioDePista, horaInicio) & memberTieneReserva(reserva, m)) 
                    {
                        Label label = new Label(); 
                        LocalTime horaFin = horaInicio.plusMinutes(duracion);        
                        String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
                        String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));
                        label.setText(horaInicioTexto + " - " + horaFinTexto + ".  Reservado por "+ m.getNickName() +"                                                                               ");  
                        label.setStyle("-fx-background-color: #ffff80");
                        GridPane.add(label, 1, i); 
                        GridPane.getChildren().get(i + 1).setId("celda"); 
                        horaInicio = horaFin;
                    } 
                     else if(devolverHoraReserva(horarioDePista, horaInicio)) 
                    {
                        Label label = new Label(); 
                        LocalTime horaFin = horaInicio.plusMinutes(duracion);        
                        String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
                        String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));
                        label.setText(horaInicioTexto + " - " + horaFinTexto + ".  Reservado                                                                                         ");  
                        label.setStyle("-fx-background-color: #ffc8c8");
                        GridPane.add(label, 1, i); 
                        GridPane.getChildren().get(i + 1).setId("celda"); 
                        horaInicio = horaFin;  
                    }
                    else 
                    {                          
                        Label label = new Label();
                        LocalTime horaFin = horaInicio.plusMinutes(duracion);        
                        String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
                        String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));
                        label.setText(horaInicioTexto + " - " + horaFinTexto + ".  No reservado                                                                                    ");
                        label.setStyle("-fx-background-color: #80ff80");                       
                        
                        /*
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
                            label.setId("unselected_reserva");              
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
                        label.setOnMouseClicked(e -> {
                                    
                        if (selectedBooking != null) {
                            // Restaurar el estilo de la reserva previamente seleccionada
                            label.setId("unselected_reserva");              
                        }
                        selectedBooking = b;
                            label.setId("selected_reserva");              
                        reservarBoton.setDisable(false); // Habilitar el botón de anular reserva
                        });
                        GridPane.add(label, 1, i);
                        GridPane.getChildren().get(i + 1).setId("celda"); 
                        horaInicio = horaFin;
                        
                        Tienes que añadir algo parecido a esta mierda, para accionar el boton anular
                        */
                        
                    }
                }           
            } 
        } 
        else 
        {
            LocalTime horaInicio = LocalTime.of(9, 0);
            int duracion = club.getBookingDuration();
            int ReservasPistas = club.getBookingSlots(); 
            horarioDePista = club.getCourtBookings(pista, fecha); 
            
            ObservableList<Node> listaDeCosas = GridPane.getChildren();
            Iterator<Node> iterator = listaDeCosas.iterator();

            while (iterator.hasNext()) {
                Node child = iterator.next();
                if (child instanceof Label) {
                    iterator.remove(); 
                }
            }       
            
            for (int i = 0, j = 0; i < ReservasPistas ; i++) 
            {
                Booking reserva; 
                if(j < horarioDePista.size()) {
                    reserva = horarioDePista.get(j);
                } 
                else { reserva = null; }
                
                if(devolverHoraReserva(horarioDePista, horaInicio) & memberTieneReserva(reserva, m)) 
                    {
                        Label label = new Label(); 
                        LocalTime horaFin = horaInicio.plusMinutes(duracion);        
                        String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
                        String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));
                        label.setText(horaInicioTexto + " - " + horaFinTexto + ".  Reservado por "+ m.getNickName() +"                                                                               ");  
                        label.setStyle("-fx-background-color: #ffff80");
                        GridPane.add(label, 1, i); 
                        GridPane.getChildren().get(i + 1).setId("celda"); 
                        horaInicio = horaFin;
                        j++; 
                    } 
                     else if(devolverHoraReserva(horarioDePista, horaInicio)) 
                    {
                        Label label = new Label(); 
                        LocalTime horaFin = horaInicio.plusMinutes(duracion);        
                        String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
                        String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));
                        label.setText(horaInicioTexto + " - " + horaFinTexto + ".  Reservado                                                                                         ");  
                        label.setStyle("-fx-background-color: #ffc8c8");
                        GridPane.add(label, 1, i); 
                        GridPane.getChildren().get(i + 1).setId("celda"); 
                        horaInicio = horaFin;  
                        j++; 
                    }
                    else 
                    {
                        Label label = new Label();
                        LocalTime horaFin = horaInicio.plusMinutes(duracion);        
                        String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
                        String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));
                        label.setText(horaInicioTexto + " - " + horaFinTexto + ".  No reservado                                                                                    ");
                        label.setStyle("-fx-background-color: #80ff80; -fx-background-insets: 0");
                        GridPane.add(label, 1, i);
                        GridPane.getChildren().get(i + 1).setId("celda"); 
                        horaInicio = horaFin;
                        
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
    
/*
        */
    @FXML
    private void hacerReserva(ActionEvent event) throws ClubDAOException, IOException {
      if (selectedBooking == null) {
        LocalDate now = LocalDate.now();
        LocalDate reservaDate = selectedBooking.getMadeForDay();
        LocalDateTime ahora = LocalDateTime.now();
        String pista = seleccionarPistaBoton.getValue(); // Obtiene la pista seleccionada en el ComboBox
        LocalDate fecha = calendarioBoton.getValue(); // Obtiene la fecha seleccionada en el DatePicker
        LocalTime horaInicio = selectedBooking.getFromTime();
        int duracion = club.getBookingDuration();
        LocalTime horaFin = horaInicio.plusMinutes(duracion);
        Court pistas = selectedBooking.getCourt();
        String nick = m.getNickName(); 

        
// Aquí debes establecer la fecha de reserva deseada
        // Aquí debes obtener la hora de inicio y duración de la reserva deseada
        
        String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
        String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));
        String diaReservaTexto = reservaDate.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        
        // Verificar que el usuario no tiene más de dos reservas
        if (fecha.isAfter(now)) {
           club.registerBooking(ahora, reservaDate, horaFin, true, pistas, m);
            mostrarDisponibilidad(event);
            hacerReserva(event);
            
            // Mostrar un mensaje de éxito de la reserva
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reserva realizada");
            alert.setHeaderText("");
            alert.setContentText("Se ha hecho la siguiente reserva: " + diaReservaTexto + " " + horaInicioTexto + " - " + horaFinTexto + " " + selectedBooking.getCourt().getName() + ".      ");

            DialogPane dialogPane = alert.getDialogPane();

            // Establecer un ancho y alto personalizados
            dialogPane.setPrefWidth(450);
            dialogPane.setPrefHeight(100);

            Optional<ButtonType> result = alert.showAndWait();
            
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/HacerReservas.fxml"));
            Parent root = miCargador.load();    
            Scene scene = new Scene(root);
            MisReservasController controlador = miCargador.getController(); 
            controlador.initUsuario(m); 
            controlador.initImageNick(m);
            controlador.initArray(ArrayAUtilizar); 
            scene.getStylesheets().add(getClass().getResource("textfield.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Hacer reservas");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            Stage myStage = (Stage) reservarBoton.getScene().getWindow();
            myStage.close();
            
            // Código adicional para actualizar la interfaz o realizar otras acciones necesarias después de la reserva
        } else {
            // Mostrar un mensaje de error si la reserva no cumple con la condición de tiempo
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error al hacer la reserva");
            alert.setHeaderText("");
            alert.setContentText("No se pueden hacer reservas en el pasado");

            Optional<ButtonType> result = alert.showAndWait();
        }
    } else {
        // Mostrar un mensaje de error si ya se ha seleccionado una reserva
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error haciendo la reserva");
        alert.setHeaderText("");
        alert.setContentText("Error al hacer la reserva. Inténtalo de nuevo.");

        Optional<ButtonType> result = alert.showAndWait();
   }
  }
}