package Controlleurs;

import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import Modeles.Stocks.Fourniture;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SupprimerFournitureClientController {

    private Receptionniste receptionnisteConnecte; // Référence au réceptionniste connecté

    @FXML
    private ComboBox<Client> comboBoxClients;

    @FXML
    private TableView<Fourniture> tableViewFournitures;

    @FXML
    private TableColumn<Fourniture, String> colNomFourniture;

    @FXML
    private TableColumn<Fourniture, String> colDescriptionFourniture;

    @FXML
    private TableColumn<Fourniture, Double> colPrixFourniture;

    @FXML
    private TableColumn<Fourniture, Integer> colQuantiteFourniture;

    @FXML
    public void initialize() {
        // Récupérer le réceptionniste connecté depuis MenuPrincipaleController
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }

        // Remplir le ComboBox avec la liste des clients
        comboBoxClients.getItems().setAll(receptionnisteConnecte.get_liste_clients());

        // Vérifier si la liste des clients est vide
        if (receptionnisteConnecte.get_liste_clients().isEmpty()) {
            showAlert("Aucun client", "Aucun client n'est disponible.");
        }

        // Personnaliser l'affichage du ComboBox pour afficher nom et prénom
        comboBoxClients.setCellFactory(param -> new javafx.scene.control.ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setText(null);
                } else {
                    setText(client.get_prenom() + " " + client.get_nom());
                }
            }
        });

        // Afficher la sélection actuelle dans le ComboBox
        comboBoxClients.setButtonCell(new javafx.scene.control.ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setText(null);
                } else {
                    setText(client.get_prenom() + " " + client.get_nom());
                }
            }
        });

        // Configurer les colonnes de la TableView pour afficher les informations des fournitures
        colNomFourniture.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDescriptionFourniture.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrixFourniture.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colQuantiteFourniture.setCellValueFactory(new PropertyValueFactory<>("quantiteStock"));
    }

    @FXML
    public void onClientSelectionChange() {
        Client selectedClient = comboBoxClients.getSelectionModel().getSelectedItem();

        if (selectedClient != null) {
            // Remplir la TableView avec les fournitures du client sélectionné
            tableViewFournitures.getItems().setAll(selectedClient.getFournituresAchetees()); // Assurez-vous que cette méthode existe dans Client
        }
    }

    @FXML
   
public void supprimerFourniture() {
    Client selectedClient = comboBoxClients.getSelectionModel().getSelectedItem();
    Fourniture selectedFourniture = tableViewFournitures.getSelectionModel().getSelectedItem();

    if (selectedClient == null || selectedFourniture == null) {
        showAlert("Erreur", "Veuillez sélectionner un client et une fourniture à supprimer.");
        return;
    }

    // Appeler la méthode de suppression dans Receptionniste
    try {
        receptionnisteConnecte.supprimer_fourniture_de_client(selectedClient.get_id(), selectedFourniture.getIdFourniture());
        showAlert("Succès", "La fourniture a été supprimée.");
        // Rafraîchir la table des fournitures
        onClientSelectionChange();
    } catch (Exception e) {
        showAlert("Erreur", "Une erreur inconnue est survenue.");
        e.printStackTrace();
    }
}


    @FXML
    public void fermerFormulaire() {
        // Action pour fermer ou quitter la fenêtre
        Stage stage = (Stage) comboBoxClients.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
