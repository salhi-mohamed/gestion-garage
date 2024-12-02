package Controlleurs;

import Modeles.Personnes.*;
import Modeles.Exceptions.EmployeExistantException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

public class AjouterEmployeAuChefController {

    private Receptionniste receptionnisteConnecte;

    @FXML
    private ComboBox<Employe> chefComboBox;

    @FXML
    private TableView<Employe> employeTableView;

    @FXML
    private TableColumn<Employe, String> colNom;

    @FXML
    private TableColumn<Employe, String> colPrenom;

    @FXML
 
public void initialize() {
    receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

    if (receptionnisteConnecte == null) {
        throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
    }

    // Remplir le ComboBox avec les chefs
    chefComboBox.getItems().setAll(receptionnisteConnecte.getListeEmployes().stream()
        .filter(employe -> employe instanceof Chef)
        .toList());

    // Utiliser un CellFactory pour afficher uniquement le nom et le prénom des chefs dans le ComboBox
    chefComboBox.setCellFactory(param -> new ListCell<Employe>() {
        @Override
        protected void updateItem(Employe item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.get_nom() + " " + item.get_prenom()); // Afficher nom et prénom du chef
            }
        }
    });

    // Afficher le nom et prénom du chef sélectionné dans le ComboBox
    chefComboBox.setButtonCell(new ListCell<Employe>() {
        @Override
        protected void updateItem(Employe item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.get_nom() + " " + item.get_prenom()); // Afficher nom et prénom du chef
            }
        }
    });

    // Remplir le TableView avec les employés qui ne sont pas des chefs
    employeTableView.getItems().setAll(receptionnisteConnecte.getListeEmployes().stream()
        .filter(employe -> !(employe instanceof Chef))
        .toList());

    // Personnaliser les colonnes du TableView pour afficher le nom et le prénom des employés
    colNom.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get_nom()));
    colPrenom.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get_prenom()));
}


    @FXML
    public void ajouterEmployeAuChef() {
        // Récupérer le chef sélectionné dans le ComboBox
        Employe selectedChef = chefComboBox.getSelectionModel().getSelectedItem();

        // Récupérer l'employé sélectionné dans le TableView
        Employe selectedEmploye = employeTableView.getSelectionModel().getSelectedItem();

        // Vérifier si un chef et un employé ont été sélectionnés
        if (selectedChef == null || selectedEmploye == null) {
            showAlert("Erreur", "Veuillez sélectionner un chef et un employé.");
            return;
        }

        // Vérifier que le chef sélectionné est bien un chef
        if (!(selectedChef instanceof Chef)) {
            showAlert("Erreur", "Le chef sélectionné n'est pas un chef.");
            return;
        }

        try {
            // Appeler la méthode pour ajouter l'employé à l'équipe du chef
            receptionnisteConnecte.ajouterEmployeAuChef(selectedChef.get_id(), selectedEmploye.get_id());

            // Rafraîchir le tableau pour refléter les changements
            employeTableView.refresh();

            // Afficher un message de succès
            showAlert("Succès", "L'employé a été ajouté à l'équipe du chef.");
            receptionnisteConnecte.afficher_tous_les_employes();

        } catch (EmployeExistantException e) {
            // Gérer le cas où l'employé est déjà dans l'équipe du chef
            showAlert("Erreur", "Cet employé est déjà dans l'équipe du chef.");
        } catch (Exception e) {
            // Gérer les autres erreurs inattendues
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout de l'employé au chef.");
            e.printStackTrace(); // Afficher les détails pour le débogage
        }
    }

    // Méthode pour afficher des alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
