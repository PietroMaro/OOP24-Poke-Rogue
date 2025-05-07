package it.unibo.PokeRogue.utilities;

import org.json.JSONObject;
import org.json.JSONArray;

public interface JsonReader{
	/**
	* Dumps a JSON file into memory creating the destionationFolder if it doesn't exist
	* @param filePath 
	* @param destionationFolder 
	* @param jsonFile
	*/
	void dumpJsonToFile(final String filePath, final String destionationFolder, final Object jsonFile);
	/**
	* Dumps a JSON file into memory
	* @param filePath 
	* @param jsonFile
	*/
	void dumpJsonToFile(final String filePath, final Object jsonFile);
	/**
	* reads a JSON from memory
	* @param filePath 
	* @return the JSON string
	*/
	String readJsonStringFromFile(final String filePath);
	/**
	* reads a JSON from memory
	* @param filePath 
	* @return the JSON Object
	*/
	JSONObject readJsonObject(final String filePath);
	/**
	* reads a JSON from memory
	* @param filePath 
	* @return the JSON array
	*/
	JSONArray readJsonArray(final String filePath);
}
