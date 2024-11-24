/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlleurs;

import Modeles.Gestion_Service.Voiture;
import Modeles.Personnes.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author LENOVO
 */
public class FormulaireModificationVoitureController {

    @FXML
    private TextField textFieldMarque;

    @FXML
    private TextField textFieldModele;

    @FXML
    private TextField textFieldKilometrage;

    

    private Voiture voiture;

    public void setVoiture(Voiture voiture) {
        this.voiture=voiture;

        // Remplir les champs avec les informations actuelles du client
        textFieldMarque.setText(voiture.getMarque());
        textFieldModele.setText(voiture.getModele());
        textFieldKilometrage.setText(String.valueOf(voiture.getKilometrage()));
    }

    @FXML
    private void enregistrerModifications() {
        // Mettre à jour les informations du client
        voiture.setMarque(textFieldMarque.getText());
       voiture.setModele(textFieldModele.getText());
        voiture.setKilometrage(Integer.parseInt(textFieldKilometrage.getText()));

        // Sauvegarder dans la base de données
        // Par exemple : receptionnisteConnecte.modifierClient(client);

        // Fermer la fenêtre
        Stage stage = (Stage) textFieldMarque.getScene().getWindow();
        stage.close();
    }
    @FXML
private void fermerFormulaire() {
    // Fermer la fenêtre actuelle
    Stage stage = (Stage) textFieldMarque.getScene().getWindow();
    stage.close();
}

}

