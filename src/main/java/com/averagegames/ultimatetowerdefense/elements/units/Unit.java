package com.averagegames.ultimatetowerdefense.elements.units;

public abstract class Unit {
    protected abstract void onSpawn();

    protected abstract void onDeath();

    protected abstract void attack();

    protected abstract void special();
}
