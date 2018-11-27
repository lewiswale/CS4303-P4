package enemies;

import processing.core.PApplet;

public class SmallSquare extends Enemy {

    public SmallSquare(PApplet p, int x, int y) {
        super(p, x, y);
        health = 100;
    }

    @Override
    public void drawEnemy() {
        p.fill(255, 0, 0);
        p.rect(x, y, 50, 50);
        p.fill(0);
    }
}
