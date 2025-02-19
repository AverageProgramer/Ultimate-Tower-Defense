package com.averagegames.ultimatetowerdefense.tools.exceptions;

import com.averagegames.ultimatetowerdefense.tools.annotations.GreaterThan;
import com.averagegames.ultimatetowerdefense.tools.annotations.verification.GreaterThanAnnotation;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;

/**
 * The {@link NumberBelowMinimumException} class is a {@link RuntimeException} meant to be thrown when the {@link GreaterThan} annotation's contract is violated.
 * @since Ultimate Tower Defense 1.0
 * @see RuntimeException
 * @see IllegalArgumentException
 * @see GreaterThan
 * @see GreaterThanAnnotation
 * @author AverageProgramer
 */
@NoArgsConstructor
public class NumberBelowMinimumException extends IllegalArgumentException {

    /**
     * The {@link NumberBelowMinimumException}'s serial version UID.
     */
    @Serial
    private static final long serialVersionUID = -9220109153736043517L;

    /**
     * A constructor that does provide an {@code error} message when a {@link NumberBelowMinimumException} is thrown.
     * @param message the error message to be printed.
     * @since Ultimate Tower Defense 1.0
     */
    public NumberBelowMinimumException(@NonNls final String message) {

        // Initializes the exception using an error message.
        super(message);
    }

    /**
     * A constructor that provides a {@link Throwable} cause but not a {@code error} message when a {@link NumberBelowMinimumException} is thrown.
     * @param cause the cause of the {@link NumberBelowMinimumException} being thrown.
     * @since Ultimate Tower Defense 1.0
     */
    public NumberBelowMinimumException(@Nullable final Throwable cause) {

        // Initializes the exception using a throwable cause.
        super(cause);
    }

    /**
     * A constructor that provides both an {@code error} message and a {@link Throwable} cause when a {@link NumberBelowMinimumException} is thrown.
     * @param message the error message to be printed.
     * @param cause the cause of the {@link NumberBelowMinimumException} being thrown.
     * @since Ultimate Tower Defense 1.0
     */
    public NumberBelowMinimumException(@NonNls final String message, @Nullable final Throwable cause) {

        // Initializes the exception using an error message and a throwable cause.
        super(message, cause);
    }
}
