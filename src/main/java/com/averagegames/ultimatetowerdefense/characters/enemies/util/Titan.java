package com.averagegames.ultimatetowerdefense.characters.enemies.util;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import org.intellij.lang.annotations.Identifier;

/**
 * The {@link Titan} interface is a {@code marker} interface.
 * Any {@link Enemy} implementing this interface is considered a {@link Titan} and should be treated as such.
 * {@link Titan}s can perform {@code attacks} but do not have any {@code specials}.
 * @since Ultimate Tower Defense
 * @see Zombie
 * @see Boss
 * @author AverageProgramer
 */
@Identifier
public interface Titan extends Zombie {
    // The interface has no need for any abstract methods when a class implements it.
}
