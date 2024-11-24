package Controlleurs;

import Modeles.Stocks.Piece_Rechange;
import Modeles.Personnes.Receptionniste;
import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifierPieceRechangeController {

    @FXML
    private TableView<Piece_Rechange> tableViewPieces;

    @FXML
    private TableColumn<Piece_Rechange, String> columnNom;

    @FXML
    private TableColumn<Piece_Rechange, String> columnCategorie;

    @FXML
    private TableColumn<Piece_Rechange, Double> columnPrix;

    @FXML
    private TableColumn<Piece_Rechange, Integer> columnQuantite;

    @FXML
    private Button buttonModifier;

    private ObservableList<Piece_Rechange> pieces;
    private Receptionniste receptionnisteConnecte;

    public void initialize() {
        // Récupérer le réceptionniste connecté
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            showAlert("Erreur", "Aucun réceptionniste connecté.");
            return;
        }

        // Charger la liste des pièces de rechange
        pieces = FXCollections.observableArrayList(receptionnisteConnecte.getListPiecesRechange());

        // Configurer les colonnes
        columnNom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getNom()));
        columnCategorie.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getDescription()));
        columnPrix.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());
        columnQuantite.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantiteStock()).asObject());

        // Ajouter la liste des pièces à la TableView
        tableViewPieces.setItems(pieces);
    }

    @FXML
    private void ouvrirFormulaireModification() {
        // Récupérer la pièce sélectionnée
        Piece_Rechange selectedPiece = tableViewPieces.getSelectionModel().getSelectedItem();
        if (selectedPiece == null) {
            showAlert("Erreur", "Veuillez sélectionner une pièce à modifier.");
            return;
        }

        // Ouvrir le formulaire de modification
        afficherFormulaireModification(selectedPiece);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherFormulaireModification(Piece_Rechange piece) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/FormulaireModificationPieceRechange.fxml"));
            Parent root = loader.load();

            // Passer les informations de la pièce au contrôleur du formulaire
            FormulaireModificationPieceRechangeController controller = loader.getController();
            controller.setPieceRechange(piece); // Méthode à implémenter dans le contrôleur

            // Afficher la fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Pièce de Rechange");
            stage.initModality(Modality.APPLICATION_MODAL); // Bloque la fenêtre principale jusqu'à la fermeture
            stage.showAndWait();

            // Rafraîchir la TableView après modification
            tableViewPieces.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le formulaire de modification.");
        }
    }
}
