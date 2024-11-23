package Controlleurs;

import Modeles.Stocks.Fourniture;
import Modeles.Personnes.Receptionniste;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.Optional;
import javafx.beans.property.SimpleDoubleProperty;

public class SupprimerFournitureController {

    @FXML
    private TableView<Fourniture> tableViewFournitures;

    @FXML
    private TableColumn<Fourniture, String> columnNom;

    @FXML
    private TableColumn<Fourniture, Double> columnPrix;

    @FXML
    private TableColumn<Fourniture, Integer> columnQuantite;

    @FXML
    private TableColumn<Fourniture, String> columnDescription;

    @FXML
    private Button buttonSupprimer;

    @FXML
    private TextField searchField;  // Champ de recherche pour filtrer par nom

    private Receptionniste receptionnisteConnecte;
    private ObservableList<Fourniture> fournitures;

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

    // Charger la liste des fournitures associées au réceptionniste connecté
    fournitures = FXCollections.observableArrayList(receptionnisteConnecte.getListeFournitures());

    // Configurer les colonnes de la TableView
    columnNom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getNom()));
    columnPrix.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());
    columnQuantite.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantiteStock()).asObject());
    columnDescription.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getDescription()));

    // Afficher les fournitures dans la TableView
    tableViewFournitures.setItems(fournitures);

    // Ajouter un écouteur d'événements pour le filtrage en temps réel
    searchField.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> filterFournitures());
}

    // Méthode de filtrage par nom
    private void filterFournitures() {
        String filterText = searchField.getText().toLowerCase();
        ObservableList<Fourniture> filteredFournitures = FXCollections.observableArrayList();

        for (Fourniture fourniture : receptionnisteConnecte.getListeFournitures()) {
            if (fourniture.getNom().toLowerCase().contains(filterText)) {
                filteredFournitures.add(fourniture);
            }
        }
        tableViewFournitures.setItems(filteredFournitures);
    }

    @FXML
    private void supprimerFourniture() {
        // Vérifier si une fourniture est sélectionnée
        Fourniture selectedFourniture = tableViewFournitures.getSelectionModel().getSelectedItem();
        if (selectedFourniture == null) {
            showAlert("Erreur", "Veuillez sélectionner une fourniture à supprimer.");
            return;
        }

        // Créer une fenêtre de confirmation avant suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette fourniture ?");
        confirmationAlert.setContentText("Fourniture : " + selectedFourniture.getNom());

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Supprimer la fourniture dans la base de données via le réceptionniste
            receptionnisteConnecte.supprimerFourniture(selectedFourniture.getIdFourniture());

            // Supprimer la fourniture de la liste observable
            fournitures.remove(selectedFourniture);

            // Filtrer à nouveau la liste pour respecter le texte de recherche
            filterFournitures();

            // Afficher un message de succès
            showAlert("Succès", "Fourniture supprimée avec succès !");
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
