package it.unibo.PokeRogue.scene.sceneFight;

/**
 * Interface for generating an enemy in the battle scene.
 * This interface defines the contract for generating an enemy Pokémon
 * that can be used in a battle.
 */
public interface GenerateEnemy {

    /**
     * Generates an enemy Pokémon for the battle.
     * Implementations should define the logic for creating an enemy Pokémon
     * and adding it to the battle scenario.
     */
    void generateEnemy();
}
