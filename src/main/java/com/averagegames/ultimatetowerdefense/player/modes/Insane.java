package com.averagegames.ultimatetowerdefense.player.modes;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Wave;
import com.averagegames.ultimatetowerdefense.characters.enemies.zombies.Abnormal;
import com.averagegames.ultimatetowerdefense.characters.enemies.zombies.Rapid;

public final class Insane {
    public static final Wave WAVE_1 = new Wave(new Enemy[] {
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal()
    });

    public static final Wave WAVE_2 = new Wave(new Enemy[] {
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal()
    });

    public static final Wave WAVE_3 = new Wave(new Enemy[] {
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal()
    });

    public static final Wave WAVE_4 = new Wave(new Enemy[] {
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal(),
            new Abnormal()
    });

    public static final Wave WAVE_5 = new Wave(new Enemy[] {
            new Rapid(),
            new Rapid(),
            new Rapid(),
            new Rapid(),
            new Rapid(),
            new Rapid(),
            new Rapid(),
            new Rapid()
    });
}
