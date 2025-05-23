package it.unibo.PokeRogue.scene.shop;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.effectParser.EffectParser;
import it.unibo.PokeRogue.effectParser.EffectParserImpl;
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
    private Item selectedItemForUse = null;
    private static final List<Item> shopItems = new ArrayList<>();
    private static final Integer PRICY_ITEMS_SIZE = 3;
    private static final Integer FREE_ITEMS_SIZE = 3;
    private final EffectParser effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);

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

    public void buyItem(final PlayerTrainerImpl trainer, final Item item, final SceneShopUpdateView sceneShopUpdateView,
            final GameEngineImpl gameEngineInstance) {

        trainer.addMoney(-item.getPrice());
        sceneShopUpdateView.updatePlayerMoneyText();
        useOrHandleItem(trainer, gameEngineInstance, item);

    }

    public void getFreeItem(final PlayerTrainerImpl trainer, final GameEngineImpl gameEngineInstance, Item item) {
        useOrHandleItem(trainer, gameEngineInstance, item);
    }

    protected void useOrHandleItem(final PlayerTrainerImpl trainer, final GameEngineImpl gameEngineInstance,
            final Item item) {
        if (item.getType().equalsIgnoreCase("Capture")) {
            int countBall = trainer.getBall().get(item.getName());
            trainer.getBall().put(item.getName(), countBall + 1);
            gameEngineInstance.setScene("fight");
        } else if (item.getType().equalsIgnoreCase("Valuable")) {
            System.out.println("entrato ");
            Optional<JSONObject> itemEffect = item.getEffect();
            this.effectParser.parseEffect(itemEffect.get(), trainer.getPokemon(0).get());
            gameEngineInstance.setScene("fight");
        } else if (item.getType().equalsIgnoreCase("Healing")
                || item.getType().equalsIgnoreCase("Boost") || item.getType().equalsIgnoreCase("PPRestore")) {
            this.selectedItemForUse = item;
        } else {
            System.out.println("ERR: item not recognized!");
        }
    }

    public void applyItemToPokemon(final int pokemonIndex, final PlayerTrainerImpl trainer,
            final GameEngineImpl gameEngineInstance) {
        if (this.selectedItemForUse != null) {
            Optional<it.unibo.PokeRogue.pokemon.Pokemon> selectedPokemon = trainer
                    .getPokemon(pokemonIndex);
            if (selectedPokemon.isPresent()) {
                it.unibo.PokeRogue.pokemon.Pokemon pokemon = selectedPokemon.get();

                // Ottieni l'effetto dell'item
                Optional<JSONObject> itemEffect = this.selectedItemForUse.getEffect();

                if (itemEffect.isPresent()) {
                    // Applica l'effetto al Pokémon
                    this.effectParser.parseEffect(itemEffect.get(), pokemon);
                }

                this.selectedItemForUse = null; // Resetta l'item selezionato
                gameEngineInstance.setScene("fight");
            }
        }
    }

    public static void initShopItems(ItemFactoryImpl itemFactory) {
        List<Item> pricyItems = new ArrayList<>();
        List<Item> freeItems = new ArrayList<>();
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

    public void compensation(PlayerTrainerImpl playerTrainerInstance) {
        playerTrainerInstance.addMoney(selectedItemForUse.getPrice());
        selectedItemForUse = null;
    }

    public void rerollShopItems(PlayerTrainerImpl playerTrainerInstance, ItemFactoryImpl itemFactoryInstance) {
        if (playerTrainerInstance.getMoney() >= 50) {
            playerTrainerInstance.addMoney(-50);
            initShopItems(itemFactoryInstance);
            updateItemsText();
        }
    }

    private void updateItemDescription(final int newSelectedButton,
            final Map<Integer, GraphicElementImpl> sceneGraphicElements) {
        Item item = null;
        sceneGraphicElements.remove(SceneShopEnum.ITEM_DESCRIPTION_TEXT.value());
        if (newSelectedButton >= SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value() &&
                newSelectedButton <= SceneShopStatusEnum.FREE_ITEM_3_BUTTON
                        .value()) {
            item = getShopItems(newSelectedButton + 2);
        } else if (newSelectedButton >= SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value() &&
                newSelectedButton <= SceneShopStatusEnum.PRICY_ITEM_3_BUTTON
                        .value()) {
            item = getShopItems(newSelectedButton - 4);
        }
        sceneGraphicElements.put(SceneShopEnum.ITEM_DESCRIPTION_TEXT.value(),
                new TextElementImpl("firstPanel",
                        item.getDescription(),
                        Color.BLACK, 0.05,
                        0.35,
                        0.85));

    }

}
