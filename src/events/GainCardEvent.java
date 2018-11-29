package events;

import cards.Card;
import main.MainSketch;
import player.Player;
import processing.core.PApplet;

public class GainCardEvent extends EventOption {
    Card card;

    public GainCardEvent(MainSketch p, String option, Card card) {
        super(p, option);
        this.card = card;
    }

    @Override
    public void activateOption(Player player) {
        player.addCardToDeck(card);
    }
}
