/**
 * Class    : EmergencyKit
 * Creator  : Raimi
 * Tester   : Najla
 * Description : Manages survival item counts, tracks resource readiness, and validates required ki items before simulation. 
 */
public interface GameLogic {

    // Starts a countdown timer with given seconds
    void startSurvivalTimer(int seconds);

    // Awards or skips points based on whether answer was correct
    void calculatePoints(boolean isCorrect);
}