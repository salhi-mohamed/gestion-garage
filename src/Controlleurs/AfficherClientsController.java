package Controlleurs;

import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import Modeles.Gestion_Service.Voiture;
import Modeles.Stocks.Fourniture;
import java.io.IOException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class AfficherClientsController {

    @FXML
    private TableView<Client> tableClients;
    @FXML
    private TableColumn<Client, Integer> colID;
    @FXML
    private TableColumn<Client, String> colNom;
    @FXML
    private TableColumn<Client, String> colPrenom;
    @FXML
    private TableColumn<Client, Integer> colTelephone;
    @FXML
    private TableColumn<Client, String> colAdresse;
    @FXML
    private TableColumn<Client, Void> colActions;

    @FXML
    private ComboBox<String> comboBoxAffichage; // ComboBox pour sélectionner Voitures ou Fournitures
    @FXML
    private VBox containerTableView; // VBox pour contenir le contenu dynamique (voitures ou fournitures)

    @FXML
    private Button btnAnnuler; // Bouton Annuler

    private Receptionniste receptionniste;

    @FXML
    public void initialize() {
        receptionniste = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionniste != null) {
            initialiserColonnes();
            afficherTousLesClients(receptionniste);
            initializeComboBox();
        }
    }

    private void initialiserColonnes() {
        colID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().get_id()).asObject());
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_nom()));
        colPrenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_prenom()));
        colTelephone.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().get_telephone()).asObject());
        colAdresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_adresse()));

        // Colonne pour les actions (Modifier / Supprimer)
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button btnModifier = new Button("Modifier");
            private final Button btnSupprimer = new Button("Supprimer");
            private final HBox hbox = new HBox(10, btnModifier, btnSupprimer);

            {
                btnModifier.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                btnSupprimer.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

                btnModifier.setOnAction(event -> {
                    Client client = getTableView().getItems().get(getIndex());
                    afficherFormulaireModification(client);
                });

                btnSupprimer.setOnAction(event -> {
                    Client client = getTableView().getItems().get(getIndex());
                    supprimerClient(client.get_id());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                }
            }
        });
    }

    private void initializeComboBox() {
        ObservableList<String> options = FXCollections.observableArrayList("Afficher Voitures", "Afficher Fournitures");
        comboBoxAffichage.setItems(options);
        comboBoxAffichage.getSelectionModel().selectFirst(); // Par défaut, "Afficher Voitures"
        comboBoxAffichage.setOnAction(event -> changerAffichage());
    }

    private void afficherTousLesClients(Receptionniste receptionniste) {
        List<Client> listeClients = receptionniste.get_liste_clients();
        tableClients.getItems().clear();
        tableClients.getItems().addAll(listeClients);
    }

    @FXML
    private void onClientSelected() {
        // Lorsque l'on sélectionne un client, rendre visible le ComboBox et le bouton "Annuler"
        if (tableClients.getSelectionModel().getSelectedItem() != null) {
            comboBoxAffichage.setVisible(true);
            btnAnnuler.setVisible(true); // Rendre visible le bouton "Annuler"
        }
    }

    @FXML
    private void handleAnnuler() {
        // Cacher le ComboBox et le bouton "Annuler", et afficher à nouveau la TableView des clients
        comboBoxAffichage.setVisible(false);
        btnAnnuler.setVisible(false);
        containerTableView.getChildren().clear();
        containerTableView.getChildren().add(tableClients);
    }

    private void changerAffichage() {
        String selectedOption = comboBoxAffichage.getSelectionModel().getSelectedItem();

        // Effacer l'affichage actuel
        containerTableView.getChildren().clear();

        Client selectedClient = tableClients.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            showAlert("Erreur", "Veuillez sélectionner un client.");
            return;
        }

        if ("Afficher Voitures".equals(selectedOption)) {
            afficherVoitures(selectedClient);
        } else if ("Afficher Fournitures".equals(selectedOption)) {
            afficherFournitures(selectedClient);
        } else {
            // Revenir à la TableView montrant les clients si aucune option n'est sélectionnée
            containerTableView.getChildren().add(tableClients);
        }
    }

    private void afficherVoitures(Client client) {
        // Créer TableView pour les Voitures
        TableView<Voiture> voituresTableView = new TableView<>();

        // Colonnes pour les Voitures
        TableColumn<Voiture, String> immatriculationColumn = new TableColumn<>("Immatriculation");
        immatriculationColumn.setCellValueFactory(cellData -> cellData.getValue().immatriculationProperty());
        TableColumn<Voiture, String> marqueColumn = new TableColumn<>("Marque");
        marqueColumn.setCellValueFactory(cellData -> cellData.getValue().marqueProperty());

        TableColumn<Voiture, String> modeleColumn = new TableColumn<>("Modèle");
        modeleColumn.setCellValueFactory(cellData -> cellData.getValue().modeleProperty());

        TableColumn<Voiture, Integer> anneeColumn = new TableColumn<>("Année");
        anneeColumn.setCellValueFactory(cellData -> cellData.getValue().anneeProperty().asObject());

        // Kilométrage column - converti de Long à Integer
        TableColumn<Voiture, Integer> kilometrageColumn = new TableColumn<>("Kilométrage");
        kilometrageColumn.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty((int) cellData.getValue().kilometrageProperty().get()).asObject());

        voituresTableView.getColumns().addAll(immatriculationColumn, marqueColumn, modeleColumn, anneeColumn, kilometrageColumn);

        ObservableList<Voiture> voituresList = FXCollections.observableArrayList(client.getVoitures());
        voituresTableView.setItems(voituresList);

        containerTableView.getChildren().add(voituresTableView);
    }

    private void afficherFournitures(Client client) {
        // Créer TableView pour les Fournitures
        TableView<Fourniture> fournituresTableView = new TableView<>();

        // Colonnes pour les Fournitures
        TableColumn<Fourniture, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());

        TableColumn<Fourniture, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        TableColumn<Fourniture, Double> prixColumn = new TableColumn<>("Prix");
        prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());

        fournituresTableView.getColumns().addAll(nomColumn, descriptionColumn, prixColumn);

        ObservableList<Fourniture> fournituresList = FXCollections.observableArrayList(client.getFournituresAchetees());
        fournituresTableView.setItems(fournituresList);

        containerTableView.getChildren().add(fournituresTableView);
    }

   private void afficherFormulaireModification(Client client) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/FormulaireModificationClient.fxml"));
        Parent root = loader.load();
        
        // Obtenir le contrôleur du formulaire et définir le client
        FormulaireModificationClientController controller = loader.getController();
        controller.setClient(client);

        // Créer une nouvelle fenêtre pour le formulaire
        Stage stage = new Stage();
        stage.setTitle("Modifier Client");
        stage.setScene(new Scene(root));
        stage.setOnHiding(event -> {
            // Rafraîchir la TableView après la modification
            afficherTousLesClients(receptionniste);
        });
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        showAlert("Erreur", "Impossible d'ouvrir le formulaire de modification.");
    }
}

   private void supprimerClient(int idClient) {
    // Demander confirmation avant de supprimer le client
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmer la suppression");
    alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce client ?");
    alert.setContentText("Cette action est irréversible.");

    // Si l'utilisateur confirme la suppression
    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            // Appeler la méthode supprimerClient de la classe Receptionniste
            receptionniste.supprimerClient(idClient);

            // Rafraîchir la table des clients après suppression
            afficherTousLesClients(receptionniste);
            showAlert("Succès", "Client supprimé avec succès.");
        }
    });
}

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
