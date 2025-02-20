package com.averagegames.ultimatetowerdefense.world;

import com.averagegames.ultimatetowerdefense.characters.Tower;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@link Towers} class serves as a way to keep track of {@link Tower} related data in a game.
 * @author AverageProgramer
 */
public final class Towers {

    /**
     * A {@link List} containing every active {@link Tower} in a game.
     */
    public static final List<@NotNull Tower> LIST_OF_ACTIVE_TOWERS = Collections.synchronizedList(new ArrayList<>());
}
