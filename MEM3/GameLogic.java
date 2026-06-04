package ecodefense;
/**
 * Interface  : GameLogic
 * Creator    : Member 3
 * Tester     : Member 3
 * Description: Defines the core game logic contract for the survival quiz simulation.
 */

public interface GameLogic {

    // Starts a countdown timer with given seconds
    void startSurvivalTimer(int seconds);

    // Awards or skips points based on whether answer was correct
    void calculatePoints(boolean isCorrect);
}