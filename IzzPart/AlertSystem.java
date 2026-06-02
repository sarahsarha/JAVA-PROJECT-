package ecodefense;

/**
 * AlertSystem Interface - Member 1: Core Systems & Disaster Profiles
 *
 * Defines the contract for broadcasting emergency alerts and safety tips
 * across the Eco-Defense application. Any class that handles warnings
 * (disaster profiles, notification services, etc.) must implement this.
 */
public interface AlertSystem {

    /**
     * Triggers an emergency alert with a specific message.
     * Implementing classes should display this prominently to the user.
     *
     * @param message The alert message to broadcast (e.g., "Flash flood warning!")
     */
    void triggerAlert(String message);

    /**
     * Broadcasts a set of general safety tips relevant to the current disaster type.
     * Implementing classes should pull tips from their own disaster context.
     */
    void broadcastSafetyTips();
}
