package events;

import cards.*;
import main.MainSketch;
import player.Player;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class EventManager {
    private MainSketch p;
    private Player player;
    private Event currentEvent;
    private final int amountOfEvents = 7;

    public EventManager(MainSketch p, Player player) {
        this.p = p;
        this.player = player;
    }

    public void makeEvent() {
        Random r = new Random();
        int n = r.nextInt(amountOfEvents);
//        int n = 5;
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
            case 2:
                event = "A dark entity steps out of the shadows. It grins wildly, its clawed hand reaching for your pocket...";

                EventOption run = new HealthEvent(p, "Attampt to flee (take 15 damage)", -15);

                player.reset();
                int i = r.nextInt(player.getDeck().size());
                Card toRemove = player.getDeck().get(i);
                EventOption steal = new RemoveCardEvent(p, "Stand very, VERY, still (Lose <" + toRemove.getName() + ">)", toRemove);

                options.add(run);
                options.add(steal);

                currentEvent = new Event(p, event, options);
                break;
            case 3:
                event = "You stumble upon a priest. He possesses the ability to heal.";

                EventOption heal = new HealthEvent(p, "Let him heal you (Gain 20 health)", 20);
                EventOption learn = new GainCardEvent(p, "Let him teach you (Gain <Leech Life>)", new LeechLife(p));

                options.add(heal);
                options.add(learn);

                currentEvent = new Event(p, event, options);
                break;
            case 4:
                event = "A crazed looking wizard falls out a tree and drops two rings.";

                options.add(new StrengthEvent(p, "Take the red ring (Gain +1 Strength)", 1));
                options.add(new DexterityEvent(p, "Take the blue ring (Gain +1 Dexterity)", 1));
                options.add(new BlankEvent(p, "Ignore him. He's crazy."));

                currentEvent = new Event(p, event, options);
                break;
            case 5:
                p.startFight();
                break;
            case 6:
                p.startPuzzle();
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
