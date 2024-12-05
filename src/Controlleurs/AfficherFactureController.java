package Controlleurs;

import Modeles.Gestion_Service.Facture;
import Modeles.Gestion_Service.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.List;

public class AfficherFactureController {

    @FXML
    private Label labelNumeroFacture;
    @FXML
    private Label labelClient;
    @FXML
    private Label labelDateFacture;
    @FXML
    private TableView<Service> tableViewServices;  // TableView pour afficher les services
    @FXML
    private TableColumn<Service, String> colService;
    @FXML
    private TableColumn<Service, String> colPieceRechange;
    @FXML
    private TableColumn<Service, String> colEmploye;
    @FXML
    private TableColumn<Service, Double> colCout;
    @FXML
    private Label labelMontantTotal;
    @FXML
    private Label labelAvecRemise;

    private Facture facture;
    private List<Facture> listeFactures;

    // Méthode pour initialiser le contrôleur et récupérer les factures à afficher
    public void initialize() {
        // Initialisation des colonnes
        colService.setCellValueFactory(new PropertyValueFactory<>("description")); // Description du service
        colPieceRechange.setCellValueFactory(new PropertyValueFactory<>("piecesUtilisees")); // Pièces utilisées
        colEmploye.setCellValueFactory(new PropertyValueFactory<>("effecteurs")); // Employé(s) effectuant le service
        colCout.setCellValueFactory(new PropertyValueFactory<>("cout")); // Coût du service

        // Récupérer la liste des factures
        listeFactures = MenuPrincipaleController.receptionnisteConnecte.getListeFactures(); // Récupérer les factures

        if (listeFactures == null || listeFactures.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucune Facture");
            alert.setHeaderText("Aucune facture trouvée");
            alert.setContentText("Aucune facture n'a été ajoutée.");
            alert.showAndWait();
        } else {
            // Si on a des factures, afficher la première facture (ou tu peux ajuster selon ton besoin)
            facture = listeFactures.get(0); // On prend ici la première facture comme exemple
            afficherFacture();
        }
    }

    // Méthode pour afficher les informations de la facture dans l'interface
    private void afficherFacture() {
        if (facture != null) {
            // Affichage du numéro de facture
            labelNumeroFacture.setText(String.valueOf(facture.getNumeroFacture()));

            // Affichage du client
            labelClient.setText(facture.getClient().toString()); // Afficher les détails du client

            // Formatage de la date de facture
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            labelDateFacture.setText(dateFormat.format(facture.getDateFacture()));

            // Ajouter les services à la TableView
            tableViewServices.getItems().clear();
            tableViewServices.getItems().addAll(facture.getServices());

            // Affichage du montant total de la facture
            labelMontantTotal.setText(String.format("%.2f", facture.totalFacture()));

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
