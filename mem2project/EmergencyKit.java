package ecodefense;

import java.util.ArrayList;

/**
 * EmergencyKit - Concrete Class implementing Inventory
 *
 * Tracks vital items needed to withstand natural disasters.
 */
public class EmergencyKit implements Inventory {

    //  Attributes 
    private ArrayList<String> items;
    private int waterSupplyDays;
    private boolean hasFirstAid;

    //  Constructor 
    public EmergencyKit() {
        this.items = new ArrayList<>();
        this.waterSupplyDays = 0;
        this.hasFirstAid = false;
    }

    //  Method Overriding (Interface Contract)
    @Override
    public void addItem(String item) {
        if (item == null || item.trim().isEmpty()) return;
        
        String cleanItem = item.trim().toLowerCase();
        if (!items.contains(cleanItem)) {
            items.add(cleanItem);
            System.out.println(" [KIT] Added item: " + item);
        }
        
        // Contextually update structural flags if matched
        if (cleanItem.contains("first aid") || cleanItem.contains("medical kit")) {
            this.hasFirstAid = true;
        }
    }

    //  Method Overloading (Same name, different parameters) 
    /**
     * Overloaded method to add water rations to the system.
     * * @param item Must be "water" to count towards waterSupplyDays.
     * @param daysRation The number of days this water pack will sustain a person.
     */
    public void addItem(String item, int daysRation) {
        if (item == null || item.trim().isEmpty()) return;
        
        String cleanItem = item.trim().toLowerCase();
        if (cleanItem.equals("water")) {
            this.waterSupplyDays += daysRation;
            System.out.println(" [KIT] Rations updated: Added " + daysRation + " day(s) of Water supply.");
            
            if (!items.contains("water")) {
                items.add("water");
            }
        } else {
            // Fallback for non-water items with quantities
            System.out.println(" [KIT] Added " + daysRation + "x units of " + item);
            if (!items.contains(cleanItem)) {
                items.add(cleanItem);
            }
        }
    }

    //  Interface Completeness Check 
    @Override
    public boolean checkCompleteness() {
        // A kit is considered contextually safe if it has water for at least 3 days,
        // a medical kit, and an N95 mask or flashlight (basic utility).
        boolean hasWaterMinimum = (waterSupplyDays >= 3);
        boolean hasUtility = items.contains("flashlight") || items.contains("n95 mask") || items.contains("radio");
        
        return hasFirstAid && hasWaterMinimum && hasUtility;
    }

    // Simulation Trigger (Launches the Exception)
    /**
     * Validates safety preparedness before letting a user enter a disaster zone quiz.
     * * @param disasterType The target crisis scenario.
     * @throws IncompleteKitException if the inventory is unsafe.
     */
    public void ventureIntoScenario(String disasterType) throws IncompleteKitException {
        System.out.println("\n Attempting to enter " + disasterType.toUpperCase() + " Simulation Module...");
        
        if (!checkCompleteness()) {
            int missingWater = Math.max(0, 3 - waterSupplyDays);
            String reason = " PREPAREDNESS FAILURE! You cannot enter the " + disasterType + " scenario safely.\n"
                    + "    -> Reason: Current kit configuration is insufficient.\n"
                    + "    -> Missing Requirements: " 
                    + (!hasFirstAid ? "[First Aid Kit] " : "")
                    + (missingWater > 0 ? "[" + missingWater + " More Day(s) of Water] " : "")
                    + "Please stock up your inventory first!";
            
            throw new IncompleteKitException(reason);
        }
        
        System.out.println("  ACCESS GRANTED. Your kit is secure. Proceeding safely into the " + disasterType + " module!");
    }

    //  Status Visualizer
    public void showKitStatus() {
        System.out.println("\n CURRENT EMERGENCY KIT INVENTORY STATUS:");
        System.out.println("=".repeat(50));
        System.out.println("  • Registered Items : " + (items.isEmpty() ? "Empty" : items));
        System.out.println("  • Water Reserves    : " + waterSupplyDays + " / 3 Days Minimum");
        System.out.println("  • First Aid Status  : " + (hasFirstAid ? " EQUIPPED" : " MISSING"));
        System.out.println("  • Safety Rating     : " + (checkCompleteness() ? " READY FOR ACTION" : "  INCOMPLETE"));
        System.out.println("=".repeat(50));
    }

    // Getters
    public ArrayList<String> getItems()      { return items; }
    public int getWaterSupplyDays()          { return waterSupplyDays; }
    public boolean isHasFirstAid()           { return hasFirstAid; }
}
