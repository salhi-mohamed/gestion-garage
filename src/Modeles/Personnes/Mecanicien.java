/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles.Personnes;
import Modeles.Gestion_Service.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Exception;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import Modeles.Exceptions.*;



/**
 *
 * @author LENOVO
 */
public class Mecanicien extends Employe implements GestionExperience{

    //--------------------Attributs--------------------

    private String specialite;
    private Expertise expertise;  // Ajout de l'attribut d'expertise
    private ArrayList<Voiture> historique_voitures;

    //--------------------constructeur--------------------

    
  
     
  public Mecanicien(int id, String nom, String prenom, int telephone, String adresse, double salaire, String specialite, Expertise expertise, String date_embauche) {
    // Appel du constructeur de la classe Employe avec la date d'embauche
    super(id, nom, prenom, telephone, adresse, salaire, date_embauche);

    // Initialisation des attributs spécifiques à Mecanicien
    this.specialite = specialite;
    this.expertise = expertise;  // Initialisation de l'expertise

    // Initialisation de la liste de voitures (Historique_voitures)
    this.historique_voitures = new ArrayList<Voiture>(); // Important : spécifier le type Voiture
}   
    //--------------------Methodes--------------------

  
      //--------------------Geters & seters--------------------

    public void set_specialite(String specialite)
    {
        this.specialite=specialite;
    }

    public String get_specialite()
    {
        return this.specialite;
    }

    public void set_historique_voitures(ArrayList<Voiture> voitures)
    {
        this.historique_voitures=voitures;
    }

    public ArrayList<Voiture> get_historique_voitures()
    {
        return this.historique_voitures;
    }

   public void set_expertise(Expertise expertise) {
    this.expertise = expertise;
   }
   public Expertise get_expertise() {
    return this.expertise;
}
   //Méthode pour afficher un mécanicien
public void afficher()
{
    super.afficher();
    System.out.println("Spécialité : "+ this.get_specialite());
        System.out.println("Expertise : "+ this.get_expertise());

    try{
        this.afficher_historique_voitures();
    }
    catch(HistoriqueVoituresVideLavMecException e)
    {
        System.out.println( e.getMessage());
    }
    
    
}
//Méthode pour modifier un mécanicien
public void modifier() {
    int choice;
    Scanner sc = new Scanner(System.in);

    do {
        // Menu pour modifier les informations
        System.out.println("Que voulez-vous modifier ?");
        System.out.println("1 : nom");
        System.out.println("2 : prénom");
        System.out.println("3 : adresse");
        System.out.println("4 : téléphone");
        System.out.println("5 : salaire");
        System.out.println("6 : date d'embauche");
        System.out.println("7 : spécialité");
        System.out.println("8 : expertise");
        System.out.println("0 : quitter");

        // Vérifier que l'entrée est bien un entier
        while (!sc.hasNextInt()) {
            System.out.println("Saisie invalide, veuillez entrer un nombre entre 0 et 8.");
            sc.next(); // Consomme l'entrée invalide
        }
        choice = sc.nextInt();

        // Vérifier si l'entier est dans la plage valide (0 à 8)
        if (choice < 0 || choice > 8) {
            System.out.println("Choix invalide, veuillez entrer un nombre entre 0 et 8.");
            continue; // Retourne au début de la boucle pour un nouveau choix
        }

        // Traiter le choix valide
        sc.nextLine(); // Consommer le retour de ligne restant après nextInt()
        switch (choice) {
            case 1:
                System.out.println("Saisir le nouveau nom : ");
                String new_name = sc.nextLine();
                super.set_nom(new_name);
                System.out.println("Nom mis à jour.");
                break;

            case 2:
                System.out.println("Saisir le nouveau prénom : ");
                String new_firstname = sc.nextLine();
                super.set_prenom(new_firstname);
                System.out.println("Prénom mis à jour.");
                break;

            case 3:
                System.out.println("Saisir la nouvelle adresse : ");
                String new_address = sc.nextLine();
                super.set_adresse(new_address);
                System.out.println("Adresse mise à jour.");
                break;

            case 4:
                // Gestion de la saisie du numéro de téléphone
                boolean validPhone = false;
                int new_telephone = 0;
                while (!validPhone) {
                    System.out.println("Saisir le nouveau téléphone (format: 1234567890) : ");
                    String phoneInput = sc.nextLine();
                    try {
                        new_telephone = Integer.parseInt(phoneInput);
                        validPhone = true; // Sortie de la boucle si la saisie est valide
                    } catch (NumberFormatException e) {
                        System.out.println("Saisie invalide, veuillez entrer un numéro de téléphone valide.");
                    }
                }
                super.set_telephone(new_telephone);
                System.out.println("Téléphone mis à jour.");
                break;

            case 5:
                // Gestion de la saisie du salaire
                boolean validSalary = false;
                double new_salary = 0.0;
                while (!validSalary) {
                    System.out.println("Saisir le nouveau salaire : ");
                    String salaryInput = sc.nextLine();
                    try {
                        new_salary = Double.parseDouble(salaryInput);
                        validSalary = true; // Sortie de la boucle si la saisie est valide
                    } catch (NumberFormatException e) {
                        System.out.println("Saisie invalide, veuillez entrer un montant de salaire valide.");
                    }
                }
                super.setSalaire(new_salary);
                System.out.println("Salaire mis à jour.");
                break;

            case 6:
                System.out.println("Saisir la nouvelle date d'embauche (format: dd/MM/yyyy) : ");
                String dateInput = sc.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    LocalDate newDateEmbauche = LocalDate.parse(dateInput, formatter);
                    super.set_date_embauche(newDateEmbauche);
                    System.out.println("Date d'embauche mise à jour.");
                } catch (DateTimeParseException e) {
                    System.out.println("Format de date invalide. Utilisez le format dd/MM/yyyy.");
                }
                break;

            case 7:
                System.out.println("Saisir la nouvelle spécialité : ");
                String nouvelleSpecialite = sc.nextLine();
                this.specialite = nouvelleSpecialite;
                System.out.println("Spécialité mise à jour.");
                break;

            case 8:
                System.out.println("Choisissez la nouvelle expertise : ");
                for (Expertise e : Expertise.values()) {
                    System.out.println(e.ordinal() + " : " + e); // Affiche chaque expertise avec un numéro correspondant
                }
                int expertiseChoice = sc.nextInt();
                if (expertiseChoice >= 0 && expertiseChoice < Expertise.values().length) {
                    this.set_expertise(Expertise.values()[expertiseChoice]);
                    System.out.println("Expertise mise à jour.");
                } else {
                    System.out.println("Choix d'expertise invalide.");
                }
                sc.nextLine(); // Consomme le retour de ligne
                break;

            case 0:
                System.out.println("Sortie du menu de modification.");
                break;

            default:
                System.out.println("Choix invalide.");
                break;
        }

    } while (choice != 0); // Répète jusqu'à ce que l'utilisateur choisisse de quitter
}
    //----------------------------Méthodes de gestion de l'historique des voitures d'un mécanicien--------------------
   
   //Méthode pour ajouter une voiture à l'hitorique des voitures du mécanicien
     public void ajouter_voiture(Voiture voiture) throws VoitureExistanteDejaPourLavMecException {
    // Vérifier si la voiture existe déjà dans l'historique
    for (Voiture v : historique_voitures) {
        if (v.equals(voiture)) {
            throw new VoitureExistanteDejaPourLavMecException("La voiture existe déjà dans l'historique du mécanicien.");
        }
    }
    // Si la voiture n'existe pas déjà, on l'ajoute à l'historique
    historique_voitures.add(voiture);
    System.out.println("Voiture ajoutée à l'historique du mécanicien avec succès !");
}

    
     
   //Méthode pour supprimer une voiture de l'hitorique des voitures du mécanicien

