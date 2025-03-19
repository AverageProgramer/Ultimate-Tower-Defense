package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.assets.ImageLoader;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class Gunship extends Tower {

    @Property
    private final Image[] images = {new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/GunshipRunway.gif")};

    @Property
    public final int placementCost = 750;

    @Property
    public final int placementLimit = 5;

    @Property
    private final int[] upgradeCosts = {300, 350, 1500, 3500, 7500};

    @Property
    private final int[] damages = {1, 2, 2, 3, 6, 10};

    @Property
    private final int[] coolDowns = {250};

    @Property
    private final int startHealth = 800;

    @Property
    private final double radius = 40;

    @Property
    private final double speed = 125;

    @Property
    private final int flightRadius = 130;

    @Property
    private final ImageLoader plane = new ImageLoader();

    AudioPlayer player;

    public Gunship() {
        super.images = this.images;
        super.placementCost = this.placementCost;
        super.placementLimit = this.placementLimit;
        super.upgradeCosts = this.upgradeCosts;
        super.damages = this.damages;
        super.coolDowns = this.coolDowns;
        super.setHealth(this.startHealth);
        super.setRadius(this.radius);
    }

    private double calculateDuration() {
        return (2 * Math.PI) / (this.speed / this.flightRadius);
    }

    @Override
    protected void onPlace() {
        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Plane 1.wav");
            player.loop(AudioPlayer.INDEFINITELY);
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }

        plane.setImage(new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/GunshipTower.gif"));
        plane.setViewOrder(Integer.MIN_VALUE);

        Circle path = new Circle(this.getPosition().x(), this.getPosition().y(), this.flightRadius);

        plane.setX(path.getCenterX() + this.flightRadius);
        plane.setY(path.getCenterY());

        Platform.runLater(() -> {
            assert super.getParent() != null;
            super.getParent().getChildren().add(plane);
        });

        PathTransition animation = new PathTransition();

        animation.setNode(plane);
        animation.setPath(path);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setAutoReverse(false);
        animation.setRate(1);
        animation.setDuration(Duration.seconds(this.calculateDuration()));

        animation.currentTimeProperty().addListener(((observable, oldValue, newValue) -> {
            Circle range = super.getRange();

            range.setCenterX(plane.getCurrentX() + (plane.getImage().getWidth() / 2));
            range.setCenterY(plane.getCurrentY() + (plane.getImage().getHeight() / 2));
        }));

        RotateTransition rotation = new RotateTransition();

        rotation.setToAngle(360);
        rotation.setInterpolator(Interpolator.LINEAR);
        rotation.setRate(1);
        rotation.setDuration(Duration.seconds(this.calculateDuration()));
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.setNode(plane);

        animation.play();
        rotation.play();
    }

    @Override
    public void setPosition(@NotNull final Position position) {

        this.getLoadedTower().setX(position.x() - (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getWidth() / 2 : 0));
        this.getLoadedTower().setY(position.y() - (this.images[super.getLevel()] != null ? Objects.requireNonNull(this.images[super.getLevel()]).getHeight() / 2 : 0));
    }

    @Override
    protected void attack(@Nullable final Enemy enemy) throws InterruptedException {

        // Determines whether the enemy is null.
        if (enemy == null || !super.isAlive()) {

            // Prevents the scout from attacking a null enemy.
            return;
        }

        try {
            player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 3.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }

        enemy.damage(super.damages[super.getLevel()]);
    }

    @Override
    public void upgrade() throws InterruptedException {
        super.setLevel(super.getLevel() + 1);
    }

    @Override
    public void onDeath() {
        player.stop();

        assert this.getParent() != null;
        this.getParent().getChildren().remove(this.plane);
    }
}
