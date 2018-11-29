package enemies;

import processing.core.PApplet;

public class Roller extends Enemy {
    public Roller(PApplet p, int x, int y) {
        super(p, x, y);
        health = 28;
        height = 100;
        width = 100;
        damage = 10;
        blockGain = 0;
        strengthGain = 0;
        playerDebuff = 0;
        shape = Shape.CIRCLE;
    }
}
