package com.averagegames.ultimatetowerdefense.tools.assets;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(force = true)
public class AudioPlayer {
    private final String url;

    public AudioPlayer(@NotNull final String url) {
        this.url = url;
    }

    public void play() {

    }
}