public void supprimer_voiture(Voiture voiture) throws VoitureNonTrouveePourLavMecException {
        boolean found = false;

        // Recherche de la voiture dans l'historique
        for (Voiture v : historique_voitures) {
            if (v.equals(voiture)) {
                historique_voitures.remove(v);  // Supprimer la voiture de l'historique
                System.out.println("La voiture a été supprimée de l'historique.");
                found = true;
                break;  // Quitter la boucle dès que la voiture est trouvée
            }
        }

        // Si la voiture n'a pas été trouvée, lancer l'exception
        if (!found) {
            throw new VoitureNonTrouveePourLavMecException("La voiture spécifiée n'a pas été trouvée dans l'historique.");
        }
    }







//Méthode pour afficher l'historique des voitures du mécanicien
    public void afficher_historique_voitures() throws HistoriqueVoituresVideLavMecException {
        if (this.get_historique_voitures().isEmpty()) {
            throw new HistoriqueVoituresVideLavMecException("Aucune voiture  dans l'historique.");
        } else {
            System.out.println("Historique des voitures  :");
            for (Voiture voiture : this.historique_voitures) {
                System.out.println(voiture); // Affiche chaque voiture dans l'historique
            }
        }
    }
   @Override
   //------------------------------------------------------------------------------------------------------------
   //Méthode ToString()
public String toString() {
    return super.toString() + "\n" +
           "Spécialité : " + this.specialite + "\n" + 
           "Expertise : " + this.expertise;
}  
  //interface fonctionnelle
  @Override
    public void afficherNiveau(int experience) {
        if (experience < 2) {
            System.out.println("Mécanicien : Niveau Junior - "+experience+" années de travail dans ce garage. ");
        } else if (experience < 5) {
            System.out.println("Mécanicien : Niveau Confirmé - "+experience+" années de travail dans ce garage. ");
        } else {
            System.out.println("Mécanicien : Niveau Senior - "+experience+" années de travail dans ce garage. ");
        }
    }
   

    // Setter pour l'expertise
   

    public String getExpertiseAsString() {
        return expertise != null ? expertise.toString() : "Aucune expertise";
    }
  
}

















    
            
    

