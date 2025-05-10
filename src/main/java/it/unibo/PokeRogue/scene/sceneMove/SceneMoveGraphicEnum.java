package it.unibo.PokeRogue.scene.sceneMove;

public enum SceneMoveGraphicEnum {

    MOVE_1_BUTTON(0),
    MOVE_2_BUTTON(1),
    MOVE_3_BUTTON(2),
    MOVE_4_BUTTON(3),
    MOVE_5_BUTTON(4),
    
    BACKGROUND(100),
    MOVE_1_BUTTON_TEXT(101),
    MOVE_2_BUTTON_TEXT(102),
    MOVE_3_BUTTON_TEXT(103),
    MOVE_4_BUTTON_TEXT(104),
    MOVE_5_BUTTON_TEXT(105);

    private final int code;

    SceneMoveGraphicEnum(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}

