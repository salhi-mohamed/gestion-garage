package Controlleurs;

import Modeles.Exceptions.ArgumentInvalideException;
import Modeles.Exceptions.PieceRechangeExisteException;
import Modeles.Exceptions.QuantiteNegatifException;
import Modeles.Personnes.Receptionniste;
import Modeles.Stocks.Piece_Rechange;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AjouterPieceRechangeController {

    private Receptionniste receptionnisteConnecte;  // Référence au réceptionniste connecté
    private static int dernierIdPiece = 0;  // Variable statique pour générer des IDs uniques

    @FXML
    private TextField nomField, descriptionField, prixField, quantiteStockField;

    @FXML
    public void initialize() {
        // Récupérer le réceptionniste connecté depuis MenuPrincipaleController
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }
    }

    @FXML
    public void ajouterPieceRechange() {
        // Récupérer les données depuis les champs de texte
        String nom = nomField.getText();
        String description = descriptionField.getText();
        String quantiteText = quantiteStockField.getText();
        String prixText = prixField.getText();

        // Vérifier si les champs sont vides ou contiennent des caractères invalides
        if (nom.isEmpty() || description.isEmpty() || quantiteText.isEmpty() || prixText.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        int quantite = 0;
        double prix = 0.0;

        try {
            // Convertir les entrées
            quantite = Integer.parseInt(quantiteText);
            prix = Double.parseDouble(prixText);

            // Générer un nouvel ID
            dernierIdPiece++;

            // Ajouter la pièce via le réceptionniste
            receptionnisteConnecte.creerPieceRechange(dernierIdPiece, nom, description, prix, quantite);
            showAlert("Succès", "La pièce de rechange a été ajoutée avec succès !");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer un nombre valide pour la quantité et le prix.");
            e.printStackTrace(); // Suivi de l'erreur dans la console
        } catch (PieceRechangeExisteException e) {
            showAlert("Erreur", "Une pièce de rechange avec ce nom existe déjà.");
            e.printStackTrace(); // Suivi de l'erreur dans la console
        } catch (ArgumentInvalideException | QuantiteNegatifException e) {
            showAlert("Erreur", "Erreur de validation : " + e.getMessage());
            e.printStackTrace(); // Suivi de l'erreur dans la console
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout de la pièce de rechange.");
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
