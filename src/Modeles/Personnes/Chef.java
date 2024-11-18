/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles.Personnes;
import java.util.Set;
import java.util.HashSet;
import Modeles.Exceptions.*;
/**
 *
 * @author LENOVO
 */
public class Chef extends Employe implements GestionExperience {
    // Attributs
    private Set<Employe> equipe; // L'équipe du chef (ensemble d'employés)

    // Constructeur
    public Chef(int id, String nom, String prenom, int telephone, String adresse, double salaire) {
        super(id, nom, prenom, telephone, adresse, salaire); // Appel au constructeur parent (Employe)
        this.equipe = new HashSet<>(); // Initialisation de l'ensemble d'employés
    }

    // Méthode pour ajouter un employé à l'équipe
   public void ajouterEmploye(Employe employe) throws EmployeExistantException {
    if (employe == null) {
        throw new IllegalArgumentException("Veuiller spécifier l'employé .");
    }

    // Vérification si l'employé existe déjà dans l'équipe par son ID
    for (Employe e : equipe) {
        if (e.get_id() == employe.get_id()) {
            throw new EmployeExistantException("L'employé avec l'ID " + employe.get_id() + " existe déjà dans l'équipe du chef : "+this.get_nom());
        }
    }

    // Si l'employé n'est pas trouvé, il est ajouté à l'équipe
    equipe.add(employe);
    System.out.println("L'employé " + employe.get_nom() + " a été ajouté à l'équipe.");
}


    // Méthode pour supprimer un employé de l'équipe
    public void supprimerEmploye(Employe employe) throws EmployeNonTrouveException {
    // Vérifier si l'employé existe dans l'équipe
    if (equipe.contains(employe)) {
        equipe.remove(employe); // Supprimer l'employé de l'équipe
        System.out.println("L'employé " + employe.get_nom() + " a été supprimé de l'équipe.");
    } else {
        // Si l'employé n'est pas dans l'équipe, lancer une exception
        throw new EmployeNonTrouveException("L'employé avec l'ID " + employe.get_id() + " n'a pas été trouvé dans l'équipe du chef "+ this.get_nom());
    }
}


    // Méthode pour afficher les membres de l'équipe
  public void afficherEquipe() throws EquipeVideException {
    if (equipe.isEmpty()) {
        throw new EquipeVideException("L'équipe est vide. Aucun employé n'a été ajouté.");
    } else {
        System.out.println("Équipe de " + get_nom() + ":");
        for (Employe employe : equipe) {
            System.out.println(employe); // Affiche les détails de chaque employé
        }
    }
}


    // Getter pour l'équipe
    public Set<Employe> getEquipe() {
        return equipe;
    }
@Override
public void afficherNiveau(int experience) {
        if (experience < 2) {
            System.out.println("Chef : Niveau Junior - "+experience+" années de travail dans ce garage. ");
        } else if (experience < 5) {
            System.out.println("Chef : Niveau Confirmé - "+experience+" années de travail dans ce garage. ");
        } else {
            System.out.println("Chef : Niveau Senior - "+experience+" années de travail dans ce garage. ");
        }
    }
}
