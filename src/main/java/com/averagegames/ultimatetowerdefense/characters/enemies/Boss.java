package com.averagegames.ultimatetowerdefense.characters.enemies;

import com.averagegames.ultimatetowerdefense.characters.Enemy;
import com.averagegames.ultimatetowerdefense.characters.Tower;

import org.intellij.lang.annotations.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Boss} interface is a {@code marker} interface.
 * Any {@link Enemy} implementing this interface is considered a {@link Boss} and should be treated as such.
 * {@link Boss}es can perform {@code attacks} as well as {@code specials}.
 * @since Ultimate Tower Defense
 * @author AverageProgramer
 */
@Identifier
@FunctionalInterface
public interface Boss {
    /**
     * An action performed whenever an {@link Enemy} is using a {@code special ability}.
     * By default, this method does nothing.`
     * @throws InterruptedException when the {@link Enemy} is {@code eliminated}.
     * @since Ultimate Tower Defense 1.0
     */
    void special(@NotNull final Tower tower) throws InterruptedException;
}
