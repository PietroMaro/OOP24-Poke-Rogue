package it.unibo.pokerogue.model.enums;

/**
 * Represents various status conditions that a character or entity
 * can experience in a game. These conditions may affect the entity's
 * actions, stats, or behavior during gameplay.
 * 
 * @author Miraglia Tommaso
 */
public enum StatusCondition {

    /**
     * The entity is burned. Typically causes damage over time and
     * may reduce physical attack power.
     */
    BURN("burn"),

    /**
     * The entity is frozen. Prevents movement or actions until thawed.
     */
    FREEZE("freeze"),

    /**
     * The entity is paralyzed. May randomly prevent actions and
     * often reduces speed.
     */
    PARALYSIS("paralysis"),

    /**
     * The entity is poisoned. Causes damage over time.
     */
    POISON("poison"),

    /**
     * The entity is asleep. Prevents actions until the entity wakes up.
     */
    SLEEP("sleep"),

    /**
     * The entity is bound. Movement is restricted, and it may
     * take damage over time.
     */
    BOUND("bound"),

    /**
     * The entity is confused. It may hurt itself instead of executing
     * the intended action.
     */
    CONFUSION("confusion"),

    /**
     * The entity flinched. It loses its turn or cannot act momentarily.
     */
    FLINCH("flinch"),

    /**
     * The entity is trapped. It cannot escape or switch out.
     */
    TRAPPED("trapped"),

    /**
     * The entity is charmed. May cause the entity to hesitate
     * or fail to attack due to affection.
     */
    CHARMED("charmed"),

    /**
     * The entity is seeded. Drains health each turn, typically
     * transferring it to the user who applied the effect.
     */
    SEEDED("seeded");

    private final String statusName;

    /**
     * Constructs a new {@code StatusCondition} with the given string
     * representation.
     *
     * @param statusName the name of the status condition
     */
    StatusCondition(final String statusName) {
        this.statusName = statusName;
    }

    /**
     * Returns the string representation of the status condition.
     *
     * @return the name of the status condition
     */
    public String statusName() {
        return this.statusName;
    }

    /**
     * Returns the corresponding {@code StatusCondition} for the given string.
     * The comparison is case-insensitive.
     *
     * @param status the string to match with a status condition
     * @return the matching {@code StatusCondition}
     * @throws IllegalArgumentException if no matching status condition is found
     */
    public static StatusCondition fromString(final String status) {
        for (final StatusCondition s : values()) {
            if (s.statusName().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status condition: " + status);
    }
}
