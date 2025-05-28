package it.unibo.PokeRogue.pokemon;

/**
 * The type of the pokemon enum.
 */
public enum Type {
		/**
		 * bug.
		 */
		BUG("bug"),
		/**
		 * dark.
		 */
		DARK("dark"),
		/**
		 * dragon.
		 */
		DRAGON("dragon"),
		/**
		 * electric.
		 */
		ELECTRIC("electric"),
		/**
		 * fairy.
		 */
		FAIRY("fairy"),
		/**
		 * fighting.
		 */
		FIGHTING("fighting"),
		/**
		 * fire.
		 */
		FIRE("fire"),
		/**
		 * flying.
		 */
		FLYING("flying"),
		/**
		 * ghost.
		 */
		GHOST("ghost"),
		/**
		 * grass.
		 */
		GRASS("grass"),
		/**
		 * ground.
		 */
		GROUND("ground"),
		/**
		 * ice.
		 */
		ICE("ice"),
		/**
		 * normal.
		 */
		NORMAL("normal"),
		/**
		 * poison.
		 */
		POISON("poison"),
		/**
		 * psychic.
		 */
		PSYCHIC("psychic"),
		/**
		 * rock.
		 */
		ROCK("rock"),
		/**
		 * steel.
		 */
		STEEL("steel"),
		/**
		 * water.
		 */
		WATER("water");

	private final String typeName;

	/**
	 * Simple constructor.
	 * @param typeName the type string value.
	 */
    Type(final String typeName) {
        this.typeName = typeName;
    }

	/**
	 * Getter for the string.
	 * @return the type string valuee.
	 */
    public String typeName() {
        return typeName;
    }

	/**
	 * From String to Type.
	 * @param type the string value
	 * @return the Type with type as string value.
	 */
    public static Type fromString(final String type) {
        for (final Type t : Type.values()) {
            if (t.typeName().equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

}
