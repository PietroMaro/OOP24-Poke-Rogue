package it.unibo.PokeRogue.scene.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.items.Item;
import it.unibo.PokeRogue.items.ItemFactoryImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

/**
 * Utility class for the SceneShop, providing methods to initialize and update
 * shop items, Pokémon stats display, item descriptions, and player money.
 * This class maintains a static list of shop items used in the current session.
 */
public class SceneShopUtilities {
    /** Fallback string when a Pokémon is not available or identifiable. */
    private static final String QUESTION_MARK_STRING = "???";

    /** Internal list holding current session's shop items. */
    private static final List<Item> shopItems = new ArrayList<>();

    /** Number of pricy items available in the shop. */
    private static final Integer PRICY_ITEMS_SIZE = 3;

    /** Number of free items available in the shop. */
    private static final Integer FREE_ITEMS_SIZE = 3;

    /** Base ID for the name text element of the first pricy item. */
    private static final Integer PRICY_ITEM_1_NAME_POSITION = 111;

    /** Base ID for the price text element of the first pricy item. */
    private static final Integer PRICY_ITEM_1_PRICE_POSITION = 105;

    /** Base ID for the name text element of the first free item. */
    private static final Integer FREE_ITEM_1_NAME_POSITION = 108;

    /**
     * Constructor to prevent instantiation of utility class.
     */
    public SceneShopUtilities() {

    }

    /**
     * Retrieves the name of the Pokémon at a given position in the trainer's team.
     *
     * @param trainer  The trainer whose Pokémon list is queried.
     * @param position The index in the team.
     * @return The name of the Pokémon or "???" if not present.
     */
    public static String getPokemonNameAt(final Trainer trainer, final int position) {
        return trainer.getPokemon(position)
                .map(Pokemon::getName)
                .orElse(QUESTION_MARK_STRING);
    }

    /**
     * Builds a string representing the current and maximum HP of a Pokémon at a
     * specific team position.
     *
     * @param position              Index of the Pokémon in the team.
     * @param playerTrainerInstance The player trainer instance containing the
     *                              Pokémon team.
     * @return A string in the format "current / max", or "??? / ???" if
     *         unavailable.
     */
    public static String getPokemonLifeText(final int position, final PlayerTrainerImpl playerTrainerInstance) {
        final Optional<Pokemon> pokemonOpt = playerTrainerInstance.getPokemon(position);

        if (!pokemonOpt.isPresent()) {
            return "??? / ???";
        }

        final Pokemon pokemon = pokemonOpt.get();
        final Integer currentHp = pokemon.getActualStats().get("hp").getCurrentValue();
        final Integer maxHp = pokemon.getActualStats().get("hp").getCurrentMax();

        return currentHp + " / " + maxHp;
    }

    /**
     * Initializes the static shop item list with a random set of pricy and free
     * items.
     *
     * @param itemFactory Factory used to generate random items.
     */
    public static void initShopItems(ItemFactoryImpl itemFactory) {
        shopItems.clear();
        for (int i = 0; i < PRICY_ITEMS_SIZE; i++) {
            shopItems.add(itemFactory.randomItem());
        }
        for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
            shopItems.add(itemFactory.randomItem());
        }
    }

    /**
     * Retrieves an item from the internal shop item list by index.
     *
     * @param index Index of the item in the list.
     * @return The item at the specified index.
     */
    public static Item getShopItems(int index) {
        return shopItems.get(index);
    }

    /**
     * Updates the item description text in the UI with the description of a given
     * item.
     *
     * @param sceneGraphicElements Registry containing the UI elements for the
     *                             current scene.
     * @param item                 The item whose description should be displayed.
     */
    public static void updateItemDescription(final GraphicElementsRegistry sceneGraphicElements, Item item) {
        ((TextElementImpl) sceneGraphicElements.getByName("ITEM_DESCRIPTION_TEXT"))
                .setText(item.getDescription());
    }

    /**
     * Updates the names and prices of all shop items in the UI.
     * Also sets their X positions dynamically based on their index.
     *
     * @param sceneGraphicElements Registry containing the UI elements to be
     *                             updated.
     */
    public static void updateItemsText(final GraphicElementsRegistry sceneGraphicElements) {

        for (int i = 0; i < PRICY_ITEMS_SIZE; i++) {
            Item item = SceneShopUtilities.getShopItems(i);
            double xPosition = 0.15 + (i * 0.29);
            double xPositionPrice = 0.25 + (i * 0.29);

            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, PRICY_ITEM_1_NAME_POSITION + i,
                    TextElementImpl.class)
                    .setText(item.getName());
            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, PRICY_ITEM_1_NAME_POSITION + i,
                    TextElementImpl.class)
                    .setLeftX(xPosition);

            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, PRICY_ITEM_1_PRICE_POSITION + i,
                    TextElementImpl.class)
                    .setText(String.valueOf(item.getPrice()));
            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, PRICY_ITEM_1_PRICE_POSITION + i,
                    TextElementImpl.class)
                    .setLeftX(xPositionPrice);
        }
        for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
            Item item = SceneShopUtilities.getShopItems(FREE_ITEMS_SIZE + i);

            double xPosition = 0.18 + (i * 0.29);

            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, FREE_ITEM_1_NAME_POSITION + i,
                    TextElementImpl.class)
                    .setText(item.getName());
            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, FREE_ITEM_1_NAME_POSITION + i,
                    TextElementImpl.class)
                    .setLeftX(xPosition);
        }
    }

    /**
     * Updates the displayed amount of money the player has.
     *
     * @param sceneGraphicElements  Registry containing the UI elements for the
     *                              scene.
     * @param playerTrainerInstance The player whose money value is displayed.
     */
    public static void updatePlayerMoneyText(final GraphicElementsRegistry sceneGraphicElements,
            final PlayerTrainerImpl playerTrainerInstance) {
        ((TextElementImpl) sceneGraphicElements.getByName("PLAYER_MONEY_TEXT"))
                .setText("MONEY: " + playerTrainerInstance.getMoney());
    }

}
