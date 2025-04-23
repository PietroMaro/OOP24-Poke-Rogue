package it.unibo.PokeRogue.move;

import it.unibo.PokeRogue.SingletonImpl;
import it.unibo.PokeRogue.utilities.JsonReader;
import it.unibo.PokeRogue.utilities.JsonReaderImpl;
import it.unibo.PokeRogue.pokemon.Type;

import java.util.Map;
import java.util.HashMap;

import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.JSONArray;


public class MoveFactoryImpl extends SingletonImpl implements MoveFactory{
	
   	//make the access in memory and saves the information of all pokemon in local
	final private JsonReader jsonReader = new JsonReaderImpl();
	final private Map<String,Move> movesBlueprints = new HashMap<String,Move>();
	
	public MoveFactoryImpl(){
		init();
	}
	
	@Override
    public void init(){
		JSONArray allMoveJson;
		allMoveJson = jsonReader.readJsonArray(Paths.get("src","pokemon_data","movesList.json").toString());
		for(int moveIndex = 0; moveIndex < allMoveJson.length(); moveIndex +=1 ){
			addMoveToBlueprints(allMoveJson.getString(moveIndex));
		}
	}

	private void addMoveToBlueprints(final String moveName){
		JSONObject moveJson;
        moveJson = jsonReader.readJsonObject(Paths.get("src","pokemon_data","moves",moveName +".json").toString());
		int pp = moveJson.getInt("pp");
		boolean isPhysical = moveJson.getBoolean("isPhysical");
		JSONObject effect = moveJson.getJSONObject("effect");
		int accuracy = moveJson.getInt("accuracy");
		int critRate = moveJson.getInt("critRate");
		int baseDamage = moveJson.getInt("baseDamage");
		Type type = Type.fromString(moveJson.getString("type"));
		int priority = moveJson.getInt("priority");
		final Move newMove = new Move(
				pp,
				isPhysical,
				effect,
				accuracy,
				critRate,
				baseDamage,
				type,
				priority
			);

		this.movesBlueprints.put(moveName,newMove);
	}

	@Override
	public Move moveFromName(final String moveName){
		Move move = this.movesBlueprints.get(moveName);
		if(move== null){
			throw new UnsupportedOperationException("The move "+moveName+" blueprint was not found. Is not present in moveList / Factory not initialized");

		}
		return move;
	}
}
