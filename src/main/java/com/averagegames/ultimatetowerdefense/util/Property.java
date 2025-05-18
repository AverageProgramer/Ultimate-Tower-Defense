package com.averagegames.ultimatetowerdefense.util;

import org.intellij.lang.annotations.Identifier;

import java.lang.annotation.*;

/**
 * The {@link Property} annotation is meant to be annotated by {@code fields} within {@code character} classes.
 * When annotated with the {@link Property} annotation, any warnings related to the {@code fields} will be discarded.
 * The {@link Property} annotation essentially functions as a specific variant of the {@link SuppressWarnings} annotation.
 * @since Ultimate Tower Defense 1.0
 * @see SuppressWarnings
 * @author AverageProgramer
 */
@Documented
@Identifier
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Property {

    /**
     * Gets whether the {@link Property} is unique to a class and not a field within the superclass if there is one.
     * @return {@code true} if the {@link Property} is unique, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    boolean unique() default false;

    /**
     * Gets whether the {@link Property} is a mutable {@link Property}.
     * @return {@code true} if the {@link Property} is mutable, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    boolean mutable() default false;
}
