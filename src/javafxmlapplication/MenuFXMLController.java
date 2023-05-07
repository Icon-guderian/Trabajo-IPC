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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import static model.Club.getInstance;
import model.ClubDAOException;
import model.Court;
import model.Member;
import model.Booking;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import static model.Club.getInstance;

/**
 * FXML Controller class
 *
 * @author UX431
 */


public class MenuFXMLController implements Initializable {
    
    private Club club; 
    
    private Booking b;
    
    private Member m; 
    
    @FXML
    private DatePicker calendarioBoton;
    @FXML
    private ComboBox<String> seleccionPistaBoton;
    @FXML
    private Button miReserva;
    @FXML
    private Button hacerReserva;
    @FXML
    private Button modificar;
    @FXML
    private ImageView fotoPerfil;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelPistaReservada;
    @FXML
    private GridPane GridPane;
    @FXML
    private Button cerrar;
    @FXML
    private Button mostrarDisponBoton;
    
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
        seleccionPistaBoton.setItems(items);  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        try {
            club = getInstance();  
            
        } catch (ClubDAOException | IOException e)  {}
                      
    }   

    
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
    private void mostrarDisponibilidad(ActionEvent event) throws ClubDAOException, IOException 
    {
        club = getInstance(); 
        
        List<Booking> horarioDePista = new ArrayList<>(); 
        
        LocalDate fecha = calendarioBoton.getValue(); 
        LocalDate fechaActual = LocalDate.now();
        String pista = seleccionPistaBoton.getValue(); 

                    
        if(fecha == null) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la fecha");
            alert.setContentText("Por favor introduzca una fecha");
            alert.showAndWait();
        
        }
        else if(pista == null) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Pista");
            alert.setContentText("Debe seleccionar 1 pista");
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
                int RerservasPistas = club.getBookingSlots(); 
                
                //borra el gridpane si hay cosas
                ObservableList<Node> listaDeCosas = GridPane.getChildren();
                Iterator<Node> iterator = listaDeCosas.iterator();
                while (iterator.hasNext()) {
                    Node child = iterator.next();
                    if (child instanceof Label) {
                        iterator.remove(); 
                    }
                }       
                
                for (int i = 0, j = 0; i < RerservasPistas ; i++) 
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
                        horaInicio = horaFin;
                    }
                }           
            } 
        } 
        else 
        {
            LocalTime horaInicio = LocalTime.of(9, 0);
            int duracion = club.getBookingDuration();
            int RerservasPistas = club.getBookingSlots(); 
            horarioDePista = club.getCourtBookings(pista, fecha); 
            
            ObservableList<Node> listaDeCosas = GridPane.getChildren();
            Iterator<Node> iterator = listaDeCosas.iterator();

            while (iterator.hasNext()) {
                Node child = iterator.next();
                if (child instanceof Label) {
                    iterator.remove(); 
                }
            }       
            
            for (int i = 0, j = 0; i < RerservasPistas ; i++) 
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
                        horaInicio = horaFin;
                        
                }       
            }  
        }
    }

    @FXML
    private void menuMiReserva(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/MisReservas.fxml"));
        Parent root = miCargador.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Mis reservas");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        Stage myStage = (Stage) miReserva.getScene().getWindow();
        myStage.close();
    }    
    
    @FXML
    private void menuReservar(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/HacerReservas.fxml"));
        Parent root = miCargador.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Reservar Pistas");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        Stage myStage = (Stage) hacerReserva.getScene().getWindow();
        myStage.close();
    }
    
    @FXML
    private void modificarPerfil(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/ModificarPerfil.fxml"));
        Parent root = miCargador.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Modificar Perfil");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        Stage myStage = (Stage) modificar.getScene().getWindow();
        myStage.close();
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
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/MenuPrincipal.fxml"));
            Parent root = miCargador.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("GreenBall");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show(); 
            Stage myStage = (Stage) cerrar.getScene().getWindow();
            myStage.close();
        } 
        else 
        { 
            alert.close(); 
        }
    }    
}
