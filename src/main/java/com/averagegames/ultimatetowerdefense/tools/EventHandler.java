package com.averagegames.ultimatetowerdefense.tools;

/**
 * The {@link EventHandler} interface is meant to be implemented by all classes responsible for reading {@code events}.
 * Classes implementing the {@link EventHandler} interface can perform unique actions whenever a specific {@code event} occurs.
 * @since Ultimate Tower Defense 1.0
 * @author AverageProgramer
 */
@FunctionalInterface
public interface EventHandler {

    /**
     * Performs a certain task whenever a specific {@code event} occurs.
     * @since Ultimate Tower Defense 1.0
     * @see ClickReader
     * @see ButtonReader
     * @author AverageProgramer
     */
    void onEvent() throws Exception;
}
