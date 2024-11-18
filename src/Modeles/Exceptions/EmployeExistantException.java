/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles.Exceptions;

/**
 *
 * @author LENOVO
 */
public class EmployeExistantException extends Exception {

    // Constructeur qui accepte un message d'erreur
    public EmployeExistantException(String message) {
        super(message);  // Appel du constructeur parent de Exception
    }
}