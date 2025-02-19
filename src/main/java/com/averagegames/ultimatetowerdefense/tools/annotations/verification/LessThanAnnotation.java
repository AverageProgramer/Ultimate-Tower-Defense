package com.averagegames.ultimatetowerdefense.tools.annotations.verification;

import java.lang.reflect.AnnotatedElement;

import com.averagegames.ultimatetowerdefense.tools.exceptions.NumberAboveMaximumException;
import com.averagegames.ultimatetowerdefense.tools.annotations.LessThan;
import com.averagegames.ultimatetowerdefense.tools.annotations.NotInstantiable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link LessThanAnnotation} class serves as a way to verify that any number annotated by the {@link LessThan} annotation is given a value {@code less} than what the annotation specifies that it has to be.
 * @since Ultimate Tower Defense 1.0
 * @see LessThan
 * @see NumberAboveMaximumException
 * @author AverageProgramer
 */
@NotInstantiable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LessThanAnnotation {

    /**
     * Verifies that a given annotated number is {@code less} than what the number's annotation specifies that it has to be.
     * @param element the number as an {@link AnnotatedElement} object.
     * @param value the number to verify.
     * @since Ultimate Tower Defense 1.0
     * @see LessThan
     * @see NumberAboveMaximumException
     */
    public static void verify(@NotNull final AnnotatedElement element, final double value) {

        // Asserts that the parameter should be annotated.
        // The reason assertions are being used here is that this expression will always evaluate to true.
        assert element.isAnnotationPresent(LessThan.class) : STR."Number is not annotated with \{LessThan.class.getName()}";

        // Determines if the provided value is less than the annotated parameter's specified maximum value.
        if (value >= element.getAnnotation(LessThan.class).value()) {

            // Throws an exception which causes the program to finish.
            throw new NumberAboveMaximumException(STR."Provided int value \{value} is not less than the specified int value");
        }
    }
}
