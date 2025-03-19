package com.averagegames.ultimatetowerdefense.characters.enemies.survival.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.characters.enemies.Zombie;
import com.averagegames.ultimatetowerdefense.maps.Path;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.player.Player;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

@Zombie
public class LootBox extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/LootBoxZombie.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 30;

    @Property
    private final int damage = 1;

    @Property
    private final int speed = 30;

    @Property
    private final int income = 0;

    public LootBox() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);
        super.damage = this.damage;

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

        ArrayList<Position> positions = new ArrayList<>();

        for (int i = 0; i < Objects.requireNonNull(this.getReferencePathing()).positions().length; i++) {
            if (i < this.getPositionIndex()) {
                continue;
            }
            positions.add(this.getReferencePathing().positions()[i]);
        }

        Path path = new Path(positions.toArray(Position[]::new));

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

        e.setParent(this.getParent());
        e.setPathing(path);
        e.setReferencePathing(super.getReferencePathing());
        e.setPositionIndex(this.getPositionIndex());
        e.spawn(this.getPosition());
        e.startMoving();
    }
}
