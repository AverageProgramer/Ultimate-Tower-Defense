package com.averagegames.ultimatetowerdefense.characters.towers;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;

/**
 * The {@link Targeting} enum provides several unique {@code modes} that a {@link Tower} can use while {@code attacking}.
 * Based on what {@link Targeting} {@code mode} is selected, the {@link Tower} will prioritize a certain {@link Enemy}.
 * @since Ultimate Tower Defense 1.0
 * @see Tower
 * @see Enemy
 * @author AverageProgramer
 */
public enum Targeting {

    /**
     * The {@link Targeting} indicating that a {@link Tower} should attack the {@code first} {@link Enemy}.
     */
    FIRST,

    /**
     * The {@link Targeting} indicating that a {@link Tower} should attack the {@code last} {@link Enemy}.
     */
    LAST,

    /**
     * The {@link Targeting} indicating that a {@link Tower} should attack the {@code strongest} {@link Enemy}.
     */
    STRONGEST,

    /**
     * The {@link Targeting} indicating that a {@link Tower} should attack the {@code weakest} {@link Enemy}.
     */
    WEAKEST,

    /**
     * The {@link Targeting} indicating that a {@link Tower} should attack no {@link Enemy}.
     */
    NONE
}
