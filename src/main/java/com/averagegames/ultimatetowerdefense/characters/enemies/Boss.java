package com.averagegames.ultimatetowerdefense.characters.enemies;

import org.intellij.lang.annotations.Identifier;

/**
 * The {@link Boss} annotation is meant to annotate classes extending the {@link Boss} class.
 * Any {@link Enemy} annotated with the {@link Titan} annotation can be considered that type.
 * @implSpec The {@link Boss} annotation does nothing by default and will need manual {@code processing}.
 * @since Ultimate Tower Defense 1.0
 * @see Enemy
 * @author AverageProgramer
 */
@Identifier
public @interface Boss {
    // This annotation does nothing by default.
}
