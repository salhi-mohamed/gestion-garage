package Controlleurs;

import Modeles.Gestion_Service.Rendez_vous;
import Modeles.Personnes.Receptionniste;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.Optional;

public class SupprimerRendezVousController {

    @FXML
    private TableView<Rendez_vous> tableViewRendezVous;

    @FXML
    private TableColumn<Rendez_vous, String> columnClient;

    @FXML
    private TableColumn<Rendez_vous, String> columnVoiture;

    @FXML
    private TableColumn<Rendez_vous, String> columnDate;



    @FXML
    private TableColumn<Rendez_vous, String> columnStatut;

    @FXML
    private Button buttonSupprimer;

    @FXML
    private TextField searchField;  // Champ de recherche pour filtrer par date

    private Receptionniste receptionnisteConnecte;
    private ObservableList<Rendez_vous> rendezVousList;

    public void initialize() {
        // Vérifier que le réceptionniste connecté est récupéré correctement
        if (receptionnisteConnecte == null) {
            receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte; // Utilisation du receptionniste du MenuPrincipaleController
        }

        // Si réceptionnisteConnecte est nul, afficher une erreur
        if (receptionnisteConnecte == null) {
            showAlert("Erreur", "Aucun réceptionniste connecté.");
            return;
        }

        // Charger la liste des rendez-vous associés au réceptionniste connecté
        rendezVousList = FXCollections.observableArrayList(receptionnisteConnecte.getListeRendezVous());

        // Configurer les colonnes de la TableView
        columnClient.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getClient().get_nom()));
        columnVoiture.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getVoiture().getMarque() + " " + cellData.getValue().getVoiture().getModele()));
        columnDate.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getDate_rendez_vous().toString()));
        columnStatut.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getStatut().toString()));

        // Afficher les rendez-vous dans la TableView
        tableViewRendezVous.setItems(rendezVousList);

        // Ajouter un écouteur d'événements pour le filtrage en temps réel
        searchField.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> filterRendezVous());
    }

    // Méthode de filtrage par date
    private void filterRendezVous() {
        String filterText = searchField.getText().toLowerCase();
        ObservableList<Rendez_vous> filteredRendezVous = FXCollections.observableArrayList();

        for (Rendez_vous rendezVous : receptionnisteConnecte.getListeRendezVous()) {
            if (rendezVous.getDate_rendez_vous().toString().toLowerCase().contains(filterText)) {
                filteredRendezVous.add(rendezVous);
            }
        }
        tableViewRendezVous.setItems(filteredRendezVous);
    }

    @FXML
    private void supprimerRendezVous() {
        // Vérifier si un rendez-vous est sélectionné
        Rendez_vous selectedRendezVous = tableViewRendezVous.getSelectionModel().getSelectedItem();
        if (selectedRendezVous == null) {
            showAlert("Erreur", "Veuillez sélectionner un rendez-vous à supprimer.");
            return;
        }

        // Créer une fenêtre de confirmation avant suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce rendez-vous ?");
        confirmationAlert.setContentText("Rendez-vous pour " + selectedRendezVous.getClient().get_nom() + " avec " + selectedRendezVous.getVoiture().getMarque());

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Supprimer le rendez-vous dans la base de données via le réceptionniste
            receptionnisteConnecte.supprimerRendezVous(selectedRendezVous.getId_rendez_vous());

            // Supprimer le rendez-vous de la liste observable
            rendezVousList.remove(selectedRendezVous);

            // Filtrer à nouveau la liste pour respecter le texte de recherche
            filterRendezVous();

            // Afficher un message de succès
            showAlert("Succès", "Rendez-vous supprimé avec succès !");
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
