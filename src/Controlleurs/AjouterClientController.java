package Controlleurs;


import Modeles.Exceptions.ClientExisteException;
import Modeles.Personnes.Receptionniste;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AjouterClientController {

    private Receptionniste receptionnisteConnecte;  // Référence au réceptionniste connecté

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

    public void ajouterClient() {
        // Récupérer les données depuis les champs de texte
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        int telephone = Integer.parseInt(telephoneField.getText());
        String adresse = adresseField.getText();
        String statutFinancier = statutField.getText();

        // Générer un nouvel ID unique en l'incrémentant
        dernierIdClient++;  // Incrémente l'ID à chaque ajout

        try {
            // Utiliser le réceptionniste pour ajouter un client avec le nouvel ID
            receptionnisteConnecte.creerClient(dernierIdClient, nom, prenom, telephone, adresse, statutFinancier);
            showAlert("Succès", "Client ajouté avec succès !");
        } catch (ClientExisteException e) {
            showAlert("Erreur", "Ce client existe déjà.");
        } catch (Exception e) {
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
