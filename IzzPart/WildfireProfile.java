package ecodefense;

/**
 * WildfireProfile — Child Class of DisasterProfile
 *
 * Represents the Wildfire disaster type. Demonstrates INHERITANCE from
 * DisasterProfile and POLYMORPHISM by overriding displayEduContent()
 * with wildfire-specific science and safety content.
 */
public class WildfireProfile extends DisasterProfile {

    // ─── Constructor ──────────────────────────────────────────────────────────

    public WildfireProfile() {
        super(
            "Wildfire",

            // Climate link explanation
            "Higher temperatures dry out vegetation, creating abundant fuel for fires. "
            + "Prolonged droughts (amplified by climate change) and shifting wind patterns "
            + "allow wildfires to ignite faster and spread further. The global area burned "
            + "by wildfires has increased significantly, with fire seasons now lasting "
            + "weeks longer than they did 50 years ago.",

            // Evacuation steps
            new String[]{
                "Sign up for local emergency alerts and monitor air quality indexes.",
                "Prepare a 'go-bag' with essentials ready to leave within minutes.",
                "Close all windows, doors, and vents to slow smoke infiltration.",
                "Move flammable furniture and items away from exterior walls.",
                "If ordered to evacuate, leave IMMEDIATELY — do not wait to see flames.",
                "Travel away from the fire, not parallel to it.",
                "Inform someone of your evacuation destination and route.",
                "Do not return until fire authorities lift the evacuation order."
            }
        );
    }

    // ─── Polymorphic Override ─────────────────────────────────────────────────

    /**
     * Displays wildfire-specific educational content (GUI screen content).
     */
    @Override
    public void displayEduContent() {
        System.out.println("\n🔥 ECO-DEFENSE | DISASTER PROFILE: WILDFIRES");
        System.out.println("=".repeat(55));
        System.out.println("📌 Climate Link:");
        System.out.println("   " + climateLinkExplanation);
        System.out.println();
        System.out.println("📊 Key Facts:");
        System.out.println("   • Wildfires can spread at speeds of up to 14 mph in forests.");
        System.out.println("   • In grassland, fires can travel up to 70 mph.");
        System.out.println("   • Smoke from wildfires contains toxic PM2.5 particles.");
        System.out.println("   • The Western U.S. fire season is now 78 days longer than in 1970.");
        System.out.println();
        System.out.println("🌡️  Climate Science:");
        System.out.println("   Each 1°C of warming increases the area burned in some regions");
        System.out.println("   by up to 600%. Vapour pressure deficit — a measure of how thirsty");
        System.out.println("   the atmosphere is — is a key driver of extreme fire behaviour.");
        System.out.println("=".repeat(55));
    }

    // ─── AlertSystem: broadcastSafetyTips ────────────────────────────────────

    /**
     * Broadcasts wildfire-specific safety tips.
     */
    @Override
    public void broadcastSafetyTips() {
        System.out.println("\n💡 WILDFIRE SAFETY TIPS:");
        System.out.println("   ✔ Create a 30-foot 'defensible space' around your home.");
        System.out.println("   ✔ Use fire-resistant materials for roofing and decks.");
        System.out.println("   ✔ Keep an N95 mask in your emergency kit for smoke protection.");
        System.out.println("   ✔ Never use water from a garden hose to fight a wildfire — evacuate.");
        System.out.println("   ✔ After a fire, beware of ash contamination and structural hazards.\n");
    }
}
