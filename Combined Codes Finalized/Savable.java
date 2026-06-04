

//Interface to handle data persistence for the application.

public interface Savable {
    void saveToFile(String filename);
    void loadFromFile(String filename);
}
