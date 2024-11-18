package Modeles.Gestion_Service;
import Modeles.Personnes.Client;
import java.util.ArrayList;
import java.util.Date;
public class Facture {
    // Attributs
    private int numeroFacture;
    private double montantTotal;
    private Date dateFacture;
    private Client client;
    private ArrayList<Service> services;
    private boolean avecRemise;


    // Constructeur


    public Facture(int numeroFacture, double montantTotal, Date dateFacture, Client client, ArrayList<Service> services, boolean avecRemise) {
        this.numeroFacture = numeroFacture;
        this.montantTotal = montantTotal;
        this.dateFacture = dateFacture;
        this.client = client;
        this.services = services;
        this.avecRemise = avecRemise;
    }

    //geters & seters


    public int getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(int numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public boolean isAvecRemise() {
        return avecRemise;
    }

    public void setAvecRemise(boolean avecRemise) {
        this.avecRemise = avecRemise;
    }



    ////////////////////////// Méthodes //////////////////////////

    // Méthode pour modifier les informations de la facture
    public void modifierFacture(Date nouvelleDate, Client nouveauClient, boolean nouvelleRemise) {
        this.dateFacture = nouvelleDate;
        this.client = nouveauClient;
        this.avecRemise = nouvelleRemise;
        System.out.println("Informations de la facture modifiées avec succès.");
    }

    // Méthode pour ajouter un service à la facture
    public void ajouterService(Service service) {
        services.add(service);
        montantTotal += service.getCout(); // Ajouter le coût du service au montant total
        System.out.println("Service ajouté avec succès à la facture.");
    }

    // Méthode pour supprimer un service par son index
    public void supprimerService(int index) {
        if (services != null && index >= 0 && index < services.size()) {
            Service service = services.remove(index); // Supprimer le service à l'indice spécifié
            montantTotal -= service.getCout(); // Soustraire le coût du service du montant total
            System.out.println("Service supprimé avec succès de la facture.");
        } else {
            System.out.println("Index invalide ou liste de services vide.");
        }
    }

    // Méthode pour afficher les informations de la facture
    public void afficherFacture() {
        System.out.println("=== Informations de la Facture ===");
        System.out.println("Numéro de facture : " + numeroFacture);
        System.out.println("Date de facture : " + dateFacture);
        System.out.println("Client : " + client);
        System.out.println("Montant total : " + montantTotal);
        System.out.println("Avec remise : " + (avecRemise ? "Oui" : "Non"));
        System.out.println("Services inclus : ");
        for (Service service : services) {
            System.out.println("  - " + service);
        }
    }

    // Méthode pour calculer le total de la facture avec remise si applicable
    public double totalFacture() {
        double total = montantTotal;
        if (avecRemise) {
            total *= 0.9; // Appliquer une remise de 10%
        }
        return total;
    }


    @Override
    public String toString() {
        return "Facture{" +
                "numeroFacture=" + numeroFacture +
                ", montantTotal=" + montantTotal +
                ", dateFacture=" + dateFacture +
                ", client=" + client +
                ", services=" + services +
                ", avecRemise=" + avecRemise +
                '}';
    }


    // Méthode pour afficher les services dont le coût est supérieur à une valeur donnée
    public void afficherServicesAvecCoutSuperieur(double coutMinimum) {
        services.stream()
                .filter(service -> service.getCout() > coutMinimum)  // Filtrer les services par coût
                .forEach(System.out::println);    // Afficher chaque service correspondant
    }


}
