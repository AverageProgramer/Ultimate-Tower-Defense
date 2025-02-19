package com.averagegames.ultimatetowerdefense.elements.enemies;

import com.averagegames.ultimatetowerdefense.tools.annotations.GreaterThan;
import com.averagegames.ultimatetowerdefense.tools.annotations.Specific;
import com.averagegames.ultimatetowerdefense.tools.exceptions.NumberBelowMinimumException;
import com.averagegames.ultimatetowerdefense.tools.exceptions.UnspecifiedAccessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@link EnemyTest} class contains a series of tests on the various features within the {@link Enemy} class.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
class EnemyTest {

    /**
     * Tests whether accessing a method in the {@link Enemy} class annotated by the {@link Specific} annotation fails.
     * The {@link EnemyTest} class does not have {@code specified} access to the method, so trying to access it should throw a {@link UnspecifiedAccessException}.
     * @see Specific
     * @see UnspecifiedAccessException
     */
    @Test
    void enemySpawnShouldNotThrowAnException() {

        // The "spawn" method in the enemy class has not specified this class having access to call that method.
        assertDoesNotThrow(() -> {

            // Calls the spawn method on a newly created enemy.
            // Because this class does not have specified access to the method, this should throw an exception.
            new Enemy() {}.spawn();
        });
    }

    /**
     * Tests whether passing a number below the specified {@code minimum} into a method in the {@link Enemy} class where the parameter is annotated by the {@link GreaterThan} annotation fails.
     * The value passed in will be {@code -1} which should throw a {@link NumberBelowMinimumException}.
     * @see GreaterThan
     * @see NumberBelowMinimumException
     */
    @Test
    void invalidParametersShouldThrowAnException() {

        // The "heal" method in the enemy class has its parameter is specified to be greater than -1 when passed in.
        assertThrows(NumberBelowMinimumException.class, () -> {

            // Calls the "heal" method on the newly created enemy.
            // Because -1 is passed in and the parameter is required to be greater than -1, this should throw an exception.
            new Enemy() {}.heal(-1);
        });

        // The "dealDamage" method in the enemy class has its parameter is specified to be greater than -1 when passed in.
        assertThrows(NumberBelowMinimumException.class, () -> {

            // Calls the "dealDamage" method on the newly created enemy.
            // Because -1 is passed in and the parameter is required to be greater than -1, this should throw an exception.
            new Enemy() {}.dealDamage(-1);
        });
    }

    /**
     * Tests whether passing a number above the specified {@code minimum} into a method in the {@link Enemy} class where the parameter is annotated by the {@link GreaterThan} annotation does not fail.
     * The value passed in will be {@code 0} which should not throw a {@link NumberBelowMinimumException}.
     * @see GreaterThan
     * @see NumberBelowMinimumException
     */
    @Test
    void validParametersShouldNotThrowAnException() {

        // The "heal" and "dealDamage" methods in the enemy class both have their parameters specified to be greater than -1 when passed in.
        assertDoesNotThrow(() -> {

            // Calls the "heal" method on the newly created enemy.
            // Because 0 is passed in and the parameter is required to be greater than -1, this should not throw an exception.
            new Enemy() {}.heal(0);

            // Calls the "dealDamage" method on the newly created enemy.
            // Because 0 is passed in and the parameter is required to be greater than -1, this should not throw an exception.
            new Enemy() {}.dealDamage(0);
        });
    }
}