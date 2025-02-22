package com.averagegames.ultimatetowerdefense.characters.towers;

import static com.averagegames.ultimatetowerdefense.characters.enemies.Enemy.LIST_OF_ACTIVE_ENEMIES;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.tools.assets.ImageLoader;
import com.averagegames.ultimatetowerdefense.maps.Position;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@link Tower} class serves as a {@code super} class to all in-game enemies.
 * Anything that extends this class is considered a {@link Tower} and can {@code attack} specific {@link Enemy}'s based on {@link Targeting} and {@code level}.
 * @implNote {@link Tower} subclasses will need to set the attributes within the {@link Tower} class manually to be properly customized.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
public abstract class Tower {

    /**
     * A {@link List} containing every active {@link Tower} in a game.
     */
    public static final List<@NotNull Tower> LIST_OF_ACTIVE_TOWERS = Collections.synchronizedList(new ArrayList<>());

    /**
     * The {@link Tower}'s parent {@link Group}.
     */
    @NotNull
    @Accessors(makeFinal = true) @Setter
    private Group parent;

    /**
     * The {@link Tower}'s {@link Image} loaded using an {@link ImageLoader}.
     */
    @NotNull
    private final ImageLoader loadedTower;

    /**
     * The {@link Tower}'s {@link Image}.
     */
    @NotNull
    @Accessors(makeFinal = true) @Setter(value = AccessLevel.PROTECTED)
    protected Image image;

    /**
     * The {@link Tower}'s current {@code health}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED) @Getter
    protected int health;

    /**
     * The {@code damage} the {@link Tower} can do during an {@code attack}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED)
    protected int damage;

    /**
     * The {@link Tower}'s {@link Targeting}.
     */
    @NotNull
    protected Targeting targeting;

    /**
     * Whether the {@link Tower} can detect a {@code hidden} {@link Enemy}.
     */
    protected boolean hiddenDetection;

    /**
     * Whether the {@link Tower} can detect a {@code flying} {@link Enemy}.
     */
    protected boolean flyingDetection;

    /**
     * The {@link Tower}'s cool down in milliseconds between {@code attacks}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED)
    protected int coolDown;

    /**
     * The {@link Tower}'s {@code level}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED)
    private int level;

    /**
     * A {@link Thread} that is responsible for handling all {@link Tower} {@code attacks}.
     */
    private Thread attackThread;
    
    {

        // Initializes the tower's parent to a default group.
        this.parent = new Group();

        // Initializes the tower's image to a default, null image.

        this.loadedTower = new ImageLoader();
        this.image = new Image(InputStream.nullInputStream());

        // Initializes the tower's targeting to 'first' by default.
        this.targeting = Targeting.FIRST;

        // Initializes the tower's level so that it starts at 1 and not 0.
        this.level = 1;

        // Initializes the thread that the tower will use to attack.
        this.attackThread = new Thread(() -> {
            // This thread does nothing by default.
        });
    }

    /**
     * Adds a given amount to the {@code health} of the {@link Tower}.
     * @param amount the amount to {@code heal} the {@link Tower} by.
     * @since Ultimate Tower Defense 1.0
     */
    public final void heal(@Range(from = 0, to = Integer.MAX_VALUE) final int amount) {

        // Adds the given amount to the tower's health.
        this.health += amount;
    }

    /**
     * Removes a given amount from the {@code health} of the {@link Tower}.
     * @param amount the amount to {@code damage} the {@link Tower} by.
     */
    public final void damage(@Range(from = 0, to = Integer.MAX_VALUE) final int amount) {

        // Removes the given amount from the tower's health.
        this.health -= amount;
    }

    /**
     * Sets the {@link Tower}'s {@link Position} to a newly given {@link Position}.
     * @param position the new {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void setPosition(@NotNull final Position position) {

        // Updates the tower's x and y coordinates to the given position's x and y coordinates.

        this.loadedTower.setX(position.x());
        this.loadedTower.setY(position.y());
    }

    /**
     * Gets the {@link Position} the {@link Tower} is currently at.
     * @return the {@link Tower}'s current {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    @Contract(" -> new")
    public final @NotNull Position getPosition() {

        // Returns the tower's current position.
        return new Position(this.loadedTower.getCurrentX(), this.loadedTower.getCurrentY());
    }

    /**
     * Determines whether the {@link Tower} intersects the given {@link Node} at any point.
     * @param node the {@link Node} to be checked.
     * @return {@code true} if the {@link Tower} intersects the {@link Node}, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    public final boolean isInRange(@NotNull final Node node) {

        // Returns whether the tower intersects the given node.
        return this.loadedTower.intersects(node.getLayoutBounds());
    }

    /**
     * Adds the {@link Tower} onto its parent {@link Group} at a given {@link Position}.
     * @param position the {@link Position} to place the {@link Tower} at.
     * @since Ultimate Tower Defense 1.0
     */
    public final void place(@NotNull final Position position) {

        // Performs the tower's spawn action.
        // This method is unique to each individual inheritor of the tower class.
        this.onPlace();

        // Adds the tower to the list containing every active tower.
        LIST_OF_ACTIVE_TOWERS.add(this);

        // Loads the tower's image.
        this.loadedTower.setImage(this.image);

        // Sets the tower's x and y coordinates to the given position's x and y coordinates.

        this.loadedTower.setX(position.x());
        this.loadedTower.setY(position.y());

        // Adds the tower to the tower's parent group.
        parent.getChildren().add(this.loadedTower);
    }

