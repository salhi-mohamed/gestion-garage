package Modeles.Gestion_Service;
//import jdk.internal.icu.text.UTF16;

import java.util.ArrayList;
import Modeles.Stocks.Piece_Rechange;
import Modeles.Personnes.Employe;
import Modeles.Personnes.Client;

public class Service {
    public static Modeles.TypeService typeService;
    // Attributs
    private int idService;
    private String description;
    private double cout;
    private ArrayList<Employe> effecteurs; // Liste des employés effectuant le service
    private Client client; // Client associé au service
    private Voiture voiture; // Voiture associée au service
    private Rendez_vous rendezVous; // Rendez-vous associé au service
    private ArrayList<Piece_Rechange> piecesUtilisees; // Liste des pièces de rechange utilisées dans le service

    // La méthode getPiecesUtilisees() doit retourner une chaîne de caractères représentant les pièces utilisées
    public String getPiecesUtilisees() {
        return piecesUtilisees.stream()
                .map(Piece_Rechange::toString)  // Si Piece_Rechange a une méthode toString() lisible
                .reduce((p1, p2) -> p1 + ", " + p2)  // Combine toutes les pièces de rechange
                .orElse("Aucune pièce de rechange");
    }



    public String getEffecteurs() {
        return effecteurs.stream()
                .map(Employe::toString)  // Si Employe a une méthode toString() qui est lisible
                .reduce((e1, e2) -> e1 + ", " + e2)  // Combine tous les employés en une seule chaîne
                .orElse("Aucun employé");
    }


    //constructeur

    public Service(int idService, String description, double cout, ArrayList<Employe> effecteurs, Client client, Voiture voiture, Rendez_vous rendezVous, ArrayList<Piece_Rechange> piecesUtilisees) {
        this.idService = idService;
        this.description = description;
        this.cout = cout;
        this.effecteurs = effecteurs;
        this.client = client;
        this.voiture = voiture;
        this.rendezVous = rendezVous;
        this.piecesUtilisees = piecesUtilisees;
    }


//geter & seter


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public void setEffecteurs(ArrayList<Employe> effecteurs) {
        this.effecteurs = effecteurs;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }


    public void setPiecesUtilisees(ArrayList<Piece_Rechange> piecesUtilisees) {
        this.piecesUtilisees = piecesUtilisees;
    }

    public Rendez_vous getRendezVous() {
        return rendezVous;
    }

    public void setRendezVous(Rendez_vous rendezVous) {
        this.rendezVous = rendezVous;
    }


    // Méthode Afficher_Service : affiche les informations du service
    public void afficherService() {
        System.out.println(this.toString());
    }

    // Méthode Ajouter_Effecteur : ajoute un employé effecteur au service
    public void ajouterEffecteur(Employe employe) {
        effecteurs.add(employe);
    }

    // Méthode Ajouter_Piece : ajoute une pièce de rechange au service
    public void ajouterPiece(Piece_Rechange piece) {

        piecesUtilisees.add(piece);
    }

    // Méthode Supprimer_Effecteur : supprime un effecteur par son index
    public void supprimerEffecteur(int index) {
        if (index >= 0 && index < effecteurs.size()) {
            effecteurs.remove(index);
        } else {
            System.out.println("Index invalide pour la suppression de l'effecteur.");
        }
    }

    // Méthode Afficher_Rendez_vous : affiche les informations du rendez-vous associé au service
    public void afficherRendezVous() {
        if (rendezVous != null) {
            System.out.println(rendezVous.toString());
        } else {
            System.out.println("Aucun rendez-vous associé à ce service.");
        }
    }



  /*  @Override
    public String toString() {
        return "Service{" +
                "idService=" + idService +
                ", description='" + description + '\'' +
                ", cout=" + cout +
                ", effecteurs=" + effecteurs +
                ", client=" + client.get_id() +
                ", voiture=" + voiture.get_immatriculation() +
                ", rendezVous=" + rendezVous.getId_rendez_vous() +
                ", piecesUtilisees=" + piecesUtilisees +
                '}';
    }*/

    @Override
    public String toString() {
        return "Service{" +
                "idService=" + idService +
                ", description='" + description + '\'' +
                ", cout=" + cout +
                ", effecteurs=" + effecteurs +
                ", client=" + client +
                ", voiture=" + voiture +
                ", rendezVous=" + rendezVous +
                ", piecesUtilisees=" + piecesUtilisees +
                '}';
    }

    public void afficher()
    {
        System.out.println("Affichage du service : ");
        System.out.println("Id service : " + this.getIdService());
        System.out.println("Description service : " + this.getDescription());
        System.out.println("Cout service : "+ this.getCout());
        System.out.println(" Effecteurs : ");
        for (Employe e : effecteurs)
        {
            System.out.println(e);
        }
        System.out.println("client concerné : "+this.client);

        System.out.println("Voiture concernée : "+voiture);
        System.out.println("Rendez-vous concerné : "+rendezVous);
        for (Piece_Rechange p : piecesUtilisees)
        {
            System.out.println(p);
        }

    }


    // Afficher toutes les pièces de rechange utilisées dans le service
    public void afficherPiecesUtilisees() {
        System.out.println("Pièces de rechange utilisées :");
        piecesUtilisees.stream()
                .forEach(System.out::println);
    }


    public Object getTypeService() {
        return null;
    }

    public void setTypeService(String s) {
    }
};
