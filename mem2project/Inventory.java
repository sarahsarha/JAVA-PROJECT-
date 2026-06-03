package ecodefense;

/**
 * Inventory Interface - Member 2: Interactive Inventory & Preparedness
 *
 * Defines the operational contract for tracking and verifying emergency items 
 * inside a survival pack.
 */
public interface Inventory {

    /**
     * Adds a single unit of an item to the inventory.
     * @param item The name of the item to add.
     */
    void addItem(String item);

    /**
     * Checks if the inventory meets the baseline criteria for basic survival safety.
     * @return true if the kit is safely complete; false otherwise.
     */
    boolean checkCompleteness();
}