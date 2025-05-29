package it.unibo.PokeRogue.scene.sceneSave;

import java.io.IOException;
import java.util.Map;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;

/**
 * Class responsible for managing the initialization and update views
 * of the save scene's graphical elements.
 * <p>
 * It coordinates the initialization of the scene's UI components and
 * updates the visual state when the user interacts with the save menu.
 */
public class SceneSaveView {
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private final SceneSaveInitView sceneSaveInitView;
    private final SceneSaveUpdateView sceneSaveUpdateView;
    private final GraphicElementsRegistry graphicElements;

    /**
     * Constructs the SceneSaveView.
     *
     * @param currentSceneGraphicElements the graphic elements registry for the
     *                                    current scene
     * @param graphicElements             the global or shared graphic elements
     *                                    registry
     * @param allPanelsElements           a map of panel elements to manage scene
     *                                    panels
     * @param currentSelectedButton       the ID of the initially selected button
     * 
     * @param newSelectedButton           the ID of the newly selected button
     */
    public SceneSaveView(final GraphicElementsRegistry currentSceneGraphicElements,
            final GraphicElementsRegistry graphicElements,
            final Map<String, PanelElementImpl> allPanelsElements, final int currentSelectedButton,
            final int newSelectedButton) {
        this.currentSceneGraphicElements = currentSceneGraphicElements;
        this.graphicElements = graphicElements;
        this.sceneSaveInitView = new SceneSaveInitView(this.currentSceneGraphicElements, this.graphicElements,
                allPanelsElements);
        this.sceneSaveUpdateView = new SceneSaveUpdateView(this.currentSceneGraphicElements,
                currentSelectedButton, newSelectedButton);
    }

    /**
     * Initializes the graphical elements for the save scene.
     *
     * @param currentSelectedButton the button ID to mark as selected initially
     * @throws IOException if an error occurs during loading of scene elements
     */
    public void initGraphicElements(final int currentSelectedButton) throws IOException {
        this.sceneSaveInitView.initGraphicElements(currentSelectedButton);
    }

    /**
     * Updates the graphical view to reflect changes such as button selection.
     *
     * @param newSelectedButton the button ID to mark as newly selected
     * @throws IOException if an error occurs during the update
     */
    public void updateGraphic(final int newSelectedButton) throws IOException {
        this.sceneSaveUpdateView.updateGraphic(newSelectedButton);
    }
}
