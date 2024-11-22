package Controlleurs;

import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

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
    private TableColumn<Client, String> colTelephone;
    @FXML
    private TableColumn<Client, String> colAdresse;
    @FXML
    private TableColumn<Client, String> colActions;

    @FXML
    public void initialize() {
        Receptionniste receptionniste = AjouterClientController.receptionnisteConnecte;

        if (receptionniste != null) {
            initialiserColonnes();
            afficherClients(receptionniste);

            // Double-click handler to display client details
            tableClients.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Client clientSelectionne = tableClients.getSelectionModel().getSelectedItem();
                    if (clientSelectionne != null) {
                        afficherVoituresClient(clientSelectionne);
                    }
                }
            });

            // Charger les fichiers CSS
            Scene scene = tableClients.getScene();
            if (scene != null) {
                scene.getStylesheets().add(getClass().getResource("/Vues/styles.css").toExternalForm());
                scene.getStylesheets().add(getClass().getResource("/Vues/actions-buttons.css").toExternalForm());
            }
        }
    }

    private void initialiserColonnes() {
        colID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().get_id()).asObject());
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_nom()));
        colPrenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_prenom()));
        colTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().get_telephone())));
        colAdresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_adresse()));

        // Configurer la colonne d'actions avec les boutons Modifier et Supprimer
        colActions.setCellFactory(column -> new TableCell<Client, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionBox = new HBox(10); // Espacement entre les boutons
                    Button btnModifier = new Button("Modifier");
                    Button btnSupprimer = new Button("Supprimer");

                    // Appliquer les classes CSS
                    btnModifier.getStyleClass().add("modifier");
                    btnSupprimer.getStyleClass().add("supprimer");

                    // Actions des boutons
                    btnModifier.setOnAction(e -> {
                        Client client = getTableView().getItems().get(getIndex());
                        modifierClient(client);
                    });

                    btnSupprimer.setOnAction(e -> {
                        Client client = getTableView().getItems().get(getIndex());
                        supprimerClient(client);
                    });

                    actionBox.getChildren().addAll(btnModifier, btnSupprimer);
                    setGraphic(actionBox);
                }
            }
        });
    }

    private void afficherClients(Receptionniste receptionniste) {
        tableClients.getItems().clear();
        tableClients.getItems().addAll(receptionniste.get_liste_clients());
    }

    private void afficherVoituresClient(Client client) {
        // Gérer l'affichage des voitures associées au client
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Voitures du client");
        alert.setHeaderText("Liste des voitures associées à " + client.get_nom() + " " + client.get_prenom());
        alert.setContentText("Cette fonctionnalité est encore à implémenter.");
        alert.showAndWait();
    }

    private void modifierClient(Client client) {
    // Créer la fenêtre de modification
    Stage modificationStage = new Stage();
    modificationStage.setTitle("Modifier le client");

    // Créer le GridPane pour la fenêtre de modification (plus précis que VBox)
    GridPane grid = new GridPane();
    grid.setVgap(10); // Espacement vertical
    grid.setHgap(10); // Espacement horizontal
    grid.setStyle("-fx-padding: 20px;");

    // Créer les labels et champs de texte pour les détails du client
    Label lblNom = new Label("Nom:");
    TextField txtNom = new TextField(client.get_nom());
    txtNom.setPromptText("Nom du client");

    Label lblPrenom = new Label("Prénom:");
    TextField txtPrenom = new TextField(client.get_prenom());
    txtPrenom.setPromptText("Prénom du client");

    Label lblTelephone = new Label("Téléphone:");
    TextField txtTelephone = new TextField(String.valueOf(client.get_telephone()));
    txtTelephone.setPromptText("Téléphone du client");

    // Ajouter un écouteur pour ne permettre que les chiffres dans le champ Téléphone
    txtTelephone.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
            txtTelephone.setText(oldValue);
        }
    });

    Label lblAdresse = new Label("Adresse:");
    TextField txtAdresse = new TextField(client.get_adresse());
    txtAdresse.setPromptText("Adresse du client");

    // Créer les boutons
    Button btnSave = new Button("Sauvegarder");
    Button btnCancel = new Button("Annuler");

    // Gérer le clic sur le bouton Sauvegarder
    btnSave.setOnAction(e -> {
        try {
            int telephone = Integer.parseInt(txtTelephone.getText());
            client.set_nom(txtNom.getText());
            client.set_prenom(txtPrenom.getText());
            client.set_telephone(telephone);
            client.set_adresse(txtAdresse.getText());
            tableClients.refresh();
            modificationStage.close();
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Téléphone invalide");
            alert.setContentText("Veuillez entrer un numéro de téléphone valide.");
            alert.showAndWait();
        }
    });

    // Gérer le clic sur le bouton Annuler
    btnCancel.setOnAction(e -> modificationStage.close());

    // Ajouter les éléments au GridPane (organisé en lignes et colonnes)
    grid.add(lblNom, 0, 0);
    grid.add(txtNom, 1, 0);
    grid.add(lblPrenom, 0, 1);
    grid.add(txtPrenom, 1, 1);
    grid.add(lblTelephone, 0, 2);
    grid.add(txtTelephone, 1, 2);
    grid.add(lblAdresse, 0, 3);
    grid.add(txtAdresse, 1, 3);

    // Créer une HBox pour les boutons et les centrer
    HBox hboxButtons = new HBox(20); // Espacement entre les boutons
    hboxButtons.setStyle("-fx-alignment: center;"); // Centrer les boutons dans l'HBox
    hboxButtons.getChildren().addAll(btnSave, btnCancel);

    // Ajouter l'HBox de boutons au GridPane (dans la dernière ligne, à la colonne 0 et 1)
    grid.add(hboxButtons, 0, 4, 2, 1); // Le '2' ici indique que la cellule s'étend sur deux colonnes

    // Appliquer le fichier CSS
    Scene scene = new Scene(grid);
    scene.getStylesheets().add(getClass().getResource("/Vues/styles.css").toExternalForm());

    // Définir la taille de la fenêtre (élargir la fenêtre)
    modificationStage.setScene(scene);
    modificationStage.setWidth(600);  // Modifier la largeur
    modificationStage.setHeight(400); // Hauteur par défaut, ou vous pouvez la changer si nécessaire

    // Afficher la fenêtre
    modificationStage.show();
}


    private void supprimerClient(Client client) {
        Receptionniste receptionniste = AjouterClientController.receptionnisteConnecte;
        if (receptionniste != null) {
            receptionniste.get_liste_clients().remove(client);
            tableClients.getItems().remove(client);
        }
    }
}
