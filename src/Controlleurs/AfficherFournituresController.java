package Controlleurs;

import Modeles.Exceptions.ArgumentInvalideException;
import Modeles.Exceptions.QuantiteNegatifException;
import Modeles.Stocks.Fourniture;
import Modeles.Personnes.Receptionniste;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AfficherFournituresController {

    @FXML
    private TableView<Fourniture> tableFournitures;
    @FXML
    private TableColumn<Fourniture, Integer> colID;
    @FXML
    private TableColumn<Fourniture, String> colNom;
    @FXML
    private TableColumn<Fourniture, Integer> colQuantite;
    @FXML
    private TableColumn<Fourniture, Double> colPrix;
    @FXML
    private TableColumn<Fourniture, String> colActions;

    @FXML
    public void initialize() {
        // Récupérer le réceptionniste connecté depuis MenuPrincipaleController
        Receptionniste receptionniste = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionniste != null) {
            initialiserColonnes();
            afficherFournitures(receptionniste);

            // Charger les fichiers CSS
            Scene scene = tableFournitures.getScene();
            if (scene != null) {
                scene.getStylesheets().add(getClass().getResource("/Vues/styles.css").toExternalForm());
                scene.getStylesheets().add(getClass().getResource("/Vues/actions-buttons.css").toExternalForm());
            }
        }
    }

    private void initialiserColonnes() {
        colID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdFourniture()).asObject());
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        colQuantite.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantiteStock()).asObject());
        colPrix.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());

        // Configurer la colonne d'actions avec les boutons Modifier et Supprimer
        colActions.setCellFactory(column -> new TableCell<Fourniture, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionBox = new HBox(10); // Espacement entre les boutons
                    Button btnModifier = new Button("Modifier");
                    Button btnSupprimer = new Button("Supprimer");

                    // Appliquer les classes CSS
                    btnModifier.getStyleClass().add("modifier");
                    btnSupprimer.getStyleClass().add("supprimer");

                    // Actions des boutons
                    btnModifier.setOnAction(e -> {
                        Fourniture fourniture = getTableView().getItems().get(getIndex());
                        modifierFourniture(fourniture);
                    });

                    btnSupprimer.setOnAction(e -> {
                        Fourniture fourniture = getTableView().getItems().get(getIndex());
                        supprimerFourniture(fourniture);
                    });

                    actionBox.getChildren().addAll(btnModifier, btnSupprimer);
                    setGraphic(actionBox);
                }
            }
        });
    }

    private void afficherFournitures(Receptionniste receptionniste) {
        tableFournitures.getItems().clear();
        tableFournitures.getItems().addAll(receptionniste.getListeFournitures());
    }

    private void modifierFourniture(Fourniture fourniture) {
        // Créer la fenêtre de modification
        Stage modificationStage = new Stage();
        modificationStage.setTitle("Modifier la fourniture");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-padding: 20px;");

        Label lblNom = new Label("Nom:");
        TextField txtNom = new TextField(fourniture.getNom());
        txtNom.setPromptText("Nom de la fourniture");

        Label lblQuantite = new Label("Quantité:");
        TextField txtQuantite = new TextField(String.valueOf(fourniture.getQuantiteStock()));
        txtQuantite.setPromptText("Quantité");

        txtQuantite.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtQuantite.setText(oldValue);
            }
        });

        Label lblPrix = new Label("Prix:");
        TextField txtPrix = new TextField(String.format("%.2f", fourniture.getPrix()));
        txtPrix.setPromptText("Prix");

        txtPrix.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtPrix.setText(oldValue);
            }
        });

        Button btnSave = new Button("Sauvegarder");
        Button btnCancel = new Button("Annuler");

        btnSave.setOnAction(e -> {
            try {
                fourniture.setNom(txtNom.getText());
                fourniture.setQuantiteStock(Integer.parseInt(txtQuantite.getText()));
                fourniture.setPrix(Double.parseDouble(txtPrix.getText()));
                tableFournitures.refresh();
                modificationStage.close();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Données invalides");
                alert.setContentText("Veuillez vérifier les valeurs saisies.");
                alert.showAndWait();
            } catch (QuantiteNegatifException | ArgumentInvalideException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnCancel.setOnAction(e -> modificationStage.close());

        grid.add(lblNom, 0, 0);
        grid.add(txtNom, 1, 0);
        grid.add(lblQuantite, 0, 1);
        grid.add(txtQuantite, 1, 1);
        grid.add(lblPrix, 0, 2);
        grid.add(txtPrix, 1, 2);

        HBox hboxButtons = new HBox(20);
        hboxButtons.setStyle("-fx-alignment: center;");
        hboxButtons.getChildren().addAll(btnSave, btnCancel);

        grid.add(hboxButtons, 0, 3, 2, 1);

        Scene scene = new Scene(grid);
        scene.getStylesheets().add(getClass().getResource("/Vues/styles.css").toExternalForm());

        modificationStage.setScene(scene);
        modificationStage.setWidth(600);
        modificationStage.setHeight(400);
        modificationStage.show();
    }

    private void supprimerFourniture(Fourniture fourniture) {
        Receptionniste receptionniste = MenuPrincipaleController.receptionnisteConnecte;
        if (receptionniste != null) {
            receptionniste.getListeFournitures().remove(fourniture);
            tableFournitures.getItems().remove(fourniture);
        }
    }
}
