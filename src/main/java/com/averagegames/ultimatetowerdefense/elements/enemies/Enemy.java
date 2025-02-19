package com.averagegames.ultimatetowerdefense.elements.enemies;

import static com.averagegames.ultimatetowerdefense.game.data.Enemies.LIST_OF_ACTIVE_ENEMIES;
import static com.averagegames.ultimatetowerdefense.game.data.Towers.LIST_OF_ACTIVE_TOWERS;
import static com.averagegames.ultimatetowerdefense.game.development.Manager.LOGGER;

import java.io.InputStream;

import com.averagegames.ultimatetowerdefense.tools.exceptions.NumberBelowMinimumException;
import com.averagegames.ultimatetowerdefense.tools.exceptions.ProhibitedAccessException;
import com.averagegames.ultimatetowerdefense.tools.exceptions.UnspecifiedAccessException;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import com.averagegames.ultimatetowerdefense.elements.enemies.tools.Type;
import com.averagegames.ultimatetowerdefense.elements.maps.tools.Path;
import com.averagegames.ultimatetowerdefense.elements.maps.tools.Position;
import com.averagegames.ultimatetowerdefense.elements.towers.Tower;
import com.averagegames.ultimatetowerdefense.tools.animations.TranslationHandler;
import com.averagegames.ultimatetowerdefense.tools.annotations.GameElement;
import com.averagegames.ultimatetowerdefense.tools.annotations.GreaterThan;
import com.averagegames.ultimatetowerdefense.tools.annotations.NotInstantiable;
import com.averagegames.ultimatetowerdefense.tools.annotations.Prohibited;
import com.averagegames.ultimatetowerdefense.tools.annotations.Specific;
import com.averagegames.ultimatetowerdefense.tools.annotations.verification.GreaterThanAnnotation;
import com.averagegames.ultimatetowerdefense.tools.annotations.verification.ProhibitedAnnotation;
import com.averagegames.ultimatetowerdefense.tools.annotations.verification.SpecificAnnotation;
import com.averagegames.ultimatetowerdefense.tools.images.ImageLoader;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
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
@NotInstantiable
@GameElement(type = "character")
public abstract class Enemy {

    /**
     * The {@link Enemy}'s parent {@link Group}.
     */
    @Accessors(makeFinal = true) @Setter
    private Group parent = new Group();

    /**
     * The {@link Enemy}'s {@link Image} loaded using an {@link ImageLoader}.
     */
    private final ImageLoader loadedEnemy = new ImageLoader();

    /**
     * The {@link Enemy}'s range.
     */
    private final Circle range = new Circle(50);

    /**
     * The {@link Enemy}'s {@link Image}.
     */
    @NotNull
    @Accessors(makeFinal = true) @Setter(value = AccessLevel.PROTECTED, onMethod_= @Specific(value = Enemy.class, subclasses = true))
    protected Image image = new Image(InputStream.nullInputStream());

    /**
     * The {@link Enemy}'s {@link Type}.
     */
    @NotNull
    @Accessors(makeFinal = true) @Setter(value = AccessLevel.PROTECTED, onMethod_= @Specific(value = Enemy.class, subclasses = true)) @Getter
    protected Type type = Type.REGULAR;

    /**
     * The {@link Enemy}'s current {@code health}.
     */
    @Accessors(makeFinal = true) @Setter(value = AccessLevel.PROTECTED, onMethod_= @Specific(value = Enemy.class, subclasses = true)) @Getter
    protected int health = 0;

    /**
     * The {@code damage} the {@link Enemy} can do during an {@code attack}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Accessors(makeFinal = true) @Setter(value = AccessLevel.PROTECTED, onMethod_= @Specific(value = Enemy.class, subclasses = true))
    protected int damage = 0;

    /**
     * The {@link Enemy}'s speed in pixels per second.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Accessors(makeFinal = true) @Setter(value = AccessLevel.PROTECTED, onMethod_= @Specific(value = Enemy.class, subclasses = true))
    protected int speed = 0;

    /**
     * The {@link Path} the {@link Enemy} will follow.
     */
    @NotNull
    @Accessors(makeFinal = true) @Setter
    private Path pathing = new Path(new Position[0]);

    /**
     * A {@link Thread} that is responsible for handling all {@link Enemy} movement.
     */
    private Thread movementThread = new Thread();

    /**
     * A {@link Thread} that is responsible for handling all {@link Enemy} attacks.
     */
    private Thread attackThread = new Thread();

    /**
     * Adds a given amount to the {@link Enemy}'s current {@code health}.
     * @param amount damage the amount to add to the {@link Enemy}'s {@code health}.
     * @throws NumberBelowMinimumException if the amount provided is below 0.
     * @since Ultimate Tower Defense 1.0
     */
    public final void heal(@GreaterThan(-1) final int amount) {

        // Verifies that the given amount is greater than the value that the annotation specifies.
        GreaterThanAnnotation.verify(new Object() {}.getClass().getEnclosingMethod().getParameters()[0], amount);

        // Performs the enemy's on healed action.
        // This method is unique to each individual inheritor of the enemy class.
        this.onHeal();

        // Adds the given amount to the enemy's health.
        this.health += amount;

        // Logs information regarding the enemy.
        LOGGER.info(STR."\{this} gained \{damage} health");
    }

