package com.averagegames.ultimatetowerdefense.game.maps.elements;

import org.jetbrains.annotations.NotNull;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.util.Wave;

import javafx.application.Platform;
import javafx.scene.Group;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The {@link Spawner} class serves as a way to automatically and easily {@code spawn} an individual {@link Enemy} or a {@link Enemy} {@link Wave}.
 * @see com.averagegames.ultimatetowerdefense.game.maps.Map
 * @see Enemy
 * @see Wave
 * @author AverageProgramer
 */
public sealed class Spawner permits Base {

    /**
     * The {@link Position} that a {@code spawned} {@link Enemy} will be placed at.
     */
    @NotNull
    @Accessors(makeFinal = true) @Setter
    private Position spawnPosition;

    private int spawnDelay;

    /**
     * The {@link Path} that any {@link Enemy} {@code spawned} by the {@link Spawner} will follow.
     */
    @NotNull
    @Accessors(makeFinal = true) @Setter
    private Path enemyPathing;

    /**
     * A {@link Thread} that is responsible for handling all {@link Enemy} {@code spawns}.
     */
    private Thread spawnThread;

    public Spawner() {

        // Initializes the spawner's spawning position to a default positions at x = 0 and y = 0.
        this.spawnPosition = new Position(0, 0);

        // Initializes the spawning enemies' pathing using a path with 0 positions.
        this.enemyPathing = new Path(new Position[0]);

        // Initializes the thread responsible for spawning enemies to null.
        // This thread will be updated when the 'spawn' method is called.
        this.spawnThread = null;
    }

    /**
     * A constructor that creates a new {@link Spawner} object with a {@code spawn} {@link Position} at a given {@link Position}.
     * @param spawnPosition the {@link Spawner}'s {@code spawn} {@link Position}.
     */
    public Spawner(@NotNull final Position spawnPosition) {

        // Sets the spawner's spawn position to the provided position.
        this.spawnPosition = spawnPosition;

        // Initializes the spawning enemies' pathing using a path with 0 positions.
        this.enemyPathing = new Path(new Position[0]);

        // Initializes the thread responsible for spawning enemies to null.
        // This thread will be updated when the 'spawn' method is called.
        this.spawnThread = null;
    }

    /**
     * Sets the {@link Spawner}'s delay in milliseconds between individual {@link Enemy} {@code spawns}.
     * @param spawnDelay the time between {@link Enemy} {@code spawns}.
     */
    public final void setSpawnDelay(final int spawnDelay) {

        // Sets the spawner's spawn delay to the newly given value.
        this.spawnDelay = spawnDelay;
    }

    /**
     * Adds an individual {@link Enemy} to a given {@link Group}.
     * If the {@link Enemy}'s {@link Position} was set prior to calling this method, then that {@link Position} will be replaced with the {@link Spawner}'s {@code spawn} {@link Position}.
     * @param enemy the {@link Enemy} to be spawned.
     * @param group the {@link Group} the {@link Enemy} should be added to.
     * @see Enemy
     */
    public final void spawn(@NotNull final Enemy enemy, @NotNull final Group group) {

        // Sets the enemy's parent group to the given group and the enemy's position to the spawner's spawn position.

        enemy.setParent(group);
        enemy.setPosition(this.spawnPosition);

        // Allows for nodes to be added to the group despite the current thread possible not being the JavaFX application thread.
        // Adds the enemy to the previously set group at the previously set position.
        Platform.runLater(enemy::spawn);

        // Sets the enemy's pathing to the previously set path.
        enemy.setPathing(enemyPathing);

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
    public final void spawn(@NotNull final Wave wave, @NotNull final Group group) {

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
    public final void stopSpawning() {

        // Interrupts the thread controlling enemy spawns.
        // This will cause an exception to be thrown which will break out of the loop spawning the enemies.
        this.spawnThread.interrupt();
    }
}
