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

    /**
     * Holds the registry for the current scene's graphical elements.
     */
    @Getter
    private final GraphicElementsRegistry currentSceneGraphicElements;

    /**
     * Stores all panel elements used in the scene, mapped by their identifiers.
     */
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;

    /**
     * Singleton instance representing the current player.
     */
    private final PlayerTrainerImpl playerTrainerInstance;

    /**
     * Reference to the core game engine instance for scene transitions and game
     * state.
     */
    private final GameEngineImpl gameEngineInstance;

    /**
     * View component responsible for rendering and updating the shop interface.
     */
    private final SceneShopView sceneShopView;

    /**
     * Utility class providing logic related to item handling, prices, and effects.
     */
    private final SceneShopUtilities sceneShopUtilities;

    /**
     * Factory for creating new item instances for the shop.
     */
    private final ItemFactoryImpl itemFactoryInstance;

    /**
     * Effect parser used to interpret and apply JSON-based item effects.
     */
    private final EffectParser effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);

    /**
     * Index of the button the user is navigating to.
     */
    @Setter
    private int newSelectedButton;

    /**
     * Index of the currently selected button in the shop menu.
     */
    @Setter
    private int currentSelectedButton;

    /**
     * Global registry of graphic elements for the scene, shared across components.
     */
    private GraphicElementsRegistry graphicElements;

    /**
     * Mapping from graphic element names to their corresponding numeric IDs.
     */

    private final Map<String, Integer> graphicElementNameToInt;

	private final static String FREE_ITEM_1 = "FREE_ITEM_1_BUTTON";
	private final static String FREE_ITEM_3 = "FREE_ITEM_3_BUTTON";
	private final static String TEAM_LITTERAL = "TEAM_BUTTON";
	private final static String REROL_LITTERAL = "REROL_BUTTON";
	private final static String PRICY_ITEM_1 = "PRICY_ITEM_1_BUTTON";
	private final static String PRICY_ITEM_3 = "PRICY_ITEM_3_BUTTON";
 	private final static String CHANGE_1 = "CHANGE_POKEMON_1_BUTTON";
 	private final static String CHANGE_2 = "CHANGE_POKEMON_2_BUTTON";
 	private final static String CHANGE_6 = "CHANGE_POKEMON_6_BUTTON";
	private final static String CHANGE_BACK = "CHANGE_POKEMON_BACK_BUTTON";

    /**
     * Indicates whether a usable item has been selected for application.
     */
    private boolean selectedItemForUse;

    /**
     * Indicates whether an item was purchased but not yet confirmed (used for
     * compensation).
     */
    private boolean buyedItem;

    /**
     * Stores the currently selected usable item, if any, to be applied to a
     * Pokémon.
     */
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
                    sceneShopView.updateGraphic(currentSelectedButton,newSelectedButton);
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(CHANGE_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(CHANGE_6)
                        && selectedItemForUse) {
                    this.initGraphicElements();
                    applyItemToPokemon(this.newSelectedButton - 200, playerTrainerInstance,
                            gameEngineInstance, effectParser);
                    this.newSelectedButton = this.graphicElementNameToInt.get(PRICY_ITEM_1);
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(PRICY_ITEM_1) &&
                        this.newSelectedButton <= this.graphicElementNameToInt.get(PRICY_ITEM_3)) {
                    final Item item = SceneShopUtilities.getShopItems(this.newSelectedButton - 4);
                    if (playerTrainerInstance.getMoney() >= item.getPrice()) {
                        this.selectedItemForUse = true;
                        buyItem(playerTrainerInstance, item, sceneShopView, gameEngineInstance);
                        buyedItem = true;
                        this.newSelectedButton = this.graphicElementNameToInt.get(CHANGE_1);
                        sceneShopView.updateGraphic(currentSelectedButton,newSelectedButton);
                    }
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(FREE_ITEM_1) &&
                        this.newSelectedButton <= this.graphicElementNameToInt.get(FREE_ITEM_3)) {
                    this.selectedItemForUse = true;
                    useOrHandleItem(playerTrainerInstance, gameEngineInstance,
                            SceneShopUtilities.getShopItems(this.newSelectedButton + 2));
                    buyedItem = false;
                    this.newSelectedButton = this.graphicElementNameToInt.get(CHANGE_1);
                    sceneShopView.updateGraphic(currentSelectedButton,newSelectedButton);
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(REROL_LITTERAL)) {
                    rerollShopItems(playerTrainerInstance, itemFactoryInstance);
                }
                break;
			default:
				break;
        }
    }

    /**
     * Initializes the initial button selection state.
     */
    private void initStatus() {
        this.currentSelectedButton = this.graphicElementNameToInt.get(PRICY_ITEM_1);
        this.newSelectedButton = this.graphicElementNameToInt.get(PRICY_ITEM_1);

    }

    public final void initGraphicElements() throws IOException {
        this.sceneShopView.initGraphicElements(this.newSelectedButton);
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
     * Handles the logic for purchasing an item from the shop.
     *
     * @param trainer            the player buying the item
     * @param item               the item to be bought
     * @param sceneShopView      the view to update
     * @param gameEngineInstance game engine instance to update scene
     * @throws IOException               if an I/O error occurs
     * @throws InstantiationException    if instantiation fails
     * @throws IllegalAccessException    if illegal access occurs
     * @throws InvocationTargetException if method invocation fails
     * @throws NoSuchMethodException     if method is not found
     */
    public void buyItem(final PlayerTrainerImpl trainer, final Item item, final SceneShopView sceneShopView,
            final GameEngineImpl gameEngineInstance) throws InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, IOException {
        trainer.addMoney(-item.getPrice());
        SceneShopUtilities.updatePlayerMoneyText(currentSceneGraphicElements, trainer);
        useOrHandleItem(trainer, gameEngineInstance, item);
    }

    /**
     * Uses or handles the selected item based on its type.
     *
     * @param trainer            the player using the item
     * @param gameEngineInstance the game engine
     * @param item               the item to be used
     * @throws IOException               if an I/O error occurs
     * @throws InstantiationException    if instantiation fails
     * @throws IllegalAccessException    if illegal access occurs
     * @throws InvocationTargetException if method invocation fails
     * @throws NoSuchMethodException     if method is not found
     */
    protected void useOrHandleItem(final PlayerTrainerImpl trainer, final GameEngineImpl gameEngineInstance,
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
                || "Boost".equalsIgnoreCase(item.getType()) ||"PPRestore".equalsIgnoreCase(item.getType())) {
            this.selectedUsableItem = item;
        }
    }

    /**
     * Applies an item to a selected Pokémon.
     *
     * @param pokemonIndex       index of the Pokémon in the party
     * @param trainer            the player using the item
     * @param gameEngineInstance the game engine
     * @param effectParser       parser to apply item effects
     * @throws IOException               if an I/O error occurs
     * @throws InstantiationException    if instantiation fails
     * @throws IllegalAccessException    if illegal access occurs
     * @throws InvocationTargetException if method invocation fails
     * @throws NoSuchMethodException     if method is not found
     */
    public void applyItemToPokemon(final int pokemonIndex, final PlayerTrainerImpl trainer,
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

    public void compensation(final PlayerTrainerImpl playerTrainerInstance) {
        playerTrainerInstance.addMoney(selectedUsableItem.getPrice());
        selectedUsableItem = null;
    }

    public void rerollShopItems(final PlayerTrainerImpl playerTrainerInstance, final ItemFactoryImpl itemFactoryInstance) {
        if (playerTrainerInstance.getMoney() >= 50) {
            playerTrainerInstance.addMoney(-50);
            SceneShopUtilities.updatePlayerMoneyText(currentSceneGraphicElements, playerTrainerInstance);
            SceneShopUtilities.initShopItems(itemFactoryInstance);
            SceneShopUtilities.updateItemsText(currentSceneGraphicElements);
        }
    }
}
