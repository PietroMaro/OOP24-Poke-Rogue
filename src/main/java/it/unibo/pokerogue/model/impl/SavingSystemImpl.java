package it.unibo.pokerogue.model.impl;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

    public SavingSystemImpl() throws IOException {
        final String currentDir = System.getProperty("user.dir");
        final File appDir = new File(currentDir, "appdata");

        if (!appDir.exists() && !appDir.mkdirs()) {
            throw new IOException("Impossibile creare la cartella: " + appDir.getAbsolutePath());
        }

        final String resourcePath = "saves";
        final ClassLoader classLoader = getClass().getClassLoader();

        try {
            final URL resource = classLoader.getResource(resourcePath);

           

            if (resource.getProtocol().equals("jar")) {
                final String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
                try (JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, "UTF-8"))) {
                    final Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        final JarEntry entry = entries.nextElement();
                        final String name = entry.getName();
                        if (name.startsWith(resourcePath + "/") && name.endsWith(".json")) {
                            final InputStream is = classLoader.getResourceAsStream(name);
                            final File outFile = new File(appDir, name.substring(name.lastIndexOf("/") + 1));
                            if (is != null && !outFile.exists()) {
                                Files.copy(is, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            }
                        }
                    }
                }
            } else if (resource.getProtocol().equals("file")) {
                final File folder = new File(resource.toURI());
                if (folder.exists() && folder.isDirectory()) {
                    for (File file : folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"))) {
                        final File outFile = new File(appDir, file.getName());
                        Files.copy(file.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }

        } catch (Exception e) {
            throw new IOException("Errore durante l'estrazione dei file di salvataggio", e);
        }

    }

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
    public void loadData(final String fileName) throws IOException {
        final String currentDir = System.getProperty("user.dir");
        final File appDir = new File(currentDir, "appdata");
        final File file = new File(appDir, fileName);

        if (!file.exists()) {
            throw new IOException("Il file " + file.getAbsolutePath() + " non esiste.");
        }

        this.savedPokemon = jsonReader.readJsonArray(file.getAbsolutePath());
    }

    @Override
    public void saveData(final String fileName) throws IOException {
        final String currentDir = System.getProperty("user.dir");
        final File appDir = new File(currentDir, "appdata");

        if (!appDir.exists()) {
            if (!appDir.mkdirs()) {
                throw new IOException("Impossibile creare la cartella: " + appDir.getAbsolutePath());
            }
        }

        final File file = new File(appDir, fileName);

        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("Impossibile creare il file: " + file.getAbsolutePath());
            }
        }

        jsonReader.dumpJsonToFile(file.getAbsolutePath(), this.savedPokemon);
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
    public List<String> getSaveFilesName() {
        final List<String> jsonFiles = new ArrayList<>();

        try {
            final String userDir = System.getProperty("user.dir");
            final File appdataFolder = new File(userDir, "appdata");

            if (appdataFolder.exists() && appdataFolder.isDirectory()) {
                final File[] files = appdataFolder
                        .listFiles((dir, name) -> name.toLowerCase(Locale.ROOT).endsWith(".json"));
                if (files != null) {
                    for (final File file : files) {
                        if (!jsonFiles.contains(file.getName())) {
                            jsonFiles.add(file.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while reading save files from appdata folder: ", e);
        }

        return jsonFiles;
    }

    @Override
    public int howManyPokemonInSave(final String fileName) throws IOException {
        final String currentDir = System.getProperty("user.dir");
        final File appDir = new File(currentDir, "appdata");

        final File file = new File(appDir, fileName);

        final JSONArray boxPokemons = jsonReader.readJsonArray(file.getAbsolutePath());
        return boxPokemons.length();
    }
}
