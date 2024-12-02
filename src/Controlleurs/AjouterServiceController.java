/*package Controlleurs;

import Modeles.Gestion_Service.Service;
import Modeles.Gestion_Service.Rendez_vous;
import Modeles.Personnes.Employe;
import Modeles.Personnes.Receptionniste;
import Modeles.Stocks.Piece_Rechange;
import Modeles.Stocks.Fourniture;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

import java.util.List;

public class AjouterServiceController {

    @FXML
    private ComboBox<Rendez_vous> rendezVousComboBox;

    @FXML
    private ComboBox<String> typeServiceComboBox;

    @FXML
    private TableView<Employe> employesTableView;

    @FXML
    private TableColumn<Employe, String> colNom;

    @FXML
    private TableColumn<Employe, String> colPrenom;

    @FXML
    private TableView<Piece_Rechange> piecesTableView;

    @FXML
    private TableColumn<Piece_Rechange, String> colPieceNom;

    @FXML
    private TableColumn<Piece_Rechange, Double> colPiecePrix;

    @FXML
    private TableView<Fourniture> fournituresTableView;

    @FXML
    private TableColumn<Fourniture, String> colFournitureNom;

    @FXML
    private TableColumn<Fourniture, Double> colFourniturePrix;

    @FXML
    private TextField coutServiceField;

    private Receptionniste receptionnisteConnecte;
    private static int dernierIdService = 0; // Variable statique pour générer des IDs uniques

    @FXML
    public void initialize() {
        // Initialiser le réceptionniste connecté
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }

        // Charger les données pour les ComboBox et les TableView
        List<Rendez_vous> rendezVousList = receptionnisteConnecte.getListeRendezVous();
        List<Employe> employesList = receptionnisteConnecte.getListeEmployes();
        List<Piece_Rechange> piecesList = receptionnisteConnecte.getListPiecesRechange();
        List<Fourniture> fournituresList = receptionnisteConnecte.getListeFournitures();

        rendezVousComboBox.getItems().setAll(rendezVousList);

        employesTableView.getItems().setAll(employesList);
        colNom.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get_nom()));
        colPrenom.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get_prenom()));

        piecesTableView.getItems().setAll(piecesList);
        colPieceNom.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        colPiecePrix.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());

        fournituresTableView.getItems().setAll(fournituresList);
        colFournitureNom.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        colFourniturePrix.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());

        // Initialiser la liste des types de service
        typeServiceComboBox.setItems(FXCollections.observableArrayList(
                "Révision", "Réparation", "Entretien", "Diagnostic", "Lavage", "Vidange"
        ));
    }

    @FXML
    public void ajouterService() {
        // Valider les champs
        Rendez_vous rendezVous = rendezVousComboBox.getSelectionModel().getSelectedItem();
        String typeService = typeServiceComboBox.getSelectionModel().getSelectedItem();
        List<Employe> employesSelectionnes = employesTableView.getSelectionModel().getSelectedItems();
        List<Piece_Rechange> piecesSelectionnees = piecesTableView.getSelectionModel().getSelectedItems();
        List<Fourniture> fournituresSelectionnees = fournituresTableView.getSelectionModel().getSelectedItems();
        String coutStr = coutServiceField.getText();

        if (rendezVous == null || typeService == null || employesSelectionnes.isEmpty()
                || piecesSelectionnees.isEmpty() || fournituresSelectionnees.isEmpty() || coutStr.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            double cout = Double.parseDouble(coutStr);
            dernierIdService++;
            // Créer un nouveau service
            Service nouveauService = new Service(typeService, cout, rendezVous, employesSelectionnes, piecesSelectionnees, fournituresSelectionnees);

            // Ajouter le service via le réceptionniste
            receptionnisteConnecte.creerService(nouveauService);

            // Afficher un message de succès
            showAlert("Succès", "Service ajouté avec succès.");

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le coût doit être un nombre valide.");
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