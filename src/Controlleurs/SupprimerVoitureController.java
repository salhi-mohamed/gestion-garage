package Controlleurs;

import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Receptionniste;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyEvent;
import java.util.Optional;

public class SupprimerVoitureController {

    @FXML
    private TableView<Voiture> tableViewVoitures;

    @FXML
    private TableColumn<Voiture, String> columnImmatriculation;

    @FXML
    private TableColumn<Voiture, String> columnMarque;

    @FXML
    private TableColumn<Voiture, String> columnModele;

    @FXML
    private TableColumn<Voiture, Integer> columnAnnee;

    @FXML
    private TableColumn<Voiture, Long> columnKilometrage;  // Using Long type for kilometrage

    @FXML
    private Button buttonSupprimer;

    @FXML
    private TextField searchField;  // Champ de recherche pour filtrer par immatriculation

    private Receptionniste receptionnisteConnecte;
    private ObservableList<Voiture> voitures;

    public void initialize() {
        // Vérifier que le réceptionniste connecté est récupéré correctement
        if (receptionnisteConnecte == null) {
            receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte; // Utilisation du réceptionniste du MenuPrincipaleController
        }

        // Si réceptionnisteConnecte est nul, afficher une erreur
        if (receptionnisteConnecte == null) {
            showAlert("Erreur", "Aucun réceptionniste connecté.");
            return;
        }

        // Charger la liste des voitures associées au réceptionniste connecté
        voitures = FXCollections.observableArrayList(receptionnisteConnecte.get_liste_voitures());

        // Configurer les colonnes de la TableView
        columnImmatriculation.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getImmatriculation()));
        columnMarque.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getMarque()));
        columnModele.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getModele()));
        columnAnnee.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAnnee()).asObject());
        columnKilometrage.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getKilometrage()).asObject());  // Using SimpleLongProperty for kilometrage

        // Afficher les voitures dans la TableView
        tableViewVoitures.setItems(voitures);

        // Ajouter un écouteur d'événements pour le filtrage en temps réel
        searchField.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> filterVoitures());
    }

    // Méthode de filtrage par immatriculation
    private void filterVoitures() {
        String filterText = searchField.getText().toLowerCase();
        ObservableList<Voiture> filteredVoitures = FXCollections.observableArrayList();

        for (Voiture voiture : receptionnisteConnecte.get_liste_voitures()) {
            if (voiture.getImmatriculation().toLowerCase().contains(filterText)) {
                filteredVoitures.add(voiture);
            }
        }
        tableViewVoitures.setItems(filteredVoitures);
    }

    @FXML
    private void supprimerVoiture() {
        // Vérifier si une voiture est sélectionnée
        Voiture selectedVoiture = tableViewVoitures.getSelectionModel().getSelectedItem();
        if (selectedVoiture == null) {
            showAlert("Erreur", "Veuillez sélectionner une voiture à supprimer.");
            return;
        }

        // Créer une fenêtre de confirmation avant suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette voiture ?");
        confirmationAlert.setContentText("Voiture : " + selectedVoiture.getImmatriculation());

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Supprimer la voiture dans la base de données via le réceptionniste
            receptionnisteConnecte.supprimerVoiture(selectedVoiture.getImmatriculation());

            // Supprimer la voiture de la liste observable
            voitures.remove(selectedVoiture);

            // Filtrer à nouveau la liste pour respecter le texte de recherche
            filterVoitures();

            // Afficher un message de succès
            showAlert("Succès", "Voiture supprimée avec succès !");
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
