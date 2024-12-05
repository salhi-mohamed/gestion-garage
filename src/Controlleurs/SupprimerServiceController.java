package Controlleurs;

import Modeles.Gestion_Service.Service;
import Modeles.Personnes.Receptionniste;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.Optional;

public class SupprimerServiceController {

    @FXML
    private TableView<Service> tableViewServices;

    @FXML
    private TableColumn<Service, String> columnService;

    @FXML
    private TableColumn<Service, String> columnDescription;

    @FXML
    private Button buttonSupprimer;

    @FXML
    private TextField searchField;  // Champ de recherche pour filtrer par service

    private Receptionniste receptionnisteConnecte;
    private ObservableList<Service> servicesList;

    public void initialize() {
        // Vérifier que le réceptionniste connecté est récupéré correctement
        if (receptionnisteConnecte == null) {
            receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte; // Utilisation du réceptionniste du MenuPrincipaleController
        }

        // Si réceptionnisteConnecte est nul, afficher une erreur
        if (receptionnisteConnecte == null) {
            showAlert("Erreur", "Aucun réceptionniste connecté.");
            return;
        }

        // Charger la liste des services associés au réceptionniste connecté
        servicesList = FXCollections.observableArrayList(receptionnisteConnecte.getListeServices());

        // Configurer les colonnes de la TableView
        columnService.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> String.valueOf(cellData.getValue().getRendezVous())));
        columnDescription.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getDescription()));

        // Afficher les services dans la TableView
        tableViewServices.setItems(servicesList);

        // Ajouter un écouteur d'événements pour le filtrage en temps réel
        searchField.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> filterServices());
    }

    // Méthode de filtrage par ID de service
    private void filterServices() {
        String filterText = searchField.getText().toLowerCase();
        ObservableList<Service> filteredServices = FXCollections.observableArrayList();

        // Vérifier si l'ID est un entier valide
        try {
            int idServiceFilter = Integer.parseInt(filterText); // Conversion du texte de recherche en entier
            for (Service service : receptionnisteConnecte.getListeServices()) {
                if (service.getIdService() == idServiceFilter) {
                    filteredServices.add(service); // Ajouter le service s'il correspond à l'ID recherché
                }
            }
        } catch (NumberFormatException e) {
            // Si la conversion échoue (si l'entrée n'est pas un nombre), ne rien filtrer
            filteredServices = servicesList;
        }

        tableViewServices.setItems(filteredServices);
    }

    @FXML
    private void supprimerService() {
        // Vérifier si un service est sélectionné
        Service selectedService = tableViewServices.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showAlert("Erreur", "Veuillez sélectionner un service à supprimer.");
            return;
        }

        // Créer une fenêtre de confirmation avant suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce service ?");
        confirmationAlert.setContentText("Service: " + selectedService.getDescription() + "\nDescription: " + selectedService.getDescription());

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Supprimer le service dans la base de données via le réceptionniste
            receptionnisteConnecte.supprimerService(selectedService.getIdService());

            // Supprimer le service de la liste observable
            servicesList.remove(selectedService);

            // Filtrer à nouveau la liste pour respecter le texte de recherche
            filterServices();

            // Afficher un message de succès
            showAlert("Succès", "Service supprimé avec succès !");
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
