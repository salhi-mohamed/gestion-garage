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

        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/AjouterPieceRechange.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'AjouterPieceRechange.fxml' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Ajouter PieceRechange");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Ajouter PieceRechange'.");
        }    }

    // Supprimer une fourniture
    @FXML
    private void supprimerFourniture() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/SupprimerFourniture.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'SupprimerFourniture' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Supprimer une Fourniture ");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Supprimer Fourniture'.");
        }
    }

    @FXML
    private void supprimerPiece() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/SupprimerPieceRechange.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'SupprimerPieceRechange' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Supprimer une PieceRechange ");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Supprimer PieceRechange'.");
        }
    }

    // Modifier une fourniture
    @FXML
    private void modifierFourniture() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/ModifierFourniture.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'ModifierFourniture' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Modifier ");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Modifier Fourniture'.");
        }
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
            e.printStackTrace(); // Affiche les détails de l'exception dans la console
            showAlert("Erreur", "Impossible de charger la page 'Afficher Fournitures'.\n" + e.getMessage());
        }
    }

    // Modifier une pièce de rechange
    @FXML
    private void modifierPiece() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/ModifierPieceRechange.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'ModifierPieceRechange' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Modifier PieceRechange");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Modifier PieceRechange'.");
        }    }

    // Afficher les pièces de rechange
    @FXML
    private void afficherPieces() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/AfficherPieceRechange.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'AfficherPieceRechange.fxml' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Afficher PieceRechange");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Affiche les détails de l'exception dans la console
            showAlert("Erreur", "Impossible de charger la page 'Afficher PieceRechange'.\n" + e.getMessage());
        }    }

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
