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

public class AjouterFournitureClientController {

    private Receptionniste receptionnisteConnecte; // Référence au réceptionniste connecté

    @FXML
    private ComboBox<Client> clientComboBox;
    
    @FXML
    private TableView<Fourniture> fournitureTableView;

    @FXML
    private TableColumn<Fourniture, String> nomColumn;
    
    @FXML
    private TableColumn<Fourniture, String> descriptionColumn;

    @FXML
    private TableColumn<Fourniture, Double> prixColumn;

    @FXML
    private TableColumn<Fourniture, Integer> quantiteColumn;  // Nouvelle colonne pour la quantité

    @FXML
    public void initialize() {
        // Récupérer le réceptionniste connecté depuis MenuPrincipaleController
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }

        // Remplir le ComboBox avec la liste des clients
        clientComboBox.getItems().setAll(receptionnisteConnecte.get_liste_clients());

        // Personnaliser l'affichage du ComboBox pour afficher nom et prénom
        clientComboBox.setCellFactory(param -> new javafx.scene.control.ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setText(null);
                } else {
                    setText(client.get_prenom() + " " + client.get_nom()); // Affiche prénom et nom
                }
            }
        });

        // Afficher la sélection actuelle dans le ComboBox
        clientComboBox.setButtonCell(new javafx.scene.control.ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setText(null);
                } else {
                    setText(client.get_prenom() + " " + client.get_nom()); // Affiche prénom et nom
                }
            }
        });

        // Remplir le TableView avec la liste des fournitures
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantiteStock"));  // Nouvelle colonne pour la quantité

        fournitureTableView.getItems().setAll(receptionnisteConnecte.getListeFournitures());
    }

    @FXML
    public void ajouterFournitureClient() {
        Client selectedClient = clientComboBox.getSelectionModel().getSelectedItem();
        Fourniture selectedFourniture = fournitureTableView.getSelectionModel().getSelectedItem();

        if (selectedClient == null || selectedFourniture == null) {
            showAlert("Erreur", "Veuillez sélectionner un client et une fourniture.");
            return;
        }

        // Vérifier si la quantité en stock est suffisante
        int quantiteDispo = selectedFourniture.getQuantiteStock();
        if (quantiteDispo <= 0) {
            showAlert("Erreur", "La fourniture sélectionnée n'est plus en stock.");
            return;
        }

        // Appeler la méthode pour ajouter la fourniture au client
        try {
            receptionnisteConnecte.ajouter_fourniture_client(selectedFourniture.getIdFourniture(), selectedClient.get_id());

            // Réduire la quantité en stock après ajout
            selectedFourniture.setQuantiteStock(quantiteDispo - 1);

            // Mettre à jour le TableView
            fournitureTableView.refresh(); // Rafraîchir le tableau pour afficher la nouvelle quantité

            showAlert("Succès", "La fourniture a été ajoutée au client.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout de la fourniture au client.");
            e.printStackTrace();
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
