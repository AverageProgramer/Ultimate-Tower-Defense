package com.averagegames.ultimatetowerdefense.scenes;

import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.characters.towers.legendary.Energizer;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.*;
import com.averagegames.ultimatetowerdefense.maps.Base;
import com.averagegames.ultimatetowerdefense.maps.Path;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.maps.Spawner;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.player.modes.Easy;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.averagegames.ultimatetowerdefense.characters.enemies.Enemy.LIST_OF_ACTIVE_ENEMIES;
import static com.averagegames.ultimatetowerdefense.characters.towers.Tower.LIST_OF_ACTIVE_TOWERS;
import static com.averagegames.ultimatetowerdefense.player.Player.LIST_OF_ACTIVE_FARMS;

// TODO: Complete overhaul of GameScene class. Add wave timer and bonus.

public final class GameScene extends Scene implements SceneBuilder {

    private int tower = -1;

    @Getter
    private static int wave = 1;

    public static final Text cashText = new Text(STR."$\{Player.cash}");

    public static final Text baseText = new Text(STR."\{Base.health} HP");

    public static final Text waveText = new Text("Wave 1");

    private static final Rectangle2D screen = Screen.getPrimary().getBounds();

    private static final double xSpace;

    private static final double ySpace;

    private Group root = (Group) super.getRoot();

    private boolean skip;

    static {
        while (true) {
            if (screen.getWidth() != 0 && screen.getHeight() != 0) {
                break;
            }
        }

        xSpace = (screen.getWidth() - 1350) / 2;
        ySpace = (screen.getHeight() - 500) / 2;
    }

    public static final Spawner SPAWNER = getTestSpawner();

    public GameScene(@NotNull final Parent root) {
        super(root);
    }

    @Override
    public void pre_build(@NotNull final Stage stage) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/music/(Official) Tower Defense Simulator OST - Nuclear Fallen King.wav");
        // player.loop(AudioPlayer.INDEFINITELY);

