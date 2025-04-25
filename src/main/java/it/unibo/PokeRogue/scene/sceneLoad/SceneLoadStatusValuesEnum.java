package it.unibo.PokeRogue.scene.sceneLoad;

public enum SceneLoadStatusValuesEnum {
    
    ABSOLUTE_FIRST_SAVE_POSITION(0),

    LAST_SAVE_POSITION(9),

    NUMBER_OF_SAVE_SHOWED(10);


    private final int code;

    SceneLoadStatusValuesEnum(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}
