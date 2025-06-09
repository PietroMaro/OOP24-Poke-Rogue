package it.unibo.pokerogue.model.api;

import java.util.Map;

import it.unibo.pokerogue.model.impl.graphic.GraphicElementImpl;

/**
 * Interface representing a registry for managing graphic elements.
 * 
 * @author Maretti Pietro
 */
public interface GraphicElementsRegistry {

    /**
     * Retrieves a graphic element by its name.
     * 
     * @param name the name of the graphic element.
     * @return the graphic element associated with the given name, or null
     *         if none exists.
     */
    GraphicElementImpl getByName(String name);

    /**
     * Retrieves a graphic element by its integer ID.
     * 
     * @param id the integer ID of the graphic element.
     * @return the graphic element associated with the given ID, or null if
     *         none exists.
     */
    GraphicElementImpl getById(int id);

    /**
     * Adds or replaces a graphic element in the registry with the given ID.
     * 
     * @param id      the integer ID to associate with the graphic element.
     * @param element the graphic element to add or replace.
     */
    void put(int id, GraphicElementImpl element);

    /**
     * Removes a graphic element from the registry by its integer ID.
     * 
     * @param id the integer ID of the graphic element to remove.
     */
    void removeById(int id);

    /**
     * Removes a graphic element from the registry by its name.
     * 
     * @param name the name of the graphic element to remove.
     */
    void removeByName(String name);

    /**
     * Clears all graphic elements from the registry.
     */
    void clear();

    /**
     * Returns the map of all graphic elements keyed by their integer IDs.
     * 
     * @return the map of graphic elements.
     */
    Map<Integer, GraphicElementImpl> getElements();

    /**
     * Returns the map map of element names to their integer IDs.
     * 
     * @return the  map of element names to their integer IDs.
     */
    Map<String, Integer> getNameToId();

}