        stage.setMaximized(true);
        stage.setResizable(false);
    }

    private static @NotNull Spawner getTestSpawner() {
        Spawner spawner = new Spawner(new Position(xSpace, ySpace));

        spawner.setEnemyPathing(new Path(new Position[] {
                new Position(xSpace + 400, ySpace),
                new Position(xSpace + 550, ySpace + 200),
                new Position(xSpace + 550, ySpace + 400),
                new Position(xSpace + 900, ySpace + 400),
                new Position(xSpace + 900, ySpace + 200),
                new Position(xSpace + 700, ySpace + 100),
                new Position(xSpace + 700, ySpace),
                new Position(xSpace + 1050, ySpace),
                new Position(xSpace + 1200, ySpace + 200),
                new Position(xSpace + 1350, ySpace + 200)
        }));

        spawner.setSpawnDelay(1500);

        return spawner;
    }

    private void timerWait() {
        Rectangle rectangle = new Rectangle(260, 115);

        rectangle.setX((screen.getWidth() / 2) - (rectangle.getWidth() / 2));
        rectangle.setY(15);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Paint.valueOf("#e1e1e1"));

        Platform.runLater(() -> root.getChildren().add(rectangle));

        Text skipText = new Text("Skip wave?");

        skipText.setFont(Font.font(20));
        skipText.setX((screen.getWidth() / 2) - (skipText.getBoundsInLocal().getWidth() / 2));
        skipText.setY(55);
        skipText.setTextAlignment(TextAlignment.CENTER);

        Platform.runLater(() -> root.getChildren().add(skipText));

        Button ySkipButton = new Button("Yes");

        ySkipButton.setPrefSize(100, 25);
        ySkipButton.setTranslateX((screen.getWidth() / 2) - 110);
        ySkipButton.setTranslateY(80);
        ySkipButton.setStyle("-fx-font-weight: bold;");
        ySkipButton.setBackground(Background.fill(Paint.valueOf("#00ff00")));
        ySkipButton.setOnMouseEntered(event -> ySkipButton.setBackground(Background.fill(Paint.valueOf("#20bc20"))));
        ySkipButton.setOnMouseExited(event -> ySkipButton.setBackground(Background.fill(Paint.valueOf("#00ff00"))));
        ySkipButton.setOnAction(event -> {
            Player.cash += (wave * 5) + 100;

            Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));

            this.skip = true;
        });

        Button nSkipButton = new Button("No");

        nSkipButton.setPrefSize(100, 25);
        nSkipButton.setTranslateX((screen.getWidth() / 2) + 10);
        nSkipButton.setTranslateY(80);
        nSkipButton.setStyle("-fx-font-weight: bold;");
        nSkipButton.setBackground(Background.fill(Paint.valueOf("#ff0000")));
        nSkipButton.setOnMouseEntered(event -> nSkipButton.setBackground(Background.fill(Paint.valueOf("#bc2020"))));
        nSkipButton.setOnMouseExited(event -> nSkipButton.setBackground(Background.fill(Paint.valueOf("#ff0000"))));
        nSkipButton.setOnAction(event -> Platform.runLater(() -> {
            root.getChildren().remove(ySkipButton);
            root.getChildren().remove(nSkipButton);
            root.getChildren().remove(skipText);
            root.getChildren().remove(rectangle);
        }));

        Platform.runLater(() -> {
            root.getChildren().add(ySkipButton);
            root.getChildren().add(nSkipButton);
        });

        while (!LIST_OF_ACTIVE_ENEMIES.isEmpty() && !this.skip && Base.health > 0);

        Platform.runLater(() -> {
            root.getChildren().remove(rectangle);

            root.getChildren().remove(ySkipButton);
            root.getChildren().remove(nSkipButton);
            root.getChildren().remove(skipText);
        });

        if (Base.health <= 0) {
            return;
        }

        ++wave;

        this.skip = false;

        for (int i = 0; i < 4; i++) {
            try {
                if (i == 0) {
                    Thread.sleep(1000);
                }

                if (i < 3) {
                    AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Timer Sound Effect 1.wav");
                    player.play();
                }

                Thread.sleep(1000);
            } catch (Exception ex) {
                // Ignore
            }
        }

        for (Farm farm : LIST_OF_ACTIVE_FARMS) {
            Player.cash += farm.getBonus();

            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Farm Income 1.wav");
            try {
                player.play();
            } catch (Exception ex) {
                // Ignore
            }
        }

        Platform.runLater(() -> waveText.setText(STR."Wave \{wave}"));

        Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));
    }

    @Override
    public void build(@NotNull final Stage stage) {
        stage.setTitle("Ultimate Tower Defense");

        Circle circle1 = new Circle(5);
        circle1.setCenterX(xSpace);
        circle1.setCenterY(ySpace);

        Circle circle2 = new Circle(5);
        circle2.setCenterX(xSpace + 1350);
        circle2.setCenterY(ySpace + 200);

        cashText.setX(50);
        cashText.setY(screen.getHeight() - 50 - cashText.getLayoutBounds().getHeight());
        cashText.setFill(Paint.valueOf("#3dbe23"));
        cashText.setFont(Font.font(50));

        baseText.setX(screen.getWidth() - 180 - baseText.getLayoutBounds().getWidth());
        baseText.setY(screen.getHeight() - 50 - baseText.getLayoutBounds().getHeight());
        baseText.setFill(Paint.valueOf("#b60e0e"));
        baseText.setFont(Font.font(50));

        waveText.setX(screen.getWidth() - 180 - waveText.getLayoutBounds().getWidth());
        waveText.setY(80);
        waveText.setFont(Font.font(50));

        double gunnerButtonX = (screen.getWidth() / 2) - 50;
        double y = screen.getHeight() - 200;

        Button scoutButton = new Button("Scout: $200");
        scoutButton.setPrefSize(100, 100);
        scoutButton.setTranslateX(gunnerButtonX - 200);
        scoutButton.setTranslateY(y);
        scoutButton.setOnAction(event -> this.tower = 0);

        Button marksmanButton = new Button("Marksman:\n$300");
        marksmanButton.setPrefSize(100, 100);
        marksmanButton.setTranslateX(gunnerButtonX - 100);
        marksmanButton.setTranslateY(y);
        marksmanButton.setOnAction(event -> this.tower = 1);

        Button gunnerButton = new Button("Gunner: $500");
        gunnerButton.setPrefSize(100, 100);
        gunnerButton.setTranslateX(gunnerButtonX);
        gunnerButton.setTranslateY(y);
        gunnerButton.setOnAction(event -> this.tower = 2);

        Button energizerButton = new Button("Gunship:\n$750");
        energizerButton.setPrefSize(100, 100);
        energizerButton.setTranslateX(gunnerButtonX + 100);
        energizerButton.setTranslateY(y);
        energizerButton.setOnAction(event -> this.tower = 6);

        Button farmButton = new Button("Farm: $250");
        farmButton.setPrefSize(100, 100);
        farmButton.setTranslateX(gunnerButtonX + 200);
        farmButton.setTranslateY(y);
        farmButton.setOnAction(event -> this.tower = 4);

        Platform.runLater(() -> {
            Position lastPos = new Position(xSpace, ySpace);
            for (Position pos : new Path(new Position[] {
                    new Position(xSpace + 400, ySpace),
                    new Position(xSpace + 550, ySpace + 200),
                    new Position(xSpace + 550, ySpace + 400),
                    new Position(xSpace + 900, ySpace + 400),
                    new Position(xSpace + 900, ySpace + 200),
                    new Position(xSpace + 700, ySpace + 100),
                    new Position(xSpace + 700, ySpace),
                    new Position(xSpace + 1050, ySpace),
                    new Position(xSpace + 1200, ySpace + 200),
                    new Position(xSpace + 1350, ySpace + 200)
            }).positions()) {
                Line line = new Line();

                line.setStartX(lastPos.x());
                line.setStartY(lastPos.y());
                line.setEndX(pos.x());
                line.setEndY(pos.y());

                line.setViewOrder(Integer.MAX_VALUE);

                lastPos = pos;

                root.getChildren().add(line);
            }

            root.getChildren().add(cashText);
            root.getChildren().add(baseText);
            root.getChildren().add(waveText);

            root.getChildren().add(circle1);
            root.getChildren().add(circle2);

            root.getChildren().add(scoutButton);
            root.getChildren().add(marksmanButton);
            root.getChildren().add(gunnerButton);
            root.getChildren().add(energizerButton);
            root.getChildren().add(farmButton);
        });

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                // Ignore
            }

            SPAWNER.spawn(Easy.WAVE_1, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_2, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_3, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_4, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_5, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_6, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_7, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_8, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_9, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_10, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_11, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_12, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_13, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_14, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_15, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_16, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_17, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_18, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_19, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_20, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_21, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_22, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_23, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_24, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_25, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_26, root);
            timerWait();

            SPAWNER.spawn(Easy.WAVE_27, root);
        }).start();

        this.setOnMouseClicked(event -> {
            if (this.tower == 0 && Player.cash >= Scout.COST && LIST_OF_ACTIVE_TOWERS.size() < Player.LIMIT) {
                Scout scout = new Scout();

                scout.setParent(root);
                scout.place(new Position(event.getX(), event.getY()));

                scout.startAttacking();

                Player.cash -= Scout.COST;

                Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));

                this.tower = -1;
            } else if (this.tower == 1 && Player.cash >= Marksman.COST && LIST_OF_ACTIVE_TOWERS.size() < Player.LIMIT) {
                Marksman marksman = new Marksman();

                marksman.setParent(root);
                marksman.place(new Position(event.getX(), event.getY()));

                marksman.startAttacking();

                Player.cash -= Marksman.COST;

                Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));

                this.tower = -1;
            } else if (this.tower == 2 && Player.cash >= Gunner.COST && LIST_OF_ACTIVE_TOWERS.size() < Player.LIMIT) {
                Gunner gunner = new Gunner();

                gunner.setParent(root);
                gunner.place(new Position(event.getX(), event.getY()));

                gunner.startAttacking();

                Player.cash -= Gunner.COST;

                Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));

                this.tower = -1;
            } else if (this.tower == 3 && Player.cash >= Energizer.COST && LIST_OF_ACTIVE_TOWERS.size() < Player.LIMIT) {
                AtomicInteger amount = new AtomicInteger();

                LIST_OF_ACTIVE_TOWERS.forEach(tower1 -> {
                    if (tower1 instanceof Energizer) {
                        amount.incrementAndGet();
                    }
                });

                if (amount.get() < Energizer.LIMIT) {
                    Energizer energizer = new Energizer();

                    energizer.setParent(root);
                    energizer.place(new Position(event.getX(), event.getY()));

                    energizer.startAttacking();

                    Player.cash -= Energizer.COST;

                    Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));

                    this.tower = -1;
                } else {
                    AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                    try {
                        player.play();
                    } catch (Exception ex) {
                        // Ignore
                    }
                }
            } else if (this.tower == 4 && Player.cash >= Farm.COST && LIST_OF_ACTIVE_TOWERS.size() < Player.LIMIT) {
                AtomicInteger amount = new AtomicInteger();

                LIST_OF_ACTIVE_TOWERS.forEach(tower1 -> {
                    if (tower1 instanceof Farm) {
                        amount.incrementAndGet();
                    }
                });

                if (amount.get() < Farm.LIMIT) {
                    Farm farm = new Farm();

                    farm.setParent(root);
                    farm.place(new Position(event.getX(), event.getY()));

                    farm.startAttacking();

                    Player.cash -= Farm.COST;

                    Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));

                    this.tower = -1;
                } else {
                    AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                    try {
                        player.play();
                    } catch (Exception ex) {
                        // Ignore
                    }
                }
            } else if (this.tower == 5 && Player.cash >= Pyromancer.COST && LIST_OF_ACTIVE_TOWERS.size() < Player.LIMIT) {
                Pyromancer pyromancer = new Pyromancer();

                pyromancer.setParent(root);
                pyromancer.place(new Position(event.getX(), event.getY()));

                pyromancer.startAttacking();

                Player.cash -= Pyromancer.COST;

                Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));

                this.tower = -1;
            } else if (this.tower == 6 && Player.cash >= Gunship.COST && LIST_OF_ACTIVE_TOWERS.size() < Player.LIMIT) {
                AtomicInteger amount = new AtomicInteger();

                LIST_OF_ACTIVE_TOWERS.forEach(tower1 -> {
                    if (tower1 instanceof Gunship) {
                        amount.incrementAndGet();
                    }
                });

                if (amount.get() < Gunship.LIMIT) {
                    Gunship gunship = new Gunship();

                    gunship.setParent(root);
                    gunship.place(new Position(event.getX(), event.getY()));

                    gunship.startAttacking();

                    Player.cash -= Gunship.COST;

                    Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));

                    this.tower = -1;
                } else {
                    AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                    try {
                        player.play();
                    } catch (Exception ex) {
                        // Ignore
                    }
                }
            } else if (this.tower == 7 && Player.cash >= MilitaryBase.COST && LIST_OF_ACTIVE_TOWERS.size() < Player.LIMIT) {
                AtomicInteger amount = new AtomicInteger();

                LIST_OF_ACTIVE_TOWERS.forEach(tower1 -> {
                    if (tower1 instanceof MilitaryBase) {
                        amount.incrementAndGet();
                    }
                });

                if (amount.get() < MilitaryBase.LIMIT) {
                    MilitaryBase militaryBase = new MilitaryBase();

                    militaryBase.setParent(root);
                    militaryBase.place(new Position(event.getX(), event.getY()));

                    militaryBase.startAttacking();

                    Player.cash -= MilitaryBase.COST;

                    Platform.runLater(() -> cashText.setText(STR."$\{Player.cash}"));

                    this.tower = -1;
                } else {
                    AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                    try {
                        player.play();
                    } catch (Exception ex) {
                        // Ignore
                    }
                }
            } else if (this.tower == -1) {

            } else {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                try {
                    player.play();
                } catch (Exception ex) {
                    // Ignore
                }
            }
        });

        this.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                this.tower = -1;
            }
        });

        stage.setScene(this);
    }

    @Override
    public void post_build(@NotNull final Stage stage) {
        stage.show();
    }
}
