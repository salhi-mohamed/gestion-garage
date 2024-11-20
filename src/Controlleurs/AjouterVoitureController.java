package Controlleurs;

import Modeles.Exceptions.VoitureDejaExistanteClientException;
import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Receptionniste;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AjouterVoitureController {

    @FXML
    private TextField idClientField, marqueField, modeleField, anneeField, kilometrageField, immatriculationField;

    @FXML
    public void initialize() {
        // Initialisation si nécessaire
        if (AjouterClientController.receptionnisteConnecte == null) {
            showAlert("Erreur", "Veuillez ajouter un client d'abord.");
        }
    }

    public void ajouterVoiture() {
        // Vérification de la connexion du réceptionniste
        Receptionniste receptionniste = AjouterClientController.receptionnisteConnecte;
        if (receptionniste == null) {
            showAlert("Erreur", "Aucun réceptionniste connecté. Veuillez réessayer.");
            return;
        }

        try {
            // Récupérer les données saisies
            int idClient = Integer.parseInt(idClientField.getText());
            String marque = marqueField.getText();
            String modele = modeleField.getText();
            int annee = Integer.parseInt(anneeField.getText());
            long kilometrage = Long.parseLong(kilometrageField.getText());
            String immatriculation = immatriculationField.getText();

            // Appeler la méthode du réceptionniste pour créer la voiture
            receptionniste.creerVoiture(idClient, marque, modele, annee, kilometrage, immatriculation);

            showAlert("Succès", "Voiture ajoutée avec succès !");
            clearFields(); // Vider les champs après ajout

        } catch (VoitureDejaExistanteClientException e) {
            showAlert("Erreur", "Cette voiture est déjà associée au client.");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez vérifier les champs numériques (ID, année, kilométrage).");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    private void clearFields() {
        idClientField.clear();
        marqueField.clear();
        modeleField.clear();
        anneeField.clear();
        kilometrageField.clear();
        immatriculationField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
