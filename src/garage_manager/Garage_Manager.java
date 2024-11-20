package garage_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Modeles.Personnes.*;
import Modeles.Exceptions.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class Garage_Manager extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        // Charger le fichier FXML qui contient la scène
        Parent root = FXMLLoader.load(getClass().getResource("/Vues/Bienvenue.fxml"));
        
        // Créer la scène et associer l'élément root
        Scene scene = new Scene(root , 900,600);
        
        // Appliquer la scène à la fenêtre (stage)
        stage.setScene(scene );
        
        // Afficher la fenêtre
        stage.setTitle("Garage Manager");
        stage.show();
    }

    public static void main(String[] args) {
        try {
             launch(args);
            Receptionniste r=new Receptionniste(0, "dd", "dd", 222, "dd", 2500, "01/02/2002", 5, "dd", "fff");
            
            r.creerClient(0, "dddd", "ddd", 0, "ddd", "dddd");
            r.afficherListeClients();
        } catch (ClientExisteException ex) {
            Logger.getLogger(Garage_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
}
