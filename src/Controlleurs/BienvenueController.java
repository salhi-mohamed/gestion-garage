package controlleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class BienvenueController {

    @FXML
    private ImageView backgroundImage;

    @FXML
    public void initialize() {
        // Load the image in the controller
        Image image = new Image("https://assets.newatlas.com/dims4/default/89191df/2147483647/strip/true/crop/4508x3005+0+0/resize/2880x1920!/quality/90/?url=http%3A%2F%2Fnewatlas-brightspot.s3.amazonaws.com%2F24%2F6f%2F92f2defe4a4998c6bcaa9c2cfb3f%2Fabt-audi-rs6-gr-22-glossyblack-4.jpg");
        backgroundImage.setImage(image);
    }

    // Méthode appelée lors du clic sur le bouton "Commencer"
    @FXML
   
public void commencer(ActionEvent event) {
    try {
        // Charger le fichier MenuPrincipale.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/MenuPrincipale.fxml"));
        Parent root = loader.load();

        // Obtenir le stage actuel
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Définir les mêmes dimensions que la scène actuelle
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();

        // Définir la nouvelle scène avec la même taille
        Scene scene = new Scene(root, currentWidth, currentHeight);
        stage.setScene(scene);

        // Afficher la fenêtre
        stage.show();
    } catch (IOException e) {
        System.out.println("Erreur lors du chargement de MenuPrincipale.fxml : " + e.getMessage());
    }
}

}