    /**
     * Removes a given amount from the {@link Enemy}'s current {@code health}.
     * @param damage the amount to remove from the {@link Enemy}'s {@code health}.
     * @throws NumberBelowMinimumException if the amount provided is below 0.
     * @since Ultimate Tower Defense 1.0
     */
    public final void dealDamage(@GreaterThan(-1) final int damage) {

        // Verifies that the given amount is greater than the value that the annotation specifies.
        GreaterThanAnnotation.verify(new Object() {}.getClass().getEnclosingMethod().getParameters()[0], damage);

        // Performs the enemy's on damaged action.
        // This method is unique to each individual inheritor of the enemy class.
        this.onDamaged();

        // Removes the given amount from the enemy's health.
        this.health -= damage;

        // Logs information regarding the enemy.
        LOGGER.info(STR."\{this} lost \{damage} health");
    }

    /**
     * Sets the {@link Enemy}'s {@link Position} to a newly given {@link Position}.
     * @param position the new {@link Position}.
     * @throws ProhibitedAccessException if the method is invoked in a prohibited class.
     * @access the {@link Tower} class and its subclasses may not use this method.
     * @since Ultimate Tower Defense 1.0
     */
    @Prohibited(value = Tower.class, subclasses = true)
    public final void setPosition(@NotNull final Position position) {

        // Verifies that the calling class of the method was not prohibited by the method's annotation.
        ProhibitedAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // Updates the image representing the enemy's position on the stage it is currently on, if any.

        this.loadedEnemy.setX(position.x());
        this.loadedEnemy.setY(position.y());

        // Updates the circle representing the enemy's range's position on the stage it is currently on, if any.

        this.range.setCenterX(position.x());
        this.range.setCenterY(position.y());

        // Logs information regarding the enemy.
        LOGGER.info(STR."\{this} has been moved to \{position}");
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
     * @throws ProhibitedAccessException if the method is invoked in a prohibited class.
     * @access the {@link Tower} class and its subclasses may not use this method.
     * @since Ultimate Tower Defense 1.0
     */
    @Prohibited(value = Tower.class, subclasses = true)
    public final void spawn() {

        // Verifies that the calling class of the method was not prohibited by the method's annotation.
        ProhibitedAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // Performs the enemy's spawn action.
        // This method is unique to each individual inheritor of the enemy class.
        this.onSpawn();

        // Adds the enemy to the list containing every active enemy.
        LIST_OF_ACTIVE_ENEMIES.add(this);

        // Loads the enemy's image.
        this.loadedEnemy.setImage(this.image);

        // Adds the enemy to the enemy's parent group.
        this.parent.getChildren().add(this.loadedEnemy);

        // Logs information regarding the enemy.
        LOGGER.info(STR."\{this} has been spawned on \{this.parent}");
    }

    /**
     * Adds the {@link Enemy} to its {@code parent} {@link Group} at a set {@link Position}.
     * Any previously set parent {@link Group} will be overridden when this method is called.
     * @param parent the {@link Group} to add the {@link Enemy} to.
     * @throws ProhibitedAccessException if the method is invoked in a prohibited class.
     * @access the {@link Tower} class and its subclasses may not use this method.
     * @since Ultimate Tower Defense 1.0
     */
    @Prohibited(value = Tower.class, subclasses = true)
    public final void spawn(@NotNull final Group parent) {

        // Verifies that the calling class of the method was not prohibited by the method's annotation.
        ProhibitedAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

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
     * @throws ProhibitedAccessException if the method is invoked in a prohibited class.
     * @access the {@link Tower} class and its subclasses may not use this method.
     * @since Ultimate Tower Defense 1.0
     */
    @NonBlocking
    @Prohibited(value = Tower.class, subclasses = true)
    public final void startMoving() {

        // Verifies that the calling class of the method was not prohibited by the method's annotation.
        ProhibitedAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // Interrupts the thread controlling enemy movement so that the new animation can override the old animation if there was one.
        this.movementThread.interrupt();

        // Creates a new thread that will handle enemy movement and starts it.
        (this.movementThread = new Thread(() -> {

            // Logs information regarding the enemy's movements.
            LOGGER.info(STR."\{this} movement has started");

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
                } finally {

                    // Determines if the current thread was interrupted.
                    if (Thread.currentThread().isInterrupted()) {

                        // Logs information regarding the enemy's movements.
                        LOGGER.warning(STR."\{this} movement was interrupted");
                    }

                    // Asserts that the enemy should have reached the next point in the path or that the enemy's health should be less than or equal to 0 by this point.
                    // The reason assertions are being used here is that this expression will always evaluate to true.
                    assert Thread.interrupted() || this.getPosition().equals(position) || this.health <= 0 : STR."Animation for \{this} did not finish correctly";
                }

                // Logs information regarding the enemy's movements.
                LOGGER.info(STR."\{this} target position was \{position} and current position is \{this.getPosition()}");
            }

            // Asserts that the enemy should be at the end of the path by this point.
            // The reason assertions are being used here is that this expression will always evaluate to true.
            assert this.getPosition().equals(this.pathing.positions()[this.pathing.positions().length - 1]) : STR."Animation for \{this} did not finish correctly";

            // Logs information regarding the enemy's movements.
            LOGGER.info(STR."\{this} movement has finished");
        })).start();
    }

