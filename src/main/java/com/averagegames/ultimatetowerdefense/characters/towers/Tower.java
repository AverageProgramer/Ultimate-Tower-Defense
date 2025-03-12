package com.averagegames.ultimatetowerdefense.characters.towers;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.maps.Path;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.util.assets.ImageLoader;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.*;

import java.util.*;

import static com.averagegames.ultimatetowerdefense.characters.enemies.Enemy.LIST_OF_ACTIVE_ENEMIES;
import static com.averagegames.ultimatetowerdefense.util.development.LogManager.LOGGER;

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
    @NotNull
    public static final List<@NotNull Tower> LIST_OF_ACTIVE_TOWERS = Collections.synchronizedList(new ArrayList<>());

    /**
     * The {@link Tower}'s parent {@link Group}.
     */
    @Nullable
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
    @Nullable
    protected Image image;

    /**
     * The {@link Tower}'s current {@code health}.
     */
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED) @Getter
    private int health;

    /**
     * The {@code damage} the {@link Tower} can do during an {@code attack}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    protected int damage;

    /**
     * The {@link Tower}'s {@code range}.
     */
    @NotNull
    private final Circle range;

    /**
     * The {@link Tower}'s {@link Targeting}.
     */
    @NotNull
    protected Targeting targeting;

    /**
     * Whether the {@link Tower} can detect a {@code hidden} {@link Enemy}.
     */
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED) @Getter(value = AccessLevel.PROTECTED)
    private boolean hiddenDetection;

    /**
     * Whether the {@link Tower} can detect a {@code flying} {@link Enemy}.
     */
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED) @Getter(value = AccessLevel.PROTECTED)
    private boolean flyingDetection;

    /**
     * The {@link Tower}'s cool down in milliseconds between {@code attacks}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    protected int coolDown;

    /**
     * The {@link Tower}'s {@code level}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED) @Getter
    private int level;

    /**
     * A {@link Thread} that is responsible for handling all {@link Tower} {@code attacks}.
     */
    private Thread attackThread;

    {

        // Initializes the tower's parent to a default, null group.
        this.parent = null;

        // Initializes the tower's image to a default, null image.

        this.loadedTower = new ImageLoader();
        this.image = null;

        this.loadedTower.toFront();

        // Initializes the tower's range to a default circle.
        this.range = new Circle();

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

        // Performs the tower's on healed action.
        // This method is unique to each individual inheritor of the tower class.
        this.onHeal();

        // Adds the given amount to the tower's health.
        this.health += amount;

        // Logs that the tower has been healed by a given amount.
        LOGGER.info(STR."Tower \{this} health has been increased by \{amount}.");
    }

    /**
     * Removes a given amount from the {@code health} of the {@link Tower}.
     * @param damage the amount to {@code damage} the {@link Tower} by.
     * @since Ultimate Tower Defense 1.0
     */
    public final void damage(@Range(from = 0, to = Integer.MAX_VALUE) final int damage) {

        // Performs the tower's on damaged action.
        // This method is unique to each individual inheritor of the tower class.
        this.onDamaged();

        // Removes the given amount from the tower's health.
        this.health -= damage;

        // Logs that the enemy has been damaged by a given amount.
        LOGGER.info(STR."Tower \{this} health has been decreased by \{damage}.");

        // Determines whether the tower has any health remaining.
        if (this.health <= 0) {

            // Removes the tower from its parent group.
            Platform.runLater(this::eliminate);
        }
    }

    /**
     * Sets the {@link Tower}'s {@link Position} to a newly given {@link Position}.
     * @param position the new {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    @SuppressWarnings("unused")
    public final void setPosition(@NotNull final Position position) {

        // Updates the tower's x and y coordinates to the given position's x and y coordinates.

        this.loadedTower.setX(position.x() - (this.image != null ? this.image.getWidth() / 2 : 0));
        this.loadedTower.setY(position.y() - (this.image != null ? this.image.getHeight() / 2 : 0));
    }

    /**
     * Gets the {@link Position} the {@link Tower} is currently at.
     * @return the {@link Tower}'s current {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    @Contract(" -> new")
    public final @NotNull Position getPosition() {

        // Returns the tower's current position.
        return new Position(this.loadedTower.getCurrentX() + (this.image != null ? this.image.getWidth() / 2 : 0), this.loadedTower.getCurrentY() + (this.image != null ? this.image.getHeight() / 2 : 0));
    }

    /**
     * Sets the radius of the {@link Circle} representing the {@link Tower}'s {@code range} to a newly given value.
     * @param radius the radius of the {@link Tower}'s {@code range}.
     * @since Ultimate Tower Defense 1.0
     */
    protected final void setRadius(@Range(from = 0, to = Integer.MAX_VALUE) final double radius) {

        // Sets the tower's range to have the given radius.
        this.range.setRadius(radius);
    }

    /**
     * Gets the radius of the {@link Circle} representing the {@link Tower}'s {@code range}.
     * @return the radius of the {@link Tower}'s {@code range}.
     * @since Ultimate Tower Defense 1.0
     */
    public final double getRadius() {

        // Returns the range's current radius.
        return this.range.getRadius();
    }

    /**
     * Determines whether the {@link Tower} is within the given {@link Circle} at any point.
     * @param range the {@link Circle} to be checked.
     * @param reference temporary
     * @return {@code true} if the {@link Tower} is within the {@link Circle}, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    public final boolean isInRange(@NotNull final Circle range, @NotNull final ImageLoader reference) {

        // The tower's current position.
        Position currentPos = this.getPosition();

        // The circle's current position.
        Position rangePos = new Position(reference.getCurrentX(), reference.getCurrentY());

        // The change in x and change in y for between the tower and the circle.

        double x = currentPos.x() - rangePos.x();
        double y = (currentPos.y() - (this.image != null ? this.image.getHeight() / 2 : 0)) - rangePos.y();

        // The circle's radius.
        double radius = range.getRadius();

        // Returns whether the tower is within the bounds of the circle.
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= radius;
    }

    /**
     * Adds the {@link Tower} to its {@code parent} {@link Group} at a set {@link Position}.
     * The {@code parent} {@link Group} and placement {@link Position} will need to be set prior to calling this method.
     * @since Ultimate Tower Defense 1.0
     */
    public final void place() {

        // Determines whether the tower's parent group is null and whether the tower was already placed on to its parent group.
        if (this.parent == null || this.parent.getChildren().contains(this.loadedTower)) {

            // Prevents the tower from being added to a null group and from being placed more than once.
            return;
        }

        // Performs the tower's spawn action.
        // This method is unique to each individual inheritor of the tower class.
        this.onPlace();

        // Determines whether the tower's image is null.
        if (this.image != null) {

            // Loads the tower's image.
            this.loadedTower.setImage(this.image);
        }

        // Sets the range's x and y components to the position of the tower's x and y components.

        this.range.setCenterX(this.getPosition().x());
        this.range.setCenterY(this.getPosition().y());

        this.range.setFill(Paint.valueOf("#3a5bb6"));
        this.range.setOpacity(0.25);
        this.range.toBack();
        this.range.setVisible(false);
        this.loadedTower.setOnMouseClicked(e -> this.range.setVisible(!this.range.isVisible()));
        this.parent.getChildren().add(this.range);

        // Adds the tower to the tower's parent group.
        this.parent.getChildren().add(this.loadedTower);

        // Adds the tower to the list containing every active tower.
        LIST_OF_ACTIVE_TOWERS.add(this);

        // Logs that the tower has been placed.
        LOGGER.info(STR."Tower \{this} placed.");
    }

    /**
     * Adds the {@link Tower} onto its parent {@link Group} at a given {@link Position}.
     * @param position the {@link Position} to place the {@link Tower} at.
     * @since Ultimate Tower Defense 1.0
     */
    public final void place(@NotNull final Position position) {

        // Sets the tower's position to the given position.
        this.setPosition(position);

        // Places the tower using the default placement method.
        this.place();
    }

    /**
     * Gets whether the {@link Tower} is alive and still a member of its parent {@link Group}.
     * @return {@code true} if the {@link Tower} is alive, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    public final boolean isAlive() {

        // Returns whether the tower is alive or not.
        return this.parent != null && this.parent.getChildren().contains(this.loadedTower);
    }

    /**
     * Gets whether the {@link Tower} can {@code attack} a given {@link Enemy}.
     * @param enemy the {@link Enemy} being {@code attacked}.
     * @return {@code true} if the {@link Tower} can {@code attack} the {@link Enemy}, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    @Contract(pure = true)
    @SuppressWarnings("all")
    private boolean canAttack(@NotNull final Enemy enemy) {

        // A switch case that determines what type of enemy the given enemy is.
        // Possible types are regular, hidden, and flying.
        return switch (enemy.getType()) {

            // The switch case for the regular enemy type.
            case Type.REGULAR ->

                // Returns true if the enemy is within the tower's range.
                    enemy.isInRange(this.range);

            // The switch case for the hidden enemy type.
            case Type.HIDDEN ->

                // Returns whether the tower has hidden detection and can detect hidden enemies and whether the enemy is within the tower's range.
                    this.hiddenDetection && enemy.isInRange(this.range);

            // The switch case for the flying enemy type.
            case Type.FLYING ->

                // Returns whether the tower has flying detection and can detect flying enemies and whether the enemy is within the tower's range.
                    this.flyingDetection && enemy.isInRange(this.range);
        };
    }

    /**
     * Gets the {@link Tower}'s target {@link Enemy} based on which {@link Targeting} mode is active.
     * @return the {@link Tower}'s target {@link Enemy}.
     */
    private @Nullable Enemy getTarget() {

        // The object that will be returned as the target enemy.
        Enemy target = null;

        // Determines whether the list containing every active enemy is empty.
        if (LIST_OF_ACTIVE_ENEMIES.isEmpty()) {

            // Returns null because there are no active enemies.
            return null;
        }

        // Determines whether the tower's targeting is on first or last.
        if (this.targeting == Targeting.FIRST || this.targeting == Targeting.LAST) {

            // Sorts the list containing every active enemy so that the enemies with the greatest position index are at the end of the list.
            LIST_OF_ACTIVE_ENEMIES.sort(Comparator.comparingInt(Enemy::getPositionIndex));

            // Creates a new list of enemies that will not contain any enemies the tower can't attack.
            // This will prevent targeting issues when the tower is trying to find a target enemy.

            List<Enemy> fixedList = new ArrayList<>(LIST_OF_ACTIVE_ENEMIES);
            fixedList.removeIf(enemy -> !this.canAttack(enemy));

            // Determines whether the fixed list containing every possible enemy target is empty.
            if (fixedList.isEmpty()) {

                // Returns null because there are no possible target enemies.
                return null;
            }

            // An index that will be used to determine which enemy has passed the most positions on a set path.
            int currentPos = this.targeting == Targeting.FIRST ? fixedList.getLast().getPositionIndex() : Integer.MAX_VALUE;

            // A double that will be used to determine which enemy is closest to the next position on a set path.
            double distance = this.targeting == Targeting.FIRST ? Integer.MAX_VALUE : 0;

            // A loop that will iterate through every enemy within the new array of active enemies.
            for (Enemy enemy : fixedList) {

                // The enemy's current position index.
                // The position index represents what position along a path the enemy is at.
                int posIndex = enemy.getPositionIndex();

                // Determines whether the enemy is a valid target based on the tower's targeting.
                if (this.targeting == Targeting.FIRST ? posIndex < currentPos : posIndex > currentPos) {

                    // Jumps to the next iteration of the loop.
                    continue;
                }

                // The enemy's current pathing.
                Path path = enemy.getReferencePathing();

                // Determines whether the path is null and whether the position index is within the bounds of the enemy's path.
                if (path != null && posIndex < path.positions().length - 1) {

                    // Gets the enemy's current position as well as the enemy's target destination.

                    Position enemyPos = enemy.getPosition();
                    Position second = path.positions()[posIndex + 1];

                    // The distance remaining to the enemy's target destination.
                    double enemyDistance = Math.sqrt(Math.pow(second.x() - enemyPos.x(), 2) + Math.pow(second.y() - enemyPos.y(), 2));

                    // Determines whether the enemy is closest to or farthest from its destination depending on the tower's targeting and whether the tower can attack the enemy.
                    if (this.targeting == Targeting.FIRST ? enemyDistance < distance : enemyDistance > distance) {

                        // Sets the original position index to the enemy's.
                        currentPos = posIndex;

                        // Sets the original distance remaining to the enemy's current distance remaining.
                        distance = enemyDistance;

                        // Sets the target enemy to the current enemy within the loop.
                        target = enemy;
                    }
                } else if (path != null && posIndex == path.positions().length - 1) {

                    // Sets the target enemy to the current enemy within the loop.
                    // This enemy will always be the enemy at the very end of the path.
                    target = enemy;
                }
            }

            // Returns the target enemy.
            return target;
        } else {

            // Sorts the list containing every active enemy so that the enemies with the greatest health are at the end of the list.
            LIST_OF_ACTIVE_ENEMIES.sort(Comparator.comparingInt(Enemy::getHealth));

            // Creates a new list of enemies that will not contain any enemies the tower can't attack.
            // This will prevent targeting issues when the tower is trying to find a target enemy.

            List<Enemy> fixedList = LIST_OF_ACTIVE_ENEMIES;
            fixedList.removeIf(enemy -> !this.canAttack(enemy));

            // Returns either the strongest or weakest active enemy depending on the tower's targeting.
            return this.targeting == Targeting.STRONGEST ? fixedList.getLast() : fixedList.getFirst();
        }
    }

    /**
     * Allows the {@link Tower} to attack an {@link Enemy} that is in {@code range}.
     * This method runs using separate {@link Thread}s and does not {@code block} the {@link Thread} in which it was called.
     * @since Ultimate Tower Defense 1.0
     */
    @NonBlocking
    @SuppressWarnings("all")
    public final void startAttacking() {

        // Interrupts the thread controlling tower attacks so that the new attacks can override the old attacks if there were any.
        this.attackThread.interrupt();

        // Creates a new thread that will handle tower attacks and starts it.
        (this.attackThread = new Thread(() -> {

            // Logs that the tower has begun attacking.
            LOGGER.info(STR."Tower \{this} has begun to attack.");

            // A loop that will continuously run until the tower is eliminated.
            while (true) {

                // Gets the tower's current target enemy.
                Enemy target = null;

                // A loop that will iterate until the enemy gets a valid target.
                while (true) {

                    // A try-catch statement that will prevent any exceptions from occurring while the enemy is getting a target.
                    try {

                        // Gets the tower's current target.
                        // A concurrent modification exception may be thrown while finding a target.
                        target = this.getTarget();

                        // Breaks out of the loop since the enemy would have gotten a valid target.
                        break;
                    } catch (ConcurrentModificationException ex) {

                        // Logs that the tower has encountered an error while getting its target.
                        LOGGER.warning(STR."Tower \{this} has encountered exception \{ex} while searching for a target.");
                    }
                }

                // Determines whether the tower's target is either null or alive.
                if (target != null && !target.isAlive()) {

                    // Jumps to the next iteration of the loop preventing any exceptions from occurring.
                    continue;
                }

                // Determines whether the tower's target is null.
                if (target != null) {

                    // Logs that the tower has found a target.
                    LOGGER.info(STR."Tower \{this} has successfully targeted enemy \{target}.");
                }

                // Allows the attack the loop to be broken out of if the tower is eliminated.
                try {

                    // Attacks the tower's current target enemy.
                    this.attack(target);

                    // Determines whether the target is null.
                    if (target != null) {

                        // Causes the current thread to wait for the tower's cool down to end.
                        Thread.sleep(this.coolDown);
                    }
                } catch (InterruptedException ex) {

                    // Determines whether the tower's target is null.
                    if (target != null) {

                        // Logs that the tower's attack has been interrupted and ended.
                        LOGGER.info(STR."Tower \{this} has stopped attacking enemy \{target}.");
                    }

                    // Breaks out of the loop if the current thread is forcefully interrupted.
                    break;
                }

                // Determines whether the tower's target is null.
                if (target != null) {

                    // Logs that the tower has attacked its target.
                    LOGGER.info(STR."Tower \{this} has successfully attacked enemy \{target}.");
                }
            }
        })).start();
    }

    /**
     * Stops all attacks the {@link Tower} may be performing.
     * The {@link Tower}'s attacking {@link Thread} will be interrupted when calling this method.
     * @since Ultimate Tower Defense 1.0
     */
    public final void stopAttacking() {

        // Interrupts the thread responsible for all tower attacks.
        // This will cause an exception to be thrown which will break out of the loop managing tower attacks.
        this.attackThread.interrupt();
    }

    /**
     * Removes the {@link Tower} from its parent {@link Group}.
     * @since Ultimate Tower Defense 1.0
     */
    public synchronized final void eliminate() {

        // Determines whether the tower's parent group is null and whether the tower's parent group contains the tower.
        if (this.parent == null || !this.parent.getChildren().contains(this.loadedTower)) {

            // Prevents the tower from being removed from a null group and from being eliminated more than once.
            return;
        }

        // Performs the tower's death action.
        // This method is unique to each individual inheritor of the tower class.
        this.onDeath();

        // Removes the tower from its parent group.
        this.parent.getChildren().remove(this.loadedTower);

        // Stops all attacks the tower may be performing.
        this.stopAttacking();

        // Removes the tower from the list containing every active tower.
        LIST_OF_ACTIVE_TOWERS.remove(this);

        // Logs that the tower has been eliminated.
        LOGGER.info(STR."Tower \{this} eliminated.");
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
    protected void onDamaged() {
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
    protected void attack(@Nullable final Enemy enemy) throws InterruptedException {
        // This method can be overridden by a subclass so each individual tower can have unique action to do when attacking.
    }

    /**
     * An action performed whenever a {@link Tower} is using a {@code special ability}.
     * By default, this method does nothing.
     * @throws InterruptedException when the {@link Tower} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    public void special() throws InterruptedException {
        // This method can be overridden by a subclass so that each individual tower can have a unique special ability.
    }

    /**
     * An action performed whenever a {@link Tower} is {@code upgraded}.
     * By default, this method does nothing.
     * @throws InterruptedException when the {@link Tower} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    public abstract void upgrade() throws InterruptedException;
}
