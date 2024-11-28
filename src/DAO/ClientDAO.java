/*package DAO;

import Modeles.Exceptions.ClientExisteException;
import Modeles.Personnes.Client;

import java.sql.*;

public class ClientDAO {
    private Connection connection;

    // Constructeur avec une connexion
    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    // Vérifie si un client existe dans la base de données
    public boolean clientExiste(int id) {
        String query = "SELECT COUNT(*) FROM Client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Ajoute un client dans la base de données
    public void creerClient(int id, String nom, String prenom, int telephone, String adresse, String statutFinancier) throws ClientExisteException {
        // Créer une requête SQL pour ajouter un client à la base de données
        String query = "INSERT INTO Client (ID_CLIENT, NOM, PRENOM, TELEPHONE, ADRESSE, Statut-FINANCIER) VALUES (?, ?, ?, ?, ?, ?)";

        String url = "jdbc:oracle:thin:@localhost:49161:xe"; // Remplacez par l'URL de votre base de données
        String user = "Gestion_Garage_Auto"; // Remplacez par votre nom d'utilisateur pour la base de données
        String password = "123456"; // Remplacez par votre mot de passe de la base de données

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setInt(4, telephone);
            preparedStatement.setString(5, adresse);
            preparedStatement.setString(6, statutFinancier);

            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                throw new ClientExisteException("Le client n'a pas pu être ajouté.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ClientExisteException("Erreur de connexion à la base de données.");
        }
    }


}*/
