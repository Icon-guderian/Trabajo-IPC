/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class JavaFXMLApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 670, 750);
        scene.getStylesheets().add(getClass().getResource("estilosBotones.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("calendario.css").toExternalForm()); 
        scene.getStylesheets().add(getClass().getResource("textfield.css").toExternalForm());
        stage.setScene(scene);
        stage.setMinWidth(650);
        stage.setMinHeight(700);
        stage.setMaxWidth(900);
        stage.setMaxHeight(1000);
        stage.setTitle("GreenBall");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
}
