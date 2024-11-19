package controlleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class BienvenueController {

    @FXML
    private StackPane stackPane;

    @FXML
    private ImageView backgroundImage;

    @FXML
    public void initialize() {
        // Charger l'image depuis une URL distante
        String imageUrl = "https://assets.newatlas.com/dims4/default/89191df/2147483647/strip/true/crop/4508x3005+0+0/resize/2880x1920!/quality/90/?url=http%3A%2F%2Fnewatlas-brightspot.s3.amazonaws.com%2F24%2F6f%2F92f2defe4a4998c6bcaa9c2cfb3f%2Fabt-audi-rs6-gr-22-glossyblack-4.jpg"; // Remplacez cette URL par l'URL réelle de votre image
        try {
            Image image = new Image(imageUrl);  // Charger l'image depuis l'URL
            backgroundImage.setImage(image);   // Afficher l'image dans le ImageView
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }
    }

    // Méthode appelée lors du clic sur le bouton "Commencer"
    @FXML
    public void commencer(ActionEvent event) {
        // Logique à exécuter lorsque l'utilisateur clique sur le bouton
        System.out.println("Le bouton Commencer a été cliqué !");
    }
}