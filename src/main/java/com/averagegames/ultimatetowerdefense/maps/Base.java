package com.averagegames.ultimatetowerdefense.maps;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor(force = true)
public final class Base {

    @Nullable
    @Setter @Getter
    private Position basePosition;

    @Setter @Getter
    private int unitSpawnDelay;

    @Nullable
    @Setter @Getter
    private Path unitPathing;

    private int health;

    @NotNull
    private Thread unitSpawnThread;

    public Base(@NotNull final Position basePosition) {
        this.basePosition = basePosition;

        this.unitSpawnThread = new Thread(() -> {});
    }
}