    /**
     * Gets the {@link Tower}'s target {@link Enemy} based on which {@link Targeting} mode is active.
     * @return the {@link Tower}'s target {@link Enemy}.
     */
    private @Nullable Enemy getTarget() {

        // TODO: Implement targeting

        // Determines whether the list containing every active enemy contains any elements.
        if (!LIST_OF_ACTIVE_ENEMIES.isEmpty()) {

            // Returns the tower's current target.
            // This is a temporary targeting method.
            return LIST_OF_ACTIVE_ENEMIES.getFirst();
        }

        // Returns a null value if no target enemy is found.
        return null;
    }

    /**
     * Gets whether the {@link Tower} can {@code attack} a given {@link Enemy}.
     * @param enemy the {@link Enemy} being {@code attacked}.
     * @return {@code true} if the {@link Tower} can {@code attack} the {@link Enemy}, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    @Contract(pure = true)
    private boolean canAttack(@NotNull final Enemy enemy) {

        // A switch case that determines what type of enemy the given enemy is.
        // Possible types are regular, hidden, and flying.
        switch (enemy.getType()) {

            // The switch case for the regular enemy type.
            case Type.REGULAR:

                // Returns true because all towers can attack regular enemies by default.
                return true;

            // The switch case for the hidden enemy type.
            case Type.HIDDEN:

                // Determines whether the tower has hidden detection capabilities.
                if (this.hiddenDetection) {

                    // Returns true if the tower has hidden detection.
                    return true;
                }

            // The switch case for the flying enemy type.
            case Type.FLYING:

                // Determines whether the tower has flying detection capabilities.
                if (this.flyingDetection) {

                    // Returns true if the tower has flying detection.
                    return true;
                }

            // The default switch case.
            default:

                // Breaks out of the switch case.
                break;
        }

        // Returns false if the tower is not able to attack the given enemy.
        return false;
    }

    /**
     * Allows the {@link Tower} to attack an {@link Enemy} that is in {@code range}.
     * This method runs using separate {@link Thread}s and does not {@code block} the {@link Thread} in which it was called.
     * @since Ultimate Tower Defense 1.0
     */
    public final void startAttacking() {

        // Creates a new thread that will handle tower attacks.
        this.attackThread = new Thread(() -> {

            // A loop that will continuously run until the tower is eliminated.
            while (true) {

                // Creates an object using the tower's current target enemy.
                var target = this.getTarget();

                // Determines whether the tower's target is either alive or null.
                if (target == null || !target.isAlive()) {

                    // Jumps to the next iteration of the loop preventing any exceptions from occurring.
                    continue;
                }

                // Determines whether the tower can attack the current target.
                // Whether the tower can attack an enemy is based on the enemy's type and the tower's enemy detection capabilities.
                if (this.canAttack(target)) {

                    // Allows the attack the loop to be broken out of if the tower is eliminated.
                    try {

                        // Attacks the tower's current target enemy.
                        this.attack(target);

                        // Causes the current thread to wait for the tower's cool down to end.
                        Thread.sleep(this.coolDown);
                    } catch (InterruptedException e) {

                        // Breaks out of the loop if the current thread is forcefully interrupted.
                        break;
                    }
                }
            }
        });

        // Starts the thread so that the Tower can attack enemies.
        this.attackThread.start();
    }

    /**
     * Stops all attacks the {@link Tower} may be performing.
     * The {@link Tower}'s attacking {@link Thread} will be interrupted when calling this method.
     * @since Ultimate Tower Defense 1.0
     */
    public final void stopAttacking() {

        // Interrupts the thread responsible for all tower attacks.
        this.attackThread.interrupt();
    }

    /**
     * Removes the {@link Tower} from its parent {@link Group}.
     * @since Ultimate Tower Defense 1.0
     */
    public synchronized final void eliminate() {

        // Performs the tower's death action.
        // This method is unique to each individual inheritor of the tower class.
        this.onDeath();

        // Removes the tower from its parent group.
        this.parent.getChildren().remove(this.loadedTower);

        // Interrupts the thread controlling tower attacks.
        // This will cause an exception to be thrown in the thread which will break out of the loop controlling the tower.
        this.stopAttacking();

        // Removes the tower from the list containing every active tower.
        LIST_OF_ACTIVE_TOWERS.remove(this);
    }

    /**
     * An action performed whenever a {@link Tower} is {@code placed}.
     * By default, this method does nothing.
     * @since Ultimate Tower Defense 1.0
     */
    protected void onPlace() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when placed.
    }

    /**
     * An action performed whenever a {@link Tower} is {@code healed}.
     * By default, this method does nothing.
     * @since Ultimate Tower Defense 1.0
     */
    protected void onHeal() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when healed.
    }

    /**
     * An action performed whenever a {@link Tower} takes {@code damage}.
     * By default, this method does nothing.
     * @since Ultimate Tower Defense 1.0
     */
    protected void onDamage() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when damaged.
    }

    /**
     * An action performed whenever a {@link Tower} is {@code eliminated}.
     * By default, this method does nothing.
     * @since Ultimate Tower Defense 1.0
     */
    protected void onDeath() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when eliminated.
    }

    /**
     * An action performed whenever a {@link Tower} is {@code attacking}.
     * By default, this method does nothing.
     * @param enemy the {@link Enemy} to attack.
     * @throws InterruptedException when the {@link Enemy} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    protected abstract void attack(@NotNull final Enemy enemy) throws InterruptedException;

    /**
     * An action performed whenever a {@link Tower} is {@code upgraded}.
     * By default, this method does nothing.
     * @throws InterruptedException when the {@link Tower} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    public abstract void upgrade() throws InterruptedException;

    /**
     * An action performed whenever a {@link Tower} is using a {@code special ability}.
     * By default, this method does nothing.
     * @throws InterruptedException when the {@link Tower} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    public void special() throws InterruptedException {
        // This method can be overridden by a subclass so that each individual tower can have a unique special ability.
    }
}
