package enemies;

import processing.core.PApplet;

public class SharpBoy extends Enemy {
    public SharpBoy(PApplet p, int x, int y) {
        super(p, x, y);
        health = 25;
        height = 100;
        width = 100;
        damage = 7;
        blockGain = 5;
        strengthGain = 1;
        debuff = 1;
        shape = Shape.TRIANGLE;
    }
}
