package Controlleurs;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GestionVoituresController {

    @FXML
    private Button btnAjouterVoiture;

    @FXML
    private Button btnSupprimerVoiture;

    @FXML
    private Button btnModifierVoiture;

    @FXML
    private Button btnAfficherVoitures;
    @FXML
    private Button btnRetour;
    @FXML
    private void ajouterVoiture() {
        try {
            // Vérifier le chemin du fichier FXML
            URL fxmlLocation = getClass().getResource("/Vues/AjouterVoiture.fxml");
            if (fxmlLocation == null) {
                showAlert("Erreur", "Le fichier FXML 'AjouterVoiture.fxml' n'a pas été trouvé.");
                return;
            }

            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Ajouter une Voiture");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Ajouter Voiture' : " + e.getMessage());
        }
    }

    @FXML
    private void supprimerVoiture() {
        // Placeholder pour la fonctionnalité de suppression
        showAlert("Supprimer Voiture", "La fonctionnalité 'Supprimer Voiture' sera implémentée ici.");
    }

    @FXML
    private void modifierVoiture() {
        // Placeholder pour la fonctionnalité de modification
        showAlert("Modifier Voiture", "La fonctionnalité 'Modifier Voiture' sera implémentée ici.");
    }

    @FXML
    


private void afficherVoitures() {
    try {
        // Log: Afficher le chemin du fichier FXML
        String fxmlPath = "/Vues/AfficherVoitures.fxml";
        System.out.println("Chargement du fichier FXML : " + fxmlPath);

        // Charger le fichier FXML avec un chemin relatif à partir de src
        URL fxmlLocation = getClass().getResource(fxmlPath);

        // Log: Vérification si le fichier est trouvé
        if (fxmlLocation == null) {
            System.err.println("Le fichier FXML '" + fxmlPath + "' n'a pas été trouvé.");
            showAlert("Erreur", "Le fichier FXML 'AfficherVoitures.fxml' n'a pas été trouvé.");
            return;
        }

        // Log: Afficher le chemin complet du fichier FXML trouvé
        System.out.println("Le fichier FXML a été trouvé : " + fxmlLocation);

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Afficher les Voitures");
        stage.setScene(scene);
        stage.show();

        // Log: Indication que la scène a été chargée et affichée
        System.out.println("La scène 'Afficher les Voitures' a été chargée et affichée.");

    } catch (IOException e) {
        // Log: Détails de l'exception en cas d'échec du chargement
        System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
        e.printStackTrace(); // Affiche le stack trace complet pour diagnostiquer l'erreur
        showAlert("Erreur", "Impossible de charger la page 'Afficher Voitures'.");
    }
}

    @FXML
    private void retour() {
        try {
            // Charger le fichier FXML du menu principal
            URL fxmlLocation = getClass().getResource("/Vues/MenuPrincipale.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'MenuPrincipale.fxml' n'a pas été trouvé.");
                return;
            }

            // Charger la scène du menu principal
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());

            // Obtenir le Stage (fenêtre actuelle)
            Stage stage = (Stage) btnRetour.getScene().getWindow();

            // S'assurer que le Stage est valide
            if (stage != null) {
                stage.setTitle("Menu Principal");
                stage.setScene(scene);  // Changer la scène pour le menu principal
                stage.show();           // Afficher la nouvelle scène (menu principal)
            } else {
                System.out.println("Stage est nul. Impossible de changer la scène.");
            }
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Menu Principal'.");
        }
    }

    // Méthode pour afficher les alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR); // Utilisation de ERROR pour les alertes d'erreur
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
