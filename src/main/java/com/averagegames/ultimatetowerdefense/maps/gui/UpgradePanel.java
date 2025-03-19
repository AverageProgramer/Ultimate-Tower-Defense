package com.averagegames.ultimatetowerdefense.maps.gui;

import com.averagegames.ultimatetowerdefense.characters.towers.Targeting;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.scenes.GameScene;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Constant;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public final class UpgradePanel extends Group {

    @Constant
    private static final int AREA_WIDTH = 260;

    @Constant
    private static final int AREA_HEIGHT = 400;

    @Constant
    private static final int AREA_ARC_LENGTH = 10;

    @Constant
    private static final int AREA_ARC_HEIGHT = 10;

    @Constant
    private static final int BUTTON_PREF_WIDTH = 100;

    @Constant
    private static final int BUTTON_PREF_HEIGHT = 25;

    @NotNull
    @Setter @Getter
    private Tower tower;

    @NotNull
    private final Rectangle area;

    @NotNull
    private final Button upgradeButton, targetingButton;

    public UpgradePanel(@NotNull final Tower tower) {
        this.tower = tower;

        this.area = new Rectangle(AREA_WIDTH, AREA_HEIGHT);

        this.area.setStroke(Color.BLACK);
        this.area.setFill(Paint.valueOf("#e1e1e1"));

        this.area.setArcWidth(AREA_ARC_LENGTH);
        this.area.setArcHeight(AREA_ARC_HEIGHT);

        this.upgradeButton = new Button(tower.getLevel() < 5 ? STR."$\{tower.getUpgradeCosts()[tower.getLevel()]}" : "MAX LEVEL");
        this.targetingButton = new Button("FIRST");

        this.upgradeButton.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        this.upgradeButton.setStyle("-fx-font-weight: bold;");
        this.upgradeButton.setBackground(Background.fill(Paint.valueOf("#2ac915")));

        this.upgradeButton.setOnMouseEntered(event -> this.upgradeButton.setBackground(Background.fill(Paint.valueOf("#30a021"))));
        this.upgradeButton.setOnMouseExited(event -> this.upgradeButton.setBackground(Background.fill(Paint.valueOf("#2ac915"))));

        this.targetingButton.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        this.targetingButton.setStyle("-fx-font-weight: bold;");
        this.targetingButton.setBackground(Background.fill(Color.ORANGE));

        this.targetingButton.setOnMouseEntered(event -> this.targetingButton.setBackground(Background.fill(Paint.valueOf("#c78814"))));
        this.targetingButton.setOnMouseExited(event -> this.targetingButton.setBackground(Background.fill(Color.ORANGE)));

        this.upgradeButton.setOnAction(event -> {
            if (tower.getLevel() < 4) {
                try {
                    if (Player.cash >= tower.getUpgradeCosts()[tower.getLevel()]) {
                        Player.cash -= tower.getUpgradeCosts()[tower.getLevel()];

                        Platform.runLater(() -> GameScene.CASH_TEXT.setText(STR."$\{Player.cash}"));

                        tower.upgrade();

                        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Upgrade 1.wav");
                        player.play();
                    } else {
                        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                        player.play();
                    }
                } catch (Exception e) {
                    System.out.println("Exception occurred");
                }

                this.upgradeButton.setText(STR."$\{tower.getUpgradeCosts()[tower.getLevel()]}");
            } else {
                try {
                    if (Player.cash >= tower.getUpgradeCosts()[tower.getLevel()]) {
                        Player.cash -= tower.getUpgradeCosts()[tower.getLevel()];

                        Platform.runLater(() -> GameScene.CASH_TEXT.setText(STR."$\{Player.cash}"));

                        tower.upgrade();

                        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Upgrade 1.wav");
                        player.play();
                    } else {
                        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                        player.play();
                    }
                } catch (Exception e) {
                    System.out.println("Exception occurred");
                }

                this.upgradeButton.setText("MAX LEVEL");
            }
        });

        this.targetingButton.setOnAction(event -> {
            switch (tower.getTargeting()) {
                case FIRST ->
                        tower.setTargeting(Targeting.LAST);
                case LAST ->
                        tower.setTargeting(Targeting.STRONGEST);
                case STRONGEST ->
                        tower.setTargeting(Targeting.WEAKEST);
                case WEAKEST ->
                        tower.setTargeting(Targeting.FIRST);
            }

            this.targetingButton.setText(STR."\{tower.getTargeting()}");
        });

        super.getChildren().addAll(this.area, this.upgradeButton, this.targetingButton);

        super.setViewOrder(Integer.MIN_VALUE);
    }

    public void setX(final double x) {
        this.area.setTranslateX(x);

        this.upgradeButton.setTranslateX(x + 20);
        this.targetingButton.setTranslateX(x + 140);
    }

    public double getX() {
        return this.area.getTranslateX();
    }

    public void setY(final double y) {
        this.area.setTranslateY(y);

        this.upgradeButton.setTranslateY((y + this.area.getHeight()) - this.upgradeButton.getPrefHeight() - 20);
        this.targetingButton.setTranslateY((y + this.area.getHeight()) - this.targetingButton.getPrefHeight() - 20);
    }

    public double getY() {
        return this.area.getTranslateY();
    }

    public double getAreaWidth() {
        return this.area.getWidth();
    }

    public double getAreaHeight() {
        return this.area.getHeight();
    }
}
