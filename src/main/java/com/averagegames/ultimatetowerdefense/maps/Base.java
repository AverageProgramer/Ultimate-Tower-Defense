package com.averagegames.ultimatetowerdefense.maps;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor @Getter
public final class Base {

    @Nullable
    @Setter @Getter(onMethod_={@SuppressWarnings("unused")})
    private Position basePosition;

    @Getter
    private int health;

    public Base(@NotNull final Position basePosition) {
        this.basePosition = basePosition;
    }
}
