package Controlleurs;

import Modeles.Exceptions.ClientExisteException;
import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AjouterClientController {

    private Receptionniste receptionnisteConnecte;  // Référence au réceptionniste connecté
    private AjouterVoitureController ajouterVoitureController;  // Référence à AjouterVoitureController

    private static int dernierIdClient = 0;  // Variable statique pour générer des IDs uniques

    @FXML
    private TextField nomField, prenomField, telephoneField, adresseField, statutField;

    @FXML
    public void initialize() {
        // Récupérer le réceptionniste connecté depuis le MenuPrincipaleController
        if (MenuPrincipaleController.receptionnisteConnecte != null) {
            receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;
        } else {
            showAlert("Erreur", "Aucun réceptionniste connecté.");
        }
    }

    // Méthode pour lier le contrôleur AjouterVoitureController
    public void setAjouterVoitureController(AjouterVoitureController controller) {
        this.ajouterVoitureController = controller;
    }

    public void ajouterClient() {
        // Récupérer les données depuis les champs de texte
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String telephoneText = telephoneField.getText();  // Texte du champ téléphone
        String adresse = adresseField.getText();
        String statutFinancier = statutField.getText();

        // Validation de l'entrée du numéro de téléphone
        int telephone = 0;
        try {
            if (!telephoneText.isEmpty()) {
                telephone = Integer.parseInt(telephoneText);
            } else {
                throw new NumberFormatException("Le numéro de téléphone ne peut pas être vide.");
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Numéro de téléphone invalide. Veuillez entrer un nombre valide.");
            return;  // Sortir de la méthode si l'entrée est invalide
        }

        // Générer un nouvel ID unique en l'incrémentant
        dernierIdClient++;  // Incrémente l'ID à chaque ajout

        try {
            // Utiliser le réceptionniste pour ajouter un client avec le nouvel ID
            receptionnisteConnecte.creerClient(dernierIdClient, nom, prenom, telephone, adresse, statutFinancier);
            showAlert("Succès", "Client ajouté avec succès !");

            // Mettre à jour la TableView dans AjouterVoitureController
            if (ajouterVoitureController != null) {
                ajouterVoitureController.updateClientsTable();
            }

        } catch (ClientExisteException e) {
            showAlert("Erreur", "Ce client existe déjà.");
        } catch (Exception e) {
            e.printStackTrace();  // Affiche la trace d'exception dans la console
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout du client.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}