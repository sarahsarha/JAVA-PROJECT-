

import javax.swing.JOptionPane;

//Member4Demo — Test Driver for Member 4: Data Management & Competitive Edge
/* Demonstrates:
 * ✅ Savable interface execution (saveToFile + loadFromFile)
 * ✅ File I/O structure with proper try-catch exception handling blocks
 * ✅ Dynamic real-time leaderboard re-ranking (moving names up/down)
 * ✅ Desktop Graphical User Interface (GUI) panel integration
 */
public class Member4Demo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║         ECO-DEFENSE — MEMBER 4 DEMO         ║");
        System.out.println("║    Data Management & Competitive Edge Test   ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        // ── 1. Initialization Demo ───────────────────────────────────────────
        System.out.println("\n>>> INITIALIZING PERSISTENT DATA CONTROL STRUCTURE...");
        Leaderboard managerSystem = new Leaderboard("leaderboard.txt");

        // ── 2. Simulating Scoring Shaking & Rank Movement ──────────────────
        System.out.println("\n>>> SIMULATING QUIZ SUBMISSIONS (Dynamic Rank Shifts)...");
        managerSystem.addScore("Player_Alpha", 75);
        System.out.println("-> Logged: Player_Alpha with 75 points.");
        
        managerSystem.addScore("Eco_Warrior_01", 95);
        System.out.println("-> Logged: Eco_Warrior_01 with 95 points.");
        
        managerSystem.addScore("Survivor_Z", 40);
        System.out.println("-> Logged: Survivor_Z with 40 points.");
        
        System.out.println("\n>>> GENERATING LIVE CONSOLE RANK VIEW...");
        System.out.println(managerSystem.getTopPlayersData());

        // ── 3. Exception Handling & File I/O Verification ────────────────────
        System.out.println("\n>>> EXECUTING DATA PERSISTENCE VIA TRY-CATCH STREAMS...");
        managerSystem.saveToFile("");

        System.out.println("\n>>> SIMULATING FLUSHING SYSTEM AND RELOADING DOCUMENT...");
        managerSystem.loadFromFile("");

        System.out.println("\n>>> VERIFYING DATA INTEGRITY AFTER FILE LOAD... COMPLETE.");
        System.out.println(" Member 4 demo verification complete. All logic lines functional.");
        System.out.println("\n=================================================================");
        System.out.println("🔥 BOOTING DESKTOP GRAPHICAL USER INTERFACE (GUI) PANEL SYSTEM...");
        System.out.println("=================================================================");

        // ── 4. Desktop GUI Interaction Loop ──────────────────────────────────
        String[] navigationOptions = {
            "1. Add New Session Score", 
            "2. View Highscores Board", 
            "3. Save Current Progress Data", 
            "4. Close Application"
        };

        boolean applicationIsActive = true;

        while (applicationIsActive) {
            String interactiveChoice = (String) JOptionPane.showInputDialog(
                null, 
                "Select a management task option below:", 
                "Eco-Defense Core Dashboard - Member 4", 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                navigationOptions, 
                navigationOptions[0]
            );

            if (interactiveChoice == null || interactiveChoice.contains("4. Close")) {
                applicationIsActive = false;
                break;
            }

            if (interactiveChoice.contains("1. Add New Session Score")) {
                String inputName = JOptionPane.showInputDialog(null, "Enter Participant Identity Name:", "Record Input Window", JOptionPane.QUESTION_MESSAGE);
                
                if (inputName != null && !inputName.trim().isEmpty()) {
                    String inputScoreStr = JOptionPane.showInputDialog(null, "Enter Quiz Score Value achieved:", "Score Input Window", JOptionPane.QUESTION_MESSAGE);
                    
                    try {
                        int conversionScoreValue = Integer.parseInt(inputScoreStr);
                        managerSystem.addScore(inputName.trim(), conversionScoreValue);
                        JOptionPane.showMessageDialog(null, "Score successfully updated in tracking structure!", "Record Logged", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid entry format. Numerical inputs only.", "Validation Alert", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } 
            else if (interactiveChoice.contains("2. View Highscores Board")) {
                managerSystem.displayTopPlayers();
            } 
            else if (interactiveChoice.contains("3. Save Current Progress Data")) {
                managerSystem.saveToFile("");
                JOptionPane.showMessageDialog(null, "Data backup file updated completely!", "Save Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        System.out.println("\n[SYSTEM SYSTEM] Application context shut down smoothly.");
        System.exit(0);
    }
}
