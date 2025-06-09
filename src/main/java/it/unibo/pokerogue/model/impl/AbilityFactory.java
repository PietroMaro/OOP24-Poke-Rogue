package it.unibo.pokerogue.model.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import java.io.IOException;
import java.nio.file.Paths;

import org.json.JSONObject;

import it.unibo.pokerogue.model.api.ability.Ability;
import it.unibo.pokerogue.model.enums.AbilitySituationChecks;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

import org.json.JSONArray;

/**
 * The ability factory.
 * 
 * @author Tverdohleb Egor
 */
public final class AbilityFactory {
    // make the access in memory and saves the information of all pokemon in local
    private static final JsonReader JSON_READER = new JsonReaderImpl();
    private static final Map<String, Ability> ABILITY_BLUEPRINTS = new HashMap<>();

    private AbilityFactory() {
        // Shouldn't be instantiated
    }

    /**
     * Initializes the factory by reading all Ability names and their corresponding
     * data
     * from the JSON files.
     *
     * @throws IOException if an error occurs while reading item files
     */
    public static void init() throws IOException {
        final JSONArray allAbilityJson;
        allAbilityJson = JSON_READER.readJsonArray("pokemonData/abilitiesList.json");

        for (int abilityIndex = 0; abilityIndex < allAbilityJson.length(); abilityIndex += 1) {
            addAbilityToBlueprints(allAbilityJson.getString(abilityIndex));
        }
    }

    private static void addAbilityToBlueprints(final String abilityName) throws IOException {
        final JSONObject abilityJson = JSON_READER
                .readJsonObject("pokemonData/abilities/" + abilityName + ".json");

        final AbilitySituationChecks situationChecks = AbilitySituationChecks
                .fromString(abilityJson.getString("situationChecks"));
        final JSONObject effect = abilityJson.getJSONObject("effect");
        final Ability newAbility = new Ability(
                situationChecks,
                Optional.ofNullable(effect));

        ABILITY_BLUEPRINTS.put(abilityName, newAbility);
    }

    /**
     * return the ability with that string value.
     * 
     * @param abilityName the ability string value
     * @return the ability
     */
    public static Ability abilityFromName(final String abilityName) {
        final Ability ability = ABILITY_BLUEPRINTS.get(abilityName);
        if (ability == null) {
            throw new UnsupportedOperationException("The ability "
                    + abilityName
                    + " blueprint was not found. Is not present in abilityList / Factory not initialized");
        }
        return ability;
    }
}
