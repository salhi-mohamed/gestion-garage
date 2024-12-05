package Modeles.Gestion_Service.record;

import java.time.LocalDate; // Pour représenter la date de la facture
import java.util.List;      // Pour gérer une liste d'objets

import Modeles.Stocks.Fourniture; // Pour représenter les fournitures incluses dans la facture
import Modeles.Personnes.Client;  // Pour représenter le client associé à la facture


public record Facture_record(
        int idFacture,            // Identifiant unique de la facture
        LocalDate dateFacture,    // Date de création ou d'émission de la facture
        Client client,            // Client associé à la facture
        double montantTotal,      // Montant total de la facture
        List<Fourniture> fournitures // Liste des fournitures incluses dans cette facture
) {
}
