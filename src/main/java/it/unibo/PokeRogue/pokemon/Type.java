package it.unibo.PokeRogue;

public enum Type {
		BUG("bug"),
		DARK("dark"),
		DRAGON("dragon"),
		ELECTRIC("electric"),
		FAIRY("fairy"),
		FIGHT("fight"),
		FIRE("fire"),
		FLYING("flying"),
		GHOST("ghost"),
		GRASS("grass"),
		GROUND("ground"),
		ICE("ice"),
		NORMAL("normal"),
		POISON("poison"),
		PSYCHIC("psychic"),
		ROCK("rock"),
		STEEL("steel"),
		WATER("water");

	private final String typeName;

    Type(String typeName) {
        this.typeName = typeName;
    }

    public String typeName() {
        return typeName;
    }

    public static Type fromString(String type) {
        for (Type t : Type.values()) {
            if (t.typeName().equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

}
