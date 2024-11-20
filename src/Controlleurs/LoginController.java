package Controlleurs;

import Modeles.Personnes.Receptionniste;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class LoginController {
    // Liste des réceptionnistes accessible dans toute la classe
    private static ArrayList<Receptionniste> receptionnistes = new ArrayList<>();

    // Ajouter un setter ou un constructeur pour initialiser la liste de réceptionnistes si elle provient d'une autre classe
    public static void setReceptionnistes(ArrayList<Receptionniste> list) {
        receptionnistes = list;
    }

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        Receptionniste receptionniste = findReceptionniste(email, password);

        if (receptionniste != null) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Connexion réussie !");
            navigateToMenuPrincipale(); // Navigation après succès
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Email ou mot de passe incorrect !");
        }
    }

    private Receptionniste findReceptionniste(String email, String password) {
        for (Receptionniste r : receptionnistes) {
            if (r.get_email().equals(email) && r.get_password().equals(password)) { // Comparaison email et mdp
                return r;
            }
        }
        return null;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private void navigateToMenuPrincipale() {
        try {
            // Charger le fichier FXML de la page MenuPrincipale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/MenuPrincipale.fxml"));
            Scene menuScene = new Scene(loader.load());

            // Récupérer la fenêtre actuelle et changer la scène
            Stage currentStage = (Stage) emailField.getScene().getWindow();
            currentStage.setScene(menuScene);

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la page MenuPrincipale : " + e.getMessage());
        }
    }
}

