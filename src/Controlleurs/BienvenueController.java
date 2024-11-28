package Controlleurs;

import Modeles.Personnes.Receptionniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BienvenueController {

    // Déclarer la liste static
    private static ArrayList<Receptionniste> receptionnistes = new ArrayList<>();

    @FXML
    private ImageView backgroundImage;

    // Méthode pour définir la liste des réceptionnistes
    public static void setReceptionnistes(ArrayList<Receptionniste> receptionnistes) {
        BienvenueController.receptionnistes = receptionnistes;
    }

    // Méthode d'initialisation pour charger l'image
    @FXML
    public void initialize() {
        // Charger l'image en arrière-plan
        Image image = new Image("https://assets.newatlas.com/dims4/default/89191df/2147483647/strip/true/crop/4508x3005+0+0/resize/2880x1920!/quality/90/?url=http%3A%2F%2Fnewatlas-brightspot.s3.amazonaws.com%2F24%2F6f%2F92f2defe4a4998c6bcaa9c2cfb3f%2Fabt-audi-rs6-gr-22-glossyblack-4.jpg");
        backgroundImage.setImage(image);
    }

    // Méthode pour démarrer et ajouter un réceptionniste par défaut
    @FXML
    public void commencer(ActionEvent event) {
        try {
            // Vérifier si la liste des réceptionnistes est null ou vide, et l'initialiser si nécessaire
            if (receptionnistes == null) {
                receptionnistes = new ArrayList<>();
            }

            // Créer un réceptionniste par défaut avec une date d'embauche sous forme de String
            String dateEmb = "01/01/2020"; // Date sous forme de String

            Receptionniste receptionniste = new Receptionniste(
                    receptionnistes.size() + 1,  // ID unique basé sur la taille de la liste
                    "Nom",                       // Nom fictif
                    "Prenom",                    // Prénom fictif
                    12345678,                    // Numéro de téléphone fictif
                    "salhi@",                    // Adresse fictive
                    2500.0,                      // Salaire fictif
                    dateEmb,                     // Date d'embauche sous forme de String
                    101,                         // Numéro de bureau fictif
                    "salhi@gmail.com",           // Email fictif
                    "123456"                     // Mot de passe fictif
            );

            // Ajouter le réceptionniste à la liste
            receptionnistes.add(receptionniste);

            // Passer la liste à LoginController
            LoginController.setReceptionnistes(receptionnistes);

            // Charger la page Login.fxml après l'inscription automatique
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/Login.fxml"));
            Parent root = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir les mêmes dimensions que la scène actuelle
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();

            // Définir la nouvelle scène avec la même taille
            Scene scene = new Scene(root, currentWidth, currentHeight);
            stage.setScene(scene);

            // Afficher la fenêtre
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de Login.fxml : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Pour capturer d'autres exceptions éventuelles
        }
    }
}
/*
package Controlleurs;

import Modeles.Personnes.Receptionniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BienvenueController {

    // Déclarer la liste static
    private static ArrayList<Receptionniste> receptionnistes = new ArrayList<>();

    @FXML
    private ImageView backgroundImage;

    // Méthode pour définir la liste des réceptionnistes
    public static void setReceptionnistes(ArrayList<Receptionniste> receptionnistes) {
        BienvenueController.receptionnistes = receptionnistes;
    }

    // Méthode d'initialisation pour charger l'image
    @FXML
    public void initialize() {
        // Charger l'image en arrière-plan
        Image image = new Image("https://assets.newatlas.com/dims4/default/89191df/2147483647/strip/true/crop/4508x3005+0+0/resize/2880x1920!/quality/90/?url=http%3A%2F%2Fnewatlas-brightspot.s3.amazonaws.com%2F24%2F6f%2F92f2defe4a4998c6bcaa9c2cfb3f%2Fabt-audi-rs6-gr-22-glossyblack-4.jpg");
        backgroundImage.setImage(image);
    }

    // Méthode pour démarrer et ajouter un réceptionniste par défaut
    @FXML
    public void commencer(ActionEvent event) {
        try {
            // Vérifier si la liste des réceptionnistes est null ou vide, et l'initialiser si nécessaire
            if (receptionnistes == null) {
                receptionnistes = new ArrayList<>();
            }

            // Créer un réceptionniste par défaut avec une date d'embauche sous forme de String
            String dateEmb = "01/01/2020"; // Date sous forme de String

            Receptionniste receptionniste = new Receptionniste(
                    receptionnistes.size() + 1,  // ID unique basé sur la taille de la liste
                    "Nom",                       // Nom fictif
                    "Prenom",                    // Prénom fictif
                    12345678,                    // Numéro de téléphone fictif
                    "salhi@",                    // Adresse fictive
                    2500.0,                      // Salaire fictif
                    dateEmb,                     // Date d'embauche sous forme de String
                    101,                         // Numéro de bureau fictif
                    "salhi@gmail.com",           // Email fictif
                    "123456"                     // Mot de passe fictif
            );

            // Ajouter le réceptionniste à la liste
            receptionnistes.add(receptionniste);

            // Passer la liste à LoginController
            LoginController.setReceptionnistes(receptionnistes);

            // Charger la page Login.fxml après l'inscription automatique
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/Login.fxml"));
            Parent root = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir les mêmes dimensions que la scène actuelle
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();

            // Définir la nouvelle scène avec la même taille
            Scene scene = new Scene(root, currentWidth, currentHeight);
            stage.setScene(scene);

            // Afficher la fenêtre
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de Login.fxml : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Pour capturer d'autres exceptions éventuelles
        }
    }
}*/
