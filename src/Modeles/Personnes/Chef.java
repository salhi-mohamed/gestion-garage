
package Modeles.Personnes;

import java.util.Set;
import java.util.HashSet;
import Modeles.Exceptions.*;
public class Chef extends Employe implements GestionExperience {

    // ===================== Attributs =====================
    private Set<Employe> equipe; // L'équipe du chef (ensemble d'employés)

    // ===================== Constructeur =====================
    // Initialise un chef avec ses informations personnelles et une équipe vide.
    public Chef(int id, String nom, String prenom, int telephone, String adresse, double salaire, String dateEmbaucheStr) {
        super(id, nom, prenom, telephone, adresse, salaire, dateEmbaucheStr); // Appel au constructeur de la classe parente
        this.equipe = new HashSet<>(); // Initialisation de l'équipe
    }

    // ===================== Getters =====================
    // Retourne l'équipe du chef.
    public Set<Employe> getEquipe() {
        return equipe;
    }
    // ===================== Setters =====================
    public void set_equipe(Set<Employe> equipe)
    {
        this.equipe=equipe;    
    }
    // ===================== Méthodes de gestion d'équipe =====================
    
    // Ajoute un employé à l'équipe.
    public void ajouterEmploye(Employe employe) throws EmployeExistantException {
        if (employe == null) {
            throw new IllegalArgumentException("Veuiller spécifier l'employé.");
        }

        // Vérification si l'employé existe déjà dans l'équipe par son ID
        for (Employe e : equipe) {
            if (e.get_id() == employe.get_id()) {
                throw new EmployeExistantException("L'employé avec l'ID " + employe.get_id() + " existe déjà dans l'équipe du chef : " + this.get_nom());
            }
        }

        equipe.add(employe); // Ajout de l'employé
        System.out.println("L'employé " + employe.get_nom() + " a été ajouté à l'équipe.");
    }

    // Supprime un employé de l'équipe.
    public void supprimerEmploye(Employe employe) throws EmployeNonTrouveException {
        if (equipe.contains(employe)) {
            equipe.remove(employe); // Suppression de l'employé
            System.out.println("L'employé " + employe.get_nom() + " a été supprimé de l'équipe.");
        } else {
            throw new EmployeNonTrouveException("L'employé avec l'ID " + employe.get_id() + " n'a pas été trouvé dans l'équipe du chef " + this.get_nom());
        }
    }

    // Affiche les membres de l'équipe.
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

    // ===================== Méthodes spécifiques =====================
    // Affiche le niveau du chef en fonction de son expérience.
    @Override
    public void afficherNiveau(int experience) {
        if (experience < 2) {
            System.out.println("Chef : Niveau Junior - " + experience + " années de travail dans ce garage.");
        } else if (experience < 5) {
            System.out.println("Chef : Niveau Confirmé - " + experience + " années de travail dans ce garage.");
        } else {
            System.out.println("Chef : Niveau Senior - " + experience + " années de travail dans ce garage.");
        }
    }
}
