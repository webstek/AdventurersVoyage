package persistence;

import model.GameState;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Structure based on the Json Serialization Demo Project (Paul Carter, UBC CPSC 210)
 * Class enabling the writing of JSON objects to json files.
 */

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String fileToWriteTo;

    // EFFECTS: constructs writer to write to the desired file location
    public JsonWriter(String fileToWriteTo) {
        this.fileToWriteTo = fileToWriteTo;
    }

    // MODIFIES: this
    // EFFECTS: writes the Json representation of the AdventurerVoyageApp class, the gameState to fileToWriteTo
    public void write(GameState gameState) {
        JSONObject json = gameState.toJson();
        writer.print((json.toString(TAB)));
    }

    // MODIFIES: this
    // EFFECTS: instantiates a writer; throws FileNotFoundException if the fileToWriteTo cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(fileToWriteTo));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }
}
