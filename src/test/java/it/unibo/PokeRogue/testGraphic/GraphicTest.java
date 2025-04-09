package it.unibo.PokeRogue.testGraphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.GraphicEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;

public class GraphicTest {
        public static void main(String[] args) {
                GraphicEngineImpl GE = new GraphicEngineImpl();
                Map<Integer, GraphicElementImpl> graphicElements = new LinkedHashMap<>();
                Map<String, PanelElementImpl> panelElements = new LinkedHashMap<>();

                panelElements.put("sfondo", new PanelElementImpl("", new OverlayLayout(null), 0, 0, 1, 1));

                panelElements.put("firstPanel", new PanelElementImpl("sfondo", new GridLayout(1, 2), 0, 0, 1, 1));
                panelElements.put("sinistra", new PanelElementImpl("firstPanel", new GridLayout(3, 1), 0, 0, 1, 1));
                panelElements.put("destra", new PanelElementImpl("firstPanel", new GridLayout(3, 1), 0, 0, 1, 1));

                graphicElements.put(0,
                                new TextElementImpl("sinistra", "prima colonna prima riga", Color.RED,
                                                new Font("Default", Font.PLAIN, 20), 0,
                                                0.1));

                graphicElements.put(1,
                                new TextElementImpl("sinistra", "prima colonna seconda riga", Color.RED,
                                                new Font("Default", Font.PLAIN, 20), 0,
                                                0.1));

                graphicElements.put(2,
                                new TextElementImpl("sinistra", "prima colonna terza riga", Color.RED,
                                                new Font("Default", Font.PLAIN, 20), 0,
                                                0.1));
                graphicElements.put(3,
                                new BoxElementImpl("destra", new Color(100, 200, 50), new Color(30, 30, 30), 2, 0.5,
                                                0.5,
                                                0.5, 0.1));

                graphicElements.put(4, new BackgroundElementImpl("destra",
                                Paths.get("src", "test", "java", "it", "unibo", "PokeRogue", "testGraphic",
                                                "testBg.jpg").toString()));
                graphicElements.put(5, new SpriteElementImpl("destra",
                                Paths.get("src", "test", "java", "it", "unibo", "PokeRogue", "testGraphic",
                                                "testSprite.jpg").toString(),
                                0, 0, 0.5, 0.5));
                graphicElements.put(6, new BackgroundElementImpl("sfondo",
                                Paths.get("src", "test", "java", "it", "unibo", "PokeRogue", "testGraphic",
                                                "testBg.jpg").toString()));

                GE.createPanels(panelElements);
                GE.drawScene(graphicElements);

        }
}
