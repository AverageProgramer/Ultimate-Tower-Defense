package com.averagegames.ultimatetowerdefense.scenes;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Wave;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.characters.towers.legendary.Energizer;
import com.averagegames.ultimatetowerdefense.characters.towers.standard.*;
import com.averagegames.ultimatetowerdefense.maps.Base;
import com.averagegames.ultimatetowerdefense.maps.Map;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.maps.gui.SkipPanel;
import com.averagegames.ultimatetowerdefense.player.Inventory;
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
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;

import static com.averagegames.ultimatetowerdefense.characters.enemies.Enemy.LIST_OF_ACTIVE_ENEMIES;
import static com.averagegames.ultimatetowerdefense.characters.towers.Tower.LIST_OF_ACTIVE_TOWERS;
import static com.averagegames.ultimatetowerdefense.maps.Map.*;
import static com.averagegames.ultimatetowerdefense.util.development.LogManager.LOGGER;

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
    private static final int STARTING_CASH = 1000000;

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

    /**
     * An {@code index} representing which {@link Tower} to place.
     */
    private int towerIndex;

    /**
     * A {@link Tower} that will be used to indicate where the {@link Player} would like to place a {@link Tower}.
     */
    private Tower tempTower;

    /**
     * A boolean value that will allow for a {@link Wave} to be {@code skipped}.
     */
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

        // Initializes the tower index so that it starts at -1 and not 0.
        this.towerIndex = -1;

        // Initializes the thread that will be used to spawn enemies.
        this.spawnThread = new Thread(() -> {
            // This thread does nothing by default.
        });

        // A default inventory that can easily be changed.
        Player.inventory = new Inventory(new Tower[] {new Scout(), new Marksman(), new Gunner(), new Energizer(), new Farm()});
    }

    @Override
    public void pre_build(@NotNull final Stage stage) {

        // Sets the stages title to the title of the game.
        stage.setTitle("Ultimate Tower Defense");

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
        CASH_TEXT.setFont(Font.font("Courier New", 50));

        CASH_TEXT.setX(50);
        CASH_TEXT.setY(SCREEN.getHeight() + 10 - CASH_TEXT.getLayoutBounds().getHeight());

        HEALTH_TEXT.setText(STR."\{Base.health} HP");

        HEALTH_TEXT.setFill(Paint.valueOf("#b60e0e"));
        HEALTH_TEXT.setTextAlignment(TextAlignment.RIGHT);
        HEALTH_TEXT.setFont(Font.font("Courier New", 50));

        HEALTH_TEXT.setX(SCREEN.getWidth() - 60 - HEALTH_TEXT.getLayoutBounds().getWidth());
        HEALTH_TEXT.setY(SCREEN.getHeight() + 20 - HEALTH_TEXT.getLayoutBounds().getHeight());

        WAVE_TEXT.setText(STR."Wave \{Player.wave}");

        WAVE_TEXT.setFont(Font.font("Courier New", 50));
        WAVE_TEXT.setTextAlignment(TextAlignment.RIGHT);

        WAVE_TEXT.setX(SCREEN.getWidth() - 60 - WAVE_TEXT.getLayoutBounds().getWidth());
        WAVE_TEXT.setY(100);

        this.parent.getChildren().addAll(CASH_TEXT, HEALTH_TEXT, WAVE_TEXT);

        double x = (SCREEN.getWidth() / 2) - 50;
        double y = SCREEN.getHeight() - 175;

        for (int i = 0; i < Player.inventory.towers().length; i++) {

            Button button = new Button(STR."\{Player.inventory.towers()[i].getClass().getSimpleName()}:\n$\{Player.inventory.towers()[i].getPlacementCost()}");

            button.setPrefSize(100, 100);
            button.setTranslateX(x + ((-50 * (Player.inventory.towers().length - 1)) + (i * 100)));
            button.setTranslateY(y);

            button.setFont(Font.font("Courier New"));
            button.setTextAlignment(TextAlignment.CENTER);

            int index = i;

            button.setOnAction(event -> {
                if (this.tempTower != null) {
                    this.tempTower.eliminate();
                    this.parent.getChildren().remove(this.tempTower.getLoadedTower());
                }

                LIST_OF_ACTIVE_TOWERS.forEach(Tower::deselect);

                this.towerIndex = index;
            });

            Platform.runLater(() -> this.parent.getChildren().add(button));
        }

        this.setOnMouseClicked(event -> {
            Tower tower = null;

            try {
                tower = this.towerIndex != -1 ? Player.inventory.towers()[this.towerIndex].getClass().getConstructor().newInstance() : null;
            } catch (Exception ex) {
                LOGGER.severe(STR."Exception \{ex} thrown when creating object using \{Player.inventory.towers()[this.towerIndex].getClass()}");
            }

            int cost = tower != null ? tower.getPlacementCost() : -1;

            if (tower != null) {
                if (this.tempTower != null) {
                    this.tempTower.eliminate();
                    this.parent.getChildren().remove(this.tempTower.getLoadedTower());
                }

                int amount = 0;

                for (Tower t : LIST_OF_ACTIVE_TOWERS) {
                    if (tower.getClass() == t.getClass()) {
                        amount++;
                    }
                }

                if (amount < tower.getPlacementLimit() && Player.cash >= cost) {
                    Tower finalTower = tower;

                    Platform.runLater(() -> {
                        finalTower.setParent(this.parent);
                        finalTower.place(new Position(event.getX(), event.getY()));
                        finalTower.select();
                        finalTower.startAttacking();
                    });

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

            this.towerIndex = -1;
        });

        this.setOnMouseMoved(event -> {
            this.tempTower = this.towerIndex != -1 ? Player.inventory.towers()[this.towerIndex] : null;

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

                LIST_OF_ACTIVE_TOWERS.forEach(tower -> tower.deselect());

                this.towerIndex = -1;
            }
        });

        // Sets the path of the global audio player.
        GLOBAL_PLAYER.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/music/(Official) Tower Defense Simulator OST - The Horde.wav");

        // Loops the previously set audio file for an indefinite amount of times.
        GLOBAL_PLAYER.loop(AudioPlayer.INDEFINITELY);

        // Creates a new thread that will be responsible for spawning the enemies during the game.
        this.spawnThread = new Thread(() -> {

            // Spawns easy mode's 1st wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_1, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 2nd wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_2, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 3rd wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_3, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 4th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_4, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 5th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_5, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 6th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_6, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 7th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_7, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 8th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_8, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 9th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_9, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 10th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_10, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 11th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_11, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 12th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_12, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 13th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_13, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 14th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_14, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 15th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_15, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 16th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_16, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 17th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_17, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 18th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_18, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 19th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_19, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 20th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_20, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 21st wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_21, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 22nd wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_22, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 23rd wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_23, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 24th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_24, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 25th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_25, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 26th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_26, this.parent);
            this.spawnerWait();

            // Spawns easy mode's 27th wave.

            ENEMY_SPAWNER.spawn(Easy.WAVE_27, this.parent);
        });

        // Starts the thread responsible for handling enemy spawns.
        this.spawnThread.start();
    }

    @Override
    public void post_build(@NotNull final Stage stage) {

        // Sets the stage's scene as this scene.
        stage.setScene(this);

        // Enables the stage for use.
        stage.show();
    }

    /**
     * Causes a {@link Thread} to wait for 3 specific conditions.
     * These conditions are the list of every active {@link Enemy} being empty, the {@link Player} {@code skipping} the {@link Wave}, or the {@link Base} {@code health} going to 0.
     * @since Ultimate Tower Defense 1.0
     */
    @Blocking
    @SuppressWarnings("all")
    private void spawnerWait() {

        // Creates a new skip panel that will display on the screen.
        SkipPanel panel = new SkipPanel();

        // Sets the x and y coordinates of the skip panel.

        panel.setX((SCREEN.getWidth() / 2) - (panel.getAreaWidth() / 2));
        panel.setY(15);

        // Sets a task to be performed whenever the player chooses to skip the current wave.
        panel.setOnAccept(event -> {

            // Adds a bonus amount of cash to the player's cash.
            Player.cash += (Player.wave * 5) + 100;

            // Updates the text displaying the player's current cash.
            Platform.runLater(() -> CASH_TEXT.setText(STR."$\{Player.cash}"));

            // Sets it so that the waiting will be finished.
            // This is one of the 3 conditions that will cause this method to stop waiting.
            this.allowSkip = true;
        });

        // Removes the skip panel from the scene's parent group if the player chooses to deny the skip.
        panel.setOnDeny(event -> Platform.runLater(() -> this.parent.getChildren().remove(panel)));

        // Allows the skip panel to be added to the scene's parent group without any issues.
        // Adds the skip panel to the scene's parent group.
        Platform.runLater(() -> this.parent.getChildren().add(panel));

        // Causes the thread to wait until all enemies have been eliminated, the player skips, or the base health reaches 0.
        while (!LIST_OF_ACTIVE_ENEMIES.isEmpty() && !this.allowSkip && Base.health > 0);

        // Allows the skip panel to be removed to the scene's parent group without any issues.
        // Removes the skip panel to the scene's parent group.
        Platform.runLater(() -> this.parent.getChildren().remove(panel));

        // Adds a wave to the player's current wave.
        Player.wave++;

        // Sets it so that future waiting will not end immediately.
        this.allowSkip = false;

        // A loop that will iterate through 4 times in order to act as a timer.
        for (int i = 0; i < 4; i++) {

            // A try-catch statement that will allow for the current thread to sleep.
            try {

                // Determines whether the index is 0.
                if (i == 0) {

                    // Causes the current thread to wait for the 1 second to pass.
                    Thread.sleep(1000);
                }

                // Determines whether the index is less than 3.
                if (i < 3) {

                    // Creates a new audio player that will play an audio file.
                    AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Timer Sound Effect 1.wav");

                    // Plays the audio file.
                    player.play();
                }

                // Causes the current thread to wait for the 1 second to pass.
                Thread.sleep(1000);
            } catch (Exception ex) {
                // The exception does not need to be handled.
            }
        }

        // A loop that will iterate through the list containing every active tower.
        for (Tower tower : LIST_OF_ACTIVE_TOWERS) {

            // Determines whether the tower is not a farm.
            if (!(tower instanceof Farm)) {

                // Skips to the next iteration of the loop.
                continue;
            }

            // Adds a farm bonus to the player's current cash.
            // For every farm, this bonus will be added to the player's current cash.
            Player.cash += ((Farm) tower).getBonuses()[tower.getLevel()];

            // A try-catch statement that will allow an audio player to play an audio file.
            try {

                // Creates a new audio player that will play an audio file.
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Farm Income 1.wav");

                // Plays the audio file.
                player.play();
            } catch (Exception ex) {
                // The exception does not need to be handled.
            }
        }

        // Allows the text-based GUI elements to be edited while on a seperate thread than the JavaFX application thread.
        Platform.runLater(() -> {

            // Updates the text displaying the player's current cash.
            CASH_TEXT.setText(STR."$\{Player.cash}");

            // Updates the text displaying the player's current wave.
            WAVE_TEXT.setText(STR."Wave \{Player.wave}");
        });
    }
}
