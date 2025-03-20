package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.scenes.GameScene;
import com.averagegames.ultimatetowerdefense.util.animation.CircularTranslationHandler;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.assets.ImageLoader;
import com.averagegames.ultimatetowerdefense.util.development.Element;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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

    public Gunship() {
        super.images = this.runwayImages;

        super.placementCost = this.placementCost;

        super.placementLimit = this.placementLimit;

        super.upgradeCosts = this.upgradeCosts;

        super.damages = this.damages;

        super.coolDowns = this.coolDowns;

        super.setHealth(this.startHealth);

        super.setRadius(0);

        this.player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Plane 1.wav");

        this.plane = new ImageLoader();

        this.plane.setImage(this.planeImages[0]);

        this.plane.setViewOrder(GameScene.GUI_LAYER + 1);

        this.flightPath = new Circle(this.flightRadius);

        this.flightPath.setOpacity(0.25);
        this.flightPath.setFill(Color.TRANSPARENT);

        this.flightPath.setStroke(Paint.valueOf("#3a5bb6"));
        this.flightPath.setStrokeWidth(1);

        this.flightPath.setViewOrder(GameScene.HIGHLIGHT_LAYER);
    }

    @Override
    public void setPosition(@NotNull final Position position) {
        this.getLoadedTower().setX(position.x() - (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getWidth() / 2 : 0));
        this.getLoadedTower().setY(position.y() - (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getHeight() / 2 : 0));
    }

    @Override
    public @NotNull Position getPosition() {
        return new Position(super.getLoadedTower().getCurrentX() + (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getWidth() / 2 : 0), super.getLoadedTower().getCurrentY() + (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getHeight() / 2 : 0));
    }

    private double calculateDuration() {
        return (2 * Math.PI) / (this.flightSpeeds[super.getLevel()] / this.flightRadius);
    }

    @Override
    @SuppressWarnings("unused")
    protected void onPlace() {
        super.setRadius(this.radii[0]);

        this.flightPath.setCenterX(this.getPosition().x());
        this.flightPath.setCenterY(this.getPosition().y());

        this.plane.setTranslateX(this.flightPath.getCenterX() + this.flightRadius);
        this.plane.setTranslateY(this.flightPath.getCenterY());

        if (this.getParent() != null) {
            Platform.runLater(() -> this.getParent().getChildren().add(this.plane));
        }

        this.animation = new CircularTranslationHandler();

        this.animation.setNode(this.plane);
        this.animation.setPath(this.flightPath);

        this.animation.setSpeed(this.flightSpeeds[super.getLevel()]);

        this.animation.setCycleCount(CircularTranslationHandler.INFINITE);

        this.animation.start();

        this.plane.translateXProperty().addListener(((observable, oldValue, newValue) -> {
            super.getRange().setCenterX(plane.getCurrentX() + (plane.getImage().getWidth() / 2));
        }));

        this.plane.translateYProperty().addListener(((observable, oldValue, newValue) -> {
            super.getRange().setCenterY(plane.getCurrentY() + (plane.getImage().getHeight() / 2));
        }));

        try {
            this.player.loop(AudioPlayer.INDEFINITELY);
        } catch (Exception ex) {
            // The exception does not need to be handled.
        }
    }

    @Override
    public void onDeath() {
        if (super.getParent() != null) {
            super.getParent().getChildren().remove(this.plane);
            super.getParent().getChildren().remove(this.flightPath);
        }

        this.player.stop();
    }

    @Override
    protected void attack(@Nullable final Enemy enemy) {

        if (enemy == null || !super.isAlive()) {
            return;
        }

        try {
            AudioPlayer effectPlayer = new AudioPlayer();

            if (super.getLevel() < 5) {
                effectPlayer.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 3.wav");
            } else {
                effectPlayer.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 6.wav");
            }

            effectPlayer.play();
        } catch (Exception ex) {
            // The exception does not need to be handled.
        }

        enemy.damage(super.damages[super.getLevel()]);
    }

    @Override
    public void upgrade() throws InterruptedException {
        if (super.getLevel() >= 5) {
            return;
        }

        super.setLevel(super.getLevel() + 1);

        if (super.getLevel() == 4) {
            super.setHiddenDetection(true);
        }

        if (super.getLevel() == 5) {
            this.animation.setSpeed(this.flightSpeeds[super.getLevel()]);

            player.stop();
            player.setPathname("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Plane 2.wav");

            try {
                player.loop(AudioPlayer.INDEFINITELY);
            } catch (Exception ex) {
                // The exception does not need to be handled.
            }
        }

        this.plane.setImage(this.planeImages[super.getLevel()]);
        this.plane.setRotate(0);

        super.setRadius(this.radii[super.getLevel()]);

        this.animation.refresh();
    }
}
