package com.averagegames.ultimatetowerdefense.characters;

import static com.averagegames.ultimatetowerdefense.world.Towers.LIST_OF_ACTIVE_TOWERS;

import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.characters.towers.Targeting;
import com.averagegames.ultimatetowerdefense.characters.util.ImageLoader;
import com.averagegames.ultimatetowerdefense.world.maps.elements.Position;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.InputStream;

/**
 * The {@link Tower} class serves as a {@code super} class to all in-game enemies.
 * Anything that extends this class is considered a {@link Tower} and can {@code attack} specific {@link Enemy}'s based on {@link Targeting} and {@code level}.
 * @implNote {@link Tower} subclasses will need to set the attributes within the {@link Tower} class manually to be properly customized.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
public abstract class Tower {

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
        return new Position(this.loadedTower.getX() + this.loadedTower.getTranslateX(), this.loadedTower.getY() + this.loadedTower.getTranslateY());
    }

    /**
     * Determines whether the {@link Tower} intersects the given {@link Node} at any point.
     * @param node the {@link Node} to be checked.
     * @return {@code true} if the {@link Tower} intersects the {@link Node}, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    public final boolean intersects(@NotNull final Node node) {

        // Returns whether the tower intersects the given node.
        return this.loadedTower.intersects(node.getLayoutBounds());
    }

    public final void place(@NotNull final Group parent) {
        this.onPlace();

        LIST_OF_ACTIVE_TOWERS.add(this);

        parent.getChildren().add(this.loadedTower);

        this.parent = parent;
    }

    @Contract(pure = true)
    private boolean canAttack(@NotNull final Enemy enemy) {
        boolean allowed = false;

        switch (enemy.getType()) {
            case Type.REGULAR:
                allowed = true;

            case Type.HIDDEN:
                if (this.hiddenDetection) {
                    allowed = true;
                }

            case Type.FLYING:
                if (this.flyingDetection) {
                    allowed = true;
                }

            default:
                break;
        }

        return allowed;
    }

    protected final void startAttacking() {
        this.attackThread = new Thread(() -> {
            if (this.canAttack(null)) {
                // TODO: Implement attacks
            }
        });

        this.attackThread.start();
    }

    public synchronized final void eliminate() {

        this.onDeath();

        this.parent.getChildren().remove(this.loadedTower);

        this.attackThread.interrupt();

        LIST_OF_ACTIVE_TOWERS.remove(this);
    }

    protected void onPlace() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when placed.
    }

    protected void onHeal() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when healed.
    }

    protected void onDamage() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when damaged.
    }

    protected void onDeath() {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when eliminated.
    }

    public abstract void upgrade() throws InterruptedException;

    protected abstract void attack(@NotNull final Enemy enemy) throws InterruptedException;

    public void special() throws InterruptedException {
        // This method can be overridden by a subclass so that each individual tower can have a unique special ability.
    }
}
