package it.unibo.PokeRogue.utilities;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;

public interface JsonReader{
	/**
	* Dumps a JSON file into memory creating the destionationFolder if it doesn't exist
	* @param filePath 
	* @param destionationFolder 
	* @param jsonFile
	*/
	void dumpJsonToFile(final String filePath, final String destionationFolder, final Object jsonFile) throws IOException;
	/**
	* Dumps a JSON file into memory
	* @param filePath 
	* @param jsonFile
	*/
	void dumpJsonToFile(final String filePath, final Object jsonFile) throws IOException;
	/**
	* reads a JSON from memory
	* @param filePath 
	* @return the JSON string
	*/
	String readJsonStringFromFile(final String filePath) throws IOException ;
	/**
	* reads a JSON from memory
	* @param filePath 
	* @return the JSON Object
	*/
	JSONObject readJsonObject(final String filePath) throws IOException ;
	/**
	* reads a JSON from memory
	* @param filePath 
	* @return the JSON array
	*/
	JSONArray readJsonArray(final String filePath) throws IOException ;
}
