package Controlleurs;

import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import javafx.scene.Scene;

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

    private Receptionniste receptionniste;

    @FXML
    public void initialize() {
        receptionniste = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionniste != null) {
            initialiserColonnes();
            afficherTousLesClients(receptionniste);
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

    private void afficherTousLesClients(Receptionniste receptionniste) {
        List<Client> listeClients = receptionniste.get_liste_clients();
        tableClients.getItems().clear();
        tableClients.getItems().addAll(listeClients);
    }

    private void afficherFormulaireModification(Client client) {
        // Création de la fenêtre de modification
        Stage stage = new Stage();
        stage.setTitle("Modifier Client");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20px;");

        // Champs pour les nouvelles valeurs
        TextField txtNom = new TextField(client.get_nom());
        txtNom.setPromptText("Nom");

        TextField txtPrenom = new TextField(client.get_prenom());
        txtPrenom.setPromptText("Prénom");

        TextField txtTelephone = new TextField(String.valueOf(client.get_telephone()));
        txtTelephone.setPromptText("Téléphone");

        TextField txtAdresse = new TextField(client.get_adresse());
        txtAdresse.setPromptText("Adresse");

        Button btnValider = new Button("Valider");
        btnValider.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        btnValider.setOnAction(event -> {
            try {
                // Récupération des nouvelles valeurs
                String nouveauNom = txtNom.getText();
                String nouveauPrenom = txtPrenom.getText();
                int nouveauTelephone = Integer.parseInt(txtTelephone.getText());
                String nouvelleAdresse = txtAdresse.getText();

                // Mise à jour des attributs du client
                client.set_nom(nouveauNom);
                client.set_prenom(nouveauPrenom);
                client.set_telephone(nouveauTelephone);
                client.set_adresse(nouvelleAdresse);

                // Rafraîchissement de la table
                afficherTousLesClients(receptionniste);
                stage.close();
            } catch (NumberFormatException e) {
                // Gestion des erreurs de saisie
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Téléphone invalide");
                alert.setContentText("Veuillez entrer un numéro de téléphone valide.");
                alert.showAndWait();
            }
        });

        btnAnnuler.setOnAction(event -> stage.close());

        vbox.getChildren().addAll(
                new Label("Nom :"), txtNom,
                new Label("Prénom :"), txtPrenom,
                new Label("Téléphone :"), txtTelephone,
                new Label("Adresse :"), txtAdresse,
                btnValider, btnAnnuler
        );

        stage.setScene(new Scene(vbox, 300, 400));
        stage.show();
    }

    private void supprimerClient(int idClient) {
        Client client = receptionniste.chercherClientParId(idClient);
        if (client != null) {
            receptionniste.get_liste_clients().remove(client); // Suppression du client
            afficherTousLesClients(receptionniste); // Rafraîchir la table
        } else {
            System.out.println("Client non trouvé.");
        }
    }
}
