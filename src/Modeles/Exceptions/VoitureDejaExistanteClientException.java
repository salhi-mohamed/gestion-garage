/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles.Exceptions;

/**
 *
 * @author LENOVO
 */
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
