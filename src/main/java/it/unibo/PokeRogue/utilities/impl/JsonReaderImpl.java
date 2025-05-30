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

/**
 * Final implementation of the {@link JsonReader} interface.
 * 
 * Provides methods to read JSON data from files.
 */
public final class JsonReaderImpl implements JsonReader {

	/**
	 * Reads the JSON file at the given path and returns its content as a string.
	 * If the file doesn't exist, it creates the necessary folders and a new file
	 * with an empty JSON array ("[]").
	 *
	 * @param filePath the path to the JSON file
	 * @return the content of the JSON file as a string
	 * @throws IOException if reading or creating the file fails
	 */
	@Override
	public String readJsonStringFromFile(final String filePath) throws IOException {
		final Path path = Path.of(filePath);
		if (Files.notExists(path)) {
			Files.createDirectories(path.getParent());
			Files.writeString(path, "[]", StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
		}
		return Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
	}

	@Override
	public void dumpJsonToFile(final String filePath, final String destionationFolder, final Object jsonFile)
			throws IOException {
		final Path folderPath = Path.of(destionationFolder);
		if (Files.notExists(folderPath)) {
			Files.createDirectories(folderPath);
		}
		dumpJsonToFile(filePath, jsonFile);
	}

	@Override
	public void dumpJsonToFile(final String filePath, final Object jsonFile) throws IOException {
		if (!(jsonFile instanceof JSONArray) && !(jsonFile instanceof JSONObject)) {
			throw new IllegalArgumentException(
					"Object dumped must be JSONArray or JSONObject, but was: "
							+ jsonFile.getClass().getName());
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
