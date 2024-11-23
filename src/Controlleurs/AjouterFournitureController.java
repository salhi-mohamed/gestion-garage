package Controlleurs;

import Modeles.Exceptions.FournitureExisteClientException;
import Modeles.Personnes.Receptionniste;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AjouterFournitureController {

    private Receptionniste receptionnisteConnecte;  // Référence au réceptionniste connecté

    private static int dernierIdFourniture = 0;  // Variable statique pour générer des IDs uniques

    @FXML
    private TextField nomField, descriptionField, quantiteField, prixUnitaireField;

    @FXML
    public void initialize() {
        // Récupérer le réceptionniste connecté depuis MenuPrincipaleController
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }
    }

  public void ajouterFourniture() {
    // Récupérer les données depuis les champs de texte
    String nom = nomField.getText();
    String description = descriptionField.getText();
    String quantiteText = quantiteField.getText();
    String prixUnitaireText = prixUnitaireField.getText();

    // Vérifier si les champs sont vides ou contiennent des caractères invalides
    if (nom.isEmpty() || description.isEmpty() || quantiteText.isEmpty() || prixUnitaireText.isEmpty()) {
        showAlert("Erreur", "Tous les champs doivent être remplis.");
        return;
    }

    // Vérifier si la quantité et le prix sont des nombres valides
    int quantite = 0;
    double prixUnitaire = 0.0;

    try {
        quantite = Integer.parseInt(quantiteText);
    } catch (NumberFormatException e) {
        showAlert("Erreur", "La quantité doit être un nombre entier valide.");
        e.printStackTrace();  // Afficher l'erreur dans la console
        return;
    }

    try {
        prixUnitaire = Double.parseDouble(prixUnitaireText);
    } catch (NumberFormatException e) {
        showAlert("Erreur", "Le prix unitaire doit être un nombre valide.");
        e.printStackTrace();  // Afficher l'erreur dans la console
        return;
    }

    // Générer un nouvel ID unique en l'incrémentant
    dernierIdFourniture++;

    try {
        // Utiliser le réceptionniste pour ajouter une fourniture avec le nouvel ID
        receptionnisteConnecte.creerFourniture(dernierIdFourniture, nom, description, prixUnitaire, quantite);
        showAlert("Succès", "Fourniture ajoutée avec succès !");
    } catch (FournitureExisteClientException e) {
        showAlert("Erreur", "Cette fourniture existe déjà.");
        e.printStackTrace();  // Afficher l'erreur dans la console
    } catch (Exception e) {
        showAlert("Erreur", "Une erreur est survenue lors de l'ajout de la fourniture.");
        e.printStackTrace();  // Afficher l'erreur dans la console
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
