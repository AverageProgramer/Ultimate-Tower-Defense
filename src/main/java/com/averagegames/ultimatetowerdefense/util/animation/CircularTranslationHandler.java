package com.averagegames.ultimatetowerdefense.util.animation;

import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.util.development.Constant;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * The {@link CircularTranslationHandler} class serves as a unique type of {@link PathTransition}.
 * Similarly to the {@link TranslationHandler} class, any {@link Node}'s being animated using the {@link CircularTranslationHandler} move at a given {@code speed}.
 * As {@link Node}'s travel around their set {@link Circle} {@code paths}, they will also be rotated accordingly.
 * @since Ultimate Tower Defense 1.0
 * @see Node
 * @see PathTransition
 * @see RotateTransition
 * @author AverageProgramer
 */
public final class CircularTranslationHandler {

    /**
     * A {@link Constant} representing an {@code infinite} cycles for the {@link CircularTranslationHandler} to complete.
     */
    @Constant
    public static final int INFINITE = Animation.INDEFINITE;

    /**
     * The {@link Node}'s animation.
     */
    private PathTransition animation;

    /**
     * The {@link Node}'s rotation.
     */
    private RotateTransition rotation;

    /**
     * The {@link Node} that is to be moved during animation.
     */
    @Nullable
    @Setter @Getter
    private Node node;

    /**
     * The {@code speed} in pixels per second that the given {@link Node} should travel at.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Setter @Getter
    private double speed;

    /**
     * The amount of times the {@link Node} should complete a full revolution around the path.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Setter @Getter
    private int cycleCount;

    /**
     * The {@link Circle} {@code path} of the animation.
     */
    @Nullable
    @Setter @Getter
    private Circle path;

    /**
     * A default, no args constructor for the {@link CircularTranslationHandler} class.
     * @since Ultimate Tower Defense 1.0
     */
    public CircularTranslationHandler() {

        // Initializes the animation's translator.
        this.animation = new PathTransition();

        // Initializes the animation's rotation.
        this.rotation = new RotateTransition();

        // Initializes the animation's node to a default, null node.
        this.node = null;

        // Initializes the animation's circle path to a default, null circle.
        this.path = null;
    }

    /**
     * Calculates the {@code duration} that should be used when the {@link Node} is moving.
     * The duration is calculated so that no matter how long the circumference of the {@link Circle} {@code path} is, the {@link Node} will always move at the given {@code speed}.
     * This is a helper method.
     * @return the {@code duration} to use when moving.
     * @since Ultimate Tower Defense 1.0
     * @see Duration
     */
    private double calculateDuration() {

        // Calculates the amount of seconds the animation's duration should last and returns the value.
        return this.node != null && this.path != null ? (2 * Math.PI) / (this.speed / this.path.getRadius()) : 0;
    }

    /**
     * Begins moving the given {@link Node} around the given {@link Circle} {@code path} at the given {@code speed}.
     * @since Ultimate Tower Defense 1.0
     */
    public void start() {

        // Determines if the node and destination's position are null.
        if (this.node != null && this.path != null) {

            // Sets the duration and path of the animation to a calculated amount of seconds and the set path.
            // The duration is based on the speed and will update accordingly based on the circumference of the path so that the node will always be moving its given speed.
            this.animation = new PathTransition(Duration.seconds(this.calculateDuration()), this.path);

            // Sets the animation's node to the given node.
            this.animation.setNode(this.node);

            // Sets the duration of the rotation to a calculated amount of seconds.
            // The duration is based on the speed and will update accordingly based on the circumference of the path so that the node will always be moving its given speed.
            this.rotation = new RotateTransition(Duration.seconds(this.calculateDuration()));

            // Sets the animation's node to the given node.
            this.rotation.setNode(this.node);
        }

        // Sets the interpolator of the animation to the linear mode so that the animation does not speed up and slow down while close to the start and ending positions of the animation.
        this.animation.setInterpolator(Interpolator.LINEAR);

        // Sets the cycle count of the node's animation to the given cycle count.
        this.animation.setCycleCount(this.cycleCount);

        // Sets the event meant to happen when the animation is finished.
        this.animation.setOnFinished(e -> {

            // Synchronizes the animation so that it can properly notify itself.
            synchronized (this) {

                // Notifies the animation so that a thread being blocked by the "waitForFinish" method can continue.
                this.notify();
            }
        });

        // Sets the destination angle of the node's rotation to 360 degrees.
        this.rotation.setToAngle(360);

        // Sets the interpolator of the rotation to the linear mode so that the rotation does not speed up and slow down while close to the start and ending angles of the rotation.
        this.rotation.setInterpolator(Interpolator.LINEAR);

        // Sets the cycle count of the node's rotation to the given cycle count.
        this.rotation.setCycleCount(this.cycleCount);

        // Starts the node's animation.
        this.animation.playFromStart();

        // Starts the node's rotation.
        this.rotation.playFromStart();
    }

    /**
     * Pauses the animation, causing the {@link Node} to stop at its current {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    public void pause() {

        // Pauses the node's animation.
        this.animation.pause();

        // Pauses the node's rotation.
        this.rotation.pause();
    }

    /**
     * Resumes the animation, causing the {@link Node} to continue moving around it's {@link Circle} {@code path}.
     * @since Ultimate Tower Defense 1.0
     */
    public void resume() {

        // Resumes the node's animation.
        this.animation.play();

        // Resumes the node's rotation.
        this.rotation.play();
    }

    /**
     * Waits for the {@link Node} to complete one full revolution around it's {@link Circle} path.
     * This method will not finish if the cycle count is set to {@link CircularTranslationHandler#INFINITE}.
     * @throws InterruptedException when the animation is forcefully stopped.
     * @since Ultimate Tower Defense 1.0
     */
    @Blocking
    public void waitForFinish() throws InterruptedException {

        // Synchronizes the animation so that it can properly wait.
        synchronized (this) {

            // Causes the animation to wait until either the animation is finished or until the animation is forcefully stopped.
            this.wait();
        }
    }

    /**
     * Refreshes the {@link CircularTranslationHandler} so that any changes made during the {@link Node}'s movement can be updated.
     * @since Ultimate Tower Defense 1.0
     */
    public void refresh() {

        // Stops the node's animation.
        this.stop();

        // Restarts the node's animation.
        this.start();
    }

    /**
     * Stops the {@link Node}'s movement.
     * @since Ultimate Tower Defense 1.0
     */
    public void stop() {

        // Stops the node's animation.
        this.animation.stop();

        // Stops the node's rotation.
        this.rotation.stop();

        // Synchronizes the animation so that it can properly notify itself.
        synchronized (this) {

            // Notifies the animation so that a thread being blocked by the "waitForFinish" method can continue.
            this.notify();
        }
    }
}
