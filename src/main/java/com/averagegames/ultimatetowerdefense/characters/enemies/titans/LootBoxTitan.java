package com.averagegames.ultimatetowerdefense.characters.enemies.titans;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.maps.tools.Path;
import com.averagegames.ultimatetowerdefense.maps.tools.Position;
import com.averagegames.ultimatetowerdefense.util.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.Property;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class LootBoxTitan extends Enemy {

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/enemies/LootBoxTitan.gif");

    @Property
    private final Type type = Type.REGULAR;

    @Property
    private final int startHealth = 500;

    @Property
    private final int speed = 20;

    @Property
    private final int income = 0;

    public LootBoxTitan() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);

        super.speed = this.speed;

        super.income = this.income;
    }

    @Override
    public void onDeath() {
        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 4.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }

        ArrayList<Position> positions = new ArrayList<>();

        for (int i = 0; i < this.getPathing().positions().length; i++) {
            if (i < this.getPositionIndex()) {
                continue;
            }

            positions.add(this.getPathing().positions()[i]);
        }

        Path path = new Path(positions.toArray(Position[]::new));

        int enemy = (int) (Math.random() * (3 - 1)) + 1;

        switch (enemy) {
            case 1:
                NormalTitan normalTitan = new NormalTitan();

                normalTitan.setParent(this.getParent());
                normalTitan.setPathing(path);
                normalTitan.setReferencePathing(this.getPathing());
                normalTitan.setPositionIndex(this.getPositionIndex());
                normalTitan.spawn(this.getPosition());
                normalTitan.startMoving();

                break;
            case 2:
                GiantTitan giantTitan = new GiantTitan();

                giantTitan.setParent(this.getParent());
                giantTitan.setPathing(path);
                giantTitan.setReferencePathing(this.getPathing());
                giantTitan.setPositionIndex(this.getPositionIndex());
                giantTitan.spawn(this.getPosition());
                giantTitan.startMoving();

                break;
            default:
                break;
        }
    }
}
