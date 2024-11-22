package Controlleurs;

import Modeles.Exceptions.ArgumentInvalideException;
import Modeles.Exceptions.QuantiteNegatifException;
import Modeles.Stocks.Fourniture;
import Modeles.Personnes.Receptionniste;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class AfficherFournituresController {

    @FXML
    private TableView<Fourniture> tableFournitures;
    @FXML
    private TableColumn<Fourniture, Integer> colID;
    @FXML
    private TableColumn<Fourniture, String> colNom;
    @FXML
    private TableColumn<Fourniture, String> colDescription;
    @FXML
    private TableColumn<Fourniture, Double> colPrix;
    @FXML
    private TableColumn<Fourniture, Integer> colQuantite;

    @FXML
    public void initialize() {
        // Récupérer le réceptionniste fictif ou la liste des fournitures
        Receptionniste receptionniste = AjouterFournitureController.receptionnisteConnecte;

        if (receptionniste != null) {
            // Initialiser les colonnes et afficher les fournitures
            initialiserColonnes();
            afficherFournitures(receptionniste);
        }
    }

    private void initialiserColonnes() {
        // Configurer les colonnes pour afficher les propriétés des fournitures
        colID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdFourniture()).asObject());
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        colDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        colPrix.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());
        colQuantite.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantiteStock()).asObject());

        // Ajouter la colonne "Actions" avec des boutons de modification et de suppression
        TableColumn<Fourniture, Void> colActions = new TableColumn<>("Actions");

        colActions.setCellFactory(new Callback<TableColumn<Fourniture, Void>, TableCell<Fourniture, Void>>() {
            @Override
            public TableCell<Fourniture, Void> call(TableColumn<Fourniture, Void> param) {
                return new TableCell<Fourniture, Void>() {
                    private final Button modifyButton = new Button("Modifier");
                    private final Button deleteButton = new Button("Supprimer");

                    {
                        // Configurer les styles des boutons
                        modifyButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

                        modifyButton.setOnAction(event -> {
                            Fourniture fourniture = getTableView().getItems().get(getIndex());
                            modifierFourniture(fourniture);
                        });

                        deleteButton.setOnAction(event -> {
                            Fourniture fourniture = getTableView().getItems().get(getIndex());
                            supprimerFourniture(fourniture);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            VBox buttons = new VBox(5, modifyButton, deleteButton);
                            setGraphic(buttons);
                        }
                    }
                };
            }
        });

        tableFournitures.getColumns().add(colActions); // Ajouter la colonne "Actions"
    }

    private void afficherFournitures(Receptionniste receptionniste) {
        // Vider la TableView avant d'ajouter des fournitures
        tableFournitures.getItems().clear();

        // Ajouter les fournitures récupérées de la liste du réceptionniste
        tableFournitures.getItems().addAll(receptionniste.getListeFournitures());
    }

    private void modifierFourniture(Fourniture fourniture) {
        // Implémentation de la modification des données de la fourniture
        Dialog<Fourniture> dialog = new Dialog<>();
        dialog.setTitle("Modifier Fourniture");
        dialog.setHeaderText("Modifier les informations de la fourniture");

        // Champs pour les informations de la fourniture
        TextField nomField = new TextField(fourniture.getNom());
        TextField descriptionField = new TextField(fourniture.getDescription());
        TextField prixField = new TextField(String.valueOf(fourniture.getPrix()));
        TextField quantiteField = new TextField(String.valueOf(fourniture.getQuantiteStock()));

        dialog.getDialogPane().setContent(new VBox(10, nomField, descriptionField, prixField, quantiteField));

        ButtonType okButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                fourniture.setNom(nomField.getText());
                fourniture.setDescription(descriptionField.getText());
                try {
                    fourniture.setPrix(Double.parseDouble(prixField.getText()));
                } catch (ArgumentInvalideException e) {
                    throw new RuntimeException(e);
                }
                try {
                    fourniture.setQuantiteStock(Integer.parseInt(quantiteField.getText()));
                } catch (QuantiteNegatifException e) {
                    throw new RuntimeException(e);
                }

                tableFournitures.refresh();
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void supprimerFourniture(Fourniture fourniture) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Voulez-vous vraiment supprimer cette fourniture ?");
        confirmationAlert.setContentText(fourniture.getNom());

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Receptionniste receptionniste = AjouterFournitureController.receptionnisteConnecte;
                receptionniste.getListeFournitures().remove(fourniture);
                tableFournitures.getItems().remove(fourniture);
            }
        });
    }
}
