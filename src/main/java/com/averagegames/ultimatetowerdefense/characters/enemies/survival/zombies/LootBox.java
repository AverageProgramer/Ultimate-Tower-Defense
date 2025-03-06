package com.averagegames.ultimatetowerdefense.characters.enemies.survival.zombies;

import com.averagegames.ultimatetowerdefense.characters.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.characters.enemies.Type;
import com.averagegames.ultimatetowerdefense.characters.enemies.Zombie;
import com.averagegames.ultimatetowerdefense.maps.Path;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.util.assets.AudioPlayer;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import com.averagegames.ultimatetowerdefense.util.development.Specific;
import com.averagegames.ultimatetowerdefense.util.development.SpecificAnnotation;
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
    private final int income = 1;

    public LootBox() {
        super.image = this.image;

        super.type = this.type;

        super.setHealth(this.startHealth);
        super.damage = this.damage;

        super.speed = this.speed;

        super.income = this.income;
    }

    @Override
    @Specific(value = Enemy.class, subclasses = true)
    protected void onDeath() {
        SpecificAnnotation.verify(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass(), new Object() {}.getClass().getEnclosingMethod());

        try {
            AudioPlayer player = new AudioPlayer("src/main/resources/com/averagegames/ultimatetowerdefense/audio/effects/Zombie Death 4.wav");
            player.play();
        } catch (Exception ex) {
            System.out.println("Exception occurred");
        }

        ArrayList<Position> positions = new ArrayList<>();

        for (int i = 0; i < Objects.requireNonNull(this.getPathing()).positions().length; i++) {
            if (i < this.getPositionIndex()) {
                continue;
            }

            positions.add(this.getPathing().positions()[i]);
        }

        Path path = new Path(positions.toArray(Position[]::new));

        int enemy = (int) (Math.random() * (5 - 1)) + 1;

        switch (enemy) {
            case 1:
                Normal normal = new Normal();

                normal.setParent(this.getParent());
                normal.setPathing(path);
                normal.setReferencePathing(this.getPathing());
                normal.setPositionIndex(this.getPositionIndex());
                normal.spawn(this.getPosition());
                normal.startMoving();

                break;
            case 2:
                Quick quick = new Quick();

                quick.setParent(this.getParent());
                quick.setPathing(path);
                quick.setReferencePathing(this.getPathing());
                quick.setPositionIndex(this.getPositionIndex());
                quick.spawn(this.getPosition());
                quick.startMoving();

                break;
            case 3:
                Slow slow = new Slow();

                slow.setParent(this.getParent());
                slow.setPathing(path);
                slow.setReferencePathing(this.getPathing());
                slow.setPositionIndex(this.getPositionIndex());
                slow.spawn(this.getPosition());
                slow.startMoving();

                break;
            case 4:
                Stealthy stealthy = new Stealthy();

                stealthy.setParent(this.getParent());
                stealthy.setPathing(path);
                stealthy.setReferencePathing(this.getPathing());
                stealthy.setPositionIndex(this.getPositionIndex());
                stealthy.spawn(this.getPosition());
                stealthy.startMoving();

                break;
            default:
                break;
        }
    }
}
