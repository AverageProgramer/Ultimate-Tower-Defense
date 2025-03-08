package com.averagegames.ultimatetowerdefense.util.development;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@link LogManager} class provides several useful tools for logging and debugging game aspects.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogManager {

    /**
     * A global {@link Logger} that can be used for debugging.
     * @since Ultimate Tower Defense 1.0
     */
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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