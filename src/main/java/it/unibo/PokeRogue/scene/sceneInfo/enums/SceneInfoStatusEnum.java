package it.unibo.PokeRogue.scene.sceneInfo.enums;

public enum SceneInfoStatusEnum {
    BACK_BUTTON(1);

    private final int code;

    SceneInfoStatusEnum(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}
