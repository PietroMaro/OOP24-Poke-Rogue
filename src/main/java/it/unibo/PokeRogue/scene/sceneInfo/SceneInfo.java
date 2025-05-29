package it.unibo.PokeRogue.scene.sceneInfo;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.scene.GraphicElementsRegistryImpl;
import it.unibo.PokeRogue.scene.Scene;
import lombok.Getter;

/**
 * Scene representing the "Info" screen of the game.
 * <p>
 * Manages the graphic elements specific to the info scene, handles input
 * events,
 * and coordinates updating the scene state and view.
 * Extends the abstract {@link Scene} class.
 */
public class SceneInfo extends Scene {

    /** Registry of the graphic elements currently displayed in this scene. */
    @Getter
    private final GraphicElementsRegistry currentSceneGraphicElements;

    /** Map of all panel elements keyed by their string identifiers. */
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;

    /**
     * View component responsible for rendering and initializing graphic elements of
     * this scene.
     */
    private final SceneInfoView sceneInfoView;

    /** Singleton instance of the main game engine. */
    private final GameEngineImpl gameEngineInstance;

    /**
     * Identifier of the button newly selected in the current input update cycle.
     */
    private int newSelectedButton;

    /** Map from graphic element names to their integer IDs. */
    private final Map<String, Integer> graphicElementNameToInt;

    /**
     * Constructs the Info Scene, loading graphic elements and initializing the
     * scene state.
     *
     * @throws IOException               if loading graphic elements from file fails
     * @throws InstantiationException    if reflective instantiation of elements
     *                                   fails
     * @throws IllegalAccessException    if reflective access is denied
     * @throws NoSuchMethodException     if expected constructor methods are missing
     * @throws InvocationTargetException if reflection invocation fails
     */
    public SceneInfo() throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        this.loadGraphicElements("sceneInfoElements.json");
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.initStatus();
        this.sceneInfoView = new SceneInfoView(this.getGraphicElements());
        this.initGraphicElements();
    }

    /**
     * Updates the scene state based on the provided key input.
     * <p>
     * Handles actions such as navigating back to the main scene when the
     * "BACK_BUTTON" is pressed.
     *
     * @param inputKey the integer code of the pressed key
     * @throws IOException               if an IO error occurs during update
     * @throws InstantiationException    if reflective instantiation fails during
     *                                   update
     * @throws IllegalAccessException    if reflective access is denied during
     *                                   update
     * @throws InvocationTargetException if reflective method invocation fails
     *                                   during update
     * @throws NoSuchMethodException     if required methods are missing during
     *                                   update
     */
    @Override
    public void updateStatus(final int inputKey) throws IOException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        switch (inputKey) {
            case KeyEvent.VK_ENTER:
                if (this.newSelectedButton == this.graphicElementNameToInt.get("BACK_BUTTON")) {
                    gameEngineInstance.setScene("main");
                }
                break;
			default:
				break;
        }
    }

    /**
     * Initializes the status of the scene, setting the default selected button.
     * Typically called during construction to set the initial input focus.
     */
    private void initStatus() {
        this.newSelectedButton = this.graphicElementNameToInt.get("BACK_BUTTON");
    }

    /**
     * Initializes the graphic elements of the scene by delegating to the
     * {@link SceneInfoView}.
     *
     * @throws IOException if graphic elements fail to initialize properly
     */
    public final void initGraphicElements() throws IOException {
        this.sceneInfoView.initGraphicElements(currentSceneGraphicElements, allPanelsElements);
    }

    /**
     * Updates the graphical display of the scene.
     * <p>
     * Currently not implemented for this scene.
     *
     * @throws IOException if an IO error occurs during graphical update
     */
    @Override
    public void updateGraphic() throws IOException {
        // No graphical update logic implemented yet
    }
}
