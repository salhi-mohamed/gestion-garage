package Controlleurs;

import Modeles.Exceptions.ArgumentInvalideException;
import Modeles.Exceptions.QuantiteNegatifException;
import Modeles.Stocks.Piece_Rechange;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormulaireModificationPieceRechangeController {

    @FXML
    private TextField textFieldNom;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextField textFieldPrix;

    @FXML
    private TextField textFieldQuantite;

    private Piece_Rechange pieceRechange;

    public void setPieceRechange(Piece_Rechange pieceRechange) {
        this.pieceRechange = pieceRechange;

        // Remplir les champs avec les informations actuelles de la pièce de rechange
        textFieldNom.setText(pieceRechange.getNom());
        textFieldDescription.setText(pieceRechange.getDescription());
        textFieldPrix.setText(String.valueOf(pieceRechange.getPrix()));
        textFieldQuantite.setText(String.valueOf(pieceRechange.getQuantiteStock()));
    }

    @FXML
    private void enregistrerModifications() {
        try {
            // Mettre à jour les informations de la pièce de rechange
            pieceRechange.setNom(textFieldNom.getText());
            pieceRechange.setDescription(textFieldDescription.getText());
            pieceRechange.setPrix(Double.parseDouble(textFieldPrix.getText()));
            pieceRechange.setQuantiteStock(Integer.parseInt(textFieldQuantite.getText()));

            // Sauvegarder dans la base de données
            // Par exemple : gestionnaireStocks.modifierPieceRechange(pieceRechange);

            // Fermer la fenêtre
            fermerFormulaire();
        } catch (NumberFormatException e) {
            // Gérer les erreurs de saisie des nombres
            showAlert("Erreur", "Veuillez entrer des valeurs valides pour le prix et la quantité.");
        } catch (QuantiteNegatifException | ArgumentInvalideException e) {
            showAlert("Erreur", "Les valeurs saisies ne sont pas valides.");
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
