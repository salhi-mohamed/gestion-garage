/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package garage_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Modeles.Personnes.*;
import Modeles.Exceptions.*;
/**
 *
 * @author LENOVO
 */
public class Garage_Manager extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
Parent root = FXMLLoader.load(getClass().getResource("/Vues/Bienvenue.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
        
       
    }
    
}
