package events;

import processing.core.PApplet;

import java.util.ArrayList;

public class Event {
    private PApplet p;
    private String eventText;
    private ArrayList<EventOption> options;

    public Event(PApplet p, String eventText, ArrayList<EventOption> options) {
        this.p = p;
        this.eventText = eventText;
        this.options = options;
    }

    public void displayEvent() {
        p.textSize(30);
        p.text(eventText, 100, 100);
        int x = 300;
        int y = 300;

        for (EventOption option: options) {
            option.setXY(x, y);
            option.drawOption();
            y += 100;
        }
    }
}
