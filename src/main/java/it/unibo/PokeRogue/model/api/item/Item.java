package it.unibo.pokerogue.model.api.item;

import java.util.Optional;
import org.json.JSONObject;

/**
 * Represents the blueprint for an item, containing all static properties
 * needed to create an {@link Item} instance.
 * <p>
 * This is an immutable data holder used by the {@link ItemFactory}.
 */
public record Item(
        int id,
        String name,
        String type,
        String description,
        int price,
        String rarity,
        String category,
        double captureRate,
        Optional<JSONObject> effect) {
}
