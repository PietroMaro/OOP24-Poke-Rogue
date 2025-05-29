package it.unibo.PokeRogue.pokemon;

import java.awt.Color;

/**
 * Enum representing Pokémon types with their associated display colors.
 */
public enum TypeColors {
	BUG(new Color(169, 252, 3)),
	DARK(new Color(60, 10, 10)),
	DRAGON(new Color(24, 11, 120)),
	ELECTRIC(new Color(255, 200, 0)),
	FAIRY(new Color(215, 25, 200)),
	FIGHT(new Color(210, 80, 25)),
	FIRE(new Color(210, 25, 25)),
	FLYING(new Color(144, 211, 230)),
	GHOST(new Color(50, 20, 225)),
	GRASS(new Color(40, 160, 35)),
	GROUND(new Color(65, 1, 1)),
	ICE(new Color(25, 220, 230)),
	NORMAL(new Color(110, 120, 120)),
	POISON(new Color(80, 30, 190)),
	PSYCHIC(new Color(185, 30, 190)),
	ROCK(new Color(145, 88, 1)),
	STEEL(new Color(175, 170, 170)),
	WATER(new Color(45, 100, 180));

	private final Color typeColor;

	/**
	 * Constructs a TypeColors enum constant with the specified color.
	 *
	 * @param typeColor the Color associated with the Pokémon type
	 */
	TypeColors(final Color typeColor) {
		this.typeColor = typeColor;
	}

	/**
	 * Returns the Color associated with this Pokémon type.
	 *
	 * @return the color representing the Pokémon type
	 */
	public Color typeColor() {
		return this.typeColor;
	}

}
