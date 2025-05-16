package it.unibo.PokeRogue.items;

import org.json.JSONObject;

public record ItemBlueprint(
        int id,
        String name,
        String type,
        String description,
        int price,
        String rarity,
        String category,
        double captureRate,
        JSONObject effect) {
}