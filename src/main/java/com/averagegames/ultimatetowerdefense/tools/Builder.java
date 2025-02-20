package com.averagegames.ultimatetowerdefense.tools;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

/**
 * The {@link Builder} interface should be implemented by all classes involved in the {@code JavaFX} start up process.
 * Classes implementing can easily build and create their own unique {@link Scene} to be added to a {@link Stage}.
 * @since Ultimate Tower Defense 1.0
 * @see Stage
 * @see Scene
 * @author AverageProgramer
 */
public interface Builder {

    /**
     * A {@link StrictHashMap} that custom-made {@link Scene}s can be added to alongside unique {@link Integer} identifiers.
     * It is not a requirement for {@link Scene}s to be added to this {@link StrictHashMap} but it is a useful tool for managing available and already built {@link Scene}s.
     */
    StrictHashMap<@NotNull Integer, @NotNull Scene> SCENE_MANAGER = new StrictHashMap<>(Collections.synchronizedMap(new StrictHashMap<>()));

    /**
     * Builds a new {@code JavaFX} {@link Scene}, unique to each implementation of the {@link Builder} interface, on a given {@link Stage}.
     * @param stage the {@link Stage} to add the {@link Scene} to.
     * @throws Exception when an {@link Exception} occurs.
     * @implSpec Any class implementing the {@link Builder} interface is required to {@code override} this method.
     * @implNote Whenever the {@link Scene} is built, it should be added to the given {@link Stage}.
     */
    void build(@NotNull final Stage stage) throws Exception;

    /**
     * A task or set of tasks to be performed before building the {@link Scene}.
     * By default, this method does nothing.
     * @param stage a {@link Stage} to use.
     * @throws Exception when an {@link Exception} occurs.
     * @apiNote This method is only called automatically when the static {@link Builder#loadBuild(Builder, Stage)} method is called.
     * @implSpec This method does not need to be overridden in implementations of the {@link Builder} interface.
     */
    default void pre_build(@NotNull final Stage stage) throws Exception {
        // This method does nothing but can be optionally overridden in implementations to have a custom set of actions.
    }

    /**
     * A task or set of tasks to be performed after building the {@link Scene}.
     * By default, this method does nothing.
     * @param stage a {@link Stage} to use.
     * @throws Exception when an {@link Exception} occurs.
     * @apiNote This method is only called automatically when the static {@link Builder#loadBuild(Builder, Stage)} method is called.
     * @implSpec This method does not need to be overridden in implementations of the {@link Builder} interface.
     */
    default void post_build(@NotNull final Stage stage) throws Exception {
        // This method does nothing but can be optionally overridden in implementations to have a custom set of actions.
    }

    /**
     * Builds an {@link Scene} containing an empty {@link Group} on the given {@link Stage}.
     * This is a helper method.
     * @param stage the {@link Stage} to add the {@link Scene} to.
     */
    private void defaultBuild(@NotNull final Stage stage) {

        // The default scene.
        Scene defaultScene = new Scene(new Group());

        // Adds the scene to the hashmap meant to contain custom-made scenes with the integer ID of -1.
        SCENE_MANAGER.put("default".hashCode(), defaultScene);

        // Sets the scene of the given stage to a scene containing an empty group.
        stage.setScene(defaultScene);
    }

    /**
     * Finishes the build process by adding the {@link Builder}'s uniquely designed {@link Scene} to a given {@link Stage}.
     * The {@link Builder#pre_build(Stage)} and {@link Builder#post_build(Stage)} methods are also called when this method is invoked.
     * @param toolkit the {@link Builder} responsible for building the {@link Scene}.
     * @param stage the {@link Stage} to add the custom {@link Scene} to.
     * @throws Exception when an {@link Exception} occurs.
     */
    static void loadBuild(@NotNull final Builder toolkit, @NotNull final Stage stage) throws Exception {

        // Performs the implementation's pre-build action.
        toolkit.pre_build(stage);

        // Stores the current, soon-to-be-previous, scene that is currently on the given stage.
        Scene previous = stage.getScene();

        // Builds the scene on the given stage.
        toolkit.build(stage);

        // Determines if the scene was actually added to the stage once it was finished building.
        if (stage.getScene() == previous) {

            // Builds the default scene on the given stage.
            toolkit.defaultBuild(stage);
        }

        // Performs the implementation's post-build action.
        toolkit.post_build(stage);
    }
}
