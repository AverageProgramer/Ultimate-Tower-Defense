package com.averagegames.ultimatetowerdefense.maps.tools;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.units.Unit;
import com.averagegames.ultimatetowerdefense.player.Player;
import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link MapBuilder} interface serves as an indicator that a class should be treated as a {@link MapBuilder}.
 * Any class implementing the {@link MapBuilder} interface will be responsible for loading game {@code assets} onto a given {@link Scene}.
 * @since Ultimate Tower Defense 1.0
 * @see Scene
 * @author Average Programer
 */
public interface MapBuilder {

    /**
     * A {@link Spawner} {@link MapBuilder}s can use for {@code spawning} an {@link Enemy}.
     */
    Spawner ENEMY_SPAWNER = new Spawner();

    /**
     * A {@link Base} {@link MapBuilder}s can use for {@code spawning} {@link Player} {@link Unit}s.
     */
    Base PLAYER_BASE = new Base();

    /**
     * Loads a new {@link MapBuilder} on a given {@link Scene}.
     * @param scene the {@link Scene} to add the {@link Scene} to.
     * @throws Exception when an {@link Exception} occurs.
     * @since Ultimate Tower Defense 1.0
     * @implSpec Any class implementing the {@link MapBuilder} interface is required to {@code override} this method.
     * @implNote Whenever the {@link MapBuilder} is built, all {@code assets} should be added to the {@link Scene}'s parent.
     */
    void load(@NotNull final Scene scene) throws Exception;

    /**
     * A task or set of tasks to be performed before loading the {@link MapBuilder}.
     * By default, this method does nothing.
     * @param scene a {@link Scene} to use.
     * @throws Exception when an {@link Exception} occurs.
     * @since Ultimate Tower Defense 1.0
     * @apiNote This method is only called automatically when the static {@link MapBuilder#loadMap(MapBuilder, Scene)} method is called.
     * @implSpec This method does not need to be overridden in implementations of the {@link MapBuilder} interface.
     */
    default void pre_load(@NotNull final Scene scene) throws Exception {
        // This method does nothing but can be optionally overridden in implementations to have a custom set of actions.
    }

    /**
     * A task or set of tasks to be performed after loading the {@link MapBuilder}.
     * By default, this method does nothing.
     * @param scene a {@link Scene} to use.
     * @throws Exception when an {@link Exception} occurs.
     * @since Ultimate Tower Defense 1.0
     * @apiNote This method is only called automatically when the static {@link MapBuilder#loadMap(MapBuilder, Scene)} method is called.
     * @implSpec This method does not need to be overridden in implementations of the {@link MapBuilder} interface.
     */
    default void post_load(@NotNull final Scene scene) throws Exception {
        // This method does nothing but can be optionally overridden in implementations to have a custom set of actions.
    }

    /**
     * Finishes the loading process by adding the {@link MapBuilder}'s uniquely oriented and placed {@code assets} to a given {@link Scene}.
     * The {@link MapBuilder#pre_load(Scene)} and {@link MapBuilder#post_load(Scene)} methods are also called when this method is invoked.
     * @param map the {@link MapBuilder} to be loaded.
     * @param scene the {@link Scene} to add the custom {@link MapBuilder} to.
     * @throws Exception when an {@link Exception} occurs.
     * @since Ultimate Tower Defense 1.0
     */
    static void loadMap(@NotNull final MapBuilder map, @NotNull final Scene scene) throws Exception {

        // Performs the implementation's pre-load action.
        map.pre_load(scene);

        // Loads the map on the given scene.
        map.load(scene);

        // Performs the implementation's post-load action.
        map.post_load(scene);
    }
}
