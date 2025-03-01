package com.averagegames.ultimatetowerdefense.characters.enemies;

import com.averagegames.ultimatetowerdefense.characters.enemies.survival.zombies.LootBox;
import org.intellij.lang.annotations.Identifier;

/**
 * The {@link Zombie} annotation is meant to annotate classes extending the {@link Enemy} class.
 * Any {@link Enemy} annotated with the {@link Zombie} annotation can be considered that type.
 * This annotation is useful for determining what a {@link LootBox} can {@code spawn}.
 * @implSpec The {@link Zombie} annotation does nothing by default and will need manual {@code processing}.
 * @since Ultimate Tower Defense 1.0
 * @see Enemy
 * @see LootBox
 * @author AverageProgramer
 */
@Identifier
public @interface Zombie {
    // This annotation does nothing by default.
}
