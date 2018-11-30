package enemies;

import processing.core.PApplet;

public class ThirdFloorBoss extends Enemy {
    public ThirdFloorBoss(PApplet p, int x, int y) {
        super(p, x, y);
        health = 250;
        height = 450;
        width = 450;
        damage = 25;
        blockGain = 20;
        strengthGain = 10;
        playerDebuff = 3;
        shape = Shape.CIRCLE;
    }
}
