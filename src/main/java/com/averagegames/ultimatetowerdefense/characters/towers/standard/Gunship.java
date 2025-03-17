package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
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
import org.jetbrains.annotations.Nullable;

public final class Gunship extends Tower {

    @Property
    public static final int COST = 750;

    @Property
    public static final int LIMIT = 5;

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/GunshipRunway.gif");

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
    private final int flightRadius = 100;


    public Gunship() {
        super.image = this.image;
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

        ImageLoader plane = new ImageLoader();

        plane.setImage(new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/GunshipTower.gif"));
        plane.setViewOrder(Integer.MIN_VALUE);

        Circle path = new Circle(super.getPosition().x(), super.getPosition().y(), this.flightRadius);

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
    protected void attack(@Nullable final Enemy enemy) throws InterruptedException {

        // Determines whether the enemy is null.
        if (enemy == null || !super.isAlive()) {

            // Prevents the scout from attacking a null enemy.
            return;
        }

        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 3.wav");
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
}
