package Modeles.Stocks;
import Modeles.Exceptions.ArgumentInvalideException;
import Modeles.Exceptions.QuantiteNegatifException;

import java.util.List;
import java.util.stream.Collectors;

public class Piece_Rechange {

    // Attributs
    private int idPiece;
    private String nom;
    private String description;
    private double prix;
    private int quantiteStock;


    //constucteur


    public Piece_Rechange(int idPiece, String nom, String description, double prix, int quantiteStock) {
        this.idPiece = idPiece;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
    }

    //geters & seters

    public int getIdPiece() {
        return idPiece;
    }

    public void setIdPiece(int idPiece) {
        this.idPiece = idPiece;
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

    public void setQuantiteStock(int quantiteStock) throws QuantiteNegatifException  {

        if (quantiteStock < 0) {
            throw new QuantiteNegatifException("La quantité en stock ne peut pas être négative.");
        }
        this.quantiteStock = quantiteStock;
    }

    ////////////////////////// Méthodes //////////////////////////

    // Méthode pour modifier les informations de la pièce de rechange
    public void modifier(int idPiece, String nom, String description, double prix, int quantiteStock) {
        this.idPiece = idPiece;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
        System.out.println("Informations de la pièce de rechange modifiées avec succès.");
    }

    // Méthode pour afficher les informations de la pièce de rechange
    public void afficherPieceRechange() {
        System.out.println("=== Informations de la Pièce de Rechange ===");
        System.out.println("ID Pièce : " + idPiece);
        System.out.println("Nom : " + nom);
        System.out.println("Description : " + description);
        System.out.println("Prix : " + prix);
        System.out.println("Quantité en Stock : " + quantiteStock);
    }

    ////////////////////////// Méthodes avec Streams //////////////////////////

    // Filtrer les pièces dont le stock est inférieur à un seuil
    public static List<Piece_Rechange> filtrerParQuantiteStock(List<Piece_Rechange> pieces, int seuil) {
        return pieces.stream()
                .filter(p -> p.getQuantiteStock() < seuil)
                .collect(Collectors.toList());
    }

    // Trier les pièces par prix
    public static List<Piece_Rechange> trierParPrix(List<Piece_Rechange> pieces) {
        return pieces.stream()
                .sorted((p1, p2) -> Double.compare(p1.getPrix(), p2.getPrix()))
                .collect(Collectors.toList());
    }

}
