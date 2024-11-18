package Modeles.Gestion_Service;

@FunctionalInterface
public interface VerificateurStatutRendezVous {
    boolean verifier(Rendez_vous rendezVous);
}
