package com.averagegames.ultimatetowerdefense.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@link Manager} class provides several useful tools for development and testing of game aspects.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Manager {

    /**
     * A global {@link Logger} that can be used for debugging.
     * By default, logging is disabled but can be enabled using the {@link Manager#enableLogging(boolean)} method.
     * @since Ultimate Tower Defense 1.0
     */
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    static {

        // Disables logging by default.
        enableLogging(false);
    }

    /**
     * Enables or disables the global {@link Logger} based on the boolean value provided.
     * @param enabled whether logging should be enabled.
     * @since Ultimate Tower Defense 1.0
     */
    public static void enableLogging(final boolean enabled) {

        // Sets the global logger's level to either "ALL" or "OFF" depending on the value passed into the method.
        // When the level is "ALL" the logger may log information, when it is "OFF" it may not log information.
        LOGGER.setLevel(enabled ? Level.ALL : Level.OFF);
    }
}