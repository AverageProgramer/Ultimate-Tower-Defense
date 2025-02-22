package com.averagegames.ultimatetowerdefense.characters.enemies;

import static com.averagegames.ultimatetowerdefense.characters.towers.Tower.LIST_OF_ACTIVE_TOWERS;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.tools.assets.ImageLoader;
import com.averagegames.ultimatetowerdefense.tools.animation.TranslationHandler;
import javafx.application.Platform;
import lombok.AccessLevel;
import org.jetbrains.annotations.*;

import com.averagegames.ultimatetowerdefense.maps.Path;
import com.averagegames.ultimatetowerdefense.maps.Position;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The {@link Enemy} class serves as a {@code super} class to all in-game enemies.
 * Anything that extends this class is considered an {@link Enemy} and can perform several actions including following specific {@link Path}s and {@code attacking}.
 * @implNote {@link Enemy} subclasses will need to set the attributes within the {@link Enemy} class manually to be properly customized.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
public abstract class Enemy {

    /**
     * A {@link List} containing every active {@link Enemy} in a game.
     */
    public static final List<@NotNull Enemy> LIST_OF_ACTIVE_ENEMIES;

    /**
     * The {@link Enemy}'s parent {@link Group}.
     */
    @NotNull
    @Accessors(makeFinal = true) @Setter
    private Group parent;

    /**
     * The {@link Enemy}'s {@link Image} loaded using an {@link ImageLoader}.
     */
    @NotNull
    private final ImageLoader loadedEnemy;

    /**
     * The {@link Enemy}'s {@link Image}.
     */
    @NotNull
    protected Image image;

    /**
     * The {@link Enemy}'s {@link Type}.
     */
    @NotNull
    @Accessors(makeFinal = true) @Getter
    protected Type type;

