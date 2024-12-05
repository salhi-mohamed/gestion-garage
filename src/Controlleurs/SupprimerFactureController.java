package Controlleurs;

import Modeles.Gestion_Service.Facture;
import Modeles.Personnes.Receptionniste;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.Optional;

public class SupprimerFactureController {

    @FXML
    private TableView<Facture> tableViewFactures;

    @FXML
    private TableColumn<Facture, Integer> columnNumero;

    @FXML
    private TableColumn<Facture, String> columnClient;

    @FXML
    private TableColumn<Facture, String> columnDate;

    @FXML
    private TableColumn<Facture, Double> columnMontant;

    @FXML
    private Button buttonSupprimer;

    @FXML
    private TextField searchField; // Champ de recherche par nom de client

    private Receptionniste receptionnisteConnecte;
    private ObservableList<Facture> factures;

    @FXML
    public void initialize() {
        // Vérification du réceptionniste connecté
        if (receptionnisteConnecte == null) {
            receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;
        }

        if (receptionnisteConnecte == null) {
            showAlert("Erreur", "Aucun réceptionniste connecté.");
            return;
        }

        // Charger les factures associées au réceptionniste
        factures = FXCollections.observableArrayList(receptionnisteConnecte.getListeFactures());

        // Configurer les colonnes
        columnNumero.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumeroFacture()).asObject());
        columnClient.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getClient().get_nom()));
        columnDate.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getDateFacture().toString()));
        columnMontant.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMontantTotal()).asObject());

        // Remplir la TableView avec les données
        tableViewFactures.setItems(factures);

        // Ajouter un filtre dynamique sur le champ de recherche
        searchField.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> filterFactures());
    }

    // Filtrer les factures par nom de client
    private void filterFactures() {
        String filterText = searchField.getText().toLowerCase();
        ObservableList<Facture> filteredFactures = FXCollections.observableArrayList();

        for (Facture facture : receptionnisteConnecte.getListeFactures()) {
            if (facture.getClient().get_nom().toLowerCase().contains(filterText)) {
                filteredFactures.add(facture);
            }
        }
        tableViewFactures.setItems(filteredFactures);
    }

    @FXML
    private void supprimerFacture() {
        // Récupérer la facture sélectionnée
        Facture selectedFacture = tableViewFactures.getSelectionModel().getSelectedItem();
        if (selectedFacture == null) {
            showAlert("Erreur", "Veuillez sélectionner une facture à supprimer.");
            return;
        }

        // Confirmation de la suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette facture ?");
        confirmationAlert.setContentText("Facture N°" + selectedFacture.getNumeroFacture());

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Supprimer la facture
            receptionnisteConnecte.getListeFactures().remove(selectedFacture);
            factures.remove(selectedFacture);
            showAlert("Succès", "La facture a été supprimée avec succès.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
