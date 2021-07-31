package persistence;

import org.json.JSONObject;

/**
 * Writable interface from Json Serialization Demo Project (Paul Carter, UBC CPSC 210).
 */

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
