package it.unibo.PokeRogue.scene.sceneSave.enums;

public enum SceneSaveStatusEnum {
    EXIT_AND_SAVE_BUTTON(5),
    CONTINUE_GAME_BUTTON(6);
    

    private final int code;

    SceneSaveStatusEnum(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}
