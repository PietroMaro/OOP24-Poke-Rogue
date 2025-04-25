package it.unibo.PokeRogue.utilities;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class JsonReaderImpl implements JsonReader {
	@Override
	public String readJsonStringFromFile(final String filePath) {
		final File file = new File(filePath);
		try {
			if (!file.exists()) {
				final boolean fileCreated = file.createNewFile();
				final FileWriter writer = new FileWriter(file);
				writer.write("[]");
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to create file " + filePath);
			System.exit(1);
		}

		final StringBuilder jsonContent = new StringBuilder();
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = reader.readLine()) != null) {
				jsonContent.append(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed convert " + filePath + " to json");
			System.exit(1);
		}
		return jsonContent.toString();
	}

	@Override
	public void dumpJsonToFile(final String filePath, final String destionationFolder, final Object jsonFile) {
		final File folder = new File(destionationFolder);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		dumpJsonToFile(filePath, jsonFile);
	}

	@Override
	public void dumpJsonToFile(final String filePath, final Object jsonFile) {
		if (!(jsonFile instanceof JSONArray) && !(jsonFile instanceof JSONObject)) {
			throw new IllegalArgumentException(
					"Object dumped must be JSONArray or JSONObject, but was: " + jsonFile.getClass().getName());
		}
		try {
			final BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			if (jsonFile instanceof JSONArray) {
				writer.write(((JSONArray) jsonFile).toString(4));
			} else if (jsonFile instanceof JSONObject) {
				writer.write(((JSONObject) jsonFile).toString(4));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("An error occurred while dumping " + filePath);
			System.exit(1);
		}
	}

	@Override
	public JSONObject readJsonObject(final String filePath) {
		return new JSONObject(readJsonStringFromFile(filePath));
	}

	@Override
	public JSONArray readJsonArray(final String filePath) {
		return new JSONArray(readJsonStringFromFile(filePath));
	}
}
