package com.averagegames.ultimatetowerdefense.tools.assets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@NoArgsConstructor(force = true) @Setter @Getter
public class AudioPlayer {

    public static final int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY;

    @NotNull
    private String pathname;

    public AudioPlayer(@NotNull final String pathname) {
        this.pathname = pathname;
    }

    private @NotNull Clip open() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(this.pathname).toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);

        return clip;
    }

    public void play() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        this.open().start();
    }

    public void loop(final int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        this.open().loop(count);
    }
}
