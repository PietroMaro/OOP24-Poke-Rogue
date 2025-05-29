package it.unibo.PokeRogue.ability;

import it.unibo.PokeRogue.Singleton;
import it.unibo.PokeRogue.utilities.JsonReader;
import it.unibo.PokeRogue.utilities.JsonReaderImpl;

import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.JSONArray;


/**
 * The ability factory.
 */
public class AbilityFactoryImpl extends Singleton implements AbilityFactory {
	
   	//make the access in memory and saves the information of all pokemon in local
	private final JsonReader jsonReader = new JsonReaderImpl();
	private final Map<String, Ability> abilityBlueprints = new HashMap<>();
	
	/**
	 * initiate the factory.
	 */
	public AbilityFactoryImpl() throws IOException {
		init();
	}
	
    private void init() throws IOException {
		final JSONArray allAbilityJson;
		allAbilityJson = jsonReader.readJsonArray(Paths
				.get("src",
					"pokemon_data",
					"abilitiesList.json").toString());
		for (int abilityIndex = 0; abilityIndex < allAbilityJson.length(); abilityIndex += 1) {
			addAbilityToBlueprints(allAbilityJson.getString(abilityIndex));
		}
	}

	private void addAbilityToBlueprints(final String abilityName) throws IOException {
		final JSONObject abilityJson = jsonReader
			.readJsonObject(Paths.get("src",
						"pokemon_data",
						"abilities",
						abilityName + ".json").toString());
		final AbilitySituationChecks situationChecks = AbilitySituationChecks
			.fromString(abilityJson.getString("situationChecks"));
		final JSONObject effect = abilityJson.getJSONObject("effect");
		final Ability newAbility = new Ability(
				situationChecks,
				effect
			);

		this.abilityBlueprints.put(abilityName, newAbility);
	}

	/**
	 * return the ability with that string value.
	 * @param abilityName the ability string value
	 * @return the ability
	 */
	@Override
	public Ability abilityFromName(final String abilityName) {
		final Ability ability = this.abilityBlueprints.get(abilityName);
		if (ability == null) {
			throw new UnsupportedOperationException("The ability "
					+ abilityName
					+ " blueprint was not found. Is not present in abilityList / Factory not initialized");
		}
		return ability;
	}
}
