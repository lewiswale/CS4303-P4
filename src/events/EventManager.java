package events;

import processing.core.PApplet;

import java.util.ArrayList;

public class EventManager {
    private PApplet p;
    private Event currentEvent;

    public EventManager(PApplet p) {
        this.p = p;
    }

    public void makeEvent() {
        String event = "This is a new event! Do A, B or C!";
        ArrayList<EventOption> options = new ArrayList<>();
        EventOption a = new EventOption(p, "This is option A");
        EventOption b = new EventOption(p, "This is option B");
        EventOption c = new EventOption(p, "This is option C");

        options.add(a);
        options.add(b);
        options.add(c);

        currentEvent = new Event(p, event, options);
    }

    public void displayEvent() {
        currentEvent.displayEvent();
    }
}
