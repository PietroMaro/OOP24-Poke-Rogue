package it.unibo.PokeRogue.ability;
import org.json.JSONObject;
/**
 * The record that holds ability read from memory.
 * @param situationChecks the situaion where the ability has to activate
 * @param effect of the ability
 */
public record Ability(
		AbilitySituationChecks situationChecks,
		JSONObject effect) { }
