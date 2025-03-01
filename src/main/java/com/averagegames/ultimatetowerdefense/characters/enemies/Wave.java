package com.averagegames.ultimatetowerdefense.characters.enemies;

import com.averagegames.ultimatetowerdefense.maps.Spawner;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Wave} record serves as a way to easily store and manage an {@link Enemy} array.
 *
 * @param enemies An array of each {@link Enemy} in the {@link Wave}.
 * @author AverageProgramer
 * @see Enemy
 * @see Spawner
 * @since Ultimate Tower Defense 1.0
 */
public record Wave(@NotNull Enemy[] enemies) {}
