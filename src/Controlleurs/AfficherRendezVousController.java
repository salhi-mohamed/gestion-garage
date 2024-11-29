package Controlleurs;

import Modeles.Gestion_Service.Rendez_vous;
import Modeles.Personnes.Client;
import Modeles.Gestion_Service.Voiture;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AfficherRendezVousController {

    @FXML
    private TableView<Rendez_vous> tableViewRendezVous;
    @FXML
    private TableColumn<Rendez_vous, String> colClient;
    @FXML
    private TableColumn<Rendez_vous, String> colVoiture;
    @FXML
    private TableColumn<Rendez_vous, String> colDate;

    @FXML
    private TableColumn<Rendez_vous, String> colDescription;
    @FXML
    private TableColumn<Rendez_vous, String> colStatut;
    @FXML
    private Button btnFermer;

    private List<Rendez_vous> listeRendezVous;

    // Méthode pour initialiser le tableau avec les données
    @FXML
    public void initialize() {
        // Initialisation des colonnes de la table
        colClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        colVoiture.setCellValueFactory(new PropertyValueFactory<>("voiture"));
        colDate.setCellValueFactory(cellData -> {
            // Convertir LocalDate en String
            return new javafx.beans.property.SimpleStringProperty(
                    cellData.getValue().getDate_rendez_vous().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            );
        });
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description_rendez_vous"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Récupérer les rendez-vous à afficher
        listeRendezVous = MenuPrincipaleController.receptionnisteConnecte.getListeRendezVous(); // Récupérer les rendez-vous

        if (listeRendezVous == null || listeRendezVous.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucun Rendez-vous");
            alert.setHeaderText("Aucun rendez-vous trouvé");
            alert.setContentText("Aucun rendez-vous n'a été ajouté.");
            alert.showAndWait();
        } else {
            // Ajouter les rendez-vous dans le tableau
            tableViewRendezVous.getItems().addAll(listeRendezVous);
        }
    }

    // Méthode pour fermer la fenêtre
    @FXML
    private void fermer() {
        Stage stage = (Stage) btnFermer.getScene().getWindow();
        stage.close();
    }
}
