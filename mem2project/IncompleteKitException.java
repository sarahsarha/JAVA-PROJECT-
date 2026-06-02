package ecodefense;

/**
 * IncompleteKitException - Member 2 Custom Exception
 * * Thrown when a user tries to venture into a disaster scenario or start 
 * a quiz simulation without the minimum survival equipment.
 */
public class IncompleteKitException extends Exception {
    
    public IncompleteKitException(String message) {
        super(message);
    }
}