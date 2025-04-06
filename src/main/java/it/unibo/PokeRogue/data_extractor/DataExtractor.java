package it.unibo.PokeRogue;

public interface DataExtractor{
	void extractPokemon(int apiIndex);
	void extractPokemons(int startIndex,int endIndex);
	void extractMove(String moveName);
	void extractMoves();
	void setDestinationFolder(String newPath);
	String getDestinationFolder();
}
