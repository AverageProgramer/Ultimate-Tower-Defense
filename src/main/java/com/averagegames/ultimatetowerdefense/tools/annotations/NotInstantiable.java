package com.averagegames.ultimatetowerdefense.tools.annotations;

import org.intellij.lang.annotations.Identifier;

import java.lang.annotation.*;

/**
 * The {@link NotInstantiable} annotation is meant to be annotated by a {@link Class} that should not be instantiated.
 * This annotation is only for documentation purposes and does not provide any further code functionality.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Identifier
public @interface NotInstantiable {
    // The annotation has no need for any parameters when a class is annotated with it.
}
