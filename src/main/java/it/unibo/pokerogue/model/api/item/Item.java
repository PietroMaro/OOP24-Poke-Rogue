package it.unibo.pokerogue.model.api.item;

import java.util.Optional;
import org.json.JSONObject;

/**
 * Represents the blueprint for an item, containing all static properties
 * needed to create an {@link Item} instance.
 * This is an immutable data holder used by the {@link ItemFactory}.
 *
 * @param id          the unique identifier of the item
 * @param name        the name of the item
 * @param type        the type or classification of the item (e.g.,
 *                    "consumable", "equipment")
 * @param description a textual description of the item and its purpose
 * @param price       the base monetary value of the item
 * @param rarity      the rarity level of the item (e.g., "common", "rare",
 *                    "legendary")
 * @param category    the general category the item belongs to (e.g., "healing",
 *                    "combat", "utility")
 * @param captureRate the probability (between 0.0 and 1.0) of capturing an
 *                    entity using this item, if applicable
 * @param effect      an optional JSON object describing additional effects or
 *                    metadata associated with the item
 * 
 * @author Casadio Alex
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
