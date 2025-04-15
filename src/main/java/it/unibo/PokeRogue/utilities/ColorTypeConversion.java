package it.unibo.PokeRogue.utilities;

import java.awt.Color;

import it.unibo.PokeRogue.pokemon.Type;
import it.unibo.PokeRogue.pokemon.TypeColors;

public class ColorTypeConversion {
     public static Color getColorForType(Type type) {
        try {
            // Cerca l'enum in TypeColors con lo stesso nome
            return TypeColors.valueOf(type.name()).typeColor();
        } catch (IllegalArgumentException e) {
            // Se non c'è corrispondenza
            return Color.BLACK; // fallback o throw se preferisci
        }
    }
}
