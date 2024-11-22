package Controlleurs;

import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

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
    public void initialize() {
        // Récupérer le réceptionniste fictif depuis le contrôleur AjouterClientController
        Receptionniste receptionniste = AjouterClientController.receptionnisteConnecte;

        if (receptionniste != null) {
            // Initialiser les colonnes et afficher les clients
            initialiserColonnes();
            afficherClients(receptionniste);
        }
    }

    private void initialiserColonnes() {
        // Configurer les colonnes pour afficher les propriétés des clients
        colID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().get_id()).asObject());
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_nom()));
        colPrenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_prenom()));
        colTelephone.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.valueOf(cellData.getValue().get_telephone())));
        colAdresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_adresse()));

        // Ajouter la colonne "Actions" avec un bouton de modification et de suppression
        TableColumn<Client, Void> colActions = new TableColumn<>("Actions");

        colActions.setCellFactory(new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
            @Override
            public TableCell<Client, Void> call(TableColumn<Client, Void> param) {
                return new TableCell<Client, Void>() {
                    private final Button modifyButton = new Button("Modifier");
                    private final Button deleteButton = new Button("Supprimer");

                    {
                        // Style moderne pour les boutons
                        modifyButton.setStyle(
                            "-fx-background-color: #4CAF50; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 12px; " +  // Réduire la taille de la police
                            "-fx-padding: 5px 15px; " +  // Réduire le padding
                            "-fx-border-radius: 5px; " +
                            "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 2, 2);" +
                            "-fx-cursor: hand;" +
                            "-fx-min-width: 100px; " +  // Définir une largeur minimale pour les deux boutons
                            "-fx-max-width: 100px; " +  // Largeur maximale égale
                            "-fx-pref-width: 100px;"    // Largeur préférée égale
                        );
                        deleteButton.setStyle(
                            "-fx-background-color: #F44336; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 12px; " +  // Réduire la taille de la police
                            "-fx-padding: 5px 15px; " +  // Réduire le padding
                            "-fx-border-radius: 5px; " +
                            "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 2, 2);" +
                            "-fx-cursor: hand;" +
                            "-fx-min-width: 100px; " +  // Définir une largeur minimale pour les deux boutons
                            "-fx-max-width: 100px; " +  // Largeur maximale égale
                            "-fx-pref-width: 100px;"    // Largeur préférée égale
                        );

                        // Ajouter les effets de survol
                        modifyButton.setOnMouseEntered(event -> modifyButton.setStyle(
                            "-fx-background-color: #45a049; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 12px; " +
                            "-fx-padding: 5px 15px; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 2, 2); " +
                            "-fx-cursor: hand;"
                        ));

                        deleteButton.setOnMouseEntered(event -> deleteButton.setStyle(
                            "-fx-background-color: #e53935; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 12px; " +
                            "-fx-padding: 5px 15px; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 2, 2); " +
                            "-fx-cursor: hand;"
                        ));

                        modifyButton.setOnMouseExited(event -> modifyButton.setStyle(
                            "-fx-background-color: #4CAF50; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 12px; " +
                            "-fx-padding: 5px 15px; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 2, 2);" +
                            "-fx-cursor: hand;"
                        ));

                        deleteButton.setOnMouseExited(event -> deleteButton.setStyle(
                            "-fx-background-color: #F44336; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 12px; " +
                            "-fx-padding: 5px 15px; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 2, 2);" +
                            "-fx-cursor: hand;"
                        ));

                        modifyButton.setOnAction(event -> {
                            Client client = getTableView().getItems().get(getIndex());
                            modifierClient(client);
                        });

                        deleteButton.setOnAction(event -> {
                            Client client = getTableView().getItems().get(getIndex());
                            supprimerClient(client);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            VBox buttons = new VBox(5, modifyButton, deleteButton);
                            setGraphic(buttons);
                        }
                    }
                };
            }
        });

        tableClients.getColumns().add(colActions); // Ajouter la colonne "Actions" à la TableView
    }

    private void afficherClients(Receptionniste receptionniste) {
        // Vider la TableView avant d'ajouter des clients
        tableClients.getItems().clear();

        // Ajouter les clients récupérés de la liste du réceptionniste
        tableClients.getItems().addAll(receptionniste.get_liste_clients());
    }

    private void modifierClient(Client client) {
        // Implémentation de la modification des données du client
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Modifier Client");
        dialog.setHeaderText("Modifier les informations du client");

        // Créer des champs pour modifier les informations
        TextField nomField = new TextField(client.get_nom());
        TextField prenomField = new TextField(client.get_prenom());
        TextField telephoneField = new TextField(String.valueOf(client.get_telephone()));
        TextField adresseField = new TextField(client.get_adresse());

        // Ajouter les champs au dialogue
        dialog.getDialogPane().setContent(new VBox(10, nomField, prenomField, telephoneField, adresseField));

        // Ajouter les boutons "OK" et "Annuler"
        ButtonType okButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Attacher l'action du bouton "OK"
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                // Mettre à jour les informations du client
                client.set_nom(nomField.getText());
                client.set_prenom(prenomField.getText());
                client.set_telephone(Integer.parseInt(telephoneField.getText()));
                client.set_adresse(adresseField.getText());

                // Mettre à jour l'affichage dans la TableView
                tableClients.refresh();
            }
            return null;
        });

        // Afficher la fenêtre de dialogue
        dialog.showAndWait();
    }

    private void supprimerClient(Client client) {
        // Demander confirmation avant de supprimer le client
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce client ?");
        alert.setContentText("Cette action est irréversible.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Supprimer le client de la liste
                Receptionniste receptionniste = AjouterClientController.receptionnisteConnecte;
                receptionniste.get_liste_clients().remove(client);
                afficherClients(receptionniste); // Réactualiser la TableView
            }
        });
    }
}
