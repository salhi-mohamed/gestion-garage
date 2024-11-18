package Modeles.Personnes;

import java.time.LocalDate;

@FunctionalInterface
public interface GestionExperience {
    // Méthode abstraite obligatoire dans une interface fonctionnelle
    void afficherNiveau(int experience);

    // Méthode par défaut pour calculer l'expérience
    default int calculerExperience(LocalDate dateEmbauche) {
        LocalDate aujourdHui = LocalDate.now();
        return java.time.Period.between(dateEmbauche, aujourdHui).getYears(); // Retourne l'expérience en années
    }
}
