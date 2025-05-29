package it.unibo.pokerogue.model.enums;

/**
 * Enum representing different stats that can affect a character or Pokémon.
 */
public enum Stats {

    /** Physical attack power stat. */
    ATTACK,

    /** Physical defense power stat. */
    DEFENSE,

    /** Special attack power stat (e.g., magic or elemental attacks). */
    SPECIAL_ATTACK,

    /** Special defense stat against special attacks. */
    SPECIAL_DEFENSE,

    /** Hit Points (health) stat, represents vitality. */
    HP,

    /** Speed stat, determines move order in battle. */
    SPEED,

    /** Critical hit rate, chance to land a critical strike. */
    CRIT_RATE,

    /** Accuracy stat, affects the chance to hit an opponent. */
    ACCURACY
}
