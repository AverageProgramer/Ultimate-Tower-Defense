package com.averagegames.ultimatetowerdefense.control;

import com.averagegames.ultimatetowerdefense.Main;
import com.averagegames.ultimatetowerdefense.tools.annotations.NotInstantiable;
import com.averagegames.ultimatetowerdefense.tools.annotations.Specific;
import com.averagegames.ultimatetowerdefense.tools.annotations.verification.SpecificAnnotation;
import com.averagegames.ultimatetowerdefense.tools.exceptions.UnspecifiedAccessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@link LogController} class provides several useful tools for development and testing of game aspects.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
@NotInstantiable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogController {

    /**
     * A global {@link Logger} that can be used for debugging.
     * By default, logging is disabled but can be enabled using the {@link LogController#enableLogging(boolean)} method.
     */
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    static {

        // Disables logging by default.
        enableLogging(false);
    }

    /**
     * Enables or disables the global {@link Logger} based on the boolean value provided.
     * @param enabled whether logging should be enabled.
     * @throws UnspecifiedAccessException if the method is invoked in a prohibited class.
     * @access This method can only be accessed in the {@link Main} class.
     */
    @Specific({LogController.class, Main.class})
    public static void enableLogging(final boolean enabled) {

        // Verifies that the calling class of the method was specified by the method's annotation.
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        // Sets the global logger's level to either "ALL" or "OFF" depending on the value passed into the method.
        // When the level is "ALL" the logger may log information, when it is "OFF" it may not log information.
        LOGGER.setLevel(enabled ? Level.ALL : Level.OFF);
    }
}
