package it.unibo.PokeRogue.scene.scene_fight;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.scene.scene_fight.enums.SceneFightGraphicEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.utilities.ColorTypeConversion;

/**
 * Utility class providing constants and helper methods
 * for managing the fight scene in the game.
 */
public final class SceneFightUtilities {
    private static final Integer FIRST_POSITION = 0;
    private static final String MOVE_PANEL_STRING = "movePanel";
    private static final String QUESTION_MARK_STRING = "???";

    /**
     * Empty constructor.
     * 
     */
    private SceneFightUtilities() {
    }

    /**
     * Returns the name of the Pokémon at the specified position in the trainer's
     * party.
     * If there is no Pokémon at the given position, returns a placeholder string.
     *
     * @param trainer  the Trainer whose Pokémon party is queried
     * @param position the position index of the Pokémon in the party
     * @return the name of the Pokémon at the given position, or a placeholder if
     *         none is found
     */
    public static String getPokemonNameAt(final Trainer trainer, final int position) {
        return trainer.getPokemon(position)
                .map(Pokemon::getName)
                .orElse(QUESTION_MARK_STRING);
    }

    /**
     * Returns a string representation of the Pokémon's current and maximum HP
     * at the specified position in the player's party.
     * If there is no Pokémon at the given position, returns a placeholder string.
     *
     * @param position              the index position of the Pokémon in the
     *                              player's party
     * @param playerTrainerInstance the player trainer instance containing the
     *                              Pokémon party
     * @return a string in the format "currentHp / maxHp" or "??? / ???" if no
     *         Pokémon is present
     */
    public static String getPokemonLifeText(final int position, final PlayerTrainerImpl playerTrainerInstance) {
        final Optional<Pokemon> pokemonOpt = playerTrainerInstance.getPokemon(position);

        if (!pokemonOpt.isPresent()) {
            return "??? / ???";
        }

        final Pokemon pokemon = pokemonOpt.get();
        final Integer currentHp = pokemon.getActualStats().get("hp").getCurrentValue();
        final Integer maxHp = pokemon.getActualStats().get("hp").getCurrentMax();

        return currentHp + " / " + maxHp;
    }

    /**
     * Removes move-related graphic elements from the provided map of scene graphic
     * elements.
     * Specifically, it removes elements associated with move PP text, move type
     * text,
     * move power text, and move type icons.
     *
     * @param sceneGraphicElements a map containing graphic elements keyed by their
     *                             integer identifiers
     */
    private static void clearMoveInfo(final Map<Integer, GraphicElementImpl> sceneGraphicElements) {
        sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_PP_TEXT.value());
        sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_TYPE_TEXT.value());
        sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_POWER_TEXT.value());
        sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_TYPE.value());
    }

    /**
     * Updates the display of move information based on the currently selected
     * button.
     * This method clears any previously displayed move information and then
     * populates
     * the relevant graphic elements with details of the selected move, such as PP,
     * type, and power.
     *
     * @param currentSelectedButton The integer representing the ID of the currently
     *                              selected button.
     *                              Expected values for move buttons are between 100
     *                              and 103,
     *                              inclusive.
     * @param sceneGraphicElements  A {@link Map} where keys are integer IDs and
     *                              values are
     *                              {@link GraphicElementImpl} objects. This map
     *                              represents
     *                              the graphic elements currently displayed on the
     *                              scene,
     *                              which will be updated by this method.
     * @param playerTrainerInstance The {@link PlayerTrainerImpl} instance,
     *                              representing
     *                              the player's trainer and their Pokémon. This is
     *                              used
     *                              to retrieve the details of the Pokémon's moves.
     */
    public static void updateMoveInfo(final int currentSelectedButton,
            final Map<Integer, GraphicElementImpl> sceneGraphicElements,
            final PlayerTrainerImpl playerTrainerInstance) {
        clearMoveInfo(sceneGraphicElements);

        final int[] indexMapping = { 0, 2, 1, 3 };
        final int moveIndex = (currentSelectedButton >= 100 && currentSelectedButton <= 103)
                ? indexMapping[currentSelectedButton - 100]
                : -1;

        if (moveIndex == -1) {
            return;
        }

        final Move move = playerTrainerInstance.getPokemon(FIRST_POSITION)
                .map(p -> {
                    final List<Move> moves = p.getActualMoves();
                    return (moveIndex >= 0 && moveIndex < moves.size()) ? moves.get(moveIndex)
                            : null;
                })
                .orElse(null);

        String pp = QUESTION_MARK_STRING;
        String type = QUESTION_MARK_STRING;
        String power = QUESTION_MARK_STRING;

        if (move != null) {
            pp = String.valueOf(move.getPp().getCurrentValue() + " / " + move.getPp().getCurrentMax());
            type = String.valueOf(move.getType());
            power = String.valueOf(move.getBaseDamage());
        }

        sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_PP_TEXT.value(),
                new TextElementImpl(MOVE_PANEL_STRING, "PP: " + pp,
                        Color.WHITE, 0.06, 0.6, 0.79));
        sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_TYPE_TEXT.value(),
                new TextElementImpl(MOVE_PANEL_STRING,
                        "Type: " + type,
                        Color.WHITE, 0.06, 0.6, 0.86));
        sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_POWER_TEXT.value(),
                new TextElementImpl(MOVE_PANEL_STRING,
                        "Power: " + power,
                        Color.WHITE, 0.06, 0.6, 0.94));
        if (move != null) {
            sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_TYPE.value(),
                    new BoxElementImpl(MOVE_PANEL_STRING,
                            ColorTypeConversion.getColorForType(
                                    move.getType()),
                            Color.BLACK, 1, 0.64, 0.82, 0.15, 0.06));
        }
    }

    /**
     * Retrieves the name of a move at a specific position for the player's first
     * Pokémon.
     * If the Pokémon, its moves, or the move at the specified position do not
     * exist,
     * a placeholder string ({@link #QUESTION_MARK_STRING}) is returned.
     *
     * @param movePosition          The zero-based index of the move to retrieve.
     * @param playerTrainerInstance The {@link PlayerTrainerImpl} instance,
     *                              representing
     *                              the player's trainer and their Pokémon.
     * @return The name of the move if it exists, otherwise
     *         {@link #QUESTION_MARK_STRING}.
     */
    public static String getMoveNameOrPlaceholder(final int movePosition,
            final PlayerTrainerImpl playerTrainerInstance) {
        if (playerTrainerInstance.getPokemon(FIRST_POSITION).isPresent()
                && playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves() != null
                && playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves().size() > movePosition
                && playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves().get(movePosition) != null) {
            return playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                    .getActualMoves().get(movePosition).getName();
        } else {
            return QUESTION_MARK_STRING;
        }
    }

    /**
     * Checks if a given button value falls within a specified range (inclusive).
     *
     * @param buttonValue The integer value of the button to check.
     * @param lowerBound  The lower bound of the range (inclusive).
     * @param upperBound  The upper bound of the range (inclusive).
     * @return {@code true} if the button value is within the range, {@code false}
     *         otherwise.
     */
    public static boolean isButtonInRange(final int buttonValue, final int lowerBound, final int upperBound) {
        return buttonValue >= lowerBound && buttonValue <= upperBound;
    }

}
