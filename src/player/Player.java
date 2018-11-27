package player;

import processing.core.PApplet;

public class Player {
    private PApplet p;
    private int health, energy;

    public Player(PApplet p) {
        this.p = p;
        this.health = 100;
        this.energy = 3;
    }

    public void drawPlayer() {
        p.fill(0, 255, 0);
        p.ellipse(200, 300, 100, 100);
        p.fill(0);
    }
}
