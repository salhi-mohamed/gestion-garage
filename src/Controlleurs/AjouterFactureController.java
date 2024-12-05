package Controlleurs;

import Modeles.Gestion_Service.Facture;
import Modeles.Gestion_Service.Service;
import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AjouterFactureController {

    @FXML
    private ComboBox<Client> comboClients;

    @FXML
    private ListView<Service> listServices;

    @FXML
    private DatePicker datePicker;

    @FXML
    private CheckBox checkRemise;

    @FXML
    private Label labelTotal; // Label pour afficher le coût total de la facture

    private Receptionniste receptionnisteConnecte;

    @FXML
    public void initialize() {
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }

        // Charger les clients dans le ComboBox
        comboClients.getItems().setAll(receptionnisteConnecte.get_liste_clients());

        // Charger tous les services dans la liste
        listServices.setItems(FXCollections.observableArrayList(receptionnisteConnecte.getListeServices()));
        listServices.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Mettre à jour le coût total lorsque les services ou la remise sont modifiés
        listServices.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTotal());
        checkRemise.selectedProperty().addListener((observable, oldValue, newValue) -> updateTotal());
    }

    @FXML
    public void creerFacture() {
        Client client = comboClients.getSelectionModel().getSelectedItem();
        LocalDate date = datePicker.getValue();
        boolean avecRemise = checkRemise.isSelected();
        List<Service> servicesSelectionnes = new ArrayList<>(listServices.getSelectionModel().getSelectedItems());

        if (client == null || date == null || servicesSelectionnes.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        int numeroFacture = receptionnisteConnecte.getListeFactures().size() + 1;

        // Calcul du montant total des services sélectionnés
        double montantTotal = servicesSelectionnes.stream()
                .mapToDouble(Service::getCout)
                .sum();

        if (avecRemise) {
            montantTotal *= 0.9; // Appliquer une remise de 10%
        }

        Facture nouvelleFacture = new Facture(
                numeroFacture,
                montantTotal,
                java.sql.Date.valueOf(date),
                client,
                new ArrayList<>(servicesSelectionnes),
                avecRemise
        );

        receptionnisteConnecte.getListeFactures().add(nouvelleFacture);

        showAlert("Succès", "La facture a été créée avec succès !");
        closeWindow();
    }

    private void updateTotal() {
        // Récupérer les services sélectionnés
        List<Service> servicesSelectionnes = new ArrayList<>(listServices.getSelectionModel().getSelectedItems());

        // Calcul du montant total des services sélectionnés
        double montantTotal = servicesSelectionnes.stream()
                .mapToDouble(Service::getCout)
                .sum();

        // Appliquer la remise si nécessaire
        if (checkRemise.isSelected()) {
            montantTotal *= 0.9; // Appliquer une remise de 10%
        }

        // Mettre à jour l'affichage du coût total
        labelTotal.setText(String.format("%.2f", montantTotal));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) comboClients.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void annuler() {
        closeWindow();
    }
}
