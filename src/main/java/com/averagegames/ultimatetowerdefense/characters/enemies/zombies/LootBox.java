package com.averagegames.ultimatetowerdefense.characters.enemies.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.EnemySpawnable;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.util.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.application.Platform;
import javafx.scene.image.Image;

@EnemySpawnable
public class LootBox extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/LootBoxZombie.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 30;

    @Property
    private final int speed = 30;

    @Property
    private final int income = 0;

    public LootBox() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);

        super.speed = this.speed;

        super.income = this.income;
    }

    @Override
    protected void onDeath() {
        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 4.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }

        super.updatePathing();

        int enemy = (int) (Math.random() * ((Player.wave >= 20 ? 6 : 5) - 1)) + 1;

        Enemy e = switch (enemy) {
            case 1 ->
                    new Normal();
            case 2 ->
                    new Quick();
            case 3 ->
                    new Slow();
            case 4 ->
                    new Stealthy();
            case 5 ->
                    new Armored();
            default ->
                    null;
        };

        if (e == null) {
            return;
        }

        e.setParent(super.getParent());
        e.setPathing(super.getPathing());
        e.setPosition(super.getPosition());
        e.setReferencePathing(super.getReferencePathing());
        e.setPositionIndex(super.getPositionIndex());
        Platform.runLater(() -> {
            e.spawn();
            e.startMoving();
        });
    }
}
