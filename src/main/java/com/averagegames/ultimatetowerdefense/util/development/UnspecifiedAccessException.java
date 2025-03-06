package com.averagegames.ultimatetowerdefense.util.development;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;

/**
 * The {@link UnspecifiedAccessException} class is a {@link RuntimeException} meant to be thrown when the {@link Specific} annotation's contract is violated.
 * @since Ultimate Tower Defense 1.0
 * @see RuntimeException
 * @see IllegalCallerException
 * @see Specific
 * @see SpecificAnnotation
 * @author AverageProgramer
 */
@NoArgsConstructor
public class UnspecifiedAccessException extends IllegalCallerException {

    /**
     * The {@link UnspecifiedAccessException}'s serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 5980618018358720791L;

    /**
     * A constructor that provides an {@code error} message but not a {@link Throwable} cause when an {@link UnspecifiedAccessException} is thrown.
     * @param message the error message to be printed.
     * @since Ultimate Tower Defense 1.0
     */
    public UnspecifiedAccessException(@NonNls final String message) {

        // Initializes the exception using an error message.
        super(message);
    }

    /**
     * A constructor that provides a {@link Throwable} cause but not a {@code error} message when an {@link UnspecifiedAccessException} is thrown.
     * @param cause the cause of the {@link UnspecifiedAccessException} being thrown.
     * @since Ultimate Tower Defense 1.0
     */
    public UnspecifiedAccessException(@Nullable final Throwable cause) {

        // Initializes the exception using a throwable cause.
        super(cause);
    }

    /**
     * A constructor that provides both an {@code error} message and a {@link Throwable} cause when an {@link UnspecifiedAccessException} is thrown.
     * @param message the error message to be printed.
     * @param cause the cause of the {@link UnspecifiedAccessException} being thrown.
     * @since Ultimate Tower Defense 1.0
     */
    public UnspecifiedAccessException(@NonNls final String message, @Nullable final Throwable cause) {

        // Initializes the exception using an error message and a throwable cause.
        super(message, cause);
    }
}