package it.unibo.PokeRogue;

import java.util.HashMap;
import java.util.Map;

import java.awt.Color;
import java.awt.Font;

public class Main {
	public static void main(String[] args) {

		DataExtractor dataExtractor = new DataExtractorImpl("pokemon_data");
		dataExtractor.extractPokemons(1,151);
	}
}
