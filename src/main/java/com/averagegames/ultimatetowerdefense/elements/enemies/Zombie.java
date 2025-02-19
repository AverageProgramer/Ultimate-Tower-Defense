package com.averagegames.ultimatetowerdefense.elements.enemies;

import org.intellij.lang.annotations.Identifier;

/**
 * The {@link Zombie} interface is a {@code marker} interface.
 * Any {@link Enemy} implementing this interface is considered a {@link Zombie} and should be treated as such.
 * {@link Zombie}s can not perform any sort of {@code attacks} or {@code specials}.
 * @since Ultimate Tower Defense
 * @see Titan
 * @see Boss
 * @author AverageProgramer
 */
@Identifier
public interface Zombie {
    // The interface has no need for any abstract methods when a class implements it.
}
