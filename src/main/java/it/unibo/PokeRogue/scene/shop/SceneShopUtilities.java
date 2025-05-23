package it.unibo.PokeRogue.scene.shop;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.effectParser.EffectParser;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.items.Item;
import it.unibo.PokeRogue.items.ItemFactoryImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.scene.shop.enums.SceneShopEnum;
import it.unibo.PokeRogue.scene.shop.enums.SceneShopStatusEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;

public class SceneShopUtilities {
    private static final String QUESTION_MARK_STRING = "???";
    private static final List<Item> shopItems = new ArrayList<>();
    private static final Integer PRICY_ITEMS_SIZE = 3;
    private static final Integer FREE_ITEMS_SIZE = 3;
    private static final String FIRST_PANEL = "firstPanel";

    public SceneShopUtilities() {

    }

    public static String getPokemonNameAt(final Trainer trainer, final int position) {
        return trainer.getPokemon(position)
                .map(Pokemon::getName)
                .orElse(QUESTION_MARK_STRING);
    }

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

    public static void initShopItems(ItemFactoryImpl itemFactory) {
        shopItems.clear();
        for (int i = 0; i < PRICY_ITEMS_SIZE; i++) {
            shopItems.add(itemFactory.randomItem());
        }
        for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
            shopItems.add(itemFactory.randomItem());
        }
    }

    public static Item getShopItems(int index) {
        return shopItems.get(index);
    }

    public static void updateItemDescription(final Map<Integer, GraphicElementImpl> sceneGraphicElements, Item item) {
        sceneGraphicElements.put(SceneShopEnum.ITEM_DESCRIPTION_TEXT.value(),
                new TextElementImpl(FIRST_PANEL,
                        item.getDescription(),
                        Color.BLACK, 0.05,
                        0.35,
                        0.85));

    }

    public static void updateItemsText(final Map<Integer, GraphicElementImpl> sceneGraphicElements) {
        
        for (int i = 0; i < PRICY_ITEMS_SIZE; i++) {
            Item item = SceneShopUtilities.getShopItems(i);

            double xPosition = 0.14 + (i * 0.29);

            sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_1_NAME_TEXT.value() + i,
                    new TextElementImpl(FIRST_PANEL,
                            item.getName(),
                            Color.BLACK, 0.055,
                            xPosition, 0.12));
            sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_1_PRICE_TEXT.value() + i,
                    new TextElementImpl(FIRST_PANEL,
                            String.valueOf(item.getPrice()),
                            Color.BLACK, 0.05,
                            xPosition, 0.17));
        }
        for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
            Item item = SceneShopUtilities.getShopItems(PRICY_ITEMS_SIZE + i);

            double xPosition = 0.14 + (i * 0.29);

            sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_1_NAME_TEXT.value() + i,
                    new TextElementImpl(FIRST_PANEL,
                            item.getName(),
                            Color.BLACK, 0.055,
                            xPosition, 0.35));
        }
    }

    public static void updatePlayerMoneyText(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
            final PlayerTrainerImpl playerTrainerInstance) {
        sceneGraphicElements.put(SceneShopEnum.PLAYER_MONEY_TEXT.value(),
                new TextElementImpl("firstPanel", "MONEY: " + playerTrainerInstance.getMoney(),
                        Color.BLACK,
                        0.04, 0.92, 0.04));

    }

}
