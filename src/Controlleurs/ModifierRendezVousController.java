package Controlleurs;

import Modeles.Gestion_Service.Rendez_vous;
import Modeles.Personnes.Receptionniste;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ModifierRendezVousController {

    @FXML
    private TableView<Rendez_vous> tableViewRendezVous;

    @FXML
    private TableColumn<Rendez_vous, String> columnClient;

    @FXML
    private TableColumn<Rendez_vous, String> columnVoiture;

    @FXML
    private TableColumn<Rendez_vous, String> columnDate;

    @FXML
    private TableColumn<Rendez_vous, String> columnHeure;

    @FXML
    private TableColumn<Rendez_vous, String> columnStatut;

    @FXML
    private Button buttonModifier;

    private ObservableList<Rendez_vous> rendezVous;
    private Receptionniste receptionnisteConnecte;

    public void initialize() {
        // Récupérer le réceptionniste connecté
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            showAlert("Erreur", "Aucun réceptionniste connecté.");
            return;
        }

        // Charger la liste des rendez-vous
        rendezVous = FXCollections.observableArrayList(receptionnisteConnecte.getListeRendezVous());

        // Configurer les colonnes
        columnClient.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getClient().get_nom()));
        columnVoiture.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getVoiture().getMarque() + " " + cellData.getValue().getVoiture().getModele()));

        // Formatage de la date en string
        columnDate.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    cellData.getValue().getDate_rendez_vous().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            );
        });


        columnStatut.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getStatut().toString()));

        // Ajouter les rendez-vous à la TableView
        tableViewRendezVous.setItems(rendezVous);
    }

    @FXML
    private void ouvrirFormulaireModification() {
        // Récupérer le rendez-vous sélectionné
        Rendez_vous selectedRendezVous = tableViewRendezVous.getSelectionModel().getSelectedItem();
        if (selectedRendezVous == null) {
            showAlert("Erreur", "Veuillez sélectionner un rendez-vous à modifier.");
            return;
        }

        // Ouvrir le formulaire de modification (via une nouvelle scène ou une fenêtre modale)
        afficherFormulaireModification(selectedRendezVous);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherFormulaireModification(Rendez_vous rendezVous) {
        // Charger la nouvelle scène ou afficher une boîte de dialogue pour la modification
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/FormulaireModificationRendezVous.fxml"));
            Parent root = loader.load();

            // Passer les informations du rendez-vous au contrôleur du formulaire
            FormulaireModificationRendezVousController controller = loader.getController();
            controller.setRendezVous(rendezVous);

            // Afficher la fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Rendez-vous");
            stage.initModality(Modality.APPLICATION_MODAL); // Bloque la fenêtre principale jusqu'à la fermeture
            stage.showAndWait();

            // Rafraîchir la TableView après modification
            tableViewRendezVous.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le formulaire de modification.");
        }
    }
}
