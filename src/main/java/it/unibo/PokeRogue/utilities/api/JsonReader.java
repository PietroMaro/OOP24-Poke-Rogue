package it.unibo.pokerogue.utilities.api;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;

/**
 * This class provides functionality to read and parse JSON data
 * from various sources, converting it into usable Java objects.
 */
public interface JsonReader {
	
	/**
	 * Dumps a JSON file into memory creating the destionationFolder if it doesn't.
	 * exist
	 * 
	 * @param filePath
	 * @param destionationFolder
	 * @param jsonFile
	 */
	void dumpJsonToFile(String filePath, String destionationFolder, Object jsonFile) throws IOException;

	/**
	 * Dumps a JSON file into memory.
	 * 
	 * @param filePath
	 * @param jsonFile
	 */
	void dumpJsonToFile(String filePath, Object jsonFile) throws IOException;

	/**
	 * reads a JSON from memory.
	 * 
	 * @param filePath
	 * @return the JSON string
	 */
	String readJsonStringFromFile(String filePath) throws IOException;

	/**
	 * reads a JSON from memory.
	 * 
	 * @param filePath
	 * @return the JSON Object
	 */
	JSONObject readJsonObject(String filePath) throws IOException;

	/**
	 * reads a JSON from memory.
	 * 
	 * @param filePath
	 * @return the JSON array
	 */
	JSONArray readJsonArray(String filePath) throws IOException;
}
