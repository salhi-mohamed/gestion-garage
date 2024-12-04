package Controlleurs;

import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.*;
import java.io.IOException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TableColumn<Employe, String> colSalaire;
    @FXML
    private TableColumn<Employe, String> colActions;

    // TableView pour afficher les voitures associées
    @FXML
    private TableView<Voiture> tableVoitures;
    @FXML
    private TableColumn<Voiture, String> colModele;
    @FXML
    private TableColumn<Voiture, String> colMarque;
    @FXML
    private TableColumn<Voiture, String> colImmatriculation;

    // TableView pour afficher l'équipe du chef
    @FXML
    private TableView<Employe> tableEquipe;
    @FXML
    private TableColumn<Employe, String> colEquipeNom;
    @FXML
    private TableColumn<Employe, String> colEquipePrenom;
    @FXML
    private TableColumn<Employe, String> colEquipeRole;

    @FXML
    public void initialize() {
        Receptionniste receptionniste = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionniste != null) {
            initialiserColonnes();
            afficherEmployes(receptionniste);

            // Ajouter un écouteur pour détecter la sélection d'un employé
            tableEmployes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                afficherVoituresEmploye(newValue); // Afficher les voitures et l'équipe si c'est un chef
            });
        }
    }

    // Méthode pour afficher les employés dans le TableView
    private void afficherEmployes(Receptionniste receptionniste) {
        tableEmployes.getItems().clear();
        tableEmployes.getItems().addAll(receptionniste.getListeEmployes());
    }

    // Méthode pour afficher les voitures et l'équipe d'un employé sélectionné
    private void afficherVoituresEmploye(Employe employe) {
        tableVoitures.getItems().clear(); // Effacer les anciennes données
        tableEquipe.getItems().clear(); // Effacer les anciennes données de l'équipe

        if (employe == null) {
            return;
        }

        // Si l'employé est un Mécanicien
        if (employe instanceof Mecanicien) {
            Mecanicien mecanicien = (Mecanicien) employe;
            tableVoitures.getItems().addAll(mecanicien.get_historique_voitures());
        }
        // Si l'employé est un Laveur
        else if (employe instanceof Laveur) {
            Laveur laveur = (Laveur) employe;
            tableVoitures.getItems().addAll(laveur.getVoitures());
        }
        // Si l'employé est un Chef
        else if (employe instanceof Chef) {
            Chef chef = (Chef) employe;
            // Afficher l'équipe du chef
            tableEquipe.getItems().addAll(chef.getEquipe());
        }
    }

    // Initialiser les colonnes pour les employés et l'équipe du chef
    private void initialiserColonnes() {
        // Colonnes des employés
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
            return new SimpleStringProperty(String.format("%.2f", employe.getSalaire())); // Affichage du salaire avec deux décimales
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

        // Colonnes de l'équipe du chef
        colEquipeNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_nom()));
        colEquipePrenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_prenom()));
        colEquipeRole.setCellValueFactory(cellData -> {
            Employe employe = cellData.getValue();
            if (employe instanceof Chef) return new SimpleStringProperty("Chef");
            if (employe instanceof Mecanicien) return new SimpleStringProperty("Mécanicien");
            if (employe instanceof Laveur) return new SimpleStringProperty("Laveur");
            return new SimpleStringProperty("Inconnu");
        });

        // Colonnes des voitures
        colModele.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
        colMarque.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
        colImmatriculation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImmatriculation()));

        // Colonne de suppression dans la table de l'équipe du chef
        TableColumn<Employe, String> colActionsEquipe = new TableColumn<>("Actions");
        colActionsEquipe.setCellFactory(column -> new TableCell<Employe, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button btnSupprimerEquipe = new Button("Supprimer");
                    btnSupprimerEquipe.setOnAction(e -> {
                        Employe employe = getTableView().getItems().get(getIndex());
                        supprimerEmployeDeLEquipe(employe);
                    });
                    setGraphic(btnSupprimerEquipe);
                }
            }
        });
        tableEquipe.getColumns().add(colActionsEquipe);
    }

   private void modifierEmploye(Employe employe) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/ModifierEmploye.fxml"));
        Parent root = loader.load();

        ModifierEmployeController controller = loader.getController();
        controller.setEmployeAModifier(employe);

        Stage stage = new Stage();
        stage.setTitle("Modifier Employé");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        tableEmployes.refresh(); // Rafraîchir les données après modification
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private void supprimerEmploye(Employe employe) {
        Receptionniste receptionniste = MenuPrincipaleController.receptionnisteConnecte;
        if (receptionniste != null) {
            receptionniste.getListeEmployes().remove(employe);
            tableEmployes.getItems().remove(employe);
        }
    }

    // Méthode pour supprimer un employé de l'équipe d'un chef
    private void supprimerEmployeDeLEquipe(Employe employe) {
        Chef chef = (Chef) tableEmployes.getSelectionModel().getSelectedItem();
        if (chef != null) {
            chef.getEquipe().remove(employe); // Retirer l'employé de l'équipe du chef
            tableEquipe.getItems().remove(employe); // Mettre à jour la table d'équipe
        }
    }

    // Méthode pour gérer la sélection d'un employé
    @FXML
    public void onEmployeSelected() {
        Employe selectedEmploye = tableEmployes.getSelectionModel().getSelectedItem();
        if (selectedEmploye != null) {
            afficherVoituresEmploye(selectedEmploye); // Afficher les voitures et l'équipe si c'est un chef
        }
    }

    // Pour fermer la fenêtre
    @FXML
    private void fermerFenetre() {
        Stage stage = (Stage) tableEmployes.getScene().getWindow();
        stage.close();
    }
}