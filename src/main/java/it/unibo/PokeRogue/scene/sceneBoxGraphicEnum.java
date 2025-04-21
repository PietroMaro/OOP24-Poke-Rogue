package it.unibo.PokeRogue.scene;

public enum sceneBoxGraphicEnum {

        UP_ARROW_BUTTON(0),
        DOWN_ARROW_BUTTON(1),
        FIRST_POKEMON_BUTTON_SELECTED(2),
        SECOND_POKEMON_BUTTON_SELECTED(3),
        THIRD_POKEMON_BUTTON_SELECTED(4),
        START_GAME_BUTTON(5),

        BACKGROUND(100),
        DETAIL_CONTAINER_SPRITE(101),
        ARROWS_CONTAINER_SPRITE(102),
        UP_ARROW_SPRITE(103),
        DOWN_ARROW_SPRITE(104),
        CURRENT_BOX_TEXT(105),
        SELECTED_POKEMON_CONTAINER_SPRITE(106),
        POKEMON_SPRITE_SELECTED_0(107),
        POKEMON_SPRITE_SELECTED_1(108),
        POKEMON_SPRITE_SELECTED_2(109),
        POKEMON_CONTAINER_SPRITE(110),

        POKEMON_NUMBER_TEXT(111),

        POKEMON_NAME(112),
        POKEMON_ABILITY(113),
        POKEMON_PASSIVE(114),
        POKEMON_NATURE(115),
        POKEMON_TYPE_1(116),
        POKEMON_TYPE_2(117),
        POKEMON_BOX_TYPE_1(118),
        POKEMON_BOX_TYPE_2(119),
        POKEMON_GENDER(120),
        POKEMON_DETAIL_SPRITE(121),
        POKEMON_GROWTH_RATE(122),
        POKEMON_MOVE_1(123),
        POKEMON_MOVE_BOX_1(124),

        START_BUTTON_CONTAINER_SPRITE(125),
        START_GAME_TEXT(126);

        private final int code;

        sceneBoxGraphicEnum(int code) {
                this.code = code;
        }

        public int value() {
                return code;
        }
}
