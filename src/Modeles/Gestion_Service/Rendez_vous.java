package Modeles.Gestion_Service;

import Modeles.Personnes.Client;
import java.time.LocalDate;
import java.time.LocalTime;

public class Rendez_vous {
    private int id_rendez_vous;
    private LocalDate Date_rendez_vous;
    private String Description_rendez_vous;
    private StatutRendezVous statut;
    private Voiture voiture;
    private Client client;

    public Rendez_vous(int id_rendez_vous, String Description_rendez_vous, Voiture voiture, Client client, LocalDate Date_rendez_vous, StatutRendezVous statut) {
        this.id_rendez_vous = id_rendez_vous;
        this.Description_rendez_vous = Description_rendez_vous;
        this.voiture = voiture;
        this.client = client;
        this.Date_rendez_vous = Date_rendez_vous;
        this.statut = statut;
    }

    // Getters et setters
    public int getId_rendez_vous() {
        return id_rendez_vous;
    }

    public void setId_rendez_vous(int id_rendez_vous) {
        this.id_rendez_vous = id_rendez_vous;
    }

    public String getDescription_rendez_vous() {
        return Description_rendez_vous;
    }

    public void setDescription_rendez_vous(String description_rendez_vous) {
        Description_rendez_vous = description_rendez_vous;
    }

    public LocalDate getDate_rendez_vous() {
        return Date_rendez_vous;
    }

    public void setDate_rendez_vous(LocalDate Date_rendez_vous) {
        this.Date_rendez_vous = Date_rendez_vous;
    }

    public StatutRendezVous getStatut() {
        return statut;
    }

    public void setStatut(StatutRendezVous statut) {
        this.statut = statut;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // Méthode pour annuler le rendez-vous
    public void annulerRendezVous() {
        if (this.statut != StatutRendezVous.ANNULE) {
            this.statut = StatutRendezVous.ANNULE;
            System.out.println("Le rendez-vous avec le client " + this.client.get_nom() + " a été annulé.");
        } else {
            System.out.println("Le rendez-vous est déjà annulé.");
        }
    }

    // Affichage des détails du rendez-vous
    public void afficherRendezVous() {
        System.out.println("=== Détails du Rendez-vous ===");
        System.out.println("ID : " + id_rendez_vous);
        System.out.println("Description : " + Description_rendez_vous);

        if (voiture != null) {
            System.out.println("Voiture : " + voiture.getMarque() + " " + voiture.getModele() +
                    " - Immatriculation : " + voiture.getImmatriculation());
        } else {
            System.out.println("Voiture : Aucune voiture associée.");
        }

        if (client != null) {
            System.out.println("Client : " + client.get_nom() + " " + client.get_prenom() +
                    " - Téléphone : " + client.get_telephone());
        } else {
            System.out.println("Client : Aucun client associé.");
        }

        System.out.println("===============================");
    }

    // Redéfinition de la méthode toString
    @Override
    public String toString() {
        return "id rendez-vous : " + this.id_rendez_vous +
                "\n date rendez-vous : " + this.Date_rendez_vous +
                "\n Description rendez-vous : " + this.Description_rendez_vous +
                "\n voiture concernée : " + this.voiture.get_immatriculation() +
                "\n client concerné : " + this.client.get_id() +
                "\n statut : " + this.statut;
    }


}
