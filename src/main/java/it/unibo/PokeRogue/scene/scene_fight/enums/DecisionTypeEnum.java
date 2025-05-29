package it.unibo.PokeRogue.scene.scene_fight.enums;

/**
 * Enum representing different types of decisions
 * that can be made during a battle.
 */
public enum DecisionTypeEnum {

    /** Attack action with priority code 1. */
    ATTACK(1),

    /** Switch-in action with priority code 3. */
    SWITCH_IN(3),

    /** Use Pokéball action with priority code 2. */
    POKEBALL(2),

    /** Do nothing action with priority code 0. */
    NOTHING(0);

    private final int code;

    /**
     * Constructs a DecisionTypeEnum with the given priority code.
     *
     * @param code the priority code of the decision type
     */
    DecisionTypeEnum(final int code) {
        this.code = code;
    }

    /**
     * Returns the priority code of the decision type.
     *
     * @return the priority code as an integer
     */
    public int priority() {
        return code;
    }

}
