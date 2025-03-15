package com.averagegames.ultimatetowerdefense.util.animation;

import javafx.animation.PathTransition;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Node;

public class CircularTranslationHandler {

    private PathTransition animation;

    @Nullable
    @Setter @Getter
    private Node node;

    @Setter @Getter
    private int speed;

    @Nullable
    @Setter @Getter
    private Circle path;

    public CircularTranslationHandler() {

        this.animation = new PathTransition();

        this.node = null;

        this.path = null;
    }

    private double calculateDuration() {
        return 0.0;
    }
}
