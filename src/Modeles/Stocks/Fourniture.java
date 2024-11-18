package Modeles.Stocks;

import Modeles.Exceptions.ArgumentInvalideException;
import Modeles.Exceptions.QuantiteNegatifException;
import java.util.List;
import java.util.stream.Collectors;

public class Fourniture {

    // Attributs
    private int idFourniture;
    private String nom;
    private String description;
    private double prix;
    private int quantiteStock;

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
    }

    // Getters & Setters
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
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) throws ArgumentInvalideException {
        if (prix < 0) {
            throw new ArgumentInvalideException("Le prix de la fourniture ne peut pas être négatif.");
        }
        this.prix = prix;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) throws QuantiteNegatifException {
        if (quantiteStock < 0) {
            throw new QuantiteNegatifException("La quantité en stock ne peut pas être négative.");
        }
        this.quantiteStock = quantiteStock;
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
