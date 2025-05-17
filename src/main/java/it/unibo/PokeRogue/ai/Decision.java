package it.unibo.PokeRogue.ai;

public record Decision(
    DecisionTypeEnum moveType,
    String subType
) {}
