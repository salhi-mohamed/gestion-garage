package Controlleurs;

import Modeles.Personnes.Chef;
import Modeles.Personnes.Laveur;
import Modeles.Personnes.Mecanicien;
import Modeles.Personnes.Employe;
import Modeles.Personnes.Receptionniste;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Optional;

public class SupprimerEmployeController {

    @FXML
    private TableView<Employe> tableViewEmployes;

    @FXML
    private TableColumn<Employe, String> columnNom;

    @FXML
    private TableColumn<Employe, String> columnPrenom;

    @FXML
    private TableColumn<Employe, Integer> columnTelephone;

    @FXML
    private TableColumn<Employe, String> columnAdresse;

    @FXML
    private TableColumn<Employe, Double> columnSalaire;

    @FXML
    private ComboBox<String> typeEmployeCombo;

    @FXML
    private Button buttonSupprimer;

    private Receptionniste receptionnisteConnecte;
    private ObservableList<Employe> employes;

    public void initialize() {
        // Récupérer le réceptionniste connecté
        this.receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        // Vérification que le réceptionniste est connecté
        if (receptionnisteConnecte == null) {
            showAlert("Erreur", "Aucun réceptionniste connecté.");
            return;
        }

        // Initialiser les types d'employés
        ObservableList<String> typesEmployes = FXCollections.observableArrayList("Laveur", "Mécanicien", "Chef");
        typeEmployeCombo.setItems(typesEmployes);

        // Ajouter un écouteur pour charger les employés selon le type sélectionné
        typeEmployeCombo.setOnAction(event -> loadEmployes());

        // Initialiser le tableau pour afficher les employés
        loadEmployes();

        // Configure the TableView columns
        columnNom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().get_nom()));
        columnPrenom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().get_prenom()));
        columnTelephone.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().get_telephone()).asObject());
        columnAdresse.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().get_adresse()));
        columnSalaire.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().get_salaire()).asObject());
    }

    // Méthode pour charger les employés en fonction du type sélectionné
    private void loadEmployes() {
        String typeEmploye = typeEmployeCombo.getValue();

        if (typeEmploye == null) return;

        ObservableList<Employe> employees = FXCollections.observableArrayList();

        // Filtrer les employés selon le type sélectionné
        for (Employe employe : receptionnisteConnecte.getListeEmployes()) {
            if (typeEmploye.equals("Laveur") && employe instanceof Laveur) {
                employees.add(employe);
            } else if (typeEmploye.equals("Mécanicien") && employe instanceof Mecanicien) {
                employees.add(employe);
            } else if (typeEmploye.equals("Chef") && employe instanceof Chef) {
                employees.add(employe);
            }
        }

        // Mise à jour de la TableView avec la liste filtrée d'employés
        tableViewEmployes.setItems(employees);
    }

    // Méthode pour supprimer un employé
    @FXML
    private void supprimerEmploye() {
        // Vérifier si un employé est sélectionné
        Employe selectedEmployee = tableViewEmployes.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showAlert("Erreur", "Veuillez sélectionner un employé à supprimer.");
            return;
        }

        // Créer une fenêtre de confirmation avant suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet employé ?");
        confirmationAlert.setContentText("Employé : " + selectedEmployee.get_nom() + " " + selectedEmployee.get_prenom());

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Supprimer l'employé de la liste du réceptionniste
            receptionnisteConnecte.getListeEmployes().remove(selectedEmployee);

            // Mettre à jour la liste des employés dans le tableau
            loadEmployes();

            // Afficher un message de succès
            showAlert("Succès", "Employé supprimé avec succès !");
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
