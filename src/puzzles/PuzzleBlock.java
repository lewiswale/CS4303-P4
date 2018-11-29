package puzzles;

import processing.core.PApplet;

public class PuzzleBlock {
    private PApplet p;
    private boolean isMine, isCovered;
    private int clue;
    private int x, y;
    private final int WIDTH = 50;

    public PuzzleBlock(PApplet p) {
        this.p = p;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public int getClue() {
        return clue;
    }

    public void setClue(int clue) {
        this.clue = clue;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isMouseOver() {
        return p.mouseX > x && p.mouseX < x + WIDTH && p.mouseY > y && p.mouseY < y + WIDTH;
    }

    public void drawBlock() {
        if (isCovered) {
            p.fill(50);
            if (isMouseOver()) {
                p.stroke(255, 255, 0);
            }
        } else {
            p.fill(200);
        }

        p.rect(x, y, WIDTH, WIDTH);
        p.fill(0);
        p.stroke(0);

        if (!isCovered) {
            p.textSize(20);
            p.text(clue, x + 20, y + WIDTH);
        }

    }
}
