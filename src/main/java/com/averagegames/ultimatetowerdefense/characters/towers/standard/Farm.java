package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import lombok.Getter;

import static com.averagegames.ultimatetowerdefense.player.Player.LIST_OF_ACTIVE_FARMS;

public final class Farm extends Tower {

    @Property
    public static final int COST = 250;

    @Property
    public static final int LIMIT = 8;

    @Property
    @Getter
    private int bonus = 50;

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/FarmTower.gif");

    public Farm() {
        super.image = this.image;

        LIST_OF_ACTIVE_FARMS.add(this);
    }

    @Override
    public void upgrade() throws InterruptedException {

    }
}
