package it.unibo.PokeRogue.dataExtractor;
import java.io.IOException;

public interface DataExtractor {
	/**
	 * extract a single pokemon from the api https://pokeapi.co/api/v2/
	 * 
	 * @param apiIndex the index of the Pokémon in the API to extract
	 */
	void extractPokemon(int apiIndex) throws IOException;

	/**
	 * extracts data for a range pokemon from the api https://pokeapi.co/api/v2/
	 * 
	 * @param startIndex
	 * @param endIndex
	 */
	void extractPokemons(int startIndex, int endIndex) throws IOException;

	/**
	 * extract a move from the api https://pokeapi.co/api/v2/
	 * 
	 * @param moveName
	 */
	void extractMove(String moveName) throws IOException;

	/**
	 * extract all the moves in the all moves list generated extracting the moves
	 * of all pokemon extracted
	 */
	void extractMoves() throws IOException;

	/**
	 * simple setter
	 * 
	 * @param newPath
	 */
	void setDestinationFolder(String newPath);

	/**
	 * simple getter
	 * 
	 * @return the destination folder path
	 */
	String getDestinationFolder();
}
