package it.unibo.pokerogue.model.impl.scene;

import java.nio.file.Paths;
import java.util.List;

import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.impl.pokemon.PokemonFactory;

import java.util.ArrayList;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * The {@code SceneBoxModel} class represents the data model for handling
 * Pokémon storage boxes in a scene. It integrates with a saving system to
 * persist Pokémon data and uses a factory to create Pokémon instances.
 * Each box can hold up to 81 Pokémon. If a box is full, a new one is created.
 * Pokémon can be loaded from a save file or initialized with default Pokémon.
 */
public final class SceneBoxLoad {

    private static final int LENGTH_OF_POKEBOX = 81;

    private final SavingSystem savingSystemInstance;

    private final List<List<Pokemon>> boxes;

    /**
     * Constructs a new {@code SceneBoxModel} and initializes the saving system
     * and Pokémon factory instances.
	 *
	 * @param savingSystem the instance of the saving sytem used
     */
    public SceneBoxLoad(final SavingSystem savingSystem) throws InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException {
        this.savingSystemInstance = savingSystem;

        this.boxes = new ArrayList<>();

    }

    /**
     * Initializes the save system by loading Pokémon data.
     * 
     * @param savePath the path to the save file; if empty, initializes default
     *                 starters
     */
    public void setUpSave(final String savePath) throws InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException,
            IOException {
        if ("".equals(savePath)) {
            this.savingSystemInstance.savePokemon(PokemonFactory.pokemonFromName("bulbasaur"));
            this.savingSystemInstance.savePokemon(PokemonFactory.pokemonFromName("charmander"));
            this.savingSystemInstance.savePokemon(PokemonFactory.pokemonFromName("squirtle"));
            this.addPokemonToBox(PokemonFactory.pokemonFromName("bulbasaur"));
            this.addPokemonToBox(PokemonFactory.pokemonFromName("charmander"));
            this.addPokemonToBox(PokemonFactory.pokemonFromName("squirtle"));

        } else {
            this.savingSystemInstance.loadData(Paths.get("src", "main", "resources", "saves", savePath).toString());

            for (final var box : this.savingSystemInstance.getSavedPokemon()) {
                for (final String pokemonName : box) {
                    this.addPokemonToBox(PokemonFactory.pokemonFromName(pokemonName));

                }

            }
        }

    }

    /**
     * Returns a defensive copy of the current list of Pokémon boxes.
     *
     * 
     * @return a new list of Pokémon boxes, each box itself a new list of
     *         Pokemon
     */
    public List<List<Pokemon>> getBoxes() {

        return new ArrayList<>(boxes);
    }

    private void addPokemonToBox(final Pokemon pokemon) {

        if (this.boxes.isEmpty()) {
            this.boxes.add(new ArrayList<>());
        }

        if (this.boxes.get(this.boxes.size() - 1).size() == LENGTH_OF_POKEBOX) {
            this.boxes.add(new ArrayList<>());

        }

        this.boxes.get(this.boxes.size() - 1).add(pokemon);

    }

}
