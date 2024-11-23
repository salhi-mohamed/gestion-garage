/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlleurs;

import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import java.io.IOException;
import javafx.beans.binding.Bindings;
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

/**
 *
 * @author LENOVO
 */
public class ModifierClientController {

    @FXML
    private TableView<Client> tableViewClients;

    @FXML
    private TableColumn<Client, String> columnNom;

    @FXML
    private TableColumn<Client, String> columnPrenom;

    @FXML
    private TableColumn<Client, Integer> columnTelephone;

    @FXML
    private TableColumn<Client, String> columnAdresse;

    @FXML
    private Button buttonModifier;

    private ObservableList<Client> clients;
    private Receptionniste receptionnisteConnecte;

  public void initialize() {
    // Récupérer le réceptionniste connecté
    receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte; // Utilisation du receptionniste du MenuPrincipaleController

    if (receptionnisteConnecte == null) {
        showAlert("Erreur", "Aucun réceptionniste connecté.");
        return;
    }

    // Charger la liste des clients
    clients = FXCollections.observableArrayList(receptionnisteConnecte.get_liste_clients());

    // Configurer les colonnes
    columnNom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().get_nom()));
    columnPrenom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().get_prenom()));
    columnTelephone.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().get_telephone()).asObject());
    columnAdresse.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().get_adresse()));

    // Ajouter la liste des clients à la TableView
    tableViewClients.setItems(clients);
}


    @FXML
    private void ouvrirFormulaireModification() {
        // Récupérer le client sélectionné
        Client selectedClient = tableViewClients.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            showAlert("Erreur", "Veuillez sélectionner un client à modifier.");
            return;
        }

        // Ouvrir le formulaire de modification (via une nouvelle scène ou une fenêtre modale)
        afficherFormulaireModification(selectedClient);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherFormulaireModification(Client client) {
        // Charger la nouvelle scène ou afficher une boîte de dialogue pour la modification
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/FormulaireModificationClient.fxml"));
            Parent root = loader.load();

            // Passer les informations du client au contrôleur du formulaire
            FormulaireModificationClientController controller = loader.getController();
            controller.setClient(client); // Méthode à implémenter dans le contrôleur

            // Afficher la fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Client");
            stage.initModality(Modality.APPLICATION_MODAL); // Bloque la fenêtre principale jusqu'à la fermeture
            stage.showAndWait();

            // Rafraîchir la TableView après modification
            tableViewClients.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le formulaire de modification.");
        }
    }
}

