package cards;

import combat.Target;
import enemies.Enemy;
import player.Player;
import processing.core.PApplet;

public class Card {
    PApplet p;
    String name;
    String flavourText;
    int cost;
    final int CARD_WIDTH = 200;
    final int CARD_HEIGHT = 300;
    boolean mouseIsOver = false;
    boolean doNotMove = false;
    int x, y;
    Target canTarget;

    public Card(PApplet p) {
        this.p = p;
    }

    public void setMouseIsOver(boolean mouseIsOver) {
        this.mouseIsOver = mouseIsOver;
    }

    public boolean isMouseIsOver() {
        return mouseIsOver;
    }

    public int getCARD_WIDTH() {
        return CARD_WIDTH;
    }

    public int getCARD_HEIGHT() {
        return CARD_HEIGHT;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDoNotMove() {
        return doNotMove;
    }

    public void setDoNotMove(boolean doNotMove) {
        this.doNotMove = doNotMove;
    }

    public void activateCard(Player player) {}

    public void activateCard(Enemy enemy) {}

    public Target getCanTarget() {
        return canTarget;
    }

    public void drawCard() {
        p.fill(0, 191, 255);
        p.rect(x, y, CARD_WIDTH, CARD_HEIGHT);
        p.fill(0);
        p.textSize(30);
        p.text(name, x + 10, y + 35);
    }
}
