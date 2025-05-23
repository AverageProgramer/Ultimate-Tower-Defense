package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

public final class Pyromancer extends Tower {

    @Property
    public static final int COST = 450;

    @Property
    private final Image[] images = {new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/PyromancerTower.gif")};

    @Property
    private final int[] damages = {0};

    @Property
    private final int[] coolDowns = {350};

    @Property
    private final int startHealth = 100;

    @Property
    private final double radius = 75;

    public Pyromancer() {
        super.images = this.images;
        super.coolDowns = this.coolDowns;
        super.setHealth(this.startHealth);
        super.setRadius(this.radius);
    }

    @Override
    protected void attack(@Nullable final Enemy enemy) {
        if (enemy != null && super.isAlive()) {
            try {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Flamethrower 1.wav");
                player.play();
            } catch (Exception ex) {
                System.out.println("Exception occurred");
            }
        }
    }

    @Override
    public void upgrade() throws InterruptedException {

    }
}
