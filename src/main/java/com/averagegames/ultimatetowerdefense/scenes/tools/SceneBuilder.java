package com.averagegames.ultimatetowerdefense.scenes.tools;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link SceneBuilder} interface should be implemented by all custom-made {@code JavaFX} scenes.
 * Classes implementing can easily build and create their own unique {@link Scene} to be added to a {@link Stage}.
 * @since Ultimate Tower Defense 1.0
 * @see Stage
 * @see Scene
 * @author AverageProgramer
 */
public interface SceneBuilder {

    /**
     * A {@link SceneManager} that allows an implementation of the {@link SceneBuilder} interface to manage {@link Scene}s.
     */
    SceneManager MANAGER = new SceneManager();

    /**
     * Builds a new {@code JavaFX} {@link Scene}, unique to each implementation of the {@link SceneBuilder} interface, on a given {@link Stage}.
     * @param stage the {@link Stage} to add the {@link Scene} to.
     * @throws Exception when an {@link Exception} occurs.
     * @since Ultimate Tower Defense 1.0
     * @implSpec Any class implementing the {@link SceneBuilder} interface is required to {@code override} this method.
     * @implNote Whenever the {@link Scene} is built, it should be added to the given {@link Stage}.
     */
    void build(@NotNull final Stage stage) throws Exception;

    /**
     * A task or set of tasks to be performed before building the {@link Scene}.
     * By default, this method does nothing.
     * @param stage a {@link Stage} to use.
     * @throws Exception when an {@link Exception} occurs.
     * @since Ultimate Tower Defense 1.0
     * @apiNote This method is only called automatically when the static {@link SceneBuilder#loadBuild(SceneBuilder, Stage)} method is called.
     * @implSpec This method does not need to be overridden in implementations of the {@link SceneBuilder} interface.
     */
    default void pre_build(@NotNull final Stage stage) throws Exception {
        // This method does nothing but can be optionally overridden in implementations to have a custom set of actions.
    }

    /**
     * A task or set of tasks to be performed after building the {@link Scene}.
     * By default, this method does nothing.
     * @param stage a {@link Stage} to use.
     * @throws Exception when an {@link Exception} occurs.
     * @since Ultimate Tower Defense 1.0
     * @apiNote This method is only called automatically when the static {@link SceneBuilder#loadBuild(SceneBuilder, Stage)} method is called.
     * @implSpec This method does not need to be overridden in implementations of the {@link SceneBuilder} interface.
     */
    default void post_build(@NotNull final Stage stage) throws Exception {
        // This method does nothing but can be optionally overridden in implementations to have a custom set of actions.
    }

    /**
     * Finishes the build process by adding the {@link SceneBuilder}'s uniquely designed {@link Scene} to a given {@link Stage}.
     * The {@link SceneBuilder#pre_build(Stage)} and {@link SceneBuilder#post_build(Stage)} methods are also called when this method is invoked.
     * @param scene the {@link SceneBuilder} responsible for building the {@link Scene}.
     * @param stage the {@link Stage} to add the custom {@link Scene} to.
     * @throws Exception when an {@link Exception} occurs.
     * @since Ultimate Tower Defense 1.0
     */
    static void loadBuild(@NotNull final SceneBuilder scene, @NotNull final Stage stage) throws Exception {

        // Performs the implementation's pre-build action.
        scene.pre_build(stage);

        // Builds the scene on the given stage.
        scene.build(stage);

        // Performs the implementation's post-build action.
        scene.post_build(stage);
    }
}
