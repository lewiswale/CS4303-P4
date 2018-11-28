package enemies;

import processing.core.PApplet;

public class SmallSquare extends Enemy {

    public SmallSquare(PApplet p, int x, int y) {
        super(p, x, y);
        health = 10;
        height = 50;
        width = 50;
        damage = 6;
        blockGain = 3;
        strengthGain = 1;
    }
}
