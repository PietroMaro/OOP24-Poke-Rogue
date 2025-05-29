package it.unibo.PokeRogue.scene.shop;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.effectParser.EffectParser;
import it.unibo.PokeRogue.effectParser.EffectParserImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.items.Item;
import it.unibo.PokeRogue.items.ItemFactoryImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.scene.GraphicElementsRegistryImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the in-game shop scene where players can purchase and use items.
 * Handles user input, manages graphical elements, and controls item
 * transactions.
 */
public class SceneShop extends Scene {
    private static final String FREE_ITEM_1 = "FREE_ITEM_1_BUTTON";
    private static final String FREE_ITEM_3 = "FREE_ITEM_3_BUTTON";
    private static final String TEAM_LITTERAL = "TEAM_BUTTON";
    private static final String REROL_LITTERAL = "REROL_BUTTON";
    private static final String PRICY_ITEM_1 = "PRICY_ITEM_1_BUTTON";
    private static final String PRICY_ITEM_3 = "PRICY_ITEM_3_BUTTON";
    private static final String CHANGE_1 = "CHANGE_POKEMON_1_BUTTON";
    private static final String CHANGE_2 = "CHANGE_POKEMON_2_BUTTON";
    private static final String CHANGE_6 = "CHANGE_POKEMON_6_BUTTON";
    private static final String CHANGE_BACK = "CHANGE_POKEMON_BACK_BUTTON";
    private static final int CHANGE_POKEMON_INITIAL_POSITION = 200;
    private static final int REROL_COST = 50;
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final PlayerTrainerImpl playerTrainerInstance;
    private final GameEngineImpl gameEngineInstance;
    private final SceneShopView sceneShopView;
    private final ItemFactoryImpl itemFactoryInstance;
    private final EffectParser effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);
    @Setter
    private int newSelectedButton;
    @Setter
    private int currentSelectedButton;
    private GraphicElementsRegistry graphicElements;
    private final Map<String, Integer> graphicElementNameToInt;
    private boolean selectedItemForUse;
    private boolean buyedItem;
    private Item selectedUsableItem;

    /**
     * Constructs the SceneShop, initializes UI elements and internal state.
     *
     * @throws IOException               if UI elements cannot be loaded
     * @throws InstantiationException    if an object cannot be instantiated
     * @throws IllegalAccessException    if access to a class or method is illegal
     * @throws NoSuchMethodException     if a method is not found via reflection
     * @throws InvocationTargetException if a method throws an exception during
     *                                   reflection
     */
    public SceneShop() throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        this.loadGraphicElements("sceneShopElements.json");
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.graphicElements = this.getGraphicElements();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.itemFactoryInstance = new ItemFactoryImpl();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.initStatus();
        this.sceneShopView = new SceneShopView(currentSceneGraphicElements, this.graphicElements, allPanelsElements,
                itemFactoryInstance,
                currentSelectedButton, newSelectedButton, this,
                graphicElementNameToInt);
        this.initGraphicElements();
    }

    /**
     * Updates the scene state based on user input.
     * Handles navigation, item purchase and usage, team interactions.
     *
     * @param inputKey the key code representing user input
     * @throws IOException               if an I/O error occurs
     * @throws InstantiationException    if object instantiation fails
     * @throws IllegalAccessException    if access to a method is illegal
     * @throws InvocationTargetException if method invocation fails
     * @throws NoSuchMethodException     if method is not found
     */
    @Override
    public void updateStatus(final int inputKey) throws IOException,
            InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (this.newSelectedButton >= this.graphicElementNameToInt.get(FREE_ITEM_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(FREE_ITEM_3)) {
                    this.newSelectedButton += 3;
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(CHANGE_2)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(CHANGE_BACK)) {
                    this.newSelectedButton -= 1;
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(TEAM_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(FREE_ITEM_3);
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(REROL_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(FREE_ITEM_1);
                }
                break;

            case KeyEvent.VK_DOWN:
                if (this.newSelectedButton >= this.graphicElementNameToInt.get(PRICY_ITEM_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(PRICY_ITEM_3)) {
                    this.newSelectedButton -= 3;
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(CHANGE_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(CHANGE_6)) {
                    this.newSelectedButton += 1;
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(FREE_ITEM_3)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(TEAM_LITTERAL);
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(FREE_ITEM_1)
                        || this.newSelectedButton == this.graphicElementNameToInt.get("FREE_ITEM_2_BUTTON")) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(REROL_LITTERAL);
                }
                break;

            case KeyEvent.VK_LEFT:
                if (this.newSelectedButton != this.graphicElementNameToInt.get(FREE_ITEM_1)
                        && this.newSelectedButton != this.graphicElementNameToInt.get(PRICY_ITEM_1)
                        && this.newSelectedButton != this.graphicElementNameToInt.get(REROL_LITTERAL)
                        && this.newSelectedButton < this.graphicElementNameToInt.get(CHANGE_1)) {
                    this.newSelectedButton -= 1;
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (this.newSelectedButton != this.graphicElementNameToInt.get(FREE_ITEM_3)
                        && this.newSelectedButton != this.graphicElementNameToInt.get(PRICY_ITEM_3)
                        && this.newSelectedButton != this.graphicElementNameToInt.get(TEAM_LITTERAL)
                        && this.newSelectedButton < this.graphicElementNameToInt.get(CHANGE_1)) {
                    this.newSelectedButton += 1;
                }
                break;

            case KeyEvent.VK_ENTER:
                if (this.newSelectedButton == this.graphicElementNameToInt.get(TEAM_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(CHANGE_1);
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(CHANGE_BACK)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(PRICY_ITEM_1);
                    if (buyedItem) {
                        compensation(playerTrainerInstance);
                        buyedItem = false;
                    }
                    this.selectedItemForUse = false;
                    sceneShopView.updateGraphic(currentSelectedButton, newSelectedButton);
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(CHANGE_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(CHANGE_6)
                        && selectedItemForUse) {
                    this.initGraphicElements();
                    applyItemToPokemon(this.newSelectedButton - CHANGE_POKEMON_INITIAL_POSITION, playerTrainerInstance,
                            gameEngineInstance, effectParser);
                    this.newSelectedButton = this.graphicElementNameToInt.get(PRICY_ITEM_1);
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(PRICY_ITEM_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(PRICY_ITEM_3)) {
                    final Item item = SceneShopUtilities.getShopItems(this.newSelectedButton - 4);
                    if (playerTrainerInstance.getMoney() >= item.getPrice()) {
                        this.selectedItemForUse = true;
                        buyItem(playerTrainerInstance, item, sceneShopView, gameEngineInstance);
                        buyedItem = true;
                        this.newSelectedButton = this.graphicElementNameToInt.get(CHANGE_1);
                        sceneShopView.updateGraphic(currentSelectedButton, newSelectedButton);
                    }
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(FREE_ITEM_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(FREE_ITEM_3)) {
                    this.selectedItemForUse = true;
                    useOrHandleItem(playerTrainerInstance, gameEngineInstance,
                            SceneShopUtilities.getShopItems(this.newSelectedButton + 2));
                    buyedItem = false;
                    this.newSelectedButton = this.graphicElementNameToInt.get(CHANGE_1);
                    sceneShopView.updateGraphic(currentSelectedButton, newSelectedButton);
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(REROL_LITTERAL)) {
                    rerollShopItems(playerTrainerInstance, itemFactoryInstance);
                }
                break;
            default:
                break;
        }
    }

    private void initStatus() {
        this.currentSelectedButton = this.graphicElementNameToInt.get(PRICY_ITEM_1);
        this.newSelectedButton = this.graphicElementNameToInt.get(PRICY_ITEM_1);

    }

    private void initGraphicElements() throws IOException {
        this.sceneShopView.initGraphicElements(this.newSelectedButton);
    }

    private void buyItem(final PlayerTrainerImpl trainer, final Item item, final SceneShopView sceneShopView,
            final GameEngineImpl gameEngineInstance) throws InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, IOException {
        trainer.addMoney(-item.getPrice());
        SceneShopUtilities.updatePlayerMoneyText(currentSceneGraphicElements, trainer);
        useOrHandleItem(trainer, gameEngineInstance, item);
    }

    private void useOrHandleItem(final PlayerTrainerImpl trainer, final GameEngineImpl gameEngineInstance,
            final Item item) throws InstantiationException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, IOException {
        if ("Capture".equalsIgnoreCase(item.getType())) {
            final int countBall = trainer.getBall().get(item.getName());
            trainer.getBall().put(item.getName(), countBall + 1);
            gameEngineInstance.setScene("fight");
        } else if ("Valuable".equalsIgnoreCase(item.getType())) {
            final Optional<JSONObject> itemEffect = item.getEffect();
            this.effectParser.parseEffect(itemEffect.get(), trainer.getPokemon(0).get());
            gameEngineInstance.setScene("fight");
        } else if ("Healing".equalsIgnoreCase(item.getType())
                || "Boost".equalsIgnoreCase(item.getType()) || "PPRestore".equalsIgnoreCase(item.getType())) {
            this.selectedUsableItem = item;
        }
    }

    private void applyItemToPokemon(final int pokemonIndex, final PlayerTrainerImpl trainer,
            final GameEngineImpl gameEngineInstance, final EffectParser effectParser) throws InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {
        if (this.selectedUsableItem != null) {
            final Optional<Pokemon> selectedPokemon = trainer
                    .getPokemon(pokemonIndex);
            if (selectedPokemon.isPresent()) {
                final Pokemon pokemon = selectedPokemon.get();

                // Ottieni l'effetto dell'item
                final Optional<JSONObject> itemEffect = this.selectedUsableItem.getEffect();

                if (itemEffect.isPresent()) {
                    effectParser.parseEffect(itemEffect.get(), pokemon);
                }
                this.selectedUsableItem = null;
                gameEngineInstance.setScene("fight");
            }
        }
    }

    private void compensation(final PlayerTrainerImpl playerTrainerInstance) {
        playerTrainerInstance.addMoney(selectedUsableItem.getPrice());
        selectedUsableItem = null;
    }

    private void rerollShopItems(final PlayerTrainerImpl playerTrainerInstance,
            final ItemFactoryImpl itemFactoryInstance) {
        if (playerTrainerInstance.getMoney() >= REROL_COST) {
            playerTrainerInstance.addMoney(-REROL_COST);
            SceneShopUtilities.updatePlayerMoneyText(currentSceneGraphicElements, playerTrainerInstance);
            SceneShopUtilities.initShopItems(itemFactoryInstance);
            SceneShopUtilities.updateItemsText(currentSceneGraphicElements);
        }
    }

    /**
     * Updates the current graphics based on user interaction.
     *
     * @throws IOException if graphics update fails
     */
    @Override
    public void updateGraphic() throws IOException {
        this.sceneShopView.updateGraphic(this.currentSelectedButton, this.newSelectedButton);
        this.currentSelectedButton = this.newSelectedButton;
    }

    /**
     * Returns a copy of the map containing all panel elements.
     *
     * @return a new {@link LinkedHashMap} instance with the current panel elements,
     *         ensuring the original map remains unmodified.
     */
    @Override
    public Map<String, PanelElementImpl> getAllPanelsElements() {
        return new LinkedHashMap<>(allPanelsElements);
    }
}
