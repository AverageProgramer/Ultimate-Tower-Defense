package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.maps.Position;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import lombok.Getter;

public final class Farm extends Tower {

    @Property
    public final int placementCost = 250;

    @Property
    public final int placementLimit = 8;

    @Property
    private final int[] upgradeCosts = {200, 550, 1000, 2500, 5000};

    @Property
    @Getter
    private int[] bonuses = {50, 100, 250, 500, 750, 1500};

    @Property
    private final Image[] images = {
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower0.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower1.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower2.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower3.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower4.gif"),
            new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/farm/FarmTower5.gif"),
    };

    public Farm() {
        super.images = this.images;

        super.setHealth(100);

        super.upgradeCosts = this.upgradeCosts;

        super.placementCost = this.placementCost;

        super.placementLimit = this.placementLimit;
    }

    @Override
    public void upgrade() throws InterruptedException {
        Position oldPos = super.getPosition();

        super.setLevel(super.getLevel() + 1);

        super.getLoadedTower().setImage(super.images[super.getLevel()]);

        super.setPosition(oldPos);
    }
}
