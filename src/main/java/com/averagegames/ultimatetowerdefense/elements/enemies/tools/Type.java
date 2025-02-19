package com.averagegames.ultimatetowerdefense.elements.enemies.tools;

import com.averagegames.ultimatetowerdefense.elements.enemies.Enemy;

/**
 * The {@link Type} enum provides several different {@link Enemy} types that can allow for varied attacks from a {@link com.averagegames.ultimatetowerdefense.elements.towers.Tower}.
 * Depending on what {@link Type} an {@link Enemy} is, certain {@link com.averagegames.ultimatetowerdefense.elements.towers.Tower}s may be able to {@code attack} it while others may not.
 * @since Ultimate Tower Defense 1.0
 * @see Enum
 * @see Enemy
 * @author AverageProgramer
 */
public enum Type {

    /**
     * The {@link Type} representing an {@link Enemy} that does not require {@code hidden detection} or {@code flying detection} to attack.
     */
    REGULAR,

    /**
     * The {@link Type} representing an {@link Enemy} that requires {@code hidden detection} to attack.
     */
    HIDDEN,

    /**
     * The {@link Type} representing an {@link Enemy} that requires {@code flying detection} to attack.
     */
    FLYING
}
