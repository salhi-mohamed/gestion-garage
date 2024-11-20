package Controlleurs;

import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
        // Configurer les colonnes pour qu'elles affichent les propriétés des clients
        colID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().get_id()).asObject());
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_nom()));
        colPrenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_prenom()));
        // Convertir le téléphone (int) en String
        colTelephone.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.valueOf(cellData.getValue().get_telephone())));
        colAdresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_adresse()));
    }

    private void afficherClients(Receptionniste receptionniste) {
        // Vider la TableView avant d'ajouter des données
        tableClients.getItems().clear();

        // Ajouter les clients récupérés de la liste du réceptionniste
        tableClients.getItems().addAll(receptionniste.get_liste_clients());
    }

    // Méthode pour retourner (fermer ou revenir à l'écran précédent)
    public void retour() {
        // Implémentation du retour à la page précédente
    }
}
