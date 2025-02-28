package com.averagegames.ultimatetowerdefense.characters.enemies;

import com.averagegames.ultimatetowerdefense.characters.enemies.survival.LootBoxTitan;
import org.intellij.lang.annotations.Identifier;

/**
 * The {@link Titan} annotation is meant to annotate classes extending the {@link Enemy} class.
 * Any {@link Enemy} annotated with the {@link Titan} annotation can be considered that type.
 * This annotation is useful for determining what a {@link LootBoxTitan} can {@code spawn}.
 * @implSpec The {@link Titan} annotation does nothing by default and will need manual {@code processing}.
 * @since Ultimate Tower Defense 1.0
 * @see Enemy
 * @see LootBoxTitan
 * @author AverageProgramer
 */
@Identifier
public @interface Titan {
    // This annotation does nothing by default.
}
