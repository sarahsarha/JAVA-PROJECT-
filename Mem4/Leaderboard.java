package leaderboard;

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

public class Leaderboard implements Savable {
    private ArrayList<String> playerScores;
    private String filePath;

    //Constructor for Leaderboard setup.
    public Leaderboard(String filePath) {
        this.playerScores = new ArrayList<>();
        this.filePath = filePath;
    }

    //Adds a player record to the tracking array list.
    public void addScore(String playerName, int score) {
        String record = playerName + "," + score;
        playerScores.add(record);
    }

    // Sorts data and generates a structured leaderboard view string.
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
        builder.append("==========================================\n");
        builder.append("         🏆 GLOBAL LEADERBOARD 🏆         \n");
        builder.append("==========================================\n");
        builder.append(String.format("%-6s %-22s %-10s\n", "Rank", "Player Name", "Score"));
        builder.append("------------------------------------------\n");

        int rank = 1;
        for (String record : sortedList) {
            String[] data = record.split(",");
            builder.append(String.format("#%-5d %-22s %-10s\n", rank, data[0], data[1]));
            rank++;
            if (rank > 10) break;
        }
        builder.append("==========================================");
        return builder.toString();
    }

    //Displays the current leaderboard table within a Swing graphical component window.
    public void displayTopPlayers() {
        String dataTableText = getTopPlayersData();
        
        // Use a JTextArea so the columns line up perfectly
        JTextArea textArea = new JTextArea(dataTableText, 15, 40);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JOptionPane.showMessageDialog(
            null, 
            scrollPane, 
            "Leaderboard Display Board", 
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

        // Attempt automatic background loading of existing records at startup
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
                "Data Management Panel - Member 4", 
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
                        JOptionPane.showMessageDialog(null, "Score successfully pinned onto simulation cache tracking structure!", "Record Logged", JOptionPane.INFORMATION_MESSAGE);
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
