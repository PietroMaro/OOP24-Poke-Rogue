package it.unibo.PokeRogue.scene.sceneSave.enums;

public enum SceneSaveEnum {
    QUESTION_BOX(1),
    QUESTION_TEXT(2),
    CONTINUE_GAME_TEXT(3),
    EXIT_AND_SAVE_TEXT(4),

    BACKGROUND(100);

    private final int code;

    SceneSaveEnum(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}
