package it.unibo.PokeRogue;

import java.util.HashMap;
import java.util.Map;

import java.awt.Color;
import java.awt.Font;

public class Main {
	public static void main(String[] args) {

		/*
		NB : This code will erase and rewrite all pokemon in the range
		and moves of those pokemon use at your own risk
		(It will delete manually added data)
		DataExtractor dataExtractor = new DataExtractorImpl("pokemon_data");
		dataExtractor.extractPokemons(1,151);
		dataExtractor.extractMoves();
		*/

		PokemonFactory pokemonFactory = new PokemonFactoryImpl();
		pokemonFactory.init();
		Pokemon testPokemon = pokemonFactory.randomPokemon(10);
	}
}
