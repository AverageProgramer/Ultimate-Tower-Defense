package com.averagegames.ultimatetowerdefense.util.development;

import java.lang.annotation.*;

/**
 * The {@link Prohibited} annotation allows methods annotated by it to prohibit specific classes from accessing and using the methods.
 * If a method that prohibited by the {@link Prohibited} annotation tries to call the method, a {@link ProhibitedAccessException} will be thrown.
 * Inheriting types overriding the method where the annotation is present and not annotating the overridden method with the {@link Prohibited} annotation will not remove the contract in place.
 * @since Ultimate Tower Defense 1.0
 * @see Annotation
 * @see ProhibitedAnnotation
 * @see ProhibitedAccessException
 * @author AverageProgramer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface Prohibited {

    /**
     * Gets an array containing each {@link Class} prohibited from accessing the method by the annotation.
     * The array must be provided when a method is annotated.
     * @return the array of each prohibited {@link Class}.
     * @since Ultimate Tower Defense 1.0
     */
    Class<?>[] value();

    /**
     * Gets whether the {@code subclasses} of a specified {@code superclasses} should be implicitly prohibited by the annotation.
     * By default, this property is {@code false} and {@code subclasses} will not be prohibited.
     * @return {@code true} if {@code subclasses} of a {@code superclass} are prohibited, {@code false} otherwise.
     * @since Ultimate Tower Defense 1.0
     */
    boolean subclasses() default false;
}