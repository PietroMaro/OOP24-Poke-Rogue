package it.unibo.pokerogue.utilities.impl;

import org.json.JSONObject;

import it.unibo.pokerogue.utilities.api.JsonReader;

import org.json.JSONArray;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JsonReaderImpl implements JsonReader {
	@Override
	public String readJsonStringFromFile(final String filePath) throws IOException {
		final Path path = Path.of(filePath);
    	if (Files.notExists(path)) {
    	    Files.createDirectories(path.getParent());
    	    Files.writeString(path, "[]", StandardCharsets.UTF_8,StandardOpenOption.CREATE_NEW);
		}
        return Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
	}

	@Override
	public void dumpJsonToFile(final String filePath, final String destionationFolder, final Object jsonFile) throws IOException {
		final Path folderPath = Path.of(destionationFolder);
    	if (Files.notExists(folderPath)) {
    	    Files.createDirectories(folderPath);
    	}
    	dumpJsonToFile(filePath, jsonFile);
	}

	@Override
	public void dumpJsonToFile(final String filePath, final Object jsonFile) throws IOException {
		if (!(jsonFile instanceof JSONArray) && !(jsonFile instanceof JSONObject)) {
        	throw new IllegalArgumentException("Object dumped must be JSONArray or JSONObject, but was: " + jsonFile.getClass().getName());
    	}

    	final BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath), StandardCharsets.UTF_8);
    	final String prettyJson = (jsonFile instanceof JSONArray)
    	    ? ((JSONArray) jsonFile).toString(4)
    	    : ((JSONObject) jsonFile).toString(4);
    	writer.write(prettyJson);
	}

	@Override
	public JSONObject readJsonObject(final String filePath) throws IOException {
		return new JSONObject(readJsonStringFromFile(filePath));
	}

	@Override
	public JSONArray readJsonArray(final String filePath) throws IOException {
		return new JSONArray(readJsonStringFromFile(filePath));
	}
}
