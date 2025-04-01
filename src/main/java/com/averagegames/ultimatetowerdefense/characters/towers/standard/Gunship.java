package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.scenes.game.GameScene;
import com.averagegames.ultimatetowerdefense.util.animation.CircularTranslationHandler;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.assets.ImageLoader;
import com.averagegames.ultimatetowerdefense.util.development.Element;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * The {@link Gunship} is a {@link Tower} that the {@link Player} will be able to purchase with {@code silver}.
 * Providing high {@code damage} per second and a secondary bomb / missile {@code attack}, the {@link Gunship} is a great {@link Tower} for intermediate {@link Player}s.
 * @since Ultimate Tower Defense 1.0
 * @see Tower
 * @author AverageProgramer
 */
public final class Gunship extends Tower {

    /**
     * The {@link Gunship}'s runway {@link Image} per {@code level}.
     */
    @Property
    private final Image[] runwayImages = {

            // The Gunship's level 0 runway image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipRunway.gif"),

            // The Gunship's level 1 runway image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipRunway.gif"),

            // The Gunship's level 2 runway image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipRunway.gif"),

            // The Gunship's level 3 runway image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipRunway.gif"),

            // The Gunship's level 4 runway image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipRunway.gif"),

            // The Gunship's level 5 runway image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipRunway.gif")
    };

    /**
     * The {@link Gunship}'s placement {@code cost}.
     */
    @Property
    private final int placementCost = 750;

    /**
     * The {@link Gunship}'s placement {@code limit}.
     */
    @Property
    private final int placementLimit = 5;

    /**
     * The {@link Gunship}'s {@code upgrade} costs per {@code level}.
     */
    @Property
    private final int[] upgradeCosts = {300, 350, 1500, 3500, 7500};

    /**
     * The {@code damage} the {@link Gunship} can do during an {@code attack} per {@code level}.
     */
    @Property
    private final int[] damages = {1, 2, 2, 3, 6, 10};

    /**
     * The {@link Gunship}'s cool down between {@code attacks} per {@code level}.
     */
    @Property
    private final int[] coolDowns = {250, 250, 250, 175, 175, 100};

    /**
     * The {@link Gunship}'s starting {@code health}.
     */
    @Property
    private final int startHealth = 100;

    /**
     * The {@link Gunship}'s {@code range} radius in pixels per {@code level}.
     */
    @Property
    private final double[] radii = {40, 40, 40, 50, 60, 80};

    /**
     * The {@link Gunship}'s flight {@code speed} in pixels per second per {@code level}.
     */
    @Property(unique = true)
    private final double[] flightSpeeds = {125, 125, 125, 125, 125, 180};

    /**
     * The {@link Gunship}'s flight speed in pixels per {@code level}.
     */
    @Property(unique = true)
    private final int flightRadius = 130;

    /**
     * The {@link AudioPlayer} that will be used to play {@code looping} sound effects for the {@link Gunship}.
     */
    @Element
    private final AudioPlayer player;

    /**
     * The {@link Gunship}'s plane {@link Image} per {@code level}.
     */
    @Property(unique = true)
    private final Image[] planeImages = {

            // The Gunship's level 0 plane image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipTower0.gif"),

            // The Gunship's level 1 plane image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipTower1.gif"),

            // The Gunship's level 2 plane image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipTower2.gif"),

            // The Gunship's level 3 plane image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipTower3.gif"),

            // The Gunship's level 4 plane image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipTower4.gif"),

            // The Gunship's level 5 plane image.
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/gunship/GunshipTower5.gif")
    };

    /**
     * The {@link Gunship}'s animation.
     */
    @Element
    private CircularTranslationHandler animation;

    /**
     * The {@link Gunship}'s plane {@link Element}
     */
    @Element
    private final ImageLoader plane;

    /**
     * The {@link Gunship}'s flight {@code path}.
     */
    @Element
    private final Circle flightPath;

