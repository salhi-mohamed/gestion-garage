package Controlleurs;

import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;

public class AjouterRendezVousController {

    @FXML
    private ComboBox<Client> comboClients;
    @FXML
    private ComboBox<Voiture> comboVoitures;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> heureComboBox;
    @FXML
    private ComboBox<String> statutComboBox;
    @FXML
    private TextField descriptionField;

    private Receptionniste receptionnisteConnecte;
    private int dernierIdRendezVous = 0;

    @FXML
    public void initialize() {
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }

        comboClients.getItems().setAll(receptionnisteConnecte.get_liste_clients());
        comboVoitures.getItems().setAll(receptionnisteConnecte.getListeVoitures());

        statutComboBox.setItems(FXCollections.observableArrayList("CONFIRME", "EN_ATTENTE", "ANNULE"));
    }

    @FXML
    public void ajouterRendezVous() {
        Client client = comboClients.getSelectionModel().getSelectedItem();
        Voiture voiture = comboVoitures.getSelectionModel().getSelectedItem();
        LocalDate date = datePicker.getValue();
        String description = descriptionField.getText();
        String statut = statutComboBox.getSelectionModel().getSelectedItem().toUpperCase().replaceAll("é", "e");

        if (client == null || voiture == null || date == null || description.isEmpty()  || statut == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        dernierIdRendezVous++;

        try {
            receptionnisteConnecte.creerRendezVous(dernierIdRendezVous, description, client.get_id(), voiture.get_immatriculation(), date, statut);
            showAlert("Succès", "Le rendez-vous a été ajouté avec succès !");
            closeWindow();
        } catch (Exception e) {
            e.printStackTrace(); // Affiche la trace d'erreur pour mieux comprendre ce qui échoue
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout du rendez-vous : " + e.getMessage());
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) descriptionField.getScene().getWindow();
        stage.close();
    }

    public void annuler(ActionEvent actionEvent) {
    }

    public void onClientSelected(ActionEvent actionEvent) {
    }
}
