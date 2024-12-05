package Controlleurs;

import Modeles.Gestion_Service.Facture;
import Modeles.Gestion_Service.Service;
import Modeles.Personnes.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AfficherFactureController {

    @FXML
    private Label labelNumeroFacture;
    @FXML
    private Label labelClient;
    @FXML
    private Label labelDateFacture;
    @FXML
    private ListView<String> listViewServices;
    @FXML
    private Label labelMontantTotal;
    @FXML
    private Label labelAvecRemise;

    private Facture facture;
    private List<Facture> listeFactures;

    // Méthode pour initialiser le contrôleur et récupérer les factures à afficher
    public void initialize() {
        // Récupérer la liste des factures pour l'utilisateur connecté (similaire à la récupération des rendez-vous)
        listeFactures = MenuPrincipaleController.receptionnisteConnecte.getListeFactures(); // Récupérer la liste des factures

        if (listeFactures == null || listeFactures.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucune Facture");
            alert.setHeaderText("Aucune facture trouvée");
            alert.setContentText("Aucune facture n'a été ajoutée.");
            alert.showAndWait();
        } else {
            // Si on a des factures, afficher la première facture (ou tu peux ajuster selon ton besoin)
            facture = listeFactures.get(0); // On prend ici la première facture comme exemple. Tu peux l'adapter.
            afficherFacture();
        }
    }

    // Méthode pour afficher les informations de la facture dans l'interface
    private void afficherFacture() {
        if (facture != null) {
            // Affichage du numéro de facture
            labelNumeroFacture.setText(String.valueOf(facture.getNumeroFacture()));

            // Affichage du client
            labelClient.setText(facture.getClient().toString()); // Afficher les détails du client (tu peux personnaliser l'affichage)

            // Formatage de la date de facture
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            labelDateFacture.setText(dateFormat.format(facture.getDateFacture()));

            // Affichage des services dans la ListView
            listViewServices.getItems().clear();
            ArrayList<Service> services = facture.getServices();
            for (Service service : services) {
                listViewServices.getItems().add(String.valueOf(service));
            }

            // Affichage du montant total de la facture
            labelMontantTotal.setText(String.format("%.2f", facture.totalFacture())); // Affiche le total avec 2 décimales

            // Affichage si une remise a été appliquée
            labelAvecRemise.setText(facture.isAvecRemise() ? "Oui" : "Non");
        }
    }

    // Méthode pour fermer la fenêtre
    @FXML
    public void retour() {
        // Ferme la fenêtre actuelle
        Stage stage = (Stage) labelNumeroFacture.getScene().getWindow();
        stage.close();
    }
}
