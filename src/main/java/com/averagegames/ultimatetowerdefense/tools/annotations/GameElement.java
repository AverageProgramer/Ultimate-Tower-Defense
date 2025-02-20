package com.averagegames.ultimatetowerdefense.tools.annotations;

import org.intellij.lang.annotations.Identifier;
import org.intellij.lang.annotations.Pattern;

import java.lang.annotation.*;

/**
 * The {@link GameElement} annotation indicates that an annotated {@link Class} is a feature that will be visible during game runtime.
 * This annotation is only for documentation purposes and does not provide any further code functionality.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Identifier
public @interface GameElement {

    /**
     * Gets the type of {@code element} that the annotated variable is.
     * Valid types include, {@code character}, {@code component}, {@code graphic}, or none at all.
     * @return the {@code element} type.
     * @defaultValue an empty {@link String}.
     * @since Ultimate Tower Defense 1.0
     */
    @Pattern("[character-component-graphic]")
    String type() default "";
}