    /**
     * A constructor that properly sets the attributes of the {@link Gunship} {@link Tower}.
     */
    public Gunship() {

        // Properly sets the gunship's images to the finalized images.
        super.images = this.runwayImages;

        // Properly sets the gunship's placement cost to the finalized placement cost.
        super.placementCost = this.placementCost;

        // Properly sets the gunship's placement limit to the finalized placement limit.
        super.placementLimit = this.placementLimit;

        // Properly sets the gunship's upgrade costs to the finalized costs.
        super.upgradeCosts = this.upgradeCosts;

        // Properly sets the gunship's damages per attack to the finalized damages per attack.
        super.damages = this.damages;

        // Properly sets the gunship's cool downs in between attacks to the finalized cool downs.
        super.coolDowns = this.coolDowns;

        // Properly sets the gunship's health to the finalized starting health.
        super.setHealth(this.startHealth);

        super.setSpaceLength(75);

        // Initializes the audio player for looping audio files.
        this.player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Plane 1.wav");

        // Initializes the plane.
        this.plane = new ImageLoader();

        // Sets the image of the plane to the first image of the plane.
        this.plane.setImage(this.planeImages[0]);

        // Sets the view order of the plane to be directly behind GUI elements like buttons and text.
        // This will make it appear as if the plane is flying above all other towers and enemies.
        this.plane.setViewOrder(GameScene.GUI_LAYER + 1);

        // Initializes the gunship's flight path using the finalized flight radius.
        this.flightPath = new Circle(this.flightRadius);
    }

