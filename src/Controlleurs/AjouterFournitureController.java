package Controlleurs;

import Modeles.Exceptions.FournitureExisteClientException;
import Modeles.Personnes.Receptionniste;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AjouterFournitureController {

    public static Receptionniste receptionnisteConnecte;  // Référence au réceptionniste fictif (partagée)

    private static int dernierIdFourniture = 0;  // Variable statique pour générer des IDs uniques

    @FXML
    private TextField nomField, descriptionField, quantiteField, prixUnitaireField;

    @FXML
    public void initialize() {
        // Vérification pour initialiser un réceptionniste fictif si nécessaire
        if (receptionnisteConnecte == null) {
            receptionnisteConnecte = new Receptionniste(
                    1, "NomReceptionniste", "PrenomReceptionniste", 123456789, "Adresse Receptionniste",
                    3000.0, "01/01/2024", 201, "email@receptionniste.com", "password123"
            );
        }
    }

    public void ajouterFourniture() {
        // Récupérer les données depuis les champs de texte
        String nom = nomField.getText();
        String description = descriptionField.getText();
        int quantite = Integer.parseInt(quantiteField.getText());
        double prixUnitaire = Double.parseDouble(prixUnitaireField.getText());

        // Générer un nouvel ID unique en l'incrémentant
        dernierIdFourniture++;

        try {
            // Utiliser le réceptionniste fictif pour ajouter une fourniture avec le nouvel ID
            receptionnisteConnecte.creerFourniture(dernierIdFourniture, nom, description, prixUnitaire, quantite);
            showAlert("Succès", "Fourniture ajoutée avec succès !");
        } catch (FournitureExisteClientException e) {
            showAlert("Erreur", "Cette fourniture existe déjà.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout de la fourniture.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}