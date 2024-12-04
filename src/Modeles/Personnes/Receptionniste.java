package Modeles.Personnes;
//import DAO.ClientDAO;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.Scanner;
import Modeles.Gestion_Service.*;

import java.util.Iterator;
import Modeles.Stocks.Fourniture;
import Modeles.Stocks.Piece_Rechange;
import Modeles.Exceptions.*;
import java.util.Optional;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.List;


public class Receptionniste extends Employe
{
    private int numeroBureau;
    private String email;
    public ArrayList<Rendez_vous> listeRendezVous; // Liste pour stocker les rendez-vous
    public ArrayList<Client> listeClients;
    public ArrayList<Voiture> ListeVoitures;
    private ArrayList<Fourniture> listeFournitures;
    private ArrayList<Piece_Rechange> listPiecesRechange;
    private ArrayList<Employe> ListeEmployes;
    private ArrayList<Service> ListeServices;
   private ArrayList<Facture> ListeFactures;
   String mdp;

   ///////////////////db/////////////////
   // private ClientDAO clientDAO;


    // --------------------Constructeur--------------------
   public Receptionniste(int id, String nom, String prenom, int telephone, String adresse, double salaire, String date_embauche, int numeroBureau, String email, String mdp) {
    // Appel du constructeur de la classe parente Employe pour initialiser les attributs hérités
    super(id, nom, prenom, telephone, adresse, salaire, date_embauche);

    // Initialisation des attributs spécifiques à Receptionniste
    this.numeroBureau = numeroBureau;
    this.email = email;
    this.mdp = mdp; // Initialisation du mot de passe (à ajouter selon votre logique)

    // Initialisation des listes pour gérer les différentes entités associées
    this.listeRendezVous = new ArrayList<Rendez_vous>();
    this.listeClients = new ArrayList<>();
    this.ListeVoitures = new ArrayList<Voiture>();
    this.listeFournitures = new ArrayList<Fourniture>();
    this.ListeEmployes = new ArrayList<Employe>();
    this.ListeServices = new ArrayList<Service>();
    this.ListeFactures = new ArrayList<Facture>();
    this.listPiecesRechange = new ArrayList<Piece_Rechange>();
// Création de la connexion à la base de données
       /*try {
           Connection connection = DriverManager.getConnection(
                   "jdbc:oracle:thin:@localhost:49161:xe", "Gestion_Garage_Auto", "123456");

           // Initialisation du clientDAO avec la connexion
           this.clientDAO = new ClientDAO(connection);
       } catch (SQLException e) {
           e.printStackTrace();
       }*/
   }
  //---------------------Getters et Setters--------------------   
 public ArrayList<Piece_Rechange> getListePieces()
{
        return this.listPiecesRechange;
}

public String get_email()
{
    return this.email;
}
public void set_mdp(String mdp)
{
    this.mdp=mdp;
}
public String get_password()
{
    return this.mdp;
}
public ArrayList<Voiture> getListeVoitures()
{
    return this.ListeVoitures;
}

public ArrayList<Employe> getListeEmployes()
{
    return this.ListeEmployes;
}

    public ArrayList<Rendez_vous> getListeRendezVous() {
        return this.listeRendezVous;
    }

    public ArrayList<Service> getListeServices() {
        return this.ListeServices;
    }

//--------------------GESTION DES FOURNITURES--------------------


    public ArrayList<Fourniture> getListeFournitures() {
        return this.listeFournitures;
    }

