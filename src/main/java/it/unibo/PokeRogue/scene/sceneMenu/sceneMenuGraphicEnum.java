package it.unibo.PokeRogue.scene.sceneMenu;

public enum sceneMenuGraphicEnum {

    LOAD_BUTTON(0),
    NEW_GAME_BUTTON(1),
    OPTIONS_BUTTON(2),
    BACKGROUND(100),
    LOAD_GAME_BUTTON_TEXT(101),
    NEW_GAME_BUTTON_TEXT(102),

    OPTIONS_GAME_BUTTON_TEXT(103);

    private final int code;

    sceneMenuGraphicEnum(int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }

    /**
     * Returns the next button in the visual navigation order (cyclical).
     *
     * @param currentSelectedButton the currently selected button.
     * @return the next button.
     */
    public static sceneMenuGraphicEnum nextButtonsNames(sceneMenuGraphicEnum currentSelectedButton) {
        if (currentSelectedButton.ordinal() == 0) {
            return values()[2];
        }
        int nextOrdinal = (currentSelectedButton.ordinal() - 1);
        return values()[nextOrdinal];
    }

    /**
     * Returns the previous button in the visual navigation order (cyclical).
     *
     * @param currentSelectedButton the currently selected button.
     * @return the previous button.
     */
    public static sceneMenuGraphicEnum previousButtonsNames(sceneMenuGraphicEnum currentSelectedButton) {

        if (currentSelectedButton.ordinal() == 2) {
            return values()[0];
        }
        int nextOrdinal = (currentSelectedButton.ordinal() + 1);
        return values()[nextOrdinal];
    }
}
