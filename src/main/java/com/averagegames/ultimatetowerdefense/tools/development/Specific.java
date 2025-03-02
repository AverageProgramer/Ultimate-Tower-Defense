package com.averagegames.ultimatetowerdefense.tools.development;

import java.lang.annotation.*;

/**
 * The {@link Specific} annotation allows methods annotated by it to specify specific classes that can access and use the method.
 * If a class that is not specified by the {@link Specific} annotation tries to call the method, a {@link UnspecifiedAccessException} will be thrown.
 * Inheriting types overriding the method where the annotation is present and not annotating the overridden method with the {@link Specific} annotation will not remove the contract in place.
 * @since Ultimate Tower Defense 1.0
 * @see Annotation
 * @see SpecificAnnotation
 * @see UnspecifiedAccessException
 * @author AverageProgramer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface Specific {

    /**
     * Gets an array containing each {@link Class} specified to have access to the method by the annotation.
     * The array must be provided when a method is annotated.
     * @return the array of each specified {@link Class}.
     * @since Ultimate Tower Defense 1.0
     */
    Class<?>[] value();

    /**
     * Gets whether the {@code subclasses} of a specified {@code superclass} should be implicitly specified by the annotation.
     * By default, this property is {@code false} and subclasses will not be specified.
     * @return {@code true} if {@code subclasses} of a {@code superclasses} are specified, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    boolean subclasses() default false;
}