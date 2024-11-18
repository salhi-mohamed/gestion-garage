package Modeles.Gestion_Service.record;
import java.time.LocalDate;
import java.util.List;
import Modeles.Stocks.Fourniture;
import Modeles.Personnes.Client;
public record Facture_record(int idFacture, LocalDate dateFacture, Client client, double montantTotal, List<Fourniture> fournitures) {
}
