package com.averagegames.ultimatetowerdefense.maps;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.units.Unit;
import com.averagegames.ultimatetowerdefense.player.Player;
import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Map} interface serves as an indicator that a class should be treated as a {@link Map}.
 * Any class implementing the {@link Map} interface will be responsible for loading game {@code assets} onto a given {@link Scene}.
 * @since Ultimate Tower Defense 1.0
 * @see Scene
 * @author Average Programer
 */
public interface Map {

    /**
     * A {@link Spawner} {@link Map}s can use for {@code spawning} an {@link Enemy}.
     */
    Spawner ENEMY_SPAWNER = new Spawner();

    /**
     * A {@link Base} {@link Map}s can use for {@code spawning} {@link Player} {@link Unit}s.
     */
    Base PLAYER_BASE = new Base();

    /**
     * Loads a new {@link Map} on a given {@link Scene}.
     * @param scene the {@link Scene} to add the {@link Scene} to.
     * @throws Exception when an {@link Exception} occurs.
     * @implSpec Any class implementing the {@link Map} interface is required to {@code override} this method.
     * @implNote Whenever the {@link Map} is built, all {@code assets} should be added to the {@link Scene}'s parent.
     */
    void load(@NotNull final Scene scene) throws Exception;

    /**
     * A task or set of tasks to be performed before loading the {@link Map}.
     * By default, this method does nothing.
     * @param scene a {@link Scene} to use.
     * @throws Exception when an {@link Exception} occurs.
     * @apiNote This method is only called automatically when the static {@link Map#loadMap(Map, Scene)} method is called.
     * @implSpec This method does not need to be overridden in implementations of the {@link Map} interface.
     */
    default void pre_load(@NotNull final Scene scene) throws Exception {
        // This method does nothing but can be optionally overridden in implementations to have a custom set of actions.
    }

    /**
     * A task or set of tasks to be performed after loading the {@link Map}.
     * By default, this method does nothing.
     * @param scene a {@link Scene} to use.
     * @throws Exception when an {@link Exception} occurs.
     * @apiNote This method is only called automatically when the static {@link Map#loadMap(Map, Scene)} method is called.
     * @implSpec This method does not need to be overridden in implementations of the {@link Map} interface.
     */
    default void post_load(@NotNull final Scene scene) throws Exception {
        // This method does nothing but can be optionally overridden in implementations to have a custom set of actions.
    }

    /**
     * Finishes the loading process by adding the {@link Map}'s uniquely oriented and placed {@code assets} to a given {@link Scene}.
     * The {@link Map#pre_load(Scene)} and {@link Map#post_load(Scene)} methods are also called when this method is invoked.
     * @param map the {@link Map} to be loaded.
     * @param scene the {@link Scene} to add the custom {@link Map} to.
     * @throws Exception when an {@link Exception} occurs.
     */
    static void loadMap(@NotNull final Map map, @NotNull final Scene scene) throws Exception {

        // Performs the implementation's pre-load action.
        map.pre_load(scene);

        // Loads the map on the given scene.
        map.load(scene);

        // Performs the implementation's post-load action.
        map.post_load(scene);
    }
}
