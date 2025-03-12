package com.averagegames.ultimatetowerdefense.characters.enemies;

import com.averagegames.ultimatetowerdefense.characters.enemies.survival.zombies.Sorcerer;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.maps.Base;
import com.averagegames.ultimatetowerdefense.maps.Path;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.scenes.GameScene;
import com.averagegames.ultimatetowerdefense.util.animation.TranslationHandler;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.assets.ImageLoader;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.*;

import java.util.*;

import static com.averagegames.ultimatetowerdefense.characters.towers.Tower.LIST_OF_ACTIVE_TOWERS;
import static com.averagegames.ultimatetowerdefense.util.development.LogManager.LOGGER;

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
    @Accessors(makeFinal = true) @Setter @Getter
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
     * The {@link Enemy}'s current shield.
     */
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED)
    private int shield;

    /**
     * A boolean that indicates whether the {@link Enemy} is {@code burning}.
     */
    @Accessors(makeFinal = true) @Getter
    private boolean burning;

    /**
     * The {@code damage} the {@link Enemy} can do during an {@code attack}.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    protected int damage;

    @NotNull
    private final Circle range;

    /**
     * The {@link Enemy}'s speed in pixels per second.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    protected double speed;

    /**
     * The current {@link Path} the {@link Enemy} will follow.
     */
    @Nullable
    @Accessors(makeFinal = true) @Setter @Getter
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

    @Range(from = 0L, to = Long.MAX_VALUE)
    protected int coolDown;

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

        // Initializes the enemy's range to a default circle.
        this.range = new Circle();

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

        // Logs that the enemy has been healed by a given amount.
        LOGGER.info(STR."Enemy \{this} health has been increased by \{amount}.");
    }

    /**
     * Removes a given amount from the {@link Enemy}'s current {@code health}.
     * @param damage the amount to remove from the {@link Enemy}'s {@code health}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void damage(@Range(from = 0, to = Integer.MAX_VALUE) final int damage) {

        if (this.shield > 0 && damage < 5) {
            try {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Shield 2.wav");
                player.play();
            } catch (Exception ex) {
                // Ignore
            }

            return;
        } else if (this.shield > 0) {
            this.shield -= damage;

            return;
        }

        // Performs the enemy's on damaged action.
        // This method is unique to each individual inheritor of the enemy class.
        this.onDamaged();

        // Removes the given amount from the enemy's health.
        this.health -= damage;

        // A loop that will iterate one time through each health point the enemy lost.
        for (int i = 0; i < damage; i++) {

            // Adds the enemy's income to the player's current cash.
            Player.cash += this.income;
        }

        // Updates the in-game text that displays the player's current cash.
        Platform.runLater(() -> GameScene.cashText.setText(STR."$\{Player.cash}"));

        // Logs that the enemy has been damaged by a given amount.
        LOGGER.info(STR."Enemy \{this} health has been decreased by \{damage}.");

        // Determines whether the enemy has any health remaining.
        if (this.health <= 0) {

            // Removes the enemy from its parent group.
            Platform.runLater(this::eliminate);
        }
    }

    /**
     * Applies burn {@code damage} and effects to the {@link Enemy} for a given {@code duration}.
     * @param damage the {@code damage} the {@link Enemy} takes every second.
     * @param duration the {@code duration} to remain burned in seconds.
     * @since Ultimate Tower Defense 1.0
     */
    public final void burn(@Range(from = 0, to = Integer.MAX_VALUE) final int damage, @Range(from = 0, to = Integer.MAX_VALUE) final int duration) {

        // Creates a new thread that will handle burn damage and starts it.
        new Thread(() -> {

            // A loop that will iterate the same amount of times as the given duration.
            for (int i = 0; i < duration; i++) {

                // A try-catch statement that will allow the enemy to take burn damage.
                try {

                    // Damages the enemy the given amount.
                    this.damage(damage);

                    // Pauses the thread for one second.
                    // Once the thread resumes the enemy will be damaged again.
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    // The exception does not need to be handled.
                }
            }

            // Determines whether the enemy is a zombie.
            if (this.getClass().isAnnotationPresent(Zombie.class)) {

                // Returns the enemy's speed back to normal.
                this.speed *= ((double) 4 / 3);

                // Updates the enemy's movement so that it will move at its original speed.
                this.updateMovement();
            }

            // Sets the boolean indicating that the enemy is burning to false.
            this.burning = false;
        }).start();

        // Determines whether the enemy is a zombie.
        if (this.getClass().isAnnotationPresent(Zombie.class)) {

            // Lowers the enemy's speed.
            this.speed *= ((double) 3 / 4);

            // Updates the enemy's movement so that it will move at the new speed.
            this.updateMovement();
        }

        // Sets the boolean indicating that the enemy is burning to true.
        this.burning = true;
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
     * Sets the radius of the {@link Circle} representing the {@link Tower}'s {@code range} to a newly given value.
     * @param radius the radius of the {@link Tower}'s {@code range}.
     * @since Ultimate Tower Defense 1.0
     */
    protected final void setRadius(@Range(from = 0, to = Integer.MAX_VALUE) final double radius) {

        // Sets the enemy's range to have the given radius.
        this.range.setRadius(radius);
    }

    /**
     * Gets the radius of the {@link Circle} representing the {@link Enemy}'s {@code range}.
     * @return the radius of the {@link Enemy}'s {@code range}.
     * @since Ultimate Tower Defense 1.0
     */
    public final double getRadius() {

        // Returns the range's current radius.
        return this.range.getRadius();
    }

    /**
     * Determines whether the {@link Enemy} is within the given {@link Circle} at any point.
     * @param range the {@link Circle} to be checked.
     * @return {@code true} if the {@link Enemy} is within the {@link Circle}, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    public final boolean isInRange(@NotNull final Circle range) {

        // The enemy's current position.
        Position currentPos = this.getPosition();

        // The circle's current position.
        Position rangePos = new Position(range.getCenterX(), range.getCenterY());

        // The change in x and change in y for between the enemy and the circle.

        double x = currentPos.x() - rangePos.x();
        double y = currentPos.y() - rangePos.y();

        // The circle's radius.
        double radius = range.getRadius();

        // Returns whether the enemy is within the bounds of the circle.
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= radius;
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

        // Determines whether the enemy's image is null.
        if (this.image != null) {

            // Loads the enemy's image.
            this.loadedEnemy.setImage(this.image);
        }

        // Adds the enemy to the enemy's parent group.
        this.parent.getChildren().add(this.loadedEnemy);

        // Adds the enemy to the list containing every active enemy.
        LIST_OF_ACTIVE_ENEMIES.add(this);

        // Logs that the enemy has been spawned.
        LOGGER.info(STR."Enemy \{this} spawned.");
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

        // Creates a new thread that will handle enemy movement and starts it.
        (this.movementThread = new Thread(() -> {

            // Logs that the enemy has begun moving along its set path.
            LOGGER.info(STR."Enemy \{this} has begun moving along path \{this.pathing}.");

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

                // Logs that the enemy has begun moving to its target destination.
                LOGGER.info(STR."Enemy \{this} moving to position \{position}.");

                // Allows the loop to be broken out of if the animation is interrupted.
                try {

                    // Causes the current thread to wait until the enemy's animation is finished.
                    animation.waitForFinish();
                } catch (InterruptedException ex) {

                    // Stops the enemy's animation.
                    animation.stop();

                    // Logs that the enemy's movement has been interrupted and ended.
                    LOGGER.info(STR."Enemy \{this} has stopped moving along path \{this.pathing}.");

                    // Finishes the method if the animation is forcefully interrupted.
                    // This will most likely only happen when the enemy despawns.
                    return;
                }

                // Increases the position index to represent the position the enemy is at.
                this.positionIndex++;

                // Logs that the enemy has reached its target destination.
                LOGGER.info(STR."Enemy \{this} has successfully reached position \{position}.");
            }

            // Damages the base by however much health the enemy has remaining.
            Base.health -= this.health;

            // Determines whether the base's health is below 0.
            if (Base.health <= 0) {

                // Sets the base's health to 0.
                Base.health = 0;

                // Stops new enemies from spawning.
                GameScene.SPAWNER.stopSpawning();

                // Allows for nodes to be added to the group despite the current thread possible not being the JavaFX application thread.
                // Removes all enemies from their parent groups.
                Platform.runLater(() -> {

                    // A loop that will iterate through the list containing every active enemy.
                    LIST_OF_ACTIVE_ENEMIES.forEach(enemy -> {

                        // Removes the enemy from its parent group.
                        enemy.getParent().getChildren().remove(enemy.loadedEnemy);

                        // Stops all enemy movement and attacks.
                        // This will prevent any threads from continuing to run and use memory.

                        enemy.stopMoving();
                        enemy.stopAttacking();
                    });
                });

                // A try-catch statement that will catch any exceptions that occur when playing an audio file.
                try {

                    // Creates a new audio player that will play an audio file.
                    AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/music/(Official) Tower Defense Simulator OST_ - You Lost!.wav");

                    // Plays the previously given audio file.
                    player.play();
                } catch (Exception ex) {
                    // The exception does not need to be handled.
                }
            }

            // Updates the in-game text that displays the base's current health.
            Platform.runLater(() -> GameScene.baseText.setText(STR."\{Base.health} HP"));

            // Removes the enemy from its parent group now that it has finished its path.
            Platform.runLater(this::eliminate);
        })).start();
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

        ArrayList<Position> positions = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(this.getReferencePathing()).positions().length; i++) {
            if (i < this.getPositionIndex()) {
                continue;
            }
            positions.add(this.getReferencePathing().positions()[i]);
        }
        this.pathing = new Path(positions.toArray(Position[]::new));
    }

    /**
     * Updates the {@link Enemy}'s movement to reflect any {@code speed} changes that may have occurred.
     * @since Ultimate Tower Defense 1.0
     */
    private void updateMovement() {

        // Stops the enemy's movement.
        this.stopMoving();

        // Resumes the enemy's movement.
        this.startMoving();
    }

    /**
     * Determines whether the {@link Enemy} can {@code attack} the given {@link Tower}.
     * This is a helper method.
     * @param tower the {@link Tower} to {@code attack}.
     * @since Ultimate Tower Defense 1.0
     */
    private boolean canAttack(@NotNull final Tower tower) {

        // Returns whether the given tower is within the enemy's range.
        return tower.isInRange(this.range, this.loadedEnemy);
    }

    /**
     * Gets the {@link Tower} that the {@link Enemy} is most likely to {@code attack}.
     * @return the {@link Tower} to {@code attack}.
     * @since Ultimate Tower Defense 1.0
     */
    private @Nullable Tower getTarget() {
        List<Tower> fixedList = new ArrayList<>(LIST_OF_ACTIVE_TOWERS);
        fixedList.removeIf(tower -> !this.canAttack(tower));

        return fixedList.isEmpty() ? null : fixedList.get((int) (Math.random() * fixedList.size()));
    }

    /**
     * Allows the {@link Enemy} to attack a {@link Tower} that is in {@code range}.
     * This method runs using separate {@link Thread}s and does not {@code block} the {@link Thread} in which it was called.
     * @since Ultimate Tower Defense 1.0
     */
    @NonBlocking
    @SuppressWarnings("all")
    public final void startAttacking() {

        // Interrupts the thread controlling enemy attacks so that the new attacks can override the old attacks if there were any.
        this.attackThread.interrupt();

        // Creates a new thread that will handle enemy attacks and starts it.
        (this.attackThread = new Thread(() -> {

            // Logs that the enemy has begun attacking.
            LOGGER.info(STR."Enemy \{this} has begun to attack.");

            // A loop that will continuously run until the tower is eliminated.
            while (true) {

                // Gets the tower's current target enemy.
                Tower target = null;

                // A loop that will iterate until the enemy gets a valid target.
                while (true) {

                    // A try-catch statement that will prevent any exceptions from occurring while the enemy is getting a target.
                    try {

                        // Gets the enemy's current target.
                        // A concurrent modification exception may be thrown while finding a target.
                        target = this.getTarget();

                        // Breaks out of the loop since the enemy would have gotten a valid target.
                        break;
                    } catch (ConcurrentModificationException ex) {

                        // Logs that the enemy has encountered an error while getting its target.
                        LOGGER.warning(STR."Enemy \{this} has encountered exception \{ex} while searching for a target.");
                    }
                }

                // Determines whether the enemy's target is either null or alive.
                if (target != null && !target.isAlive()) {

                    // Jumps to the next iteration of the loop preventing any exceptions from occurring.
                    continue;
                }

                // Determines whether the enemy's target is null.
                if (target != null) {

                    // Logs that the enemy has found a target.
                    LOGGER.info(STR."Enemy \{this} has successfully targeted tower \{target}.");
                }

                // Allows the attack the loop to be broken out of if the tower is eliminated.
                try {

                    // Attacks the enemy's current target tower.
                    this.attack(target);

                    // Determines whether the target is null.
                    if (target != null || this instanceof Sorcerer) {

                        // Causes the current thread to wait for the enemy's cool down to end.
                        Thread.sleep(this.coolDown);
                    }
                } catch (InterruptedException ex) {

                    // Determines whether the enemy's target is null.
                    if (target != null) {

                        // Logs that the enemy's attack has been interrupted and ended.
                        LOGGER.info(STR."Enemy \{this} has stopped attacking tower \{target}.");
                    }

                    // Breaks out of the loop if the current thread is forcefully interrupted.
                    break;
                }

                // Determines whether the enemy's target is null.
                if (target != null) {

                    // Logs that the enemy has attacked its target.
                    LOGGER.info(STR."Enemy \{this} has successfully attacked tower \{target}.");
                }
            }
        })).start();
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

        // Logs that the enemy has been eliminated.
        LOGGER.info(STR."Enemy \{this} eliminated.");
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
    protected void attack(@Nullable final Tower tower) throws InterruptedException {
        // This method can be overridden by a subclass so that each individual enemy can have a unique attack.
    }

    /**
     * An action performed whenever an {@link Enemy} is using a {@code special ability}.
     * By default, this method does nothing.
     * @throws InterruptedException when the {@link Enemy} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    protected void special(@Nullable final Tower tower) throws InterruptedException {
        // This method can be overridden by a subclass so that each individual enemy can have a unique special ability.
    }
}
