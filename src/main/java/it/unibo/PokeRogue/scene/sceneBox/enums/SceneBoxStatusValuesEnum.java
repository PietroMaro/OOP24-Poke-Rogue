package it.unibo.PokeRogue.scene.sceneBox.enums;

public enum SceneBoxStatusValuesEnum {

    UP_ARROW_BUTTON_POSITION(0),
    DOWN_ARROW_BUTTON_POSITION(1),
    START_BUTTON_POSITION(5),
    FIRST_POKEMON_BUTTON_POSITION(6),
    POKE_BOX_ROW_LENGHT(9);

    private final int code;

    SceneBoxStatusValuesEnum(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }

}
