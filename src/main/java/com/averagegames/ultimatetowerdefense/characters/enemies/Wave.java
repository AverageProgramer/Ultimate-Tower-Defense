package com.averagegames.ultimatetowerdefense.characters.enemies;

import com.averagegames.ultimatetowerdefense.maps.Spawner;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Wave} record serves as a way to easily store and manage an {@link Enemy} array.
 * @since Ultimate Tower Defense 1.0
 * @see Enemy
 * @see Spawner
 * @author AverageProgramer
 */
@Accessors(fluent = true) @Getter
public final class Wave {

    /**
     * An array of each {@link Enemy} in the {@link Wave}.
     */
    @NotNull
    private final Enemy[] enemies;

    /**
     * A constructor that creates a new {@link Wave} storing a given {@link Enemy} array.
     * @param enemies the {@link Enemy} array to be stored.
     */
    public <T extends Enemy> Wave(@NotNull final T[] enemies) {

        // Sets the array of enemies in the wave to the newly given array.
        this.enemies = enemies;
    }
}
