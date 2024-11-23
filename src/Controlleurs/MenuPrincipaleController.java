package Controlleurs;

import Modeles.Personnes.Receptionniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import java.io.IOException;

public class MenuPrincipaleController {

    // Déclaration de la variable static pour le réceptionniste connecté
    public static Receptionniste receptionnisteConnecte;

    private static final double MAIN_MENU_WIDTH = 1237.0;
    private static final double MAIN_MENU_HEIGHT = 879.0;

    @FXML
    public void initialize() {
        // Initialisation du réceptionniste avec des valeurs spécifiques pour le constructeur
        if (receptionnisteConnecte == null) {
            receptionnisteConnecte = new Receptionniste(
                1,  // id
                "Dupont",  // nom
                "Pierre",  // prénom
                123456789,  // téléphone
                "123 rue Exemple",  // adresse
                2500.00,  // salaire
                "12/03/2022",  // date d'embauche
                101,  // numéro du bureau
                "pierre.dupont@example.com",  // email
                "password123"  // mot de passe
            );
        }
    }

    @FXML
    public void ouvrirClients(ActionEvent event) {
        ouvrirNouvelleScene("/Vues/GestionClients.fxml", event);
    }

    @FXML
    public void ouvrirVoitures(ActionEvent event) {
        ouvrirNouvelleScene("/Vues/GestionVoitures.fxml", event);
    }

    @FXML
    public void ouvrirEmployes(ActionEvent event) {
       // ouvrirNouvelleScene("/path/to/Employes.fxml", event);
    }

    @FXML
    public void ouvrirRendezVous(ActionEvent event) {
       // ouvrirNouvelleScene("/path/to/RendezVous.fxml", event);
    }

    @FXML
    public void ouvrirFactures(ActionEvent event) {
        //ouvrirNouvelleScene("/path/to/Factures.fxml", event);
    }

    @FXML
    public void ouvrirStock(ActionEvent event) {
        ouvrirNouvelleScene("/Vues/GestionStocks.fxml", event);
    }

    private void ouvrirNouvelleScene(String fxmlPath, ActionEvent event) {
        try {
            // Charger le fichier FXML pour la scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Obtenir le stage actuel (fenêtre)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Obtenir les dimensions actuelles du stage (fenêtre)
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();

            // Créer la nouvelle scène avec les mêmes dimensions
            Scene scene = new Scene(root, currentWidth, currentHeight);
            stage.setScene(scene);

            // Afficher la fenêtre
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la scène : " + e.getMessage());
        }
    }
}
