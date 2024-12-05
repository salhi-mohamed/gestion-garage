package Controlleurs;

import Modeles.Gestion_Service.Facture;
import Modeles.Personnes.Receptionniste;
import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
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

public class ModifierFactureController {

    @FXML
    private TableView<Facture> tableViewFactures;

    @FXML
    private TableColumn<Facture, Integer> columnId;

    @FXML
    private TableColumn<Facture, String> columnClient;

    @FXML
    private TableColumn<Facture, String> columnDate;

    @FXML
    private TableColumn<Facture, Double> columnMontant;

    @FXML
    private TableColumn<Facture, String> columnStatut;

    @FXML
    private Button buttonModifier;

    private ObservableList<Facture> factures;
    private Receptionniste receptionnisteConnecte;

    public void initialize() {
        // Récupérer le réceptionniste connecté
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            showAlert("Erreur", "Aucun réceptionniste connecté.");
            return;
        }

        // Charger la liste des factures
        factures = FXCollections.observableArrayList(receptionnisteConnecte.getListeFactures());

        // Configurer les colonnes
        columnId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumeroFacture()));
        columnClient.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getClient().get_nom()));
        columnDate.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getDateFacture().toString()));
        columnMontant.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMontantTotal()).asObject());
        //columnStatut.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().));

        // Ajouter la liste des factures à la TableView
        tableViewFactures.setItems(factures);
    }

    @FXML
    private void ouvrirFormulaireModification() {
        // Récupérer la facture sélectionnée
        Facture selectedFacture = tableViewFactures.getSelectionModel().getSelectedItem();
        if (selectedFacture == null) {
            showAlert("Erreur", "Veuillez sélectionner une facture à modifier.");
            return;
        }

        // Ouvrir le formulaire de modification
        afficherFormulaireModification(selectedFacture);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherFormulaireModification(Facture facture) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/FormulaireModificationFacture.fxml"));
            Parent root = loader.load();

            // Passer les informations de la facture au contrôleur du formulaire
            FormulaireModificationFactureController controller = loader.getController();
            controller.setFacture(facture);

            // Afficher la fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Facture");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Rafraîchir la TableView après modification
            tableViewFactures.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le formulaire de modification.");
        }
    }
}
