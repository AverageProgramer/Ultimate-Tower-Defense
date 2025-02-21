package com.averagegames.ultimatetowerdefense.maps;

import org.jetbrains.annotations.NotNull;

/**
 * The {@link Path} record serves as a list of given {@link Position}s.
 * @param positions an {@code array} of specific {@link Position}s.
 * @since Ultimate Tower Defense 1.0
 * @see Record
 * @see Position
 * @author AverageProgramer
 */
public record Path(@NotNull Position[] positions) {}
