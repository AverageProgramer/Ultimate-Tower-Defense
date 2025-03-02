package com.averagegames.ultimatetowerdefense.characters.enemies;

import com.averagegames.ultimatetowerdefense.characters.enemies.survival.titans.LootBoxTitan;
import org.intellij.lang.annotations.Identifier;

import java.lang.annotation.*;

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
@Documented
@Identifier
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Titan {
    // This annotation does nothing by default.
}
