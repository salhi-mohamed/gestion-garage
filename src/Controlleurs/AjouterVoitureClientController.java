package Controlleurs;

import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Client;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AjouterVoitureClientController {

    @FXML
    private ComboBox<Client> comboBoxClients;  // Pour sélectionner un client

    @FXML
    private TextField textFieldMarque, textFieldModele, textFieldKilometrage, textFieldImmatriculation;

    private ObservableList<Client> clientsList;

    public void initialize() {
        // Charger la liste des clients dans le ComboBox
        clientsList = FXCollections.observableArrayList(AjouterClientController.receptionnisteConnecte.get_liste_clients());
        comboBoxClients.setItems(clientsList);
    }

    @FXML
    public void ajouterVoiture() {
        // Récupérer les données du formulaire
        Client selectedClient = comboBoxClients.getSelectionModel().getSelectedItem();
        String marque = textFieldMarque.getText();
        String modele = textFieldModele.getText();
        String kilometrageStr = textFieldKilometrage.getText();
        String immatriculation = textFieldImmatriculation.getText();

        // Vérifier si un client est sélectionné
        if (selectedClient == null) {
            showAlert("Erreur", "Veuillez sélectionner un client.");
            return;
        }

        // Vérifier si tous les champs sont remplis
        if (marque.isEmpty() || modele.isEmpty() || kilometrageStr.isEmpty() || immatriculation.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            int kilometrage = Integer.parseInt(kilometrageStr); // Convertir le kilométrage en entier
            Voiture nouvelleVoiture = new Voiture(marque, modele, 2024, kilometrage, immatriculation, selectedClient);

            // Appeler la méthode pour ajouter la voiture au client
            AjouterClientController.receptionnisteConnecte.ajouterVoitureAuClient(selectedClient.get_id(), nouvelleVoiture);

            showAlert("Succès", "La voiture a été ajoutée au client avec succès.");

            // Fermer le formulaire (vous pouvez aussi réinitialiser les champs si nécessaire)
            fermerFormulaire();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le kilométrage doit être un nombre valide.");
        }
    }

    @FXML
    public void fermerFormulaire() {
        // Logic to close the form or reset the fields (depends on your requirements)
        textFieldMarque.clear();
        textFieldModele.clear();
        textFieldKilometrage.clear();
        textFieldImmatriculation.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
