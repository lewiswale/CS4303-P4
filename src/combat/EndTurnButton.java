package combat;

import processing.core.PApplet;

public class EndTurnButton {
    PApplet p;
    float x, y;
    int width, height;

    public EndTurnButton(PApplet p) {
        this.p = p;
        this.x = p.width - 150;
        this.y = p.height - 100;
        this.width = 100;
        this.height = 50;
    }

    public boolean isMouseOver() {
         return p.mouseX > x && p.mouseX < x + width && p.mouseY > y && p.mouseY < y + height;
    }

    public void drawEndTurn() {
        p.fill(50);
        p.rect(x, y, width, height);
        if (isMouseOver()) {
            p.fill(255,255,0);
        } else {
            p.fill(0);
        }
        p.textSize(20);
        p.text("End Turn",x + 5, y + 40);
    }
}
