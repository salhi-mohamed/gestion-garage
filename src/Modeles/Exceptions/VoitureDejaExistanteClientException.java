package Modeles.Exceptions;


public class VoitureDejaExistanteClientException extends Exception {

    // Constructeur sans paramètre
    public VoitureDejaExistanteClientException() {
        super("Cette voiture existe déjà dans la liste.");
    }

    // Constructeur avec un message personnalisé
    public VoitureDejaExistanteClientException(String message) {
        super(message);
    }

    // Constructeur avec message et cause de l'exception
    public VoitureDejaExistanteClientException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec cause de l'exception
    public VoitureDejaExistanteClientException(Throwable cause) {
        super(cause);
    }
}
