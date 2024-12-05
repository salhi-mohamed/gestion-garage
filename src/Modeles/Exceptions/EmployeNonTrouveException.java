package Modeles.Exceptions;


public class EmployeNonTrouveException extends Exception {
    public EmployeNonTrouveException(String message) {
        super(message); // Passer le message d'exception au constructeur parent
    }
}
