package com.averagegames.ultimatetowerdefense.tools.exceptions;

import java.io.Serial;

import com.averagegames.ultimatetowerdefense.tools.annotations.Prohibited;
import com.averagegames.ultimatetowerdefense.tools.annotations.verification.ProhibitedAnnotation;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import lombok.NoArgsConstructor;

/**
 * The {@link ProhibitedAccessException} class is a {@link RuntimeException} meant to be thrown when the {@link Prohibited} annotation's contract is violated.
 * @since Ultimate Tower Defense 1.0
 * @see RuntimeException
 * @see IllegalCallerException
 * @see Prohibited
 * @see ProhibitedAnnotation
 * @author AverageProgramer
 */
@NoArgsConstructor
public class ProhibitedAccessException extends IllegalCallerException {

    /**
     * The {@link ProhibitedAccessException} class' serial version UID.
     */
    @Serial
    private static final long serialVersionUID = -5712385153087316125L;

    /**
     * A constructor that does provide an {@code error} message when a {@link ProhibitedAccessException} is thrown.
     * @param message the error message to be printed.
     */
    public ProhibitedAccessException(@NonNls final String message) {

        // Initializes the exception using an error message.
        super(message);
    }

    /**
     * A constructor that provides a {@link Throwable} cause but not a {@code error} message when a {@link ProhibitedAccessException} is thrown.
     * @param cause the cause of the {@link ProhibitedAccessException} being thrown.
     */
    public ProhibitedAccessException(@Nullable final Throwable cause) {

        // Initializes the exception using a throwable cause.
        super(cause);
    }

    /**
     * A constructor that provides both an {@code error} message and a {@link Throwable} cause when a {@link ProhibitedAccessException} is thrown.
     * @param message the error message to be printed.
     * @param cause the cause of the {@link ProhibitedAccessException} being thrown.
     */
    public ProhibitedAccessException(@NonNls final String message, @Nullable final Throwable cause) {

        // Initializes the exception using an error message and a throwable cause.
        super(message, cause);
    }
}
