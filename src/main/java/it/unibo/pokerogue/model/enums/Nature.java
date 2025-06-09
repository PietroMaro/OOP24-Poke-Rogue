package it.unibo.pokerogue.model.enums;

import static it.unibo.pokerogue.model.enums.Stats.ATTACK;
import static it.unibo.pokerogue.model.enums.Stats.DEFENSE;
import static it.unibo.pokerogue.model.enums.Stats.SPECIAL_ATTACK;
import static it.unibo.pokerogue.model.enums.Stats.SPECIAL_DEFENSE;
import static it.unibo.pokerogue.model.enums.Stats.SPEED;

import java.util.Random;

/**
 * Pokemon nature enumerator.
 * 
 * @author Tverdohleb Egor
 */
public enum Nature {
    /**
     * + attack | - specialAttack.
     */
    ADAMANT(ATTACK, SPECIAL_ATTACK),
    /**
     * neutral.
     */
    BASHFUL(SPECIAL_ATTACK, SPECIAL_ATTACK),
    /**
     * + defense | - attack.
     */
    BOLD(DEFENSE, ATTACK),
    /**
     * + attack | - speed.
     */
    BRAVE(ATTACK, SPEED),
    /**
     * + specialDefense | - attack.
     */
    CALM(SPECIAL_DEFENSE, ATTACK),
    /**
     * + specialDefense | - specialAttack.
     */
    CAREFUL(SPECIAL_DEFENSE, SPECIAL_ATTACK),
    /**
     * neutral.
     */
    DOCILE(DEFENSE, DEFENSE),
    /**
     * + specialDefense | - defense.
     */
    GENTLE(SPECIAL_DEFENSE, DEFENSE),
    /**
     * neutral.
     */
    HARDY(ATTACK, ATTACK),
    /**
     * + speed | - defense.
     */
    HASTY(SPEED, DEFENSE),
    /**
     * + defense | - specialAttack.
     */
    IMPISH(DEFENSE, SPECIAL_ATTACK),
    /**
     * + speed | - specialAttack.
     */
    JOLLY(SPEED, SPECIAL_ATTACK),
    /**
     * + defense | - specialDefense.
     */
    LAX(DEFENSE, SPECIAL_DEFENSE),
    /**
     * + attack | - defense.
     */
    LONELY(ATTACK, DEFENSE),
    /**
     * + specialAttack | - defense.
     */
    MILD(SPECIAL_ATTACK, DEFENSE),
    /**
     * + specialAttack | - attack.
     */
    MODEST(SPECIAL_ATTACK, ATTACK),
    /**
     * + speed | - specialDefense.
     */
    NAIVE(SPEED, SPECIAL_DEFENSE),
    /**
     * + attack | - specialDefense.
     */
    NAUGHTY(ATTACK, SPECIAL_DEFENSE),
    /**
     * + specialAttack | - speed.
     */
    QUIET(SPECIAL_ATTACK, SPEED),
    /**
     * neutral.
     */
    QUIRKY(SPECIAL_DEFENSE, SPECIAL_DEFENSE),
    /**
     * + specialAttack | - specialDefense.
     */
    RASH(SPECIAL_ATTACK, SPECIAL_DEFENSE),
    /**
     * + defense | - speed.
     */
    RELAXED(DEFENSE, SPEED),
    /**
     * + specialDefense | - speed.
     */
    SASSY(SPECIAL_DEFENSE, SPEED),
    /**
     * neutral.
     */
    SERIOUS(SPEED, SPEED),
    /**
     * + speed | - attack.
     */
    TIMID(SPEED, ATTACK);

    private static final Random RANDOM = new Random();

    private final Stats statIncrease; // Statistica che aumenta.
    private final Stats statDecrease; // Statistica che diminuisce

    // Costruttore per inizializzare i campi
    Nature(final Stats statIncrease, final Stats statDecrease) {
        this.statIncrease = statIncrease;
        this.statDecrease = statDecrease;
    }

    /**
     * Simple getter.
     * 
     * @return The stat increase
     */
    public Stats statIncrease() {
        return this.statIncrease;
    }

    /**
     * Simple getter.
     * 
     * @return The stat decrease
     */
    public Stats statDecrease() {
        return this.statDecrease;
    }

    /**
     * Returns a random Nature.
     * 
     * @return a random nature
     */
    public static Nature getRandomNature() {
        final int index = RANDOM.nextInt(values().length);
        return values()[index];
    }

    /**
     * Check if the nature given increases the stat.
     * 
     * @param nature The nature you want to check
     * @param stat   The statistic you want to check
     * @return true if the stat is increased by the nature
     */
    public static boolean checkStatIncrease(final Nature nature, final Stats stat) {
        return nature.statIncrease.equals(stat);
    }

    /**
     * Check if the nature given decrease the stat.
     * 
     * @param nature The nature you want to check
     * @param stat   The statistic you want to check
     * @return true if the stat is decreased by the nature
     */
    public static boolean checkStatDecrease(final Nature nature, final Stats stat) {
        return nature.statDecrease.equals(stat);
    }
}
