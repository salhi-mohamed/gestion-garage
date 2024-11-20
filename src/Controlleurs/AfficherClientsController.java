package Controlleurs;

import Modeles.Personnes.Receptionniste;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class AfficherClientsController {

    @FXML
    private ListView<String> clientsListView;

    @FXML
    public void initialize() {
        // Récupérer le réceptionniste fictif depuis le contrôleur AjouterClientController
        Receptionniste receptionniste = AjouterClientController.receptionnisteConnecte;

        if (receptionniste != null) {
            // Récupérer la liste des clients du réceptionniste fictif
            afficherClients(receptionniste);
        }
    }

    private void afficherClients(Receptionniste receptionniste) {
        // Vider la ListView avant de l'afficher
        clientsListView.getItems().clear();

        // Ajouter les clients à la ListView
        for (Modeles.Personnes.Client client : receptionniste.get_liste_clients()) {
            clientsListView.getItems().add(client.get_nom() + " " + client.get_prenom());
        }
    }

    // Méthode pour retourner (fermer ou revenir à l'écran précédent)
    public void retour() {
        // Implémentation du retour à la page précédente, ou retour dans l'interface principale
    }
}
