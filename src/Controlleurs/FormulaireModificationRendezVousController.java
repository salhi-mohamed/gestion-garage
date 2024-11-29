package Controlleurs;

import Modeles.Gestion_Service.Rendez_vous;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FormulaireModificationRendezVousController {

    @FXML
    private ComboBox<String> comboClient;

    @FXML
    private ComboBox<String> comboVoiture;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textHeure;

    @FXML
    private ComboBox<String> comboStatut;

    private Rendez_vous rendezVous;

    public void setRendezVous(Rendez_vous rendezVous) {
        this.rendezVous = rendezVous;

        // Initialiser les champs avec les valeurs du rendez-vous existant
        comboClient.setValue(rendezVous.getClient().get_nom());
        comboVoiture.setValue(rendezVous.getVoiture().getMarque());
        datePicker.setValue(rendezVous.getDate_rendez_vous());
        comboStatut.setValue(rendezVous.getStatut().toString());
    }

    @FXML
    private void enregistrerModification() {
        // Validation et mise à jour des informations du rendez-vous
        // Logic de mise à jour

        // Fermer la fenêtre
        Stage stage = (Stage) comboClient.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void fermerFenetre() {
        Stage stage = (Stage) comboClient.getScene().getWindow();
        stage.close();
    }
}
