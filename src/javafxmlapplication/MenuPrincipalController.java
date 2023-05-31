/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Booking;
import model.Club;
import static model.Club.getInstance;
import model.Court;
import model.ClubDAOException;

/**
 * FXML Controller class
 *
 * @author david
 */
public class MenuPrincipalController extends ListCell<String> implements Initializable {

    private Club club;
    
    private Booking reserva;

    @FXML
    private Button registro;
    @FXML
    private Button autenticarse;
    @FXML
    private DatePicker calendarioBoton;
    @FXML
    private ComboBox<String> seleccionPistaBoton;
    @FXML
    private GridPane GridPane;
    @FXML
    private Button disponibilidadBoton;
    @FXML
    private Label saberFecha;
    @FXML
    private Label saberPista;

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
        
        registro.setId("boton_blanco_a_sombra");
        autenticarse.setId("boton_blanco_a_sombra");
        disponibilidadBoton.setId("boton_verde_a_sombra");
        seleccionPistaBoton.setId("seleccionPistaBoton"); 
        calendarioBoton.setId("calendarioBoton");
        
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
  
    @FXML
    private void accederRegistro(ActionEvent event) throws IOException 
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
        //la ventana se muestra modal
        stage.show();
        Stage myStage = (Stage) registro.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void accederAutenticarse(ActionEvent event) throws IOException 
    {     
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/javafxmlapplication/IniciarSesion.fxml"));
        Parent root = miCargador.load();       
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("textfield.css").toExternalForm()); 
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(680);
        stage.setMinHeight(400);
        stage.setMaxWidth(800);
        stage.setMaxHeight(600);
        stage.setTitle("Autenticarse");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        Stage myStage = (Stage) autenticarse.getScene().getWindow();
        myStage.close();
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
            ButtonType botonNo = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(botonSi, botonNo);
            Optional<ButtonType> resultado = alert.showAndWait();
            
            
            if (resultado.isPresent() && resultado.get() == botonSi) 
            {
                LocalTime horaInicio = LocalTime.of(9, 0);
                int duracion = club.getBookingDuration();
                int RerservasPistas = club.getBookingSlots(); 
                
                
                ObservableList<Node> listaDeCosas = GridPane.getChildren();
                Iterator<Node> iterator = listaDeCosas.iterator();

                while (iterator.hasNext()) {
                    Node child = iterator.next();
                    if (child instanceof Label) {
                        iterator.remove(); 
                    }
                }       
                
                for (int i = 0; i < RerservasPistas; i++) 
                { 
                    
                     if(devolverHoraReserva(horarioDePista, horaInicio)) 
                    {
                        Label label = new Label(); 
                        LocalTime horaFin = horaInicio.plusMinutes(duracion);        
                        String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
                        String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));
                        label.setText(horaInicioTexto + " - " + horaFinTexto + ".  Reservado por: " + reserva.getMember().getNickName() + "                                                                                         ");  
                        label.setStyle("-fx-background-color: #ffc8c8");
                        GridPane.add(label, 1, i); 
                        GridPane.getChildren().get(i + 1).setId("celda"); 
                        horaInicio = horaFin;
                    } 
                    else {
                                                  
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
            
            for (int i = 0; i < RerservasPistas; i++) {
                    
                if(devolverHoraReserva(horarioDePista, horaInicio)) 
                {       
                        Label label = new Label();
                        double anchura = GridPane.getColumnConstraints().get(1).getPrefWidth();
                        label.minWidth(anchura);
                        LocalTime horaFin = horaInicio.plusMinutes(duracion);        
                        String horaInicioTexto = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));
                        String horaFinTexto = horaFin.format(DateTimeFormatter.ofPattern("HH:mm"));
label.setText(horaInicioTexto + " - " + horaFinTexto + ".  Reservado por: " + reserva.getMember().getNickName() + "                                                                                         ");                          label.setStyle("-fx-background-color: #ffc8c8; -fx-background-insets: 0");
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
}

