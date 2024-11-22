package Controlleurs;

import Modeles.Exceptions.VoitureDejaExistanteClientException;
import Modeles.Exceptions.VoitureDejaExistanteException;
import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Client;
import Modeles.Personnes.Receptionniste;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AjouterVoitureController {

    @FXML
    private TextField idClientField, marqueField, modeleField, anneeField, kilometrageField, immatriculationField;

    @FXML
    public void initialize() {
        // Initialisation si nécessaire
        if (AjouterClientController.receptionnisteConnecte == null) {
            showAlert("Erreur", "Veuillez ajouter un client d'abord.");
        }
    }

   public void ajouterVoiture() {
    // Vérification de la connexion du réceptionniste
    Receptionniste receptionniste = AjouterClientController.receptionnisteConnecte;
    if (receptionniste == null) {
        showAlert("Erreur", "Aucun réceptionniste connecté. Veuillez réessayer.");
        return;
    }

    // Récupérer les données saisies
    String idClientText = idClientField.getText();
    String marque = marqueField.getText();
    String modele = modeleField.getText();
    String anneeText = anneeField.getText();
    String kilometrageText = kilometrageField.getText();
    String immatriculation = immatriculationField.getText();

    // Vérifier que tous les champs sont remplis
    if (idClientText.isEmpty() || marque.isEmpty() || modele.isEmpty() || anneeText.isEmpty() 
            || kilometrageText.isEmpty() || immatriculation.isEmpty()) {
        showAlert("Erreur", "Tous les champs doivent être remplis.");
        return;
    }

    try {
        // Convertir les champs texte en valeurs appropriées
        int idClient = Integer.parseInt(idClientText);
        int annee = Integer.parseInt(anneeText);
        long kilometrage = Long.parseLong(kilometrageText);

        // Vérifier si le client existe avant d'appeler la méthode creerVoiture
        Optional<Client> clientExist = receptionniste.listeClients.stream()
                .filter(client -> client.get_id() == idClient)
                .findFirst();

        if (!clientExist.isPresent()) {
            // Si le client n'existe pas, afficher un message d'erreur
            showAlert("Erreur", "Le client avec l'ID " + idClient + " n'existe pas.");
            return;
        }

        // Appeler la méthode du réceptionniste pour créer la voiture
        receptionniste.creerVoiture(idClient, marque, modele, annee, kilometrage, immatriculation);

        showAlert("Succès", "Voiture ajoutée avec succès !");
        clearFields(); // Vider les champs après ajout

    } catch (VoitureDejaExistanteClientException e) {
        showAlert("Erreur", "Cette voiture est déjà associée au client.");
    } catch (NumberFormatException e) {
        showAlert("Erreur", "Veuillez vérifier les champs numériques (ID, année, kilométrage).");
    } catch (VoitureDejaExistanteException vde) {
        showAlert("Erreur", "Cette voiture existe déjà pour un autre client.");
    } catch (Exception e) {
        showAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
    }
}


    private void clearFields() {
        idClientField.clear();
        marqueField.clear();
        modeleField.clear();
        anneeField.clear();
        kilometrageField.clear();
        immatriculationField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