    /**
     * The {@link Enemy}'s current {@code health}.
     */
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED) @Getter
    private int health;

    /**
     * The {@code damage} the {@link Enemy} can do during an {@code attack}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    protected int damage;

    /**
     * The {@link Enemy}'s speed in pixels per second.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    protected int speed;

    /**
     * The {@link Path} the {@link Enemy} will follow.
     */
    @NotNull
    @Accessors(makeFinal = true) @Setter
    private Path pathing;

    /**
     * A {@link Thread} that is responsible for handling all {@link Enemy} movement.
     */
    @NotNull
    private Thread movementThread;

    /**
     * A {@link Thread} that is responsible for handling all {@link Enemy} attacks.
     */
    @NotNull
    private Thread attackThread;

    static {

        // Initializes the list containing every active enemy.
        LIST_OF_ACTIVE_ENEMIES = Collections.synchronizedList(new ArrayList<>());
    }

    {

        // Initializes the enemy's parent to a default group.
        this.parent = new Group();

        // Initializes the enemy's image to a default, null image.

        this.loadedEnemy = new ImageLoader();
        this.image = new Image(InputStream.nullInputStream());

        // Initializes the enemy's type to regular by default.
        this.type = Type.REGULAR;

        // Initializes the enemy's pathing to a path with 0 positions.
        this.pathing = new Path(new Position[0]);

        // Initializes the threads that the enemy will use to move and attack.

        this.movementThread = new Thread(() -> {
            // This thread does nothing by default.
        });
        this.attackThread = new Thread(() -> {
            // This thread does nothing by default.
        });
    }

    /**
     * Adds a given amount to the {@link Enemy}'s current {@code health}.
     * @param amount damage the amount to add to the {@link Enemy}'s {@code health}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void heal(@Range(from = 0, to = Integer.MAX_VALUE) final int amount) {

        // Performs the enemy's on healed action.
        // This method is unique to each individual inheritor of the enemy class.
        this.onHeal();

        // Adds the given amount to the enemy's health.
        this.health += amount;
    }

    /**
     * Removes a given amount from the {@link Enemy}'s current {@code health}.
     * @param damage the amount to remove from the {@link Enemy}'s {@code health}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void damage(@Range(from = 0, to = Integer.MAX_VALUE) final int damage) {

        // Performs the enemy's on damaged action.
        // This method is unique to each individual inheritor of the enemy class.
        this.onDamaged();

        // Removes the given amount from the enemy's health.
        this.health -= damage;

        // Determines whether the enemy has any health remaining.
        if (this.health <= 0) {

            // Removes the enemy from its parent group.
            Platform.runLater(this::eliminate);
        }
    }

    /**
     * Sets the {@link Enemy}'s {@link Position} to a newly given {@link Position}.
     * @param position the new {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void setPosition(@NotNull final Position position) {

        // Updates the enemy's x and y coordinates to the given position's x and y coordinates.

        this.loadedEnemy.setX(position.x());
        this.loadedEnemy.setY(position.y());
    }

    /**
     * Gets the {@link Position} the {@link Enemy} is currently at.
     * @return the {@link Enemy}'s current {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    @Contract(" -> new")
    public final @NotNull Position getPosition() {

        // Returns the enemy's current position.
        return new Position(this.loadedEnemy.getCurrentX(), this.loadedEnemy.getCurrentX());
    }

    /**
     * Determines whether the {@link Enemy} intersects the given {@link Node} at any point.
     * @param node the {@link Node} to be checked.
     * @return {@code true} if the {@link Enemy} intersects the {@link Node}, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    public final boolean isInRange(@NotNull final Node node) {

        // Returns whether the enemy intersects the given node.
        return this.loadedEnemy.intersects(node.getLayoutBounds());
    }

    /**
     * Adds the {@link Enemy} to its {@code parent} {@link Group} at a set {@link Position}.
     * The {@code parent} {@link Group} and spawn {@link Position} will need to be set prior to calling this method.
     * @since Ultimate Tower Defense 1.0
     */
    public final void spawn() {

        // Determines whether the enemy was already placed on to its parent group.
        if (this.parent.getChildren().contains(this.loadedEnemy)) {

            // Prevents the enemy from being spawned more than once.
            return;
        }

        // Performs the enemy's spawn action.
        // This method is unique to each individual inheritor of the enemy class.
        this.onSpawn();

        // Adds the enemy to the list containing every active enemy.
        LIST_OF_ACTIVE_ENEMIES.add(this);

        // Loads the enemy's image.
        this.loadedEnemy.setImage(this.image);

        // Adds the enemy to the enemy's parent group.
        this.parent.getChildren().add(this.loadedEnemy);
    }

    /**
     * Gets whether the {@link Enemy} is alive and still a member of its parent {@link Group}.
     * @return {@code true} if the {@link Enemy} is alive, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    public final boolean isAlive() {

        // Returns whether the enemy is alive or not.
        return this.parent.getChildren().contains(this.loadedEnemy);
    }

    /**
     * Begins moving the {@link Enemy} along a set {@link Path}.
     * The {@link Path} will need to be set prior to calling this method.
     * This method runs using separate {@link Thread}s and does not {@code block} the {@link Thread} in which it was called.
     * @since Ultimate Tower Defense 1.0
     */
    @SuppressWarnings("all")
    public final void startMoving() {

        // Interrupts the thread controlling enemy movement so that the new animation can override the old animation if there was one.
        this.movementThread.interrupt();

        // Creates a new thread that will handle enemy movement.
        this.movementThread = new Thread(() -> {

            // Creates a new animation that will control the enemy's movement along a given path.
            var animation = new TranslationHandler();

            // Sets up the animation, setting the node to control to the enemy's image, and setting the speed to use to the enemy's speed.

            animation.setNode(this.loadedEnemy);
            animation.setSpeed(this.speed);

            // A loop that will iterate through every position on the given path.
            for (var position : this.pathing.positions()) {

                // Sets the animation's destination to the current position in the loop.
                animation.setDestination(position);

                // Starts the animation.
                animation.start();

                // Allows the loop to be broken out of if the animation is interrupted.
                try {

                    // Causes the current thread to wait until the enemy's animation is finished.
                    animation.waitForFinish();
                } catch (InterruptedException ex) {

                    // Stops the enemy's animation.
                    animation.stop();

                    // Finishes the method if the animation is forcefully interrupted.
                    // This will most likely only happen when the enemy despawns.
                    return;
                }
            }
        });

        // Starts the thread so that the enemy can move along its set path.
        this.movementThread.start();
    }

    /**
     * Stops moving the {@link Enemy} on its current {@link Path}.
     * The {@link Enemy}'s movement {@link Thread} will be interrupted when calling this method.
     * @since Ultimate Tower Defense 1.0
     */
    public final void stopMoving() {

        // Interrupts the thread responsible for all enemy movement.
        this.movementThread.interrupt();
    }

    /**
     * Gets the {@link Tower} that the {@link Enemy} is most likely to {@code attack}.
     * @param towers an array of {@link Tower}s to choose from.
     * @return the {@link Tower} to {@code attack}.
     * @since Ultimate Tower Defense 1.0
     */
    @Contract(pure = true)
    private @NotNull Tower getTarget(@NotNull final Tower[] towers) {
        return LIST_OF_ACTIVE_TOWERS.getFirst();
    }

    /**
     * Determines whether the {@link Enemy} can {@code attack} the given {@link Tower}.
     * This is a helper method.
     * @param tower the {@link Tower} to {@code attack}.
     * @since Ultimate Tower Defense 1.0
     */
    private boolean canAttack(@NotNull final Tower tower) {

        // Returns whether the given tower is within the enemy's range.
        return tower.isInRange(this.loadedEnemy);
    }

    /**
     * Allows the {@link Enemy} to attack a {@link Tower} that is in {@code range}.
     * This method runs using separate {@link Thread}s and does not {@code block} the {@link Thread} in which it was called.
     * @since Ultimate Tower Defense 1.0
     */
    public final void startAttacking() {

        // Creates a new thread that will handle enemy attacks.
        this.attackThread = new Thread(() -> {
            // Enemy attacking has not yet been implemented.
        });

        // Starts the thread so that the enemy can attack towers.
        this.attackThread.start();
    }

    /**
     * Stops all attacks the {@link Enemy} may be performing.
     * The {@link Enemy}'s attacking {@link Thread} will be interrupted when calling this method.
     * @since Ultimate Tower Defense 1.0
     */
    public final void stopAttacking() {

        // Interrupts the thread responsible for all enemy attacks.
        this.attackThread.interrupt();
    }

    /**
     * Removes the {@link Enemy} from its parent {@link Group}.
     * @since Ultimate Tower Defense 1.0
     */
    public synchronized final void eliminate() {

        // Determines whether the enemy was already eliminated from its parent group.
        if (!this.parent.getChildren().contains(this.loadedEnemy)) {

            // Prevents the enemy from being eliminated more than once.
            return;
        }

        // Performs the enemy's death action.
        // This method is unique to each individual inheritor of the enemy class.
        this.onDeath();

        // Removes the enemy from its parent group.
        this.parent.getChildren().remove(this.loadedEnemy);

        // Interrupts the threads controlling enemy actions.
        // This will cause an exception to be thrown in both threads which will break out of the loop controlling the enemy.

        this.stopMoving();
        this.stopAttacking();

        // Removes the enemy from the list containing every active enemy.
        LIST_OF_ACTIVE_ENEMIES.remove(this);
    }

    /**
     * An action performed whenever an {@link Enemy} is {@code spawned}.
     * By default, this method does nothing.
     * @since Ultimate Tower Defense 1.0
     */
    protected void onSpawn() {
        // This method can be overridden by a subclass so that each individual enemy can have unique action to do when spawned.
    }

    /**
     * An action performed whenever an {@link Enemy} is {@code healed}.
     * By default, this method does nothing.
     * @since Ultimate Tower Defense 1.0
     */
    protected void onHeal() {
        // This method can be overridden by a subclass so that each individual enemy can have unique action to do when healed.
    }

    /**
     * An action performed whenever an {@link Enemy} takes {@code damage}.
     * By default, this method does nothing.
     * @since Ultimate Tower Defense 1.0
     */
    protected void onDamaged() {
        // This method can be overridden by a subclass so that each individual enemy can have unique action to do when damaged.
    }

    /**
     * An action performed whenever an {@link Enemy} is {@code eliminated}.
     * By default, this method does nothing.
     * @since Ultimate Tower Defense 1.0
     */
    protected void onDeath() {
        // This method can be overridden by a subclass so that each individual enemy can have unique action to do when eliminated.
    }

    /**
     * An action performed whenever an {@link Enemy} is {@code attacking}.
     * By default, this method does nothing.
     * @param tower the {@link Tower} to attack.
     * @throws InterruptedException when the {@link Enemy} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    protected void attack(@NotNull final Tower tower) throws InterruptedException {
        // This method can be overridden by a subclass so that each individual enemy can have a unique attack.
    }

    /**
     * An action performed whenever an {@link Enemy} is using a {@code special ability}.
     * By default, this method does nothing.
     * @throws InterruptedException when the {@link Enemy} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    protected void special(@NotNull final Tower tower) throws InterruptedException {
        // This method can be overridden by a subclass so that each individual enemy can have a unique special ability.
    }
}
