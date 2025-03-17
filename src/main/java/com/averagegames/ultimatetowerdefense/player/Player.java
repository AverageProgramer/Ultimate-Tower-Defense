package com.averagegames.ultimatetowerdefense.player;

import com.averagegames.ultimatetowerdefense.characters.towers.standard.Farm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    public static int cash = 500;
    public static int LIMIT = 40;
    public static final List<Farm> LIST_OF_ACTIVE_FARMS = Collections.synchronizedList(new ArrayList<>());
}