    /**
     * Sets the {@link Gunship}'s {@link Position} to a newly given {@link Position}.
     * @param position the new {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    public void setPosition(@NotNull final Position position) {

        // Updates the tower's x and y coordinates to the given position's x and y coordinates.
        // The overridden method will set the gunship's position at the center instead of bottom-middle position.

        this.getLoadedTower().setX(position.x() - (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getWidth() / 2 : 0));
        this.getLoadedTower().setY(position.y() - (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getHeight() / 2 : 0));
    }

    /**
     * Gets the {@link Position} the {@link Gunship} is currently at.
     * @return the {@link Tower}'s current {@link Position}.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    @Contract(" -> new")
    public @NotNull Position getPosition() {

        // Returns the tower's current position.
        // The overridden method will return the gunship's position at the center instead of bottom-middle position.
        return new Position(super.getLoadedTower().getCurrentX() + (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getWidth() / 2 : 0), super.getLoadedTower().getCurrentY() + (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getHeight() / 2 : 0));
    }

    /**
     * The {@link Scout}'s uniquely implemented on {@code placement} action.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    @SuppressWarnings("unused")
    protected void onPlace() {

        // Sets the range's radius to the gunship's level 0 range radius.
        super.setRadius(this.radii[0]);

        // Sets the flight path's x and y coordinates to be at the gunships current center position.

        this.flightPath.setCenterX(this.getPosition().x());
        this.flightPath.setCenterY(this.getPosition().y());

        // Sets the x and y coordinates of the plane to be at the right-most point on the flight path.

        this.plane.setTranslateX(this.flightPath.getCenterX() + this.flightRadius);
        this.plane.setTranslateY(this.flightPath.getCenterY());

        // Determines whether the gunship's parent is not null.
        if (this.getParent() != null) {

            // Adds the plane to the gunship's parent.
            this.getParent().getChildren().add(this.plane);
        }

        // Creates a new circular translation handler that will allow the gunship to fly in a circular path.
        this.animation = new CircularTranslationHandler();

        // Sets the animation's node to the previously initialized plane.
        this.animation.setNode(this.plane);

        // Sets the animation's path to the previously initialized flight path.
        this.animation.setPath(this.flightPath);

        // Sets the animation's speed to the level 0 speed for the gunship.
        this.animation.setSpeed(this.flightSpeeds[super.getLevel()]);

        // Sets the animation's cycle count to loop and infinite amount of times.
        this.animation.setCycleCount(CircularTranslationHandler.INFINITE);

        // Allows the gunship's animation to be starting without any issues.
        // Starts the animation.
        Platform.runLater(() -> this.animation.start());

        // Adds listeners to the plane's translate x and y properties.
        // This will allow the range's position to update while the plane moves around its circular flight path.

        this.plane.translateXProperty().addListener(((observable, oldValue, newValue) -> super.getRange().setCenterX(plane.getCurrentX() + (plane.getImage().getWidth() / 2))));
        this.plane.translateYProperty().addListener(((observable, oldValue, newValue) -> super.getRange().setCenterY(plane.getCurrentY() + (plane.getImage().getHeight() / 2))));

        // A try-catch statement that will allow the audio player for looping audio files to play.
        try {

            // Loops the audio file.
            this.player.loop(AudioPlayer.INDEFINITELY);
        } catch (Exception ex) {
            // The exception does not need to be handled.
        }
    }

    /**
     * The {@link Scout}'s uniquely implemented on {@code death} action.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    public void onDeath() {

        // Determines whether the gunship's parent is not null.
        if (super.getParent() != null) {

            // Removes the plane from the gunship's parent.
            super.getParent().getChildren().remove(this.plane);

            // Removes the flight path from the gunship's parent.
            super.getParent().getChildren().remove(this.flightPath);
        }

        // Stops playing the audio file.
        this.player.stop();
    }

    /**
     * The {@link Gunship}'s uniquely implemented {@code attack}.
     * @param enemy the {@link Enemy} to attack.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    protected void attack(@Nullable final Enemy enemy) {

        // Determines whether the given enemy is null or whether the gunship is still alive.
        if (enemy == null || !super.isAlive()) {

            // Prevents the tower from attacking a null enemy or after being eliminated.
            return;
        }

        // A try-catch statement that will allow an audio player to play an audio file.
        try {

            // Creates a new audio player that will be used to play an audio file.
            AudioPlayer effectPlayer = new AudioPlayer();

            // Determines whether the gunship's level is less than 5.
            if (super.getLevel() < 5) {

                // Sets the audio player's path to the path for the audio file for levels 1 - 4.
                effectPlayer.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 3.wav");
            } else {

                // Sets the audio player's path to the path for the audio file for level 5.
                effectPlayer.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 6.wav");
            }

            // Plays the audio file.
            effectPlayer.play();
        } catch (Exception ex) {
            // The exception does not need to be handled.
        }

        // Damages the given enemy.
        enemy.damage(super.damages[super.getLevel()]);
    }

    /**
     * The {@link Gunship}'s uniquely implemented {@code upgrades}.
     * @since Ultimate Tower Defense 1.0
     */
    @Override
    public void upgrade() throws InterruptedException {

        // Determines whether the gunship's current level is 5.
        if (super.getLevel() == 5) {

            // Prevents the gunship from being upgraded beyond level 5.
            return;
        }

        // Increases the gunship's level by 1.
        super.setLevel(super.getLevel() + 1);

        // Determines whether the gunship's new level is 4.
        if (super.getLevel() == 4) {

            // Enables the gunship's hidden detection capabilities.
            super.setHiddenDetection(true);
        }

        // Determines whether the gunship's new level is 5.
        if (super.getLevel() == 5) {

            // Updates the animation's speed to a new speed.
            this.animation.setSpeed(this.flightSpeeds[super.getLevel()]);

            // Stops playing the audio file.
            player.stop();

            // Updates the audio player for looping audio files' path to the path for the audio file for level 5.
            player.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Plane 2.wav");

            // A try-catch statement that will allow an audio player to play an audio file.
            try {

                // Loops the audio file.
                player.loop(AudioPlayer.INDEFINITELY);
            } catch (Exception ex) {
                // The exception does not need to be handled.
            }
        }

        // Sets the plane's image to a new image.
        this.plane.setImage(this.planeImages[super.getLevel()]);

        // Returns the plane to its default rotation angle.
        this.plane.setRotate(0);

        // Updates the gunship's range radius to a new radius.
        super.setRadius(this.radii[super.getLevel()]);

        // Refreshes the animation so that the speed changes are registered.
        this.animation.refresh();

        // Updates the gunship's attack timer to use a new cool down between attacks.
        super.attackTimer.setHandleTime(super.coolDowns[super.getLevel()]);
    }
}
