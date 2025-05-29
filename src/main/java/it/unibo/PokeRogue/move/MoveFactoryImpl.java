package it.unibo.PokeRogue.move;

import it.unibo.PokeRogue.utilities.Range;
import it.unibo.PokeRogue.Singleton;
import it.unibo.PokeRogue.utilities.JsonReader;
import it.unibo.PokeRogue.utilities.JsonReaderImpl;
import it.unibo.PokeRogue.utilities.RangeImpl;
import it.unibo.PokeRogue.pokemon.Type;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.JSONArray;


/**
 * Move factory implementation.
 */
public class MoveFactoryImpl extends Singleton implements MoveFactory {
	
   	//make the access in memory and saves the information of all pokemon in local
	private final JsonReader jsonReader = new JsonReaderImpl();
	private final Map<String, Move> movesBlueprints = new HashMap<>();
	
	/**
	 * Constructor initiate the factory.
	 */
	public MoveFactoryImpl() throws IOException {
		init();
	}

	@Override
    public final void init() throws IOException {
		final JSONArray allMoveJson = jsonReader
			.readJsonArray(Paths
					.get("src",
						"pokemon_data",
						"movesList.json").toString());
		for (int moveIndex = 0; moveIndex < allMoveJson.length(); moveIndex += 1) {
			addMoveToBlueprints(allMoveJson.getString(moveIndex));
		}
	}

	private void addMoveToBlueprints(final String moveName) throws IOException {
		final JSONObject moveJson = jsonReader.readJsonObject(Paths
				.get("src",
					"pokemon_data",
					"moves",
					moveName + ".json").toString());
		final String name = moveName;
		final Range<Integer> pp = new RangeImpl<>(0, moveJson.getInt("pp"), moveJson.getInt("pp"));
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
				effect,
				accuracy,
				critRate,
				baseDamage,
				0,
				1.5,
				false,
				type,
				priority);

		this.movesBlueprints.put(moveName, newMove);
	}

	@Override
	public final Move moveFromName(final String moveName) {
		Move move = this.movesBlueprints.get(moveName);
		if (move == null) {
			throw new UnsupportedOperationException("The move " + moveName
					+ " blueprint was not found. Is not present in moveList / Factory not initialized");

		}
		move = move.deepCopy();
		return move;
	}
}
