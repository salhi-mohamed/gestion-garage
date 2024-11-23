package Controlleurs;

import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.Optional;
import javafx.beans.property.SimpleIntegerProperty;

public class SupprimerClientController {

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
    private TableColumn<Client, String> columnStatutFinancier;

    @FXML
    private Button buttonSupprimer;

    @FXML
    private TextField searchField;  // Champ de recherche pour filtrer par nom

    private Receptionniste receptionnisteConnecte;
    private ObservableList<Client> clients;

    public void initialize() {
        // Vérifier que le réceptionniste connecté est récupéré correctement
        if (receptionnisteConnecte == null) {
            receptionnisteConnecte = AjouterClientController.receptionnisteConnecte; // Assurez-vous que c'est bien configuré
        }

        // Si réceptionnisteConnecte est nul, afficher une erreur
        if (receptionnisteConnecte == null) {
            showAlert("Erreur", "Aucun réceptionniste connecté.");
            return;
        }

        // Charger la liste des clients associés au réceptionniste connecté
        clients = FXCollections.observableArrayList(receptionnisteConnecte.get_liste_clients());

        // Configurer les colonnes de la TableView
        columnNom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().get_nom()));
        columnPrenom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().get_prenom()));
columnTelephone.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().get_telephone()).asObject());
        columnAdresse.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().get_adresse()));
        columnStatutFinancier.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getStatutFinancier()));

        // Afficher les clients dans la TableView
        tableViewClients.setItems(clients);

        // Ajouter un écouteur d'événements pour le filtrage en temps réel
        searchField.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> filterClients());
    }

    // Méthode de filtrage par nom
    private void filterClients() {
        String filterText = searchField.getText().toLowerCase();
        ObservableList<Client> filteredClients = FXCollections.observableArrayList();

        for (Client client : receptionnisteConnecte.get_liste_clients()) {
            if (client.get_nom().toLowerCase().contains(filterText)) {
                filteredClients.add(client);
            }
        }
        tableViewClients.setItems(filteredClients);
    }

    @FXML
    private void supprimerClient() {
        // Vérifier si un client est sélectionné
        Client selectedClient = tableViewClients.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            showAlert("Erreur", "Veuillez sélectionner un client à supprimer.");
            return;
        }

        // Créer une fenêtre de confirmation avant suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce client ?");
        confirmationAlert.setContentText("Client : " + selectedClient.get_nom() + " " + selectedClient.get_prenom());

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Supprimer le client
            receptionnisteConnecte.supprimerClient(selectedClient.get_id());

            // Mettre à jour la TableView
            clients.remove(selectedClient);

            // Afficher un message de succès
            showAlert("Succès", "Client supprimé avec succès !");
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
