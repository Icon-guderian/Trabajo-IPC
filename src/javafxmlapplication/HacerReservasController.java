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
import java.util.Collections;
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
    
    private boolean a;
    
    private int[] contador = {0, 0, 0}; 

    private LocalDate fechaReserva;
    
    private String pistaReserva; 
    
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
    private GridPane GridPane;
    @FXML
    private Button mostrarDisponBoton;
    @FXML
    private Button reservarBoton;
    @FXML
    private ComboBox<String> seleccionPistaBoton;
    @FXML
    private Label saberDia;
    @FXML
    private Label saberPista;
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
    
    public static List<Booking> ordenarPorFechaYHora(List<Booking> bookings) {
        List<Booking> copy = new ArrayList<>(bookings);
        Collections.sort(copy, (Booking booking1, Booking booking2) -> booking1.getMadeForDay().compareTo(booking2.getMadeForDay()));
        return copy;
    }
    
    public static LocalDateTime darFecha(int value) {
        LocalDateTime now = LocalDateTime.now(); 
        LocalDateTime dateTime = now.withHour(9).withMinute(0).plusHours(value - 2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDateTime = dateTime.format(formatter);
        dateTime = LocalDateTime.parse(formattedDateTime, formatter);
        return dateTime;
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
    
    public void meterComboBox(List<Court> elarray) 
    {
        elarray = club.getCourts(); 
        ObservableList<String> items = FXCollections.observableArrayList();
        for(int i = 0; i < elarray.size(); i++) 
        {
            items.add(elarray.get(i).getName()); 
        }
        seleccionPistaBoton.setItems(items);         
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
        seleccionPistaBoton.setItems(items);
        
        opcionesBoton.setId("boton_blanco_a_sombra");
        mostrarDisponBoton.setId("boton_verde_a_sombra");
        reservarBoton.setId("boton_verde_a_sombra");
    }    
    
    @FXML
    private void mostrarDisponibilidad(ActionEvent event) throws ClubDAOException, IOException 
    {
        club = getInstance(); 
        
        List<Booking> horarioDePista = new ArrayList<>(); 
        
        LocalDate fecha = calendarioBoton.getValue(); 
        LocalDate fecha1 = fecha; 
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaString = fecha1.format(formato);
        fechaReserva = LocalDate.parse(fechaString, formato);
        String pista = seleccionPistaBoton.getValue();
        pistaReserva = pista;
        reservarBoton.setDisable(true);            
        contador[0] = 0;  
        contador[1] = 0; 
        contador[2] = 0; 
        
        if(fecha == null) 
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en selección de la fecha");
            alert.setContentText("Por favor introduzca una fecha.");
            alert.showAndWait();
        
        }
        else if(pista == null) 
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la selección de pista");
            alert.setContentText("Debe seleccionar 1 pista.");
            alert.showAndWait();
        } 
        else if(fecha.isBefore(fechaActual)) 
        {
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
            {
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
                        GridPane.add(label, 1, i);
                        GridPane.getChildren().get(i + 1).setId("celda"); 
                        horaInicio = horaFin;
                        
                        label.setOnMouseClicked(e -> {
                            if (selectedBooking != null) {
                            // Restaurar el estilo de la reserva previamente seleccionada
                                label.setId("unselected_reserva");              
                            }
                                selectedBooking = b;
                                label.setId("selected_reserva"); 
                                reservarBoton.setId("boton_verde_a_sombra"); 
                                reservarBoton.setDisable(false); // Habilitar el botón de anular reserva
                        });
                    }
                }           
            } 
            reservarBoton.setId("boton_verde_a_sombra");
            saberDia.setText("Está mostrando la fecha "+ fechaString); 
            saberPista.setText("Está mostrando la pista "+ pista);
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
                        
                        label.setOnMouseClicked(e -> {
                            
                                if(contador[0] == 2) 
                                {
                                    if(GridPane.getChildren().indexOf(label) == contador[1]) 
                                    {
                                        label.setStyle("-fx-background-color: #80ff80;");
                                        contador[1] = contador[2]; 
                                        contador[2] = 0;
                                        contador[0]--; 
                                    }
                                    else if(GridPane.getChildren().indexOf(label) == contador[2]) 
                                    {
                                        label.setStyle("-fx-background-color: #80ff80;");
                                        contador[2] = 0; 
                                        contador[0]--;
                                    }
                                    else {
                                        Node node = (Label) GridPane.getChildren().get(contador[1]); 
                                        contador[1] = contador[2]; 
                                        node.setStyle("-fx-background-color: #80ff80;");
                                        label.setStyle("-fx-background-color: #C9E0F7");
                                        contador[2] = GridPane.getChildren().indexOf(label); 
                                    }
                                }
                                else 
                                {
                                    if(GridPane.getChildren().indexOf(label) != contador[1]) 
                                    {
                                        label.setStyle("-fx-background-color: #C9E0F7"); 
                                        reservarBoton.setDisable(false); 
                                        if(contador[0] == 0) { contador[1] = GridPane.getChildren().indexOf(label); }
                                        if(contador[0] == 1) { contador[2] = GridPane.getChildren().indexOf(label); }
                                        contador[0]++;
                                    }
                                    else 
                                    {
                                        label.setStyle("-fx-background-color: #80ff80;");
                                        contador[1] = 0; 
                                        contador[0]--; 
                                    }
                                }

                        });     
                }       
            }  
            saberDia.setText("Está mostrando la fecha "+ fechaString); 
            saberPista.setText("Está mostrando la pista "+ pista);
        }
        reservarBoton.setId("boton_verde_a_sombra");
        GridPane.getChildren().get(14).setId("celda");
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
            stage.setTitle("Mis Reservas");
            stage.setMinWidth(650);
            stage.setMinHeight(720);
            stage.setMaxWidth(900);
            stage.setMaxHeight(1000);
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
    private void hacerReserva(ActionEvent event) throws ClubDAOException 
    {
        if(contador[2] < 1 && contador[1] < 1) 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe pulsar una hora.");
            alert.setContentText("Si no marca una pista para jugar no puede realizar la reserva de la o las pistas.");
            alert.showAndWait();
        }
        else 
        {
            LocalDateTime FechaReserva1;
            LocalDateTime FechaReserva2;
            
            if(contador[1] < 1) 
            {
                FechaReserva2 = darFecha(contador[2]);
                LocalDateTime horaActual = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
                String fechaString = horaActual.format(formato);
                LocalTime horaReserva = LocalTime.parse(fechaString, formato); 
                if(FechaReserva2.getHour() < Integer.parseInt(fechaString.substring(0, 2))) 
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la hora");
                    alert.setContentText("No puede reservar una pista cuya hora está en el pasado");
                    alert.showAndWait();
                } 
                else 
                {
                    boolean a = club.hasCreditCard(m.getNickName());  
                    Booking b1 = club.registerBooking(LocalDateTime.now(), fechaReserva, horaReserva, a, club.getCourt(pistaReserva), m); 
                    if(a) 
                    { 
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("¡Reserva realizada!");
                        alert.setHeaderText("");
                        alert.setContentText("Tiene introducidos sus datos bancarios correctamente la pista está pagada. Para actualizar la lista, salga y vuelva a entrar a esta ventana.");
                        alert.showAndWait();
                    }
                    else 
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("¡Reservas realizadas!");
                        alert.setHeaderText("");
                        alert.setContentText("No tiene introducidos sus datos bancarios correctamente pase por secretaría para pagarla. Para actualizar la lista, salga y vuelva a entrar a esta ventana.");
                        alert.showAndWait();
                    }
                }
            }
            else if(contador[2] < 1) 
            {
                FechaReserva1 = darFecha(contador[1]);
                LocalDateTime horaActual = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
                String fechaString = horaActual.format(formato);
                LocalTime horaReserva = LocalTime.parse(fechaString, formato);
                if(FechaReserva1.getHour() < Integer.parseInt(fechaString.substring(0, 2))) 
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la hora");
                    alert.setContentText("No puede reservar una pista cuya hora está en el pasado");
                    alert.showAndWait();
                } 
                else 
                {
                    boolean a = club.hasCreditCard(m.getNickName());  
                    Booking b1 = club.registerBooking(LocalDateTime.now(), fechaReserva, horaReserva, a, club.getCourt(pistaReserva), m); 
                    if(a) 
                    { 
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("¡Reserva realizada!");
                        alert.setHeaderText("");
                        alert.setContentText("Tiene introducidos sus datos bancarios correctamente la pista está pagada. Para actualizar la lista, salga y vuelva a entrar a esta ventana.");
                        alert.showAndWait();
                    }
                    else 
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("¡Reservas realizadas!");
                        alert.setHeaderText("");
                        alert.setContentText("No tiene introducidos sus datos bancarios correctamente pase por secretaría para pagarla. Para actualizar la lista, salga y vuelva a entrar a esta ventana.");
                        alert.showAndWait();
                    }
                }
            }
            else if (contador[1] >= 2 && contador[2] >= 2)
            {
                FechaReserva1 = darFecha(contador[1]);
                FechaReserva2 = darFecha(contador[2]);
                LocalDateTime horaActual = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
                String fechaString = horaActual.format(formato);
                LocalTime horaReserva = LocalTime.parse(fechaString, formato);
                
                LocalDateTime horaActual1 = LocalDateTime.now();
                DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("HH:mm");
                String fechaString1 = horaActual1.format(formato1);
                LocalTime horaReserva1 = LocalTime.parse(fechaString, formato);
                
                if(FechaReserva1.getHour() < Integer.parseInt(fechaString.substring(0, 2))) 
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la hora");
                    alert.setContentText("No puede reservar una pista cuya hora está en el pasado");
                    alert.showAndWait();
                } 
                else if(FechaReserva2.getHour() < Integer.parseInt(fechaString1.substring(0, 2))) 
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la hora");
                    alert.setContentText("No puede reservar una pista cuya hora está en el pasado");
                    alert.showAndWait();
                } 
                else 
                {
                    boolean a = club.hasCreditCard(m.getNickName());  
                    Booking b1 = club.registerBooking(LocalDateTime.now(), fechaReserva, horaReserva, a, club.getCourt(pistaReserva), m); 
                    Booking b2 = club.registerBooking(LocalDateTime.now(), fechaReserva, horaReserva1, a, club.getCourt(pistaReserva), m); 
                    if(a) 
                    { 
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("¡Reservas realizadas!");
                        alert.setHeaderText("");
                        alert.setContentText("Tiene introducidos sus datos bancarios correctamente las pistas están pagadas. Para actualizar la lista, salga y vuelva a entrar a esta ventana.");
                        alert.showAndWait();
                    }
                    else 
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("¡Reservas realizadas!");
                        alert.setHeaderText("");
                        alert.setContentText("No tiene introducidos sus datos bancarios correctamente pase por secretaría para pagarlas. Para actualizar la lista, salga y vuelva a entrar a esta ventana.");
                        alert.showAndWait();
                    }
                }
            }     
        }
    } 
}