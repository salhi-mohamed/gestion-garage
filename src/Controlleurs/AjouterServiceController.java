package Controlleurs;

import Modeles.Gestion_Service.Rendez_vous;
import Modeles.Gestion_Service.Service;
import Modeles.Personnes.Employe;
import Modeles.Personnes.Receptionniste;
import Modeles.Stocks.Fourniture;
import Modeles.Stocks.Piece_Rechange;
import Modeles.TypeService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class AjouterServiceController {

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField coutField;
    @FXML
    private ComboBox<Rendez_vous> rendezVousComboBox;
    @FXML
    private ComboBox<String> serviceTypeComboBox;
    @FXML
    private ComboBox<Piece_Rechange> pieceRechangeComboBox;
    @FXML
    private ComboBox<Employe> employeComboBox;

    private Receptionniste receptionnisteConnecte;
    private int dernierIdService;

    @FXML
    public void initialize() {
        receptionnisteConnecte = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionnisteConnecte == null) {
            throw new IllegalStateException("Aucun réceptionniste connecté trouvé !");
        }

        // Remplir les ComboBox
        rendezVousComboBox.setItems(FXCollections.observableArrayList(receptionnisteConnecte.getListeRendezVous()));
        pieceRechangeComboBox.setItems(FXCollections.observableArrayList(receptionnisteConnecte.getListePieces()));
        employeComboBox.setItems(FXCollections.observableArrayList(receptionnisteConnecte.getListeEmployes()));

        // Remplir le ComboBox de type de service avec des valeurs prédéfinies
        serviceTypeComboBox.setItems(FXCollections.observableArrayList(
                Arrays.stream(TypeService.values())
                        .map(TypeService::getName)
                        .toList()
        ));

        configureComboBox(rendezVousComboBox);
        configureComboBox(pieceRechangeComboBox);
        configureComboBox(employeComboBox);

        dernierIdService = receptionnisteConnecte.getListeServices().size();
    }

    private <T> void configureComboBox(ComboBox<T> comboBox) {
        comboBox.setCellFactory(param -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });
        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });
    }

    @FXML
    public void handleAjouter(ActionEvent event) {
        String description = descriptionField.getText();
        String coutText = coutField.getText();
        Rendez_vous rendezVous = rendezVousComboBox.getSelectionModel().getSelectedItem();
        String serviceType = serviceTypeComboBox.getSelectionModel().getSelectedItem();
        Piece_Rechange pieceRechange = pieceRechangeComboBox.getSelectionModel().getSelectedItem();
        Employe employe = employeComboBox.getSelectionModel().getSelectedItem();

        if (description.isEmpty() || coutText.isEmpty() || rendezVous == null || serviceType == null || pieceRechange == null || employe == null) {
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
            ArrayList<Employe> effecteurs = new ArrayList<>();
            effecteurs.add(employe);

            ArrayList<Piece_Rechange> piecesUtilisees = new ArrayList<>();
            piecesUtilisees.add(pieceRechange);

            Service nouveauService = new Service(
                    dernierIdService,
                    serviceType + ": " + description, // Inclure le type dans la description
                    cout,
                    effecteurs,
                    null,
                    null,
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
