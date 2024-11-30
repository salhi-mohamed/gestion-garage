package Modeles.Gestion_Service;

import Modeles.Personnes.Client;
import javafx.beans.property.*;

public class Voiture {

    // Attributs classiques
    private String marque;
    private String modele;
    private int annee;
    private long kilometrage;
    private String immatriculation; 
    private Client client;

    // Propriétés JavaFX pour liaison avec l'interface graphique
    private StringProperty marqueProperty;
    private StringProperty modeleProperty;
    private IntegerProperty anneeProperty;
    private LongProperty kilometrageProperty;
    private StringProperty immatriculationProperty;
    private ObjectProperty<Client> clientProperty;

    // Constructeur
    public Voiture(String marque, String modele, int annee, long kilometrage, String immatriculation , Client client ) {
        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.kilometrage = kilometrage;
        this.immatriculation = immatriculation;
        this.client = client;

        // Initialisation des propriétés JavaFX
        this.marqueProperty = new SimpleStringProperty(marque);
        this.modeleProperty = new SimpleStringProperty(modele);
        this.anneeProperty = new SimpleIntegerProperty(annee);
        this.kilometrageProperty = new SimpleLongProperty(kilometrage);
        this.immatriculationProperty = new SimpleStringProperty(immatriculation);
        this.clientProperty = new SimpleObjectProperty<>(client);
    }

    // Getters pour les propriétés JavaFX
    public StringProperty marqueProperty() {
        return marqueProperty;
    }

    public StringProperty modeleProperty() {
        return modeleProperty;
    }

    public IntegerProperty anneeProperty() {
        return anneeProperty;
    }

    public LongProperty kilometrageProperty() {
        return kilometrageProperty;
    }

    public StringProperty immatriculationProperty() {
        return immatriculationProperty;
    }

    public ObjectProperty<Client> clientProperty() {
        return clientProperty;
    }

    // Getters classiques pour les attributs
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

    // Méthodes pour afficher les informations de la voiture et du client
    public void afficher() {
        System.out.println("=== Informations de la Voiture ===");
        System.out.println("Marque : " + marque);
        System.out.println("Modèle : " + modele);
        System.out.println("Année : " + annee);
        System.out.println("Kilométrage : " + kilometrage);
        System.out.println("Immatriculation : " + immatriculation);
        System.out.println("Client : " + client.get_id());
    }

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
                '}';
    }

    public String get_immatriculation() {
        return this.immatriculation;
    }

}
