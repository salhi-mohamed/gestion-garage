/*package Controlleurs;

import Modeles.Exceptions.VoitureExistanteDejaPourLavMecException;
import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Employe;
import Modeles.Personnes.Receptionniste;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AjouterVoitureEmployeController {

    private Receptionniste receptionnisteConnecte;

    @FXML
    private ComboBox<Employe> employeComboBox;

    @FXML
    private TableView<Voiture> voitureTableView;

    @FXML
    private TableColumn<Voiture, String> marqueColumn;

    @FXML
    private TableColumn<Voiture, String> modeleColumn;

    @FXML
    private TableColumn<Voiture, String> immatriculationColumn;

    @FXML
    public void initialize() {
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }

        employeComboBox.getItems().setAll(receptionnisteConnecte.getListeEmployes());

        employeComboBox.setCellFactory(param -> new javafx.scene.control.ListCell<Employe>() {
            @Override
            protected void updateItem(Employe employe, boolean empty) {
                super.updateItem(employe, empty);
                if (empty || employe == null) {
                    setText(null);
                } else {
                    setText(employe.get_prenom() + " " + employe.get_nom());
                }
            }
        });

        employeComboBox.setButtonCell(new javafx.scene.control.ListCell<Employe>() {
            @Override
            protected void updateItem(Employe employe, boolean empty) {
                super.updateItem(employe, empty);
                if (empty || employe == null) {
                    setText(null);
                } else {
                    setText(employe.get_prenom() + " " + employe.get_nom());
                }
            }
        });

        // Configure les colonnes du TableView pour utiliser les propriétés JavaFX
        marqueColumn.setCellValueFactory(cellData -> cellData.getValue().marqueProperty());
        modeleColumn.setCellValueFactory(cellData -> cellData.getValue().modeleProperty());
        immatriculationColumn.setCellValueFactory(cellData -> cellData.getValue().immatriculationProperty());

        voitureTableView.getItems().setAll(receptionnisteConnecte.get_liste_voitures());
    }

    @FXML
    public void ajouterVoitureEmploye() {
        Employe selectedEmploye = employeComboBox.getSelectionModel().getSelectedItem();
        Voiture selectedVoiture = voitureTableView.getSelectionModel().getSelectedItem();

        if (selectedEmploye == null || selectedVoiture == null) {
            showAlert("Erreur", "Veuillez sélectionner un employé et une voiture.");
            return;
        }

        try {
            receptionnisteConnecte.ajouterVoitureMecLaveur(selectedEmploye.get_id(), selectedVoiture.get_immatriculation());
            voitureTableView.refresh();
            showAlert("Succès", "La voiture a été ajoutée à l'employé.");
        } catch (VoitureExistanteDejaPourLavMecException e) {
            showAlert("Erreur", "Cette voiture existe déjà dans l'historique de l'employé sélectionné.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout de la voiture à l'employé.");
            e.printStackTrace();
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
*/