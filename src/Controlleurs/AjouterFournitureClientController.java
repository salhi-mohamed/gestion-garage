package Controlleurs;

import Modeles.Stocks.Fourniture;
import Modeles.Exceptions.ArgumentInvalideException;
import Modeles.Exceptions.QuantiteNegatifException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AjouterFournitureClientController {

    @FXML
    private TextField nomFournitureTextField;

    @FXML
    private ComboBox<String> typeFournitureComboBox;

    @FXML
    private TextField quantiteTextField;

    @FXML
    private TextField prixTextField;

    @FXML
    private Label messageLabel;

    @FXML
  
public void initialize() {
    // Initialisation du ComboBox avec les types de fournitures
    ObservableList<String> typesFournitures = FXCollections.observableArrayList("Pièce", "Accessoire", "Produit");
    typeFournitureComboBox.setItems(typesFournitures);
}


    @FXML
    private void ajouterFourniture() {
        String nomFourniture = nomFournitureTextField.getText();
        String typeFourniture = typeFournitureComboBox.getValue();
        String quantiteStr = quantiteTextField.getText();
        String prixStr = prixTextField.getText();

        // Validation des entrées
        if (nomFourniture.isEmpty() || typeFourniture == null || quantiteStr.isEmpty() || prixStr.isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        try {
            int quantite = Integer.parseInt(quantiteStr);
            double prix = Double.parseDouble(prixStr);

            // Création de la fourniture
            Fourniture fourniture = new Fourniture(0, nomFourniture, typeFourniture, prix, quantite);

            // Ajouter la fourniture (ajoutez votre logique d'ajout ici, par exemple dans une base de données)
            // Si la fourniture est ajoutée avec succès, vous pouvez l'ajouter à une liste ou base de données.

            // Afficher un message de succès
            messageLabel.setText("Fourniture ajoutée avec succès !");
            showAlert(AlertType.INFORMATION, "Succès", "Fourniture ajoutée avec succès !");

            // Réinitialiser les champs
            resetFields();

        } catch (NumberFormatException e) {
            messageLabel.setText("Veuillez entrer des valeurs numériques valides pour la quantité et le prix.");
        } catch (ArgumentInvalideException | QuantiteNegatifException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void annulerAjout() {
        // Réinitialiser les champs de texte
        resetFields();
    }

    private void resetFields() {
        nomFournitureTextField.clear();
        typeFournitureComboBox.getSelectionModel().clearSelection();
        quantiteTextField.clear();
        prixTextField.clear();
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
