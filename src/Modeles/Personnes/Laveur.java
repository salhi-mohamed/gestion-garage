
package Modeles.Personnes;
import Modeles.Gestion_Service.Voiture;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import Modeles.Exceptions.*;
public class Laveur extends Employe implements GestionExperience{
    //-------------------Attributs-----------------------------
    private boolean specialise_interieur;
    private boolean specialise_exterieur;
    private ArrayList<Voiture> voitures;
    //------------------------constructeur-------------------------------
   public Laveur(int id, String nom, String prenom, int telephone, String adresse, double salaire, String dateEmbaucheStr) {
        // Call the parent constructor (Employe) to initialize the shared attributes
        super(id, nom, prenom, telephone, adresse, salaire, dateEmbaucheStr);
        
        // Initialize the Scanner for input and the specialisation choice
        Scanner sc = new Scanner(System.in);    
        char specialite; // Déclaration de la variable 'specialite'
        
        // Loop until a valid specialization is chosen
        do {
            System.out.println("Quelle est la spécialité de ce laveur ? I : Intérieur / E : Extérieur ");
            specialite = sc.next().charAt(0); // Affectation à la variable specialite

            switch (specialite) {
                case 'E':
                case 'e':
                    this.specialise_exterieur = true;
                    this.specialise_interieur = false;
                    break;
                case 'I':
                case 'i':  
                    this.specialise_interieur = true;
                    this.specialise_exterieur = false;
                    break;
                default: 
                    System.out.println("Choix invalide !");
            }
        } while (specialite != 'I' && specialite != 'i' && specialite != 'E' && specialite != 'e');
        
        // Initialize the list of voitures
        voitures = new ArrayList<Voiture>();
    }
    public Laveur(int id, String nom, String prenom, int telephone, String adresse, double salaire, String dateEmbaucheStr, String specialiteLaveur) {
        super(id, nom, prenom, telephone, adresse, salaire, dateEmbaucheStr);
        
        // Set the specialization directly from the passed parameter
        if ("Extérieur".equalsIgnoreCase(specialiteLaveur)) {
            this.specialise_exterieur = true;
            this.specialise_interieur = false;
        } else if ("Intérieur".equalsIgnoreCase(specialiteLaveur)) {
            this.specialise_interieur = true;
            this.specialise_exterieur = false;
        } else {
            // Default case if something goes wrong (although this shouldn't happen in the controller)
            this.specialise_interieur = false;
            this.specialise_exterieur = false;
        }

        // Initialize the list of voitures
        voitures = new ArrayList<Voiture>();
    }
    //-----------------------geters & seters-------------------------------

    public void set_specialise_interieur(boolean spec )
    {
        this.specialise_interieur=spec;
    }

    public boolean get_specialise_interieur()
    {
        return this.specialise_interieur;
    }

    public void set_specialise_exterieur(boolean spec)
    {
        this.specialise_exterieur=spec;
    }

    public boolean get_specialise_exterieur()
    {
        return this.specialise_exterieur;
    }

    public ArrayList<Voiture> getVoitures() {
        return voitures;
    }

    public void setVoitures(ArrayList<Voiture> voitures) {
        this.voitures = voitures;
    }

