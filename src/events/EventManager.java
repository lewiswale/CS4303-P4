package events;

import cards.Dexterity;
import cards.Foresight;
import cards.Strength;
import main.MainSketch;
import player.Player;
import processing.core.PApplet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class EventManager {
    private MainSketch p;
    private Player player;
    private Event currentEvent;

    public EventManager(MainSketch p, Player player) {
        this.p = p;
        this.player = player;
    }

    public void makeEvent() {
        Random r = new Random();
        int n = r.nextInt(2);
        String event = "";
        ArrayList<EventOption> options = new ArrayList<>();

        switch (n) {
            case 0:
                event = "You meet an old man with three items. He offers to give you one.";
                EventOption getStrength = new GainCardEvent(p, "Get a new sword. (gain <Strength> card)", new Strength(p));
                EventOption getDex = new GainCardEvent(p, "Get a new shield. (gain <Dexterity> card)", new Dexterity(p));
                EventOption getFS = new GainCardEvent(p, "Get a new book. (gain <Foresight> card)", new Foresight(p));

                options.add(getStrength);
                options.add(getDex);
                options.add(getFS);

                currentEvent = new Event(p, event, options);
                break;
            case 1:
                event = "You are in a bar. A drunkard challenges you. \"What will it be?\" he says. \"Brains or brawn?\"";
                EventOption fight = new FightEvent(p, "Time to fight baby!");
                EventOption puzzle = new PuzzleEvent(p, "The mind is the greatest weapon.");

                options.add(fight);
                options.add(puzzle);

                currentEvent = new Event(p, event, options);
                break;
        }
    }

    public boolean checkSelection() {
        ArrayList<EventOption> options = currentEvent.getOptions();
        for (EventOption option: options) {
            if (option.isMouseOver()) {
                option.activateOption(player);
                return true;
            }
        }

        return false;
    }

    public void displayEvent() {
        currentEvent.displayEvent();
    }
}
