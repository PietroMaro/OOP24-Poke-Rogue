package it.unibo.pokerogue.model.impl;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import java.nio.file.Paths;

import org.json.JSONObject;

import it.unibo.pokerogue.model.api.Range;
import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

import org.json.JSONArray;

/**
 * Move factory implementation.
 * 
 * @author Tverdohleb Egor
 */
public final class MoveFactory {

    // make the access in memory and saves the information of all pokemon in local
    private static final JsonReader JSON_READER = new JsonReaderImpl();
    private static final Map<String, Move> MOVES_BLUEPRINTS = new HashMap<>();

    private MoveFactory() {
        // Shouldn't be instanciated
    }

    /**
     * Initialize the factory reading in memory.
     */
    public static void init() throws IOException {
        final JSONArray allMoveJson = JSON_READER
                .readJsonArray("pokemonData/movesList.json");
        for (int moveIndex = 0; moveIndex < allMoveJson.length(); moveIndex += 1) {
            addMoveToBlueprints(allMoveJson.getString(moveIndex));
        }
    }

    private static void addMoveToBlueprints(final String moveName) throws IOException {
        final JSONObject moveJson = JSON_READER.readJsonObject("pokemonData/moves/" + moveName + ".json");
        final String name = moveName;
        final Range pp = new RangeImpl(0, moveJson.getInt("pp"), moveJson.getInt("pp"));
        final boolean isPhysical = moveJson.getBoolean("isPhysical");
        final JSONObject effect = moveJson.getJSONObject("effect");
        final int accuracy = moveJson.getInt("accuracy");
        final int critRate = moveJson.getInt("critRate");
        final int baseDamage = moveJson.getInt("baseDamage");
        final Type type = Type.fromString(moveJson.getString("type"));
        final int priority = moveJson.getInt("priority");
        final Move newMove = new Move(
                name,
                pp,
                isPhysical,
                Optional.of(effect),
                accuracy,
                critRate,
                baseDamage,
                0,
                1.5,
                false,
                type,
                priority);

        MOVES_BLUEPRINTS.put(moveName, newMove);
    }

    /**
     * Generate a name with the givven name.
     *
     * @param moveName the name of the move
     * @return the move
     */
    public static Move moveFromName(final String moveName) {
        Move move = MOVES_BLUEPRINTS.get(moveName);
        if (move == null) {
            throw new UnsupportedOperationException("The move " + moveName
                    + " blueprint was not found. Is not present in moveList / Factory not initialized");

        }
        move = move.deepCopy();
        return move;
    }
}
