package Controlleurs;

import Modeles.Exceptions.VoitureExistanteDejaPourLavMecException;
import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Employe;
import Modeles.Personnes.Receptionniste;
import Modeles.Stocks.Fourniture;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AjouterVoitureEmployeController {

    private Receptionniste receptionnisteConnecte; // Référence au réceptionniste connecté

    @FXML
    private ComboBox<Employe> employeComboBox;
    
    @FXML
    private TableView<Voiture> voitureTableView;

    @FXML
    private TableColumn<Voiture, String> modeleColumn;
    
    @FXML
    private TableColumn<Voiture, String> marqueColumn;

    @FXML
    private TableColumn<Voiture, String> immatriculationColumn;

    @FXML

 
    public void initialize() {
        // Récupérer le réceptionniste connecté depuis MenuPrincipaleController
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }

        employeComboBox.getItems().setAll(receptionnisteConnecte.getListeEmployes());

        // Personnaliser l'affichage du ComboBox pour afficher nom et prénom
        employeComboBox.setCellFactory(param -> new javafx.scene.control.ListCell<Employe>() {
            @Override
            protected void updateItem(Employe employe, boolean empty) {
                super.updateItem(employe, empty);
                if (empty || employe == null) {
                    setText(null);
                } else {
                    setText(employe.get_prenom() + " " + employe.get_nom()); // Affiche prénom et nom
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
                    setText(employe.get_prenom() + " " + employe.get_nom()); // Affiche prénom et nom
                }
            }
        });

        marqueColumn.setCellValueFactory(new PropertyValueFactory<>("Marque"));
        modeleColumn.setCellValueFactory(new PropertyValueFactory<>("Modèle"));
        immatriculationColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

        voitureTableView.getItems().setAll(receptionnisteConnecte.get_liste_voitures());
    }

    @FXML
   

public void ajouterVoitureEmploye() {
    // Récupérer les sélections dans l'interface
    Employe selectedEmploye = employeComboBox.getSelectionModel().getSelectedItem();
    Voiture selectedVoiture = voitureTableView.getSelectionModel().getSelectedItem();

    // Vérifier si un employé et une voiture ont été sélectionnés
    if (selectedEmploye == null || selectedVoiture == null) {
        showAlert("Erreur", "Veuillez sélectionner un employé et une voiture.");
        return; // Terminer si aucune sélection
    }

    try {
        // Appeler la méthode pour associer la voiture à l'employé
        receptionnisteConnecte.ajouterVoitureMecLaveur(selectedEmploye.get_id(), selectedVoiture.getImmatriculation());

        // Rafraîchir le tableau pour refléter les changements
        voitureTableView.refresh();

        // Afficher un message de succès
        showAlert("Succès", "La voiture a été ajoutée à l'employé.");
    } catch (VoitureExistanteDejaPourLavMecException e) {
        // Gérer le cas où la voiture existe déjà dans l'historique de l'employé
        showAlert("Erreur", "Cette voiture existe déjà dans l'historique de l'employé sélectionné.");
    } catch (Exception e) {
        // Gérer les autres erreurs inattendues
        showAlert("Erreur", "Une erreur est survenue lors de l'ajout de la voiture à l'employé.");
        e.printStackTrace(); // Afficher les détails pour le débogage
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
