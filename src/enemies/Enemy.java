package enemies;

import player.Player;
import processing.core.PApplet;

import java.util.Random;

import static enemies.NextTurn.*;

public class Enemy {
    PApplet p;
    int health, strength, block, damage, blockGain, strengthGain;
    float x, y;
    int width, height;
    NextTurn nextTurn;

    public Enemy(PApplet p, int x, int y) {
        this.p = p;
        this.x = x;
        this.y = y;
        chooseNextTurn();
    }

    public void chooseNextTurn() {
        Random r = new Random();
        int n = r.nextInt(100);

        if (n > 90) {
            nextTurn = DEBUFF;
        } else if (n > 80) {
            nextTurn = BUFF;
        } else if (n > 40) {
            nextTurn = BLOCK;
        } else
            nextTurn = ATTACK;
    }

    public void doNextTurn(Player player) {
        switch (nextTurn) {
            case ATTACK:
                player.takeDamage(damage + strength);
                break;
            case BLOCK:
                block += blockGain;
                break;
            case BUFF:
                strength += strengthGain;
                break;
            case DEBUFF:
                player.getDebuffed(1);
        }
    }

    public int takeDamage(int dmg) {
        if (block > 0) {
            if (dmg > block) {
                dmg -= block;
                block = 0;
                health -= dmg;
                return dmg;
            } else {
                block -= dmg;
                return 0;
            }
        } else {
            health -= dmg;
            return dmg;
        }
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
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
        p.text("Health: " + health, x, y + height + 25);
        p.text("Next Turn:\n" + nextTurn, x, y - 35);
        float offset = y + height + 50;
        if (block > 0) {
            p.text("Block: " + block, x, offset);
            offset += 25;
        }
        if (strength > 0) {
            p.text("Strength: +" + strength + " dmg", x, offset);
            offset += 25;
        }
    }
}
