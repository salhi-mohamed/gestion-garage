package Controlleurs;

import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class AjouterVoitureController {

    @FXML
    private TableView<Client> clientsTable;  // TableView pour afficher les clients
    @FXML
    private TableColumn<Client, Integer> idClientColumn;
    @FXML
    private TableColumn<Client, String> nomClientColumn;
    @FXML
    private TableColumn<Client, String> prenomClientColumn;
    @FXML
    private TextField marqueField, modeleField, anneeField, kilometrageField, immatriculationField;

    private Receptionniste receptionniste;
    private Client selectedClient;
    @FXML
    private VBox vbox; // Correctly map VBox from FXML

    @FXML
    public void initialize() {
        // Initialisation de la table des clients avec la liste des clients du réceptionniste
        receptionniste = MenuPrincipaleController.receptionnisteConnecte;
        if (receptionniste != null) {
            System.out.println("Réceptionniste connecté : " + receptionniste.get_nom());  // Debug
            updateClientsTable();  // Mettre à jour la table dès l'initialisation

            // Apply the stylesheet for the current scene (fixed previous null issue)
            if (vbox != null && vbox.getScene() != null) {
                vbox.getScene().getStylesheets().add(getClass().getResource("/Vues/ajoutervoitureclient.css").toExternalForm());
            }
        }
    }

    public void setReceptionniste(Receptionniste receptionniste) {
        this.receptionniste = receptionniste;
        System.out.println("Réceptionniste défini via setReceptionniste : " + receptionniste.get_nom());  // Debug
        updateClientsTable();  // Mettre à jour la table des clients si le réceptionniste est défini
    }

    public void updateClientsTable() {
        if (receptionniste != null && receptionniste.listeClients != null) {
            System.out.println("Nombre de clients : " + receptionniste.listeClients.size());  // Debug

            // Lier les colonnes à des propriétés spécifiques du client
            idClientColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
            nomClientColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
            prenomClientColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());

            // Mettre à jour la TableView avec la liste des clients
            ObservableList<Client> clientsObservableList = FXCollections.observableArrayList(receptionniste.listeClients);
            clientsTable.setItems(clientsObservableList);
        } else {
            System.out.println("Aucun client trouvé dans la liste.");  // Debug
        }
    }

    @FXML
    public void onClientSelected() {
        // Récupérer le client sélectionné dans la table
        selectedClient = clientsTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            // Remplir les champs de la voiture
            marqueField.setDisable(false);
            modeleField.setDisable(false);
            anneeField.setDisable(false);
            kilometrageField.setDisable(false);
            immatriculationField.setDisable(false);
        }
    }

    public void ajouterVoiture() {
        if (selectedClient == null) {
            showAlert("Erreur", "Veuillez sélectionner un client avant d'ajouter la voiture.");
            return;
        }

        // Récupérer les données du formulaire
        String marque = marqueField.getText();
        String modele = modeleField.getText();
        String anneeText = anneeField.getText();
        String kilometrageText = kilometrageField.getText();
        String immatriculation = immatriculationField.getText();

        // Vérifier que tous les champs sont remplis
        if (marque.isEmpty() || modele.isEmpty() || anneeText.isEmpty() || kilometrageText.isEmpty() || immatriculation.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        try {
            // Convertir les champs texte en valeurs appropriées
            int annee = Integer.parseInt(anneeText);
            long kilometrage = Long.parseLong(kilometrageText);

            // Créer la voiture et l'ajouter au client
            Voiture voiture = new Voiture(marque, modele, annee, kilometrage, immatriculation, selectedClient);
            receptionniste.ListeVoitures.add(voiture);
            selectedClient.ajouterVoiture(voiture);
            receptionniste.afficherVoitures();

            // Afficher un message de succès
            showAlert("Succès", "Voiture ajoutée avec succès !");
            clearFields(); // Vider les champs après ajout

            // Mettre à jour la TableView après ajout
            updateClientsTable();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez vérifier les champs numériques (année, kilométrage).");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    private void clearFields() {
        marqueField.clear();
        modeleField.clear();
        anneeField.clear();
        kilometrageField.clear();
        immatriculationField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
