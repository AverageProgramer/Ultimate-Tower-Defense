package com.averagegames.ultimatetowerdefense.tools.assets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The {@link AudioPlayer} class is a simple way to load and play a variety of {@code sounds} and {@code sound effects}.
 * @since Ultimate Tower Defense 1.0
 * @see AudioInputStream
 * @see Clip
 * @author AverageProgramer
 */
@NoArgsConstructor(force = true) @Setter @Getter
public class AudioPlayer {

    /**
     * A value used to indicate that a {@link Clip} should loop indefinitely.
     */
    public static final int INDEFINITELY = Clip.LOOP_CONTINUOUSLY;

    /**
     * The {@code path} the the audio {@link File}.
     */
    @NotNull
    private String pathname;

    /**
     * A constructor that loads an audio {@link File} at a given location.
     * @param pathname the {@link File}'s location.
     * @implNote The audio {@link File} must be a {@code WAV} {@link File}.
     */
    public AudioPlayer(@NotNull final String pathname) {

        // Initializes the string representing the path to the file using the given string.
        this.pathname = pathname;
    }

    /**
     * Opens a {@link Clip} using the given audio {@link File}.
     * @return the newly opened {@link Clip}.
     * @since Ultimate Tower Defense 1.0
     * @throws LineUnavailableException if the {@code line} cannot be opened.
     * @throws IOException if an {@code input} or {@code output} related issue occurs while {@code reading} the {@link File}.
     * @throws UnsupportedAudioFileException if the audio {@link File} is in an unsupported {@code format}.
     */
    private @NotNull Clip open() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        // Creates a new audio input stream using the given path.
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(this.pathname).toURI().toURL());

        // Creates a new clip and opens it using the new audio system.

        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);

        // Returns the newly opened clip.
        return clip;
    }

    /**
     * Plays an audio {@link File} one time through to completion.
     * @since Ultimate Tower Defense 1.0
     * @throws LineUnavailableException if the {@code line} cannot be opened.
     * @throws IOException if an {@code input} or {@code output} related issue occurs while {@code reading} the {@link File}.
     * @throws UnsupportedAudioFileException if the audio {@link File} is in an unsupported {@code format}.
     */
    public void play() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        // Plays the audio file once.
        this.open().start();
    }

    /**
     * Plays an audio {@link File} through to completion a given amount of times.
     * @since Ultimate Tower Defense 1.0
     * @param count the amount of times to loop the audio {@link File}.
     * @throws LineUnavailableException if the {@code line} cannot be opened.
     * @throws IOException if an {@code input} or {@code output} related issue occurs while {@code reading} the {@link File}.
     * @throws UnsupportedAudioFileException if the audio {@link File} is in an unsupported {@code format}.
     */
    public void loop(final int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        // Plays the audio file the given amount of times.
        this.open().loop(count);
    }
}