    /**
     * Determines whether the {@link Enemy} can {@code attack} the given {@link Tower}.
     * This is a helper method.
     * @param tower the {@link Tower} to {@code attack}.
     * @since Ultimate Tower Defense 1.0
     */
    private <T extends Tower> boolean canAttack(@NotNull final T tower) {

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
     * @throws ProhibitedAccessException if the method is invoked in a prohibited class.
     * @access the {@link Tower} class and its subclasses may not use this method.
     * @since Ultimate Tower Defense 1.0
     */
    @NonBlocking
    @Prohibited(value = Tower.class, subclasses = true)
    public final void startAttacking() {

        // Verifies that the calling class of the method was not prohibited by the method's annotation.
        ProhibitedAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // Creates a new thread that will handle enemy attacks and starts it.
        (this.attackThread = new Thread(() -> {

            // Determines if the enemy can attack a specific tower.
            if (this.canAttack(this.getTarget(LIST_OF_ACTIVE_TOWERS.toArray(new Tower[0])))) {

                // Logs information regarding the enemy's attacks.
                LOGGER.fine(STR."\{this} does not have attacks implemented yet");
            }
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

        // Logs information regarding the enemy.
        LOGGER.info(STR."\{this} has been eliminated from \{this.parent}");
    }

    /**
     * An action performed whenever an {@link Enemy} is {@code spawned}.
     * By default, this method does nothing.
     * @throws UnspecifiedAccessException if the method is invoked in an unspecified class.
     * @access this method can only be called in the {@link Enemy} class but is intended to be overridden in the {@link Enemy} class' subclasses.
     * @since Ultimate Tower Defense 1.0
     */
    @OverrideOnly
    @Specific(value = {Enemy.class, Zombie.class}, subclasses = true)
    protected void onSpawn() {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // This method can be overridden by a subclass so that each individual enemy can have unique action to do when spawned.
    }

    /**
     * An action performed whenever an {@link Enemy} is {@code healed}.
     * By default, this method does nothing.
     * @throws UnspecifiedAccessException if the method is invoked in an unspecified class.
     * @access this method can only be called in the {@link Enemy} class but is intended to be overridden in the {@link Enemy} class' subclasses.
     * @since Ultimate Tower Defense 1.0
     */
    @OverrideOnly
    @Specific(value = {Enemy.class, Zombie.class}, subclasses = true)
    protected void onHeal() {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // This method can be overridden by a subclass so that each individual enemy can have unique action to do when healed.
    }

    /**
     * An action performed whenever an {@link Enemy} takes {@code damage}.
     * By default, this method does nothing.
     * @throws UnspecifiedAccessException if the method is invoked in an unspecified class.
     * @access this method can only be called in the {@link Enemy} class but is intended to be overridden in the {@link Enemy} class' subclasses.
     * @since Ultimate Tower Defense 1.0
     */
    @OverrideOnly
    @Specific(value = {Enemy.class, Zombie.class}, subclasses = true)
    protected void onDamaged() {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // This method can be overridden by a subclass so that each individual enemy can have unique action to do when damaged.
    }

    /**
     * An action performed whenever an {@link Enemy} is {@code eliminated}.
     * By default, this method does nothing.
     * @throws UnspecifiedAccessException if the method is invoked in an unspecified class.
     * @access this method can only be called in the {@link Enemy} class but is intended to be overridden in the {@link Enemy} class' subclasses.
     * @since Ultimate Tower Defense 1.0
     */
    @OverrideOnly
    @Specific(value = {Enemy.class, Zombie.class}, subclasses = true)
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
     * @throws UnspecifiedAccessException if the method is invoked in an unspecified class.
     * @access this method can only be called in the {@link Enemy} class but is intended to be overridden in the {@link Enemy} class' subclasses.
     * @since Ultimate Tower Defense 1.0
     */
    @Specific(value = {Enemy.class, Titan.class}, subclasses = true)
    protected void attack(@NotNull final Tower tower) throws InterruptedException {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // This method can be overridden by a subclass so that each individual enemy can have a unique attack.
    }

    /**
     * An action performed whenever an {@link Enemy} is using a {@code special ability}.
     * By default, this method does nothing.`
     * @throws InterruptedException when the {@link Enemy} is {@code eliminated}.
     * @throws UnspecifiedAccessException if the method is invoked in an unspecified class.
     * @access this method can only be called in the {@link Enemy} class but is intended to be overridden in the {@link Enemy} class' subclasses.
     * @since Ultimate Tower Defense 1.0
     */
    @Specific(value = {Enemy.class, Boss.class}, subclasses = true)
    protected void special(@NotNull final Tower tower) throws InterruptedException {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // This method can be overridden by a subclass so that each individual enemy can have a unique special ability.
    }
}
