package enemies;

import processing.core.PApplet;

public class FirstFloorBoss extends Enemy {
    public FirstFloorBoss(PApplet p, int x, int y) {
        super(p, x, y);
        health = 100;
        height = 200;
        width = 200;
        damage = 12;
        blockGain = 10;
        strengthGain = 5;
        playerDebuff = 2;
        shape = Shape.TRIANGLE;
    }
}
