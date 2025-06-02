package it.unibo.pokerogue.view.api.scene.save;

import java.io.IOException;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;

/**
 * Interface representing the view component responsible for updating the save
 * scene's graphics.
 * 
 */
public interface SaveUpdateView {

    /**
     * Updates the graphical selection to reflect the newly selected button.
     *
     * @param newSelectedButton           the ID of the button to be selected
     * @param currentSceneGraphicElements the registry holding the current scene's
     *                                    graphic elements
     */
    void updateGraphic(int newSelectedButton,
            GraphicElementsRegistry currentSceneGraphicElements)
            throws IOException;

}
