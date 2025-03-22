package com.averagegames.ultimatetowerdefense.scenes.assets;

import com.averagegames.ultimatetowerdefense.characters.towers.Targeting;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.scenes.game.GameScene;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Constant;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private final Rectangle area, healthBar, highlight;

    @NotNull
    private final Label healthLabel, levelLabel;

    private int totalSpent;

    @NotNull
    private final Button upgradeButton, targetingButton, sellButton, escButton;

    public UpgradePanel(@NotNull final Tower tower) {
        this.tower = tower;

        this.totalSpent = tower.getPlacementCost();

        this.area = new Rectangle(AREA_WIDTH, AREA_HEIGHT);

        this.area.setStroke(Color.BLACK);
        this.area.setFill(Paint.valueOf("#e1e1e1"));

        this.area.setArcWidth(AREA_ARC_LENGTH);
        this.area.setArcHeight(AREA_ARC_HEIGHT);

        this.healthBar = new Rectangle();

        this.healthBar.setHeight(20);

        this.healthBar.setFill(Paint.valueOf("#00ff00"));

        this.healthBar.setArcWidth(AREA_ARC_LENGTH);
        this.healthBar.setArcHeight(AREA_ARC_HEIGHT);

        this.update();

        this.highlight = new Rectangle();

        this.highlight.setHeight(20);

        this.highlight.setFill(Color.TRANSPARENT);
        this.highlight.setStroke(Color.BLACK);

        this.highlight.setArcWidth(AREA_ARC_LENGTH);
        this.highlight.setArcHeight(AREA_ARC_HEIGHT);

        this.highlight.setWidth(136);

        this.healthLabel = new Label();

        this.healthLabel.setText("HEALTH: ");

        this.healthLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        this.healthLabel.setTextFill(Color.BLACK);

        this.levelLabel = new Label(STR."\{tower.getClass().getSimpleName().toUpperCase()}: Level \{tower.getLevel()}");

        this.levelLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        this.levelLabel.setTextFill(Color.BLACK);

        this.sellButton = new Button();

        this.sellButton.setPrefSize((double) BUTTON_PREF_WIDTH / 2, BUTTON_PREF_HEIGHT);

        this.sellButton.setText(STR."Sell");

        this.sellButton.setStyle("-fx-font-weight: bold;");
        this.sellButton.setBackground(Background.fill(Paint.valueOf("#ff0000")));

        this.sellButton.setOnMouseEntered(event -> this.sellButton.setBackground(Background.fill(Paint.valueOf("#bc2020"))));
        this.sellButton.setOnMouseExited(event -> this.sellButton.setBackground(Background.fill(Paint.valueOf("#ff0000"))));

        this.escButton = new Button("X");

        this.escButton.setPrefSize((double) BUTTON_PREF_WIDTH / 4, BUTTON_PREF_HEIGHT);

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

        this.sellButton.setOnAction(event -> {
            tower.eliminate();

            Player.cash += this.totalSpent / 2;

            Platform.runLater(() -> GameScene.CASH_TEXT.setText(STR."$\{Player.cash}"));

            try {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Upgrade 1.wav");
                player.play();
            } catch (Exception ex) {
                // Ignore
            }
        });

        this.escButton.setOnAction(event -> tower.deselect());

        this.upgradeButton.setOnAction(event -> {
            if (tower.getLevel() < 4) {
                try {
                    if (Player.cash >= tower.getUpgradeCosts()[tower.getLevel()]) {
                        Player.cash -= tower.getUpgradeCosts()[tower.getLevel()];
                        this.totalSpent += tower.getUpgradeCosts()[tower.getLevel()];

                        Platform.runLater(() -> GameScene.CASH_TEXT.setText(STR."$\{Player.cash}"));

                        tower.upgrade();

                        this.levelLabel.setText(STR."\{tower.getClass().getSimpleName().toUpperCase()}: Level \{tower.getLevel()}");

                        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Upgrade 1.wav");
                        player.play();

                        this.upgradeButton.setText(STR."$\{tower.getUpgradeCosts()[tower.getLevel()]}");

                        tower.heal(100 - tower.getHealth());
                    } else {
                        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                        player.play();
                    }
                } catch (Exception e) {
                    // Ignore
                }
            } else if (tower.getLevel() == 4) {
                try {
                    if (Player.cash >= tower.getUpgradeCosts()[4]) {
                        Player.cash -= tower.getUpgradeCosts()[4];
                        this.totalSpent += tower.getUpgradeCosts()[tower.getUpgradeCosts().length - 1];

                        Platform.runLater(() -> GameScene.CASH_TEXT.setText(STR."$\{Player.cash}"));

                        tower.upgrade();

                        this.levelLabel.setText(STR."\{tower.getClass().getSimpleName().toUpperCase()}: Level \{tower.getLevel()}");

                        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Upgrade 1.wav");
                        player.play();

                        this.upgradeButton.setText("MAX LEVEL");

                        tower.heal(100 - tower.getHealth());
                    } else {
                        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                        player.play();
                    }
                } catch (Exception e) {
                    // Ignore
                }
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

        super.getChildren().addAll(this.area, this.upgradeButton, this.targetingButton, this.sellButton, this.escButton, this.healthBar, this.highlight, this.healthLabel, this.levelLabel);

        super.setViewOrder(GameScene.GUI_LAYER);
    }

    public void setX(final double x) {
        this.area.setTranslateX(x);

        this.upgradeButton.setTranslateX(x + 20);
        this.targetingButton.setTranslateX(x + 140);

        this.sellButton.setTranslateX(x + 20);
        this.escButton.setTranslateX((x + this.getAreaWidth()) - this.escButton.getPrefWidth() - 20);

        this.healthLabel.setTranslateX(x + 20);

        this.levelLabel.setTranslateX(x + 20);

        this.healthBar.setTranslateX(x + 105);
        this.highlight.setTranslateX(x + 105);
    }

    public double getX() {
        return this.area.getTranslateX();
    }

    public void setY(final double y) {
        this.area.setTranslateY(y);

        this.upgradeButton.setTranslateY((y + this.area.getHeight()) - this.upgradeButton.getPrefHeight() - 20);
        this.targetingButton.setTranslateY((y + this.area.getHeight()) - this.targetingButton.getPrefHeight() - 20);

        this.sellButton.setTranslateY(y + 20);
        this.escButton.setTranslateY(y + 20);

        this.healthLabel.setTranslateY((y + this.area.getHeight()) - this.healthLabel.getPrefHeight() - 80);

        this.levelLabel.setTranslateY(y + 55);

        this.healthBar.setTranslateY((y + this.area.getHeight()) - this.healthLabel.getPrefHeight() - 77.5);
        this.highlight.setTranslateY((y + this.area.getHeight()) - this.healthLabel.getPrefHeight() - 77.5);
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

    public void update() {
        if (tower.getHealth() > 50) {
            this.healthBar.setFill(Paint.valueOf("#00ff00"));
        } else if (tower.getHealth() > 25) {
            this.healthBar.setFill(Paint.valueOf("#e8ff00"));
        } else {
            this.healthBar.setFill(Paint.valueOf("#ff0000"));
        }

        this.healthBar.setWidth(((double) tower.getHealth() / 100) * 136);
    }
}
