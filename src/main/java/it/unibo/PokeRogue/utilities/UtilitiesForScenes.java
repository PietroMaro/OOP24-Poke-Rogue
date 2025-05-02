package it.unibo.PokeRogue.utilities;

public interface UtilitiesForScenes {
    String getPathString(final String directory, final String fileName);

    void setButtonStatus(final int buttonCode, final boolean status);

    String capitalizeFirst(final String str);
}
