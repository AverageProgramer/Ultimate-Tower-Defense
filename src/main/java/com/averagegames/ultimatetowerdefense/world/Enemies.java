package com.averagegames.ultimatetowerdefense.world;

import com.averagegames.ultimatetowerdefense.characters.Enemy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@link Enemies} class serves as a way to keep track of {@link Enemy} related data in a game.
 * @since Ultimate Tower Defense 1.0
 * @see Enemy
 * @author AverageProgramer
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Enemies {

    /**
     * A {@link List} containing every active {@link Enemy} in a game.
     */
    public static final List<@NotNull Enemy> LIST_OF_ACTIVE_ENEMIES = Collections.synchronizedList(new ArrayList<>());
}
