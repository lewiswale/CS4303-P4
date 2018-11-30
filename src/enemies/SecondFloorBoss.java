package enemies;

import processing.core.PApplet;

public class SecondFloorBoss extends Enemy {
    public SecondFloorBoss(PApplet p, int x, int y) {
        super(p, x, y);
        health = 150;
        height = 300;
        width = 300;
        damage = 20;
        blockGain = 20;
        strengthGain = 7;
        playerDebuff = 2;
        shape = Shape.RECTANGLE;
    }
}
