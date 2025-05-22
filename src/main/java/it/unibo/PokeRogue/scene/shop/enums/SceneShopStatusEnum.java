package it.unibo.PokeRogue.scene.shop.enums;

public enum SceneShopStatusEnum {
    FREE_ITEM_1_BUTTON(1),
    FREE_ITEM_2_BUTTON(2),
    FREE_ITEM_3_BUTTON(3),
    PRICY_ITEM_1_BUTTON(4),
    PRICY_ITEM_2_BUTTON(5),
    PRICY_ITEM_3_BUTTON(6),
    REROL_BUTTON(7),
    TEAM_BUTTON(8),

    CHANGE_POKEMON_1_BUTTON(200),
    CHANGE_POKEMON_2_BUTTON(201),
    CHANGE_POKEMON_3_BUTTON(202),
    CHANGE_POKEMON_4_BUTTON(203),
    CHANGE_POKEMON_5_BUTTON(204),
    CHANGE_POKEMON_6_BUTTON(205),
    CHANGE_POKEMON_BACK_BUTTON(206);

    private final int code;

    SceneShopStatusEnum(final int code) {
        this.code = code;
    }

    public int value() {
        return code;
    }
}
