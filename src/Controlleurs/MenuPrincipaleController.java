package controlleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class MenuPrincipaleController {

    @FXML
    public void ouvrirClients(ActionEvent event) {
       // ouvrirNouvelleScene("/path/to/Clients.fxml", event);
    }

    @FXML
    public void ouvrirVoitures(ActionEvent event) {
       // ouvrirNouvelleScene("/path/to/Voitures.fxml", event);
    }

    @FXML
    public void ouvrirEmployes(ActionEvent event) {
       // ouvrirNouvelleScene("/path/to/Employes.fxml", event);
    }

    @FXML
    public void ouvrirRendezVous(ActionEvent event) {
       // ouvrirNouvelleScene("/path/to/RendezVous.fxml", event);
    }

    @FXML
    public void ouvrirFactures(ActionEvent event) {
       // ouvrirNouvelleScene("/path/to/Factures.fxml", event);
    }

    @FXML
    public void ouvrirStock(ActionEvent event) {
        //ouvrirNouvelleScene("/path/to/Stock.fxml", event);
    }

    private void ouvrirNouvelleScene(String fxmlPath, ActionEvent event) {
        try {
            // Charger la nouvelle scène à partir du fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            // Récupérer la fenêtre actuelle (Stage) et la mettre à jour
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la scène : " + e.getMessage());
        }
    }
}
