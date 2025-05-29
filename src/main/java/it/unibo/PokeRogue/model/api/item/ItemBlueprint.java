package it.unibo.pokerogue.model.api.item;

import org.json.JSONObject;

/**
 * Represents a blueprint for creating an in-game item.
 * <p>
 * An {@code ItemBlueprint} contains all the necessary properties
 * to define an item, including metadata such as type, rarity, and
 * an optional effect in JSON format.
 *
 * @param id          the unique identifier of the item
 * @param name        the name of the item
 * @param type        the type of the item (e.g., healing, capture)
 * @param description the textual description of the item
 * @param price       the price of the item in in-game currency
 * @param rarity      the rarity level of the item (e.g., common, rare)
 * @param category    the category of the item
 * @param captureRate the capture rate modifier associated with the item
 * @param effect      a JSON object describing the effect of the item
 */
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
