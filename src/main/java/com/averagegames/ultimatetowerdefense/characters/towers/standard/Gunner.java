package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

public final class Gunner extends Tower {

    public static final int COST = 500;

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/GunnerTower.gif");

    @Property
    private final int damage = 1;

    @Property
    private final int coolDown = 1250;

    @Property
    private final double radius = 125;

    public Gunner() {
        super.image = this.image;
        super.damage = this.damage;
        super.coolDown = this.coolDown;
        super.setRadius(this.radius);
    }

    @Override
    protected void attack(@Nullable final Enemy enemy) throws InterruptedException {
        // Determines whether the enemy is null.
        if (enemy == null) {

            // Prevents the scout from attacking a null enemy.
            return;
        }

        for (int i = 0; i < 3; i++) {
            try {
                AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Gunshot 1.wav");
                player.play();
            } catch (Exception ex) {
                System.out.println("Exception occurred");
            }

            enemy.damage(this.damage);

            Thread.sleep(200);
        }
    }

    @Override
    public void upgrade() throws InterruptedException {

    }
}
