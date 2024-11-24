package Controlleurs;

import Modeles.Stocks.Piece_Rechange;
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

public class SupprimerPieceRechangeController {

    @FXML
    private TableView<Piece_Rechange> tableViewPieceRechanges;

    @FXML
    private TableColumn<Piece_Rechange, String> columnNom;

    @FXML
    private TableColumn<Piece_Rechange, Double> columnPrix;

    @FXML
    private TableColumn<Piece_Rechange, Integer> columnQuantite;

    @FXML
    private TableColumn<Piece_Rechange, String> columnDescription;

    @FXML
    private Button buttonSupprimer;

    @FXML
    private TextField searchField;  // Champ de recherche pour filtrer par nom

    private Receptionniste receptionnisteConnecte;
    private ObservableList<Piece_Rechange> pieceRechanges;

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

        // Charger la liste des pièces de rechange associées au réceptionniste connecté
        pieceRechanges = FXCollections.observableArrayList(receptionnisteConnecte.getListPiecesRechange());

        // Configurer les colonnes de la TableView
        columnNom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getNom()));
        columnPrix.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());
        columnQuantite.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantiteStock()).asObject());
        columnDescription.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getDescription()));

        // Afficher les pièces de rechange dans la TableView
        tableViewPieceRechanges.setItems(pieceRechanges);

        // Ajouter un écouteur d'événements pour le filtrage en temps réel
        searchField.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> filterPieceRechanges());
    }

    // Méthode de filtrage par nom
    private void filterPieceRechanges() {
        String filterText = searchField.getText().toLowerCase();
        ObservableList<Piece_Rechange> filteredPieceRechanges = FXCollections.observableArrayList();

        for (Piece_Rechange pieceRechange : receptionnisteConnecte.getListPiecesRechange()) {
            if (pieceRechange.getNom().toLowerCase().contains(filterText)) {
                filteredPieceRechanges.add(pieceRechange);
            }
        }
        tableViewPieceRechanges.setItems(filteredPieceRechanges);
    }

    @FXML
    private void supprimerPieceRechange() {
        // Vérifier si une pièce de rechange est sélectionnée
        Piece_Rechange selectedPieceRechange = tableViewPieceRechanges.getSelectionModel().getSelectedItem();
        if (selectedPieceRechange == null) {
            showAlert("Erreur", "Veuillez sélectionner une pièce de rechange à supprimer.");
            return;
        }

        // Créer une fenêtre de confirmation avant suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette pièce de rechange ?");
        confirmationAlert.setContentText("Pièce de rechange : " + selectedPieceRechange.getNom());

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Supprimer la pièce de rechange dans la base de données via le réceptionniste
            receptionnisteConnecte.supprimerPieceRechange(selectedPieceRechange.getIdPiece());

            // Supprimer la pièce de rechange de la liste observable
            pieceRechanges.remove(selectedPieceRechange);

            // Filtrer à nouveau la liste pour respecter le texte de recherche
            filterPieceRechanges();

            // Afficher un message de succès
            showAlert("Succès", "Pièce de rechange supprimée avec succès !");
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
