package com.averagegames.ultimatetowerdefense.util.assets;

import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * The {@link Timer} class is a useful tool for performing certain {@code actions} after a certain amount of time has passed.
 * Using the {@link Timer} class, you can easily manage and perform multiple different {@code actions} all on separate time intervals.
 * The {@link Timer} class is essentially just an easier to use version of the {@code JavaFX} {@link AnimationTimer} class.
 * @since Ultimate Tower Defense 1.0
 * @see AnimationTimer
 * @author AverageProgramer
 */
@Getter @NoArgsConstructor
public class Timer extends AnimationTimer {

    /**
     * The time in milliseconds between calling the {@link Runnable} action.
     */
    @Range(from = 0L, to = Long.MAX_VALUE)
    @Accessors(makeFinal = true) @Setter
    private long handleTime;

    /**
     * The last time in the {@link Runnable} action was called.
     */
    @Accessors(makeFinal = true)
    private long lastTimeCalled;

    /**
     * The {@link Runnable} action to be performed every given amount of time.
     */
    @Nullable
    @Accessors(makeFinal = true) @Setter
    private Runnable onHandle;

    /**
     * A constructor that initializes the {@link Timer} with a given {@code handle} time.
     * @param handleTime the time in milliseconds between handling {@link Runnable} actions.
     */
    public Timer(@Range(from = 0L, to = Long.MAX_VALUE) final long handleTime) {

        // Sets the timer's handle time to the given handle time.
        this.handleTime = handleTime;
    }

    /**
     * Resets the {@link Timer} to a time of 0.
     * @since Ultimate Tower Defense 1.0
     */
    public final void reset() {

        // Sets the last time the action was called to 0.
        this.lastTimeCalled = 0;
    }

    /**
     * Handles the given {@link Runnable} action the "{@link Timer} should perform.
     * @param now The timestamp of the current frame given in nanoseconds.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    public final void handle(final long now) {

        // Converts the system's current time to milliseconds.
        long currentTimeInMillis = now / 1_000_000;

        // Determines whether the amount of time passed since the last time the action was called is less than the amount of time needed.
        if (currentTimeInMillis - this.lastTimeCalled < this.handleTime) {
            return;
        }

        // Sets the last time the action was called to the system's current time.
        this.lastTimeCalled = currentTimeInMillis;

        // Determines whether the given action is not null.
        if (this.onHandle != null) {

            // Performs the given action.
            this.onHandle.run();
        }
    }
}
