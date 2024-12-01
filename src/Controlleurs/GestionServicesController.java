package Controlleurs;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GestionServicesController {
    @FXML
    private Button btnAjouterFournitureService;
    public Button btnAjouterPieceService;
    @FXML
    private Button btnAjouterService;
    @FXML
    private Button btnSupprimerService;
    @FXML
    private Button btnModifierService;
    @FXML
    private Button btnAfficherServices;
    @FXML
    private Button btnAjouterRendezVous;
    @FXML
    private Button btnSupprimerRendezVous;
    @FXML
    private Button btnModifierRendezVous;
    @FXML
    private Button btnAfficherRendezVous;
    @FXML
    private Button btnRetour;

    // Ajouter un service
    @FXML
    private void ajouterService() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/AjouterService.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'AjouterService.fxml' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Ajouter un service");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // This will print the full stack trace of the exception

            showAlert("Erreur", "Impossible de charger la page 'Ajouter Service'.");
        }    }

    // Supprimer un service
    @FXML
    private void supprimerService() {
        chargerVue("/Vues/SupprimerService.fxml", "Supprimer un Service");
    }

    // Modifier un service
    @FXML
    private void modifierService() {
        chargerVue("/Vues/ModifierService.fxml", "Modifier un Service");
    }

    // Afficher les services
    @FXML
    private void afficherServices() {
        chargerVue("/Vues/AfficherServices.fxml", "Afficher les Services");
    }

    @FXML
    private void ajouterFournitureService() {
        chargerVue("/Vues/AjouterFournitureService.fxml", "Ajouter Fourniture au Service");
    }

    @FXML
    private void ajouterPieceService() {
        chargerVue("/Vues/AjouterPieceService.fxml", "Ajouter Fourniture au Service");
    }




    // Ajouter un rendez-vous
    @FXML
    private void ajouterRendezVous() {
        chargerVue("/Vues/AjouterRendezVous.fxml", "Ajouter un Rendez-Vous");
    }

    // Supprimer un rendez-vous
    @FXML
    private void supprimerRendezVous() {
        chargerVue("/Vues/SupprimerRendezVous.fxml", "Supprimer un Rendez-Vous");
    }

    // Modifier un rendez-vous
    @FXML
    private void modifierRendezVous() {
        chargerVue("/Vues/ModifierRendezVous.fxml", "Modifier un Rendez-Vous");
    }

    // Afficher les rendez-vous
    @FXML
    private void afficherRendezVous() {
        chargerVue("/Vues/AfficherRendezVous.fxml", "Afficher les Rendez-Vous");
    }

    // Retourner au menu principal
    @FXML
    private void retour() {
        try {
            URL fxmlLocation = getClass().getResource("/Vues/MenuPrincipale.fxml");
            if (fxmlLocation == null) {
                showAlert("Erreur", "Le fichier FXML 'MenuPrincipale.fxml' n'a pas été trouvé.");
                return;
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) btnRetour.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("Menu Principal");
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Menu Principal'.");
        }
    }

    // Méthode utilitaire pour charger une vue
    private void chargerVue(String cheminFXML, String titreFenetre) {
        try {
            URL fxmlLocation = getClass().getResource(cheminFXML);
            if (fxmlLocation == null) {
                showAlert("Erreur", "Le fichier FXML '" + cheminFXML + "' n'a pas été trouvé.");
                return;
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle(titreFenetre);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page '" + titreFenetre + "'.");
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void ajouterPieceService(ActionEvent actionEvent) {
    }
}
