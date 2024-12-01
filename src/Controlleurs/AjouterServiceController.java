package Controlleurs;

import Modeles.Gestion_Service.Service;
import Modeles.Personnes.Receptionniste;
import Modeles.Gestion_Service.Voiture;
import Modeles.Gestion_Service.Rendez_vous;
import Modeles.Personnes.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AjouterServiceController {

    @FXML private TextField nomServiceField;
    @FXML private TextField descriptionField;
    @FXML private TextField tarifField;
    @FXML private ComboBox<String> typeServiceCombo;  // ComboBox pour le type de service
    @FXML private ComboBox<Rendez_vous> rendezVousCombo;  // ComboBox pour les rendez-vous

    private ObservableList<String> typeServiceList;
    private ObservableList<Rendez_vous> rendezVousList;  // Liste des rendez-vous

    private static int idCounter = 0; // Compteur pour l'ID des services

    private Receptionniste receptionniste;  // Instance de la classe Receptionniste

    public void initialize() {
        // Initialisation de la liste des types de services
        typeServiceList = FXCollections.observableArrayList("Entretien", "Réparation", "Diagnostic");
        typeServiceCombo.setItems(typeServiceList);

        // Récupération des rendez-vous dans la liste du réceptionniste
        rendezVousList = FXCollections.observableArrayList(receptionniste.getListeRendezVous());
        rendezVousCombo.setItems(rendezVousList);

        // Accès à la référence du réceptionniste connecté
        this.receptionniste = MenuPrincipaleController.receptionnisteConnecte;
    }

    // Méthode pour ajouter un service
    public void ajouterService() {
        // Récupération des données du formulaire
        String nomService = nomServiceField.getText();
        String description = descriptionField.getText();
        String tarifStr = tarifField.getText();
        String typeService = typeServiceCombo.getValue();
        Rendez_vous rendezVous = rendezVousCombo.getValue();  // Récupération du rendez-vous sélectionné

        // Vérification des champs obligatoires
        if (nomService.isEmpty() || description.isEmpty() || tarifStr.isEmpty() || typeService == null || rendezVous == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        double tarif;

        // Vérification du format du tarif
        try {
            tarif = Double.parseDouble(tarifStr);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer un tarif valide.");
            return;
        }

        // Incrémentation de l'ID pour le nouveau service
        idCounter++;

        // Supposons que vous ayez une voiture et un client à passer à votre service
        Voiture voiture = new Voiture();  // Créez une instance de Voiture ici
        Client client = new Client();  // Créez une instance de Client ici

        // Création du service
        Service service = new Service(description, tarif, idCounter, voiture, rendezVous, client);

        // Ajout du service à la liste des services du réceptionniste
        this.receptionniste.getListeServices().add(service);

        // Affichage d'une alerte de succès
        showAlert("Succès", "Service ajouté avec succès.");

        // Optionnellement, afficher la liste des services après ajout
        this.receptionniste.afficherTousLesServices();
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