    //Méthode pour créer une fourniture
    public void creerFourniture(int idFourniture, String nom, String description, double prix, int quantiteStock) throws FournitureExisteClientException {
        try {
            // Vérifier les arguments
            if (nom == null || nom.isEmpty()) {
                throw new ArgumentInvalideException("Le nom de la fourniture ne peut pas être vide.");
            }
            if (description == null || description.isEmpty()) {
                throw new ArgumentInvalideException("La description ne peut pas être vide.");
            }
            if (prix <= 0) {
                throw new ArgumentInvalideException("Le prix doit être supérieur à 0.");
            }
            if (quantiteStock < 0) {
                throw new QuantiteNegatifException("La quantité en stock ne peut pas être négative.");
            }

            // Vérifier si une fourniture avec le même nom existe déjà
            for (Fourniture fourniture : listeFournitures) {
                if (fourniture.getNom().equalsIgnoreCase(nom)) {
                    throw new FournitureExisteClientException("Une fourniture avec ce nom existe déjà.");
                }
            }

            // Créer et ajouter la nouvelle fourniture
            Fourniture nouvelleFourniture = new Fourniture(idFourniture, nom, description, prix, quantiteStock);
            listeFournitures.add(nouvelleFourniture);
            System.out.println("Fourniture ajoutée avec succès.");
        } catch (ArgumentInvalideException | QuantiteNegatifException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }





    //Méthode pour supprimer une fourniture
    public void supprimerFourniture(int idFourniture) {
        Iterator<Fourniture> iterator = listeFournitures.iterator();
        boolean trouve = false;

        while (iterator.hasNext()) {
            Fourniture f = iterator.next();
            if (f.getIdFourniture() == idFourniture) {
                iterator.remove();
                System.out.println("Fourniture supprimée avec succès.");
                trouve = true;
                break;
            }
        }

        if (!trouve) {
            System.out.println("Aucune fourniture trouvée avec cet ID.");
        }
    }




    // Méthode pour chercher une fourniture par son ID
    private Fourniture chercherFournitureParId(int idFourniture) {
        for (Fourniture f : listeFournitures) {
            if (f.getIdFourniture() == idFourniture) {
                return f;
            }
        }
        return null;
    }




    // Méthode pour modifier une fourniture
    public void ModifierFourniture(int idFourniture) {
        try {
            Fourniture fourniture = chercherFournitureParId(idFourniture);

            if (fourniture != null) {
                Scanner sc = new Scanner(System.in);

                System.out.println("Modifier le nom de la fourniture : ");
                String nom = sc.nextLine();
                if (nom == null || nom.isEmpty()) {
                    throw new ArgumentInvalideException("Le nom de la fourniture ne peut pas être vide.");
                }

                System.out.println("Modifier la description : ");
                String description = sc.nextLine();
                if (description == null || description.isEmpty()) {
                    throw new ArgumentInvalideException("La description ne peut pas être vide.");
                }

                System.out.println("Modifier le prix : ");
                double prix = sc.nextDouble();
                if (prix <= 0) {
                    throw new ArgumentInvalideException("Le prix doit être supérieur à 0.");
                }

                System.out.println("Modifier la quantité en stock : ");
                int quantiteStock = sc.nextInt();
                if (quantiteStock < 0) {
                    throw new QuantiteNegatifException("La quantité en stock ne peut pas être négative.");
                }

                // Modifier les informations de la fourniture
                fourniture.modifierFourniture(idFourniture, nom, description, prix, quantiteStock);
            } else {
                System.out.println("Fourniture non trouvée.");
            }
        } catch (ArgumentInvalideException | QuantiteNegatifException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    // Méthode pour afficher toutes les fournitures
    public void afficherFournitures() {
        if (listeFournitures.isEmpty()) {
            System.out.println("Aucune fourniture à afficher.");
        } else {
            for (Fourniture f : listeFournitures) {
                f.afficherFourniture();
                System.out.println();
            }
        }
    }




                 //----------------------------GESTION DES PIECES DE RECHANGE ------------------------------


    public ArrayList<Piece_Rechange> getListPiecesRechange() {
        return this.listPiecesRechange;
    }

    // Méthode pour créer une pièce de rechange
    public void creerPieceRechange(int idPiece, String nom, String description, double prix, int quantiteStock)
            throws PieceRechangeExisteException, ArgumentInvalideException, QuantiteNegatifException {
        // Vérification que la liste des pièces de rechange est initialisée
        if (listPiecesRechange == null) {
            listPiecesRechange = new ArrayList<>();
        }

        // Vérification des arguments
        if (nom == null || nom.isEmpty()) {
            throw new ArgumentInvalideException("Le nom de la pièce de rechange ne peut pas être vide.");
        }
        if (description == null || description.isEmpty()) {
            throw new ArgumentInvalideException("La description de la pièce de rechange ne peut pas être vide.");
        }
        if (prix <= 0) {
            throw new ArgumentInvalideException("Le prix de la pièce de rechange doit être supérieur à 0.");
        }
        if (quantiteStock < 0) {
            throw new QuantiteNegatifException("La quantité en stock ne peut pas être négative.");
        }

        // Vérification des doublons dans la liste
        for (Piece_Rechange piece : listPiecesRechange) {
            if (piece.getNom().equalsIgnoreCase(nom)) {
                throw new PieceRechangeExisteException("Une pièce de rechange avec ce nom existe déjà.");
            }
        }

        // Création de la nouvelle pièce
        Piece_Rechange nouvellePiece = new Piece_Rechange(idPiece, nom, description, prix, quantiteStock);
        listPiecesRechange.add(nouvellePiece);

        // Message de confirmation
        System.out.println("La pièce de rechange " + nom + " a été ajoutée avec succès.");
    }






    //Supprimer piece rechange
    public void supprimerPieceRechange(int idPiece) {
        Iterator<Piece_Rechange> iterator = listPiecesRechange.iterator();

        while (iterator.hasNext()) {
            Piece_Rechange piece = iterator.next();

            if (piece.getIdPiece() == idPiece) {
                iterator.remove(); // Supprimer l'élément de la liste
                System.out.println("La pièce de rechange avec l'ID " + idPiece + " a été supprimée avec succès.");
                return;
            }
        }

        System.out.println("Aucune pièce de rechange trouvée avec l'ID " + idPiece);
    }






//Méthode pour modifier piece rechange

    public Piece_Rechange chercherPieceRechangeParId(int idPieceRechange) {
        for (Piece_Rechange piece : listPiecesRechange) {
            if (piece.getIdPiece() == idPieceRechange) {
                return piece;
            }
        }
        return null; // Retourner null si aucune pièce n'a été trouvée
    }

    public void modifierPieceRechange(int idPieceRechange) {
        Piece_Rechange pieceRechange = chercherPieceRechangeParId(idPieceRechange);

        if (pieceRechange != null) {
            Scanner sc = new Scanner(System.in);

            System.out.println("Modifier le nom de la pièce de rechange : ");
            String nom = sc.nextLine();

            System.out.println("Modifier la description : ");
            String description = sc.nextLine();

            System.out.println("Modifier le prix : ");
            double prix = sc.nextDouble();

            System.out.println("Modifier la quantité en stock : ");
            int quantiteStock = sc.nextInt();

            // Appeler la méthode pour modifier les informations de la pièce de rechange
            pieceRechange.modifier(idPieceRechange, nom, description, prix, quantiteStock);
        } else {
            System.out.println("Pièce de rechange non trouvée.");
        }
    }





    //Méthode pour afficher_toute les pieces

    public void afficherToutesLesPiecesRechange() {
        if (listPiecesRechange.isEmpty()) {
            System.out.println("Aucune pièce de rechange à afficher.");
        } else {
            for (Piece_Rechange p : listPiecesRechange) {
                p.afficherPieceRechange();
                System.out.println();
            }
        }
    }




   
    //--------------------GESTION DES VOITURES--------------------



    // Méthode pour créer une voiture
   public void creerVoiture(int idClient, String marque, String modele, int annee, long kilometrage, String immatriculation) 
        throws VoitureDejaExistanteClientException, VoitureDejaExistanteException {
    // Vérifier si le client avec l'ID donné existe dans la liste des clients
    Client clientExist = listeClients.stream()
            .filter(client -> client.get_id() == idClient)
            .findFirst()
            .orElse(null);

    if (clientExist == null) {
        // Si le client n'existe pas, afficher un message et sortir de la méthode
        System.out.println("Client avec ID " + idClient + " n'existe pas. La voiture n'a pas été créée.");
        return;
    }

    // Vérifier si la voiture existe déjà pour ce client
    for (Voiture v : clientExist.getVoitures()) {
        if (v.get_immatriculation().equals(immatriculation)) {
            throw new VoitureDejaExistanteClientException("Cette voiture est déjà associée à ce client.");
        }
    }

    // Vérifier si la voiture existe déjà dans la liste générale des voitures
    for (Voiture v : ListeVoitures) {
        if (v.get_immatriculation().equals(immatriculation)) {
            throw new VoitureDejaExistanteException("Cette voiture existe déjà dans la liste générale.");
        }
    }

    // Création de la nouvelle voiture avec les informations fournies
    Voiture voiture = new Voiture(marque, modele, annee, kilometrage, immatriculation, clientExist);

    // Ajouter la voiture à la liste générale des voitures
    ListeVoitures.add(voiture);

    // Ajouter la voiture à la liste des voitures du client
    clientExist.ajouterVoiture(voiture);

    System.out.println("Voiture créée et ajoutée au client avec succès.");
}



    // Méthode pour supprimer une voiture en fonction de l'ID
    public void supprimerVoiture(String immatriculation) {
        boolean voitureSupprimee = false;

        Iterator<Voiture> iterator = ListeVoitures.iterator();
        while (iterator.hasNext()) {
            Voiture voiture = iterator.next();

            if (voiture.get_immatriculation().equals(immatriculation)) {
                iterator.remove();  // Supprime la voiture de la liste générale
                voiture.getClient().getVoitures().remove(voiture);  // Supprime la voiture de la collection du client
                voitureSupprimee = true;
                System.out.println("Voiture supprimée avec succès.");
                break;
            }
        }

        if (!voitureSupprimee) {
            System.out.println("Aucune voiture trouvée avec l'immatriculation " + immatriculation);
        }
    }

    // Méthode pour mettre à jour les informations d'une voiture
    public void modifierVoiture(String immatriculation, String nouvelleMarque, String nouveauModele, int nouvelleAnnee, long nouveauKilometrage) {
        Voiture voiture = ListeVoitures.stream()
                .filter(v -> v.get_immatriculation().equals(immatriculation))
                .findFirst()
                .orElse(null);

        if (voiture != null) {
            voiture.setMarque(nouvelleMarque);
            voiture.setModele(nouveauModele);
            voiture.setAnnee(nouvelleAnnee);
            voiture.setKilometrage(nouveauKilometrage);
            System.out.println("Les informations de la voiture ont été mises à jour avec succès.");
        } else {
            System.out.println("Aucune voiture trouvée avec l'immatriculation " + immatriculation);
        }
    }



    // Méthode pour afficher toutes les voitures
    public void afficherVoitures() {
        if (ListeVoitures.isEmpty()) {
            System.out.println("Aucune voiture n'est disponible dans la liste.");
            return;
        }

        System.out.println("Liste des voitures :");
        for (Voiture voiture : ListeVoitures) {
            System.out.println("Immatriculation : " + voiture.get_immatriculation());
            System.out.println("Marque : " + voiture.getMarque());
            System.out.println("Modèle : " + voiture.getModele());
            System.out.println("Année : " + voiture.getAnnee());
            System.out.println("Kilométrage : " + voiture.getKilometrage());

            // Informations du client associé
            Client client = voiture.getClient();
            if (client != null) {
                System.out.println("Client associé : " + client.get_nom() + " " + client.get_prenom());
                System.out.println("ID du client : " + client.get_id());
            } else {
                System.out.println("Aucun client associé.");
            }
            System.out.println("--------------------------");
        }
    }
    private Voiture trouverVoitureParId(String immatriculation) {
    // Recherche de la voiture dans la liste des voitures disponibles
    for (Voiture v : this.ListeVoitures) { // get_liste_voitures() renvoie la liste des voitures
        if (v.get_immatriculation() == immatriculation) {
            return v; // Retourne la voiture trouvée
        }
    }
    return null; // Retourne null si la voiture n'a pas été trouvée
}







  //--------------------Gestion Rendez_vous--------------------




//methode pour afficher un rendez vous par son id
    public void afficherRendezVous(int idRendezVous) {
        // Recherche du rendez-vous par ID
        Optional<Rendez_vous> rendezVousOpt = listeRendezVous.stream()
                .filter(rdv -> rdv.getId_rendez_vous() == idRendezVous)
                .findFirst();

        if (rendezVousOpt.isEmpty()) {
            System.out.println("Erreur : Rendez-vous avec l'ID " + idRendezVous + " introuvable.");
            return; // Sortie si le rendez-vous n'est pas trouvé
        }

        // Affichage des détails du rendez-vous
        Rendez_vous rendezVous = rendezVousOpt.get();
        System.out.println("Détails du rendez-vous :");
        System.out.println(rendezVous.toString());
    }

//methode pour afficher tous les rendez vous
    public void afficherTousLesRendezVous() {
        if (listeRendezVous.isEmpty()) {
            System.out.println("Aucun rendez-vous disponible !");
            return; // Sortie si la liste est vide
        }

        System.out.println("Liste des rendez-vous :");
        listeRendezVous.forEach(rdv -> System.out.println(rdv.toString()));
    }


    public void supprimerRendezVous(int idRendezVous) {
        // Recherche du rendez-vous par ID
        Optional<Rendez_vous> rendezVousOpt = listeRendezVous.stream()
                .filter(rdv -> rdv.getId_rendez_vous() == idRendezVous)
                .findFirst();

        if (rendezVousOpt.isEmpty()) {
            System.out.println("Erreur : Rendez-vous avec l'ID " + idRendezVous + " introuvable.");
            return; // Sortie si le rendez-vous n'est pas trouvé
        }

        // Suppression du rendez-vous de la liste
        Rendez_vous rendezVous = rendezVousOpt.get();
        listeRendezVous.remove(rendezVous);

        System.out.println("Rendez-vous avec l'ID " + idRendezVous + " supprimé avec succès !");
    }



  //Méthode pour modifier un rendez-vous
    public void modifierRendezVous(int idRendezVous, String nouvelleDescription, int idClient, String immatriculationVoiture) {
        // Recherche du rendez-vous par ID
        Optional<Rendez_vous> rendezVousOpt = listeRendezVous.stream()
                .filter(rdv -> rdv.getId_rendez_vous() == idRendezVous)
                .findFirst();

        if (rendezVousOpt.isEmpty()) {
            System.out.println("Erreur : Rendez-vous avec l'ID " + idRendezVous + " introuvable.");
            return; // Sortie si le rendez-vous n'est pas trouvé
        }

        Rendez_vous rendezVous = rendezVousOpt.get();

        // Vérification du client
        Optional<Client> clientOpt = listeClients.stream()
                .filter(client -> client.get_id() == idClient)
                .findFirst();

        if (clientOpt.isEmpty()) {
            System.out.println("Erreur : Client avec l'ID " + idClient + " introuvable.");
            return; // Sortie si le client n'est pas trouvé
        }
        Client client = clientOpt.get();

        // Vérification de la voiture dans la liste globale
        Optional<Voiture> voitureOpt = ListeVoitures.stream()
                .filter(v -> v.get_immatriculation().equals(immatriculationVoiture))
                .findFirst();

        if (voitureOpt.isEmpty()) {
            System.out.println("Erreur : Voiture avec l'immatriculation " + immatriculationVoiture + " introuvable.");
            return; // Sortie si la voiture n'existe pas
        }
        Voiture voiture = voitureOpt.get();

        // Vérification que la voiture appartient au client
        if (!client.getVoitures().contains(voiture)) {
            System.out.println("Erreur : La voiture avec l'immatriculation " + immatriculationVoiture + " n'appartient pas au client avec l'ID " + idClient + ".");
            return; // Sortie si la voiture n'appartient pas au client
        }

        // Modification des informations du rendez-vous
        rendezVous.setDescription_rendez_vous(nouvelleDescription);
        rendezVous.setClient(client);
        rendezVous.setVoiture(voiture);

        System.out.println("Rendez-vous avec l'ID " + idRendezVous + " modifié avec succès !");
        System.out.println("Nouvelles informations : " + rendezVous.toString());
    }









    //*********** GESTION DE Service /////////////////////////

  //methode pour creer un service
    public void creerService(int idClient, String immatriculationVoiture, int idService, String description, double cout, int idRendezVous) {
        // Vérification du client
        Optional<Client> clientOpt = listeClients.stream()
                .filter(client -> client.get_id() == idClient)
                .findFirst();

        if (clientOpt.isEmpty()) {
            System.out.println("Erreur : Client avec l'ID " + idClient + " introuvable.");
            return;  // Sortie de la méthode si le client n'est pas trouvé
        }
        Client client = clientOpt.get();

        // Vérification de l'existence de la voiture dans la liste globale de voitures
        Optional<Voiture> voitureGlobaleOpt = ListeVoitures.stream()
                .filter(voiture -> voiture.getImmatriculation().equals(immatriculationVoiture))
                .findFirst();

        if (voitureGlobaleOpt.isEmpty()) {
            System.out.println("Erreur : La voiture avec l'immatriculation " + immatriculationVoiture + " n'existe pas dans la liste des voitures.");
            return;  // Sortie de la méthode si la voiture n'existe pas
        }
        Voiture voitureGlobale = voitureGlobaleOpt.get();

        // Vérification que la voiture appartient bien au client spécifié
        if (!client.getVoitures().contains(voitureGlobale)) {
            System.out.println("Erreur : La voiture avec l'immatriculation " + immatriculationVoiture + " n'appartient pas au client avec l'ID " + idClient + ".");
            return;  // Sortie de la méthode si la voiture n'appartient pas au client
        }

        // Vérification du rendez-vous
        Optional<Rendez_vous> rendezVousOpt = listeRendezVous.stream()
                .filter(rdv -> rdv.getId_rendez_vous() == idRendezVous)
                .findFirst();

        if (rendezVousOpt.isEmpty()) {
            System.out.println("Erreur : Rendez-vous avec l'ID " + idRendezVous + " introuvable.");
            return;  // Sortie de la méthode si le rendez-vous n'est pas trouvé
        }
        Rendez_vous rendezVous = rendezVousOpt.get();

        // Vérification que le rendez-vous appartient bien au client
        if (rendezVous.getClient().get_id() != client.get_id()) {
            System.out.println("Erreur : Le rendez-vous avec l'ID " + idRendezVous + " n'appartient pas au client avec l'ID " + idClient + ".");
            return;  // Sortie de la méthode si le rendez-vous n'appartient pas au client
        }

        // Création d'une liste vide d'employés pour l'instant
        ArrayList<Employe> effecteurs = new ArrayList<>();

        // Création d'une liste vide de pièces utilisées (si aucune pièce n'est utilisée)
        ArrayList<Piece_Rechange> piecesUtilisees = new ArrayList<>();

        // Création du service avec tous les paramètres nécessaires, y compris la liste vide de pièces utilisées
        Service service = new Service(idService, description, cout, effecteurs, client, voitureGlobale, rendezVous, piecesUtilisees);

        // Ajout du service à la liste des services
        ListeServices.add(service);

        // Affichage des informations du service créé
        System.out.println("Service créé avec succès :");
        System.out.println(service.toString());
    }
    /*public void creerService(int idClient, String immatriculationVoiture, int idService, String description, double cout, int idRendezVous) {
    // Vérification du client
    Optional<Client> clientOpt = listeClients.stream()
            .filter(client -> client.get_id() == idClient)
            .findFirst();

    if (clientOpt.isEmpty()) {
        System.out.println("Erreur : Client avec l'ID " + idClient + " introuvable.");
        return;  // Sortie de la méthode si le client n'est pas trouvé
    }
    Client client = clientOpt.get();

    // Vérification de l'existence de la voiture dans la liste globale de voitures
    Optional<Voiture> voitureGlobaleOpt = ListeVoitures.stream()
            .filter(voiture -> voiture.get_immatriculation().equals(immatriculationVoiture))
            .findFirst();

    if (voitureGlobaleOpt.isEmpty()) {
        System.out.println("Erreur : La voiture avec l'immatriculation " + immatriculationVoiture + " n'existe pas dans la liste des voitures.");
        return;  // Sortie de la méthode si la voiture n'existe pas
    }
    Voiture voitureGlobale = voitureGlobaleOpt.get();

    // Vérification que la voiture appartient bien au client spécifié
    if (!client.getVoitures().contains(voitureGlobale)) {
        System.out.println("Erreur : La voiture avec l'immatriculation " + immatriculationVoiture + " n'appartient pas au client avec l'ID " + idClient + ".");
        return;  // Sortie de la méthode si la voiture n'appartient pas au client
    }

    // Vérification du rendez-vous
    Optional<Rendez_vous> rendezVousOpt = listeRendezVous.stream()
            .filter(rdv -> rdv.getId_rendez_vous() == idRendezVous)
            .findFirst();

    if (rendezVousOpt.isEmpty()) {
        System.out.println("Erreur : Rendez-vous avec l'ID " + idRendezVous + " introuvable.");
        return;  // Sortie de la méthode si le rendez-vous n'est pas trouvé
    }
    Rendez_vous rendezVous = rendezVousOpt.get();

    // Vérification que le rendez-vous appartient bien au client
    if (rendezVous.getClient().get_id() != client.get_id()) {
        System.out.println("Erreur : Le rendez-vous avec l'ID " + idRendezVous + " n'appartient pas au client avec l'ID " + idClient + ".");
        return;  // Sortie de la méthode si le rendez-vous n'appartient pas au client
    }

    // Création du service avec le bon constructeur
    Service service = new Service(idService,description, cout, idService, voitureGlobale, rendezVous, client);
    ListeServices.add(service);
    // Affichage des informations du service créé
    System.out.println("Service créé avec succès :");
    System.out.println(service.toString());
    }*/



    /*public void creerService(int idClient, String immatriculationVoiture, String typeService, double cout, int idRendezVous) {
        // Vérification du client
        Optional<Client> clientOpt = listeClients.stream()
                .filter(client -> client.get_id() == idClient)
                .findFirst();

        if (clientOpt.isEmpty()) {
            System.out.println("Erreur : Client avec l'ID " + idClient + " introuvable.");
            return;
        }
        Client client = clientOpt.get();

        // Vérification de l'existence de la voiture dans la liste globale de voitures
        Optional<Voiture> voitureGlobaleOpt = ListeVoitures.stream()
                .filter(voiture -> voiture.get_immatriculation().equals(immatriculationVoiture))
                .findFirst();

        if (voitureGlobaleOpt.isEmpty()) {
            System.out.println("Erreur : La voiture avec l'immatriculation " + immatriculationVoiture + " n'existe pas dans la liste des voitures.");
            return;
        }
        Voiture voitureGlobale = voitureGlobaleOpt.get();

        // Vérification que la voiture appartient bien au client spécifié
        if (!client.getVoitures().contains(voitureGlobale)) {
            System.out.println("Erreur : La voiture avec l'immatriculation " + immatriculationVoiture + " n'appartient pas au client avec l'ID " + idClient + ".");
            return;
        }

        // Vérification du rendez-vous
        Optional<Rendez_vous> rendezVousOpt = listeRendezVous.stream()
                .filter(rdv -> rdv.getId_rendez_vous() == idRendezVous)
                .findFirst();

        if (rendezVousOpt.isEmpty()) {
            System.out.println("Erreur : Rendez-vous avec l'ID " + idRendezVous + " introuvable.");
            return;
        }
        Rendez_vous rendezVous = rendezVousOpt.get();

        // Vérification que le rendez-vous appartient bien au client
        if (rendezVous.getClient().get_id() != client.get_id()) {
            System.out.println("Erreur : Le rendez-vous avec l'ID " + idRendezVous + " n'appartient pas au client avec l'ID " + idClient + ".");
            return;
        }

        // Initialisation des listes nécessaires pour le constructeur
        List<Employe> employesSelectionnes = new ArrayList<>();
        List<Piece_Rechange> piecesSelectionnees = new ArrayList<>();
        List<Fourniture> fournituresSelectionnees = new ArrayList<>();

        // Création du service avec le bon constructeur
        Service service = new Service(typeService, cout, rendezVous, employesSelectionnes, piecesSelectionnees, fournituresSelectionnees);
        if (ListeServices == null) {
            ListeServices = new ArrayList<>();
        }
        ListeServices.add(service);
        System.out.println("Service ajouté à la liste interne du réceptionniste : " + service);


        // Affichage des informations du service créé
        System.out.println("Service créé avec succès :");
        System.out.println(service.toString());
    }

*/




   /* public void creerService(int idClient, String immatriculationVoiture, int idService, String description, double cout, int idRendezVous) {
        // Vérification de la validité des paramètres d'entrée
        if (description == null || description.isBlank()) {
            System.out.println("Erreur : La description du service est invalide.");
            return;
        }

        if (cout <= 0) {
            System.out.println("Erreur : Le coût doit être supérieur à 0.");
            return;
        }

        // Recherche du client
        Optional<Client> clientOpt = listeClients.stream()
                .filter(client -> client.get_id() == idClient)
                .findFirst();

        if (clientOpt.isEmpty()) {
            System.out.println("Erreur : Client avec l'ID " + idClient + " introuvable.");
            return;
        }
        Client client = clientOpt.get();

        // Recherche de la voiture globale
        Optional<Voiture> voitureOpt = ListeVoitures.stream()
                .filter(voiture -> voiture.get_immatriculation().equals(immatriculationVoiture))
                .findFirst();

        if (voitureOpt.isEmpty()) {
            System.out.println("Erreur : La voiture avec l'immatriculation " + immatriculationVoiture + " n'existe pas.");
            return;
        }
        Voiture voiture = voitureOpt.get();

        // Vérification que la voiture appartient au client
        if (!client.getVoitures().contains(voiture)) {
            System.out.println("Erreur : La voiture n'appartient pas au client avec l'ID " + idClient + ".");
            return;
        }

        // Recherche du rendez-vous
        Optional<Rendez_vous> rendezVousOpt = listeRendezVous.stream()
                .filter(rdv -> rdv.getId_rendez_vous() == idRendezVous)
                .findFirst();

        if (rendezVousOpt.isEmpty()) {
            System.out.println("Erreur : Rendez-vous avec l'ID " + idRendezVous + " introuvable.");
            return;
        }
        Rendez_vous rendezVous = rendezVousOpt.get();

        // Vérification que le rendez-vous appartient au client
        if (rendezVous.getClient().get_id() != client.get_id()) {
            System.out.println("Erreur : Le rendez-vous avec l'ID " + idRendezVous + " n'appartient pas au client.");
            return;
        }

        // Création du service
        Service nouveauService = new Service(description, cout, idService, voiture, rendezVous, client);
        ListeServices.add(nouveauService);

        // Affichage du service créé
        System.out.println("Service créé avec succès :");
        System.out.println(nouveauService);
    }
*/

   /* public void creerService(int idClient, String immatriculationVoiture, int idService, String description, double cout, int idRendezVous) {
        // Vérification du client
        Optional<Client> clientOpt = listeClients.stream()
                .filter(client -> client.get_id() == idClient)
                .findFirst();

        if (clientOpt.isEmpty()) {
            System.out.println("Erreur : Client avec l'ID " + idClient + " introuvable.");
            return;  // Sortie de la méthode si le client n'est pas trouvé
        }
        Client client = clientOpt.get();

        // Vérification de l'existence de la voiture dans la liste globale de voitures
        Optional<Voiture> voitureGlobaleOpt = ListeVoitures.stream()
                .filter(voiture -> voiture.get_immatriculation().equals(immatriculationVoiture))
                .findFirst();

        if (voitureGlobaleOpt.isEmpty()) {
            System.out.println("Erreur : La voiture avec l'immatriculation " + immatriculationVoiture + " n'existe pas dans la liste des voitures.");
            return;  // Sortie de la méthode si la voiture n'existe pas
        }
        Voiture voitureGlobale = voitureGlobaleOpt.get();

        // Vérification que la voiture appartient bien au client spécifié
        if (!client.getVoitures().contains(voitureGlobale)) {
            System.out.println("Erreur : La voiture avec l'immatriculation " + immatriculationVoiture + " n'appartient pas au client avec l'ID " + idClient + ".");
            return;  // Sortie de la méthode si la voiture n'appartient pas au client
        }

        // Vérification du rendez-vous
        Optional<Rendez_vous> rendezVousOpt = listeRendezVous.stream()
                .filter(rdv -> rdv.getId_rendez_vous() == idRendezVous)
                .findFirst();

        if (rendezVousOpt.isEmpty()) {
            System.out.println("Erreur : Rendez-vous avec l'ID " + idRendezVous + " introuvable.");
            return;  // Sortie de la méthode si le rendez-vous n'est pas trouvé
        }
        Rendez_vous rendezVous = rendezVousOpt.get();

        // Vérification que le rendez-vous appartient bien au client
        if (rendezVous.getClient().get_id() != client.get_id()) {
            System.out.println("Erreur : Le rendez-vous avec l'ID " + idRendezVous + " n'appartient pas au client avec l'ID " + idClient + ".");
            return;  // Sortie de la méthode si le rendez-vous n'appartient pas au client
        }

        // Création du service avec le bon constructeur
        Service service = new Service(idService, description, cout, ArrayList<Employe> effecteurs, rendezVous, client);
        ListeServices.add(service);
        // Affichage des informations du service créé
        System.out.println("Service créé avec succès :");
        System.out.println(service.toString());
    }

*/
//methode pour supprimer un service par son id
    public void supprimerService(int idService) {
        // Recherche du service par ID
        Optional<Service> serviceOpt = ListeServices.stream()
                .filter(service -> service.getIdService() == idService)
                .findFirst();

        if (serviceOpt.isEmpty()) {
            System.out.println("Erreur : Service avec l'ID " + idService + " introuvable.");
            return; // Sortie de la méthode si le service n'existe pas
        }

        // Suppression du service de la liste
        Service serviceASupprimer = serviceOpt.get();
        ListeServices.remove(serviceASupprimer);

        System.out.println("Service avec l'ID " + idService + " supprimé avec succès.");
    }



//methode pour modifer un service
    public void modifierService(int idService, String nouvelleDescription, double nouveauCout, int nouvelIdRendezVous) {
        // Recherche du service par ID
        Optional<Service> serviceOpt = ListeServices.stream()
                .filter(service -> service.getIdService() == idService)
                .findFirst();

        if (serviceOpt.isEmpty()) {
            System.out.println("Erreur : Service avec l'ID " + idService + " introuvable.");
            return; // Sortie de la méthode si le service n'existe pas
        }

        Service serviceAModifier = serviceOpt.get();

        // Vérification du nouveau rendez-vous
        Optional<Rendez_vous> rendezVousOpt = listeRendezVous.stream()
                .filter(rdv -> rdv.getId_rendez_vous() == nouvelIdRendezVous)
                .findFirst();

        if (rendezVousOpt.isEmpty()) {
            System.out.println("Erreur : Rendez-vous avec l'ID " + nouvelIdRendezVous + " introuvable.");
            return; // Sortie de la méthode si le rendez-vous n'existe pas
        }

        Rendez_vous nouveauRendezVous = rendezVousOpt.get();

        // Modification des attributs du service
        serviceAModifier.setDescription(nouvelleDescription);
        serviceAModifier.setCout(nouveauCout);
        serviceAModifier.setRendezVous(nouveauRendezVous);

        System.out.println("Service modifié avec succès !");
        System.out.println(serviceAModifier.toString());
    }


//Methode pour afficher un service par son id
    public void afficherService(int idService) {
        // Recherche du service par ID
        Optional<Service> serviceOpt = ListeServices.stream()
                .filter(service -> service.getIdService() == idService)
                .findFirst();

        if (serviceOpt.isEmpty()) {
            System.out.println("Erreur : Service avec l'ID " + idService + " introuvable.");
            return; // Sortie de la méthode si le service n'existe pas
        }

        // Affichage du service trouvé
        Service service = serviceOpt.get();
        System.out.println("Détails du service :");
        System.out.println(service.toString());
    }
//methode pour afficher tous les services
    public void afficherTousLesServices() {
        if (ListeServices.isEmpty()) {
            System.out.println("Aucun service disponible !");
            return;
        }

        System.out.println("Liste des services :");
        ListeServices.forEach(service -> System.out.println(service.toString()));
    }








    //--------------------Gestion des factures--------------------
    public void creerFacture(int numeroFacture, Date dateFacture, int idClient, boolean avecRemise) {
        // Vérification du client
        Optional<Client> clientOpt = listeClients.stream()
                .filter(client -> client.get_id() == idClient)
                .findFirst();

        if (clientOpt.isEmpty()) {
            System.out.println("Erreur : Client avec l'ID " + idClient + " introuvable.");
            return; // Sortie de la méthode si le client n'existe pas
        }

        Client client = clientOpt.get();

        // Récupération des services associés à ce client
        List<Service> servicesClient = ListeServices.stream()
                .filter(service -> service.getClient().get_id() == idClient)
                .toList();

        if (servicesClient.isEmpty()) {
            System.out.println("Erreur : Aucun service associé au client avec l'ID " + idClient + ".");
            return; // Sortie de la méthode si aucun service n'est trouvé
        }

        // Calcul du montant total des services
        double montantTotal = servicesClient.stream()
                .mapToDouble(Service::getCout)
                .sum();

        // Création de la facture
        Facture nouvelleFacture = new Facture(numeroFacture, montantTotal, dateFacture, client, new ArrayList<>(servicesClient), avecRemise);

        // Ajout à la liste des factures
        ListeFactures.add(nouvelleFacture);

        System.out.println("Facture créée avec succès :");
        System.out.println(nouvelleFacture.toString());
    }





//Méthode pour supprimer une facture
    public void supprimerFacture(int numeroFacture) {
        // Recherche de la facture par numéro
        Optional<Facture> factureOpt = ListeFactures.stream()
                .filter(facture -> facture.getNumeroFacture() == numeroFacture)
                .findFirst();

        if (factureOpt.isEmpty()) {
            System.out.println("Erreur : Facture avec le numéro " + numeroFacture + " introuvable.");
            return; // Sortie de la méthode si la facture n'existe pas
        }

        Facture factureASupprimer = factureOpt.get();

        // Suppression de la facture de la liste
        ListeFactures.remove(factureASupprimer);

        System.out.println("Facture avec le numéro " + numeroFacture + " supprimée avec succès.");
    }






//Méthode pour modifier une facture
    public void modifierFacture(int numeroFacture, Date nouvelleDate, boolean avecNouvelleRemise) {
        // Recherche de la facture par numéro
        Optional<Facture> factureOpt = ListeFactures.stream()
                .filter(facture -> facture.getNumeroFacture() == numeroFacture)
                .findFirst();

        if (factureOpt.isEmpty()) {
            System.out.println("Erreur : Facture avec le numéro " + numeroFacture + " introuvable.");
            return; // Sortie de la méthode si la facture n'existe pas
        }

        Facture factureAModifier = factureOpt.get();

        // Modification des attributs
        factureAModifier.setDateFacture(nouvelleDate);
        factureAModifier.setAvecRemise(avecNouvelleRemise);

        System.out.println("Facture modifiée avec succès :");
        System.out.println(factureAModifier.toString());
    }

//Méthode pour afficher une facture
    public void afficherFacture(int numeroFacture) {
        // Recherche de la facture par numéro
        Optional<Facture> factureOpt = ListeFactures.stream()
                .filter(facture -> facture.getNumeroFacture() == numeroFacture)
                .findFirst();

        if (factureOpt.isEmpty()) {
            System.out.println("Erreur : Facture avec le numéro " + numeroFacture + " introuvable.");
            return; // Sortie de la méthode si la facture n'existe pas
        }

        Facture facture = factureOpt.get();

        // Affichage des détails de la facture
        System.out.println("Détails de la facture :");
        System.out.println(facture.toString());
    }

//Méthode pour afficher tous les factures
    public void afficherToutesLesFactures() {
        if (ListeFactures.isEmpty()) {
            System.out.println("Aucune facture disponible !");
            return;
        }

        System.out.println("Liste des factures :");
        ListeFactures.forEach(facture -> System.out.println(facture.toString()));
    }
                        //-------------------- GESTION DES CLIENTS--------------------

    //Méthode pour créer un client 
    public void creerClient(int id, String nom, String prenom, int telephone, String adresse, String statutFinancier) throws ClientExisteException {
        // Création d'un nouveau client avec les informations fournies
        Client client = new Client(id, nom, prenom, telephone, adresse, statutFinancier);

        // Vérifier si un client avec le même ID existe déjà dans la liste
        for (Client c : listeClients) {
            if (c.get_id() == client.get_id()) {  // Comparaison basée sur l'ID
                throw new ClientExisteException("Un client avec cet ID existe déjà.");
            }
        }

        // Si le client n'existe pas, on l'ajoute à la liste des clients
        listeClients.add(client);
        System.out.println("Client créé et ajouté à la liste des clients. ");
    }
    public ArrayList<Client> get_liste_clients()
    {
        return this.listeClients;
    }
//Méthode pour supprimer un client (avec recherche à travers Stream)
public void supprimerClient(int idClient) {
    // Vérifier si la liste des clients est vide
    if (listeClients.isEmpty()) {
        System.out.println("La liste des clients est vide, aucune suppression possible.");
        return; // Sortir de la méthode si la liste est vide
    }

    // Utilisation d'un Stream pour rechercher et supprimer le client
    Optional<Client> clientASupprimer = listeClients.stream()
            .filter(client -> client.get_id() == idClient)  // Filtrer le client avec l'ID spécifié
            .findFirst();  // Récupérer le premier élément trouvé (s'il y en a un)

    if (clientASupprimer.isPresent()) {
        Client client = clientASupprimer.get();

        // Supprimer les voitures associées à ce client dans la liste globale des voitures
        ListeVoitures.removeIf(voiture -> client.getVoitures().contains(voiture));

        // Supprimer le client de la liste des clients
        listeClients.remove(client);
        System.out.println("Client avec ID " + idClient + " et ses voitures associées ont été supprimés.");
    } else {
        System.out.println("Client avec ID " + idClient + " non trouvé.");
    }
}
//Méthode pour modifier un client
 public void modifierClient(int idClient) {
    // Chercher le client à partir de l'ID
    Client client = chercherClientParId(idClient);  // Supposons que tu as une méthode pour chercher un client par ID

    if (client != null) {
        // Si le client est trouvé, appeler la méthode modifier de la classe Client
        client.modifier();  // Cette méthode est déjà définie dans la classe Client
    } else {
        // Si le client n'est pas trouvé, afficher un message d'erreur
        System.out.println("Client non trouvé.");
    }
}
 //Methode pour chercher un client en fonction de son id
 public Client chercherClientParId(int idClient) {
    for (Client client : listeClients) {
        if (client.get_id() == idClient) {  // Supposons que la méthode getId() existe dans la classe Client
            return client;
        }
    }
    return null;  // Si le client n'est pas trouvé
}
 //Méthode pour afficher tous les clients 
 public void afficherListeClients() {
    // Vérifier si la liste des clients est vide
    if (listeClients.isEmpty()) {
        System.out.println("Aucun client à afficher.");
        return;
    }

    // Parcourir la liste des clients
    for (Client client : listeClients) {
        // Afficher les informations du client via la méthode toString()
        System.out.println(client);  // Cela appelle automatiquement client.toString()

       
    }
}
 //Méthode pour afficher les voitures d'un client 
    public void afficher_voiturs_client()
    {
        for (Voiture v : this.ListeVoitures)
        {
            System.out.println(v);
        }
    }
   //Méthode pour ajouter une voiture à un client
    public void ajouterVoitureAuClient(int idClient, Voiture voiture) {
    // Trouver le client par son ID
    Client clientExist = null;
    for (Client client : listeClients) {
        if (client.get_id() == idClient) {
            clientExist = client;
            break; // Si le client est trouvé, on peut sortir de la boucle
        }
    }
 
    // Vérification si le client existe
    if (clientExist != null) {
        // Vérifier si la voiture existe déjà pour ce client
        try {
            clientExist.ajouterVoiture(voiture); // Cette méthode lève une exception si la voiture existe déjà pour le client
            this.ListeVoitures.add(voiture); // Ajouter la voiture à la liste des voitures de la réceptionniste
            System.out.println("La voiture a été ajoutée au client.");
        } catch (VoitureDejaExistanteClientException e) {
            // Si la voiture existe déjà pour ce client, on lève l'exception et on sort
            System.out.println("Erreur: " + e.getMessage());
            return; // On sort immédiatement de la méthode si la voiture existe déjà pour ce client
        }

        // Si la voiture n'existe pas pour ce client, on vérifie dans la liste générale des voitures
        boolean voitureExistanteGlobale = false;
        for (Voiture v : this.ListeVoitures) { // Vérifier la voiture dans la liste globale des voitures
            if (v.get_immatriculation().equals(voiture.get_immatriculation())) {
                voitureExistanteGlobale = true;
                break; // La voiture existe déjà globalement
            }
        }

        // Si la voiture existe déjà dans la liste générale, on affiche un message
        if (voitureExistanteGlobale) {
            System.out.println("Cette voiture existe  dans la liste générale.");
        } else {
            // Si elle n'existe ni pour ce client ni dans la liste générale, on l'ajoute
            this.ListeVoitures.add(voiture); // Ajouter la voiture à la liste des voitures globales
            clientExist.add_car(voiture); // Ajouter la voiture à la liste du client concerné
            System.out.println("La voiture a été ajoutée à la liste générale et au client.");
        }

    } else {
        System.out.println("Client non trouvé.");
    }
    }
    //Méthode pour supprimer une voiture d'un client 
    public void supprimerVoitureClient(int idClient, String idVoiture) {
    Client clientTrouve = null;

    // Recherche du client dans la liste des clients
    for (Client client : this.listeClients) {
        if (client.get_id() == idClient) {
            clientTrouve = client;
            break;
        }
    }

    // Si le client n'est pas trouvé, afficher un message d'erreur
    if (clientTrouve == null) {
        System.out.println("Client avec l'ID " + idClient + " non trouvé.");
        return;
    }

    // Si le client est trouvé, on appelle la méthode retirerVoiture
    try {
        // Tenter de retirer la voiture du client
        clientTrouve.retirerVoiture(idVoiture);  // On laisse la gestion de la voiture à cette méthode
        
        // Supprimer la voiture de la liste générale des voitures
        Voiture voitureASupprimer = null;
        for (Voiture voiture : this.ListeVoitures) {
            if (voiture.get_immatriculation().equals(idVoiture)) {
                voitureASupprimer = voiture;
                break;
            }
        }

        // Si la voiture est trouvée dans la liste générale, on la retire
        if (voitureASupprimer != null) {
            this.ListeVoitures.remove(voitureASupprimer);
            System.out.println("La voiture avec l'ID " + idVoiture + " a été supprimée de la liste générale.");
        } else {
            System.out.println("Erreur : La voiture avec l'ID " + idVoiture + " n'a pas été trouvée dans la liste générale.");
        }
        
        System.out.println("La voiture avec l'ID " + idVoiture + " a été supprimée du client " + clientTrouve.get_nom() + " " + clientTrouve.get_prenom());
    } catch (VoitureNonTrouveeClientException e) {
        // En cas d'erreur si la voiture n'est pas trouvée pour ce client
        System.out.println("Erreur : " + e.getMessage() + " pour ce client");
    } catch (ArgumentInvalideException e) {
        // En cas d'argument invalide dans la méthode retirerVoiture
        System.out.println("Erreur : " + e.getMessage());
    }
}

    //Méthode pour afficher les voitures par client : 
    public void afficher_voitures_par_client(int idclient)
    {
         Client clientTrouve = null;

    // Recherche du client dans la liste des clients
    for (Client client : this.listeClients) {
        if (client.get_id() == idclient) {
            clientTrouve = client;
            break;
        }
    }

    // Si le client n'est pas trouvé, afficher un message d'erreur
    if (clientTrouve == null) {
        System.out.println("Client avec l'ID " + idclient + " non trouvé.");
        return;
    }
    if (clientTrouve.getVoitures().isEmpty())
    {
        System.out.println("Ce client n'a pas de voitures encore ");
        return;
    }
    for (Voiture voiture : clientTrouve.getVoitures())
    {
      System.out.println(voiture);
    }
}

    //Méthode pour ajouter une fourniture à un client 
    public void ajouter_fourniture_client(int idFourniture, int idClient) {
    // Trouver la fourniture correspondante
    Fourniture fourniture = null;
    for (Fourniture f : listeFournitures) {
        if (f.getIdFourniture() == idFourniture) {
            fourniture = f;
            break;
        }
    }

    if (fourniture == null) {
        System.out.println("Fourniture introuvable avec l'ID: " + idFourniture);
        return;
    }

    // Trouver le client correspondant
    Client client = null;
    for (Client c : listeClients) {
        if (c.get_id() == idClient) {
            client = c;
            break;
        }
    }

    if (client == null) {
        System.out.println("Client introuvable avec l'ID: " + idClient);
        return;
    }

    // Ajouter la fourniture au client
    try {
        client.ajouterFourniture(fourniture);
    } catch (FournitureExisteClientException e) {
        System.out.println(e.getMessage());
    }
}
    //Méthode pour supprimer une fourniture d'un client 
    public void supprimer_fourniture_de_client(int idClient, int idFourniture) {
    // Recherche du client par son ID
    Client client = trouverClientParId(idClient);  // Méthode pour trouver le client

    if (client == null) {
        System.out.println("Client introuvable avec l'ID : " + idClient);
        return;
    }

    // Recherche de la fourniture par son ID dans la liste des fournitures achetées du client
    Fourniture fournitureASupprimer = trouverFournitureParId(idFourniture);  // Méthode pour trouver la fourniture

    if (fournitureASupprimer == null) {
        System.out.println("Fourniture introuvable avec l'ID : " + idFourniture);
        return;
    }

    // Utiliser le try-catch pour gérer l'exception levée par supprimerFourniture
    try {
        // Appeler la méthode pour supprimer la fourniture du client
        client.supprimerFourniture(fournitureASupprimer);
    } catch (FournitureNonTrouveeClientException e) {
        // Gérer l'exception si la fourniture n'est pas trouvée dans la liste
        System.out.println("Erreur : " + e.getMessage());
    }
}
    //Méthode pour afficher les fournitures d'un client 
    public void afficherFournituresClient(int idClient) {
    Client client = null;

    // Recherche du client dans la liste des clients par ID
    for (Client c : listeClients) { // listeClients représente la collection des clients
        if (c.get_id() == idClient) {
            client = c;
            break;
        }
    }

    // Si le client est trouvé, afficher ses fournitures
    if (client != null) {
        client.afficherFournitures(); // Appel à la méthode afficherFournitures() de la classe Client
    } else {
        System.out.println("Client avec l'ID " + idClient + " non trouvé.");
    }
}
    //Méthodes pour afficher le fournitures par client
public void afficher_fournitures_par_client(int idClient) {
    // Vérifier si le client existe dans la liste des clients
    Client client = null;
    for (Client c : listeClients) {
        if (c.get_id() == idClient) {
            client = c;
            break;
        }
    }

    if (client != null) {
        // Appeler la méthode afficherFournitures du client
        client.afficherFournitures();
    } else {
        System.out.println("Client non trouvé avec l'ID : " + idClient);
    }
}


// Méthode pour trouver un client en fonction de son ID
private Client trouverClientParId(int idClient) {
    // Ici vous pouvez chercher le client dans votre liste de clients
    // Par exemple :
    for (Client client : listeClients) {
        if (client.get_id() == idClient) {
            return client;
        }
    }
    return null;  // Retourne null si le client n'est pas trouvé
}

// Méthode pour trouver une fourniture par en fonction de son ID 
private Fourniture trouverFournitureParId(int idFourniture) {
    // Chercher la fourniture dans une liste de fournitures
    // Par exemple, si vous avez une liste globale de fournitures :
    for (Fourniture fourniture : listeFournitures) {
        if (fourniture.getIdFourniture() == idFourniture) {
            return fourniture;
        }
    }
    return null;  // Retourne null si la fourniture n'est pas trouvée
}
//-------------------------Gestion des employés-------------------------

  //Méthode pour créer un employé
    public void creerEmploye(int id, String nom, String prenom, int telephone, String adresse, double salaire, String typeEmploye,String date_embauche) {
    // Vérifier si un employé avec cet ID existe déjà
    for (Employe e : ListeEmployes) {
        if (e.get_id() == id) {
            System.out.println("Un employé avec cet ID existe déjà. Impossible de créer un nouvel employé.");
            return;
        }
    }

    // Créer l'employé en fonction du type
    Employe nouvelEmploye = null;
    switch (typeEmploye.toLowerCase()) {
        case "laveur":
            // Créer un laveur
            nouvelEmploye = new Laveur(id, nom, prenom, telephone, adresse, salaire , date_embauche);
            break;

        case "mécanicien":
            // Demander la spécialité pour le mécanicien
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez la spécialité du mécanicien :");
            String specialite = scanner.nextLine(); // Demander la spécialité du mécanicien

            // Vérifier que la spécialité n'est pas vide
            if (specialite == null || specialite.trim().isEmpty()) {
                System.out.println("La spécialité du mécanicien ne peut pas être vide !");
                return;
            }

            // Créer un mécanicien avec la spécialité
///            nouvelEmploye = new Mecanicien(id, nom, prenom, telephone, adresse, salaire, specialite);
            break;

        case "chef":
            // Créer un chef
            nouvelEmploye = new Chef(id, nom, prenom, telephone, adresse, salaire,date_embauche);
            break;

        default:
            System.out.println("Type d'employé invalide !");
            return;  // Sortir si le type est invalide
    }

    // Ajouter l'employé à la liste
    if (nouvelEmploye != null) {
        ListeEmployes.add(nouvelEmploye);
        System.out.println("L'employé " + nouvelEmploye.get_nom() + " a été créé et ajouté à la liste.");
    }
}
    //Méthode pour créer un employé avec l'attribut expertise
    public void creer_employe(int id, String nom, String prenom, int telephone, String adresse, double salaire, String typeEmploye,String date_emb) {
    // Vérifier si un employé avec cet ID existe déjà
    for (Employe e : ListeEmployes) {
        if (e.get_id() == id) {
            System.out.println("Un employé avec cet ID existe déjà. Impossible de créer un nouvel employé.");
            return;
        }
    }

    // Créer l'employé en fonction du type
    Employe nouvelEmploye = null;
    switch (typeEmploye.toLowerCase()) {
        case "laveur":
            // Créer un laveur
            nouvelEmploye = new Laveur(id, nom, prenom, telephone, adresse, salaire,date_emb);
            break;

        case "mécanicien":
            // Demander la spécialité pour le mécanicien
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez la spécialité du mécanicien :");
            String specialite = scanner.nextLine(); // Demander la spécialité du mécanicien

            // Demander l'expertise du mécanicien
            Expertise expertise = null;
            while (expertise == null) {
                System.out.println("Entrez l'expertise du mécanicien (CHOISIR PARMI : MOTEUR, ELECTRONIQUE, CARROSSERIE , PEINTURE , FREINAGE , CLIMATISATION ,TRANSMISSION , SUSPENSION ,ESSUIE_GLACES , PNEUMATIQUES");

                String expertiseStr = scanner.nextLine().toUpperCase();
                
                // Vérifier que l'expertise correspond à une valeur valide de l'énumération
                try {
                    expertise = Expertise.valueOf(expertiseStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("Expertise invalide ! Veuillez entrer une expertise valide.");
                }
            }

            // Créer un mécanicien avec la spécialité et l'expertise
            nouvelEmploye = new Mecanicien(id, nom, prenom, telephone, adresse, salaire, specialite, expertise,date_emb);
            break;

        case "chef":
            // Créer un chef
            nouvelEmploye = new Chef(id, nom, prenom, telephone, adresse, salaire,date_emb);
            break;

        default:
            System.out.println("Type d'employé invalide !");
            return;  // Sortir si le type est invalide
    }

    // Ajouter l'employé à la liste
    if (nouvelEmploye != null) {
        ListeEmployes.add(nouvelEmploye);
        System.out.println("L'employé " + nouvelEmploye.get_nom() + " a été créé et ajouté à la liste.");
    }
}


//Méthode pour afficher les employés
public void afficherTousLesEmployes() {
    if (ListeEmployes.isEmpty()) {
        System.out.println("Il n'y a pas d'employés à afficher.");
        return;
    }

    // Parcours de tous les employés dans la liste
    for (Employe employe : ListeEmployes) {
        // Afficher les informations de base de l'employé
        System.out.println("ID : " + employe.get_id());
        System.out.println("Nom : " + employe.get_nom());
        System.out.println("Prénom : " + employe.get_prenom());
        System.out.println("Téléphone : " + employe.get_telephone());
        System.out.println("Adresse : " + employe.get_adresse());
        System.out.println("Salaire : " + employe.getSalaire());
        
        // Vérifier le type d'employé et afficher des informations supplémentaires
        if (employe instanceof Laveur) {
            // Si l'employé est un Laveur, afficher les voitures lavées
            Laveur laveur = (Laveur) employe;
            System.out.println("Type : Laveur");
            try {
                laveur.afficher_historique_voitures();// Affiche les voitures lavées
            } catch (HistoriqueVoituresVideLavMecException e) {
                System.out.println("Aucune voiture lavée.");
            }
        } 
        else if (employe instanceof Mecanicien) {
            // Si l'employé est un Mécanicien, afficher les voitures réparées
            Mecanicien mecanicien = (Mecanicien) employe;
            System.out.println("Type : Mécanicien");
            try {
                mecanicien.afficher_historique_voitures();// Affiche les voitures réparées
            } catch (HistoriqueVoituresVideLavMecException e) {
                System.out.println("Aucune voiture réparée.");
            }
        } 
        else if (employe instanceof Chef) {
            // Si l'employé est un Chef, afficher l'équipe
            Chef chef = (Chef) employe;
            System.out.println("Type : Chef");
            try {
                chef.afficherEquipe();// Affiche l'équipe du chef
            } catch (EquipeVideException e) {
                System.out.println("L'équipe est vide.");
            }
        } 
        else {
            System.out.println("Type d'employé inconnu.");
        }
        System.out.println(); // Ajouter une ligne vide pour séparer chaque employé
    }
}
//Méthode pour afficher les employés
public void afficher_tous_les_employes() {
    if (ListeEmployes.isEmpty()) {
        System.out.println("Il n'y a pas d'employés à afficher.");
        return;
    }

    // Parcours de tous les employés dans la liste
    for (Employe employe : ListeEmployes) {
        // Afficher les informations de base de l'employé
        System.out.println("ID : " + employe.get_id());
        System.out.println("Nom : " + employe.get_nom());
        System.out.println("Prénom : " + employe.get_prenom());
        System.out.println("Téléphone : " + employe.get_telephone());
        System.out.println("Adresse : " + employe.get_adresse());
        System.out.println("Salaire : " + employe.getSalaire());
        
        // Vérifier le type d'employé et afficher des informations supplémentaires
        if (employe instanceof Laveur) {
            // Si l'employé est un Laveur, afficher les voitures lavées
            Laveur laveur = (Laveur) employe;
            System.out.println("Type : Laveur");
            try {
                laveur.afficher_historique_voitures(); // Affiche les voitures lavées
            } catch (HistoriqueVoituresVideLavMecException e) {
                System.out.println("Aucune voiture lavée.");
            }
        } 
        else if (employe instanceof Mecanicien) {
            // Si l'employé est un Mécanicien, afficher les voitures réparées, la spécialité et l'expertise
            Mecanicien mecanicien = (Mecanicien) employe;
            System.out.println("Type : Mécanicien");
            System.out.println("Spécialité : " + mecanicien.get_specialite());  // Affichage de la spécialité
            System.out.println("Expertise : " + mecanicien.get_expertise());  // Affichage de l'expertise
            try {
                mecanicien.afficher_historique_voitures(); // Affiche les voitures réparées
            } catch (HistoriqueVoituresVideLavMecException e) {
                System.out.println("Aucune voiture réparée.");
            }
        } 
        else if (employe instanceof Chef) {
            // Si l'employé est un Chef, afficher l'équipe
            Chef chef = (Chef) employe;
            System.out.println("Type : Chef");
            try {
                chef.afficherEquipe(); // Affiche l'équipe du chef
            } catch (EquipeVideException e) {
                System.out.println("L'équipe est vide.");
            }
        } 
        else {
            System.out.println("Type d'employé inconnu.");
        }
        System.out.println(); // Ajouter une ligne vide pour séparer chaque employé
    }
}

//Méthode pour supprimer un employé
public void supprimerEmploye(int id) {
    // Vérifier si la liste des employés est vide
    if (ListeEmployes.isEmpty()) {
        System.out.println("La liste des employés est vide.");
        return;
    }

    // Chercher l'employé par son ID
    Iterator<Employe> iterator = ListeEmployes.iterator();
    while (iterator.hasNext()) {
        Employe employe = iterator.next();
        
        // Si l'employé avec l'ID donné est trouvé, le supprimer
        if (employe.get_id() == id) {
            iterator.remove();
            System.out.println("L'employé  " + employe.get_nom() + " "+employe.get_prenom()+" a été supprimé.");
            return;
        }
    }
    
    // Si l'employé n'est pas trouvé
    System.out.println("Aucun employé trouvé avec l'ID " + id + ".");
}
//Méthode pour  ajouter voiture à un mecanicien ou un laveur 
public void ajouterVoitureMecLaveur(int employeId, String voitureId) throws VoitureExistanteDejaPourLavMecException {
    try {
        // Recherche de la voiture par son ID
        Voiture voiture = trouverVoitureParId(voitureId); // Méthode pour récupérer la voiture par son ID

        if (voiture == null) {
            System.out.println("Erreur : Aucune voiture trouvée avec l'ID " + voitureId);
            return; // Sortir de la méthode si la voiture n'existe pas
        }

        // Vérification si la voiture existe dans la liste des voitures disponibles
        if (!getListeVoitures().contains(voiture)) {
            System.out.println("Erreur : La voiture n'existe pas dans la liste des voitures disponibles.");
            return; // Sortir de la méthode si la voiture n'est pas présente
        }

        // Recherche de l'employé par son ID
        Employe employe = trouverEmployeParId(employeId); // Méthode pour récupérer l'employé par ID

        if (employe == null) {
            System.out.println("Erreur : Aucun employé trouvé avec l'ID " + employeId);
            return;
        }

        // Vérification que l'employé est un mécanicien ou un laveur
        if (employe instanceof Mecanicien) {
            Mecanicien mecanicien = (Mecanicien) employe; // Cast de l'employé en mécanicien

            // Vérifier si la voiture est déjà dans l'historique
            if (mecanicien.get_historique_voitures().contains(voiture)) {
                throw new VoitureExistanteDejaPourLavMecException("La voiture est déjà associée à ce mécanicien.");
            }

            mecanicien.ajouter_voiture(voiture); // Appel à la méthode ajouterVoiture du mécanicien
            System.out.println("La voiture a été ajoutée à l'historique du mécanicien.");
        } else if (employe instanceof Laveur) {
            Laveur laveur = (Laveur) employe; // Cast de l'employé en laveur

            // Vérifier si la voiture est déjà dans l'historique
            if (laveur.getVoitures().contains(voiture)) {
                throw new VoitureExistanteDejaPourLavMecException("La voiture est déjà associée à ce laveur.");
            }

            laveur.ajouter_voiture(voiture); // Appel à la méthode ajouterVoiture du laveur
            System.out.println("La voiture a été ajoutée à l'historique du laveur.");
        } else {
            System.out.println("L'employé avec l'ID " + employeId + " n'est ni un mécanicien ni un laveur.");
        }
    } catch (VoitureExistanteDejaPourLavMecException e) {
        // Gestion de l'exception si la voiture est déjà dans l'historique du mécanicien ou laveur
        throw e; // Relancer l'exception pour que le contrôleur puisse la gérer
    }
}

// Méthode pour supprimer voiture d'un mecanicien ou d'un laveur 
public void supprimerVoitureMecLaveur(int employeId, String voitureId) {
    try {
        // Recherche de l'employé par son ID
        Employe employe = trouverEmployeParId(employeId); // Méthode pour récupérer l'employé par ID

        if (employe == null) {
            System.out.println("Erreur : Aucun employé trouvé avec l'ID " + employeId);
            return;
        }

        // Vérification si l'employé est un chef
        if (employe instanceof Chef) {
            System.out.println("Erreur : L'employé concerné est un chef.");
            return; // Sortir de la méthode si l'employé est un chef
        }

        // Recherche de la voiture par son ID
        Voiture voiture = trouverVoitureParId(voitureId); // Méthode pour récupérer la voiture par ID

        if (voiture == null) {
            System.out.println("Erreur : Aucune voiture trouvée avec l'immatriculation  " + voitureId);
            return; // Sortir de la méthode si la voiture n'existe pas
        }
        
        // Vérification que l'employé est un mécanicien ou un laveur
        if (employe instanceof Mecanicien) {
            
            Mecanicien mecanicien = (Mecanicien) employe; // Cast en mécanicien
            mecanicien.supprimer_voiture(voiture); // Appel de la méthode supprimer_voiture
        } else if (employe instanceof Laveur) {
            Laveur laveur = (Laveur) employe; // Cast en laveur
            laveur.supprimer_voiture(voiture); // Appel de la méthode supprimer_voiture
        } else {
            System.out.println("L'employé avec l'ID " + employeId + " n'est ni un mécanicien ni un laveur.");
        }
    } catch (VoitureNonTrouveePourLavMecException e) {
        // Gestion de l'exception si la voiture n'est pas trouvée dans l'historique
        System.out.println("Erreur : " + e.getMessage());
    } catch (Exception e) {
        // Gestion des autres exceptions
        System.out.println("Erreur inattendue : " + e.getMessage());
    }
}


//Méthode pour trouver un employé en fonction de son id 
private Employe trouverEmployeParId(int id) {
    // Recherche de l'employé dans la collection d'employés (par exemple une liste ou un ensemble)
    for (Employe e : ListeEmployes) { // listeEmployes est la collection d'employés
        if (e.get_id() == id) {
            return e; // Retourne l'employé trouvé
        }
    }
    return null; // Retourne null si aucun employé n'a été trouvé
}


// Méthode pour ajouter un employe à l'équipe d un chef
public void ajouterEmployeAuChef(int chefId, int employeId) throws EmployeExistantException {
    try {
        // Récupérer le chef et l'employé
        Employe chef = trouverEmployeParId(chefId);
        Employe employe = trouverEmployeParId(employeId);

        // Vérifier si l'employé est déjà dans l'équipe du chef
        if (chef instanceof Chef) {
            Chef chefInstance = (Chef) chef;
            if (chefInstance.getEquipe().contains(employe)) {
                throw new EmployeExistantException("L'employé est déjà dans l'équipe du chef.");
            }

            // Ajouter l'employé à l'équipe du chef
            chefInstance.ajouterEmploye(employe);
        }
    } catch (Exception e) {
        // Gérer d'autres erreurs ici
        e.printStackTrace();
    }
}

// Méthode pour afficher tous les chefs
public void afficherChefs() {
    boolean chefTrouve = false;
    System.out.println("Liste des chefs :");

    // Parcours de la liste des employés
    for (Employe employe : ListeEmployes) { // listeEmployes représente la collection d'employés
        if (employe instanceof Chef) { // Vérifie si l'employé est une instance de Chef
            System.out.println(employe); // Affiche les détails du chef
            chefTrouve = true;
        }
    }

    // Message si aucun chef n'a été trouvé
    if (!chefTrouve) {
        System.out.println("Aucun chef trouvé dans la liste des employés.");
    }
}
// Méthode pour afficher tous les mecaniciens 
public void afficherMecaniciens() {
    boolean mecanicienTrouve = false;
    System.out.println("Liste des mécaniciens :");

    // Parcours de la liste des employés
    for (Employe employe : ListeEmployes) { // listeEmployes représente la collection d'employés
        if (employe instanceof Mecanicien) { // Vérifie si l'employé est une instance de Mecanicien
            System.out.println(employe); // Affiche les détails du mécanicien
            mecanicienTrouve = true;
        }
    }

    // Message si aucun mécanicien n'a été trouvé
    if (!mecanicienTrouve) {
        System.out.println("Aucun mécanicien trouvé dans la liste des employés.");
    }
}
// Méthode pour afficher tous les laveurs
public void afficherLaveurs() {
    boolean laveurTrouve = false;
    System.out.println("Liste des laveurs :");

    // Parcours de la liste des employés
    for (Employe employe : ListeEmployes) { // listeEmployes représente la collection d'employés
        if (employe instanceof Laveur) { // Vérifie si l'employé est une instance de Laveur
            System.out.println(employe); // Affiche les détails du laveur
            laveurTrouve = true;
        }
    }

    // Message si aucun laveur n'a été trouvé
    if (!laveurTrouve) {
        System.out.println("Aucun laveur trouvé dans la liste des employés.");
    }
}
//Méthode pour  afficher voitures par mecanicien 
public void afficherVoituresParMecanicien(int idEmploye) {
    // Recherche de l'employé par son ID dans la liste des employés
    Employe employeTrouve = null;
    for (Employe employe : ListeEmployes) {
        if (employe.get_id() == idEmploye) {
            employeTrouve = employe;
            break;
        }
    }

    // Vérification si l'employé existe
    if (employeTrouve == null) {
        System.out.println("Aucun employé trouvé avec l'ID " + idEmploye + ".");
        return;
    }

    // Vérification si l'employé est un mécanicien
    if (!(employeTrouve instanceof Mecanicien)) {
        System.out.println("L'employé avec l'ID " + idEmploye + " n'est pas un mécanicien.");
        return;
    }

    // Si l'employé est un mécanicien, on cast l'employé en Mecanicien
    Mecanicien mecanicien = (Mecanicien) employeTrouve;

    // Affichage des voitures associées au mécanicien
    ArrayList<Voiture> voituresAssociees = mecanicien.get_historique_voitures(); // Récupérer les voitures associées
    if (voituresAssociees.isEmpty()) {
        System.out.println("Le mécanicien " + mecanicien.get_nom() + " n'a aucune voiture associée.");
    } else {
        System.out.println("Voitures associées au mécanicien " + mecanicien.get_nom() + ":");
        for (Voiture voiture : voituresAssociees) {
            System.out.println(voiture); // Affiche les détails de chaque voiture
        }
    }
}
// Méthode pour afficher voitures par laveurs
public void afficherVoituresParLaveur(int idEmploye) {
    // Recherche de l'employé par son ID dans la liste des employés
    Employe employeTrouve = null;
    for (Employe employe : ListeEmployes) {
        if (employe.get_id() == idEmploye) {
            employeTrouve = employe;
            break;
        }
    }

    // Vérification si l'employé existe
    if (employeTrouve == null) {
        System.out.println("Aucun employé trouvé avec l'ID " + idEmploye + ".");
        return;
    }

    // Vérification si l'employé est un mécanicien
    if (!(employeTrouve instanceof Laveur)) {
        System.out.println("L'employé avec l'ID " + idEmploye + " n'est pas un laveur.");
        return;
    }

    // Si l'employé est un mécanicien, on cast l'employé en Mecanicien
    Laveur laveur = (Laveur) employeTrouve;

    // Affichage des voitures associées au mécanicien
    ArrayList<Voiture> voituresAssociees = laveur.getVoitures(); // Récupérer les voitures associées
    if (voituresAssociees.isEmpty()) {
        System.out.println("Le laveur " + laveur.get_nom() + " n'a aucune voiture associée.");
    } else {
        System.out.println("Voitures associées au laveur " + laveur.get_nom() + ":");
        for (Voiture voiture : voituresAssociees) {
            System.out.println(voiture); // Affiche les détails de chaque voiture
        }
    }
}



//Méthode pour modifier un  employé
public void modifierEmploye(int id) {
    // Rechercher l'employé avec l'ID fourni
    for (Employe employe : ListeEmployes) {
        if (employe.get_id() == id) {
            System.out.println("Modification de l'employé ID : " + id);

            // Vérifier le type d'employé et appeler la méthode modifier appropriée
            if (employe instanceof Mecanicien) {
                ((Mecanicien) employe).modifier();
            } else if (employe instanceof Laveur) {
                ((Laveur) employe).modifier();
            } else {
                System.out.println("Un chef ne peut pas etre modifiable.");
            }
            return;
        }
    }
    System.out.println("Aucun employé trouvé avec l'ID : " + id);
}



/*
public void creerRendezVous(int idRendezVous, String description, int idClient, String immatriculationVoiture) {
    // Vérification du client
    Client client = listeClients.stream()
            .filter(c -> c.get_id() == idClient)
            .findFirst()
            .orElse(null);

    if (client == null) {
        System.out.println("Client introuvable !");
        return;  // Arrête la méthode si le client n'est pas trouvé
    }

    // Vérification de la voiture dans la liste générale des voitures
    Voiture voitureGenerale = ListeVoitures.stream()
            .filter(v -> v.get_immatriculation().equals(immatriculationVoiture))
            .findFirst()
            .orElse(null);

    if (voitureGenerale == null) {
        System.out.println("La voiture avec l'immatriculation " + immatriculationVoiture + " n'existe pas dans la liste générale des voitures !");
        return;  // Arrête la méthode si la voiture n'existe pas dans la liste générale
    }

    // Vérification que la voiture appartient bien au client
    Voiture voitureClient = client.getVoitures().stream()
            .filter(v -> v.get_immatriculation().equals(immatriculationVoiture))
            .findFirst()
            .orElse(null);

    if (voitureClient == null) {
        System.out.println("La voiture avec l'immatriculation " + immatriculationVoiture + " n'appartient pas au client !");
        return;  // Arrête la méthode si la voiture n'appartient pas au client
    }

    // Création du rendez-vous avec l'ID passé en paramètre
    Rendez_vous rendezVous = new Rendez_vous(idRendezVous, description, voitureClient, client);

    // Ajout du rendez-vous à la liste globale
    listeRendezVous.add(rendezVous);

    System.out.println("Rendez-vous créé avec succès !");
    System.out.println("ID du rendez-vous : " + idRendezVous);
    rendezVous.toString();
}
*/


    public void creerRendezVous(int idRendezVous, String description, int idClient, String immatriculationVoiture, LocalDate date, String statut) {
        // Vérification du client
        Client client = listeClients.stream()
                .filter(c -> c.get_id() == idClient)
                .findFirst()
                .orElse(null);

        if (client == null) {
            System.out.println("Client introuvable !");
            throw new IllegalArgumentException("Client introuvable");
        }

        // Vérification de la voiture dans la liste générale des voitures
        Voiture voitureGenerale = ListeVoitures.stream()
                .filter(v -> v.get_immatriculation().equals(immatriculationVoiture))
                .findFirst()
                .orElse(null);

        if (voitureGenerale == null) {
            System.out.println("La voiture avec l'immatriculation " + immatriculationVoiture + " n'existe pas dans la liste générale des voitures !");
            throw new IllegalArgumentException("Voiture non trouvée dans la liste générale");
        }

        // Vérification que la voiture appartient bien au client
        Voiture voitureClient = client.getVoitures().stream()
                .filter(v -> v.get_immatriculation().equals(immatriculationVoiture))
                .findFirst()
                .orElse(null);

        if (voitureClient == null) {
            System.out.println("La voiture avec l'immatriculation " + immatriculationVoiture + " n'appartient pas au client !");
            throw new IllegalArgumentException("La voiture ne appartient pas au client");
        }

        // Création du rendez-vous avec l'ID, description, client, voiture, date, heure et statut
        Rendez_vous rendezVous = new Rendez_vous(idRendezVous, description, voitureClient, client, date, StatutRendezVous.valueOf(statut));

        // Ajout du rendez-vous à la liste globale
        listeRendezVous.add(rendezVous);

        System.out.println("Rendez-vous créé avec succès !");
        System.out.println("ID du rendez-vous : " + idRendezVous);
    }

    // Méthode pour afficher le niveau d'un employé par son ID
      private int calculerExperience(Employe employe) {
        // Logique pour calculer l'expérience en fonction de la date d'embauche
        // (par exemple, en utilisant la date actuelle et la date d'embauche)
        LocalDate dateEmbauche = employe.getDate_embauche();  // supposons que la méthode getDateEmbauche() existe
        LocalDate dateActuelle = LocalDate.now();
        
        // Calcul de l'expérience en années
        int experience = (int) ChronoUnit.YEARS.between(dateEmbauche, dateActuelle);
        return experience;
    }
    public void afficherNiveauEmploye(int idEmploye) {
        // Recherche de l'employé dans la liste par son ID
        Employe employe = null;
        for (Employe e : ListeEmployes) {
            if (e.get_id() == idEmploye) {
                employe = e;
                break; // L'employé a été trouvé, on sort de la boucle
            }
        }

        // Si l'employé n'existe pas
        if (employe == null) {
            System.out.println("Aucun employé trouvé avec l'ID " + idEmploye);
            return; // On arrête la méthode si l'employé n'est pas trouvé
        }

        // Calcul de l'expérience de l'employé
        int experience = calculerExperience(employe);  // Calcul de l'expérience

        // Si l'employé existe, on vérifie son type et on affiche son niveau avec l'expérience
        if (employe instanceof Mecanicien) {
            Mecanicien mecanicien = (Mecanicien) employe; // Cast en Mécanicien
            mecanicien.afficherNiveau(experience);  // Appel avec l'expérience
        } else if (employe instanceof Laveur) {
            Laveur laveur = (Laveur) employe; // Cast en Laveur
            laveur.afficherNiveau(experience);  // Appel avec l'expérience
        } else if (employe instanceof Chef) {
            Chef chef = (Chef) employe; // Cast en Chef
            chef.afficherNiveau(experience);  // Appel avec l'expérience
        } else {
            System.out.println("Le type d'employé avec l'ID " + idEmploye + " n'est pas reconnu.");
        }
    }
    //stream pour afficher liste d employe par niveau d experience (de plus experimenté au moin experimenté)
     public void afficherListeEmployesParExperience() {
        // Vérification si la liste d'employés est vide
        if (ListeEmployes.isEmpty()) {
            System.out.println("La liste des employés est vide.");
            return;
        }

        // Tri des employés en fonction de leur expérience (du plus expérimenté au moins expérimenté)
        List<Employe> listeTriee = ListeEmployes.stream()
                .sorted((e1, e2) -> Integer.compare(calculerExperience(e2), calculerExperience(e1)))  // Tri décroissant
                .collect(Collectors.toList());

        // Affichage des employés triés par expérience
        System.out.println("Liste des employés triée par expérience :");
        for (Employe employe : listeTriee) {
            System.out.println(employe.get_nom() + " " + employe.get_prenom() + " - Expérience : " + calculerExperience(employe) + " années");
        }
    }

    public void creerService(Service service) {
        if (service != null) {
            ListeServices.add(service);
            System.out.println("Service ajouté avec succès : " + service);
        } else {
            System.out.println("Erreur : Le service est null et ne peut pas être ajouté.");
        }
    }




        /*public void creerService(Service nouveauService) {
            if (nouveauService != null) {
                ListeServices.add(nouveauService); // Ajouter le service à la liste
                System.out.println("Service ajouté : " + nouveauService);
            } else {
                System.out.println("Service invalide. Aucune action effectuée.");
            }
        }*/
}
























































//








































































































































    















































   

