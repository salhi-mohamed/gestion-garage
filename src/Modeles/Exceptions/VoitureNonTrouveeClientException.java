package Modeles.Exceptions;


public class VoitureNonTrouveeClientException extends Exception {
    
    // Constructeur sans paramètre
    public VoitureNonTrouveeClientException() {
        super("Voiture non trouvée.");
    }
    
    // Constructeur avec un message personnalisé
    public VoitureNonTrouveeClientException(String message) {
        super(message);
    }
    
    // Constructeur avec un message et la cause
    public VoitureNonTrouveeClientException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Constructeur avec la cause de l'exception
    public VoitureNonTrouveeClientException(Throwable cause) {
        super(cause);
    }
}

