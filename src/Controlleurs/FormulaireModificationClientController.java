/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlleurs;

import Modeles.Personnes.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author LENOVO
 */
public class FormulaireModificationClientController {

    @FXML
    private TextField textFieldNom;

    @FXML
    private TextField textFieldPrenom;

    @FXML
    private TextField textFieldTelephone;

    @FXML
    private TextField textFieldAdresse;

    private Client client;

    public void setClient(Client client) {
        this.client = client;

        // Remplir les champs avec les informations actuelles du client
        textFieldNom.setText(client.get_nom());
        textFieldPrenom.setText(client.get_prenom());
        textFieldTelephone.setText(String.valueOf(client.get_telephone()));
        textFieldAdresse.setText(client.get_adresse());
    }

    @FXML
    private void enregistrerModifications() {
        // Mettre à jour les informations du client
        client.set_nom(textFieldNom.getText());
        client.set_prenom(textFieldPrenom.getText());
        client.set_telephone(Integer.parseInt(textFieldTelephone.getText()));
        client.set_adresse(textFieldAdresse.getText());

        // Sauvegarder dans la base de données
        // Par exemple : receptionnisteConnecte.modifierClient(client);

        // Fermer la fenêtre
        Stage stage = (Stage) textFieldNom.getScene().getWindow();
        stage.close();
    }
    @FXML
private void fermerFormulaire() {
    // Fermer la fenêtre actuelle
    Stage stage = (Stage) textFieldNom.getScene().getWindow();
    stage.close();
}

}

