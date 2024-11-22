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

public class GestionStocksController {

    @FXML
    private Button btnAjouterFourniture;
    @FXML
    private Button btnSupprimerFourniture;
    @FXML
    private Button btnModifierFourniture;
    @FXML
    private Button btnAfficherFournitures;
    @FXML
    private Button btnAjouterPiece;
    @FXML
    private Button btnSupprimerPiece;
    @FXML
    private Button btnModifierPiece;
    @FXML
    private Button btnAfficherPieces;

    @FXML
    private Button btnRetour;

    // Ajouter une fourniture
    @FXML
    private void ajouterFourniture() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/AjouterFourniture.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'AjouterFourniture.fxml' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Ajouter une Fourniture");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Ajouter Fourniture'.");
        }
    }

    @FXML
    private void ajouterPiece() {
        showAlert("Ajouter Piece", "La fonctionnalité 'Ajouter Piece' sera implémentée ici.");
    }

    // Supprimer une fourniture
    @FXML
    private void supprimerFourniture() {
        showAlert("Supprimer Fourniture", "La fonctionnalité 'Supprimer Fourniture' sera implémentée ici.");
    }

    @FXML
    private void supprimerPiece() {
        showAlert("supprimer Piece ","La fonctionnalité 'Supprimer Piece' sera implémentée ici.");
    }

    // Modifier une fourniture
    @FXML
    private void modifierFourniture() {
        showAlert("Modifier Fourniture", "La fonctionnalité 'Modifier Fourniture' sera implémentée ici.");
    }

    // Afficher les fournitures
    @FXML
    private void afficherFournitures() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/AfficherFournitures.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'AfficherFournitures.fxml' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Afficher Fournitures");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Afficher Fournitures'.");
        }    }

    // Modifier une pièce de rechange
    @FXML
    private void modifierPiece() {
        showAlert("Modifier Pièce de Rechange", "La fonctionnalité 'Modifier Pièce de Rechange' sera implémentée ici.");
    }

    // Afficher les pièces de rechange
    @FXML
    private void afficherPieces() {
        showAlert("Afficher Pièces de Rechange", "La fonctionnalité 'Afficher Pièces de Rechange' sera implémentée ici.");
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
}
