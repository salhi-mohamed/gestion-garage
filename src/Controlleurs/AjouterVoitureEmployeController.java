package Controlleurs;

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
        Employe selectedEmploye = employeComboBox.getSelectionModel().getSelectedItem();
        Voiture selectedVoiture = voitureTableView.getSelectionModel().getSelectedItem();

        if (selectedEmploye == null || selectedVoiture == null) {
            showAlert("Erreur", "Veuillez sélectionner un employé et une voiture.");
            return;
        }

       
    

        try {
            receptionnisteConnecte.ajouterVoitureMecLaveur(selectedEmploye.get_id(), selectedVoiture.getImmatriculation());

         

            // Mettre à jour le TableView
            voitureTableView.refresh(); // Rafraîchir le tableau pour afficher la nouvelle quantité

            showAlert("Succès", "La voiture a été ajoutée à l'employé.");
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
