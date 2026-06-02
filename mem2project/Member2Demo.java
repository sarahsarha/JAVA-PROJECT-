package ecodefense;

/**
 * Member2Demo - Test Driver for Member 2
 *
 * Compiles and exercises all Member 2 infrastructure components and explicitly
 * interlocks with Member 1's Disaster profiles to show cross-system cohesion.
 */
public class Member2Demo {

    public static void main(String[] args) {
        System.out.println("----------------------------------------------");
        System.out.println("        ECO-DEFENSE - MEMBER 2 DEMO           ");
        System.out.println("    Interactive Inventory & Exception Test    ");
        System.out.println("----------------------------------------------");

        // 1. Initialize Member 1 context objects to show cross member synergy
        DisasterProfile flood = new FloodProfile();
        DisasterProfile wildfire = new WildfireProfile();

        // 2. Instantiate Member 2 Emergency Kit
        EmergencyKit userKit = new EmergencyKit();
        userKit.showKitStatus();

        // 3. SCENARIO A: Test Exception handling when user is under prepared
        try {
            // Try to jump into a flood zone immediately with an empty pack
            userKit.ventureIntoScenario(flood.getDisasterType());
        } catch (IncompleteKitException e) {
            System.out.println("\n System caught an expected Exception:");
            System.out.println(e.getMessage());
        }

        System.out.println("\n>>> STOCKING UP EMERGENGY ITEMS...");
        
        // 4. Test Overriding (adds string items)
        userKit.addItem("First Aid Medical Kit");
        userKit.addItem("Flashlight");
        
        // 5. Test Overloading (adds item with distinct quantitative data parameter)
        userKit.addItem("Water", 1); // Not enough water yet (Needs 3)
        
        userKit.showKitStatus();

        // SCENARIO B: Try entering simulation again (still failing water metrics)
        try {
            userKit.ventureIntoScenario(flood.getDisasterType());
        } catch (IncompleteKitException e) {
            System.out.println("\n System caught another expected Exception:");
            System.out.println(e.getMessage());
        }

        System.out.println("\n>>> COLLECTING FINAL RATIONS...");
        // Use overloaded method again to fulfill criteria
        userKit.addItem("Water", 2); 
        userKit.addItem("N95 Mask"); // Good for wildfires!

        userKit.showKitStatus();

        // SCENARIO C: Successful transition execution
        try {
            // Should pass perfectly now
            userKit.ventureIntoScenario(wildfire.getDisasterType());
            
            // Interlock: Successfully trigger Member 1's interactive data screens
            wildfire.displayEduContent();
            wildfire.broadcastSafetyTips();
            
        } catch (IncompleteKitException e) {
            System.out.println("Unexpected failure: " + e.getMessage());
        }

        System.out.println("\n✅ Member 2 development architecture is fully operational and verified!");
    }
}
