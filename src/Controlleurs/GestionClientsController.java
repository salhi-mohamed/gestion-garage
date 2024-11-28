package Controlleurs;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GestionClientsController {

    // Attributs pour les éléments de l'interface utilisateur
    @FXML private Button btnAjouterClient;
    @FXML private Button btnSupprimerClient;
    @FXML private Button btnModifierClient;
    @FXML private Button btnAfficherClients;
    @FXML private Button btnAjouterVoiture;
    @FXML private Button btnSupprimerVoiture;
    @FXML private Button btnAjouterFourniture;
    @FXML private Button btnSupprimerFourniture;
    @FXML private Button btnRetour;

    // Ajouter un client
    @FXML

    private void ajouterClient() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/AjouterClient.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'AjouterClient.fxml' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Ajouter un Client");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Ajouter Client'.");
        }
    }


    // Supprimer un client
    @FXML
    private void supprimerClient() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/SupprimerClient.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'SupprimerClient' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Supprimer un client ");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Supprimer client'.");
        } 
    }

    // Modifier un client
    @FXML
    private void modifierClient() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/ModifierClient.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'ModifierClient' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Modifier ");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Modifier Client'.");
        }     }

    // Afficher la liste des clients
    @FXML
    private void afficherClients() {
        try {
            // Check if the FXML file exists
            URL fxmlLocation = getClass().getResource("/Vues/AfficherClients.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'AfficherClients.fxml' n'a pas été trouvé.");
                return;  // Exit the method if the FXML is not found
            } else {
                System.out.println("FXML file loaded successfully.");
            }

            // Proceed with loading the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Afficher clients");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Afficher Client'.");
        }    }

    // Ajouter une voiture à un client
    @FXML
   private void ajouterVoiture() {
    try {
        // Check if the FXML file exists
        URL fxmlLocation = getClass().getResource("/Vues/AjouterVoitureClient.fxml");
        if (fxmlLocation == null) {
            System.out.println("FXML file not found!");
            showAlert("Erreur", "Le fichier FXML 'AjouterVoitureClient.fxml' n'a pas été trouvé.");
            return;  // Exit the method if the FXML is not found
        } else {
            System.out.println("FXML file loaded successfully.");
        }

        // Proceed with loading the FXML file
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Ajouter Voiture à un client ");
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();  // Print the stack trace for debugging
        showAlert("Erreur", "Impossible de charger la page 'Ajouter Voiture '.");
    }
}


    // Supprimer une voiture d'un client
    @FXML
    private void supprimerVoiture() {
    try {
        // Check if the FXML file exists
        URL fxmlLocation = getClass().getResource("/Vues/SupprimerVoitureClient.fxml");
        if (fxmlLocation == null) {
            System.out.println("FXML file not found!");
            showAlert("Erreur", "Le fichier FXML 'SupprimerVoitureClient' n'a pas été trouvé.");
            return;  // Exit the method if the FXML is not found
        } else {
            System.out.println("FXML file loaded successfully.");
        }

        // Proceed with loading the FXML file
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Supprimer une voiture d'un client");
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        // Afficher l'erreur dans la console
        e.printStackTrace();  // This will print the full stack trace of the exception
        showAlert("Erreur", "Impossible de charger la page 'Supprimer voiture client'.");
    } 
}


    // Ajouter une fourniture à un client
    @FXML
    private void ajouterFourniture() {
try {
        // Check if the FXML file exists
        URL fxmlLocation = getClass().getResource("/Vues/AjouterFournitureClient.fxml");
        if (fxmlLocation == null) {
            System.out.println("FXML file not found!");
            showAlert("Erreur", "Le fichier FXML 'AjouterFournitureClient' n'a pas été trouvé.");
            return;  // Exit the method if the FXML is not found
        } else {
            System.out.println("FXML file loaded successfully.");
        }

        // Proceed with loading the FXML file
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Ajouter une fourniture à un client");
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        // Afficher l'erreur dans la console
        e.printStackTrace();  // This will print the full stack trace of the exception
        showAlert("Erreur", "Impossible de charger la page 'Ajouter Fourniture Client'.");
    }     }

    // Supprimer une fourniture d'un client
    @FXML
    private void supprimerFourniture() {
         
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML


    private void retour() {
        try {
            // Charger le fichier FXML du menu principal
            URL fxmlLocation = getClass().getResource("/Vues/MenuPrincipale.fxml");
            if (fxmlLocation == null) {
                System.out.println("FXML file not found!");
                showAlert("Erreur", "Le fichier FXML 'MenuPrincipale.fxml' n'a pas été trouvé.");
                return;
            }

            // Charger la scène du menu principal
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());

            // Obtenir le Stage (fenêtre actuelle)
            Stage stage = (Stage) btnRetour.getScene().getWindow();

            // S'assurer que le Stage est valide
            if (stage != null) {
                stage.setTitle("Menu Principal");
                stage.setScene(scene);  // Changer la scène pour le menu principal
                stage.show();           // Afficher la nouvelle scène (menu principal)
            } else {
                System.out.println("Stage est nul. Impossible de changer la scène.");
            }
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page 'Menu Principal'.");
        }
    }



}