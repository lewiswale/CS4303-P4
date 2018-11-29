package enemies;

import processing.core.PApplet;

public class TallBoy extends Enemy {
    public TallBoy(PApplet p, int x, int y) {
        super(p, x, y);
        health = 20;
        height = 120;
        width = 60;
        damage = 7;
        blockGain = 2;
        strengthGain = 1;
        playerDebuff = 0;
        shape = Shape.RECTANGLE;
    }
}
