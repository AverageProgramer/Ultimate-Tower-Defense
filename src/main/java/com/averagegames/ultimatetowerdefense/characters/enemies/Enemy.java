package com.averagegames.ultimatetowerdefense.characters.enemies;

import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.maps.tools.Base;
import com.averagegames.ultimatetowerdefense.maps.tools.MapBuilder;
import com.averagegames.ultimatetowerdefense.maps.tools.Path;
import com.averagegames.ultimatetowerdefense.maps.tools.Position;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.scenes.GameScene;
import com.averagegames.ultimatetowerdefense.util.TranslationHandler;
import com.averagegames.ultimatetowerdefense.util.ImageLoader;
import com.averagegames.ultimatetowerdefense.util.Timer;
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
import static com.averagegames.ultimatetowerdefense.util.LogManager.LOGGER;

/**
 * The {@link Enemy} class serves as a {@code super} class to all in-game enemies.
 * Anything that extends this class is considered an {@link Enemy} and can perform several actions including following specific {@link Path}s and {@code attacking}.
 * @implNote {@link Enemy} subclasses will need to set the attributes within the {@link Enemy} class manually to be properly customized.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
@SuppressWarnings("all")
public abstract class Enemy {

    /**
     * A {@link List} containing every active {@link Enemy} in a game.
     */
    @NotNull
    public static final List<@NotNull Enemy> LIST_OF_ACTIVE_ENEMIES = Collections.synchronizedList(new LinkedList<>());

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
     * The {@link Enemy}'s speed in pixels per second.
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    protected double speed;

    /**
     * The money the {@code player} will receive when damaging the {@link Enemy}.
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    @Accessors(makeFinal = true) @Getter
    protected int income;

    /**
     * The {@link Enemy}'s cool down in milliseconds between {@code attacks}.
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    protected int coolDown;

    /**
     * The {@link Enemy}'s current {@code health}.
     */
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED) @Getter
    protected int health;

    /**
     * The {@link Enemy}'s current shield.
     */
    @Accessors(makeFinal = true) @Setter(AccessLevel.PROTECTED) @Getter
    protected int shield;

    /**
     * The {@code damage} needed to break through the {@link Enemy}'s shield.
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    protected int shieldBreak;

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
    @Accessors(makeFinal = true) @Getter
    private final ImageLoader loadedEnemy;


    /**
     * The {@link Enemy}'s {@code range}.
     */
    @NotNull
    @Accessors(makeFinal = true) @Getter
    private final Circle range;

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
    @Range(from = 0, to = Integer.MAX_VALUE)
    @Accessors(makeFinal = true) @Setter @Getter
    private int positionIndex;

    /**
     * A {@link TranslationHandler} responsible for handling all {@link Enemy} {@code movement}.
     */
    @NotNull
    protected final TranslationHandler animation;

    /**
     * A {@link Timer} responsible for handling all {@link Enemy} {@code attacks}.
     */
    @NotNull
    protected final Timer attacks;

    /**
     * A boolean value that determines whether the {@link Enemy} should perform its on {@code event} actions.
     */
    private boolean enableActions;

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

        // Initializes the boolean that determines whether the enemy should perform on event actions to true.
        this.enableActions = true;

        // Initializes the enemy's animation to a default animation.
        this.animation = new TranslationHandler();

        // Initializes the enemy's attack timer to a default timer.
        this.attacks = new Timer();
    }

    /**
     * Sets whether the {@link Enemy} should perform its on {@code event} actions to a given boolean value.
     * @param enabled whether the {@link Enemy} should perform its on {@code event} actions.
     * @since Ultimate Tower Defense 1.0
     */
    @Contract(mutates = "true")
    public void enableActions(final boolean enabled) {

        // Sets the boolean value that determines whether the enemy should perform its on event actions to the given value.
        this.enableActions = enabled;
    }

    /**
     * Gets whether the {@link Enemy} has its on {@code event} actions enabled.
     * @return {@code true} if the on {@code event} actions are enabled, {@code false} otherwise.
     */
    public boolean actionsEnabled() {

        // Gets the boolean value that determines whether the enemy should perform its on event actions and returns it.
        return this.enableActions;
    }

    /**
     * Adds a given amount to the {@link Enemy}'s current {@code health}.
     * @param amount damage the amount to add to the {@link Enemy}'s {@code health}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void heal(@Range(from = 0, to = Integer.MAX_VALUE) final int amount) {

        // Determines whether on event actions have been enabled for the enemy.
        if (this.enableActions) {

            // Performs the enemy's on healed action.
            // This method is unique to each individual inheritor of the enemy class.
            this.onHeal();
        }

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

        // Determines whether on event actions have been enabled for the enemy.
        if (this.enableActions) {

            // Performs the enemy's on damaged action.
            // This method is unique to each individual inheritor of the enemy class.
            this.onDamaged();
        }

        // Determines whether the enemy's shield is greater than 0.
        if (this.shield > 0) {

            // Determines whether the damage dealt to the enemy is enough to break through the enemy's shield.
            if (damage >= this.shieldBreak) {

                // Damages the enemy's shield.
                this.shield -= damage;
            }

            // Prevents the enemy from losing additional health.
            return;
        }

        // Removes the given amount from the enemy's health.
        this.health -= damage;

        // A loop that will iterate one time through each health point the enemy lost.
        for (int i = 0; i < damage; i++) {

            // Adds the enemy's income to the player's current cash.
            Player.cash += this.income;
        }

        // Updates the in-game text that displays the player's current cash.
        Platform.runLater(() -> GameScene.CASH_TEXT.setText(STR."$\{Player.cash}"));

        // Logs that the enemy has been damaged by a given amount.
        LOGGER.info(STR."Enemy \{this} health has been decreased by \{damage}.");

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
        Position rangePos = new Position(range.getCenterX() + (range.getTranslateX() + range.getStrokeWidth() / 2), range.getCenterY() + (range.getTranslateY() + range.getStrokeWidth() / 2));

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

        // Determines whether on event actions have been enabled for the enemy.
        if (this.enableActions) {

            // Performs the enemy's spawn action.
            // This method is unique to each individual inheritor of the enemy class.
            this.onSpawn();
        }

        // Determines whether the enemy's image is null.
        if (this.image != null) {

            // Loads the enemy's image.
            this.loadedEnemy.setImage(this.image);
        }

        // Sets the enemy's view order to its current y position.
        this.loadedEnemy.setViewOrder(-this.getPosition().y());

        // Adds a listener to the enemy's translate y property.
        // This will change the enemy's view order depending on its current y position.
        this.loadedEnemy.translateYProperty().addListener(((observable, oldValue, newValue) -> this.loadedEnemy.setViewOrder(-this.getPosition().y())));

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
        return this.parent != null && this.parent.getChildren().contains(this.loadedEnemy) && this.health > 0;
    }

    /**
     * Begins moving the {@link Enemy} along a set {@link Path}.
     * The {@link Path} will need to be set prior to calling this method.
     * @since Ultimate Tower Defense 1.0
     */
    @NonBlocking
    public final void startMoving() {

        // Sets up the animation, setting the node to control to the enemy's image, and setting the speed to use to the enemy's speed.

        this.animation.setNode(this.loadedEnemy);
        this.animation.setSpeed(this.speed);

        // Sets the animation's destination to the first position on the path.
        this.animation.setDestination(new Position(this.pathing.positions()[0].x() - (this.image != null ? this.image.getWidth() / 2 : 0), this.pathing.positions()[0].y() - (this.image != null ? this.image.getHeight() : 0)));

        // Sets the animation's on finished action to properly update the next position the enemy needs to travel to.
        this.animation.setOnFinished(() -> {

            // Determines whether the enemy's position index is within the enemy's reference pathing.
            if (this.positionIndex < this.referencePathing.positions().length - 1) {

                // Updates the enemy's position index to proplery reflect the enemy's current position.
                this.positionIndex++;

                // Sets the animation's destination to the next position on the path.
                this.animation.setDestination(new Position(this.referencePathing.positions()[this.positionIndex].x() - (this.image != null ? this.image.getWidth() / 2 : 0), this.referencePathing.positions()[this.positionIndex].y() - (this.image != null ? this.image.getHeight() : 0)));

                // Allows the enemy's animation to be started without any issues.
                // Starts the animation.
                Platform.runLater(() -> this.animation.start());
            } else {

                // Determines whether the enemy's health is greater than 0.
                if (this.health > 0) {

                    // Damages the base by however much health the enemy has remaining.
                    Base.health -= this.health;
                }

                // Determines whether the base's health is below 0.
                if (Base.health <= 0) {

                    // Sets the base's health to 0.
                    Base.health = 0;

                    // Stops new enemies from spawning.
                    MapBuilder.ENEMY_SPAWNER.stopSpawning();

                    // Disables all enemy spawning.
                    MapBuilder.ENEMY_SPAWNER.enableSpawning(false);

                    // A loop that will iterate through the list containing every active enemy.
                    LIST_OF_ACTIVE_ENEMIES.forEach(enemy -> {

                        // Determines whether the enemy is not the current enemy.
                        if (enemy != this) {

                            // Disables the enemy's on event actions.
                            enemy.enableActions(false);

                            // Allows for nodes to be removed to the group despite the current thread possible not being the JavaFX application thread.
                            // Eliminates each remaining enemy from their parent groups.
                            Platform.runLater(enemy::eliminate);
                        }
                    });

                    // A try-catch statement that will catch any exceptions that occur when playing an audio file.
                    try {

                        // Stops the global audio player from playing an audio file.
                        GameScene.GLOBAL_PLAYER.stop();

                        // Sets the pathname of the global audio player to a new audio file.
                        GameScene.GLOBAL_PLAYER.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/music/(Official) Tower Defense Simulator OST_ - You Lost!.wav");

                        // Plays the previously set audio file.
                        GameScene.GLOBAL_PLAYER.play();
                    } catch (Exception ex) {
                        // The exception does not need to be handled.
                    }
                }

                // Updates the in-game text that displays the base's current health.
                Platform.runLater(() -> GameScene.HEALTH_TEXT.setText(STR."\{Base.health} HP"));

                // Removes the enemy from its parent group now that it has finished its path.
                Platform.runLater(this::eliminate);
            }
        });

        // Allows the enemy's animation to be started without any issues.
        // Starts the animation.
        Platform.runLater(() -> this.animation.start());
    }

    /**
     * Stops moving the {@link Enemy} on its current {@link Path}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void stopMoving() {

        // Allows the enemy's animation to be stopped without any issues.
        // Stops the animation.
        Platform.runLater(() -> this.animation.stop());
    }

    /**
     * Updates the {@link Enemy}'s movement to reflect any {@code speed} changes that may have occurred.
     * @since Ultimate Tower Defense 1.0
     */
    public final void updateMovement() {

        // Stops the enemy's movement.
        this.stopMoving();

        // Resumes the enemy's movement.
        this.startMoving();
    }

    /**
     * updates the {@link Enemy}'s {@link Path} to begin at the {@link Enemy}'s current {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void updatePathing() {

        // Creates a list of positions that will be used to set the enemy's pathing.
        ArrayList<Position> positions = new ArrayList<>();

        // A loop that will iterate through every position in the enemy's reference pathing.
        for (int i = 0; i < Objects.requireNonNull(this.getReferencePathing()).positions().length; i++) {

            // Determines whether the index is less the enemy's current position index.
            if (i < this.getPositionIndex()) {

                // Jumps to the next iteration of the loop.
                continue;
            }

            // Adds the current position to the list.
            positions.add(this.getReferencePathing().positions()[i]);
        }

        // Sets the enemy's pathing to the positions in the list.
        this.pathing = new Path(positions.toArray(Position[]::new));
    }

    /**
     * Determines whether the {@link Enemy} can {@code attack} the given {@link Tower}.
     * This is a helper method.
     * @param tower the {@link Tower} to {@code attack}.
     * @since Ultimate Tower Defense 1.0
     */
    @Contract(pure = true)
    private boolean canAttack(@NotNull final Tower tower) {

        // Returns whether the given tower is within the enemy's range.
        return tower.isInRange(this.range, this);
    }

    /**
     * Gets the {@link Tower} that the {@link Enemy} is most likely to {@code attack}.
     * @return the {@link Tower} to {@code attack}.
     * @since Ultimate Tower Defense 1.0
     */
    private @Nullable Tower getTarget() {

        // Creates a new list of towers that will not contain any enemies the tower can't attack.
        // This will prevent targeting issues when the enemy is trying to find a target tower.

        List<Tower> fixedList = new ArrayList<>(LIST_OF_ACTIVE_TOWERS);
        fixedList.removeIf(tower -> !this.canAttack(tower));

        // Returns a random tower that they enemy can attack.
        return fixedList.isEmpty() ? null : fixedList.get((int) (Math.random() * fixedList.size()));
    }

    /**
     * Allows the {@link Enemy} to attack a {@link Tower} that is in {@code range}.
     * @since Ultimate Tower Defense 1.0
     */
    @NonBlocking
    public final void startAttacking() {

        // Stops the timer responsible for enemy attacks.
        this.attacks.stop();

        // Sets the handle time of the timer to the enemy's cool down between attacks.
        this.attacks.setHandleTime(this.coolDown);

        // Sets the action to be performed by the timer.
        // This action will be performed once for every cool down time that passes.
        this.attacks.setOnHandle(() -> {

            // Sets the thread's uncaught exception handler to log a warning message when an exception occurs.
            Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> LOGGER.warning(STR."Exception \{throwable} has occurred while enemy \{this} was attacking"));

            // Logs that the enemy has begun attacking.
            LOGGER.info(STR."Enemy \{this} has begun to attack.");

            // Gets the tower's current target enemy.
            Tower target = this.getTarget();

            // Determines whether the enemy's target is null or if the enemy is not a summoner.
            if (target == null && !this.getClass().isAnnotationPresent(Summoner.class)) {

                // Resets the timer responsible for handling enemy attacks.
                this.attacks.reset();
            } else if (target != null) {

                // Logs that the enemy has found a target.
                LOGGER.info(STR."Enemy \{this} has successfully targeted tower \{target}.");
            }

            // Allows the attack the loop to be broken out of if the tower is eliminated.
            try {

                // Attacks the enemy's current target tower.
                this.attack(target);
            } catch (InterruptedException ex) {

                // Determines whether the enemy's target tower is null.
                if (target != null) {

                    // Logs that the enemy's attack has been interrupted and ended.
                    LOGGER.info(STR."Enemy \{this} has stopped attacking tower \{target}.");
                }

                // Stops the timer from continuing if the enemy's attack is forcefully interrupted.
                this.attacks.stop();
            }

            // Determines whether the enemy's target is not null.
            if (target != null) {

                // Logs that the enemy has attacked its target.
                LOGGER.info(STR."Enemy \{this} has successfully attacked tower \{target}.");
            }
        });

        // Starts the timer responsible for enemy attacks.
        this.attacks.start();
    }

    /**
     * Stops all attacks the {@link Enemy} may be performing.
     * @since Ultimate Tower Defense 1.0
     */
    public final void stopAttacking() {

        // Stops the timer responsible for handling enemy attacks.
        this.attacks.stop();
    }

    /**
     * Removes the {@link Enemy} from its parent {@link Group}.
     * @since Ultimate Tower Defense 1.0
     */
    public final void eliminate() {

        // Determines whether the enemy's parent group is null and whether the enemy was already eliminated from its parent group.
        if (this.parent == null || !this.parent.getChildren().contains(this.loadedEnemy)) {

            // Prevents the enemy from being removed from a null group and from being eliminated more than once.
            return;
        }

        // Determines whether on event actions have been enabled for the enemy.
        if (this.enableActions) {

            // Performs the enemy's death action.
            // This method is unique to each individual inheritor of the enemy class.
            this.onDeath();
        }

        // Sets the loaded enemy's image to null.
        this.loadedEnemy.setImage(null);

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
