package events;

import processing.core.PApplet;

public class EventOption {
    private PApplet p;
    private String option;
    private int x, y, width, height;

    public EventOption(PApplet p, String option) {
        this.p = p;
        this.option = option;
        this.width = option.length() * 15;
        this.height = 25;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isMouseOver() {
        return p.mouseX > x && p.mouseX < x + width && p.mouseY > y && p.mouseY < y + height;
    }

    public void drawOption() {
        p.fill(100);
        p.rect(x, y, width, height);
        p.fill(0);
        p.textSize(20);
        if (isMouseOver()) {
            p.fill(255, 255, 0);
        }
        p.text(option, x + 5, y + height - 5);
        p.fill(0);
    }


}
