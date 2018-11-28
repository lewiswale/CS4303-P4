package enemies;

import processing.core.PApplet;

public class SmallSquare extends Enemy {

    public SmallSquare(PApplet p, int x, int y) {
        super(p, x, y);
        health = 100;
        height = 50;
        width = 50;
    }
}
