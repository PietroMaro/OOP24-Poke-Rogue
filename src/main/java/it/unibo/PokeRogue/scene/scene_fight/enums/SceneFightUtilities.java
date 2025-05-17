package it.unibo.PokeRogue.scene.scene_fight.enums;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.utilities.ColorTypeConversion;

public class SceneFightUtilities {
    private final static Integer FIRST_POSITION = 0;

    SceneFightUtilities() {
    }

    public static String getPokemonNameAt(Trainer trainer, int position) {
        return trainer.getPokemon(position)
                .map(Pokemon::getName)
                .orElse("???");
    }

    public static String getPokemonLifeText(int position, PlayerTrainerImpl playerTrainerInstance) {
        Optional<Pokemon> pokemonOpt = playerTrainerInstance.getPokemon(position);

        if (!pokemonOpt.isPresent()) {
            return "??? / ???";
        }

        Pokemon pokemon = pokemonOpt.get();
        Integer currentHp = pokemon.getActualStats().get("hp").getCurrentValue();
        Integer maxHp = pokemon.getActualStats().get("hp").getCurrentMax();

        return currentHp + " / " + maxHp;
    }

    private static void clearMoveInfo(Map<Integer, GraphicElementImpl> sceneGraphicElements) {
        sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_PP_TEXT.value());
        sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_TYPE_TEXT.value());
        sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_POWER_TEXT.value());
        sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_TYPE.value());
    }

    public static void updateMoveInfo(int currentSelectedButton, Map<Integer, GraphicElementImpl> sceneGraphicElements,
            PlayerTrainerImpl playerTrainerInstance) {
        clearMoveInfo(sceneGraphicElements);

        int moveIndex = switch (currentSelectedButton) {
            case 100 -> 0;
            case 101 -> 2;
            case 102 -> 1;
            case 103 -> 3;
            default -> -1;
        };

        if (moveIndex == -1)
            return;

        Move move = playerTrainerInstance.getPokemon(FIRST_POSITION)
                .map(p -> {
                    List<Move> moves = p.getActualMoves();
                    return (moveIndex >= 0 && moveIndex < moves.size()) ? moves.get(moveIndex)
                            : null;
                })
                .orElse(null);

        String pp = "???";
        String type = "???";
        String power = "???";

        if (move != null) {
            pp = String.valueOf(move.getPp().getCurrentValue() + " / " + move.getPp().getCurrentMax());
            type = String.valueOf(move.getType());
            power = String.valueOf(move.getBaseDamage());
        }

        sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_PP_TEXT.value(),
                new TextElementImpl("movePanel", "PP: " + pp,
                        Color.WHITE, 0.06, 0.6, 0.79));
        sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_TYPE_TEXT.value(),
                new TextElementImpl("movePanel",
                        "Type: " + type,
                        Color.WHITE, 0.06, 0.6, 0.86));
        sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_POWER_TEXT.value(),
                new TextElementImpl("movePanel",
                        "Power: " + power,
                        Color.WHITE, 0.06, 0.6, 0.94));
        if (move != null) {
            sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_TYPE.value(),
                    new BoxElementImpl("movePanel",
                            ColorTypeConversion.getColorForType(
                                    move.getType()),
                            Color.BLACK, 1, 0.65, 0.82, 0.15, 0.06));
        }
    }

    public static String getMoveNameOrPlaceholder(int movePosition, PlayerTrainerImpl playerTrainerInstance) {
        if (playerTrainerInstance.getPokemon(FIRST_POSITION).isPresent() &&
                playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves() != null &&
                playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves()
                        .size() > movePosition
                &&
                playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves()
                        .get(movePosition) != null) {
            return playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                    .getActualMoves().get(movePosition).getName();
        } else {
            return "???";
        }
    }

    public static boolean isButtonInRange(int buttonValue, int lowerBound, int upperBound) {
        return buttonValue >= lowerBound && buttonValue <= upperBound;
    }

}
