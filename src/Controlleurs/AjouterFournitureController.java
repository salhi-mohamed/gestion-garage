package Controlleurs;

import Modeles.Exceptions.ArgumentInvalideException;
import Modeles.Exceptions.FournitureExisteClientException;
import Modeles.Exceptions.QuantiteNegatifException;
import Modeles.Personnes.Receptionniste;
import Modeles.Stocks.Fourniture;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AjouterFournitureController {

    private Receptionniste receptionnisteConnecte; // Référence au réceptionniste connecté
    private static int dernierIdFourniture = 0;   // Variable statique pour générer des IDs uniques

    @FXML
    private TextField nomField, descriptionField, prixUnitaireField, quantiteField;

    @FXML
    public void initialize() {
        // Récupérer le réceptionniste connecté depuis MenuPrincipaleController
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }
    }

    @FXML
    public void ajouterFourniture() {
        // Récupérer les données depuis les champs de texte
        String nom = nomField.getText();
        String description = descriptionField.getText();
        String quantiteText = quantiteField.getText();
        String prixText = prixUnitaireField.getText();

        // Vérifier si les champs sont vides ou contiennent des caractères invalides
        if (nom.isEmpty() || description.isEmpty() || quantiteText.isEmpty() || prixText.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        int quantite = 0;
        double prixUnitaire = 0.0;

        try {
            // Convertir les entrées
            quantite = Integer.parseInt(quantiteText);
            prixUnitaire = Double.parseDouble(prixText);

            // Générer un nouvel ID
            dernierIdFourniture++;

            // Ajouter la fourniture via le réceptionniste
            receptionnisteConnecte.creerFourniture(dernierIdFourniture, nom, description, prixUnitaire, quantite);
            showAlert("Succès", "La fourniture a été ajoutée avec succès !");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer un nombre valide pour la quantité et le prix unitaire.");
            e.printStackTrace(); // Suivi de l'erreur dans la console
        } catch (FournitureExisteClientException e) {
            showAlert("Erreur", "Une fourniture avec ce nom existe déjà.");
            e.printStackTrace(); // Suivi de l'erreur dans la console
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout de la fourniture.");
            e.printStackTrace(); // Suivi de l'erreur dans la console
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
