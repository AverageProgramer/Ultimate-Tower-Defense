package com.averagegames.ultimatetowerdefense.tools.annotations.verification;

import java.lang.reflect.AnnotatedElement;

import com.averagegames.ultimatetowerdefense.tools.annotations.GreaterThan;
import com.averagegames.ultimatetowerdefense.tools.exceptions.NumberBelowMinimumException;
import com.averagegames.ultimatetowerdefense.tools.annotations.NotInstantiable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link GreaterThanAnnotation} class serves as a way to verify that any number annotated by the {@link GreaterThan} annotation is given a value {@code greater} than what the annotation specifies that it has to be.
 * @since Ultimate Tower Defense 1.0
 * @see GreaterThan
 * @see NumberBelowMinimumException
 * @author AverageProgramer
 */
@NotInstantiable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GreaterThanAnnotation {

    /**
     * Verifies that a given annotated number is {@code greater} than what the number's annotation specifies that it has to be.
     * @param element the number as an {@link AnnotatedElement} object.
     * @param value the number to verify.
     * @since Ultimate Tower Defense 1.0
     * @see GreaterThan
     * @see NumberBelowMinimumException
     */
    public static void verify(@NotNull final AnnotatedElement element, final double value) {

        // Asserts that the parameter should be annotated.
        // The reason assertions are being used here is that this expression will always evaluate to true.
        assert element.isAnnotationPresent(GreaterThan.class) : STR."Number is not annotated with \{GreaterThan.class.getName()}";

        // Determines if the provided number is greater than the annotated parameter's specified minimum value.
        if (value <= element.getAnnotation(GreaterThan.class).value()) {

            // Throws an exception which causes the program to finish.
            throw new NumberBelowMinimumException(STR."Provided int value \{value} is not greater than the specified int value");
        }
    }
    
}
