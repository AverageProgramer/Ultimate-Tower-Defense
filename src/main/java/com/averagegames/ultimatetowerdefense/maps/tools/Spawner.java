package com.averagegames.ultimatetowerdefense.maps.tools;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Wave;
import javafx.application.Platform;
import javafx.scene.Group;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.averagegames.ultimatetowerdefense.util.LogManager.LOGGER;

/**
 * The {@link Spawner} class serves as a way to automatically and easily {@code spawn} an individual {@link Enemy} or a {@link Enemy} {@link Wave}.
 * @see Enemy
 * @see Wave
 * @author AverageProgramer
 */
@Setter @Getter
public final class Spawner {

    /**
     * The {@link Position} that a {@code spawned} {@link Enemy} will be placed at.
     */
    @Nullable
    private Position spawnPosition;

    /**
     * The time between {@link Enemy} {@code spawns}.
     */
    private int spawnDelay;

    /**
     * The {@link Path} that any {@link Enemy} {@code spawned} by the {@link Spawner} will follow.
     */
    @Nullable
    private Path enemyPathing;

    /**
     * A boolean value that determines whether the {@link Spawner} can {@code spawn} an {@link Enemy}.
     */
    private boolean enableSpawning;

    /**
     * A default, no args constructor for the {@link Spawner} class.
     * @since Ultimate Tower Defense 1.0
     */
    public Spawner() {

        // Initializes the spawner's spawning position to a default, null position.
        this.spawnPosition = null;

        // Initializes the spawning enemies' pathing to a default, null path.
        this.enemyPathing = null;

        // Initializes the boolean that determines whether the spawner can spawn enemies as true.
        this.enableSpawning = true;
    }

    /**
     * A constructor that creates a new {@link Spawner} object with a {@code spawn} {@link Position} at a given {@link Position}.
     * @param spawnPosition the {@link Spawner}'s {@code spawn} {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    public Spawner(@NotNull final Position spawnPosition) {

        // Calls the spawner's default, no-args constructor.
        this();

        // Sets the spawner's spawn position to the provided position.
        this.spawnPosition = spawnPosition;
    }


    /**
     * Sets whether the {@link Spawner} can {@code spawn} an {@link Enemy} to a given boolean value.
     * @param enabled whether the {@link Spawner} can {@code spawn} and {@link Enemy}.
     * @since Ultimate Tower Defense 1.0
     */
    public void enableSpawning(final boolean enabled) {

        // Sets the boolean value that determines whether the spawner can spawn enemies to the given value.
        this.enableSpawning = enabled;
    }

    /**
     * Gets whether the {@link Spawner} has its {@code spawning} enabled.
     * @return {@code true} if {@code spawning} is enabled, {@code false} otherwise.
     */
    @SuppressWarnings("unused")
    public boolean spawningEnabled() {

        // Gets the boolean value that determines whether the spawner can spawn enemies and returns it.
        return this.enableSpawning;
    }

    /**
     * Adds an individual {@link Enemy} to a given {@link Group}.
     * If the {@link Enemy}'s {@link Position} was set prior to calling this method, then that {@link Position} will be replaced with the {@link Spawner}'s {@code spawn} {@link Position}.
     * @param enemy the {@link Enemy} to be spawned.
     * @param group the {@link Group} the {@link Enemy} should be added to.
     * @see Enemy
     */
    public void spawn(@NotNull final Enemy enemy, @NotNull final Group group) {

        // Determines whether the spawner has spawning enabled.
        if (!this.enableSpawning) {

            // Prevents any enemies from spawning.
            return;
        }

        // Sets the enemy's parent group to the given group.
        enemy.setParent(group);

        // Allows for nodes to be added to the group despite the current thread possible not being the JavaFX application thread.
        // Adds the enemy to the previously set group at the previously set position.
        Platform.runLater(() -> {

            // Determines whether the spawner's spawn position is null or not.
            if (this.spawnPosition != null) {

                // Spawns the enemy at the spawner's spawn position.
                enemy.spawn(this.spawnPosition);
            }
        });

        // Determines whether the previously given enemy pathing is null.
        if (this.enemyPathing != null) {

            // Sets the enemy's pathing to the previously set path.
            enemy.setPathing(this.enemyPathing);

            // Determines whether the enemy's reference pathing is null.
            if (enemy.getReferencePathing() == null) {

                // Sets the enemy's reference pathing to the previously given pathing.
                enemy.setReferencePathing(this.enemyPathing);
            }
        }

        // Allows for the enemy to begin moving down its set path without any issues.
        // Begins moving the enemy.
        Platform.runLater(enemy::startMoving);

        // Logs that the spawner has spawned the given enemy.
        LOGGER.info(STR."Spawner \{this} has successfully spawned enemy \{enemy}.");
    }

    /**
     * Adds an {@link Enemy} {@link Wave} to a given {@link Group}.
     * The {@link Spawner}'s will wait its previously set time delay before adding one {@link Enemy} after another.
     * If the {@link Enemy} {@link Position}s were set prior to calling this method, then those {@link Position}s will be replaced with the {@link Spawner}'s {@code spawn} {@link Position}.
     * @param wave the {@link Enemy} {@link Wave} to be {@code spawned}.
     * @param group the {@link Group} the {@link Enemy} {@link Wave} should be added to.
     * @see Wave
     */
    @Blocking
    public void spawn(@NotNull final Wave wave, @NotNull final Group group) {

        // Logs that the spawner has begun spawning the given wave.
        LOGGER.info(STR."Spawner \{this} has begun spawning wave \{wave}.");

        // A loop that will iterate through every enemy in the given wave.
        for (Enemy enemy : wave.enemies()) {

            // Spawns the individual enemy.
            this.spawn(enemy, group);

            // Allows the loop to be broken out of if the spawner is interrupted.
            try {

                // Causes the current thread to wait for the spawner's time delay between spawns.
                Thread.sleep(this.spawnDelay);
            } catch (InterruptedException ex) {

                // Logs that the spawner has stopped spawning the given wave.
                LOGGER.info(STR."Spawner \{this} has stopped spawning wave \{wave}.");

                // Breaks out of the loop if the spawner is forcefully interrupted.
                break;
            }
        }

        // Logs that the spawner has finished spawning the given wave.
        LOGGER.info(STR."Spawner \{this} has finished spawning wave \{wave}.");
    }

    /**
     * Stops the {@link Spawner} from {@code spawning}.
     */
    public void stopSpawning() {

        // Interrupts the current thread which controls enemy spawns.
        // This will cause an exception to be thrown which will break out of the loop spawning the enemies.
        Thread.currentThread().interrupt();
    }
}
