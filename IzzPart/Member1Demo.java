package ecodefense;

/**
 * Member1Demo — Test Driver for Member 1: Core Systems & Disaster Profiles
 *
 * Demonstrates:
 *  ✅ AlertSystem interface (via triggerAlert + broadcastSafetyTips)
 *  ✅ DisasterProfile abstract class (abstraction)
 *  ✅ FloodProfile, WildfireProfile, StormProfile (inheritance)
 *  ✅ displayEduContent() polymorphism via parent reference
 *  ✅ viewEmergencyRoute() shared/inherited method
 */
public class Member1Demo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║        ECO-DEFENSE — MEMBER 1 DEMO           ║");
        System.out.println("║   Core Systems & Disaster Profiles Test       ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        // ── Create disaster profiles (child class objects) ──────────────────
        DisasterProfile flood    = new FloodProfile();
        DisasterProfile wildfire = new WildfireProfile();
        DisasterProfile storm    = new StormProfile();

        // Store in array to demonstrate POLYMORPHISM
        DisasterProfile[] profiles = { flood, wildfire, storm };

        // ── Polymorphic loop: same call, different behaviour ─────────────────
        System.out.println("\n>>> LOADING ALL EDUCATIONAL CONTENT PAGES...");
        for (DisasterProfile profile : profiles) {
            profile.displayEduContent();     // Polymorphic — each subclass responds differently
        }

        // ── AlertSystem interface demo ───────────────────────────────────────
        System.out.println("\n>>> TRIGGERING ALERTS (AlertSystem interface)...");
        flood.triggerAlert("River levels critical — evacuate Zone A NOW!");
        wildfire.triggerAlert("Red Flag Warning active — extreme fire danger!");
        storm.triggerAlert("Hurricane Category 3 making landfall in 6 hours!");

        // ── broadcastSafetyTips via AlertSystem ──────────────────────────────
        System.out.println("\n>>> BROADCASTING SAFETY TIPS...");
        flood.broadcastSafetyTips();
        wildfire.broadcastSafetyTips();
        storm.broadcastSafetyTips();

        // ── Inherited viewEmergencyRoute method ──────────────────────────────
        System.out.println("\n>>> VIEWING EVACUATION ROUTES (inherited method)...");
        flood.viewEmergencyRoute();
        wildfire.viewEmergencyRoute();
        storm.viewEmergencyRoute();

        System.out.println("✅ Member 1 demo complete. All components functional.");
    }
}
