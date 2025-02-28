package com.averagegames.ultimatetowerdefense.tools.animation;

import lombok.Getter;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Nullable;

import com.averagegames.ultimatetowerdefense.maps.Position;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import lombok.Setter;

/**
 * The {@link TranslationHandler} class serves as a way to move a given {@link Node}.
 * Unlike animations methods provided in the {@code JavaFX} library, which run on a time based system, the {@link TranslationHandler} class runs using a set {@code speed}.
 * No matter how far away the provided {@link Position} is, the given {@link Node} will always move at a constant {@code speed}.
 * @since Ultimate Tower Defense 1.0
 * @see Node
 * @see TranslateTransition
 * @author AverageProgramer
 */
public class TranslationHandler {

    /**
     * The {@link Node}'s animation.
     */
    private TranslateTransition animation;

    /**
     * The {@link Node} that is to be moved during animation.
     */
    @Nullable
    @Setter @Getter(onMethod_={@SuppressWarnings("unused")})
    private Node node;

    /**
     * The {@code speed} in pixels per second that the given {@link Node} should travel at.
     */
    @Setter @Getter(onMethod_={@SuppressWarnings("unused")})
    private int speed;

    /**
     * The {@code destination} of the animation.
     */
    @Nullable
    @Setter @Getter(onMethod_={@SuppressWarnings("unused")})
    private Position destination;

    /**
     * A default, no args constructor for the {@link TranslationHandler} class.
     * @since Ultimate Tower Defense 1.0
     */
    public TranslationHandler() {

        // Initializes the animation's translator.
        this.animation = new TranslateTransition();

        // Initializes the animation's node to a default, null node.
        this.node = null;

        // Initializes the animation's destination to a default, null destination.
        this.destination = null;
    }

    /**
     * Calculates the {@code duration} that should be used when the {@link Node} is moving.
     * The duration is calculated so that no matter how far away the {@code destination} is, the {@link Node} will always move at the given {@code speed}.
     * This is a helper method.
     * @return the {@code duration} to use when moving.
     * @since Ultimate Tower Defense 1.0
     * @see Duration
     */
    private double calculateDuration() {

        // Calculates the amount of seconds the animation's duration should last and returns the value.
        return speed > 0 && this.node != null && this.destination != null ? Math.abs(Math.sqrt(Math.pow(this.destination.x() - (this.node.getTranslateX() + this.node.getLayoutBounds().getMinX()), 2) + Math.pow(this.destination.y() - (this.node.getTranslateY() + this.node.getLayoutBounds().getMinY()), 2)) / this.speed) : 0;
    }

    /**
     * Begins moving the given {@link Node} to the given {@code destination} at the given {@code speed}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void start() {

        // Sets the duration of the animation to a calculated amount of seconds.
        // The duration is based on the speed and will update accordingly based on the distance to the given destination so that the node will always be moving its given speed.
        this.animation = new TranslateTransition(Duration.seconds(this.calculateDuration()));

        // Sets the animation's node to the given node.
        this.animation.setNode(this.node);

        // Sets the interpolator of the animation to the linear mode so that the animation does not speed up and slow down while close to the start and ending positions of the animation
        this.animation.setInterpolator(Interpolator.LINEAR);

        // Determines if the destination's position is null.
        if (this.node != null &&  this.destination != null) {

            // A loop that will iterate until the node has been properly loaded.
            while (true) {

                // Determines whether the x and y variables are not equal to 0.
                // When the x and y variables are not equal to 0, the node is loaded.
                if (this.node.getLayoutBounds().getMinX() != 0 && this.node.getLayoutBounds().getMinY() != 0) {

                    // Breaks out of the loop as the node has now loaded.
                    break;
                }
            }

            // Sets the x and y coordinates that the node should travel to using the given destination.
            // The coordinates are relative to the window, not the node.

            this.animation.setToX(this.destination.x() - this.node.getLayoutBounds().getMinX());
            this.animation.setToY(this.destination.y() - this.node.getLayoutBounds().getMinY());
        }

        // Sets the event meant to happen when the animation is finished.
        this.animation.setOnFinished(e -> {

            // Synchronizes the animation so that it can properly notify itself.
            synchronized (this) {

                // Notifies the animation so that a thread being blocked by the "waitForFinish" method can continue.
                this.notify();
            }
        });

        // Starts the node's animation.
        this.animation.play();
    }

    /**
     * Pauses the animation, causing the {@link Node} to stop at its current {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void pause() {

        // Pauses the node's animation.
        this.animation.pause();
    }

    /**
     * Resumes the animation, causing the {@link Node} to continue moving from its current {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void resume() {

        // Resumes the node's animation.
        this.animation.play();
    }

    /**
     * Waits for the {@link Node} to reach it's {@code destination}.
     * @throws InterruptedException when the animation is forcefully stopped.
     * @since Ultimate Tower Defense 1.0
     */
    @Blocking
    public final void waitForFinish() throws InterruptedException {

        // Synchronizes the animation so that it can properly wait.
        synchronized (this) {

            // Causes the animation to wait until either the animation is finished or until the animation is forcefully stopped.
            this.wait();
        }
    }

    /**
     * Gets whether the {@link Node} has reached its {@code destination}.
     * @return the animation's current {@code state}.
     * @since Ultimate Tower Defense 1.0
     */
    @SuppressWarnings("unused")
    public final boolean isFinished() {

        // Returns whether the animation is finished.
        return this.node == null || this.destination == null || this.node.getLayoutX() + this.node.getTranslateX() == this.destination.x() && this.node.getLayoutY() + this.node.getTranslateY() == this.destination.y();
    }

    /**
     * Stops the {@link Node}'s movement.
     * @since Ultimate Tower Defense 1.0
     */
    public final void stop() {

        // Stops the node's animation.
        this.animation.stop();

        // Synchronizes the animation so that it can properly notify itself.
        synchronized (this) {

            // Notifies the animation so that a thread being blocked by the "waitForFinish" method can continue.
            this.notify();
        }
    }
}
