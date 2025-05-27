package it.unibo.PokeRogue.scene.sceneInfo.enums;

public enum SceneInfoEnum {
    BACK_TEXT(0);

    private final int code;

    SceneInfoEnum(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}
