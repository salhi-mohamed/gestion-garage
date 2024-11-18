/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles.Exceptions;

/**
 *
 * @author LENOVO
 */
public class ArgumentInvalideException extends Exception {
    
    // Constructeur sans paramètre
    public ArgumentInvalideException() {
        super("Argument invalide.");
    }
    
    // Constructeur avec un message personnalisé
    public ArgumentInvalideException(String message) {
        super(message);
    }
    
    // Constructeur avec un message et la cause
    public ArgumentInvalideException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Constructeur avec la cause de l'exception
    public ArgumentInvalideException(Throwable cause) {
        super(cause);
    }
}
