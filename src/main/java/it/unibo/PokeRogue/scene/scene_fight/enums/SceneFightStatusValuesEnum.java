package it.unibo.PokeRogue.scene.scene_fight.enums;


public enum SceneFightStatusValuesEnum {

    FIGHT_BUTTON(1),        
    POKEMON_BUTTON(2),      
    BALL_BUTTON(3),         
    RUN_BUTTON(4),          

    MOVE_BUTTON_1(100),
    MOVE_BUTTON_3(101),
    MOVE_BUTTON_2(102),
    MOVE_BUTTON_4(103),

    CHANGE_POKEMON_1(200),
    CHANGE_POKEMON_2(201),
    CHANGE_POKEMON_3(202),
    CHANGE_POKEMON_4(203),
    CHANGE_POKEMON_5(204),
    CHANGE_POKEMON_BACK(205),

    POKEBALL_BUTTON(300),
    MEGABALL_BUTTON(301),
    ULTRABALL_BUTTON(302),
    MASTERBALL_BUTTON(303),
    CANCEL_BUTTON(304);

    private final int code;

    SceneFightStatusValuesEnum(final int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}
