package Controlleurs;

import Modeles.Gestion_Service.Rendez_vous;
import Modeles.Gestion_Service.Service;
import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Client;
import Modeles.Personnes.Employe;
import Modeles.Personnes.Receptionniste;
import Modeles.Stocks.Fourniture;
import Modeles.Stocks.Piece_Rechange;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AjouterServiceController {

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField coutField;
    @FXML
    private ComboBox<Rendez_vous> rendezVousComboBox;
    @FXML
    private ComboBox<Fourniture> fournitureComboBox;
    @FXML
    private ComboBox<Piece_Rechange> pieceRechangeComboBox;
    @FXML
    private ComboBox<Employe> employeComboBox;

    private Receptionniste receptionnisteConnecte;
    private int dernierIdService;

    @FXML
    public void initialize() {
        // Initialisation du réceptionniste connecté
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }

        // Remplissage des ComboBox avec les données
        rendezVousComboBox.setItems(FXCollections.observableArrayList(receptionnisteConnecte.getListeRendezVous()));
        fournitureComboBox.setItems(FXCollections.observableArrayList(receptionnisteConnecte.getListeFournitures()));
        pieceRechangeComboBox.setItems(FXCollections.observableArrayList(receptionnisteConnecte.getListePieces()));
        employeComboBox.setItems(FXCollections.observableArrayList(receptionnisteConnecte.getListeEmployes()));

        // Configuration des ComboBox pour un affichage personnalisé
        configureComboBox(rendezVousComboBox);
        configureComboBox(fournitureComboBox);
        configureComboBox(pieceRechangeComboBox);
        configureComboBox(employeComboBox);

        // Initialisation du dernier ID pour les services
        dernierIdService = receptionnisteConnecte.getListeServices().size();
    }

    private <T> void configureComboBox(ComboBox<T> comboBox) {
        comboBox.setCellFactory(param -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });

        comboBox.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
    }

    @FXML
    public void handleAjouter(ActionEvent event) {
        String description = descriptionField.getText();
        String coutText = coutField.getText();
        Rendez_vous rendezVous = rendezVousComboBox.getSelectionModel().getSelectedItem();
        Fourniture fourniture = fournitureComboBox.getSelectionModel().getSelectedItem();
        Piece_Rechange pieceRechange = pieceRechangeComboBox.getSelectionModel().getSelectedItem();
        Employe employe = employeComboBox.getSelectionModel().getSelectedItem();

        if (description.isEmpty() || coutText.isEmpty() || rendezVous == null || fourniture == null || pieceRechange == null || employe == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        double cout;
        try {
            cout = Double.parseDouble(coutText);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le coût doit être un nombre valide.");
            return;
        }

        dernierIdService++;
        try {
            // Création d'un nouveau service
            ArrayList<Employe> effecteurs = new ArrayList<>();
            effecteurs.add(employe);

            ArrayList<Piece_Rechange> piecesUtilisees = new ArrayList<>();
            piecesUtilisees.add(pieceRechange);

            Service nouveauService = new Service(
                    dernierIdService,
                    description,
                    cout,
                    effecteurs,
                    null, // Pas besoin de client ici
                    null, // Pas besoin de voiture ici
                    rendezVous,
                    piecesUtilisees
            );

            receptionnisteConnecte.getListeServices().add(nouveauService);
            showAlert("Succès", "Le service a été ajouté avec succès !");
            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) descriptionField.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleAnnuler(ActionEvent event) {
        closeWindow();
    }
}
