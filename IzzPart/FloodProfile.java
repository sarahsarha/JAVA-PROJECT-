package ecodefense;

/**
 * FloodProfile — Child Class of DisasterProfile
 *
 * Represents the Flood disaster type. Overrides displayEduContent() with
 * flood-specific science and safety facts, demonstrating INHERITANCE
 * and POLYMORPHISM from the abstract parent.
 */
public class FloodProfile extends DisasterProfile {

    // ─── Constructor ──────────────────────────────────────────────────────────

    public FloodProfile() {
        super(
            "Flood",

            // Climate link explanation
            "Rising global temperatures intensify the water cycle: warmer air holds more "
            + "moisture, leading to heavier rainfall events. Melting glaciers and rising sea "
            + "levels further increase coastal and riverine flood risk. Since 1980, flood "
            + "events globally have increased by over 134%.",

            // Evacuation steps
            new String[]{
                "Monitor local weather alerts and river level reports.",
                "Move valuables and important documents to higher floors immediately.",
                "Turn off electricity at the main breaker if flooding is imminent.",
                "Never walk or drive through floodwaters — 6 inches can knock you down.",
                "Follow your pre-planned evacuation route to higher ground.",
                "Contact local emergency services and register your location.",
                "Do NOT return home until authorities declare it safe."
            }
        );
    }

    // ─── Polymorphic Override ─────────────────────────────────────────────────

    /**
     * Displays flood-specific educational content.
     * This polymorphic override is called when displayEduContent() is invoked
     * on a FloodProfile object — even through a DisasterProfile reference.
     */
    @Override
    public void displayEduContent() {
        System.out.println("\n🌊 ECO-DEFENSE | DISASTER PROFILE: FLOODS");
        System.out.println("=".repeat(55));
        System.out.println("📌 Climate Link:");
        System.out.println("   " + climateLinkExplanation);
        System.out.println();
        System.out.println("📊 Key Facts:");
        System.out.println("   • Floods are the most common natural disaster worldwide.");
        System.out.println("   • Flash floods can produce walls of water 10–20 feet high.");
        System.out.println("   • 2 inches of fast-moving water can sweep away a car.");
        System.out.println("   • 90% of all U.S. natural disaster declarations involve flooding.");
        System.out.println();
        System.out.println("🌡️  Climate Science:");
        System.out.println("   For every 1°C rise in temperature, the atmosphere holds");
        System.out.println("   ~7% more water vapour — making storms significantly wetter.");
        System.out.println("=".repeat(55));
    }

    // ─── AlertSystem: broadcastSafetyTips ────────────────────────────────────

    /**
     * Broadcasts flood-specific safety tips.
     * Fulfils the broadcastSafetyTips() contract from the AlertSystem interface.
     */
    @Override
    public void broadcastSafetyTips() {
        System.out.println("\n💡 FLOOD SAFETY TIPS:");
        System.out.println("   ✔ Know your area's flood zone designation.");
        System.out.println("   ✔ Store an emergency kit on an upper floor.");
        System.out.println("   ✔ Purchase flood insurance — standard home insurance won't cover it.");
        System.out.println("   ✔ Never ignore evacuation orders — act early.");
        System.out.println("   ✔ After flooding, watch for contaminated water and structural damage.\n");
    }
}
