package it.unibo.PokeRogue.ability;

import it.unibo.PokeRogue.Singleton;
import it.unibo.PokeRogue.utilities.JsonReader;
import it.unibo.PokeRogue.utilities.JsonReaderImpl;
import it.unibo.PokeRogue.ability.AbilitySituationChecks;

import java.util.Map;
import java.util.HashMap;

import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.JSONArray;


public class AbilityFactoryImpl extends Singleton implements AbilityFactory{
	
   	//make the access in memory and saves the information of all pokemon in local
	final private JsonReader jsonReader = new JsonReaderImpl();
	final private Map<String,Ability> abilityBlueprints = new HashMap<String,Ability>();
	
	public AbilityFactoryImpl(){
		init();
	}
	
	@Override
    public void init(){
		JSONArray allAbilityJson;
		allAbilityJson = jsonReader.readJsonArray(Paths.get("src","pokemon_data","abilitiesList.json").toString());
		for(int abilityIndex = 0; abilityIndex < allAbilityJson.length(); abilityIndex +=1 ){
			addAbilityToBlueprints(allAbilityJson.getString(abilityIndex));
		}
	}

	private void addAbilityToBlueprints(final String abilityName){
		JSONObject abilityJson;
        abilityJson = jsonReader.readJsonObject(Paths.get("src","pokemon_data","abilities",abilityName +".json").toString());
		AbilitySituationChecks situationChecks  = AbilitySituationChecks.fromString(abilityJson.getString("situationChecks"));
		JSONObject effect = abilityJson.getJSONObject("effect");
		final Ability newAbility = new Ability(
				situationChecks,
				effect
			);

		this.abilityBlueprints.put(abilityName,newAbility);
	}

	@Override
	public Ability abilityFromName(final String abilityName){
		Ability ability = this.abilityBlueprints.get(abilityName);
		if(ability== null){
			throw new UnsupportedOperationException("The ability "+abilityName+" blueprint was not found. Is not present in abilityList / Factory not initialized");

		}
		return ability;
	}
}
