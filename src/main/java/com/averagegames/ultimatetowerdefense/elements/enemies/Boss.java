package com.averagegames.ultimatetowerdefense.elements.enemies;

import org.intellij.lang.annotations.Identifier;

/**
 * The {@link Boss} interface is a {@code marker} interface.
 * Any {@link Enemy} implementing this interface is considered a {@link Boss} and should be treated as such.
 * {@link Boss}es can perform {@code attacks} as well as {@code specials}.
 * @since Ultimate Tower Defense
 * @see Zombie
 * @see Titan
 * @author AverageProgramer
 */
@Identifier
public interface Boss extends Titan {
    // The interface has no need for any abstract methods when a class implements it.
}
