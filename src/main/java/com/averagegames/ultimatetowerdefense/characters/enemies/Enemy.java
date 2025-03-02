package com.averagegames.ultimatetowerdefense.characters.enemies;

import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.maps.Path;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.tools.animation.TranslationHandler;
import com.averagegames.ultimatetowerdefense.tools.assets.ImageLoader;
import com.averagegames.ultimatetowerdefense.tools.development.Specific;
import com.averagegames.ultimatetowerdefense.tools.development.SpecificAnnotation;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.averagegames.ultimatetowerdefense.characters.towers.Tower.LIST_OF_ACTIVE_TOWERS;

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
    @NotNull
    public static final List<@NotNull Enemy> LIST_OF_ACTIVE_ENEMIES = Collections.synchronizedList(new ArrayList<>());

    /**
     * The {@link Enemy}'s parent {@link Group}.
     */
    @Nullable
    @Accessors(makeFinal = true) @Setter
    @Getter
    private Group parent;

    /**
     * The {@link Enemy}'s {@link Image} loaded using an {@link ImageLoader}.
     */
    @NotNull
    private final ImageLoader loadedEnemy;

    /**
     * The {@link Enemy}'s {@link Image}.
     */
    @Nullable
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
     * The current {@link Path} the {@link Enemy} will follow.
     */
    @Nullable
    @Accessors(makeFinal = true) @Getter
    private Path pathing;

    /**
     * The entire {@link Path} and {@link Enemy} will follow.
     */
    @Nullable
    @Accessors(makeFinal = true) @Setter @Getter
    private Path referencePathing;

    /**
     * The current {@link Position} the {@link Enemy} is at along its {@link Path}.
     */
    @Accessors(makeFinal = true) @Setter @Getter
    private int positionIndex;

    /**
     * The money the {@code player} will receive when damaging the {@link Enemy}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Accessors(makeFinal = true) @Getter
    protected int income;

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

    {

        // Initializes the enemy's parent to a default, null group.
        this.parent = null;

        // Initializes the enemy's image to a default, null image.

        this.loadedEnemy = new ImageLoader();
        this.image = null;

        // Initializes the enemy's type to regular by default.
        this.type = Type.REGULAR;

        // Initializes the enemy's pathing to a default, null path.
        this.pathing = null;

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

        this.loadedEnemy.setX(position.x() - (this.image != null ? this.image.getWidth() / 2 : 0));
        this.loadedEnemy.setY(position.y() - (this.image != null ? this.image.getHeight() : 0));
    }

    /**
     * Gets the {@link Position} the {@link Enemy} is currently at.
     * @return the {@link Enemy}'s current {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    @Contract(" -> new")
    public final @NotNull Position getPosition() {

        // Returns the enemy's current position.
        return new Position(this.loadedEnemy.getCurrentX() + (this.image != null ? this.image.getWidth() / 2 : 0), this.loadedEnemy.getCurrentY() + (this.image != null ? this.image.getHeight() : 0));
    }

    /**
     * Sets the {@link Enemy}'s {@link Path} to a newly given {@link Path}
     * @param pathing the {@link Enemy}'s {@link Path} to follow.
     */
    public final void setPathing(@Nullable final Path pathing) {

        // Sets the enemy's pathing to the given pathing.
        this.pathing = pathing;

        // Sets the enemy's pathing to the reference pathing so that towers can properly target the enemy.
        // The reference pathing allows enemies spawned by loot box zombies or loot box titans to be targeted.
        this.referencePathing = pathing;
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

        // Determines whether the enemy's parent group is null or if the enemy was already placed on to its parent group.
        if (this.parent == null || this.parent.getChildren().contains(this.loadedEnemy)) {

            // Prevents the enemy from being added to a null group or spawned more than once.
            return;
        }

        // Performs the enemy's spawn action.
        // This method is unique to each individual inheritor of the enemy class.
        this.onSpawn();

        // Adds the enemy to the list containing every active enemy.
        LIST_OF_ACTIVE_ENEMIES.add(this);

        // Determines whether the enemy's image is null.
        if (this.image != null) {

            // Loads the enemy's image.
            this.loadedEnemy.setImage(this.image);
        }

        // Adds the enemy to the enemy's parent group.
        this.parent.getChildren().add(this.loadedEnemy);
    }

    /**
     * Adds the {@link Enemy} to its {@code parent} {@link Group} at a newly given {@link Position}.
     * The {@code parent} {@link Group} and spawn {@link Position} will need to be set prior to calling this method.
     * @since Ultimate Tower Defense 1.0
     */
    public final void spawn(@NotNull final Position position) {

        // Sets the enemy's position to the given position.
        this.setPosition(position);

        // Spawns the enemy using the default spawn method.
        this.spawn();
    }

    /**
     * Gets whether the {@link Enemy} is alive and still a member of its parent {@link Group}.
     * @return {@code true} if the {@link Enemy} is alive, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    public final boolean isAlive() {

        // Returns whether the enemy is alive or not.
        return this.parent != null && this.parent.getChildren().contains(this.loadedEnemy);
    }

    /**
     * Begins moving the {@link Enemy} along a set {@link Path}.
     * The {@link Path} will need to be set prior to calling this method.
     * This method runs using separate {@link Thread}s and does not {@code block} the {@link Thread} in which it was called.
     * @since Ultimate Tower Defense 1.0
     */
    @NonBlocking
    @SuppressWarnings("all")
    public final void startMoving() {

        // Interrupts the thread controlling enemy movement so that the new animation can override the old animation if there was one.
        this.movementThread.interrupt();

        // Creates a new thread that will handle enemy movement.
        this.movementThread = new Thread(() -> {

            // Creates a new animation that will control the enemy's movement along a given path.
            TranslationHandler animation = new TranslationHandler();

            // Sets up the animation, setting the node to control to the enemy's image, and setting the speed to use to the enemy's speed.

            animation.setNode(this.loadedEnemy);
            animation.setSpeed(this.speed);

            // Determines whether the enemy's pathing is null.
            if (this.pathing == null) {

                // Prevents the enemy from moving along a null path.
                return;
            }

            // A loop that will iterate through every position on the given path.
            for (Position position : this.pathing.positions()) {

                // Sets the animation's destination to the current position in the loop.
                animation.setDestination(new Position(position.x() - (this.image != null ? this.image.getWidth() / 2 : 0), position.y() - (this.image != null ? this.image.getHeight() : 0)));

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

                // Increases the position index to represent the position the enemy is at.
                this.positionIndex++;
            }

            // Removes the enemy from its parent group now that it has finished its path.
            Platform.runLater(this::eliminate);
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
        // This will cause an exception to be thrown which will break out of the loop managing enemy movement.
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

        // TODO: Implement enemy ranges

        return false;
    }

    /**
     * Allows the {@link Enemy} to attack a {@link Tower} that is in {@code range}.
     * This method runs using separate {@link Thread}s and does not {@code block} the {@link Thread} in which it was called.
     * @since Ultimate Tower Defense 1.0
     */
    @NonBlocking
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
        // This will cause an exception to be thrown which will break out of the loop managing enemy attacks.
        this.attackThread.interrupt();
    }

    /**
     * Removes the {@link Enemy} from its parent {@link Group}.
     * @since Ultimate Tower Defense 1.0
     */
    public synchronized final void eliminate() {

        // Determines whether the enemy's parent group is null and whether the enemy was already eliminated from its parent group.
        if (this.parent == null || !this.parent.getChildren().contains(this.loadedEnemy)) {

            // Prevents the enemy from being removed from a null group and being eliminated more than once.
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
    @Specific(value = Enemy.class, subclasses = true)
    protected void onSpawn() {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // This method can be overridden by a subclass so that each individual enemy can have unique action to do when spawned.
    }

    /**
     * An action performed whenever an {@link Enemy} is {@code healed}.
     * By default, this method does nothing.
     * @since Ultimate Tower Defense 1.0
     */
    @Specific(value = Enemy.class, subclasses = true)
    protected void onHeal() {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // This method can be overridden by a subclass so that each individual enemy can have unique action to do when healed.
    }

    /**
     * An action performed whenever an {@link Enemy} takes {@code damage}.
     * By default, this method does nothing.
     * @since Ultimate Tower Defense 1.0
     */
    @Specific(value = Enemy.class, subclasses = true)
    protected void onDamaged() {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // This method can be overridden by a subclass so that each individual enemy can have unique action to do when damaged.
    }

    /**
     * An action performed whenever an {@link Enemy} is {@code eliminated}.
     * By default, this method does nothing.
     * @since Ultimate Tower Defense 1.0
     */
    @Specific(value = Enemy.class, subclasses = true)
    protected void onDeath() {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // This method can be overridden by a subclass so that each individual enemy can have unique action to do when eliminated.
    }

    /**
     * An action performed whenever an {@link Enemy} is {@code attacking}.
     * By default, this method does nothing.
     * @param tower the {@link Tower} to attack.
     * @throws InterruptedException when the {@link Enemy} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    @Specific(value = Enemy.class, subclasses = true)
    protected void attack(@NotNull final Tower tower) throws InterruptedException {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // This method can be overridden by a subclass so that each individual enemy can have a unique attack.
    }

    /**
     * An action performed whenever an {@link Enemy} is using a {@code special ability}.
     * By default, this method does nothing.
     * @throws InterruptedException when the {@link Enemy} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    @Specific(value = Enemy.class, subclasses = true)
    protected void special(@NotNull final Tower tower) throws InterruptedException {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // This method can be overridden by a subclass so that each individual enemy can have a unique special ability.
    }
}
