package it.unibo.PokeRogue.utilities;

import org.json.JSONObject;
import org.json.JSONArray;

public interface JsonReader{
	void dumpJsonToFile(final String filePath, final String destionationFolder, final Object jsonFile);
	void dumpJsonToFile(final String filePath, final Object jsonFile);
	String readJsonStringFromFile(final String filePath);
	JSONObject readJsonObject(final String filePath);
	JSONArray readJsonArray(final String filePath);
}
