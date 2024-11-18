package Modeles.Gestion_Service;
import Modeles.Personnes.Client;
import java.util.ArrayList;
import java.util.Date;
import Modeles.Gestion_Service.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;



public class Rendez_vous {
    //attribut
   private int id_rendez_vous;
   private LocalDate Date_rendez_vous;
   private String Description_rendez_vous;
   private StatutRendezVous statut;
   private Voiture voiture;
   private Client client;





    // Constructeur
    public Rendez_vous(int id_rendez_vous, String Description_rendez_vous, Voiture voiture, Client client) {
        this.id_rendez_vous = id_rendez_vous;
        this.Description_rendez_vous = Description_rendez_vous;
        this.voiture = voiture;
        this.client = client;
        this.statut = StatutRendezVous.EN_ATTENTE;  // Statut initial du rendez-vous
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner scanner = new Scanner(System.in);
        boolean dateValide = false;
        while (!dateValide) {
            System.out.println("Veuillez entrer la date du rendez-vous (format: dd/MM/yyyy) : ");
            String dateInput = scanner.nextLine();
            try {
                this.Date_rendez_vous = LocalDate.parse(dateInput, formatter);
                dateValide = true;
            } catch (DateTimeParseException e) {
                System.out.println("Format de date invalide. Utilisez le format dd/MM/yyyy.");
            }
        }
    }


   //geter et seter
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

                         /////////Mehode */////////////

    // Méthode pour annuler le rendez-vous
    public void annulerRendezVous() {
        if (this.statut != StatutRendezVous.ANNULE) {
            this.statut = StatutRendezVous.ANNULE;
            System.out.println("Le rendez-vous avec le client " + this.client.get_nom() + " a été annulé.");
        } else {
            System.out.println("Le rendez-vous est déjà annulé.");
        }
    }


    public void ajouter_client(Client C){
        
    {
        this.client=client;
    }
    }

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











