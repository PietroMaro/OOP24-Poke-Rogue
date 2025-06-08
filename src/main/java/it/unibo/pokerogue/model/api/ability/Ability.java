package it.unibo.pokerogue.model.api.ability;

import org.json.JSONObject;
import java.util.Optional;

import it.unibo.pokerogue.model.enums.AbilitySituationChecks;

/**
 * The record that holds ability read from memory.
 * 
 * @param situationChecks the situaion where the ability has to activate
 * @param effect          of the ability
 */
public record Ability(
        AbilitySituationChecks situationChecks,
        Optional<JSONObject> effect) {
}
