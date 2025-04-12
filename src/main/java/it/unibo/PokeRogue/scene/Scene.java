package it.unibo.PokeRogue.scene;

public interface Scene {

void initGpraphicElements();

void initStatus();

void updateGraphic();

void updateStatus(String inputKey);

}