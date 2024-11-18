/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles.Personnes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


/**
 *
 * @author LENOVO
 */
public class Employe extends Personne{

    //Attributs
    private  LocalDate  date_embauche;//consider putting final
    private double salaire;

    //constructeur
    public Employe(int id , String nom , String prenom , int telephone , String adresse,double salaire)
    {
        super(id,nom,prenom,telephone,adresse);
        this.salaire=salaire;
       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        Scanner scanner = new Scanner(System.in);
        boolean dateValide = false;
        
        // Boucle jusqu'à ce que l'utilisateur entre une date valide
        while (!dateValide) {
            System.out.println("Veuillez entrer la date d'embauche (format: dd/MM/yyyy) : ");
            String dateInput = scanner.nextLine();
            
            try {
                // Convertir la chaîne saisie en LocalDate
                this.date_embauche = LocalDate.parse(dateInput, formatter);
                System.out.println("Date d'embauche enregistrée : " + this.date_embauche);
                dateValide = true;  // Si aucune exception, la date est valide, on sort de la boucle
            } catch (DateTimeParseException e) {
                // En cas de format de date incorrect
                System.out.println("Format de date invalide. Utilisez le format dd/MM/yyyy.");
            } 
        
        
    }

}

                                //***********/Methodes////////////////



    public void afficher()
    {
        super.afficher();
        System.out.println("Salaire : " + this.get_salaire());
        System.out.println("Date d embauche : " + this.get_date_embauche());
    }
    public void set_salaire(double salaire)
    {
        this.salaire=salaire;
    }
    public void set_date_embauche(LocalDate date_embauche) {
    this.date_embauche = date_embauche;
    }   






@Override    
  public void modifier() {
    int choice;
    Scanner sc = new Scanner(System.in);

    do {
        // Boucle pour forcer un choix valide (entre 0 et 6)
        do {
            System.out.println("Que voulez-vous modifier ?");
            System.out.println("1 : nom");
            System.out.println("2 : prenom");
            System.out.println("3 : adresse");
            System.out.println("4 : telephone");
            System.out.println("5 : salaire");
            System.out.println("6 : date d'embauche");
            System.out.println("0 : quitter");

            // Vérifier que l'entrée est bien un entier
            while (!sc.hasNextInt()) {
                System.out.println("Saisie invalide, veuillez entrer un nombre entre 0 et 6.");
                sc.next(); // Consomme l'entrée invalide
            }
            choice = sc.nextInt();

            // Vérifier si l'entier est dans la plage valide (0 à 6)
            if (choice < 0 || choice > 6) {
                System.out.println("Choix invalide, veuillez entrer un nombre entre 0 et 6.");
            }

        } while (choice < 0 || choice > 6);  // Répéter tant que l'utilisateur entre un choix invalide

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
                String new_adresse = sc.nextLine();
                super.set_adresse(new_adresse);
                System.out.println("Adresse mise à jour.");
                break;

            case 4:
                System.out.println("Saisir le nouveau téléphone : ");
                int new_tel = sc.nextInt();
                sc.nextLine(); // Consommer le retour de ligne
                super.set_telephone(new_tel);
                System.out.println("Téléphone mis à jour.");
                break;

            case 5:
                System.out.println("Saisir le nouveau salaire : ");
                double new_salary = sc.nextDouble();  // Utiliser double pour les salaires
                sc.nextLine(); // Consommer le retour de ligne
                set_salaire(new_salary);  // Utiliser une méthode set_salaire()
                System.out.println("Salaire mis à jour.");
                break;

            case 6:
                // Modification de la date d'embauche
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                boolean dateValide = false;

                while (!dateValide) {
                    System.out.println("Veuillez entrer la nouvelle date d'embauche (format: dd/MM/yyyy) : ");
                    String dateInput = sc.nextLine();
                    
                    try {
                        // Convertir la chaîne saisie en LocalDate
                        LocalDate new_date_embauche = LocalDate.parse(dateInput, formatter);
                        set_date_embauche(new_date_embauche);  // Méthode set_date_embauche()
                        System.out.println("Date d'embauche mise à jour : " + new_date_embauche);
                        dateValide = true; // Sortir de la boucle si aucune exception
                    } catch (DateTimeParseException e) {
                        // En cas de format de date incorrect
                        System.out.println("Format de date invalide. Utilisez le format dd/MM/yyyy.");
                    }
                }
                break;

            case 0:
                System.out.println("Quitter le menu.");
                break;
        }

    } while (choice != 0);  // Continuer tant que l'utilisateur ne choisit pas "quitter" (0)

    sc.close();  // Fermer le scanner après utilisation
}






    @Override
    
  public String toString() {
    return super.toString() + "\n" + "Salaire de l'employé : " + this.salaire + "\n" + "Date d'embauche de l'employé : " + this.date_embauche;
    }
  public int calcul_anciennette()
  {
      LocalDate today=LocalDate.now();
      int current_year=today.getYear();
      int experience=current_year-date_embauche.getYear();
      return experience;
  }
  public double get_salaire()
  {
      return this.salaire;
              
  }
  public LocalDate get_date_embauche()
  {
      return this.date_embauche;
  }






                       ///////////geters & seters /////////////


    public LocalDate getDate_embauche() {
        return date_embauche;
    }

    public void setDate_embauche(LocalDate date_embauche) {
        this.date_embauche = date_embauche;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }
}
