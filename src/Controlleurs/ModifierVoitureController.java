/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlleurs;

import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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

/**
 *
 * @author LENOVO
 */
public class ModifierVoitureController {

    @FXML
    private TableView<Voiture> tableViewVoitures;

    @FXML
    private TableColumn<Voiture, String> columnMarque;

    @FXML
    private TableColumn<Voiture, String> columnModele;
    @FXML
    
private TableColumn<Voiture, String> columnImmatriculation;


    @FXML

private TableColumn<Voiture, Long> columnKilometrage;

    @FXML
    private Button buttonModifier;

    private ObservableList<Voiture> voitures;
    private Receptionniste receptionnisteConnecte;

  public void initialize() {
    // Récupérer le réceptionniste connecté
    receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte; // Utilisation du receptionniste du MenuPrincipaleController

    if (receptionnisteConnecte == null) {
        showAlert("Erreur", "Aucun réceptionniste connecté.");
        return;
    }

    // Charger la liste des clients
    voitures = FXCollections.observableArrayList(receptionnisteConnecte.getListeVoitures());

    // Configurer les colonnes
   columnMarque.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getMarque()));
    columnModele.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getModele()));
columnKilometrage.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getKilometrage()).asObject());
    columnImmatriculation.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getImmatriculation()));


    // Ajouter la liste des clients à la TableView
    tableViewVoitures.setItems(voitures);
}


    @FXML
    private void ouvrirFormulaireModification() {
        // Récupérer le client sélectionné
       Voiture selectedVoiture = tableViewVoitures.getSelectionModel().getSelectedItem();
        if (selectedVoiture == null) {
            showAlert("Erreur", "Veuillez sélectionner un client à modifier.");
            return;
        }

        // Ouvrir le formulaire de modification (via une nouvelle scène ou une fenêtre modale)
        afficherFormulaireModification(selectedVoiture);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherFormulaireModification(Voiture voiture) {
        // Charger la nouvelle scène ou afficher une boîte de dialogue pour la modification
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/FormulaireModificationVoiture.fxml"));
            Parent root = loader.load();

            // Passer les informations du client au contrôleur du formulaire
            FormulaireModificationVoitureController controller = loader.getController();
            controller.setVoiture(voiture); // Méthode à implémenter dans le contrôleur

            // Afficher la fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Client");
            stage.initModality(Modality.APPLICATION_MODAL); // Bloque la fenêtre principale jusqu'à la fermeture
            stage.showAndWait();

            // Rafraîchir la TableView après modification
            tableViewVoitures.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le formulaire de modification.");
        }
    }
}

