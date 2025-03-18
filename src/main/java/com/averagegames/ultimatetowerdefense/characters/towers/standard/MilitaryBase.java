package com.averagegames.ultimatetowerdefense.characters.towers.standard;

import com.averagegames.ultimatetowerdefense.characters.towers.Tower;
import com.averagegames.ultimatetowerdefense.util.development.Property;
import javafx.scene.image.Image;
import lombok.Getter;

import static com.averagegames.ultimatetowerdefense.player.Player.LIST_OF_ACTIVE_FARMS;

public final class MilitaryBase extends Tower {

    @Property
    public static final int COST = 400;

    @Property
    public static final int LIMIT = 5;

    @Property
    private final Image[] images = {new Image("file:src/main/resources/com/averagegames/ultimatetowerdefense/images/towers/MilitaryBaseTower.gif")};

    public MilitaryBase() {
        super.images = this.images;
    }

    @Override
    public void upgrade() throws InterruptedException {

    }
}
