package Controlleurs;

import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AfficherVoituresClientController {

    @FXML
    private TableView<Voiture> tableVoitures;
    @FXML
    private TableColumn<Voiture, String> colMarque;
    @FXML
    private TableColumn<Voiture, String> colModele;
    @FXML
    private TableColumn<Voiture, Integer> colAnnee;
    @FXML
    private TableColumn<Voiture, String> colKilometrage;
    @FXML
    private TableColumn<Voiture, String> colImmatriculation;

    private Client client;

    public void setClient(Client client) {
        this.client = client;
        afficherVoitures();
    }

    @FXML
    public void initialize() {
        initialiserColonnes();
    }

    private void initialiserColonnes() {
        colMarque.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
        colModele.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
        colAnnee.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAnnee()).asObject());
        colKilometrage.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getKilometrage())));
        colImmatriculation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImmatriculation()));
    }

    private void afficherVoitures() {
        if (client != null) {
            tableVoitures.getItems().clear();
            tableVoitures.getItems().addAll(client.getVoitures());
        }
    }
}
