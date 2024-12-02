package Modeles.Gestion_Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Modeles.Stocks.Fourniture;
import Modeles.Stocks.Piece_Rechange;
import Modeles.Personnes.Employe;
import Modeles.Personnes.Client;

public class Service {
    // Attributs
    private int idService;
    private String description;
    private double cout;
    private ArrayList<Employe> effecteurs; // Liste des employés effectuant le service
    private Client client; // Client associé au service
    private Voiture voiture; // Voiture associée au service
    private Rendez_vous rendezVous; // Rendez-vous associé au service
    private ArrayList<Piece_Rechange> piecesUtilisees; // Liste des pièces de rechange utilisées dans le service
    private String Nom;

    // Constructeur
   /* public Service(String description, double cout, int idService, Voiture voiture, Rendez_vous rendezVous, Client client) {
        this.description = description;
        this.cout = cout;
        this.idService = idService;
        this.voiture = voiture;
        this.rendezVous = rendezVous;
        this.client = client;
        this.effecteurs = new ArrayList<>();
        this.piecesUtilisees = new ArrayList<>();
    }*/


public Service(int idService,String description, String Nom,double cout){
    this.idService=idService;
    this.Nom=Nom;
    this.description=description;
    this.cout=cout;

}
    public Service(String typeService, double cout, Rendez_vous rendezVous, List<Employe> employesSelectionnes, List<Piece_Rechange> piecesSelectionnees, List<Fourniture> fournituresSelectionnees) {
    }


    // Getters et setters
    public String getNom() {
        return Nom; // Retourne la description comme "nom" du service
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getIdService() { return idService; }
    public void setIdService(int idService) { this.idService = idService; }
    public double getCout() { return cout; }
    public void setCout(double cout) { this.cout = cout; }
    public ArrayList<Employe> getEffecteurs() { return effecteurs; }
    public void setEffecteurs(ArrayList<Employe> effecteurs) { this.effecteurs = effecteurs; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public Voiture getVoiture() { return voiture; }
    public void setVoiture(Voiture voiture) { this.voiture = voiture; }
    public ArrayList<Piece_Rechange> getPiecesUtilisees() { return piecesUtilisees; }
    public void setPiecesUtilisees(ArrayList<Piece_Rechange> piecesUtilisees) { this.piecesUtilisees = piecesUtilisees; }
    public Rendez_vous getRendezVous() { return rendezVous; }
    public void setRendezVous(Rendez_vous rendezVous) { this.rendezVous = rendezVous; }

    // Méthodes
    public void afficherService() {
        System.out.println(this);
    }

    public void ajouterEffecteur(Employe employe) {
        effecteurs.add(employe);
    }

    public void ajouterPiece(Piece_Rechange piece) {
        piecesUtilisees.add(piece);
    }

    public void supprimerEffecteur(int index) {
        if (index >= 0 && index < effecteurs.size()) {
            effecteurs.remove(index);
        } else {
            System.out.println("Index invalide pour la suppression de l'effecteur.");
        }
    }

    public void afficherRendezVous() {
        if (rendezVous != null) {
            System.out.println(rendezVous);
        } else {
            System.out.println("Aucun rendez-vous associé à ce service.");
        }
    }

    public void afficherPiecesUtilisees() {
        System.out.println("Pièces de rechange utilisées :");
        if (piecesUtilisees.isEmpty()) {
            System.out.println("Aucune pièce de rechange utilisée.");
        } else {
            for (Piece_Rechange piece : piecesUtilisees) {
                System.out.println(piece);
            }
        }
    }

    @Override
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
    }

    public void afficher() {
        System.out.println("Affichage du service : ");
        System.out.println("Id service : " + idService);
        System.out.println("Description : " + description);
        System.out.println("Cout : " + cout);
        System.out.println("Effecteurs :");
        for (Employe e : effecteurs) {
            System.out.println(e);
        }
        System.out.println("Client concerné : " + client);
        System.out.println("Voiture concernée : " + voiture);
        System.out.println("Rendez-vous concerné : " + rendezVous);
        afficherPiecesUtilisees();
    }


    public void setNom(String Nom) {
        this.description = Nom;
    }
}
