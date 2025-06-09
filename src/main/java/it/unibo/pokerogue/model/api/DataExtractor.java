package it.unibo.pokerogue.model.api;

import java.io.IOException;

/**
 * Interface for extracting Pokémon data from the PokéAPI
 * (https://pokeapi.co/api/v2/).
 * Supports extracting Pokémon, moves, and writing results to JSON files.
 * 
 * @author Tverdohleb Egor
 */
public interface DataExtractor {

    /**
     * Extracts data for a single Pokémon from the PokéAPI.
     *
     * @param apiIndex the index (Pokédex number) of the Pokémon to extract
     * @throws IOException          if file writing or network access fails
     * @throws InterruptedException if the HTTP request is interrupted
     */
    void extractPokemon(int apiIndex) throws IOException, InterruptedException;

    /**
     * Extracts data for a range of Pokémon from the PokéAPI.
     *
     * @param startIndex the starting Pokédex number (inclusive)
     * @param endIndex   the ending Pokédex number (inclusive)
     * @throws IOException          if file writing or network access fails
     * @throws InterruptedException if the HTTP request is interrupted
     */
    void extractPokemons(int startIndex, int endIndex) throws IOException, InterruptedException;

    /**
     * Extracts data for a specific move from the PokéAPI.
     *
     * @param moveName the name of the move to extract
     * @throws IOException          if file writing or network access fails
     * @throws InterruptedException if the HTTP request is interrupted
     */
    void extractMove(String moveName) throws IOException, InterruptedException;

    /**
     * Extracts data for all moves currently present in the moves list,
     * typically populated while extracting Pokémon.
     *
     * @throws IOException          if file writing or network access fails
     * @throws InterruptedException if the HTTP request is interrupted
     */
    void extractMoves() throws IOException, InterruptedException;

    /**
     * Sets the folder where extracted data will be saved.
     *
     * @param newPath the new destination folder path
     */
    void setDestinationFolder(String newPath);

    /**
     * Returns the current destination folder path.
     *
     * @return the destination folder
     */
    String getDestinationFolder();
}
