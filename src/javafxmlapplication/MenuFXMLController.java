/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Court;
import model.Member;
import model.Booking;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import static model.Club.getInstance;
import javafx.scene.control.MenuButton;

/**
 * FXML Controller class
 *
 * @author UX431
 */


public class MenuFXMLController implements Initializable {
    
    private Club club; 
    
    private Member m; 
    
    private Booking reserva;
    
    @FXML
    private DatePicker calendarioBoton;
    @FXML
    private ComboBox<String> seleccionPistaBoton;
    @FXML
    private ImageView fotoPerfil;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelPistaReservada;
    @FXML
    private GridPane GridPane;
    @FXML
    private MenuItem miReserva;
    @FXML
    private MenuItem hacerReserva;
    @FXML
    private MenuItem modificar;
    @FXML
    private MenuItem cerrar;
    @FXML
    private Button mostrarDisponBoton;
    @FXML
    private MenuButton opcionesBoton;
    @FXML
    private Label saberFecha;
    @FXML
    private Label saberPista;
    
    /**
     * Initializes the controller class.
     */
    
    public void initUsuario(Member member) {
       m = member;  
    }
    
    public static int cambiarStrAInt(String str) {
        return Integer.parseInt(str);
    }
    
    public boolean devolverHoraReserva(List<Booking> ar, LocalTime local) 
    {
        boolean devolver = false; 
        for(int i = 0; i < ar.size(); i++)
        {
            reserva = ar.get(i);
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
    
    public boolean tienesReserva(List<Booking> array, Member m) 
    {
        for(int i = 0; i < array.size(); i++) 
        {
            Booking b = array.get(i); 
            if(b.equals(null) & !b.belongsToMember(m.getNickName())) {return false;}
        }
        return true; 
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
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        try {
            club = getInstance();  
            
        } catch (ClubDAOException | IOException e)  {}
                      
        opcionesBoton.setId("boton_blanco_a_sombra");
        mostrarDisponBoton.setId("boton_verde_a_sombra");
    }   


    @FXML
    private void mostrarDisponibilidad(ActionEvent event) throws ClubDAOException, IOException, NullPointerException
    {
        club = getInstance(); 
        
        List<Booking> horarioDePista = new ArrayList<>(); 
        
        LocalDate fecha = calendarioBoton.getValue(); 
        LocalDate fecha1 = fecha; 
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaString = fecha1.format(formato);
        String pista = seleccionPistaBoton.getValue(); 
                    
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
                        label.setText(horaInicioTexto + " - " + horaFinTexto + ".  Reservado por: "+ m.getNickName() +"                                                                               ");  
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
                        label.setText(horaInicioTexto + " - " + horaFinTexto + ".  Reservado por: " + reserva.getMember().getNickName() + "                                                                                      ");  
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
                    }
                }    
                saberFecha.setText("Está mostrando la fecha "+ fechaString); 
                saberPista.setText("Está mostrando la pista "+ pista);
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
                        label.setText(horaInicioTexto + " - " + horaFinTexto + ".  Reservado por: "+ m.getNickName() +"                                                                               ");  
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
                        label.setText(horaInicioTexto + " - " + horaFinTexto + ".  Reservado por: " + reserva.getMember().getNickName() + "                                                                                      ");  
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
            saberFecha.setText("Está mostrando la fecha "+ fechaString); 
            saberPista.setText("Está mostrando la pista "+ pista);
        }
    }

    @FXML
    private void menuMiReserva(ActionEvent event) throws IOException 
    {      
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
    private void menuReservar(ActionEvent event) throws IOException 
    {     
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
    private void modificarPerfil(ActionEvent event) throws IOException 
    {
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
    private void cerrarSesion(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Estás seguro de que deseas cerrar sesión?");

        ButtonType cancelarButton = new ButtonType("Cancelar", ButtonData.OK_DONE);
        ButtonType cerrarSesionButton = new ButtonType("Cerrar sesión", ButtonData.CANCEL_CLOSE);
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
}
