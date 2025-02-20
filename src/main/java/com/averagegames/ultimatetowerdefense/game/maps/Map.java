package com.averagegames.ultimatetowerdefense.game.maps;

import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Map} interface is implemented by each individual custom {@link Map} design.
 * It provides methods meant for loading and unloading individual {@link Map}s and adding them to a given {@link Scene}.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
public interface Map {

    /**
     * Loads the {@link Map} on a given {@link Scene}.
     * This method is unique to each implementation of the {@link Map} interface.
     * @param scene the {@link Scene} to load the {@link Map} on.
     * @throws Exception when an {@link Exception} occurs.
     * @implSpec Any class implementing the {@link Map} interface is required to {@code override} this method.
     * @implNote Whatever {@code elements} are being loaded to on the {@link Map} should be added to the given {@link Scene}.
     */
    void load(@NotNull final Scene scene) throws Exception;

    /**
     * Unloads the {@link Map} from a given {@link Scene}.
     * By default, this method clears every element from the given {@link Scene}.
     * @param scene the {@link Scene} to unload the {@link Map} from.
     * @throws Exception when an {@link Exception} occurs.
     * @implSpec This method does not need to be overridden in implementations of the {@link Map} interface.
     * @implNote Whatever {@code elements} were loaded to on the {@link Map} should be removed from the given {@link Scene}.
     */
    default void unload(@NotNull final Scene scene) throws Exception {

        // Clears every element off of the given scene.
        scene.getProperties().clear();
    }
}
