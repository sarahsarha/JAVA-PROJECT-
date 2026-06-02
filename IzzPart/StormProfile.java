package ecodefense;

/**
 * StormProfile — Child Class of DisasterProfile
 *
 * Represents the Storm disaster type (hurricanes, tornadoes, severe thunderstorms).
 * Demonstrates INHERITANCE and POLYMORPHISM through its override of displayEduContent().
 */
public class StormProfile extends DisasterProfile {

    // ─── Constructor ──────────────────────────────────────────────────────────

    public StormProfile() {
        super(
            "Storm",

            // Climate link explanation
            "Warmer ocean surfaces provide more energy to tropical storms, increasing their "
            + "intensity and the likelihood of rapid intensification. Climate change is also "
            + "causing storms to stall more frequently, dumping far more rain over one area. "
            + "Category 4 and 5 hurricanes have become significantly more common since the 1980s.",

            // Evacuation steps
            new String[]{
                "Track the storm using official weather services and apps.",
                "Secure or bring indoors all outdoor furniture and loose objects.",
                "Fill bathtubs with water in case supply is disrupted.",
                "Charge all devices and prepare power banks for outages.",
                "Identify your nearest storm shelter or reinforced interior room.",
                "If in a flood-prone area, evacuate before the storm arrives.",
                "Stay indoors and away from windows during the storm.",
                "After the storm, beware of downed power lines and debris."
            }
        );
    }

    // ─── Polymorphic Override ─────────────────────────────────────────────────

    /**
     * Displays storm-specific educational content (GUI screen content).
     */
    @Override
    public void displayEduContent() {
        System.out.println("\n⛈️  ECO-DEFENSE | DISASTER PROFILE: STORMS");
        System.out.println("=".repeat(55));
        System.out.println("📌 Climate Link:");
        System.out.println("   " + climateLinkExplanation);
        System.out.println();
        System.out.println("📊 Key Facts:");
        System.out.println("   • Atlantic hurricane intensity has increased ~8% per decade since 1980.");
        System.out.println("   • A single hurricane can release the energy of 10,000 nuclear bombs.");
        System.out.println("   • Storm surge, not wind, causes the majority of hurricane fatalities.");
        System.out.println("   • Tornadoes can reach wind speeds of over 300 mph.");
        System.out.println();
        System.out.println("🌡️  Climate Science:");
        System.out.println("   Sea surface temperatures above 26°C (79°F) fuel tropical cyclones.");
        System.out.println("   With oceans warming faster than ever, the 'hurricane season'");
        System.out.println("   window is expanding beyond its traditional June–November range.");
        System.out.println("=".repeat(55));
    }

    // ─── AlertSystem: broadcastSafetyTips ────────────────────────────────────

    /**
     * Broadcasts storm-specific safety tips.
     */
    @Override
    public void broadcastSafetyTips() {
        System.out.println("\n💡 STORM SAFETY TIPS:");
        System.out.println("   ✔ Know the difference: a Watch means conditions are possible;");
        System.out.println("     a Warning means they are imminent — act immediately.");
        System.out.println("   ✔ If a tornado approaches, go to the lowest floor, innermost room.");
        System.out.println("   ✔ Never shelter under a highway overpass during a tornado.");
        System.out.println("   ✔ Keep a battery-powered NOAA weather radio in your emergency kit.");
        System.out.println("   ✔ After the storm, avoid floodwater — it may be electrically charged.\n");
    }
}
