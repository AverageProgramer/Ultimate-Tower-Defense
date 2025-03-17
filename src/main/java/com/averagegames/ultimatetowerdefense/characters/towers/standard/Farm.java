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
    private final int[] upgradeCosts = {200, 550, 1000, 2500, 5000};

    @Property
    @Getter
    private int[] bonuses = {50, 100, 250, 500, 750, 1500};

    @Property
    private final Image image = new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/FarmTower.gif");

    public Farm() {
        super.image = this.image;

        super.upgradeCosts = this.upgradeCosts;

        LIST_OF_ACTIVE_FARMS.add(this);
    }

    @Override
    public void upgrade() throws InterruptedException {
        super.setLevel(super.getLevel() + 1);
    }
}
