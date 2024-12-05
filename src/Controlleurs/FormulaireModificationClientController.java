package Controlleurs;

import Modeles.Personnes.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Contrôleur pour le formulaire de modification des informations d'un client.
 */
public class FormulaireModificationClientController {

    @FXML
    private TextField textFieldNom;

    @FXML
    private TextField textFieldPrenom;

    @FXML
    private TextField textFieldTelephone;

    @FXML
    private TextField textFieldAdresse;

    private Client client;

    /*
     * Méthode pour initialiser le formulaire avec les données du client sélectionné.
     * @param client Le client à modifier
     */
    public void setClient(Client client) {
        this.client = client;

        // Remplir les champs avec les informations actuelles du client
        textFieldNom.setText(client.get_nom());
        textFieldPrenom.setText(client.get_prenom());
        textFieldTelephone.setText(String.valueOf(client.get_telephone()));
        textFieldAdresse.setText(client.get_adresse());
    }

    /*
     * Méthode pour enregistrer les modifications effectuées sur les informations du client.
     */
    @FXML
    private void enregistrerModifications() {
        // Vérifier que les champs sont valides avant de mettre à jour les informations
        try {
            client.set_nom(textFieldNom.getText());
            client.set_prenom(textFieldPrenom.getText());
            client.set_telephone(Integer.parseInt(textFieldTelephone.getText()));
            client.set_adresse(textFieldAdresse.getText());

            // Sauvegarder dans la base de données si nécessaire
            // Exemple : receptionnisteConnecte.modifierClient(client);

            // Fermer la fenêtre après l'enregistrement
            Stage stage = (Stage) textFieldNom.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si le téléphone est invalide
            showAlert("Erreur", "Le numéro de téléphone doit être un nombre valide.");
        }
    }

    /*
     * Méthode pour fermer le formulaire sans enregistrer les modifications.
     */
    @FXML
    private void fermerFormulaire() {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) textFieldNom.getScene().getWindow();
        stage.close();
    }

    /*
     * Affiche une alerte avec un titre et un message.
     * @param title Titre de l'alerte
     * @param message Message à afficher
     */
    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
