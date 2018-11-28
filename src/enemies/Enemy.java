package enemies;

import processing.core.PApplet;

public class Enemy {
    PApplet p;
    int health;
    float x, y;
    int width, height;

    public Enemy(PApplet p, int x, int y) {
        this.p = p;
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isMouseOver() {
        return p.mouseX > x && p.mouseX < x + width && p.mouseY > y && p.mouseY < y + height;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void drawEnemy() {
        p.fill(255, 0, 0);
        if (isMouseOver()) {
            p.stroke(255, 255, 0);
        }
        p.rect(x, y, width, height);
        p.fill(0);
        p.stroke(0);
        p.textSize(20);
        p.text("Health: " + health, x, y -5);
    }
}
