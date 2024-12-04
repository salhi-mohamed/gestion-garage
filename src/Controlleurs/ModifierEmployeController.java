package Controlleurs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// Importation des classes de modèle
import Modeles.Personnes.Chef;
import Modeles.Personnes.Employe;
import Modeles.Personnes.Expertise;
import Modeles.Personnes.Laveur;
import Modeles.Personnes.Mecanicien;
import Modeles.Personnes.Receptionniste;

public class ModifierEmployeController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField telephoneField;
    @FXML private TextField adresseField;
    @FXML private TextField salaireField;

    @FXML private ComboBox<String> specialiteCombo;
    @FXML private ComboBox<Expertise> expertiseCombo;
    @FXML private CheckBox specialisationExterieurCheckBox;
    @FXML private CheckBox specialisationInterieurCheckBox;

    private Receptionniste receptionnisteConnecte;
    private Employe employeAModifier;

    public void initialize() {
        // Initialisation du réceptionniste connecté
        this.receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        // Initialisation des ComboBox pour les mécaniciens
        specialiteCombo.setItems(FXCollections.observableArrayList("Moteur", "Electricité", "Carrosserie", "Peinture", "Freinage"));
        expertiseCombo.setItems(FXCollections.observableArrayList(Expertise.values()));

        // Remplir les champs avec les données de l'employé
        remplirChampsEmploye();
    }

    private void remplirChampsEmploye() {
        if (employeAModifier == null) return;

        // Remplir les champs communs
        nomField.setText(employeAModifier.get_nom());
        prenomField.setText(employeAModifier.get_prenom());
        telephoneField.setText(String.valueOf(employeAModifier.get_telephone()));
        adresseField.setText(employeAModifier.get_adresse());
        salaireField.setText(String.format("%.2f", employeAModifier.getSalaire()));

        // Gérer les champs spécifiques selon le type d'employé
        if (employeAModifier instanceof Mecanicien) {
            Mecanicien mecanicien = (Mecanicien) employeAModifier;
            specialiteCombo.setValue(mecanicien.get_specialite());
            expertiseCombo.setValue(mecanicien.get_expertise());
            specialiteCombo.setDisable(false);
            expertiseCombo.setDisable(false);
            specialisationExterieurCheckBox.setDisable(true);
            specialisationInterieurCheckBox.setDisable(true);
            // Décocher les CheckBox au cas où un mécanicien a été modifié
            specialisationExterieurCheckBox.setSelected(false);
            specialisationInterieurCheckBox.setSelected(false);
        } else if (employeAModifier instanceof Laveur) {
            Laveur laveur = (Laveur) employeAModifier;
            specialisationExterieurCheckBox.setSelected(laveur.get_specialise_exterieur());
            specialisationInterieurCheckBox.setSelected(laveur.get_specialise_interieur());
            specialiteCombo.setDisable(true);
            expertiseCombo.setDisable(true);
            specialisationExterieurCheckBox.setDisable(false);
            specialisationInterieurCheckBox.setDisable(false);
            // Désactiver les ComboBox spécifiques à Mécanicien
            specialiteCombo.setValue(null);
            expertiseCombo.setValue(null);
        } else if (employeAModifier instanceof Chef) {
            specialiteCombo.setDisable(true);
            expertiseCombo.setDisable(true);
            specialisationExterieurCheckBox.setDisable(true);
            specialisationInterieurCheckBox.setDisable(true);
        }
    }

    public void setEmployeAModifier(Employe employe) {
        this.employeAModifier = employe;
    }

    @FXML
    private void enregistrerModifications() {
        try {
            // Mise à jour des informations communes
            employeAModifier.set_nom(nomField.getText());
            employeAModifier.set_prenom(prenomField.getText());
            employeAModifier.set_telephone(Integer.parseInt(telephoneField.getText()));
            employeAModifier.set_adresse(adresseField.getText());
            employeAModifier.setSalaire(Double.parseDouble(salaireField.getText()));

            // Mise à jour des informations spécifiques pour Mécanicien
            if (employeAModifier instanceof Mecanicien) {
                Mecanicien mecanicien = (Mecanicien) employeAModifier;
                mecanicien.set_specialite(specialiteCombo.getValue());
                mecanicien.set_expertise(expertiseCombo.getValue());
            } 
            // Mise à jour des informations spécifiques pour Laveur
            else if (employeAModifier instanceof Laveur) {
                Laveur laveur = (Laveur) employeAModifier;
                laveur.set_specialise_exterieur(specialisationExterieurCheckBox.isSelected());
                laveur.set_specialise_interieur(specialisationInterieurCheckBox.isSelected());
            }

            // Affichage d'un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Modifications enregistrées");
            alert.setContentText("Les informations de l'employé ont été modifiées avec succès.");
            alert.showAndWait();

            // Fermer la fenêtre après l'enregistrement
            ((Stage) nomField.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            // Gérer les erreurs de saisie pour les champs numériques
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Modification échouée");
            alert.setContentText("Veuillez vérifier les champs numériques et réessayer.");
            alert.showAndWait();
        } catch (Exception e) {
            // Gérer toute autre erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Modification échouée");
            alert.setContentText("Veuillez vérifier les champs et réessayer.");
            alert.showAndWait();
        }
    }

    @FXML
    private void annuler() {
        // Fermer la fenêtre
        ((Stage) nomField.getScene().getWindow()).close();
    }
}
