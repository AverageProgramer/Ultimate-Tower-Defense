package com.averagegames.ultimatetowerdefense.elements.enemies.campaign;

import com.averagegames.ultimatetowerdefense.elements.enemies.Enemy;
import com.averagegames.ultimatetowerdefense.elements.enemies.Zombie;
import com.averagegames.ultimatetowerdefense.elements.enemies.tools.Campaign;
import com.averagegames.ultimatetowerdefense.tools.annotations.GameElement;

import java.io.Serial;

@Campaign
@GameElement(type = "character")
public class Normal extends Enemy implements Zombie {
    @Serial
    private static final long serialVersionUID = 1090544581496562698L;
}
