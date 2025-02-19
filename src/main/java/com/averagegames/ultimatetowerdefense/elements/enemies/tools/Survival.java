package com.averagegames.ultimatetowerdefense.elements.enemies.tools;

import java.lang.annotation.*;

/**
 * The {@link Survival} annotation indicates that an annotated {@link com.averagegames.ultimatetowerdefense.elements.enemies.Enemy} will be used in the {@code survival} portion of the game.
 * This annotation is only for documentation purposes and does not provide any further code functionality.
 * @since Ultimate Tower Defense 1.0
 * @see Annotation
 * @see com.averagegames.ultimatetowerdefense.elements.enemies.Enemy
 * @author AverageProgramer
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Survival {
    // The annotation has no need for any parameters when a class is annotated with it.
}
