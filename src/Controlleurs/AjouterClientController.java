package Controlleurs;

import Modeles.Exceptions.ClientExisteException;
import Modeles.Personnes.Receptionniste;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AjouterClientController {

    public static Receptionniste receptionnisteConnecte;  // Référence au réceptionniste fictif (partagée)

    private static int dernierIdClient = 0;  // Variable statique pour générer des IDs uniques

    @FXML
    private TextField nomField, prenomField, telephoneField, adresseField, statutField;

    @FXML
    public void initialize() {
        // Créer un réceptionniste fictif avec des valeurs par défaut
        if (receptionnisteConnecte == null) {
            receptionnisteConnecte = new Receptionniste(
                1, "NomFictif", "PrenomFictif", 123456789, "Adresse Fictive",
                2000.0, "12/03/2022", 101, "email@fictif.com", "password123"
            );
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
            // Utiliser le réceptionniste fictif pour ajouter un client avec le nouvel ID
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
