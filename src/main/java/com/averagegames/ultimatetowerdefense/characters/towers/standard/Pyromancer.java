package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

public class Pyromancer extends Tower {

    public static final int COST = 750;

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/PyromancerTower.gif");

    @Property
    private final int damage = 0;

    @Property
    private final int coolDown = 350;

    @Property
    private final double radius = 75;

    public Pyromancer() {
        super.image = this.image;
        super.damage = this.damage;
        super.coolDown = this.coolDown;
        super.setRadius(this.radius);
    }

    @Override
    protected void attack(@Nullable final Enemy enemy) {
        if (enemy != null) {
            try {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Flamethrower 1.wav");
                player.play();
            } catch (Exception ex) {
                System.out.println("Exception occurred");
            }

            // Basic burn damage.
            if (!enemy.isBurning()) {
                enemy.burn(1,4);
            }
        }
    }

    @Override
    public void upgrade() throws InterruptedException {

    }
}
