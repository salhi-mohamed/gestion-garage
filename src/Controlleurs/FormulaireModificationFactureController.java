package Controlleurs;

import Modeles.Exceptions.ArgumentInvalideException;
import Modeles.Gestion_Service.Facture;
import Modeles.Personnes.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormulaireModificationFactureController {

    @FXML
    private TextField textFieldNumeroFacture;

    @FXML
    private TextField textFieldMontantTotal;

    @FXML
    private TextField textFieldDateFacture;

    @FXML
    private TextField textFieldClient;

    @FXML
    private TextField textFieldAvecRemise;

    private Facture facture;

    /**
     * Méthode pour initialiser le formulaire avec les informations actuelles de la facture.
     */
    public void setFacture(Facture facture) {
        this.facture = facture;

        // Remplir les champs avec les données actuelles de la facture
        textFieldNumeroFacture.setText(String.valueOf(facture.getNumeroFacture()));
        textFieldMontantTotal.setText(String.valueOf(facture.getMontantTotal()));
        textFieldDateFacture.setText(new SimpleDateFormat("yyyy-MM-dd").format(facture.getDateFacture()));
        textFieldClient.setText(facture.getClient() != null ? facture.getClient().toString() : "");
        textFieldAvecRemise.setText(facture.isAvecRemise() ? "Oui" : "Non");
    }

    @FXML
    private void enregistrerModifications() {
        try {
            // Mettre à jour les informations de la facture
            facture.setMontantTotal(Double.parseDouble(textFieldMontantTotal.getText()));

            // Convertir la date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(textFieldDateFacture.getText());
            facture.setDateFacture(date);

            // Gérer le client (supposant qu'un gestionnaire de clients existe pour récupérer le client par nom ou ID)
            String clientNom = textFieldClient.getText();
            Client client = trouverClientParNom(clientNom); // À implémenter
            if (client != null) {
                facture.setClient(client);
            } else {
                showAlert("Erreur", "Client introuvable.");
                return;
            }

            // Gérer la remise
            boolean avecRemise = "Oui".equalsIgnoreCase(textFieldAvecRemise.getText().trim());
            facture.setAvecRemise(avecRemise);

            // Sauvegarder dans la base de données si nécessaire
            // Par exemple : gestionnaireFactures.modifierFacture(facture);

            // Fermer la fenêtre
            fermerFormulaire();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides pour le montant.");
        } catch (ParseException e) {
            showAlert("Erreur", "Veuillez entrer une date valide au format 'yyyy-MM-dd'.");
        }
    }

    @FXML
    private void fermerFormulaire() {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) textFieldNumeroFacture.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Simuler la recherche d'un client par nom.
     * Cette méthode doit être connectée à votre gestionnaire de clients.
     */
    private Client trouverClientParNom(String nom) {
        // Exemple fictif, à remplacer par un vrai appel à la base de données ou gestionnaire
        return new Client(); // Remplacez par une recherche réelle
    }
}
