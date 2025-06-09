package it.unibo.pokerogue.model.impl;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.pokerogue.controller.impl.InputHandlerImpl;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Implementation of the {@link SavingSystem} interface that handles
 * saving and loading Pokémon data using JSON files. Pokémon are stored
 * as a list of names and grouped into boxes of fixed size.
 * 
 * @author Egor Tverdohleb
 */
public class SavingSystemImpl implements SavingSystem {
    private static final int BOX_SIZE = 81;
    private final JsonReader jsonReader = new JsonReaderImpl();
    private JSONArray savedPokemon = new JSONArray();
    private static final Logger LOGGER = LoggerFactory.getLogger(InputHandlerImpl.class);

    @Override
    public void savePokemon(final Pokemon pokemon) {
        for (int pokemonIndex = 0; pokemonIndex < this.savedPokemon.length(); pokemonIndex += 1) {
            if (pokemon.getName().equals(this.savedPokemon.getString(pokemonIndex))) {
                return;
            }
        }
        savedPokemon.put(pokemon.getName());
    }

    @Override
    public void loadData(final String path) throws IOException {
        this.savedPokemon = jsonReader.readJsonArray(path);
    }

    @Override
    public void saveData(final String path, final String fileName) throws IOException {
        final File file = new File(path, fileName);
        if (file.exists() || file.createNewFile()) {
            jsonReader.dumpJsonToFile(file.getAbsolutePath(), this.savedPokemon);
        }
    }

    @Override
    public List<List<String>> getSavedPokemon() {
        final List<List<String>> result = new ArrayList<>();
        final List<String> newBox = new ArrayList<>();
        for (int pokemonIndex = 0; pokemonIndex < this.savedPokemon.length(); pokemonIndex += 1) {
            if (newBox.size() >= BOX_SIZE) {
                result.add(newBox);
                newBox.clear();
            }
            newBox.add(this.savedPokemon.getString(pokemonIndex));
        }
        if (!newBox.isEmpty()) {
            result.add(newBox);
        }
        return result;
    }

    @Override
    public List<String> getSaveFilesName(final String dirPath) {
        final List<String> jsonFiles = new ArrayList<>();
        try {
            final URL dirURL = getClass().getClassLoader().getResource(dirPath);
            if (dirURL != null) {
                if (dirURL.getProtocol().equals("file")) {
                    final File folder = new File(dirURL.toURI());
                    if (folder.exists() && folder.isDirectory()) {
                        final File[] files = folder
                                .listFiles((dir, name) -> name.toLowerCase(Locale.ROOT).endsWith(".json"));
                        if (files != null) {
                            for (final File file : files) {
                                jsonFiles.add(file.getName());
                            }
                        }
                    }
                } else if (dirURL.getProtocol().equals("jar")) {
                    final String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));

                    try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"))) {
                        final Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            final JarEntry entry = entries.nextElement();
                            final String name = entry.getName();
                            if (name.startsWith(dirPath + "/") && name.endsWith(".json")) {
                                jsonFiles.add(name.substring(name.lastIndexOf("/") + 1));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while reading save files: ", e);
        }

        return jsonFiles;
    }

    @Override
    public int howManyPokemonInSave(final String path) throws IOException {
        final JSONArray boxPokemons = jsonReader.readJsonArray(path);
        return boxPokemons.length();
    }
}
