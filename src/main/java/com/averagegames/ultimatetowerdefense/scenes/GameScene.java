package com.averagegames.ultimatetowerdefense.scenes;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.characters.towers.legendary.Energizer;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.Farm;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.Gunner;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.Marksman;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.Scout;
import com.averagegames.ultimatetowerdefense.maps.Base;
import com.averagegames.ultimatetowerdefense.maps.Map;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.maps.gui.SkipPanel;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.player.modes.Easy;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Constant;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static com.averagegames.ultimatetowerdefense.characters.enemies.Enemy.LIST_OF_ACTIVE_ENEMIES;
import static com.averagegames.ultimatetowerdefense.characters.towers.Tower.LIST_OF_ACTIVE_TOWERS;
import static com.averagegames.ultimatetowerdefense.maps.Map.*;

/**
 * The {@link GameScene} class is one of a variety of different {@link Scene}'s the {@link Player} will experience while playing the game.
 * The {@link GameScene} class, using the {@link Builder} interface, is a custom-built scene that {@link Map}s can easily be added to and removed from.
 * {@link Player} cash and coins are also managed and altered accordingly within the {@link GameScene} class.
 * @since Ultimate Tower Defense 1.0
 * @see MenuScene
 * @see StoreScene
 * @author AverageProgramer
 */
@SuppressWarnings("all")
public class GameScene extends Scene implements Builder {

    /**
     * A {@link Constant} representing the starting cash for the {@link Player}.
     */
    @Constant
    private static final int STARTING_CASH = 500;

    /**
     * A {@link Constant} representing the starting wave for the {@link Player}.
     */
    @Constant
    private static final int STARTING_WAVE = 1;

    /**
     * The {@link Player}'s {@link Screen} dimensions.
     */
    public static final Rectangle2D SCREEN;

    /**
     * A global {@link AudioPlayer} for playing background music.
     */
    public static final AudioPlayer GLOBAL_PLAYER;

    /**
     * The {@link Text} that will display the {@link Player}'s {@code cash}.
     */
    public static final Text CASH_TEXT;

    /**
     * The {@link Text} that will display the {@link Base}'s {@code health}.
     */
    public static final Text HEALTH_TEXT;

    /**
     * The {@link Text} that will display the {@code wave}.
     */
    public static final Text WAVE_TEXT;

    /**
     * The {@link GameScene}'s parent {@link Group}.
     */
    @NotNull
    private final Group parent;

    /**
     * The {@link GameScene}'s {@link Map} to be loaded.
     */
    @NotNull
    private final Map map;

    @NotNull
    private Tower[] inventory = {
            new Scout(),
            new Marksman(),
            new Gunner(),
            new Energizer(),
            new Farm()
    };

    /**
     * An {@code index} representing which {@link Tower} to place.
     */
    private int towerIndex;

    /**
     * A {@link Tower} that will be used to indicate where the {@link Player} would like to place a {@link Tower}.
     */
    private Tower tempTower;

    private boolean allowSkip;

    /**
     * A {@link Thread} that is responsible for handling all {@link Enemy} {@code spawns}.
     */
    @NotNull
    private Thread spawnThread;

    static {

        // Initializes the screen bounds as the player's screen.
        SCREEN = Screen.getPrimary().getVisualBounds();

        // Initializes the global audio player to a default audio player with no given path.
        GLOBAL_PLAYER = new AudioPlayer();

        // Initializes the text that will display the player's current cash.
        CASH_TEXT = new Text();

        // Initializes the text that will display the base's current health.
        HEALTH_TEXT = new Text();

        // Initializes the text that will display the current wave.
        WAVE_TEXT = new Text();
    }

    /**
     * A constructor that initializes the {@link GameScene} using a given {@link Group}.
     * @param root the parent {@link Group}.
     * @since Ultimate Tower Defense 1.0
     */
    public GameScene(@NotNull final Group root, @NotNull final Map map) {

        // Initializes the scene using a constructor that takes a group object as a parameter from the inherited super class.
        super(root);

        // Sets the scene's parent group to the given group.
        this.parent = root;

        // Sets the scene's map to the given map.
        this.map = map;

        this.towerIndex = -1;

        // Initializes the thread that will be used to spawn enemies.
        this.spawnThread = new Thread(() -> {
            // This thread does nothing by default.
        });
    }

