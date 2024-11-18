/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles.Exceptions;

/**
 *
 * @author LENOVO
 */
public class EmployeNonTrouveException extends Exception {
    public EmployeNonTrouveException(String message) {
        super(message); // Passer le message d'exception au constructeur parent
    }
}
