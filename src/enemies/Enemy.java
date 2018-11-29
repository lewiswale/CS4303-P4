package enemies;

import player.Player;
import processing.core.PApplet;

import java.util.Random;

import static enemies.NextTurn.*;

public class Enemy {
    PApplet p;
    int health, strength, block, damage, blockGain, strengthGain, playerDebuff, tempDebuff;
    float x, y;
    int width, height;
    NextTurn nextTurn;
    String nextTurnDisplay;
    Shape shape;

    public Enemy(PApplet p, int x, int y) {
        this.p = p;
        this.x = x;
        this.y = y;
    }

    public void chooseNextTurn() {
        Random r = new Random();
        boolean nextTurnChosen = false;

        while (!nextTurnChosen) {
            int n = r.nextInt(100);

            if (n > 90) {
                if (playerDebuff > 0) {
                    nextTurn = DEBUFF;
                    nextTurnDisplay = "Weaken";
                    nextTurnChosen = true;
                }
            } else if (n > 80) {
                if (blockGain > 0) {
                    nextTurn = BLOCK;
                    nextTurnDisplay = "Block";
                    nextTurnChosen = true;
                }
            } else if (n > 40) {
                if (strengthGain > 0) {
                    nextTurn = BUFF;
                    nextTurnDisplay = "Strengthen";
                    nextTurnChosen = true;
                }
            } else {
                nextTurn = ATTACK;
                nextTurnDisplay = "Attack for " + (damage + strength + tempDebuff);
                nextTurnChosen = true;
            }
        }
    }

    public void doNextTurn(Player player) {
        switch (nextTurn) {
            case ATTACK:
                player.takeDamage(damage + strength + tempDebuff);
                break;
            case BLOCK:
                block += blockGain;
                break;
            case BUFF:
                strength += strengthGain;
                break;
            case DEBUFF:
                player.getDebuffed(playerDebuff);
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

    public void setTempDebuff(int buf) {
        this.tempDebuff = buf;
    }

    public boolean isMouseOver() {
        if (shape == Shape.RECTANGLE) {
            return p.mouseX > x && p.mouseX < x + width && p.mouseY > y && p.mouseY < y + height;
        } else if (shape == Shape.CIRCLE) {
            float x1 = x - p.mouseX;
            x1 *= x1;
            float y1 = y - p.mouseY;
            y1 *= y1;

            return width * width >= x1 + y1;
        } else if (shape == Shape.TRIANGLE) {
            float x1 = x;
            float y1 = y;
            float x2 = x + width;
            float y2 = y;
            float x3 = x + width/2;
            float y3 = y - height;

            float origArea = Math.abs((x2-x1)*(y3-y1) - (x3-x1)*(y2-y1));
            float area1 = Math.abs( (x1-p.mouseX)*(y2-p.mouseY) - (x2-p.mouseX)*(y1-p.mouseY) );
            float area2 = Math.abs( (x2-p.mouseX)*(y3-p.mouseY) - (x3-p.mouseX)*(y2-p.mouseY) );
            float area3 = Math.abs( (x3-p.mouseX)*(y1-p.mouseY) - (x1-p.mouseX)*(y3-p.mouseY) );

            return area1 + area2 + area3 == origArea;
        } else
            return false;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void drawEnemy() {
        p.fill(255, 0, 0);
        if (isMouseOver()) {
            p.stroke(255, 255, 0);
        }
        if (shape == Shape.RECTANGLE) {
            p.rect(x, y, width, height);
        } else if (shape == Shape.TRIANGLE) {
            p.triangle(x, y, x + width, y, x + width/2, y - height);
        } else if (shape == Shape.CIRCLE) {
            p.ellipse(x, y, width, height);
        }
        p.fill(0);
        p.stroke(0);
        p.textSize(20);
        float offset;
        if (shape == Shape.RECTANGLE) {
            p.text("Next Turn:\n" + nextTurnDisplay, x, y - 35);
            p.text("Health: " + health, x, y + height + 25);
            offset = y + height + 50;
        } else if (shape == Shape.CIRCLE){
            p.text("Next Turn:\n" + nextTurnDisplay, x - width/2, y - height/2 - 35);
            p.text("Health: " + health, x - width/2, y + height/2 + 25);
            offset = y + height/2 + 50;
        } else {
            p.text("Next Turn:\n" + nextTurnDisplay, x, y - height - 35);
            p.text("Health:" + health, x, y + 25);
            offset = y + 50;
        }
        if (block > 0) {
            p.text("Block: " + block, x, offset);
            offset += 25;
        }
        if (strength + tempDebuff > 0) {
            p.text("Strength: +" + (strength + tempDebuff) + " dmg", x, offset);
            offset += 25;
        } else if (strength + tempDebuff < 0) {
            p.text("Strength: " + (strength + tempDebuff) + " dmg", x, offset);
            offset += 25;
        }
    }
}
