package it.unibo.PokeRogue.scene.scene_fight;

import it.unibo.PokeRogue.scene.scene_fight.enums.DecisionTypeEnum;

/**
 * Represents a player's decision during a fight scene.
 * Encapsulates the main decision type and an optional sub-type detail.
 *
 * @param moveType the main type of decision
 * @param subType  an optional sub-category or specific identifier
 */
public record Decision(
        DecisionTypeEnum moveType,
        String subType) {
}
