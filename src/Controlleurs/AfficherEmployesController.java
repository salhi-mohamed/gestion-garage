package Controlleurs;

import Modeles.Personnes.Employe;
import Modeles.Personnes.Mecanicien;
import Modeles.Personnes.Laveur;
import Modeles.Personnes.Chef;
import Modeles.Personnes.Expertise;
import Modeles.Personnes.Receptionniste;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AfficherEmployesController {

    @FXML
    private TableView<Employe> tableEmployes;
    @FXML
    private TableColumn<Employe, Integer> colID;
    @FXML
    private TableColumn<Employe, String> colNom;
    @FXML
    private TableColumn<Employe, String> colPrenom;
    @FXML
    private TableColumn<Employe, String> colType;
    @FXML
    private TableColumn<Employe, String> colSpecialite;
    @FXML
    private TableColumn<Employe, String> colExpertise;
    @FXML
private TableColumn<Employe, String> colSalaire;  // Déclaration de la colonne Salaire

    @FXML
    private TableColumn<Employe, String> colActions;

    @FXML
    public void initialize() {
        Receptionniste receptionniste = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionniste != null) {
            initialiserColonnes();
            afficherEmployes(receptionniste);
        }
    }

 private void initialiserColonnes() {
    colID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().get_id()).asObject());
    colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_nom()));
    colPrenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_prenom()));

    // Type d'employé (Mécanicien, Laveur, Chef)
    colType.setCellValueFactory(cellData -> {
        Employe employe = cellData.getValue();
        if (employe instanceof Mecanicien) return new SimpleStringProperty("Mécanicien");
        if (employe instanceof Laveur) return new SimpleStringProperty("Laveur");
        if (employe instanceof Chef) return new SimpleStringProperty("Chef");
        return new SimpleStringProperty("Inconnu");
    });

    // Spécialité (Laveur ou Mécanicien)
    colSpecialite.setCellValueFactory(cellData -> {
        Employe employe = cellData.getValue();
        if (employe instanceof Laveur) {
            Laveur laveur = (Laveur) employe;
            StringBuilder specialite = new StringBuilder();
            if (laveur.get_specialise_interieur()) specialite.append("Intérieur ");
            if (laveur.get_specialise_exterieur()) specialite.append("Extérieur ");
            return new SimpleStringProperty(specialite.toString().trim());
        }
        if (employe instanceof Mecanicien) {
            return new SimpleStringProperty(((Mecanicien) employe).get_specialite());
        }
        return new SimpleStringProperty("-");
    });

    // Expertise (seulement pour Mécanicien)
    colExpertise.setCellValueFactory(cellData -> {
        Employe employe = cellData.getValue();
        if (employe instanceof Mecanicien) {
            Mecanicien mecanicien = (Mecanicien) employe;
            return new SimpleStringProperty(mecanicien.get_expertise().toString());
        }
        return new SimpleStringProperty("-");
    });

    // Colonne Salaire
    colSalaire.setCellValueFactory(cellData -> {
        Employe employe = cellData.getValue();
        return new SimpleStringProperty(String.format("%.2f", employe.getSalaire()));  // Affichage du salaire avec deux décimales
    });

    // Colonne d'actions
    colActions.setCellFactory(column -> new TableCell<Employe, String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setGraphic(null);
            } else {
                HBox actionBox = new HBox(10);
                Button btnModifier = new Button("Modifier");
                Button btnSupprimer = new Button("Supprimer");

                btnModifier.setOnAction(e -> {
                    Employe employe = getTableView().getItems().get(getIndex());
                    modifierEmploye(employe);
                });

                btnSupprimer.setOnAction(e -> {
                    Employe employe = getTableView().getItems().get(getIndex());
                    supprimerEmploye(employe);
                });

                actionBox.getChildren().addAll(btnModifier, btnSupprimer);
                setGraphic(actionBox);
            }
        }
    });
}


    private void afficherEmployes(Receptionniste receptionniste) {
        tableEmployes.getItems().clear();
   tableEmployes.getItems().addAll(receptionniste.getListeEmployes());
    }

  private void modifierEmploye(Employe employe) {
    // Créer la fenêtre de modification
    Stage modificationStage = new Stage();
    modificationStage.setTitle("Modifier l'employé");

    // Créer le GridPane pour la fenêtre de modification
    GridPane grid = new GridPane();
    grid.setVgap(10); // Espacement vertical
    grid.setHgap(10); // Espacement horizontal
    grid.setStyle("-fx-padding: 20px;");

    // Créer les labels et champs de texte pour les détails de l'employé
    Label lblNom = new Label("Nom:");
    TextField txtNom = new TextField(employe.get_nom());
    txtNom.setPromptText("Nom de l'employé");

    Label lblPrenom = new Label("Prénom:");
    TextField txtPrenom = new TextField(employe.get_prenom());
    txtPrenom.setPromptText("Prénom de l'employé");

    Label lblSalaire = new Label("Salaire:");
    TextField txtSalaire = new TextField(String.valueOf(employe.getSalaire()));
    txtSalaire.setPromptText("Salaire de l'employé");

    // Ajouter un écouteur pour ne permettre que les chiffres dans le champ Salaire
    txtSalaire.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
            txtSalaire.setText(oldValue);
        }
    });

    // Créer des ComboBox pour la spécialité et l'expertise selon le type d'employé
    ComboBox<String> comboSpecialite = new ComboBox<>();
    ComboBox<Expertise> comboExpertise = new ComboBox<>();

    // Afficher la spécialité et l'expertise selon le type d'employé
    if (employe instanceof Mecanicien) {
        Mecanicien mecanicien = (Mecanicien) employe;

        // Remplir la ComboBox pour la spécialité d'un Mécanicien
        comboSpecialite.getItems().addAll("Moteur", "Electricité", "Carrosserie", "Peinture", "Freinage");
        comboSpecialite.setValue(mecanicien.get_specialite()); // Pré-remplir avec la spécialité existante

        // Remplir la ComboBox pour l'expertise d'un Mécanicien avec les valeurs de l'énumération Expertise
        comboExpertise.getItems().addAll(Expertise.values()); // Remplir avec toutes les expertises
        comboExpertise.setValue(mecanicien.get_expertise()); // Pré-remplir avec l'expertise existante

        grid.add(new Label("Spécialité Mécanicien:"), 0, 3);
        grid.add(comboSpecialite, 1, 3);
        grid.add(new Label("Expertise:"), 0, 4);
        grid.add(comboExpertise, 1, 4);
    } else if (employe instanceof Laveur) {
        Laveur laveur = (Laveur) employe;

        // Remplir la ComboBox pour la spécialité d'un Laveur
        comboSpecialite.getItems().addAll("Intérieur", "Extérieur");
        comboSpecialite.setValue(laveur.get_specialise_interieur() ? "Intérieur" : "Extérieur"); // Pré-remplir avec la spécialité existante

        grid.add(new Label("Spécialité Laveur:"), 0, 3);
        grid.add(comboSpecialite, 1, 3);
    }

    // Créer les boutons
    Button btnSave = new Button("Sauvegarder");
    Button btnCancel = new Button("Annuler");

    // Gérer le clic sur le bouton Sauvegarder
    btnSave.setOnAction(e -> {
        try {
            double salaire = Double.parseDouble(txtSalaire.getText());
            employe.set_nom(txtNom.getText());
            employe.set_prenom(txtPrenom.getText());
            employe.setSalaire(salaire);

            // Mettre à jour la spécialité et l'expertise en fonction du type d'employé
            if (employe instanceof Mecanicien) {
                Mecanicien mecanicien = (Mecanicien) employe;
                mecanicien.set_specialite(comboSpecialite.getValue());
                mecanicien.set_expertise(comboExpertise.getValue());
            } else if (employe instanceof Laveur) {
                Laveur laveur = (Laveur) employe;
                laveur.set_specialise_interieur(comboSpecialite.getValue().equals("Intérieur"));
                laveur.set_specialise_exterieur(comboSpecialite.getValue().equals("Extérieur"));
            }

            tableEmployes.refresh();  // Rafraîchir la table des employés
            modificationStage.close(); // Fermer la fenêtre de modification
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Salaire invalide");
            alert.setContentText("Veuillez entrer un salaire valide.");
            alert.showAndWait();
        }
    });

    // Gérer le clic sur le bouton Annuler
    btnCancel.setOnAction(e -> modificationStage.close());

    // Ajouter les éléments au GridPane
    grid.add(lblNom, 0, 0);
    grid.add(txtNom, 1, 0);
    grid.add(lblPrenom, 0, 1);
    grid.add(txtPrenom, 1, 1);
    grid.add(lblSalaire, 0, 2);
    grid.add(txtSalaire, 1, 2);

    // Créer une HBox pour les boutons
    HBox hboxButtons = new HBox(20);
    hboxButtons.setStyle("-fx-alignment: center;");
    hboxButtons.getChildren().addAll(btnSave, btnCancel);

    // Ajouter l'HBox de boutons au GridPane
    grid.add(hboxButtons, 0, 5, 2, 1);

    // Appliquer le fichier CSS
    Scene scene = new Scene(grid);
    scene.getStylesheets().add(getClass().getResource("/Vues/styles.css").toExternalForm());

    // Définir la taille de la fenêtre
    modificationStage.setScene(scene);
    modificationStage.setWidth(600);
    modificationStage.setHeight(400);

    // Afficher la fenêtre
    modificationStage.show();
}



    private void supprimerEmploye(Employe employe) {
        Receptionniste receptionniste = MenuPrincipaleController.receptionnisteConnecte;
        if (receptionniste != null) {
            receptionniste.getListeEmployes().remove(employe);
            tableEmployes.getItems().remove(employe);
        }
    }
}
