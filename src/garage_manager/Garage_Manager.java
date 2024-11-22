package garage_manager;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * @author LENOVO
 */
public class Garage_Manager extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Charger le fichier FXML qui contient la scène
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vues/Bienvenue.fxml")));

        // Créer la scène et associer l'élément root
        Scene scene = new Scene(root, 900, 600);

        // Appliquer la scène à la fenêtre (stage)
        stage.setScene(scene);

        // Afficher la fenêtre
        stage.setTitle("Garage Manager");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}












































