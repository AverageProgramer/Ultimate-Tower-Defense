package com.averagegames.ultimatetowerdefense.characters;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Unit {

    /**
     * A {@link List} containing every active {@link Unit} in a game.
     */
    public static final List<@NotNull Enemy> LIST_OF_ACTIVE_UNITS = Collections.synchronizedList(new ArrayList<>());

    protected abstract void onSpawn();

    protected abstract void onDeath();

    protected abstract void attack();

    protected abstract void special();
}
