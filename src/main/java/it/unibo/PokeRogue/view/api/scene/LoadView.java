package it.unibo.pokerogue.view.api.scene;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface representing the view component for the Load scene of the
 * application.
 * 
 * The LoadView interface defines the behavior required to render
 * and update the graphical user interface related to loading saved game data.
 * This includes initialization of visual elements and displaying save slot
 * information in a paginated manner.
 * 
 */

public interface LoadView {

    /**
     * Initializes the graphic elements used in the scene load view.
     * It creates and registers the base panel elements required for the scene,
     * and loads additional elements from a JSON file.
     *
     * @param allPanelsElements    a map to store panel elements created for this
     *                             scene
     * @param sceneGraphicElements the registry to hold scene-specific graphic
     *                             elements
     * @param graphicElements      the global registry containing all available
     *                             graphic elements
     */
    public void initGraphicElements(final Map<String, PanelElementImpl> allPanelsElements,
            final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements) throws IOException;

    /**
     * Displays a paginated list of save game slots with their respective box sizes.
     * Updates the text elements in the scene to reflect save data or mark empty
     * slots.
     *
     * @param savesListStart       the index offset in the full save list to begin
     *                             displaying from
     * @param savesList            the list of available save file names
     * @param savingSystemInstance an instance of the saving system used to retrieve
     *                             save data
     * @param allPanelsElements    a map of panel elements in the scene (unused in
     *                             this method but passed in)
     * @param sceneGraphicElements the registry containing all scene elements,
     *                             including text fields to update
     */
    public void showSaves(final int savesListStart, final List<String> savesList,
            final SavingSystem savingSystemInstance, final Map<String, PanelElementImpl> allPanelsElements,
            final GraphicElementsRegistry sceneGraphicElements) throws IOException;

}
