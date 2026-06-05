//Interface to handle data persistence

public interface Savable {
    void saveToFile(String filename);
    void loadFromFile(String filename);
}
