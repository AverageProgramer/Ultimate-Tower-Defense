package com.averagegames.ultimatetowerdefense.characters.enemies;

import static com.averagegames.ultimatetowerdefense.game.data.Enemies.LIST_OF_ACTIVE_ENEMIES;

import java.io.InputStream;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import com.averagegames.ultimatetowerdefense.characters.enemies.util.Type;
import com.averagegames.ultimatetowerdefense.game.maps.elements.Path;
import com.averagegames.ultimatetowerdefense.game.maps.elements.Position;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.tools.development.TranslationHandler;
import com.averagegames.ultimatetowerdefense.tools.development.ImageLoader;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import lombok.AccessLevel;
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
     * The {@link Enemy}'s parent {@link Group}.
     */
    @Accessors(makeFinal = true) @Setter
    private Group parent;

    /**
     * The {@link Enemy}'s {@link Image} loaded using an {@link ImageLoader}.
     */
    private final ImageLoader loadedEnemy;

    /**
     * The {@link Enemy}'s {@link Image}.
     */
    @NotNull
    @Accessors(makeFinal = true) @Setter(value = AccessLevel.PROTECTED)
    protected Image image;

    /**
     * The {@link Enemy}'s {@link Type}.
     */
    @NotNull
    @Accessors(makeFinal = true) @Setter(value = AccessLevel.PROTECTED) @Getter
    protected Type type;

    /**
     * The {@link Enemy}'s current {@code health}.
     */
    @Accessors(makeFinal = true) @Setter(value = AccessLevel.PROTECTED) @Getter
    protected int health;

    /**
     * The {@code damage} the {@link Enemy} can do during an {@code attack}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Accessors(makeFinal = true) @Setter(value = AccessLevel.PROTECTED)
    protected int damage;

    /**
     * The {@link Enemy}'s speed in pixels per second.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Accessors(makeFinal = true) @Setter(value = AccessLevel.PROTECTED)
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
    private Thread movementThread;

    /**
     * A {@link Thread} that is responsible for handling all {@link Enemy} attacks.
     */
    private Thread attackThread;

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

        this.movementThread = new Thread();
        this.attackThread = new Thread();
    }

    /**
     * Adds a given amount to the {@link Enemy}'s current {@code health}.
     * @param amount damage the amount to add to the {@link Enemy}'s {@code health}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void heal(final int amount) {

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
    public final void dealDamage(final int damage) {

        // Performs the enemy's on damaged action.
        // This method is unique to each individual inheritor of the enemy class.
        this.onDamaged();

        // Removes the given amount from the enemy's health.
        this.health -= damage;
    }

    /**
     * Sets the {@link Enemy}'s {@link Position} to a newly given {@link Position}.
     * @param position the new {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void setPosition(@NotNull final Position position) {

        // Updates the image representing the enemy's position on the stage it is currently on, if any.

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
        return new Position(this.loadedEnemy.getX() + this.loadedEnemy.getTranslateX(), this.loadedEnemy.getY() + this.loadedEnemy.getTranslateY());
    }

    /**
     * Determines whether the {@link Enemy} intersects the given {@link Node} at any point.
     * @param node the {@link Node} to be checked.
     * @return {@code true} if the {@link Enemy} intersects the {@link Node}, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    public final boolean intersects(@NotNull final Node node) {

        // Returns whether the enemy intersects the given node.
        return this.loadedEnemy.intersects(node.getLayoutBounds());
    }

    /**
     * Adds the {@link Enemy} to its {@code parent} {@link Group} at a set {@link Position}.
     * The {@code parent} {@link Group} and spawn {@link Position} will need to be set prior to calling this method.
     * @since Ultimate Tower Defense 1.0
     */
    public final void spawn() {

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
     * Adds the {@link Enemy} to its {@code parent} {@link Group} at a set {@link Position}.
     * Any previously set parent {@link Group} will be overridden when this method is called.
     * @param parent the {@link Group} to add the {@link Enemy} to.
     * @since Ultimate Tower Defense 1.0
     */
    public final void spawn(@NotNull final Group parent) {

        // Sets the enemy's parent group to the newly given group.
        this.setParent(parent);

        // Spawns the enemy.
        this.spawn();
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
    public final void startMoving() {

        // Interrupts the thread controlling enemy movement so that the new animation can override the old animation if there was one.
        this.movementThread.interrupt();

        // Creates a new thread that will handle enemy movement and starts it.
        (this.movementThread = new Thread(() -> {

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
        })).start();
    }

    /**
     * Determines whether the {@link Enemy} can {@code attack} the given {@link Tower}.
     * This is a helper method.
     * @param tower the {@link Tower} to {@code attack}.
     * @since Ultimate Tower Defense 1.0
     */
    private boolean canAttack(@NotNull final Tower tower) {

        // Returns whether the given tower is within the enemy's range.
        return tower.intersects(this.loadedEnemy);
    }

    /**
     * Gets the {@link Tower} that the {@link Enemy} is most likely to {@code attack}.
     * This method requires complex validation and is written in {@code C++} as it is a much faster language.
     * @param towers an array of {@link Tower}s to choose from.
     * @return the {@link Tower} to {@code attack}.
     * @since Ultimate Tower Defense 1.0
     */
    private Tower getTarget(@NotNull final Tower[] towers) {
        return null;
    }

    /**
     * Allows the {@link Enemy} to {@code attack} {@link Tower}s that it encounters.
     * This method runs using separate {@link Thread}s and does not {@code block} the {@link Thread} in which it was called.
     * @since Ultimate Tower Defense 1.0
     */
    @NonBlocking
    public final void startAttacking() {

        // Creates a new thread that will handle enemy attacks and starts it.
        (this.attackThread = new Thread(() -> {
            // Enemy attacking has not yet been implemented.
        })).start();
    }

    /**
     * Removes the {@link Enemy} from its parent {@link Group}.
     * @since Ultimate Tower Defense 1.0
     */
    public synchronized final void eliminate() {

        // Performs the enemy's death action.
        // This method is unique to each individual inheritor of the enemy class.
        this.onDeath();

        // Removes the enemy from its parent group.
        this.parent.getChildren().remove(this.loadedEnemy);

        // Interrupts the threads controlling enemy actions.
        // This will cause an exception to be thrown in both threads which will break out of the loop controlling the enemy.

        this.movementThread.interrupt();
        this.attackThread.interrupt();

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
     * By default, this method does nothing.`
     * @throws InterruptedException when the {@link Enemy} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    protected void special(@NotNull final Tower tower) throws InterruptedException {
        // This method can be overridden by a subclass so that each individual enemy can have a unique special ability.
    }
}
