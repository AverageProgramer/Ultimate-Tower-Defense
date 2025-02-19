package com.averagegames.ultimatetowerdefense.tools.exceptions;

import com.averagegames.ultimatetowerdefense.tools.annotations.LessThan;
import com.averagegames.ultimatetowerdefense.tools.annotations.verification.LessThanAnnotation;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;

/**
 * The {@link NumberAboveMaximumException} class is a {@link RuntimeException} meant to be thrown when the {@link LessThan} annotation's contract is violated.
 * @see RuntimeException
 * @see IllegalArgumentException
 * @see LessThan
 * @see LessThanAnnotation
 * @author AverageProgramer
 */
@NoArgsConstructor
public class NumberAboveMaximumException extends IllegalArgumentException {

    /**
     * The {@link NumberAboveMaximumException}'s serial version UID.
     */
    @Serial
    private static final long serialVersionUID = -6700582763317659626L;

    /**
     * A constructor that does provide an {@code error} message when a {@link NumberAboveMaximumException} is thrown.
     * @param message the error message to be printed.
     */
    public NumberAboveMaximumException(@NonNls final String message) {

        // Initializes the exception using an error message.
        super(message);
    }

    /**
     * A constructor that provides a {@link Throwable} cause but not a {@code error} message when a {@link NumberAboveMaximumException} is thrown.
     * @param cause the cause of the {@link NumberAboveMaximumException} being thrown.
     */
    public NumberAboveMaximumException(@Nullable final Throwable cause) {

        // Initializes the exception using a throwable cause.
        super(cause);
    }

    /**
     * A constructor that provides both an {@code error} message and a {@link Throwable} cause when a {@link NumberAboveMaximumException} is thrown.
     * @param message the error message to be printed.
     * @param cause the cause of the {@link NumberAboveMaximumException} being thrown.
     */
    public NumberAboveMaximumException(@NonNls final String message, @Nullable final Throwable cause) {

        // Initializes the exception using an error message and a throwable cause.
        super(message, cause);
    }
}
