
package Controlleurs;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import Modeles.Stocks.Fourniture;
import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;

public class AjouterFournitureClientController {

    @FXML
    private TableView<Client> tableViewClients;  // TableView pour les clients
    @FXML
    private TableView<Fourniture> tableViewFournitures;  // TableView pour les fournitures
    @FXML
    private TableColumn<Client, String> colonneNomClient;  // Colonne pour afficher le nom du client
    @FXML
    private TableColumn<Fourniture, String> colonneNomFourniture;  // Colonne pour afficher le nom de la fourniture
    @FXML
    private TableColumn<Fourniture, Double> colonnePrixFourniture;  // Colonne pour afficher le prix des fournitures
    @FXML
    private Button boutonAjouterFourniture;  // Bouton pour ajouter la fourniture sélectionnée

    private Receptionniste receptionniste;
    private Client clientSelectionne;
    private Fourniture fournitureSelectionnee;

    @FXML
    public void initialize() {
        // Récupérer le réceptionniste connecté depuis le MenuPrincipaleController
        receptionniste = MenuPrincipaleController.receptionnisteConnecte;
        if (receptionniste != null) {
            System.out.println("Réceptionniste connecté : " + receptionniste.get_nom());
        }

        // Lier les clients du réceptionniste à la table
        ObservableList<Client> clients = FXCollections.observableArrayList(receptionniste.get_liste_clients());
        colonneNomClient.setCellValueFactory(cellData -> cellData.getValue(). nomProperty());
        tableViewClients.setItems(clients);

        // Lier les fournitures du réceptionniste à la table
        ObservableList<Fourniture> fournitures = FXCollections.observableArrayList(receptionniste.getListeFournitures());
        colonneNomFourniture.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colonnePrixFourniture.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());
        tableViewFournitures.setItems(fournitures);

        // Sélectionner un client dans le TableView
        tableViewClients.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Client> observable, Client oldValue, Client newValue) -> {
            clientSelectionne = newValue;
            System.out.println("Client sélectionné : " + (clientSelectionne != null ? clientSelectionne.get_nom() : "Aucun"));
        });

        // Sélectionner une fourniture dans le TableView
        tableViewFournitures.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Fourniture> observable, Fourniture oldValue, Fourniture newValue) -> {
            fournitureSelectionnee = newValue;
            System.out.println("Fourniture sélectionnée : " + (fournitureSelectionnee != null ? fournitureSelectionnee.getNom() : "Aucune"));
        });
    }

    // Méthode appelée lors de l'ajout d'une fourniture au client sélectionné
    @FXML
    public void ajouterFourniture(MouseEvent event) {
        if (clientSelectionne != null && fournitureSelectionnee != null) {
            System.out.println("Ajout de la fourniture " + fournitureSelectionnee.getNom() + " au client " + clientSelectionne.get_nom());

            // Logique pour ajouter la fourniture au client
            // Par exemple, mettre à jour le stock du client, si nécessaire
            // clientSelectionne.ajouterFourniture(fournitureSelectionnee);

            // Afficher une confirmation ou autre action
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Fourniture ajoutée");
            alert.setContentText("La fourniture " + fournitureSelectionnee.getNom() + " a été ajoutée au client " + clientSelectionne.get_nom() + ".");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("Sélection invalide");
            alert.setContentText("Veuillez sélectionner un client et une fourniture.");
            alert.showAndWait();
        }
    }
}
