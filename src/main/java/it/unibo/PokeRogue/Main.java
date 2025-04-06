package it.unibo.PokeRogue;

import java.util.HashMap;
import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;

import java.awt.Color;
import java.awt.Font;

public class Main {
	public static void main(String[] args) {

		DataExtractor dataExtractor = new DataExtractorImpl("pokemon_data");
		dataExtractor.extractPokemons(1,151);
	}
}