    //////////Methodes////////////////

//Méthode toString()
    @Override
    public String toString()
    {
        if (this.specialise_exterieur)
            return super.toString()+" specialité : extérieur";
        else
            return super.toString()+" spécialité : intérieur";
    }

//Méthode pour afficher un laveur 
  public void afficher() {
    super.afficher();
    if (this.specialise_exterieur)
        System.out.println("Spécialité : extérieur");
    else
        System.out.println("Spécialité : intérieur");

    try {
        this.afficher_historique_voitures(); // Cette méthode peut lancer HistoriqueVoituresVideException
    } catch (HistoriqueVoituresVideLavMecException e) {
        System.out.println(e.getMessage()); // Affiche le message de l'exception si l'historique est vide
    }
}
  //Méthode pour modifier un laveur
   @Override
public void modifier() {
    int choice;
    Scanner sc = new Scanner(System.in);

    do {
        // Afficher le menu de modification
        do {
            System.out.println("Que voulez-vous modifier ?");
            System.out.println("1 : nom");
            System.out.println("2 : prenom");
            System.out.println("3 : adresse");
            System.out.println("4 : telephone");
            System.out.println("5 : salaire");
            System.out.println("6 : date d'embauche");
            System.out.println("7 : spécialité (intérieur / extérieur)");
            System.out.println("0 : quitter");

            // Vérifier que l'entrée est bien un entier
            while (!sc.hasNextInt()) {
                System.out.println("Saisie invalide, veuillez entrer un nombre entre 0 et 7.");
                sc.next();
            }
            choice = sc.nextInt();

            if (choice < 0 || choice > 7) {
                System.out.println("Choix invalide, veuillez entrer un nombre entre 0 et 7.");
            }
        } while (choice < 0 || choice > 7);

        // Traiter le choix de l'utilisateur
        sc.nextLine();  // Consommer le retour de ligne après nextInt()

        switch (choice) {
            case 1:
                System.out.println("Saisir le nouveau nom : ");
                super.set_nom(sc.nextLine());
                System.out.println("Nom mis à jour.");
                break;
            case 2:
                System.out.println("Saisir le nouveau prénom : ");
                super.set_prenom(sc.nextLine());
                System.out.println("Prénom mis à jour.");
                break;
            case 3:
                System.out.println("Saisir la nouvelle adresse : ");
                super.set_adresse(sc.nextLine());
                System.out.println("Adresse mise à jour.");
                break;
            case 4:
                System.out.println("Saisir le nouveau téléphone : ");
                super.set_telephone(sc.nextInt());
                sc.nextLine();
                System.out.println("Téléphone mis à jour.");
                break;
            case 5:
                System.out.println("Saisir le nouveau salaire : ");
                setSalaire(sc.nextDouble());
                sc.nextLine();
                System.out.println("Salaire mis à jour.");
                break;
            case 6:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                boolean dateValide = false;
                while (!dateValide) {
                    System.out.println("Veuillez entrer la nouvelle date d'embauche (format: dd/MM/yyyy) : ");
                    String dateInput = sc.nextLine();
                    try {
                        LocalDate newDate = LocalDate.parse(dateInput, formatter);
                        set_date_embauche(newDate);
                        System.out.println("Date d'embauche mise à jour : " + newDate);
                        dateValide = true;
                    } catch (DateTimeParseException e) {
                        System.out.println("Format de date invalide. Utilisez le format dd/MM/yyyy.");
                    }
                }
                break;
            case 7:
                char specialite;
                do {
                    System.out.println("Saisir la nouvelle spécialité (I : Intérieur / E : Extérieur) : ");
                    specialite = sc.next().charAt(0);
                    if (specialite == 'I' || specialite == 'i') {
                        set_specialise_interieur(true);
                        set_specialise_exterieur(false);
                        System.out.println("Spécialité mise à jour : Intérieur.");
                    } else if (specialite == 'E' || specialite == 'e') {
                        set_specialise_exterieur(true);
                        set_specialise_interieur(false);
                        System.out.println("Spécialité mise à jour : Extérieur.");
                    } else {
                        System.out.println("Choix invalide ! Utilisez 'I' pour Intérieur ou 'E' pour Extérieur.");
                    }
                } while (specialite != 'I' && specialite != 'i' && specialite != 'E' && specialite != 'e');
                break;
            case 0:
                System.out.println("Quitter le menu.");
                break;
        }

    } while (choice != 0);

    sc.close();  // Fermer le scanner après utilisation
}
                  //---------------------Gestion des voitures------------------------

//Méthode pour ajouter une voiture à l'historique des voitures du laveur 
 public void ajouter_voiture(Voiture voiture) throws VoitureExistanteDejaPourLavMecException {
    // Vérifier si la voiture existe déjà dans la liste
    if (this.getVoitures().contains(voiture)) {
        throw new VoitureExistanteDejaPourLavMecException("La voiture existe déjà pour ce laveur.");
    }
    this.voitures.add(voiture);  // Ajouter la voiture à la liste si elle n'existe pas déjà
    System.out.println("Voiture ajoutée avec succès !");
}
 //Méthode pour supprimer une voiture de l'historique des voitures du laveur 

 public void supprimer_voiture(Voiture voiture) throws VoitureNonTrouveePourLavMecException {
    // Vérifier si la voiture existe dans la liste
    if (!this.getVoitures().contains(voiture)) {
        throw new VoitureNonTrouveePourLavMecException("La voiture n'a pas été trouvée pour ce laveur.");
    }
    this.voitures.remove(voiture);  // Supprimer la voiture de la liste
    System.out.println("Voiture supprimée avec succès !");
}
 //Méthode pour afficher l'histrique des voitures d'un laveur 
 
 public void afficher_historique_voitures() throws HistoriqueVoituresVideLavMecException {
        if (this.getVoitures().isEmpty()) {
            throw new HistoriqueVoituresVideLavMecException("Aucune voiture lavée dans l'historique.");
        } else {
            System.out.println("Historique des voitures lavées :");
            for (Voiture voiture : voitures) {
                System.out.println(voiture); // Affiche chaque voiture dans l'historique
            }
        }
    }
   
   @Override
    public void afficherNiveau(int experience) {
        if (experience < 2) {
            System.out.println("Laveur : Niveau Junior - "+experience+" années de travail dans ce garage. ");
        } else if (experience < 5) {
            System.out.println("Laveur : Niveau Confirmé - "+experience+" années de travail dans ce garage. ");
        } else {
            System.out.println("Laveur : Niveau Senior - "+experience+" années de travail dans ce garage. ");
        }
    } 
}






                     

   