package it.unibo.PokeRogue.pokemon;

import java.awt.Color;

/**
 * Enum representing Pokémon types with their associated display colors.
 */
public enum TypeColors {

	/** Type: Bug. */
	BUG(new Color(169, 252, 3)),
	/** Type: Dark. */
	DARK(new Color(60, 10, 10)),
	/** Type: Dragon. */
	DRAGON(new Color(24, 11, 120)),
	/** Type: Electric. */
	ELECTRIC(new Color(255, 200, 0)),
	/** Type: Fairy. */
	FAIRY(new Color(215, 25, 200)),
	/** Type: Fighting. */
	FIGHT(new Color(210, 80, 25)),
	/** Type: Fire. */
	FIRE(new Color(210, 25, 25)),
	/** Type: Flying. */
	FLYING(new Color(144, 211, 230)),
	/** Type: Ghost. */
	GHOST(new Color(50, 20, 225)),
	/** Type: Grass. */
	GRASS(new Color(40, 160, 35)),
	/** Type: Ground. */
	GROUND(new Color(65, 1, 1)),
	/** Type: Ice. */
	ICE(new Color(25, 220, 230)),
	/** Type: Normal. */
	NORMAL(new Color(110, 120, 120)),
	/** Type: Poison. */
	POISON(new Color(80, 30, 190)),
	/** Type: Psychic. */
	PSYCHIC(new Color(185, 30, 190)),
	/** Type: Rock. */
	ROCK(new Color(145, 88, 1)),
	/** Type: Steel. */
	STEEL(new Color(175, 170, 170)),
	/** Type: Water. */
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
