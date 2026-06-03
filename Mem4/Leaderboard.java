package leaderboard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//Manages score persistence and displays competitive leaderboards.
//Fulfills Member 4 requirements, including File I/O with Exception Handling.

public class Leaderboard implements Savable {
    // Attributes
    private ArrayList<String> playerScores;
    private String filePath;

    //Constructor initializing the leaderboard.
    // @param filePath The default file path for saving/loading scores.

    public Leaderboard(String filePath) {
        this.playerScores = new ArrayList<>();
        this.filePath = filePath;
    }

    //Adds a new record to the score list.
    // @param playerName Name of the participant.
    // @param score Score achieved in the quiz/simulation.

    public void addScore(String playerName, int score) {
        // Storing as a formatted String "PlayerName,Score" for easy parsing later
        String record = playerName + "," + score;
        playerScores.add(record);
        System.out.println("Score successfully recorded for: " + playerName);
    }

    //Displays the top performing players sorted from highest to lowest score.
    public void displayTopPlayers() {
        if (playerScores.isEmpty()) {
            System.out.println("\n--- Leaderboard ---");
            System.out.println("No records found. Play a game to set a score!");
            return;
        }

        // Create a temporary copy to sort without breaking the original insertion order if needed
        ArrayList<String> sortedList = new ArrayList<>(playerScores);

        // Custom sort using a Comparator to sort descending based on the numerical score segment
        Collections.sort(sortedList, new Comparator<String>() {
            @Override
            public int compare(String record1, String record2) {
                int score1 = Integer.parseInt(record1.split(",")[1]);
                int score2 = Integer.parseInt(record2.split(",")[1]);
                return Integer.compare(score2, score1); // Descending order
            }
        });

        System.out.println("\n==================================");
        System.out.println("       🏆 GLOBAL LEADERBOARD 🏆    ");
        System.out.println("==================================");
        System.out.printf("%-6s %-18s %-10s\n", "Rank", "Player Name", "Score");
        System.out.println("----------------------------------");

        int rank = 1;
        for (String record : sortedList) {
            String[] data = record.split(",");
            String name = data[0];
            String score = data[1];
            
            System.out.printf("#%-5d %-18s %-10s\n", rank, name, score);
            rank++;
            
            // Optional: Cap the display at top 10 players for a cleaner UI view
            if (rank > 10) break;
        }
        System.out.println("==================================");
    }

    //Handles Java File I/O inside try-catch blocks to save data.
    //Fulfills Savable interface.
    @Override
    public void saveToFile(String filename) {
        // update class attribute path if a new filename is passed
        if (filename != null && !filename.trim().isEmpty()) {
            this.filePath = filename;
        }

        // Try-with-resources syntax automatically handles closing the writer
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath))) {
            for (String scoreRecord : playerScores) {
                writer.write(scoreRecord);
                writer.newLine();
            }
            System.out.println("Leaderboard data successfully saved to '" + this.filePath + "'.");
        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Failed to write leaderboard data to file.");
            System.err.println("Error details: " + e.getMessage());
        }
    }

    //Handles Java File I/O inside try-catch blocks to load data.
    //Fulfills Savable interface.
    @Override
    public void loadFromFile(String filename) {
        if (filename != null && !filename.trim().isEmpty()) {
            this.filePath = filename;
        }

        // Clear existing local list before loading historical entries
        playerScores.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Ensure text line is not completely blank before adding
                if (!line.trim().isEmpty()) {
                    playerScores.add(line);
                }
            }
            System.out.println("Leaderboard data successfully loaded from '" + this.filePath + "'.");
        } catch (IOException e) {
            System.err.println("WARNING: Target leaderboard file dynamic loading encountered an issue.");
            System.err.println("Reason: " + e.getMessage() + ". Creating a brand new file session context instead.");
        }
    }

    // Getters and Setters for integration safety
    public ArrayList<String> getPlayerScores() {
        return playerScores;
    }

    public String getFilePath() {
        return filePath;
    }
}
