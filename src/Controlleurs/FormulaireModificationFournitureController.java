package Controlleurs;

import Modeles.Exceptions.ArgumentInvalideException;
import Modeles.Exceptions.QuantiteNegatifException;
import Modeles.Stocks.Fourniture;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Contrôleur pour la modification d'une fourniture.
 */
public class FormulaireModificationFournitureController {

    @FXML
    private TextField textFieldNom;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextField textFieldPrix;

    @FXML
    private TextField textFieldQuantite;

    private Fourniture fourniture;

    /**
     * Initialise le formulaire avec les données de la fourniture sélectionnée.
     *
     * @param fourniture Fourniture à modifier.
     */
    public void setFourniture(Fourniture fourniture) {
        this.fourniture = fourniture;

        // Remplir les champs avec les informations actuelles de la fourniture
        textFieldNom.setText(fourniture.getNom());
        textFieldDescription.setText(fourniture.getDescription());
        textFieldPrix.setText(String.valueOf(fourniture.getPrix()));
        textFieldQuantite.setText(String.valueOf(fourniture.getQuantiteStock()));
    }

    @FXML
    private void enregistrerModifications() {
        try {
            // Mettre à jour les informations de la fourniture
            fourniture.setNom(textFieldNom.getText());
            fourniture.setDescription(textFieldDescription.getText());
            fourniture.setPrix(Double.parseDouble(textFieldPrix.getText()));
            fourniture.setQuantiteStock(Integer.parseInt(textFieldQuantite.getText()));

            // Sauvegarder dans la base de données
            // Par exemple : gestionnaireStocks.modifierFourniture(fourniture);

            // Fermer la fenêtre
            fermerFormulaire();
        } catch (NumberFormatException e) {
            // Gérer les erreurs de saisie des nombres
            showAlert("Erreur", "Veuillez entrer des valeurs valides pour le prix et la quantité.");
        } catch (QuantiteNegatifException | ArgumentInvalideException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void fermerFormulaire() {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) textFieldNom.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
