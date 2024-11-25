package Controlleurs;

import Modeles.Personnes.Chef;
import Modeles.Personnes.Laveur;
import Modeles.Personnes.Mecanicien;
import Modeles.Personnes.Expertise;
import Modeles.Personnes.Receptionniste;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AjouterEmployeController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField telephoneField;
    @FXML private TextField adresseField;
    @FXML private TextField salaireField;
    
    @FXML private DatePicker dateEmbaucheField;  // Using DatePicker for date input
    
    @FXML private ComboBox<String> typeEmployeCombo;
    @FXML private ComboBox<String> specialiteCombo;
    @FXML private ComboBox<Expertise> expertiseCombo;
    @FXML private ComboBox<String> specialiteLaveurCombo;  // New ComboBox for Laveur specialization

    private ObservableList<String> typeEmployeList;
    private ObservableList<String> specialiteList;
    private ObservableList<Expertise> expertiseList;
    private ObservableList<String> specialiteLaveurList;  // List for Laveur specializations

    private static int idCounter = 0; // Static variable to auto-increment ID for each employee

    private Receptionniste receptionniste;  // Instance variable to hold the logged-in receptionniste

    public void initialize() {
        // Access the logged-in Receptionniste from MenuPrincipaleController
        this.receptionniste = MenuPrincipaleController.receptionnisteConnecte;

        // Initialisation des ComboBox
        typeEmployeList = FXCollections.observableArrayList("Laveur", "Mécanicien", "Chef");
        typeEmployeCombo.setItems(typeEmployeList);

        // Initialisation des ComboBox de spécialité et expertise pour le Mécanicien
        specialiteList = FXCollections.observableArrayList("Moteur", "Electricité", "Carrosserie", "Peinture", "Freinage");
        specialiteCombo.setItems(specialiteList);

        expertiseList = FXCollections.observableArrayList(Expertise.values());
        expertiseCombo.setItems(expertiseList);

        // Initialize ComboBox for Laveur specializations
        specialiteLaveurList = FXCollections.observableArrayList("Intérieur", "Extérieur");
        specialiteLaveurCombo.setItems(specialiteLaveurList);

        // Gérer la visibilité des ComboBox selon le type d'employé
        typeEmployeCombo.setOnAction(event -> {
            if ("Mécanicien".equals(typeEmployeCombo.getValue())) {
                specialiteCombo.setDisable(false);
                expertiseCombo.setDisable(false);
                specialiteLaveurCombo.setDisable(true);  // Disable Laveur specialization for Mécanicien
            } else if ("Laveur".equals(typeEmployeCombo.getValue())) {
                specialiteCombo.setDisable(true);  // Disable Mécanicien specializations for Laveur
                expertiseCombo.setDisable(true);
                specialiteLaveurCombo.setDisable(false);  // Enable Laveur specialization
            } else {
                specialiteCombo.setDisable(true);
                expertiseCombo.setDisable(true);
                specialiteLaveurCombo.setDisable(true);
            }
        });
    }

    // Méthode pour ajouter un employé
    public void ajouterEmploye() {
        // Récupération des données du formulaire
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String telephoneStr = telephoneField.getText();
        String adresse = adresseField.getText();
        String salaireStr = salaireField.getText();
        LocalDate dateEmbauche = dateEmbaucheField.getValue();  // Get the selected date from the DatePicker
        
        // Vérification des données entrées
        if (nom.isEmpty() || prenom.isEmpty() || telephoneStr.isEmpty() || adresse.isEmpty() || salaireStr.isEmpty() || dateEmbauche == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }
        
        int telephone;
        double salaire;

        try {
            telephone = Integer.parseInt(telephoneStr);
            salaire = Double.parseDouble(salaireStr);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer un numéro de téléphone et un salaire valides.");
            return;
        }

        // Convert LocalDate to String in the format "dd/MM/yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateEmbaucheStr = dateEmbauche.format(formatter);  // Convert to string
        
        // Création de l'employé selon le type sélectionné
        String typeEmploye = typeEmployeCombo.getValue();
        if (typeEmploye == null) {
            showAlert("Erreur", "Veuillez sélectionner un type d'employé.");
            return;
        }

        // Increment the ID for the new employee
        idCounter++; // Increment the static ID counter for each new employee

        // Création de l'employé en fonction du type sélectionné
        switch (typeEmploye) {
            case "Laveur":
                String specialiteLaveur = specialiteLaveurCombo.getValue();
                if (specialiteLaveur == null) {
                    showAlert("Erreur", "Veuillez sélectionner une spécialité pour le laveur.");
                    return;
                }
                Laveur laveur = new Laveur(idCounter, nom, prenom, telephone, adresse, salaire, dateEmbaucheStr, specialiteLaveur); 
                this.receptionniste.getListeEmployes().add(laveur);
                // Pass the String date and specialization
                showAlert("Succès", "Laveur ajouté avec succès.");
                break;
            case "Mécanicien":
                String specialite = specialiteCombo.getValue();
                Expertise expertise = expertiseCombo.getValue();
                if (specialite == null || expertise == null) {
                    showAlert("Erreur", "Veuillez sélectionner une spécialité et une expertise pour le mécanicien.");
                    return;
                }
                Mecanicien mecanicien = new Mecanicien(idCounter, nom, prenom, telephone, adresse, salaire, specialite, expertise, dateEmbaucheStr);  // Pass the String date
                this.receptionniste.getListeEmployes().add(mecanicien);
                showAlert("Succès", "Mécanicien ajouté avec succès.");
                break;
            case "Chef":
                Chef chef = new Chef(idCounter, nom, prenom, telephone, adresse, salaire, dateEmbaucheStr);  // Pass the String date
                this.receptionniste.getListeEmployes().add(chef);
                showAlert("Succès", "Chef ajouté avec succès.");
                break;
            default:
                showAlert("Erreur", "Type d'employé inconnu.");
                break;
        }
        this.receptionniste.afficherTousLesEmployes();
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
