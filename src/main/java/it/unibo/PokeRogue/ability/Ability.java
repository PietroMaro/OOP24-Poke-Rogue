package it.unibo.PokeRogue.ability;
import org.json.JSONObject;
import it.unibo.PokeRogue.ability.AbilitySituationChecks;
public record Ability(
		AbilitySituationChecks situationChecks,
		JSONObject effect
		){}
