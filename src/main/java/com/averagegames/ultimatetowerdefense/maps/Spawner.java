package com.averagegames.ultimatetowerdefense.maps;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Wave;
import javafx.application.Platform;
import javafx.scene.Group;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@link Spawner} class serves as a way to automatically and easily {@code spawn} an individual {@link Enemy} or a {@link Enemy} {@link Wave}.
 * @see Enemy
 * @see Wave
 * @author AverageProgramer
 */
public final class Spawner {

    /**
     * The {@link Position} that a {@code spawned} {@link Enemy} will be placed at.
     */
    @Nullable
    @Setter @Getter
    private Position spawnPosition;

    /**
     * The time between {@link Enemy} {@code spawns}.
     */
    @Setter @Getter
    private int spawnDelay;

    /**
     * The {@link Path} that any {@link Enemy} {@code spawned} by the {@link Spawner} will follow.
     */
    @Nullable
    @Setter @Getter
    private Path enemyPathing;

    /**
     * A {@link Thread} that is responsible for handling all {@link Enemy} {@code spawns}.
     */
    @NotNull
    private Thread spawnThread;

    /**
     * A default, no args constructor for the {@link Spawner} class.
     * @since Ultimate Tower Defense 1.0
     */
    public Spawner() {

        // Initializes the spawner's spawning position to a default, null position.
        this.spawnPosition = null;

        // Initializes the spawning enemies' pathing to a default, null path.
        this.enemyPathing = null;

        // Initializes the thread responsible for spawning enemies.
        this.spawnThread = new Thread(() -> {
            // This thread does nothing by default.
        });
    }

    /**
     * A constructor that creates a new {@link Spawner} object with a {@code spawn} {@link Position} at a given {@link Position}.
     * @param spawnPosition the {@link Spawner}'s {@code spawn} {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    public Spawner(@NotNull final Position spawnPosition) {

        // Calls the spawner's default no-args constructor.
        this();

        // Sets the spawner's spawn position to the provided position.
        this.spawnPosition = spawnPosition;
    }

    /**
     * Adds an individual {@link Enemy} to a given {@link Group}.
     * If the {@link Enemy}'s {@link Position} was set prior to calling this method, then that {@link Position} will be replaced with the {@link Spawner}'s {@code spawn} {@link Position}.
     * @param enemy the {@link Enemy} to be spawned.
     * @param group the {@link Group} the {@link Enemy} should be added to.
     * @see Enemy
     */
    public void spawn(@NotNull final Enemy enemy, @NotNull final Group group) {

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
            enemy.setPathing(enemyPathing);
        }

        // Begins moving the enemy.
        enemy.startMoving();
    }

    /**
     * Adds an {@link Enemy} {@link Wave} to a given {@link Group}.
     * The {@link Spawner}'s will wait its previously set time delay before adding one {@link Enemy} after another.
     * If the {@link Enemy} {@link Position}s were set prior to calling this method, then those {@link Position}s will be replaced with the {@link Spawner}'s {@code spawn} {@link Position}.
     * @param wave the {@link Enemy} {@link Wave} to be {@code spawned}.
     * @param group the {@link Group} the {@link Enemy} {@link Wave} should be added to.
     * @see Wave
     */
    public void spawn(@NotNull final Wave wave, @NotNull final Group group) {

        // Creates a new thread that will handle enemy spawns.
        this.spawnThread = new Thread(() -> {

            // A loop that will iterate through every enemy in the given wave.
            for (Enemy enemy : wave.enemies()) {

                // Spawns the individual enemy.
                this.spawn(enemy, group);

                // Allows the loop to be broken out of if the spawner is interrupted.
                try {

                    // Causes the current thread to wait for the spawner's time delay between spawns.
                    Thread.sleep(this.spawnDelay);
                } catch (InterruptedException ex) {

                    // Breaks out of the loop if the spawner is forcefully interrupted.
                    break;
                }
            }
        });

        // Starts the thread so that enemies can be spawned.
        this.spawnThread.start();
    }

    /**
     * Stops the {@link Spawner} from {@code spawning}.
     */
    public void stopSpawning() {

        // Interrupts the thread controlling enemy spawns.
        // This will cause an exception to be thrown which will break out of the loop spawning the enemies.
        this.spawnThread.interrupt();
    }
}
