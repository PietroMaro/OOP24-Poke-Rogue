package it.unibo.PokeRogue.utilities;

import java.awt.Color;

import it.unibo.PokeRogue.pokemon.Type;
import it.unibo.PokeRogue.pokemon.TypeColors;

/**
 * Utility class for converting a Pokemon {@link Type} into its corresponding
 * {@link Color}.
 * 
 * This class provides a static method to retrieve the color associated with a
 * specific Pokemon type.
 * If the type is not recognized, it returns black as a default.
 * 
 */
public final class ColorTypeConversion {

    private ColorTypeConversion() {

    }

    /**
     * Returns the {@link Color} associated with the given Pokemon {@link Type}.
     * 
     * @param type the Pokemon type for which the color is requested
     * @return the color corresponding to the given type, or black if
     *         the type is not recognized
     */
    public static Color getColorForType(final Type type) {
        try {

            return TypeColors.valueOf(type.name()).typeColor();
        } catch (final IllegalArgumentException e) {
            return Color.BLACK;
        }
    }
}
