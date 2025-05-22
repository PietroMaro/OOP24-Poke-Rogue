package it.unibo.PokeRogue.scene.scene_fight;

import it.unibo.PokeRogue.scene.scene_fight.enums.DecisionTypeEnum;

public record Decision(
    DecisionTypeEnum moveType,
    String subType
) {}
