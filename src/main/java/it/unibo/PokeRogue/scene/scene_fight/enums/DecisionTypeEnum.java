package it.unibo.PokeRogue.scene.scene_fight.enums;

public enum DecisionTypeEnum {

    ATTACK(1),
    SWITCH_IN(3),
    POKEBALL(2),
    NOTHING(0);

    private final int code;

    DecisionTypeEnum(final int code) {
        this.code = code;
    }

    public int priority() {
        return code;
    }

}
