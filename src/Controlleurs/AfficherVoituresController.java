package Controlleurs;

import Modeles.Personnes.Receptionniste;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class AfficherVoituresController {

    @FXML
    private TableView<Modeles.Gestion_Service.Voiture> tableVoitures;
    
    @FXML
    private TableColumn<Modeles.Gestion_Service.Voiture, String> colMarque;
    
    @FXML
    private TableColumn<Modeles.Gestion_Service.Voiture, String> colModele;
    
    @FXML
    private TableColumn<Modeles.Gestion_Service.Voiture, Integer> colAnnee;
    
    @FXML
    private TableColumn<Modeles.Gestion_Service.Voiture, Double> colPrix;
    
    // Nouvelles colonnes pour l'ID, le nom du client et l'immatriculation
    @FXML
    private TableColumn<Modeles.Gestion_Service.Voiture, String> colClientID;
    
    @FXML
    private TableColumn<Modeles.Gestion_Service.Voiture, String> colClientNom;
    
    @FXML
    private TableColumn<Modeles.Gestion_Service.Voiture, String> colImmatriculation;

    @FXML
    public void initialize() {
        // Récupérer le réceptionniste connecté depuis MenuPrincipaleController
        Receptionniste receptionniste = MenuPrincipaleController.receptionnisteConnecte;

        if (receptionniste != null) {
            // Récupérer la liste des voitures du réceptionniste
            afficherVoitures(receptionniste);
        }
    }

    private void afficherVoitures(Receptionniste receptionniste) {
        // Vider la TableView avant de l'afficher
        tableVoitures.getItems().clear();

        // Remplir les colonnes avec les valeurs correspondantes
        colMarque.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
        colModele.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
        colAnnee.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAnnee()).asObject());
        colPrix.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getKilometrage()).asObject());

        // Ajouter l'ID et le nom du client
        colClientID.setCellValueFactory(cellData -> 
            new SimpleStringProperty(Integer.toString(cellData.getValue().getClient().get_id())));
        colClientNom.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getClient().get_nom()));

        // Ajouter l'immatriculation de la voiture
        colImmatriculation.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getImmatriculation()));

        // Ajouter les voitures à la TableView
        for (Modeles.Gestion_Service.Voiture voiture : receptionniste.getListeVoitures()) {
            tableVoitures.getItems().add(voiture);
        }
    }

    // Méthode pour retourner (fermer ou revenir à l'écran précédent)
    public void retour() {
        // Implémentation du retour à la page précédente
    }
}
