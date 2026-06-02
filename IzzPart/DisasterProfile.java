package ecodefense;

/**
 * DisasterProfile Abstract Class - Member 1: Core Systems & Disaster Profiles
 *
 * Serves as the base blueprint for all natural disaster types in the app.
 * Marked abstract to enforce that each disaster (Flood, Wildfire, Storm)
 * provides its own educational display — achieving ABSTRACTION + POLYMORPHISM.
 *
 * Implements AlertSystem so every disaster profile can trigger its own alerts.
 */
public abstract class DisasterProfile implements AlertSystem {

    // ─── Attributes ───────────────────────────────────────────────────────────

    /** E.g., "Flood", "Wildfire", "Storm" */
    protected String disasterType;

    /** Explains how climate change links to this disaster's increased frequency. */
    protected String climateLinkExplanation;

    /** Step-by-step evacuation instructions for this disaster type. */
    protected String[] evacuationSteps;

    // ─── Constructor ──────────────────────────────────────────────────────────

    /**
     * @param disasterType          The name of the disaster.
     * @param climateLinkExplanation How climate change worsens this disaster.
     * @param evacuationSteps       Ordered steps for safely evacuating.
     */
    public DisasterProfile(String disasterType,
                           String climateLinkExplanation,
                           String[] evacuationSteps) {
        this.disasterType = disasterType;
        this.climateLinkExplanation = climateLinkExplanation;
        this.evacuationSteps = evacuationSteps;
    }

    // ─── Abstract Method (Polymorphism) ───────────────────────────────────────

    /**
     * Displays the educational content screen for this specific disaster.
     * Each child class (FloodProfile, WildfireProfile, StormProfile) overrides
     * this to show disaster-specific facts, stats, and climate science.
     *
     * POLYMORPHIC: Called generically on any DisasterProfile reference,
     * but behaves differently depending on the actual subclass.
     */
    public abstract void displayEduContent();

    // ─── Concrete Method ──────────────────────────────────────────────────────

    /**
     * Displays the evacuation route steps for this disaster.
     * Shared implementation across all disaster types — subclasses inherit this.
     */
    public void viewEmergencyRoute() {
        System.out.println("\n🚨 EMERGENCY EVACUATION ROUTE — " + disasterType.toUpperCase());
        System.out.println("=".repeat(50));
        for (int i = 0; i < evacuationSteps.length; i++) {
            System.out.println("  Step " + (i + 1) + ": " + evacuationSteps[i]);
        }
        System.out.println("=".repeat(50));
        System.out.println("Stay calm. Follow official instructions. Your safety first.\n");
    }

    // ─── AlertSystem Implementation ───────────────────────────────────────────

    /**
     * Triggers an emergency alert message on-screen.
     * Shared base implementation — child classes may override for custom alerts.
     *
     * @param message The warning message to display.
     */
    @Override
    public void triggerAlert(String message) {
        System.out.println("\n⚠️  [ALERT — " + disasterType.toUpperCase() + "] " + message);
    }

    // broadcastSafetyTips() is left abstract via the interface —
    // each child class will provide its own disaster-specific tips.

    // ─── Getters ──────────────────────────────────────────────────────────────

    public String getDisasterType()          { return disasterType; }
    public String getClimateLinkExplanation(){ return climateLinkExplanation; }
    public String[] getEvacuationSteps()     { return evacuationSteps; }
}