    @Override
    public void pre_build(@NotNull final Stage stage) {

        // Sets the stage to maximize.
        stage.setMaximized(true);

        // Sets it so that the stage can't be resized.
        stage.setResizable(false);

        // Sets the dimensions of the stage to the screen's dimensions.

        stage.setWidth(SCREEN.getWidth());
        stage.setHeight(SCREEN.getHeight());

        // Sets the player's current cash to the global constant representing starting cash.
        Player.cash = STARTING_CASH;

        // Sets the player's current wave to the global constant representing starting wave.
        Player.wave = STARTING_WAVE;
    }

    @Override
    public void build(@NotNull final Stage stage) throws Exception {
        loadMap(this.map, this);

        CASH_TEXT.setText(STR."$\{Player.cash}");

        CASH_TEXT.setFill(Paint.valueOf("#3dbe23"));
        CASH_TEXT.setFont(Font.font(50));

        CASH_TEXT.setX(50);
        CASH_TEXT.setY(SCREEN.getHeight() + 10 - CASH_TEXT.getLayoutBounds().getHeight());

        HEALTH_TEXT.setText(STR."\{Base.health} HP");

        HEALTH_TEXT.setFill(Paint.valueOf("#b60e0e"));
        HEALTH_TEXT.setTextAlignment(TextAlignment.RIGHT);
        HEALTH_TEXT.setFont(Font.font(50));

        HEALTH_TEXT.setX(SCREEN.getWidth() - 60 - HEALTH_TEXT.getLayoutBounds().getWidth());
        HEALTH_TEXT.setY(SCREEN.getHeight() + 20 - HEALTH_TEXT.getLayoutBounds().getHeight());

        WAVE_TEXT.setText(STR."Wave \{Player.wave}");

        WAVE_TEXT.setFont(Font.font(50));
        WAVE_TEXT.setTextAlignment(TextAlignment.RIGHT);

        WAVE_TEXT.setX(SCREEN.getWidth() - 60 - WAVE_TEXT.getLayoutBounds().getWidth());
        WAVE_TEXT.setY(100);

        this.parent.getChildren().addAll(CASH_TEXT, HEALTH_TEXT, WAVE_TEXT);

        Button scoutButton = new Button(STR."Scout: \{Scout.COST}");
        Button marksmanButton = new Button(STR."Marksman:\n\{Marksman.COST}");
        Button gunnerButton = new Button(STR."Gunner: \{Gunner.COST}");
        Button energizerButton = new Button(STR."Energizer:\n\{Energizer.COST}");
        Button farmButton = new Button(STR."Farm: \{Farm.COST}");

        Button[] buttons = new Button[5];

        buttons[0] = scoutButton;
        buttons[1] = marksmanButton;
        buttons[2] = gunnerButton;
        buttons[3] = energizerButton;
        buttons[4] = farmButton;

        double x = (SCREEN.getWidth() / 2) - 50;
        double y = SCREEN.getHeight() - 175;

        for (int i = 0; i < buttons.length; i++) {

            buttons[i].setPrefSize(100, 100);
            buttons[i].setTranslateX(x + (-200 + (i * 100)));
            buttons[i].setTranslateY(y);

            int index = i;

            buttons[i].setOnAction(event -> {
                if (this.tempTower != null) {
                    this.tempTower.eliminate();
                    this.parent.getChildren().remove(this.tempTower.getLoadedTower());
                }

                LIST_OF_ACTIVE_TOWERS.forEach(Tower::deselect);

                this.towerIndex = index;
            });

            this.parent.getChildren().add(buttons[i]);
        }

        this.setOnMouseClicked(event -> {
            Tower tower = switch (this.towerIndex) {
                case 0 ->
                        new Scout();
                case 1 ->
                        new Marksman();
                case 2 ->
                        new Gunner();
                case 3 ->
                        new Energizer();
                case 4 ->
                        new Farm();
                default ->
                        null;
            };

            int cost = switch (tower) {
                case Scout scout ->
                        Scout.COST;
                case Marksman marksman ->
                        Marksman.COST;
                case Gunner gunner ->
                        Gunner.COST;
                case Energizer energizer ->
                        Energizer.COST;
                case Farm farm ->
                        Farm.COST;
                case null ->
                        Integer.MAX_VALUE;
                default ->
                        throw new IllegalStateException("Unexpected value: " + tower);
            };

            if (tower != null) {
                int amount = 0;

                for (Tower t : LIST_OF_ACTIVE_TOWERS) {
                    if (tower.getClass() == t.getClass()) {
                        amount++;
                    }
                }

                if (amount < tower.getPlacementLimit() && Player.cash >= cost) {
                    tower.setParent(this.parent);
                    tower.place(new Position(event.getX(), event.getY()));
                    tower.select();
                    tower.startAttacking();

                    Player.cash -= cost;
                    CASH_TEXT.setText(STR."$\{Player.cash}");
                } else {
                    try {
                        AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Error 1.wav");
                        player.play();
                    } catch (Exception ex) {
                        // Ignore
                    }
                }
            }

            if (this.tempTower != null) {
                this.tempTower.eliminate();
                this.parent.getChildren().remove(this.tempTower.getLoadedTower());
            }

            this.towerIndex = -1;
        });

        this.setOnMouseMoved(event -> {
            this.tempTower = switch (this.towerIndex) {
                case 0 ->
                        inventory[this.towerIndex];
                case 1 ->
                        inventory[this.towerIndex];
                case 2 ->
                        inventory[this.towerIndex];
                case 3 ->
                        inventory[this.towerIndex];
                case 4 ->
                        inventory[this.towerIndex];
                default ->
                        null;
            };

            if (this.towerIndex != -1) {
                if (!this.parent.getChildren().contains(this.tempTower.getLoadedTower())) {
                    this.tempTower.setParent(this.parent);

                    this.tempTower.place(new Position(event.getX(), event.getY()));

                    LIST_OF_ACTIVE_TOWERS.remove(this.tempTower);

                    this.tempTower.getLoadedTower().setOpacity(0.75);
                    this.tempTower.getRange().setVisible(true);
                }

                this.tempTower.setPosition(new Position(event.getX(), event.getY()));

                this.tempTower.getRange().setCenterX(event.getX());
                this.tempTower.getRange().setCenterY(event.getY());
            }
        });

        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (this.tempTower != null) {
                    this.tempTower.eliminate();
                    this.parent.getChildren().remove(this.tempTower.getLoadedTower());
                }

                this.towerIndex = -1;
            }
        });

        GLOBAL_PLAYER.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/music/(Official) Tower Defense Simulator OST - The Horde.wav");
        GLOBAL_PLAYER.loop(AudioPlayer.INDEFINITELY);

        this.spawnThread = new Thread(() -> {
            ENEMY_SPAWNER.spawn(Easy.WAVE_1, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_2, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_3, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_4, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_5, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_6, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_7, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_8, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_9, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_10, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_11, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_12, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_13, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_14, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_15, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_16, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_17, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_18, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_19, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_20, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_21, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_22, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_23, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_24, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_25, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_26, this.parent);
            this.spawnerWait();

            ENEMY_SPAWNER.spawn(Easy.WAVE_27, this.parent);
        });

        this.spawnThread.start();
    }

    @Override
    public void post_build(@NotNull final Stage stage) {

        // Sets the stage's scene as this scene.
        stage.setScene(this);

        // Enables the stage for use.
        stage.show();
    }

    @SuppressWarnings("all")
    private void spawnerWait() {
        SkipPanel panel = new SkipPanel();

        panel.setX((SCREEN.getWidth() / 2) - (panel.getAreaWidth() / 2));
        panel.setY(15);

        panel.setOnAccept(event -> {
            Player.cash += (Player.wave * 5) + 100;
            Platform.runLater(() -> CASH_TEXT.setText(STR."$\{Player.cash}"));

            this.allowSkip = true;
        });

        panel.setOnDeny(event -> Platform.runLater(() -> this.parent.getChildren().remove(panel)));

        Platform.runLater(() -> this.parent.getChildren().add(panel));

        while (!LIST_OF_ACTIVE_ENEMIES.isEmpty() && !this.allowSkip && Base.health > 0);

        Platform.runLater(() -> this.parent.getChildren().remove(panel));

        Player.wave++;
        this.allowSkip = false;

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

        for (Tower tower : LIST_OF_ACTIVE_TOWERS) {
            if (!(tower instanceof Farm)) {
                continue;
            }

            Player.cash += ((Farm) tower).getBonuses()[tower.getLevel()];

            try {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Farm Income 1.wav");
                player.play();
            } catch (Exception ex) {
                // Ignore
            }
        }

        Platform.runLater(() -> {
            CASH_TEXT.setText(STR."$\{Player.cash}");
            WAVE_TEXT.setText(STR."Wave \{Player.wave}");
        });
    }
}
