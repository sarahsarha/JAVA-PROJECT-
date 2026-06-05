/**
 * Class    : Leaderboard
 * Creator  : Najla
 * Tester   : Annie
 * Description : Manages survival item counts, tracks resource readiness, and validates required ki items before simulation. 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;

/**
 * Leaderboard Class - Handles player progression, persistent data storage, 
 * score accumulation, and gamified climate badge tier allocations.
 */
public class Leaderboard implements Savable {
    private ArrayList<String> playerScores;
    private String filePath;

    // Constructor for Leaderboard setup.
    public Leaderboard(String filePath) {
        this.playerScores = new ArrayList<>();
        this.filePath = filePath;
    }

    /**
     * Maps accumulated total scores to specific cute, climate-themed badges.
     * Aligned perfectly with the Eco-Defense Learning Module design.
     */
    private String determineBadge(int score) {
        if (score >= 1200) return "💎 EcoMaster Elite";
        if (score >= 1000) return "🏆 Climate Legend";
        if (score >= 850)  return "🔥 Fire Defender";
        if (score >= 700)  return "🌊 Flood Tamer";
        if (score >= 500)  return "⚡ Storm Chaser";
        if (score >= 300)  return "🌿 Eco Warrior";
        return "🌱 Climate Novice";
    }

    /**
     * Adds or accumulates a player's score. 
     * If the player already exists, new points are added to their historical total.
     */
    public void addScore(String playerName, int score) {
        int existingIndex = -1;
        int currentTotalScore = score;
        String cleanedName = playerName.trim();

        // 1. Scan the database cache to check if this player has played before
        for (int i = 0; i < playerScores.size(); i++) {
            String[] data = playerScores.get(i).split(",");
            if (data[0].equalsIgnoreCase(cleanedName)) {
                existingIndex = i;
                // Accumulate new score points onto their historical total
                currentTotalScore = Integer.parseInt(data[1]) + score;
                break;
            }
        }

        // 2. Compute the correct badge milestone based on the aggregated score total
        String badge = determineBadge(currentTotalScore);
        double percentage = ((double) currentTotalScore / 200) * 100;
        String updatedRecord = cleanedName + "," 
                + currentTotalScore + "," 
                + String.format("%.0f", percentage) + "%," 
                + badge;


        // 3. Update existing entry or append a completely fresh player row
        if (existingIndex != -1) {
            playerScores.set(existingIndex, updatedRecord);
            JOptionPane.showMessageDialog(null, 
                "Welcome back, " + cleanedName + "! 🔥\n" +
                "Your new session score has been accumulated!\n\n" +
                "📈 Total Progress Score: " + currentTotalScore + " Points\n" +
                "🏅 Current Badge Tier: " + badge, 
                "Progress Updated! ⚡", JOptionPane.INFORMATION_MESSAGE);
        } else {
            playerScores.add(updatedRecord);
            JOptionPane.showMessageDialog(null, 
                "✨ Achievement Unlocked! ✨\n\n" +
                cleanedName + ", you are officially a:\n" + badge + "!\n\n" +
                "Keep playing to accumulate points for higher tiers! 💚", 
                "Badge Unlocked! 🎉", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Sorts data by total score and generates a formatted leaderboard visualization layout.
    public String getTopPlayersData() {
        if (playerScores.isEmpty()) {
            return "No records found yet. Play a game session first!";
        }

        ArrayList<String> sortedList = new ArrayList<>(playerScores);
        Collections.sort(sortedList, new Comparator<String>() {
            @Override
            public int compare(String record1, String record2) {
                int score1 = Integer.parseInt(record1.split(",")[1]);
                int score2 = Integer.parseInt(record2.split(",")[1]);
                return Integer.compare(score2, score1); 
            }
        });

        StringBuilder builder = new StringBuilder();
        builder.append("====================================================================\n");
        builder.append("         🏆 GLOBAL ECO LEADERBOARD W/ BADGES 🏆         \n");
        builder.append("====================================================================\n");
        builder.append(String.format(
            "%-6s %-12s %-8s %-8s %-20s\n",
            "Rank", "Name", "Score", "%", "Badge"
        ));

        builder.append("--------------------------------------------------------------------\n");

        int rank = 1;
        for (String record : sortedList) {
            String[] data = record.split(",");
            // Backward compatibility fallback safety check
            String percent = (data.length > 2) ? data[2] : "0%";
            String badge = (data.length > 3) ? data[3] : "NO BADGE";
            builder.append(String.format(
                "#%-5d %-12s %-8s %-8s %-20s\n",
                rank, data[0], data[1], percent, badge
            ));

            rank++;
            if (rank > 10) break;
        }
        builder.append("====================================================================");
        return builder.toString();
    }

    // Displays the current leaderboard table within a scrollable Swing window pane.
    public void displayTopPlayers() {
        String dataTableText = getTopPlayersData();
        
        // Widened column constraints to elegantly handle long custom badge names
        JTextArea textArea = new JTextArea(dataTableText, 15, 65);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JOptionPane.showMessageDialog(
            null, 
            scrollPane, 
            "Eco-Defense Leaderboard Board", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void saveToFile(String filename) {
        if (filename != null && !filename.trim().isEmpty()) {
            this.filePath = filename;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath))) {
            for (String scoreRecord : playerScores) {
                writer.write(scoreRecord);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Data successfully backed up to: " + this.filePath, "Save Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing data storage structure: " + e.getMessage(), "IO Error File Save", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void loadFromFile(String filename) {
        if (filename != null && !filename.trim().isEmpty()) {
            this.filePath = filename;
        }

        playerScores.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] data = line.split(",");
                    // Gracefully handle older 2-column database save files via real-time badge updates
                    if (data.length == 2) {
                        int score = Integer.parseInt(data[1]);
                        line += ",0%," + determineBadge(score);
                    } 
                    else if (data.length == 3) {
                        int score = Integer.parseInt(data[1]);
                        line += "," + determineBadge(score);
                    }

                    playerScores.add(line);
                }
            }
            JOptionPane.showMessageDialog(null, "Historical data reloaded successfully!", "Load Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No existing historical database text document discovered. Starting a clean dashboard session context instead.", "File Setup Context", JOptionPane.WARNING_MESSAGE);
        }
    }

    // =========================================================================
    // SWING GRAPHICAL MAIN MENU RUNNER
    // =========================================================================
    public static void main(String[] args) {
        Leaderboard managerSystem = new Leaderboard("leaderboard.txt");

        // Attempt automatic background loading of existing records at application startup
        managerSystem.loadFromFile("");

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
                "Eco-Defense Panel - Member 4", 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                navigationOptions, 
                navigationOptions[0]
            );

            // Handle user closing the dialogue windows directly
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
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid entry format. Numeric numbers only please.", "Parsing Validation Alert", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } 
            else if (interactiveChoice.contains("2. View Highscores Board")) {
                managerSystem.displayTopPlayers();
            } 
            else if (interactiveChoice.contains("3. Save Current Progress Data")) {
                managerSystem.saveToFile("");
            }
        }

        System.exit(0);
    }
}