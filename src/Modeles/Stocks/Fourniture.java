package Modeles.Stocks;

import Modeles.Exceptions.ArgumentInvalideException;
import Modeles.Exceptions.QuantiteNegatifException;
import javafx.beans.property.*;

import java.util.List;
import java.util.stream.Collectors;

public class Fourniture {

    // Attributs existants
    private int idFourniture;
    private String nom;
    private String description;
    private double prix;
    private int quantiteStock;

    // Nouvelles propriétés JavaFX pour la liaison avec TableView
    private StringProperty nomProperty;
    private StringProperty descriptionProperty;
    private DoubleProperty prixProperty;
    private IntegerProperty quantiteStockProperty;

    // Constructeur
    public Fourniture(int idFourniture, String nom, String description, double prix, int quantiteStock) throws ArgumentInvalideException, QuantiteNegatifException {
        if (prix < 0) {
            throw new ArgumentInvalideException("Le prix de la fourniture ne peut pas être négatif.");
        }
        if (quantiteStock < 0) {
            throw new QuantiteNegatifException("La quantité en stock ne peut pas être négative.");
        }

        this.idFourniture = idFourniture;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.quantiteStock = quantiteStock;

        // Initialisation des propriétés JavaFX
        this.nomProperty = new SimpleStringProperty(nom);
        this.descriptionProperty = new SimpleStringProperty(description);
        this.prixProperty = new SimpleDoubleProperty(prix);
        this.quantiteStockProperty = new SimpleIntegerProperty(quantiteStock);
    }

    // Getters & Setters pour les attributs existants
    public int getIdFourniture() {
        return idFourniture;
    }

    public void setIdFourniture(int idFourniture) {
        this.idFourniture = idFourniture;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        this.nomProperty.set(nom);  // Met à jour la propriété JavaFX
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.descriptionProperty.set(description);  // Met à jour la propriété JavaFX
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) throws ArgumentInvalideException {
        if (prix < 0) {
            throw new ArgumentInvalideException("Le prix de la fourniture ne peut pas être négatif.");
        }
        this.prix = prix;
        this.prixProperty.set(prix);  // Met à jour la propriété JavaFX
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) throws QuantiteNegatifException {
        if (quantiteStock < 0) {
            throw new QuantiteNegatifException("La quantité en stock ne peut pas être négative.");
        }
        this.quantiteStock = quantiteStock;
        this.quantiteStockProperty.set(quantiteStock);  // Met à jour la propriété JavaFX
    }

    // Propriétés JavaFX
    public StringProperty nomProperty() {
        return nomProperty;
    }

    public StringProperty descriptionProperty() {
        return descriptionProperty;
    }

    public DoubleProperty prixProperty() {
        return prixProperty;
    }

    public IntegerProperty quantiteStockProperty() {
        return quantiteStockProperty;
    }

    ////////////////////////// Méthodes //////////////////////////

    // Méthode pour modifier les informations de la fourniture
    public void modifierFourniture(int idFourniture, String nom, String description, double prix, int quantiteStock) throws ArgumentInvalideException, QuantiteNegatifException {
        if (prix < 0) {
            throw new ArgumentInvalideException("Le prix de la fourniture ne peut pas être négatif.");
        }
        if (quantiteStock < 0) {
            throw new QuantiteNegatifException("La quantité en stock ne peut pas être négative.");
        }

        this.idFourniture = idFourniture;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.quantiteStock = quantiteStock;

        // Mise à jour des propriétés JavaFX
        this.nomProperty.set(nom);
        this.descriptionProperty.set(description);
        this.prixProperty.set(prix);
        this.quantiteStockProperty.set(quantiteStock);

        System.out.println("Informations de la fourniture modifiées avec succès.");
    }

    // Méthode pour afficher les informations de la fourniture
    public void afficherFourniture() {
        System.out.println("=== Informations de la Fourniture ===");
        System.out.println("ID Fourniture : " + idFourniture);
        System.out.println("Nom : " + nom);
        System.out.println("Description : " + description);
        System.out.println("Prix : " + prix);
        System.out.println("Quantité en Stock : " + quantiteStock);
    }

    ////////////////////////// Méthodes avec Streams //////////////////////////

    // Filtrer les fournitures avec quantité en stock inférieure au seuil
    public static List<Fourniture> filtrerParQuantiteStock(List<Fourniture> fournitures, int seuil) {
        return fournitures.stream()
                .filter(f -> f.getQuantiteStock() < seuil)
                .collect(Collectors.toList());
    }

    // Trier les fournitures par prix croissant
    public static List<Fourniture> trierParPrix(List<Fourniture> fournitures) {
        return fournitures.stream()
                .sorted((f1, f2) -> Double.compare(f1.getPrix(), f2.getPrix()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Fourniture {" +
               "ID = " + idFourniture +
               ", Nom = '" + nom + '\'' +
               ", Description = '" + description + '\'' +
               ", Prix = " + prix +
               ", Quantité en Stock = " + quantiteStock +
               '}';
    }
}
