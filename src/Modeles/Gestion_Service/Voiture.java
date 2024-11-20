package Modeles.Gestion_Service;
import Modeles.Personnes.Client;
import java.util.ArrayList;

public class Voiture {

    // Attributs
    private String marque;
    private String modele;
    private int annee;
    private long kilometrage;
    private String immatriculation; // Ajout de l'attribut immatriculation
    private Client client;


    // Constructeur
    public Voiture(String marque, String modele, int annee, long kilometrage, String immatriculation , Client client ) {
        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.kilometrage = kilometrage;
        this.immatriculation = immatriculation;
        this.client=client;

    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public long getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(long kilometrage) {
        this.kilometrage = kilometrage;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


////////////////////////// Méthodes //////////////////////////

    // Méthode pour afficher les informations de la voiture
    public void afficher() {
        System.out.println("=== Informations de la Voiture ===");
        System.out.println("Marque : " + marque);
        System.out.println("Modèle : " + modele);
        System.out.println("Année : " + annee);
        System.out.println("Kilométrage : " + kilometrage);
        System.out.println("Immatriculation : " + immatriculation);
        System.out.println("Client : " + client.get_id());

    }


    // Méthode pour afficher le client propriétaire de la voiture
    public void afficherClient() {
        System.out.println("=== Informations du Client Propriétaire ===");
        System.out.println(client);
    }



    @Override
    public String toString() {
        return "Voiture{" +
                "marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", annee=" + annee +
                ", kilometrage=" + kilometrage +
                ", immatriculation='" + immatriculation + '\'' +
                ", client=" + client.get_id() +
       //         ", services=" + services +
                '}';
    }
    public String get_immatriculation()
    {
        return this.immatriculation;
    }

    public void modeleProperty() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void marqueProperty() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object anneeProperty() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
