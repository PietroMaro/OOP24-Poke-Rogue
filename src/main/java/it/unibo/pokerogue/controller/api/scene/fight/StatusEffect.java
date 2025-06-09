package it.unibo.pokerogue.controller.api.scene.fight;


import it.unibo.pokerogue.model.api.pokemon.Pokemon;

/**
 * This interface defines the behavior of a status effect applied to a Pokémon
 * during a battle.
 * Implementing classes should define how the effect influences actions such as
 * attacking or switching,
 * and how it is applied to the Pokémon.
 * @author Miraglia Tommaso Cosimo
 */
public interface StatusEffect {

    /**
     * Checks whether the given Pokémon is allowed to perform an attack, depending
     * on its current status effect.
     *
     * @param attacker The Pokémon attempting to attack.
     * @return {@code true} if the attack is allowed, {@code false} otherwise.
     */
    Boolean checkStatusAttack(Pokemon attacker);

    /**
     * Checks whether the given Pokémon is allowed to switch out, depending on its
     * current status effect.
     *
     * @param attacker The Pokémon attempting to switch out.
     * @return {@code true} if the switch is allowed, {@code false} otherwise.
     */
    Boolean checkStatusSwitch(Pokemon attacker);

    /**
     * Applies the status effect to a target Pokémon, potentially affecting both the
     * target and the opponent.
     *
     * @param pokemon The Pokémon receiving the status effect.
     * @param enemy   The opposing Pokémon (if needed for the effect's logic).
     */
    void applyStatus(Pokemon pokemon, Pokemon enemy);
}
