package Controlleurs;

import Modeles.Gestion_Service.Service;
import Modeles.Gestion_Service.Rendez_vous;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModifierServiceController {

    @FXML
    private ComboBox<Rendez_vous> comboRendezVous;

    @FXML
    private ComboBox<String> comboTypeService;

    @FXML
    private ComboBox<String> comboPiece;

    @FXML
    private TextArea textDescription;

    @FXML
    private TextField textCout;

    private Service service;

    public void setService(Service service) {
        this.service = service;

        // Initialiser les champs avec les valeurs du service existant
        comboRendezVous.setValue(service.getRendezVous());
        comboTypeService.setValue(service.getTypeService().toString()); // Remplacer par un énum si nécessaire
        comboPiece.setValue(service.getPiecesUtilisees());
        textDescription.setText(service.getDescription());
        textCout.setText(String.valueOf(service.getCout()));
    }

    @FXML
    private void enregistrerModification() {
        // Validation des données et mise à jour du service
        service.setRendezVous(comboRendezVous.getValue());
        // Convertir le type du service si nécessaire (par exemple, avec un enum)
        service.setTypeService(comboTypeService.getValue());
       // service.setPiecesUtilisees(comboPiece.getValue());
        service.setDescription(textDescription.getText());
        service.setCout(Double.parseDouble(textCout.getText()));

        // Fermer la fenêtre
        Stage stage = (Stage) comboRendezVous.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void fermerFenetre() {
        Stage stage = (Stage) comboRendezVous.getScene().getWindow();
        stage.close();
    }
}
