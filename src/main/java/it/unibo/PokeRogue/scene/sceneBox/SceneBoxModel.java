package it.unibo.PokeRogue.scene.sceneBox;

import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactory;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.savingSystem.SavingSystemImpl;
import lombok.Getter;
import lombok.AccessLevel;

public final class SceneBoxModel {

    private final SavingSystem savingSystemInstance;
    private final PokemonFactory pokemonFactoryInstance;

    @Getter(AccessLevel.PACKAGE)
    private final List<List<Pokemon>> boxes;

    public SceneBoxModel() throws InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException {
        this.savingSystemInstance = SavingSystemImpl.getInstance(SavingSystemImpl.class);
        this.pokemonFactoryInstance = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);

        this.boxes = new ArrayList<>();

    }

    /**
     * Sets up the save system by either loading saved Pokémon data from a specified
     * path
     * or saving a set of default Pokémon if no save path is provided.
     * 
     * @param savePath The path to the save file. If empty, default Pokémon are
     *                 saved.
     */
    protected void setUpSave(final String savePath) throws InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException,
            IOException {
        if ("".equals(savePath)) {
            this.savingSystemInstance.savePokemon(pokemonFactoryInstance.pokemonFromName("bulbasaur"));
            this.savingSystemInstance.savePokemon(pokemonFactoryInstance.pokemonFromName("charmander"));
            this.savingSystemInstance.savePokemon(pokemonFactoryInstance.pokemonFromName("squirtle"));
            this.addPokemonToBox(pokemonFactoryInstance.pokemonFromName("bulbasaur"));
            this.addPokemonToBox(pokemonFactoryInstance.pokemonFromName("charmander"));
            this.addPokemonToBox(pokemonFactoryInstance.pokemonFromName("squirtle"));

        } else {
            this.savingSystemInstance.loadData(Paths.get("src", "saves", savePath).toString());

            for (final var box : this.savingSystemInstance.getSavedPokemon()) {
                for (final String pokemonName : box) {
                    this.addPokemonToBox(pokemonFactoryInstance.pokemonFromName(pokemonName));

                }

            }
        }

    }

    /**
     * Adds a Pokémon to the current box. If the box is full (81 Pokémon), a new box
     * is created.
     * 
     * @param pokemon The Pokémon to add to the box.
     */
    private void addPokemonToBox(final Pokemon pokemon) {

        if (this.boxes.isEmpty()) {
            this.boxes.add(new ArrayList<>());
        }

        if (this.boxes.get(this.boxes.size() - 1).size() == 81) {
            this.boxes.add(new ArrayList<>());

        }

        this.boxes.get(this.boxes.size() - 1).add(pokemon);

    }

}
