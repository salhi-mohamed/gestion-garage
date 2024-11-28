/*package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:49161:xe"; // URL pour Oracle
    private static final String USER = "Gestion_Garage_Auto";
    private static final String PASSWORD = "123456";

    // Méthode pour obtenir la connexion à la base de données
    public static Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            // Charger le driver Oracle JDBC (si nécessaire)
            // Class.forName("oracle.jdbc.OracleDriver"); // En général, cela n'est plus nécessaire avec JDBC 4.0+

            // Établir la connexion
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Erreur de connexion à la base de données Oracle", e);
        }
        return con;
    }
}*/
