package Controlleurs;

import Modeles.Personnes.Client;
import Modeles.Gestion_Service.Voiture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class SupprimerVoitureClientController {

    // ComboBox pour sélectionner un client
    @FXML
    private ComboBox<Client> comboBoxClients;

    // TableView pour afficher les voitures du client sélectionné
    @FXML
    private TableView<Voiture> tableViewVoitures;

    // Colonnes de la TableView pour afficher les informations des voitures
    @FXML
    private TableColumn<Voiture, String> colCarMarque;
    @FXML
    private TableColumn<Voiture, String> colCarModele;
    @FXML
    private TableColumn<Voiture, String> colCarImmatriculation;
    @FXML
    private TableColumn<Voiture, Long> colCarKilometrage; // Kilométrage en tant que long

    // Liste observable pour les voitures d'un client
    private ObservableList<Voiture> voituresList = FXCollections.observableArrayList();

    // Constructeur
    public SupprimerVoitureClientController() {
        // Initialisation des clients et voitures
    }

    // Méthode d'initialisation pour remplir le ComboBox et la TableView
    public void initialize() {
        // Initialiser le ComboBox avec la liste des clients (récupérés de Receptionniste)
        comboBoxClients.setItems(FXCollections.observableArrayList(AjouterClientController.receptionnisteConnecte.get_liste_clients()));

        // Initialiser les colonnes de la TableView
        colCarMarque.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
        colCarModele.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
        colCarImmatriculation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImmatriculation()));
        colCarKilometrage.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getKilometrage()).asObject()); // Kilométrage comme long

        // Ajouter un listener sur la sélection d'un client dans le ComboBox
        comboBoxClients.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mettre à jour la table avec les voitures du client sélectionné
                remplirTableVoitures(newValue);
            }
        });
    }

    // Méthode pour remplir la TableView avec les voitures d'un client
    private void remplirTableVoitures(Client selectedClient) {
        voituresList.clear();
        // Ajouter les voitures du client sélectionné à la liste observable
        voituresList.addAll(selectedClient.getVoitures());
        // Mettre à jour la TableView avec les voitures
        tableViewVoitures.setItems(voituresList);
    }

    // Méthode pour supprimer une voiture d'un client
    @FXML
    public void supprimerVoiture() {
        // Récupérer la voiture sélectionnée dans la TableView
        Voiture selectedVoiture = tableViewVoitures.getSelectionModel().getSelectedItem();
        
        if (selectedVoiture == null) {
            showAlert("Erreur", "Veuillez sélectionner une voiture à supprimer.");
            return;
        }

        // Récupérer le client sélectionné
        Client selectedClient = comboBoxClients.getSelectionModel().getSelectedItem();
        
        if (selectedClient == null) {
            showAlert("Erreur", "Veuillez sélectionner un client.");
            return;
        }

        // Appeler la méthode pour supprimer la voiture
        try {
            String idVoiture = selectedVoiture.get_immatriculation();  // ID de la voiture
            AjouterClientController.receptionnisteConnecte.supprimerVoitureClient(selectedClient.get_id(), idVoiture);
            showAlert("Succès", "La voiture a été supprimée du client.");

            // Mettre à jour la table après suppression
            remplirTableVoitures(selectedClient);
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la suppression de la voiture.");
        }
    }

    // Méthode pour afficher des alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Méthode pour fermer ou réinitialiser le formulaire
    @FXML
    public void fermerFormulaire() {
        comboBoxClients.getSelectionModel().clearSelection();
        tableViewVoitures.getItems().clear();
    }
}
