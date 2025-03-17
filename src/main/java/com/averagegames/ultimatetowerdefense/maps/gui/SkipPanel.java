package com.averagegames.ultimatetowerdefense.maps.gui;

import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.scenes.GameScene;
import com.averagegames.ultimatetowerdefense.util.development.Constant;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.jetbrains.annotations.NotNull;

public final class SkipPanel extends Group {

    @Constant
    private static final int AREA_WIDTH = 260;

    @Constant
    private static final int AREA_HEIGHT = 115;

    @Constant
    private static final int AREA_ARC_LENGTH = 10;

    @Constant
    private static final int AREA_ARC_HEIGHT = 10;

    @Constant
    private static final int BUTTON_PREF_WIDTH = 100;

    @Constant
    private static final int BUTTON_PREF_HEIGHT = 25;

    @NotNull
    private final Rectangle area;

    @NotNull
    private final Button yButton, nButton;

    @NotNull
    private final Text text;

    public SkipPanel() {
        this.area = new Rectangle(AREA_WIDTH, AREA_HEIGHT);

        this.area.setStroke(Color.BLACK);
        this.area.setFill(Paint.valueOf("#e1e1e1"));

        this.area.setArcWidth(AREA_ARC_LENGTH);
        this.area.setArcHeight(AREA_ARC_HEIGHT);

        this.yButton = new Button("Yes");
        this.nButton = new Button("No");

        this.yButton.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        this.yButton.setStyle("-fx-font-weight: bold;");
        this.yButton.setBackground(Background.fill(Paint.valueOf("#00ff00")));

        this.yButton.setOnMouseEntered(event -> this.yButton.setBackground(Background.fill(Paint.valueOf("#20bc20"))));
        this.yButton.setOnMouseExited(event -> this.yButton.setBackground(Background.fill(Paint.valueOf("#00ff00"))));

        this.nButton.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        this.nButton.setStyle("-fx-font-weight: bold;");
        this.nButton.setBackground(Background.fill(Paint.valueOf("#ff0000")));

        this.nButton.setOnMouseEntered(event -> this.nButton.setBackground(Background.fill(Paint.valueOf("#bc2020"))));
        this.nButton.setOnMouseExited(event -> this.nButton.setBackground(Background.fill(Paint.valueOf("#ff0000"))));

        this.text = new Text("Skip wave?");

        this.text.setTextAlignment(TextAlignment.CENTER);
        this.text.setFont(Font.font(20));

        this.yButton.setOnAction(event -> {
            Player.cash += (GameScene.getWave() * 5) + 100;
            Platform.runLater(() -> GameScene.cashText.setText(STR."$\{Player.cash}"));

            GameScene.skip = true;

            super.getChildren().removeAll(this.area, this.yButton, this.nButton, this.text);
        });
        this.nButton.setOnAction(event -> super.getChildren().removeAll(this.area, this.yButton, this.nButton, this.text));

        super.getChildren().addAll(this.area, this.yButton, this.nButton, this.text);

        super.setViewOrder(Integer.MIN_VALUE);
    }

    public void setX(final double x) {
        this.area.setTranslateX(x);

        this.yButton.setTranslateX(x + 20);
        this.nButton.setTranslateX(x + 140);

        this.text.setX(x + (this.area.getWidth() / 2) - (this.text.getBoundsInLocal().getWidth() / 2));
    }

    public double getX() {
        return this.area.getTranslateX();
    }

    public void setY(final double y) {
        this.area.setTranslateY(y);

        this.yButton.setTranslateY((y + this.area.getHeight()) - this.yButton.getPrefHeight() - 20);
        this.nButton.setTranslateY((y + this.area.getHeight()) - this.nButton.getPrefHeight() - 20);

        this.text.setY(y + this.text.getBoundsInLocal().getHeight() + 15);
    }

    public double getY() {
        return this.area.getTranslateY();
    }

    @SuppressWarnings("unused")
    public double getAreaWidth() {
        return this.area.getWidth();
    }

    @SuppressWarnings("unused")
    public double getAreaHeight() {
        return this.area.getHeight();
    }
}
