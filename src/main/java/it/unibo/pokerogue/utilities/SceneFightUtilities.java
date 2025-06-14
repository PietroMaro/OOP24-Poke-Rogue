package it.unibo.pokerogue.utilities;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.impl.graphic.BoxElementImpl;
import it.unibo.pokerogue.model.impl.graphic.GraphicElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;

/**
 * Utility class providing constants and helper methods
 * for managing the fight scene in the game.
 * 
 * @author Miraglia Tommaso Cosimo
 */
public final class SceneFightUtilities {
    private static final int ATTACK_START_CODE = 100;
    private static final int ATTACK_END_CODE = 103;
    private static final Integer FIRST_POSITION = 0;
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
    public static String getPokemonLifeText(final int position, final Trainer playerTrainerInstance) {
        final Optional<Pokemon> pokemonOpt = playerTrainerInstance.getPokemon(position);

        if (!pokemonOpt.isPresent()) {
            return "??? / ???";
        }

        final Pokemon pokemon = pokemonOpt.get();
        final Integer currentHp = pokemon.getActualStats().get(Stats.HP).getCurrentValue();
        final Integer maxHp = pokemon.getActualStats().get(Stats.HP).getCurrentMax();

        return currentHp + " / " + maxHp;
    }

    /**
     * Updates the display of move information based on the currently selected
     * button.
     * This method clears any previously displayed move information and then
     * populates
     * the relevant graphic elements with details of the selected move, such as PP,
     * type, and power.
     *
     * @param currentSelectedButton       The integer representing the ID of the
     *                                    currently
     *                                    selected button.
     *                                    Expected values for move buttons are
     *                                    between 100
     *                                    and 103,
     *                                    inclusive.
     * @param currentSceneGraphicElements A {@link Map} where keys are integer IDs
     *                                    and
     *                                    values are
     *                                    {@link GraphicElementImpl} objects. This
     *                                    map
     *                                    represents
     *                                    the graphic elements currently displayed
     *                                    on the
     *                                    scene,
     *                                    which will be updated by this method.
     * @param playerTrainerInstance       The {@link Trainer} instance,
     *                                    representing
     *                                    the player's trainer and their Pokémon.
     *                                    This is
     *                                    used
     *                                    to retrieve the details of the Pokémon's
     *                                    moves.
     * @throws IOException
     */
    public static void updateMoveInfo(final int currentSelectedButton,
            final GraphicElementsRegistry currentSceneGraphicElements,
            final Trainer playerTrainerInstance) throws IOException {
        final int[] indexMapping = { 0, 2, 1, 3 };
        final int moveIndex = (currentSelectedButton >= ATTACK_START_CODE && currentSelectedButton <= ATTACK_END_CODE)
                ? indexMapping[currentSelectedButton - ATTACK_START_CODE]
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
        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "MOVE_PP_TEXT", TextElementImpl.class)
                .setText("PP: " + pp);
        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "MOVE_TYPE_TEXT", TextElementImpl.class)
                .setText("Type: " + type);
        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "MOVE_POWER_TEXT", TextElementImpl.class)
                .setText("Power: " + power);
        if (move != null) {
            UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "MOVE_TYPE", BoxElementImpl.class)
                    .setMainColor(ColorTypeConversion.getColorForType(
                            move.getType()));
        } else {
            UtilitiesForScenes.removeSceneElements("sceneFightElement.json", "clearMoveInfo",
                    currentSceneGraphicElements);
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
     * @param playerTrainerInstance The {@link Trainer} instance,
     *                              representing
     *                              the player's trainer and their Pokémon.
     * @return The name of the move if it exists, otherwise
     *         {@link #QUESTION_MARK_STRING}.
     */
    public static String getMoveNameOrPlaceholder(final int movePosition,
            final Trainer playerTrainerInstance) {
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
