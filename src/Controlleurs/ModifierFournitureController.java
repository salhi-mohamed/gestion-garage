package Controlleurs;

import Modeles.Stocks.Fourniture;
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

public class ModifierFournitureController {

    @FXML
    private TableView<Fourniture> tableViewFournitures;

    @FXML
    private TableColumn<Fourniture, String> columnNom;

    @FXML
    private TableColumn<Fourniture, String> columnCategorie;

    @FXML
    private TableColumn<Fourniture, Double> columnPrix;

    @FXML
    private TableColumn<Fourniture, Integer> columnQuantite;

    @FXML
    private Button buttonModifier;

    private ObservableList<Fourniture> fournitures;
    private Receptionniste receptionnisteConnecte;

    public void initialize() {
    // Récupérer le réceptionniste connecté
    receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte; // Utilisation du receptionniste du MenuPrincipaleController

    if (receptionnisteConnecte == null) {
        showAlert("Erreur", "Aucun réceptionniste connecté.");
        return;
    }

    // Charger la liste des fournitures
    fournitures = FXCollections.observableArrayList(receptionnisteConnecte.getListeFournitures());

    // Configurer les colonnes
    columnNom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getNom()));
    columnCategorie.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getDescription()));
    columnPrix.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());
    columnQuantite.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantiteStock()).asObject());

    // Ajouter la liste des fournitures à la TableView
    tableViewFournitures.setItems(fournitures);
}


    @FXML
    private void ouvrirFormulaireModification() {
        // Récupérer la fourniture sélectionnée
        Fourniture selectedFourniture = tableViewFournitures.getSelectionModel().getSelectedItem();
        if (selectedFourniture == null) {
            showAlert("Erreur", "Veuillez sélectionner une fourniture à modifier.");
            return;
        }

        // Ouvrir le formulaire de modification (via une nouvelle scène ou une fenêtre modale)
        afficherFormulaireModification(selectedFourniture);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherFormulaireModification(Fourniture fourniture) {
        // Charger la nouvelle scène ou afficher une boîte de dialogue pour la modification
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/FormulaireModificationFourniture.fxml"));
            Parent root = loader.load();

            // Passer les informations de la fourniture au contrôleur du formulaire
            FormulaireModificationFournitureController controller = loader.getController();
            controller.setFourniture(fourniture); // Méthode à implémenter dans le contrôleur

            // Afficher la fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Fourniture");
            stage.initModality(Modality.APPLICATION_MODAL); // Bloque la fenêtre principale jusqu'à la fermeture
            stage.showAndWait();

            // Rafraîchir la TableView après modification
            tableViewFournitures.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le formulaire de modification.");
        }
    }
}
