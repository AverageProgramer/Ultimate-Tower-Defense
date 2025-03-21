package com.averagegames.ultimatetowerdefense.scenes.menu;

import com.averagegames.ultimatetowerdefense.scenes.Builder;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public final class LobbyScene extends Scene implements Builder {
    public LobbyScene(@NotNull final Group root) {
        super(root);
    }

    @Override
    public void pre_build(@NotNull Stage stage) throws Exception {
        Builder.super.pre_build(stage);
    }

    @Override
    public void build(@NotNull Stage stage) throws Exception {

    }

    @Override
    public void post_build(@NotNull Stage stage) throws Exception {
        Builder.super.post_build(stage);
    }
}
