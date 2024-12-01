package Modeles.Personnes;
import java.util.Scanner;
import Modeles.Stocks.Fourniture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.InputMismatchException;
import java.util.HashSet;
import Modeles.Gestion_Service.Voiture;
import Modeles.Exceptions.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client extends Personne {
    private HashSet<Voiture> voitures;  // Liste des voitures du client
    private String statutFinancier;  
    private ArrayList<Fourniture>  fournitures_achetees ;// Statut financier du client
     private final IntegerProperty idProperty = new SimpleIntegerProperty();
    private final StringProperty nomProperty = new SimpleStringProperty();
    private final StringProperty prenomProperty = new SimpleStringProperty();

    // Constructeur
    public Client(int id, String nom, String prenom, int telephone, String adresse, String statutFinancier) {
        super(id, nom, prenom, telephone, adresse);
        this.voitures = new HashSet<Voiture>();
        this.statutFinancier = statutFinancier;
        this.fournitures_achetees=new ArrayList<Fourniture>();
    }

    public Client() {
        super();
    }


    // Getters et setters
    public HashSet<Voiture> getVoitures() {
        return voitures;
    }

    public void setVoitures(HashSet<Voiture> voitures) {
        this.voitures = voitures;
    }

    public String getStatutFinancier() {
        return statutFinancier;
    }

    public void setStatutFinancier(String statutFinancier) {
        this.statutFinancier = statutFinancier;
    }

    public ArrayList<Fourniture> getFournituresAchetees() {
        return fournitures_achetees;
    }

    public void setFournituresAchetees(ArrayList<Fourniture> fournituresAchetees) {
        this.fournitures_achetees = fournituresAchetees;
    }


    // Ajouter une voiture au client
   public void ajouterVoiture(Voiture voiture) throws VoitureDejaExistanteClientException {
    for (Voiture v : this.getVoitures()) {
        if (v.get_immatriculation().equals(voiture.get_immatriculation())) {
            throw new VoitureDejaExistanteClientException("Cette voiture existe déjà pour ce client.");
        }
    }
    this.voitures.add(voiture);
}
    

    // Retirer une voiture du client
 public void retirerVoiture(String immatriculation) throws VoitureNonTrouveeClientException, ArgumentInvalideException {
    if (immatriculation == null || immatriculation.isEmpty()) {
        throw new ArgumentInvalideException("L'immatriculation fournie est invalide.");
    }

    boolean voitureTrouvee = false;
    for (Voiture v : this.getVoitures()) {
        if (v.get_immatriculation().equals(immatriculation)) {
            this.voitures.remove(v);
            voitureTrouvee = true;
            break;
        }
    }

    if (!voitureTrouvee) {
        throw new VoitureNonTrouveeClientException("Aucune voiture trouvée avec l'immatriculation : " + immatriculation);
    }
}
 public void afficherVoitures() throws VoituresNonDisponiblesClientException {
        if (this.getVoitures() == null || this.getVoitures().isEmpty()) {
            throw new VoituresNonDisponiblesClientException("Aucune voiture disponible pour ce client.");
        }

        System.out.println("Liste des voitures de " + this.get_nom() + " " + this.get_prenom() + ":");
        for (Voiture voiture : this.getVoitures()) {
            System.out.println(voiture);
        }
    }



@Override
public void modifier() {
    Scanner scanner = new Scanner(System.in);
    int choix;

    do {
        // Afficher le menu de modification
        System.out.println("\nMenu de modification :");
        System.out.println("1. Modifier le nom");
        System.out.println("2. Modifier le prénom");
        System.out.println("3. Modifier le téléphone");
        System.out.println("4. Modifier l'adresse");
        System.out.println("5. Modifier le statut financier");
        System.out.println("6. Quitter");
        System.out.print("Choisissez une option (1-6): ");

        // Contrôle de saisie pour choisir une option
        while (true) {
            try {
                choix = scanner.nextInt();
                if (choix < 1 || choix > 6) {
                    System.out.print("Choix invalide. Veuillez entrer un nombre entre 1 et 6: ");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.print("Entrée invalide. Veuillez entrer un nombre entier: ");
                scanner.next(); // Nettoyer l'entrée
            }
        }

        scanner.nextLine(); // Consommer le retour à la ligne restant

        switch (choix) {
            case 1:
                // Modifier le nom
                System.out.print("Entrez le nouveau nom: ");
                String nouveauNom = scanner.nextLine();
                this.set_nom(nouveauNom);  // Utilisation du setter de la classe Personne
                System.out.println("Nom modifié avec succès.");
                break;

            case 2:
                // Modifier le prénom
                System.out.print("Entrez le nouveau prénom: ");
                String nouveauPrenom = scanner.nextLine();
                this.set_prenom(nouveauPrenom);  // Utilisation du setter de la classe Personne
                System.out.println("Prénom modifié avec succès.");
                break;

            case 3:
                // Modifier le téléphone
                System.out.print("Entrez le nouveau téléphone: ");
                int nouveauTelephone = scanner.nextInt();
                this.set_telephone(nouveauTelephone);  // Utilisation du setter de la classe Personne
                System.out.println("Téléphone modifié avec succès.");
                break;

            case 4:
                // Modifier l'adresse
                System.out.print("Entrez la nouvelle adresse: ");
                String nouvelleAdresse = scanner.nextLine();
                this.set_adresse(nouvelleAdresse);  // Utilisation du setter de la classe Personne
                System.out.println("Adresse modifiée avec succès.");
                break;

            case 5:
                // Modifier le statut financier
                System.out.print("Entrez le nouveau statut financier: ");
                String nouveauStatut = scanner.nextLine();
                this.statutFinancier = nouveauStatut;
                System.out.println("Statut financier modifié avec succès.");
                break;

            case 6:
                // Quitter
                System.out.println("Sortie du menu.");
                break;

            default:
                System.out.println("Option invalide.");
                break;
        }

    } while (choix != 6); // Répéter jusqu'à ce que l'utilisateur choisisse de quitter
}


    // Redéfinition de la méthode toString() pour afficher les informations du client
    
  @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(super.toString()).append("\n");
    sb.append("Statut financier : ").append(this.getStatutFinancier()).append("\n");

    // Vérification des voitures
    if (this.getVoitures() != null && !this.getVoitures().isEmpty()) {
        sb.append("Voitures : ").append(this.getVoitures().toString()).append("\n");
    } else {
        sb.append("Aucune voiture associée à ce client.\n");
    }

    // Vérification des fournitures achetées
    if (this.get_fournitures_achetees() != null && !this.get_fournitures_achetees().isEmpty()) {
        sb.append("Fournitures achetées : ").append(this.get_fournitures_achetees().toString()).append("\n");
    } else {
        sb.append("Pas de fournitures achetées pour ce client.\n");
    }

    return sb.toString();
}

    public ArrayList<Fourniture> get_fournitures_achetees()
    {
        return this.fournitures_achetees;
    }
    public void set_fournitures_achetees(ArrayList<Fourniture> fournitures)
    {
        this.fournitures_achetees=fournitures;
    }
    public void ajouterFourniture(Fourniture fourniture) throws FournitureExisteClientException {
    for (Fourniture f : fournitures_achetees) {
        if (f.getIdFourniture() == fourniture.getIdFourniture()) {
            throw new FournitureExisteClientException("La fourniture avec cet ID existe déjà pour ce client.");
        }
    }
    fournitures_achetees.add(fourniture);
    System.out.println("Fourniture ajoutée avec succès.");
}
    public void supprimerFourniture(Fourniture fourniture) throws FournitureNonTrouveeClientException {
    boolean trouvée = false;
    Iterator<Fourniture> iterator = fournitures_achetees.iterator();

    while (iterator.hasNext()) {
        Fourniture f = iterator.next();
        if (f.getIdFourniture() == fourniture.getIdFourniture()) {
            iterator.remove();
            trouvée = true;
            System.out.println("Fourniture supprimée avec succès.");
            break;
        }
    }

    if (!trouvée) {
        throw new FournitureNonTrouveeClientException("La fourniture n'existe pas dans la liste des fournitures achetées.");
    }
}
    public void afficherFournitures() {
    if (fournitures_achetees.isEmpty()) {
        System.out.println("Aucune fourniture achetée pour ce client.");
    } else {
        System.out.println("Liste des fournitures achetées par le client " + this.get_prenom() +" "+this.get_nom());

        Iterator<Fourniture> iterator = fournitures_achetees.iterator();
        while (iterator.hasNext()) {
            Fourniture fourniture = iterator.next();
            System.out.println(fourniture.toString()); // ou fourniture.afficherFourniture()
        }
    }
}
    public void add_car(Voiture v)
    {
        this.voitures.add(v);
    }

   
     public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(this.get_id());
    }

    public StringProperty nomProperty() {
        return new SimpleStringProperty(this.get_nom());
    }

    public StringProperty prenomProperty() {
        return new SimpleStringProperty(this.get_prenom());
    }

    public StringProperty statutFinancierProperty() {
        return new SimpleStringProperty(this.getStatutFinancier());
    }


}